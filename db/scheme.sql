CREATE TABLE items (
    id          serial primary key,
    description text,
    created     timestamp,
    done        boolean
);
