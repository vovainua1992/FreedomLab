ALTER TABLE publishes
    DROP COLUMN title_images;

ALTER TABLE publishes
    ADD COLUMN image_id int8;

alter table if exists publishes
    add constraint publish_image_fk
        foreign key (image_id) references images;

ALTER TABLE message
    DROP COLUMN image;

ALTER TABLE message
    ADD COLUMN image_id int8;

alter table if exists message
    add constraint message_image_fk
        foreign key (image_id) references images;
