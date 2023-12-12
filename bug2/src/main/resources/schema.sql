CREATE TABLE IF NOT EXISTS Parent (
    id INTEGER IDENTITY PRIMARY KEY,
    name VARCHAR(100),
    nested_child_id NUMERIC,
    nested_type VARCHAR(100)
);


CREATE TABLE IF NOT EXISTS Child (
                                   id INTEGER IDENTITY PRIMARY KEY,
                                   name VARCHAR(100)
);

