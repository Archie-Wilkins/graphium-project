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
VALUES ('testUser2','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 1, 'John','Smith','John@Cardiff.ac.uk',NOW());

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

-- --------------------------
-- Access_Audit_Actions
-- --------------------------
INSERT INTO `access_audit_actions` (`id`, `action_description`)
VALUES (null, 'User logged In - Success');

INSERT INTO `access_audit_actions` (`id`, `action_description`)
VALUES (null, 'User logged In - Failed');

INSERT INTO `access_audit_actions` (`id`, `action_description`)
VALUES (null, 'All system documents accessed - Success');

INSERT INTO `access_audit_actions` (`id`, `action_description`)
VALUES (null, 'All system documents accessed - Failed');

INSERT INTO `access_audit_actions` (`id`, `action_description`)
VALUES (null, 'Organisation documents accessed - Success');

INSERT INTO `access_audit_actions` (`id`, `action_description`)
VALUES (null, 'Organisation documents accessed - Failed');

INSERT INTO `access_audit_actions` (`id`, `action_description`)
VALUES (null, 'Document downloaded');

INSERT INTO `access_audit_actions` (`id`, `action_description`)
VALUES (null, 'Document deleted');

INSERT INTO `access_audit_actions` (`id`, `action_description`)
VALUES (null, 'Document viewed');

INSERT INTO `access_audit_actions` (`id`, `action_description`)
VALUES (null, 'New organisation created');

INSERT INTO `access_audit_actions` (`id`, `action_description`)
VALUES (null, 'New user created');

INSERT INTO `access_audit_actions` (`id`, `action_description`)
VALUES (null, 'File uploaded');

-- --------------------------
-- Access_Audit_Reports
-- --------------------------
INSERT INTO `access_audit_reports` (`id`, `fk_username`, `fk_document_id`, `fk_action_id`, `action_date`)
VALUES (null, 'testUser', null, 1, NOW());

INSERT INTO `access_audit_reports` (`id`, `fk_username`, `fk_document_id`, `fk_action_id`, `action_date`)
VALUES (null, 'testUser', null, 6, NOW());

INSERT INTO `access_audit_reports` (`id`, `fk_username`, `fk_document_id`, `fk_action_id`, `action_date`)
VALUES (null, 'testUser', 1, 7, NOW());


