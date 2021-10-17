-- auto-generated definition
create sequence hibernate_sequence;

alter sequence hibernate_sequence owner to postgres;

create table usr
(
    id              serial8          not null,
    activation_code varchar(255),
    active          boolean      not null,
    avatare         varchar(255) default 'static/icons/image.svg',
    email           varchar(255) not null,
    password        varchar(255) not null,
    username        varchar(255) not null,
    primary key (id)
);


create table user_role
(
    user_id int8 not null references usr,
    roles   varchar(255)
);


create table publishes
(
    id           serial8          not null,
    active       boolean       not null,
    title_images varchar(255) default 'static/icons/image.svg',
    title_names  varchar(255)  not null,
    text_html    varchar(4096) not null,
    author_id    int8          not null references usr,
    primary key (id)
);

