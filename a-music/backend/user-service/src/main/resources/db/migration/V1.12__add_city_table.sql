create table if not exists cities
(
    id        serial not null primary key,
    city_name    text,
    country_id integer ,
    CONSTRAINT "FK_countries" FOREIGN KEY (country_id)
        REFERENCES countries (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
;
alter table if exists users
    add city_id integer;

alter table if exists users
    ADD CONSTRAINT "FK_cities"
    FOREIGN KEY (city_id)
    REFERENCES cities (id)
;
