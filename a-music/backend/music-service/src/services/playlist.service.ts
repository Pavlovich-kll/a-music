import { Request, Response } from 'express';
import { MongooseDocument } from 'mongoose';
import ModelFactory from '../models';

export class PlaylistService {
    private readonly model: any;

    constructor() {
        const modelFactoryInstance: ModelFactory = new ModelFactory();
        this.model = modelFactoryInstance.getModel('PlaylistModel');
        modelFactoryInstance.getModel('AudioModel');

        this.get_all_playlists = this.get_all_playlists.bind(this);
        this.create_new_playlist = this.create_new_playlist.bind(this);
        this.get_playlist = this.get_playlist.bind(this);
        this.set_like = this.set_like.bind(this);
        this.set_new_tracks = this.set_new_tracks.bind(this);
    }

    public async get_all_playlists(request: Request, response: Response) {
        try {
            const doc: MongooseDocument = await this.model
                .find()
                .populate('tracks');
            response.json(doc);
        } catch (error) {
            response.status(500).json({ message: error.message });
        }
    }

    public async create_new_playlist(
        request: Request & { file: any },
        response: Response
    ) {
        try {
            const playlist = new this.model({
                ...request.body,
                track_count: request.body.tracks.length,
                pic: request.file.filename,
            });
            const playlist_document: MongooseDocument = await playlist.save();
            const populated_playlist_document: MongooseDocument = await playlist_document
                .populate('tracks')
                .execPopulate();
            response.json(populated_playlist_document);
        } catch (error) {
            response.status(500).json({ message: error.message });
        }
    }

    public async get_playlist(request: Request, response: Response) {
        try {
            const doc: MongooseDocument = await this.model
                .findById(request.params.id)
                .populate('tracks');
            response.json(doc);
        } catch (error) {
            response.status(500).json({ message: error.message });
        }
    }

    public async set_like(request: Request, response: Response) {
        try {
            const doc: MongooseDocument = await this.model
                .findOneAndUpdate(
                    { _id: request.body.id },
                    {
                        $inc: {
                            likes: 1,
                        },
                    },
                    { new: true }
                )
                .populate('tracks');
            response.json(doc);
        } catch (error) {
            response.status(500).json({ message: error.message });
        }
    }

    public async set_new_tracks(request: Request, response: Response) {
        try {
            const { tracks, id } = request.body;
            const doc: MongooseDocument = await this.model
                .findOneAndUpdate(
                    { _id: id },
                    {
                        $push: { tracks },
                        $inc: {
                            track_count: tracks.length,
                        },
                    },
                    { new: true }
                )
                .populate('tracks');
            response.json(doc);
        } catch (error) {
            response.status(500).json({ message: error.message });
        }
    }
}
