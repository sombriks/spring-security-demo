-- liquibase formatted sql

-- changeset sombriks:2025/11/16/initial-data-1
insert into my_logins (email, password)
values ('bobby@tables.net', '$2a$10$9yFjnxPlkamR1uaWp5iVzeKaKqtZD5Z12sXn7F1xAerWYBLm1cBky');

-- changeset sombriks:2025/11/16/initial-data-2
insert into my_logins (email, password, perms)
values ('root@root.com', '$2a$10$V8jn68KexuAIHpYDypzcQOXJQLF9Wzjqd6EbQ0DCu44ytN3hc8DSu','USER;ADM');
