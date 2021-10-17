create table categories(
    id serial8 not null primary key,
    name varchar(255) not null
);

INSERT INTO categories( name)
VALUES
       ('Фінанси'),
       ('Політика'),
       ('Україна'),
       ('Спорт'),
       ('Шоу-бізнес'),
       ('Музика'),
       ('Мультиплікація/Аніме'),
       ('Новини проекту'),
       ('Розслідування'),
       ('Блоги'),
       ('Технології'),
       ('Фанфіки'),
       ('Твори'),
       ('Погода'),
       ('Наука');

ALTER TABLE tags rename column value to tags;