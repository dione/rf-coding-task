create table db_link(
    id bigserial primary key,
    slug varchar(255) unique not null,
    original_url varchar(2048) not null
);

create sequence slug_seq start 1;
