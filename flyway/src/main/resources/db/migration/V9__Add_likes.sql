create table publishes_likes
(
    publish_id        int8 not null references publishes,
    user_id int8 not null references usr,
    primary key (user_id, publish_id)
);