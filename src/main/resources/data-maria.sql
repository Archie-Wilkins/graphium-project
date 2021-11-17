use graphium_schema;

truncate table users;
truncate table authorities;

-- -----------------------
-- Users
-- -----------------------
INSERT INTO users (`username`,`password`, `enabled`)
VALUES ('user','password', 1);
INSERT INTO users (`username`,`password`,`enabled`)
VALUES ('admin','password', 1);

INSERT INTO authorities(`username`,`authority`)
VALUES ('user','user');
INSERT INTO authorities(`username`,`authority`)
VALUES ('admin','admin');


create table users
(
	username int null,
	password int null,
	enabled int null
);

create table authorities
(
	username int null,
	authority int null
);

