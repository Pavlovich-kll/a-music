CREATE TABLE IF NOT EXISTS notification_metadata
(
    id serial NOT NULL primary key,
    name varchar,
    description varchar,
    secondary_description varchar
);

INSERT INTO notification_metadata (name, description, secondary_description)
values ('Recommendations', 'Музыкальные рекомендации', 'Последние открытия от редакционного коллектива');

INSERT INTO notification_metadata (name, description, secondary_description)
values ('Musicians', 'Мои любимые исполнители', 'Свежие треки и новости об исполнителях, которые мне нравятся или могут мне понравиться');

INSERT INTO notification_metadata (name, description, secondary_description)
values ('Playlists', 'Мои любимые плейлисты', 'Свежие треки и обновления плейлистов, которые мне нравятся или могут мне понравиться');

INSERT INTO notification_metadata (name, description, secondary_description)
values ('Social updates', 'Социальные обновления', 'Обновления у пользователей, которых я читаю');

INSERT INTO notification_metadata (name, description, secondary_description)
values ('Music habits', 'Музыкальные привычки', 'Включая статистику прослушивания, интерактивные возможности и эксклюзивные проекты');

INSERT INTO notification_metadata (name, description, secondary_description)
values ('Podcasts', 'Рекомендованные подкасты', 'Последние открытия и рекомендации, подобранные специально для меня');

INSERT INTO notification_metadata (name, description, secondary_description)
values ('News and promotions', 'Новости, предложения и акции', 'Первым узнавать о новостях, получать полезные советы и предложения');

INSERT INTO notification_metadata (name, description, secondary_description)
values ('Tips for service', 'Советы и рекомендации по продуктам', 'Узнать больше о приложениях, возможностях и бета-сообществе');

INSERT INTO notification_metadata (name, description, secondary_description)
values ('Contests and concerts', 'Конкурсы и концерты', 'Выигрывать билеты и узнавать больше о событиях');

INSERT INTO notification_metadata (name, description, secondary_description)
values ('Feedback', 'Обратная связь', 'Отправить отзыв для улучшения возможностей');

INSERT INTO notification_metadata (name, description, secondary_description)
values ('Partner news', 'Новости наших партнеров', 'Получать новости от партнерских музыкальных лейблов, провайдеров и продавцов');
