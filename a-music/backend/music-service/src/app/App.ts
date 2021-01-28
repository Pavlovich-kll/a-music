import express, { Response, Request } from 'express';
import path from 'path';
import bodyParser from 'body-parser';
import cors from 'cors';
import cookieParser from 'cookie-parser';
import swaggerUI from 'swagger-ui-express';
import methodOverride from 'method-override';
import { headersMiddleware } from '../helpers/headers';
import { AudioController } from '../controllers/audio.controller';
import { ContentController } from '../controllers/content.controller';
import { PlaylistController } from '../controllers/playlist.controller';
import { BackgroundController } from '../controllers/background.controller';
import isAuth from '../middlewares/is-auth';
import { Mongoose } from '../DB/db';
import swaggerDoc from '../swagger';

class App {
    public app: any;
    private fileController: AudioController;
    private contentController: ContentController;
    private playlistController: PlaylistController;
    public db: Mongoose;

    constructor() {
        this.app = express();
        new Mongoose();
        new BackgroundController();
        this.fileController = new AudioController();
        this.contentController = new ContentController();
        this.playlistController = new PlaylistController();
        this.set_config();
    }

    private set_config() {
        this.app.set('view engine', 'ejs');
        this.app.use(express.static(path.join(__dirname, '../static')));
        this.app.set('views', path.join(__dirname, '../views'));
        this.app.use(bodyParser.json({ limit: '50mb' }));
        this.app.use(methodOverride('_method'));
        this.app.use(bodyParser.urlencoded({ limit: '50mb', extended: true }));
        this.app.use(cors());
        this.app.use(cookieParser());
        this.app.use(
            '/music-service/doc',
            swaggerUI.serve,
            swaggerUI.setup(swaggerDoc)
        );
        this.app.use(headersMiddleware);

        this.app.use('/music-service/content', this.contentController.router);
        this.app.use('/music-service', isAuth, this.fileController.router);
        this.app.use(
            '/music-service/playlist',
            isAuth,
            this.playlistController.router
        );

        this.app.get('/music-service', (req: Request, res: Response) =>
            res.render('index')
        );
    }
}

export default new App().app;
