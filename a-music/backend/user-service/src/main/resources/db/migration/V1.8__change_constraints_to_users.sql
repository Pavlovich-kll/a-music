alter table if exists users
    alter column password drop not null;

alter table users
    drop constraint if exists check_social_or_password_not_null;

alter table users
    add constraint check_social_or_password_not_null check ( (password is not null) or (social is not null) );


alter table users
    drop constraint if exists check_phone_or_email_not_null,
    drop constraint if exists check_phone_or_email_not_null_or_username_and_social_not_null,
    add constraint check_phone_or_email_not_null_or_username_and_social_not_null check (
            ((phone is not null and phone != '') or (email is not null and email != ''))
            or (username is not null and social is not null));

