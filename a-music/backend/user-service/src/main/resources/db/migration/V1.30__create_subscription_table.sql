CREATE TABLE subscriptions
(
    id serial NOT NULL primary key,
    name text NOT NULL,
    user_capacity integer NOT NULL,
    price integer NOT NULL,
    description text NOT NULL,
    trial_period integer NOT NULL
);