
create table messagess (
                         id int8 not null,
                         image varchar(255),
                         tag varchar(255),
                         text varchar(2048) not null,
                         user_id int8,
                         primary key (id)
);
