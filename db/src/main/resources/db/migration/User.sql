
CREATE TABLE users
(
    id bigserial primary key,
    username varchar(64) unique not null,
    password varchar(64) not null,
    nickname varchar(64),
    first_Name varchar(64),
    last_Name varchar(64),
    telephone varchar(64),
    registration_date timestamptz not null,
    create_token_date_time timestamptz,
    status boolean not null,
    visibility boolean not null,
    role varchar(64) not null
);
