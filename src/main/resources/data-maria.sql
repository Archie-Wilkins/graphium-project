-- ------------------------------------
-- Database 'graphium'
-- ------------------------------------
use `graphium`;

SET FOREIGN_KEY_CHECKS = 0;
truncate table `document_access_rights`;
truncate table `users`;
truncate table `authorities`;
truncate table `documents`;
truncate table `organisations`;
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
VALUES ('testUser2','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 2, 'John','Smith','John@Swansea.ac.uk',NOW());

INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('testUser3','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 1, 'John','Smith','John@Swansea.ac.uk',NOW());

INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('testUser4','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 2, 'John','Smith','John@Swansea.ac.uk',NOW());

INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('testOrgAdmin','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 1, 'John','Smith','John@Cardiff.ac.uk',NOW());

INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('testOrgAdmin2','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 2, 'John','Smith','John@Swansea.ac.uk',NOW());

INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('testSystemAdmin','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 1, 'John','Smith','John@Cardiff.ac.uk',NOW());

INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('the_invalid_username_king','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 1, 'John','Smith','John@Cardiff.ac.uk',NOW());

-- ------------------------
-- Authorities
-- ------------------------
INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('testUser', 'researcher');
INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('testUser2', 'researcher');
INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('testUser3', 'researcher');
INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('testUser4', 'researcher');
INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('testOrgAdmin', 'orgAdmin');
INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('testOrgAdmin2', 'orgAdmin');
INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('testSystemAdmin', 'systemAdmin');


-- --------------------------
-- Documents
-- ---------------------------
INSERT INTO `documents` (`id`, `fk_creator`, `title`, `date`, `file_type`, `file_data`)
VALUES (null, 'testUser', 'An Excellent File', '2021-10-13' ,'pdf', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `date`, `file_type`, `file_data`)
VALUES (null, 'testUser', 'A Fun PDF', '2021-12-14' ,'pdf', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `date`, `file_type`, `file_data`)
VALUES (null, 'testUser', 'Word Test', '2021-11-08' ,'docx', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `date`, `file_type`, `file_data`)
VALUES (null, 'testUser2', 'Different Org 1', '2021-07-26' ,'pdf', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `date`, `file_type`, `file_data`)
VALUES (null, 'testUser2', 'Different Org 2', '2021-04-06' ,'pdf', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `date`, `file_type`, `file_data`)
VALUES (null, 'testUser2', 'Different Org 3', '2020-12-31' ,'pdf', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `date`, `file_type`, `file_data`)
VALUES (null, 'testUser2', 'Swansea Uni PDF', '2021-12-03' ,'pdf', null);


-- --------------------------
-- Document Access Rights
-- --------------------------
INSERT INTO `document_access_rights` (`id`, `fk_document_id`, `fk_organisation_id`, `fk_user_id`)
VALUES (null, 1, 1, null);
INSERT INTO `document_access_rights` (`id`, `fk_document_id`, `fk_organisation_id`, `fk_user_id`)
VALUES (null, 2, null, 'testUser2');
INSERT INTO `document_access_rights` (`id`, `fk_document_id`, `fk_organisation_id`, `fk_user_id`)
VALUES (null, 4, 1, null);
INSERT INTO `document_access_rights` (`id`, `fk_document_id`, `fk_organisation_id`, `fk_user_id`)
VALUES (null, 5, 1, null);


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

INSERT INTO `access_audit_actions` (`id`, `action_description`)
VALUES (null, 'File upload failed');

INSERT INTO `access_audit_actions` (`id`, `action_description`)
VALUES (null, 'Document download failed');

INSERT INTO `access_audit_actions` (`id`, `action_description`)
VALUES (null, 'View document failed');


-- --------------------------
-- Access_Audit_Reports
-- --------------------------
INSERT INTO `access_audit_reports` (`id`, `fk_username`, `document_id`, `fk_action_id`, `action_date`, `action_description`)
VALUES (null, 'testUser', null, 1, NOW(), 'This is a test piece of data under testUser');

INSERT INTO `access_audit_reports` (`id`, `fk_username`, `document_id`, `fk_action_id`, `action_date`, `action_description`)
VALUES (null, 'testUser', null, 6, NOW(), 'This is a test piece of data under testUser');

INSERT INTO `access_audit_reports` (`id`, `fk_username`, `document_id`, `fk_action_id`, `action_date`, `action_description`)
VALUES (null, 'testUser', 1, 7, NOW(), 'This is a test piece of data under testUser');


