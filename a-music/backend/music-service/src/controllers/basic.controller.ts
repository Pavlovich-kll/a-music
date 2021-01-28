import { Router } from 'express';
import { ValidationChain, check } from 'express-validator';

interface LocationFn {
    (fields: string | string[]): ValidationChain;
}

export abstract class BasicController {
    public router: Router;

    constructor() {
        this.router = Router();
    }

    protected abstract initRouter(): void;

    protected addIdField = (locationFn: LocationFn) => {
        return locationFn('id')
            .isLength({ min: 16 })
            .withMessage('Provided ID is too short');
    };

    protected addNonEmptyField = (
        fieldName: string | string[],
        locationFn: LocationFn
    ) => {
        return locationFn(fieldName)
            .exists()
            .withMessage(`Please pass ${fieldName} field`)
            .not()
            .isEmpty()
            .withMessage(`Provide ${fieldName} field with some data`);
    };

    protected addTypeField = (locationFn: LocationFn) => {
        return locationFn('type')
            .isIn(['music', 'book', 'podcast'])
            .withMessage('Type must be music, book or podcast');
    };

    protected sanitizeFieldToArray = (
        fieldName: string,
        locationFn: LocationFn
    ) => {
        return locationFn(fieldName).customSanitizer((value) => {
            if (!value) {
                return value;
            }
            return Array.isArray(value) ? value : value.split(',');
        });
    };

    protected checkSinglefileFieldMimetype = (filetype: RegExp) => {
        return check('file').custom((value, { req: { file } }) => {
            if (!file) {
                return false;
            }
            const { mimetype } = file;
            const isValidMemetype = filetype.test(mimetype);
            return isValidMemetype;
        });
    };

    protected checkMultifileFieldMimetype = (
        fieldName: string,
        filetype: RegExp
    ) => {
        return check(`files.${fieldName}`).custom(
            (value, { req: { files } }) => {
                if (!files[fieldName]) {
                    return false;
                }

                const {
                    [fieldName]: [{ mimetype }],
                } = files;
                const isValidMemetype = filetype.test(mimetype);

                return isValidMemetype;
            }
        );
    };
}
