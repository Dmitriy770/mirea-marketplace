create table cart
(
    username  text,
    product_type   text,
    product_id     int,
    product_amount int,

    primary key (username, product_type, product_id)
);

create table storage
(
    product_type text,
    product_id   int,
    amount       int,

    primary key (product_type, product_id)
);