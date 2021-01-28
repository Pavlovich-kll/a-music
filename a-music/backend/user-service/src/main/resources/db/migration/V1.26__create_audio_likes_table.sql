CREATE TABLE audios_likes
(
    audio_id integer NOT NULL,
    user_id  integer NOT NULL,
    PRIMARY KEY (audio_id, user_id),
    CONSTRAINT "FK_audios" FOREIGN KEY (audio_id)
        REFERENCES audios (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT "FK_user" FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);