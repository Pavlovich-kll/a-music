CREATE TABLE playlists_audios
(
    playlist_id integer NOT NULL,
    audio_id    integer NOT NULL,
    PRIMARY KEY (playlist_id, audio_id),
    CONSTRAINT "FK_playlists" FOREIGN KEY (playlist_id)
        REFERENCES playlists (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT "FK_audios" FOREIGN KEY (audio_id)
        REFERENCES audios (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);