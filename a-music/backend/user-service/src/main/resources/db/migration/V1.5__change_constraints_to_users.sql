alter table if exists users
alter column phone drop not null;

alter table users
drop constraint  if exists check_phone_or_email_not_null;

alter table users
add constraint check_phone_or_email_not_null check ( (phone is not null and phone != '') or (email is not null and email !=''));