ALTER TABLE images
    DROP COLUMN number_of_uses;

create table avatars
(
    id             int8 not null,
    image_id       int8 not null references images,
    small_image_id int8 not null references images,
    primary key (id)
);

create table galleries
(
    id       int8 not null,
    owner_id int8 not null references usr,
    type     varchar(255),
    name     varchar(255),
    primary key (id)
);

create table gallery_images
(
    gallery_id int8 not null references galleries,
    image_id   int8 not null references images,
    primary key (gallery_id, image_id)
);

ALTER TABLE usr
    DROP COLUMN avatare;

ALTER TABLE usr
    ADD COLUMN avatar_id int8 references avatars;




