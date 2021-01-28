// Parent
import { BasicController } from './basic.controller';
// Validation
import { body, query, param } from 'express-validator';
import validate from '../middlewares/validate';
// Dependencies
import { AudioService } from '../services/audio.service';
import { FileStorage } from '../helpers/file-storage';

export class AudioController extends BasicController {
    private audioService: AudioService;
    private fileStorage: FileStorage;

    constructor() {
        super();
        this.audioService = new AudioService();
        this.fileStorage = FileStorage.getInstance();
        this.initRouter();
    }

    public initRouter() {
        this.router.get('/file', this.audioService.get_all_audio);

        this.router.get(
            '/favorites',
            [
                query('track_ids')
                    .exists()
                    .withMessage('Please pass track_ids query')
                    .isArray()
                    .withMessage('Provided value is not array')
                    .not()
                    .isEmpty()
                    .withMessage('There are no favorite tracks yet'),
            ],
            validate,
            this.audioService.get_tracks_by_ids
        );

        this.router.get(
            '/:type/file',
            [this.addTypeField(param)],
            validate,
            this.audioService.get_by_type
        );

        this.router.post(
            '/file',
            [
                this.fileStorage.upload.fields([
                    { name: 'track', maxCount: 1 },
                    { name: 'cover', maxCount: 1 },
                ]),
                this.addTypeField(body)
                    .exists()
                    .withMessage('Please pass type field'),
                this.addNonEmptyField(
                    'author',
                    body
                ),
                this.addNonEmptyField(
                    'title',
                    body
                ),
                this.addNonEmptyField(
                    'album',
                    body
                ),
                this.addNonEmptyField(
                    'genres',
                    body
                ),
                this.sanitizeFieldToArray('genres', body),
                this.checkMultifileFieldMimetype(
                    'track',
                    /^audio\/\w+$/
                ).withMessage(
                    `Track file is not provided or has a wrong mime type (must be audio)`
                ),
                this.checkMultifileFieldMimetype(
                    'cover',
                    /^image\/\w+$/
                ).withMessage(
                    `Сover file is not provided or has a wrong mime type (must be image)`
                ),
            ],
            validate,
            this.audioService.upload_audio
        );

        this.router.put(
            '/file',
            [this.addNonEmptyField('track_id', body)],
            validate,
            this.audioService.set_like_to_audio_by_id
        );

        this.router.get(
            '/search',
            [this.addNonEmptyField('searching', query)],
            validate,
            this.audioService.search
        );
    }
}
