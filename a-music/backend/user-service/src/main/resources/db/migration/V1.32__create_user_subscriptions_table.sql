CREATE TABLE user_subscriptions
(
    id serial NOT NULL primary key,
    actual_price integer NOT NULL,
    purchase_date date NOT NULL,
    is_valid boolean NOT NULL,
    subscription_id integer NOT NULL,
    host_user_id integer NOT NULL,
    CONSTRAINT "FK_subscription" FOREIGN KEY (subscription_id)
        REFERENCES subscriptions (id)
        ON UPDATE NO ACTION
        ON DELETE cascade,
    CONSTRAINT "FK_host_user" FOREIGN KEY (host_user_id)
        REFERENCES users (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);