import SwaggerJSDoc from 'swagger-jsdoc';

const options = {
    definition: {
        openapi: '3.0.0',
        info: {
            title: 'A-Music Music Service REST API',
            version: '1.0.0',
        },
        basePath: '/',
        components: {
            securitySchemes: {
                bearerAuth: {
                    type: 'http',
                    scheme: 'bearer',
                    bearerFormat: 'JWT',
                },
            },
        },
        security: [{ bearerAuth: [] }],
    },

    explorer: true,
    apis: ['**/*.yml'],
};

export default SwaggerJSDoc(options);
