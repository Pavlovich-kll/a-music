create table if not exists email_codes
(
    id    serial       not null primary key,
    email varchar(254) not null,
    code  varchar unique
);
