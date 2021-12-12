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
INSERT INTO `organisations` (`id`,`name`,`email`) VALUES (null, 'Cardiff University', 'Cardiff@CardiffUniversity.ac.uk');
INSERT INTO `organisations` (`id`,`name`,`email`) VALUES (null, 'Swansea University', 'Swansea@SwanseaUniversity.ac.uk');
INSERT INTO `organisations` (`id`,`name`,`email`) VALUES (null, 'Cardiff Council', 'Cardiff@CardiffCouncil.com');


-- ------------------------------------
-- Users
-- ------------------------------------
-- both the passwords decrypt to just be password (using bcrypt)
-- If usernames are changed, make sure to refactor all tests using them or they will error
INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('RyanBeck','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 1, 'Ryan','Beck','RyanBeck@CardiffUni.ac.uk',NOW());

INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('ChloeWick','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 2, 'Chloe','Wick','ChloeWick@SwanseaUni.ac.uk',NOW());

INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('GeorgeBold','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 1, 'George','Bould','GeorgeBould@SwanseaUni.ac.uk',NOW());

INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('RonMicky','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 2, 'Ron','Micky','RonMick@SwanseaUni.ac.uk',NOW());

INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('RobBarlow','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 1, 'Rob','Barlow','RobBarlow@CardiffUni.ac.uk',NOW());

INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('PeteEvans','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 2, 'Pete','Evans','PeteEvans@SwanseaUni.ac.uk',NOW());

INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('ClaireShore','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 1, 'Claire','Shore','John@CardiffUni.ac.uk',NOW());

INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('the_invalid_username_king','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 1, 'John','Smith','John@Cardiff.ac.uk',NOW());

-- ------------------------
-- Authorities
-- ------------------------
INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('RyanBeck', 'researcher');
INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('ChloeWick', 'researcher');
INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('GeorgeBold', 'researcher');
INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('RonMicky', 'researcher');
INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('RobBarlow', 'orgAdmin');
INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('PeteEvans', 'orgAdmin');
INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('ClaireShore', 'systemAdmin');
INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('the_invalid_username_king', 'systemAdmin');


-- --------------------------
-- Documents
-- ---------------------------
INSERT INTO `documents` (`id`, `fk_creator`, `title`, `date`, `file_type`, `file_data`)
VALUES (null, 'RyanBeck', 'A Study On Bridges', '2021-10-13' ,'pdf', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `date`, `file_type`, `file_data`)
VALUES (null, 'RyanBeck', 'The Science of Bridges', '2021-12-14' ,'pdf', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `date`, `file_type`, `file_data`)
VALUES (null, 'RyanBeck', 'A Critical Review of Bridges', '2021-11-08' ,'docx', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `date`, `file_type`, `file_data`)
VALUES (null, 'PeteEvans', 'Computer Science Made Easy', '2021-07-26' ,'pdf', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `date`, `file_type`, `file_data`)
VALUES (null, 'PeteEvans', 'Computer Goes Beep Boop', '2021-04-06' ,'pdf', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `date`, `file_type`, `file_data`)
VALUES (null, 'PeteEvans', 'Agile Architecture', '2020-12-31' ,'pdf', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `date`, `file_type`, `file_data`)
VALUES (null, 'PeteEvans', 'Agile Methodologies', '2021-12-03' ,'pdf', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `date`, `file_type`, `file_data`)
VALUES (null, 'ClaireShore', 'Structuring a business', '2021-04-06' ,'pdf', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `date`, `file_type`, `file_data`)
VALUES (null, 'ClaireShore', 'Developing a USP', '2020-12-31' ,'pdf', null);

INSERT INTO `documents` (`id`, `fk_creator`, `title`, `date`, `file_type`, `file_data`)
VALUES (null, 'ClaireShore', 'Starting a start-up', '2021-12-03' ,'pdf', null);

-- --------------------------
-- Document Access Rights
-- --------------------------
INSERT INTO `document_access_rights` (`id`, `fk_document_id`, `fk_organisation_id`, `fk_user_id`)
VALUES (null, 1, 1, null);
INSERT INTO `document_access_rights` (`id`, `fk_document_id`, `fk_organisation_id`, `fk_user_id`)
VALUES (null, 2, null, 'PeteEvans');
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
VALUES (null, 'RyanBeck', null, 1, NOW(), 'This is a test piece of data under RyanBeck');

INSERT INTO `access_audit_reports` (`id`, `fk_username`, `document_id`, `fk_action_id`, `action_date`, `action_description`)
VALUES (null, 'RyanBeck', null, 6, NOW(), 'This is a test piece of data under RyanBeck');

INSERT INTO `access_audit_reports` (`id`, `fk_username`, `document_id`, `fk_action_id`, `action_date`, `action_description`)
VALUES (null, 'RyanBeck', 1, 7, NOW(), 'This is a test piece of data under RyanBeck');

