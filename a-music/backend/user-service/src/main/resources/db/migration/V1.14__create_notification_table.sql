CREATE TABLE IF NOT EXISTS notification
(
    id serial NOT NULL primary key,
    user_id bigint NOT NULL,
    notification_metadata_id bigint NOT NULL,
    mobile_notification boolean DEFAULT true,
    mail_notification boolean DEFAULT true,
    text_notification boolean DEFAULT true,
    CONSTRAINT metadata_id_fk FOREIGN KEY (notification_metadata_id)
        REFERENCES notification_metadata (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);
