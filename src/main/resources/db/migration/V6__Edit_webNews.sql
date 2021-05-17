ALTER TABLE web_news
    RENAME TO publishes;

ALTER TABLE publishes
    ADD COLUMN color_text varchar(255);