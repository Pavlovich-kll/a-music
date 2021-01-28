// Dependencies
import { BackgroundService } from '../services/background.service';

export class BackgroundController {
    private backgroundService: BackgroundService;

    constructor() {
        this.backgroundService = new BackgroundService();
    }
}
