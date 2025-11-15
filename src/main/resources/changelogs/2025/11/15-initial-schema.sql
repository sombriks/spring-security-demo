-- liquibase formatted sql

-- changeset sombriks:2025/11/15/initial-schema-1

create table my_logins
(
    id       integer      not null primary key auto_increment,
    email    varchar(255) not null unique,
    password varchar(255) not null,
    perms    varchar(255) not null default 'USER;NORMAL'
);
