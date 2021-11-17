use graphium_schema;

truncate table users;
truncate table authorities;

-- -----------------------
-- Users
-- -----------------------
INSERT INTO `users` (`username`,`password`, `enabled`)
VALUES ('user','password', 1);
INSERT INTO `users` (`username`,`password`,`enabled`)
VALUES ('admin','password', 1);

INSERT INTO authorities(`username`,`authority`)
VALUES ("user","researcher");
INSERT INTO authorities(`username`,`authority`)
VALUES ("admin","admin");


