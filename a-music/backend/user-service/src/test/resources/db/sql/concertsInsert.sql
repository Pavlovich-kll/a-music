truncate concerts cascade;

insert into concerts (id, title, performer, age_restriction, concert_date, total_number_of_tickets,
                      base_price, city_id)
values (1, 'Concert', 'concert', 18, '2020-07-29', 5, 1800, 1);