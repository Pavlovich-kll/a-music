import { verify } from 'jsonwebtoken';
import { Request, Response, NextFunction } from 'express';
import { config } from 'dotenv';

config();

export default (req: Request, res: Response, next: NextFunction) => {
    const authHeader = req.get('Authorization');
    if (!authHeader) {
        return res.status(401).json({
            message: 'Provide request with Authorization header!',
        });
    }

    const [bearer, token] = authHeader.split(' '); // Bearer + JWT
    const secret = Buffer.from(process.env.TOKEN_SECRET, 'base64').toString();
    let decodedToken;
    try {
        decodedToken = verify(token, secret);
    } catch (error) {
        return res.status(500).json({ message: error.message });
    }
    if (!decodedToken) {
        return res.status(401).json({ message: 'Invalid token' });
    }

    next();
};
