-- ------------------------------------
-- Database 'graphium'
-- ------------------------------------
use `graphium`;

SET FOREIGN_KEY_CHECKS = 0;
truncate table `users`;
truncate table `authorities`;
truncate table `documents`;
SET FOREIGN_KEY_CHECKS = 1;

-- -----------------------------------
-- Organisations 
-- -----------------------------------

INSERT INTO `organisations` (`id`,`name`,`email`) VALUES (null, 'Cardiff University', 'Cardiff@CardiffEmail.com');
INSERT INTO `organisations` (`id`,`name`,`email`) VALUES (null, 'Swansea University', 'Swansea@SwanseaEmail.com');

-- ------------------------------------
-- Users
-- ------------------------------------

-- both the passwords decrypt to just be password (using bcrypt)
-- If usernames are changed, make sure to refactor all tests using them or they will error
INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('testUser','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 1, 'John','Smith','John@Cardiff.ac.uk',NOW());

INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('testUser2','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 2, 'John','Smith','John@Cardiff.ac.uk',NOW());

INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('testOrgAdmin','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 1, 'John','Smith','John@Cardiff.ac.uk',NOW());

INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('testSystemAdmin','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 1, 'John','Smith','John@Cardiff.ac.uk',NOW());

-- ------------------------
-- Authorities
-- ------------------------

INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('testUser', 'researcher');
INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('testUser2', 'researcher');
INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('testOrgAdmin', 'orgAdmin');
INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('testSystemAdmin', 'systemAdmin');


-- --------------------------
-- Documents
-- ---------------------------

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `file_type`, `file_data`)
VALUES (null, 'testUser', 'An Excellent File', 'pdf', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `file_type`, `file_data`)
VALUES (null, 'testUser', 'A Fun PDF', 'pdf', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `file_type`, `file_data`)
VALUES (null, 'testUser', 'Word Test', 'docx', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `file_type`, `file_data`)
VALUES (null, 'testUser2', 'Different Org 1', 'pdf', null);



INSERT INTO `documents` (`id`, `fk_creator`, `title`, `file_type`, `file_data`)
VALUES (null, 'testUser2', 'Different Org 2', 'pdf', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `file_type`, `file_data`)
VALUES (null, 'testUser2', 'Different Org 3', 'pdf', null);
