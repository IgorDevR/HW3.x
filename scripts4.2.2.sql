
CREATE TABLE Car
(
    id    integer NOT NULL PRIMARY KEY,
    brand varchar NOT NULL,
    model varchar NOT NULL,
    coast DECIMAL NOT NULL
);

CREATE TABLE Human
(
    id       integer NOT NULL PRIMARY KEY,
    name     varchar NOT NULL,
    age      integer NOT NULL,
    isDriver boolean not null,
    car_id INTEGER REFERENCES Car
);


