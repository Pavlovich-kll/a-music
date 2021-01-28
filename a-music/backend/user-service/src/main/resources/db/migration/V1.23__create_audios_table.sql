CREATE TYPE audio_type as ENUM ('MUSIC', 'BOOK', 'PODCAST');

CREATE TABLE audios
(
    id        serial     NOT NULL,
    title     varchar    NOT NULL,
    type      audio_type NOT NULL,
    author_id integer    NOT NULL,
    album_id  integer,
    genre_id  integer,
    file_id   integer    NOT NULL,
    cover_id  integer,
    PRIMARY KEY (id),
    CONSTRAINT "FK_authors" FOREIGN KEY (author_id)
        REFERENCES authors (id)
        ON UPDATE CASCADE
        ON DELETE SET NULL,
    CONSTRAINT "FK_albums" FOREIGN KEY (album_id)
        REFERENCES albums (id)
        ON UPDATE CASCADE
        ON DELETE SET NULL,
    CONSTRAINT "FK_genres" FOREIGN KEY (genre_id)
        REFERENCES genres (id)
        ON UPDATE CASCADE
        ON DELETE SET NULL,
    CONSTRAINT "FK_cover" FOREIGN KEY (cover_id)
        REFERENCES files (id)
        ON UPDATE CASCADE
        ON DELETE SET NULL,
    CONSTRAINT "FK_files" FOREIGN KEY (file_id)
        REFERENCES files (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);