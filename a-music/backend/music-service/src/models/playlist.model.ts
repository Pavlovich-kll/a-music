import { Schema } from 'mongoose';

interface IPlaylist {
    pic: string;
    title: string;
    track_count: number;
    likes_count: number;
    tracks: Array<string | number>;
}

export const PlaylistSchema: Schema = new Schema<IPlaylist>(
    {
        pic: { type: Schema.Types.String, required: true },
        title: { type: Schema.Types.String, required: true },
        track_count: { type: Schema.Types.Number },
        description: { type: Schema.Types.String, required: true },
        tracks: [
            { type: Schema.Types.ObjectId, ref: 'AudioModel', required: true },
        ],
        likes: { type: Schema.Types.Number, default: 0 },
        createdAt: { type: Date, default: Date.now },
    },
    {
        timestamps: true,
    }
);
