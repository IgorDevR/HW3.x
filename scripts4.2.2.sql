CREATE TABLE Human
(
    id       integer NOT NULL PRIMARY KEY,
    name     varchar NOT NULL,
    age      integer NOT NULL,
    isDriver boolean not null
);

CREATE TABLE Car
(
    id       integer NOT NULL PRIMARY KEY,
    brand    varchar NOT NULL,
    model    varchar NOT NULL,
    coast    DECIMAL NOT NULL,
    human_id INTEGER,
    FOREIGN KEY (human_id) REFERENCES Human (id)
);