import { Request, Response } from 'express';
import { promisify } from 'util';
import { MongooseDocument } from 'mongoose';
import { head } from 'ramda';
import { capitalize } from '../helpers/strings';
import GenresEnum from '../constants/genres';
import { createWriteStream, createReadStream, unlink, ReadStream } from 'fs';
import ModelFactory from '../models';
import FileGrid from '../helpers/file-storage';
import { join } from 'path';
import ffmpeg from 'fluent-ffmpeg';
import ffmpeg_static from 'ffmpeg-static';
import uuid4 from 'uuid4';

export interface MulterFile {
    key: string; // Available using `S3`.
    path: string; // Available using `DiskStorage`.
    mimetype: string;
    originalname: string;
    size: number;

    [key: string]: any;
}

export interface MulterBundle {
    [filename: string]: MulterFile[];
}

export interface IFiles {
    files: MulterBundle;
}

type SupportedFormats = 'ogg' | 'mp3';
type SupportedBitrates = '64' | '128' | '192' | '320';

export class AudioService {
    private readonly model: any;
    private readonly genreModel: any;

    constructor() {
        const modelFactoryInstance: ModelFactory = new ModelFactory();
        this.model = modelFactoryInstance.getModel('AudioModel');
        this.genreModel = modelFactoryInstance.getModel('GenreModel');

        // Передача кастомного анализатора при необходимости
        this.model.createMapping(
            {
                settings: {
                    analysis: {
                        analyzer: {
                            autocomplete: {
                                tokenizer: 'autocomplete',
                                filter: ['lowercase'],
                            },
                            autocomplete_search: {
                                tokenizer: 'lowercase',
                            },
                        },
                        tokenizer: {
                            autocomplete: {
                                type: 'edge_ngram',
                                min_gram: 2,
                                max_gram: 20,
                                token_chars: ['letter'],
                            },
                        },
                    },
                },
                mappings: {
                    properties: {
                        quote: {
                            type: 'text',
                            analyzer: 'autocomplete',
                            search_analyzer: 'autocomplete_search',
                        },
                    },
                },
            },
            (error, mapping) => {}
        );
        this.model.synchronize();

        this.upload_audio = this.upload_audio.bind(this);
        this.get_all_audio = this.get_all_audio.bind(this);
        this.get_tracks_by_ids = this.get_tracks_by_ids.bind(this);
        this.set_like_to_audio_by_id = this.set_like_to_audio_by_id.bind(this);
        this.get_by_type = this.get_by_type.bind(this);
        this.search = this.search.bind(this);
    }

    public async get_all_audio(req: Request, res: Response) {
        try {
            const doc: MongooseDocument = await this.model.find();
            res.json(doc);
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    }

    public async search(req: Request, res: Response) {
        this.model.search = promisify(this.model.search);
        try {
            const results = await this.model.search(
                {
                    simple_query_string: {
                        query: req.query.searching,
                        fields: [
                            'author^5',
                            'title^4',
                            'album^3',
                            'genres^2',
                            'type',
                        ],
                    },
                },
                {
                    hydrate: true,
                }
            );
            const {
                hits: { hits: data },
            } = results;
            res.json(data);
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    }

    public async get_by_type(req: Request, res: Response) {
        try {
            const result: MongooseDocument = await this.model.find({
                type: req.params.type,
            });
            res.json(result);
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    }

    public async get_tracks_by_ids(req: Request, res: Response) {
        try {
            const result: MongooseDocument = await this.model.find({
                track_id: { $in: req.query.track_ids },
            });
            res.json(result);
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    }

    public async upload_audio(req: Request & IFiles, res: Response) {
        FileGrid.gfs.write = promisify(FileGrid.gfs.write);
        FileGrid.gfs.unlink = promisify(FileGrid.gfs.unlink);
        try {
            const { track, cover } = req.files;
            const singleTrack: MulterFile = head(track);
            const singleCover: MulterFile = head(cover);
            await this.fetchAllGenresFromRequest(req);

            const audio = new this.model({
                ...req.body,
                track_id: singleTrack.filename as string,
                cover_id: singleCover.filename as string,
            });
            const audio_document: MongooseDocument = await audio.save();

            if (!req.body.format && !req.body.bitrate) {
                return res.json(audio_document);
            }

            if (req.body.format && req.body.bitrate) {
                const format: SupportedFormats = req.body.format;
                const bitrate: SupportedBitrates = req.body.bitrate;

                const mimetype =
                    req.body.format === 'mp3' ? 'mpeg' : req.body.format;

                const pressedTempFilePath: string = join(
                    __dirname,
                    '..',
                    '__fixtures__',
                    `pressed_temp-${uuid4()}`
                );

                const gridReadStream: ReadStream = FileGrid.gfs.read({
                    filename: singleTrack.filename,
                });

                const fsReadStream: ReadStream = await this.compressAudio(
                    gridReadStream,
                    format,
                    bitrate,
                    pressedTempFilePath
                );
                await FileGrid.gfs.write(
                    {
                        filename: singleTrack.filename,
                        contentType: `audio/${mimetype}`,
                    },
                    fsReadStream
                );

                await FileGrid.gfs.unlink({ _id: singleTrack.id });
                unlink(pressedTempFilePath, (err) => {});

                res.json(audio_document);
            } else {
                throw Error('Both format and bitrate must be specified');
            }
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    }

    public async set_like_to_audio_by_id(req: Request, res: Response) {
        try {
            const { track_id } = req.body;
            const audio = await this.model.findOneAndUpdate(
                { track_id },
                {
                    $inc: {
                        likes: 1,
                    },
                    updatedAt: Date.now(),
                },
                { new: true }
            );
            res.json(audio);
        } catch (error) {
            res.status(500).json(error);
        }
    }

    private compressAudio = (
        readStream: ReadStream,
        targetFormat: string,
        targetBitrate: string,
        pathForSavingResult: string
    ): Promise<ReadStream> => {
        return new Promise<ReadStream>((resolve, reject) => {
            ffmpeg(readStream)
                .setFfmpegPath(ffmpeg_static)
                .toFormat(targetFormat)
                .audioBitrate(targetBitrate)
                .on('end', () => {
                    resolve(createReadStream(pathForSavingResult));
                })
                .on('error', (err) => {
                    reject(err);
                })
                .pipe(createWriteStream(pathForSavingResult), {
                    end: true,
                });
        });
    };

    private fetchAllGenresFromRequest = async (req: Request): Promise<void> => {
        const genres: string[] = (req.body.genres as string[])
            .filter(Boolean)
            .map((genre: string) => capitalize(genre.toLowerCase() as any));

        if (genres.some((genre) => !GenresEnum.includes(genre))) {
            throw Error('One of the genres has invalid type');
        }
        try {
            const promiseArray = genres.map((genre) => {
                return this.genreModel.updateOne(
                    {
                        title: genre,
                    },
                    {
                        title: genre,
                        $inc: {
                            in_tracks_count: 1,
                        },
                    },
                    { upsert: true }
                );
            });
            await Promise.all(promiseArray);
        } catch (error) {
            throw error;
        }
    };
}
