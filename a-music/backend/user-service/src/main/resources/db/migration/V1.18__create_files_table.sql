CREATE TYPE file_extension as ENUM ('MP3','JPEG', 'PNG');

CREATE TABLE files
(
    id             serial         NOT NULL,
    data           bytea          NOT NULL,
    file_extension file_extension NOT NULL,
    PRIMARY KEY (id)
);