CREATE TABLE playlists
(
    id          serial  NOT NULL,
    title       varchar NOT NULL,
    description varchar NOT NULL,
    cover_id    integer,
    PRIMARY KEY (id),
    CONSTRAINT "FK_files" FOREIGN KEY (cover_id)
        REFERENCES files (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE SET NULL
)