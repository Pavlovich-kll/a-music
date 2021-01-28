truncate users cascade;

insert into users (id, username, password, first_name, last_name, email, phone, role, date_of_birth,
                                avatar, social, city_id)
VALUES (1, 'User', 'password', 'someFirstName', 'someLastName', 'user@mail.com', 89211111111, 'ROLE_USER', '1995-5-5',
        null, null, 1),
       (2, 'User2', 'password2', 'someFirstName2', 'someLastName2', 'user2@mail.com', 89212222222, 'ROLE_USER', '1995-6-6',
        null, null, 1);