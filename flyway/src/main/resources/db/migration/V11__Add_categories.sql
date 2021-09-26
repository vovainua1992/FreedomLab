create table categories(
    id int8 not null primary key,
    name varchar(255) not null
);

INSERT INTO categories(id, name)
VALUES
       (0,'Фінанси'),
       (1,'Політика'),
       (2,'Україна'),
       (3,'Спорт'),
       (4,'Шоу-бізнес'),
       (5,'Музика'),
       (6,'Мультиплікація/Аніме'),
       (7,'Новини проекту'),
       (8,'Розслідування'),
       (9,'Блоги'),
       (10,'Технології'),
       (11,'Фанфіки'),
       (12,'Твори'),
       (13,'Погода'),
       (14,'Наука');

ALTER TABLE tags rename column value to tags;