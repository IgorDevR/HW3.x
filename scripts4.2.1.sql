
ALTER TABLE student
    ADD CONSTRAINT age_constraint CHECK (age > 0);

ALTER TABLE student
   ALTER Column name SET NOT NULL, ADD  constraint unique_name unique (name);

ALTER TABLE faculty
    ADD CONSTRAINT nameAndColorFaculty_unique UNIQUE (name, color);

ALTER TABLE student
    ALTER COLUMN age SET DEFAULT 20;