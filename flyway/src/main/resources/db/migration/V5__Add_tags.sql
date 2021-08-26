create table tags
(
    publish_id int8 not null references publishes,
    tags        varchar(255)
);
