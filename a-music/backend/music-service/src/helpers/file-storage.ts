import { Request } from 'express';
import path from 'path';
import mongoose, { Connection } from 'mongoose';
import { config } from 'dotenv';
import crypto from 'crypto';
import multer from 'multer';
import GridFs from 'multer-gridfs-storage';
import { MONGO_URL } from '../constants/db';
import Grid from 'mongoose-gridfs';

config();
const mongo_port = process.env.DB_URI || MONGO_URL;

interface FileStorageBuffer {
    filename: string;
    bucketName: string;
}

export class FileStorage {
    public storage: GridFs;
    public upload: any;
    private static instance: FileStorage;

    private constructor() {
        this.storage = new GridFs({
            db: mongoose.createConnection(mongo_port, {
                user: process.env.MONGODB_APPLICATION_USER,
                pass: process.env.MONGODB_APPLICATION_PASS,
                useNewUrlParser: true,
                useUnifiedTopology: true,
                useCreateIndex: true,
                useFindAndModify: false,
            }),
            file: FileStorage.file_handler,
        });
        this.upload = multer({ storage: this.storage });
    }

    static async file_handler(
        req: Request,
        file: any
    ): Promise<FileStorageBuffer | Error> {
        try {
            const buffer = await crypto.randomBytes(16);

            return {
                filename: `${buffer.toString('hex')}${path.extname(
                    file.originalname
                )}`,
                bucketName: 'uploads',
            };
        } catch (error) {
            throw Error(error.message);
        }
    }

    public static getInstance(): FileStorage {
        if (!FileStorage.instance) {
            FileStorage.instance = new FileStorage();
        }

        return FileStorage.instance;
    }
}

class FileGrid {
    public static gfs: any;

    constructor(connection: Connection) {
        FileGrid.gfs = Grid.createModel({ bucketName: 'uploads', connection });
    }
}

export default FileGrid;
