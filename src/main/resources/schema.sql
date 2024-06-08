create table if not exists spacecraft (
    id bigint auto_increment primary key,
    spacecraft_name varchar(150) not null,
    movie varchar(200) not null,
    pilot varchar(100)
);