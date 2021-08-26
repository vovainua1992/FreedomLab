create table user_subscriptions
(
    channel_id    int8 not null references usr,
    subscriber_id int8 not null references usr,
    primary key (channel_id, subscriber_id)
);

create table images
(
    id               int8          not null,
    name             varchar(255),
    url              varchar(255),
    description      varchar(2048) not null,
    upload_date_time timestamp,
    delete_date_time timestamp,
    number_of_uses   int4,
    primary key (id)
);

ALTER TABLE publishes
    DROP COLUMN title_images;

ALTER TABLE publishes
    ADD COLUMN image_id int8 references images;

ALTER TABLE publishes
    add COLUMN type varchar(255);

ALTER TABLE usr
    DROP COLUMN avatare;

ALTER TABLE usr
    ADD COLUMN image_id int8 references images;

