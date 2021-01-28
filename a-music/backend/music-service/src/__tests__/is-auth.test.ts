import isAuth from '../middlewares/is-auth';
import { when } from 'jest-when';
import { verify } from 'jsonwebtoken';
jest.mock('jsonwebtoken');

// Data for testing
let req: any;
let res: any;
let next: any;

// Passing env values
process.env.TOKEN_SECRET = 'base64_encoded_secret';

// Mocking
when(verify as any)
    .calledWith('dummy', expect.anything())
    .mockImplementation(() => {
        throw new Error('Verification error');
    })
    .calledWith('JWT_without_payload', expect.anything())
    .mockReturnValue(undefined)
    .calledWith('JWT_with_payload', expect.anything())
    .mockReturnValue({ id: 1, username: 'User' });

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
    };

    req = {
        headers: {},
        get: jest.fn(() => req.headers.authorization),
    };

    next = jest.fn();

    jest.clearAllMocks();
});

describe('Testing authentication middleware', () => {
    test("When request doesn't have 'Authorization' header, give 401 error with details", () => {
        isAuth(req, res, next);

        expect(req.get).toBeCalledWith('Authorization');
        expect(req.get).toReturnWith(undefined);
        expect(res.statusCode).toEqual(401);
        expect(res.body.message).toEqual(
            'Provide request with Authorization header!'
        );
    });

    test('When verification fails, give 500 error with details', () => {
        req.headers.authorization = 'Bearer dummy';
        const stringSpy = jest.spyOn(global.String.prototype, 'split');

        isAuth(req, res, next);

        expect(req.get).toBeCalledWith('Authorization');
        expect(stringSpy).toReturnWith(['Bearer', 'dummy']);
        expect(verify).toHaveBeenCalledWith('dummy', expect.anything());

        expect(res.statusCode).toEqual(500);
        expect(res.body.message).toEqual('Verification error');
    });

    test('When verification returns undefined, give 401 error with details', () => {
        req.headers.authorization = 'Bearer JWT_without_payload';

        isAuth(req, res, next);

        expect(req.get).toBeCalledWith('Authorization');
        expect(verify).toHaveBeenCalledWith(
            'JWT_without_payload',
            expect.anything()
        );

        expect(res.statusCode).toEqual(401);
        expect(res.body.message).toEqual('Invalid token');
    });

    test('When verification passed, call next() for granting access to service', () => {
        req.headers.authorization = 'Bearer JWT_with_payload';

        isAuth(req, res, next);

        expect(req.get).toBeCalledWith('Authorization');
        expect(verify).toHaveBeenCalledWith(
            'JWT_with_payload',
            expect.anything()
        );

        expect(res.statusCode).toEqual(200);
        expect(next).toBeCalled();
    });
});
