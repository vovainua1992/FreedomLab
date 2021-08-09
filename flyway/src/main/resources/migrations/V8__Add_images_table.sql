create table images(
    id          int8          not null,
    name        varchar(255),
    url         varchar(255),
    description varchar(2048) not null,
    upload_date_time timestamp,
    delete_date_time timestamp,
    number_of_uses int4,
    primary key (id)
);