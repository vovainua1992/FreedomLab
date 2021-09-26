ALTER TABLE publishes
ADD COLUMN category varchar(255);
ALTER TABLE tags rename column tags to value;

