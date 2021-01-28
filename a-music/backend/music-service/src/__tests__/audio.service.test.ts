import ModelFactory from '../models/index';
jest.mock('../models/index');
import FileGrid from '../helpers/file-storage';
jest.mock('../helpers/file-storage');
import { AudioService } from '../services/audio.service';
import { createReadStream, unlink, statSync, readdirSync } from 'fs';
import { join } from 'path';
import { audioUnit, newAudioUnit } from '../__fixtures__/audio';

// Data for testing
let req: any;
let res: any;
let audioCollection;

// Mocked models
const audioModel = {
    createMapping: jest.fn(),
    synchronize: jest.fn(),
    find: jest.fn(() => Promise.resolve(audioCollection)),
    search: jest.fn((searchOptions, filterOptions, searchCallback) => {}),
    findOneAndUpdate: jest.fn(() => {
        audioCollection[0].likes += 1;
        return Promise.resolve(audioCollection);
    }),
} as any;

const save = jest.fn(() => {
    audioCollection = [...audioCollection, newAudioUnit];
    return Promise.resolve(audioCollection);
});
const audioModelForSave = jest.fn().mockImplementation(() => {
    return { save };
});

(audioModelForSave as any).__proto__.createMapping = jest.fn();
(audioModelForSave as any).__proto__.synchronize = jest.fn();
(audioModelForSave as any).__proto__.updateOne = jest.fn(() =>
    Promise.resolve()
);

FileGrid.gfs = {
    write: jest.fn((options, readStream, callback) => callback(null, true)),
    read: jest.fn((options) => {
        return createReadStream(
            join(__dirname, '..', '__fixtures__', 'demo.mp3')
        );
    }),
    unlink: jest.fn((options, callback) => callback(null, true)),
};

(unlink as any) = jest.fn();

beforeEach(() => {
    res = {
        statusCode: 200,
        body: {},
        status: jest.fn((code) => {
            res.statusCode = code;
            return res;
        }),
        json: jest.fn((result) => {
            res.body = result;
            return res;
        }),
    } as any;

    req = {
        body: {},
        file: {},
        params: {},
        query: {},
    } as any;

    audioCollection = [audioUnit];

    ModelFactory.prototype.getModel = jest.fn(
        (modelName: string) => audioModel
    );
    jest.clearAllMocks();
});

describe('Testing audio service', () => {
    describe('Valid flow', () => {
        test('Getting correct models', () => {
            new AudioService();
            expect(ModelFactory).toHaveBeenCalledTimes(1);
            const modelFactoryInstance: ModelFactory = (ModelFactory as any)
                .mock.instances[0];
            expect(modelFactoryInstance.getModel).toHaveBeenCalledWith(
                'AudioModel'
            );
            expect(modelFactoryInstance.getModel).toHaveBeenCalledWith(
                'GenreModel'
            );
        });

        test('Testing get_all_audio method', async () => {
            const audioService = new AudioService();
            await audioService.get_all_audio(req, res);

            expect(audioModel.find).toHaveBeenCalledTimes(1);
            expect(res.statusCode).toEqual(200);
            expect(res.body).toBe(audioCollection);
        });

        test('Testing get_by_type method', async () => {
            req.params = {
                type: 'music',
            };

            const audioService = new AudioService();
            await audioService.get_by_type(req, res);

            expect(audioModel.find).toHaveBeenCalledWith({
                type: req.params.type,
            });
            expect(res.statusCode).toEqual(200);
            expect(res.body).toBe(audioCollection);
        });

        test('Testing get_tracks_by_ids method', async () => {
            req.query = {
                track_ids: [audioUnit._id],
            };

            const audioService = new AudioService();
            await audioService.get_tracks_by_ids(req, res);

            expect(audioModel.find).toHaveBeenCalledWith({
                track_id: { $in: req.query.track_ids },
            });
            expect(res.statusCode).toEqual(200);
            expect(res.body).toBe(audioCollection);
        });

        test('Testing upload_audio method', async () => {
            ModelFactory.prototype.getModel = jest.fn(
                (modelName: string) => audioModelForSave
            ) as any;

            req.files = {
                track: [{ filename: newAudioUnit.track_id }],
                cover: [{ filename: newAudioUnit.cover_id }],
            };
            req.body = {
                author: newAudioUnit.author,
                title: newAudioUnit.title,
                type: newAudioUnit.type,
                genres: newAudioUnit.genres,
                album: newAudioUnit.album,
                format: 'mp3',
                bitrate: '128',
            };

            const audioService = new AudioService();
            await audioService.upload_audio(req, res);
            const { size: demoFileSize } = statSync(
                join(__dirname, '..', '__fixtures__', 'demo.mp3')
            );
            const pressed_file_name = readdirSync(
                join(__dirname, '..', '__fixtures__')
            ).find((value) => value.match(/pressed_temp-(\w+)/));
            const { size: pressedDemoFileSize } = statSync(
                join(__dirname, '..', '__fixtures__', pressed_file_name)
            );

            expect(audioModelForSave).toHaveBeenCalledWith({
                ...req.body,
                track_id: req.files.track[0].filename,
                cover_id: req.files.cover[0].filename,
            });
            expect(save).toHaveBeenCalledTimes(1);
            expect(res.statusCode).toEqual(200);
            expect(res.body).toBe(audioCollection);
            expect(pressedDemoFileSize).toBeLessThanOrEqual(demoFileSize);
        });

        test('Testing set_like_to_audio_by_id method', async () => {
            req.body = {
                track_id: audioUnit.track_id,
            };

            expect(audioCollection[0].likes).toBe(0);

            const audioService = new AudioService();
            await audioService.set_like_to_audio_by_id(req, res);

            expect(audioModel.findOneAndUpdate).toHaveBeenCalledWith(
                {
                    track_id: req.body.track_id,
                },
                expect.anything(),
                expect.anything()
            );
            expect(res.statusCode).toEqual(200);
            expect(audioCollection[0].likes).toBe(1);
            expect(res.body).toBe(audioCollection);
        });
    });

    describe('Getting errors', () => {
        beforeEach(() => {
            ModelFactory.prototype.getModel = jest.fn((modelName: string) => ({
                createMapping: jest.fn(),
                synchronize: jest.fn(),
            })) as any;
        });

        test('Error in get_all_audio method', async () => {
            const playlistService = new AudioService();
            await playlistService.get_all_audio(req, res);

            expect(res.statusCode).toEqual(500);
        });

        test('Error in get_by_type method', async () => {
            const playlistService = new AudioService();
            await playlistService.get_by_type(req, res);

            expect(res.statusCode).toEqual(500);
        });

        test('Error in get_tracks_by_ids method (500)', async () => {
            const playlistService = new AudioService();
            await playlistService.get_tracks_by_ids(req, res);

            expect(res.statusCode).toEqual(500);
        });

        test('Error in upload_audio method', async () => {
            const playlistService = new AudioService();
            await playlistService.upload_audio(req, res);

            expect(res.statusCode).toEqual(500);
        });

        test('Error in set_like_to_audio_by_id method (500)', async () => {
            const playlistService = new AudioService();
            await playlistService.set_like_to_audio_by_id(req, res);

            expect(res.statusCode).toEqual(500);
        });
    });
});
