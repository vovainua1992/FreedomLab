ALTER TABLE publishes
    RENAME COLUMN date_publication TO date_create;
ALTER TABLE publishes
    ADD COLUMN date_publication timestamp;