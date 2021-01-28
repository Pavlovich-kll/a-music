import validate from '../middlewares/validate';
import { validationResult } from 'express-validator';
jest.mock('express-validator');

let req: any;
let res: any;
let next: any;
let errors;
const error_in_body = {
    value: '',
    msg: 'Provide title field with some data',
    param: 'title',
    location: 'body',
};
const error_in_query = {
    value: '',
    msg: "Search param can't me empty",
    param: 'searching',
    location: 'query',
};

(validationResult as any).mockImplementation(() => errors);

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

    next = jest.fn();

    errors = {
        hasNoErrors: true,
        message: null,
        isEmpty: jest.fn(() => errors.hasNoErrors),
        array: jest.fn(() => errors.message),
    } as any;

    jest.clearAllMocks();
});

describe('Testing validating middleware', () => {
    test('When required body field is empty, give 422 error with details', () => {
        errors.hasNoErrors = false;
        errors.message = [error_in_body];

        validate(req, res, next);

        expect(validationResult).toHaveBeenCalledWith(req);
        expect(errors.isEmpty()).toBeFalsy();
        expect(res.statusCode).toEqual(422);
        expect(res.body.message).toEqual([error_in_body]);
    });
    test('When required query param is empty, give 422 error with details', () => {
        errors.hasNoErrors = false;
        errors.message = [error_in_query];

        validate(req, res, next);

        expect(validationResult).toHaveBeenCalledWith(req);
        expect(errors.isEmpty()).toBeFalsy();
        expect(res.statusCode).toEqual(422);
        expect(res.body.message).toEqual([error_in_query]);
    });
    test('When required body field and query param are empty, give 422 error with details', () => {
        errors.hasNoErrors = false;
        errors.message = [error_in_body, error_in_query];

        validate(req, res, next);

        expect(validationResult).toHaveBeenCalledWith(req);
        expect(errors.isEmpty()).toBeFalsy();
        expect(res.statusCode).toEqual(422);
        expect(res.body.message).toEqual([error_in_body, error_in_query]);
    });
    test('When validation has no errors, call next() for granting access to service', () => {
        validate(req, res, next);

        expect(validationResult).toHaveBeenCalledWith(req);
        expect(errors.isEmpty()).toBeTruthy();
        expect(next).toBeCalled();
    });
});
