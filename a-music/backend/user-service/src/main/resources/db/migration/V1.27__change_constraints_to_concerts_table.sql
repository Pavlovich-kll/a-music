alter table if exists concerts
    add city_id integer;

alter table if exists concerts
    ADD CONSTRAINT "FK_cities"
        FOREIGN KEY (city_id)
            REFERENCES cities (id)
            ON UPDATE CASCADE
            ON DELETE CASCADE
;
