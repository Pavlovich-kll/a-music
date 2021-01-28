import mongoose, { Schema, Model, Document, Connection } from 'mongoose';
import { config } from 'dotenv';
import { MONGO_URL } from '../constants/db';
import { AudioSchema } from './audio.model';
import { PlaylistSchema } from './playlist.model';
import { GenreSchema } from './genre.model';

config();
const mongo_port = process.env.DB_URI || MONGO_URL;

interface IModelsMap {
    [key: string]: Schema;
}

class ModelFactory {
    connection: Connection;

    static modelsMap: IModelsMap = {
        AudioModel: AudioSchema,
        PlaylistModel: PlaylistSchema,
        GenreModel: GenreSchema,
    };

    constructor() {
        this.connection = mongoose.createConnection(mongo_port, {
            user: process.env.MONGODB_APPLICATION_USER,
            pass: process.env.MONGODB_APPLICATION_PASS,
            useNewUrlParser: true,
            useUnifiedTopology: true,
            useCreateIndex: true,
            useFindAndModify: false,
        });

        this.getModel = this.getModel.bind(this);
    }

    getModel(modelName: string): Model<Document, {}> {
        return this.connection.model(
            modelName,
            ModelFactory.modelsMap[modelName]
        );
    }
}

export default ModelFactory;
