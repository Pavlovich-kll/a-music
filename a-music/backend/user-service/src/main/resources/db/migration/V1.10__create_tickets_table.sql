CREATE TABLE tickets
(
    id serial NOT NULL,
    concert_id serial NOT NULL,
    user_id serial NOT NULL,
    date_of_payment date NOT NULL,
    price integer NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT "FK_concerts" FOREIGN KEY (concert_id)
        REFERENCES concerts (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT "FK_users" FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT price_greater_than_zero CHECK (price > 0) NOT VALID
);