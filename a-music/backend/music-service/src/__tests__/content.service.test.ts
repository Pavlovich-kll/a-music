import { ContentService } from '../services/content.service';
import { ALLOWED_AUDIO_TYPES } from '../constants/allowed_audio_types';

// Data for testing
let req: any;
let res: any;
let allowedTypes;

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

    allowedTypes = ALLOWED_AUDIO_TYPES;
    jest.clearAllMocks();
});

describe('Testing content service', () => {
    describe('Valid flow', () => {
        test('Testing get_types method', () => {
            const contentService = new ContentService();
            contentService.get_types(req, res);

            expect(res.statusCode).toEqual(200);
            expect(res.body).toBe(allowedTypes);
        });
    });
    describe('Getting errors', () => {});
});
