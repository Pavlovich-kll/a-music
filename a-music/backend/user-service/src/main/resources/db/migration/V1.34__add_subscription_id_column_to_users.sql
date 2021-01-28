alter table if exists users
    add current_user_subscription_id integer,
    add FOREIGN KEY (current_user_subscription_id) REFERENCES user_subscriptions (id);