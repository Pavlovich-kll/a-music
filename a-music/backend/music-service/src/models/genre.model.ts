import { Schema } from 'mongoose';
import GenresEnum from '../constants/genres';

interface IGenre {
    title: string;
    in_tracks_count: number;
}

export const GenreSchema: Schema = new Schema<IGenre>({
    title: { type: Schema.Types.String, enum: GenresEnum, required: true },
    in_tracks_count: { type: Schema.Types.Number, default: 1 },
});
