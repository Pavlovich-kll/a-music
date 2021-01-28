import { Request, Response } from 'express';
import { ALLOWED_AUDIO_TYPES } from '../constants/allowed_audio_types';
import FileGrid from '../helpers/file-storage';
import ModelFactory from '../models';

export class ContentService {
    private readonly model: any;

    constructor() {
        const modelFactoryInstance: ModelFactory = new ModelFactory();
        this.model = modelFactoryInstance.getModel('GenreModel');

        this.get_types = this.get_types.bind(this);
        this.get_genres = this.get_genres.bind(this);
        this.get_file = this.get_file.bind(this);
        this.create_file_stream = this.create_file_stream.bind(this);
    }

    public get_types(request: Request, response: Response) {
        try {
            response.json(ALLOWED_AUDIO_TYPES);
        } catch (error) {
            response.status(500).json({ message: error.message });
        }
    }

    public async get_genres(request: Request, response: Response) {
        try {
            const genres = await this.model.find();
            response.json(genres);
        } catch (error) {
            response.status(500).json({ message: error.message });
        }
    }

    public get_file(request: Request, response: Response) {
        FileGrid.gfs.findOne(
            { filename: request.params.id },
            this.create_file_stream(response)
        );
    }

    private create_file_stream(response: Response) {
        return (error: Error | null, file: any) => {
            if (!file) {
                return response
                    .status(500)
                    .json({ message: 'File is not found' });
            }
            if (error) {
                return response.status(500).json({ message: error.message });
            }
            response.writeHead(200, {
                'Content-Range': `bytes ${file.chunkSize}`,
                'Accept-Ranges': 'bytes',
                'Content-Length': file.length,
                'Content-Type': file.contentType,
            });

            FileGrid.gfs.read({ _id: file._id }).pipe(response);
        };
    }
}
