ALTER TABLE publishes DROP COLUMN category;
ALTER TABLE publishes ADD COLUMN category_id int8 references categories;