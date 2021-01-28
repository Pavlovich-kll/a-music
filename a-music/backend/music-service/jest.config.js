module.exports = {
    roots: ['./src'],
    testMatch: [
        '**/__tests__/**/*.+(ts|tsx|js)',
        '**/?(*.)+(spec|test).+(ts|tsx|js)',
    ],
    coveragePathIgnorePatterns: [
        '/node_modules/',
        '__fixtures__',
        '__tests__',
        'constants',
        'controllers',
        'DB',
        'helpers',
        'models',
        'swagger',
        'views',
    ],
    preset: 'ts-jest',
    testEnvironment: 'node'
};
