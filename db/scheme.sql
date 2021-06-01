CREATE TABLE users (
     id serial primary key,
     name text,
     email VARCHAR (50) UNIQUE,
     password TEXT
);

CREATE TABLE items (
    id          serial primary key,
    description text,
    created     timestamp,
    done        boolean,
    user_id int not null references users(id)
);
