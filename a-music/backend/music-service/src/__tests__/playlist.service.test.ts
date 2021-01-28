import ModelFactory from '../models/index';
jest.mock('../models/index');
import { PlaylistService } from '../services/playlist.service';
import { playlistUnit, newPlaylistUnit } from '../__fixtures__/playlist';
import { newAudioUnit } from '../__fixtures__/audio';

// Data for testing
let req: any;
let res: any;
let playlistCollection;

// Mocked models
const playlistModel = {
    find: jest.fn(() => playlistModel),
    findById: jest.fn((id: string) => playlistModel),
    findOneAndUpdate: jest.fn((id: string, filter, config) => {
        const actions = {
            $inc: () => (playlistCollection[0].likes += 1),
            $push: () => {
                playlistCollection[0].tracks = [
                    ...playlistCollection[0].tracks,
                    newAudioUnit,
                ];
                playlistCollection[0].track_count += 1;
            },
        };
        const [key] = Object.keys(filter);
        actions[key]();
        return playlistModel;
    }),
    populate: jest.fn((target: string) => {
        return Promise.resolve(playlistCollection);
    }),
} as any;

const savedPlaylistModel = {
    populate: jest.fn((target: string) => {
        return savedPlaylistModel;
    }),
    execPopulate: jest.fn(() => {
        return Promise.resolve(playlistCollection);
    }),
};
const save = jest.fn(() => {
    playlistCollection = [...playlistCollection, newPlaylistUnit];
    return savedPlaylistModel;
});
const playlistModelForSave = jest.fn().mockImplementation(() => {
    return { save };
});

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
    } as any;

    playlistCollection = [playlistUnit];

    ModelFactory.prototype.getModel = jest.fn(
        (modelName: string) => playlistModel
    );
    jest.clearAllMocks();
});

describe('Testing playlist service', () => {
    describe('Valid flow', () => {
        test('Getting correct models', () => {
            new PlaylistService();
            expect(ModelFactory).toHaveBeenCalledTimes(1);
            const modelFactoryInstance: ModelFactory = (ModelFactory as any)
                .mock.instances[0];
            expect(modelFactoryInstance.getModel).toHaveBeenCalledWith(
                'PlaylistModel'
            );
            expect(modelFactoryInstance.getModel).toHaveBeenCalledWith(
                'AudioModel'
            );
        });

        test('Testing get_all_playlists method', async () => {
            const playlistService = new PlaylistService();
            await playlistService.get_all_playlists(req, res);

            expect(playlistModel.find).toHaveBeenCalledTimes(1);
            expect(playlistModel.populate).toHaveBeenCalledWith('tracks');
            expect(res.statusCode).toEqual(200);
            expect(res.body).toBe(playlistCollection);
        });

        test('Testing create_new_playlist method', async () => {
            req.body = {
                title: newPlaylistUnit.title,
                description: newPlaylistUnit.description,
                tracks: newPlaylistUnit.tracks,
            };
            req.file = {
                filename: newPlaylistUnit.pic,
            };
            ModelFactory.prototype.getModel = jest.fn(
                (modelName: string) => playlistModelForSave
            ) as any;

            const playlistService = new PlaylistService();
            await playlistService.create_new_playlist(req, res);

            expect(playlistModelForSave).toHaveBeenCalledWith({
                ...req.body,
                pic: req.file.filename,
                track_count: req.body.tracks.length,
            });
            expect(save).toHaveBeenCalledTimes(1);
            expect(res.statusCode).toEqual(200);
            expect(res.body).toBe(playlistCollection);
        });

        test('Testing get_playlist method', async () => {
            req.params = {
                id: playlistUnit._id,
            };

            const playlistService = new PlaylistService();
            await playlistService.get_playlist(req, res);

            expect(playlistModel.findById).toHaveBeenCalledWith(req.params.id);
            expect(playlistModel.populate).toHaveBeenCalledWith('tracks');
            expect(res.statusCode).toEqual(200);
            expect(res.body).toBe(playlistCollection);
        });

        test('Testing set_like method', async () => {
            req.body = {
                id: playlistUnit._id,
            };

            expect(playlistCollection[0].likes).toBe(0);

            const playlistService = new PlaylistService();
            await playlistService.set_like(req, res);

            expect(playlistModel.findOneAndUpdate).toHaveBeenCalledWith(
                { _id: req.body.id },
                expect.anything(),
                expect.anything()
            );
            expect(res.statusCode).toEqual(200);
            expect(res.body).toBe(playlistCollection);
            expect(res.body[0].likes).toBe(1);
        });

        test('Testing set_new_tracks method', async () => {
            req.body = {
                id: playlistUnit._id,
                tracks: [newAudioUnit._id],
            };

            expect(playlistCollection[0].track_count).toBe(1);

            const playlistService = new PlaylistService();
            await playlistService.set_new_tracks(req, res);

            expect(playlistModel.findOneAndUpdate).toHaveBeenCalledWith(
                { _id: req.body.id },
                {
                    $push: { tracks: req.body.tracks },
                    $inc: {
                        track_count: req.body.tracks.length,
                    },
                },
                expect.anything()
            );
            expect(playlistModel.populate).toHaveBeenCalled();
            expect(res.statusCode).toEqual(200);
            expect(res.body).toBe(playlistCollection);
            expect(res.body[0].track_count).toBe(2);
        });
    });

    describe('Getting errors', () => {
        beforeEach(() => {
            ModelFactory.prototype.getModel = jest.fn(
                (modelName: string) => {}
            ) as any;
        });

        test('Error in get_all_playlists method', async () => {
            const playlistService = new PlaylistService();
            await playlistService.get_all_playlists(req, res);

            expect(res.statusCode).toEqual(500);
        });

        test('Error in create_new_playlist method', async () => {
            const playlistService = new PlaylistService();
            await playlistService.create_new_playlist(req, res);

            expect(res.statusCode).toEqual(500);
        });

        test('Error in get_playlist method', async () => {
            const playlistService = new PlaylistService();
            await playlistService.get_playlist(req, res);

            expect(res.statusCode).toEqual(500);
        });

        test('Error in set_like method (500)', async () => {
            const playlistService = new PlaylistService();
            await playlistService.set_like(req, res);

            expect(res.statusCode).toEqual(500);
        });

        test('Error in set_new_tracks method (500)', async () => {
            const playlistService = new PlaylistService();
            await playlistService.set_new_tracks(req, res);

            expect(res.statusCode).toEqual(500);
        });
    });
});
