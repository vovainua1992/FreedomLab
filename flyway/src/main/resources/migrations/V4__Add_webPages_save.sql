create table web_news (
                     id int8 not null,
                     active boolean not null,
                     title_images varchar(255) default 'static/icons/image.svg',
                     title_names varchar(255) not null,
                     text_html varchar(4096) not null,
                     author varchar(255) not null,
                     author_id int8 not null references usr,
                     primary key (id)
);
