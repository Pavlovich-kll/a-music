CREATE TABLE user_subscription_email_code
(
    id serial NOT NULL,
    user_subscription_id integer NOT NULL,
    invited_user_id integer NOT NULL,
    code text NOT NULL
);