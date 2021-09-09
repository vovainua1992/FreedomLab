create table avatars(
  id int8 not null primary key,
  origin_id int8 not null references images,
  small_id int8 references images,
  small_size int4,
  small_pos_x int4,
  small_pos_y int4
);

ALTER TABLE usr
    DROP COLUMN image_id;

ALTER TABLE usr
    ADD COLUMN avatar_id int8 references avatars;