CREATE TABLE concerts
(
    id serial NOT NULL,
    title character varying NOT NULL,
    performer character varying NOT NULL,
    age_restriction integer NOT NULL,
    concert_date date,
    total_number_of_tickets integer,
    base_price integer,
    PRIMARY KEY (id),
    CONSTRAINT age_restriction_greater_than_zero CHECK (age_restriction > 0) NOT VALID,
    CONSTRAINT total_number_of_tickets_greater_than_zero CHECK (total_number_of_tickets > 0) NOT VALID,
    CONSTRAINT base_price_greater_than_zero CHECK (base_price > 0) NOT VALID
);