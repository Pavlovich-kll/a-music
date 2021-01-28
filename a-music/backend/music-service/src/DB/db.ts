import mongoose, { Connection } from 'mongoose';
import { config } from 'dotenv';
import { MONGO_URL } from '../constants/db';
import FileGrid from '../helpers/file-storage';

config();
const mongo_port = process.env.DB_URI || MONGO_URL;

export class Mongoose {
    static connection: Connection;
    constructor() {
        this.connect();
    }

    private async connect(): Promise<void> {
        mongoose.Promise = global.Promise;
        await mongoose.connect(mongo_port, {
            user: process.env.MONGODB_APPLICATION_USER,
            pass: process.env.MONGODB_APPLICATION_PASS,
            useNewUrlParser: true,
            useUnifiedTopology: true,
            useCreateIndex: true,
            useFindAndModify: false,
        });

        new FileGrid(mongoose.connection);
    }
}
