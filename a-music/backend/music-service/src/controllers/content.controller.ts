// Parent
import { BasicController } from './basic.controller';
// Validation
import { param } from 'express-validator';
import validate from '../middlewares/validate';
import isAuth from '../middlewares/is-auth';
// Dependencies
import { ContentService } from '../services/content.service';

export class ContentController extends BasicController {
    private contentService: ContentService;

    constructor() {
        super();
        this.contentService = new ContentService();
        this.initRouter();
    }

    protected initRouter() {
        this.router.get('/types', isAuth, this.contentService.get_types);

        this.router.get('/genres', isAuth, this.contentService.get_genres);

        this.router.get(
            '/file/:id',
            [this.addIdField(param)],
            validate,
            this.contentService.get_file
        );
    }
}
