create table book
(
    id              serial primary key,
    title           text,
    author          text,
    price           double precision,
    seller_username text,
    product_type    text
);

create table telephone
(
    id               serial primary key,
    producer         text,
    battery_capacity int,
    seller_username  text,
    product_type     text,
    price            double precision,
    name             text
);

create table washing_machine
(
    id              serial primary key,
    producer        text,
    tank_volume     double precision,
    seller_username text,
    product_type    text,
    price           double precision,
    name            text
);