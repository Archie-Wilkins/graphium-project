-- ------------------------------------
-- Database 'graphium'
-- ------------------------------------
use `graphium`;

SET FOREIGN_KEY_CHECKS = 0;
truncate table `users`;
truncate table `authorities`;
truncate table `documents`;
SET FOREIGN_KEY_CHECKS = 1;

-- ------------------------------------
-- Users
-- ------------------------------------

-- both the passwords decrypt to just be password (using bcrypt)
-- If usernames are changed, make sure to refactor all tests using them or they will error
INSERT INTO `users` (`username`, `password`, `enabled`) VALUES ('testUser','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1);
INSERT INTO `users` (`username`, `password`, `enabled`) VALUES ('testUser2','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1);
INSERT INTO `users` (`username`, `password`, `enabled`) VALUES ('testOrgAdmin','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1);
INSERT INTO `users` (`username`, `password`, `enabled`) VALUES ('testSystemAdmin','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1);

INSERT INTO `authorities` (`username`, `authority`) VALUES ('testUser', 'researcher');
INSERT INTO `authorities` (`username`, `authority`) VALUES ('testUser2', 'researcher');
INSERT INTO `authorities` (`username`, `authority`) VALUES ('testOrgAdmin', 'orgAdmin');
INSERT INTO `authorities` (`username`, `authority`) VALUES ('testSystemAdmin', 'systemAdmin');

