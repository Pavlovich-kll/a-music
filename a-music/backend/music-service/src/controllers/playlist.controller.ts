// Parent
import { BasicController } from './basic.controller';
// Validation
import { body, param } from 'express-validator';
import validate from '../middlewares/validate';
// Dependencies
import { PlaylistService } from '../services/playlist.service';
import { FileStorage } from '../helpers/file-storage';

export class PlaylistController extends BasicController {
    private service: PlaylistService;
    private fileStorage: FileStorage;

    constructor() {
        super();
        this.service = new PlaylistService();
        this.fileStorage = FileStorage.getInstance();
        this.initRouter();
    }

    protected initRouter() {
        this.router.get('/', this.service.get_all_playlists);

        this.router.post(
            '/',
            [
                this.fileStorage.upload.single('pic'),
                this.addNonEmptyField('title', body),
                this.addNonEmptyField('description', body),
                this.addNonEmptyField('tracks', body),
                this.sanitizeFieldToArray('tracks', body),
                this.checkSinglefileFieldMimetype(/^image\/\w+$/).withMessage(
                    'Picture file is not provided or has a wrong mime type (should be image)'
                ),
            ],
            validate,
            this.service.create_new_playlist
        );

        this.router.put(
            '/',
            [this.addIdField(body).exists().withMessage('ID is required')],
            validate,
            this.service.set_like
        );

        this.router.put(
            '/new',
            [
                this.addIdField(body).exists().withMessage('ID is required'),
                this.addNonEmptyField('tracks', body),
                this.sanitizeFieldToArray('tracks', body),
            ],
            validate,
            this.service.set_new_tracks
        );

        this.router.get(
            '/:id',
            [this.addIdField(param)],
            validate,
            this.service.get_playlist
        );
    }
}
