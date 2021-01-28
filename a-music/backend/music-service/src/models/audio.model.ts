import { Schema } from 'mongoose';
import { config } from 'dotenv';
import mongoosastic from 'mongoosastic';
import {
    ALLOWED_AUDIO_TYPES,
    AllowedType,
} from '../constants/allowed_audio_types';
import { ELASTIC_URL } from '../constants/elastic';

config();

const TypeEnum: string[] = ALLOWED_AUDIO_TYPES.map(({ id }: AllowedType) => id);

const AudioSchema: Schema = new Schema({
    author: {
        type: Schema.Types.String,
        required: true,
        es_indexed: true,
        es_analyzer: 'autocomplete',
        es_search_analyzer: 'autocomplete_search',
    },
    title: {
        type: Schema.Types.String,
        required: true,
        es_indexed: true,
        es_analyzer: 'autocomplete',
        es_search_analyzer: 'autocomplete_search',
    },
    album: {
        type: Schema.Types.String,
        default: 'Untitled',
        es_indexed: true,
        es_analyzer: 'autocomplete',
        es_search_analyzer: 'autocomplete_search',
    },
    track_id: { type: Schema.Types.String, required: true, unique: true },
    cover_id: { type: Schema.Types.String, required: true },
    type: {
        type: Schema.Types.String,
        required: true,
        enum: TypeEnum,
        es_indexed: true,
        es_analyzer: 'autocomplete',
        es_search_analyzer: 'autocomplete_search',
    },
    likes: { type: Schema.Types.Number, default: 0 },
    createdAt: { type: Date, default: Date.now },
    updatedAt: { type: Date, default: Date.now },
    genres: {
        type: [String],
        default: [],
        es_indexed: true,
        es_analyzer: 'autocomplete',
        es_search_analyzer: 'autocomplete_search',
    },
});

const host = process.env.ELASTIC_URL || ELASTIC_URL;

AudioSchema.plugin(mongoosastic, {
    hosts: [`${host}`],
});

export { AudioSchema };
