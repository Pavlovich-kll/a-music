truncate notification_metadata cascade;

insert into notification_metadata (id, name, description, secondary_description)
values  (1, 'someNotification', 'someDescription', 'someSecondaryDescription'),
        (2, 'someNotification2', 'someDescription2', 'someSecondaryDescription2');