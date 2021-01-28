truncate notification cascade;

insert into notification (id, user_id, notification_metadata_id, mobile_notification, mail_notification,
                                       text_notification)
values (1, 1, 1, true, true, true),
       (2, 1, 2, true, true, true);