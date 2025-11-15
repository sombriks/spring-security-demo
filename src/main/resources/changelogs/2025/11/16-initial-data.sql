-- liquibase formatted sql

-- changeset sombriks:2025/11/16/initial-data-1
insert into my_logins (email, password)
values ('bobby@tables.net', 'todo');

-- changeset sombriks:2025/11/16/initial-data-2
insert into my_logins (email, password, perms)
values ('root@root.com', 'todo','USER;ADM');
