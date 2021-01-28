CREATE TABLE playlists_likes
(
    playlist_id integer NOT NULL,
    user_id     integer NOT NULL,
    PRIMARY KEY (playlist_id, user_id),
    CONSTRAINT "FK_playlists" FOREIGN KEY (playlist_id)
        REFERENCES playlists (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT "FK_user" FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);