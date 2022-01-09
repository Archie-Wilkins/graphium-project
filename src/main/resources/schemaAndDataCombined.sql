-- ------------------------------------
-- Database Accounts
-- ------------------------------------

-- User can access database from localhost as we only want users to be able to access the database from known hosts.
-- Passwords follows 3 random words best practice (automatically hashed by mySQL)
-- For the sake of demonstration various users with different roles have been created all names are made up

-- ====================================
-- Comment code out before running project from here end point on line 48 (Marked) 
-- ====================================

-- Super admin(Trusted with more privilidges such as drop table)
-- Incharge of overseeing the database  
CREATE USER 'MarkPaulSuperAdmin'@'127.0.0.1' IDENTIFIED BY 'PlantBridgeTrain';

GRANT SELECT, UPDATE, DELETE, INSERT, ALTER, CREATE, DROP, INDEX, GRANT OPTION ON graphium.documents TO 'MarkPaulSuperAdmin'@'127.0.0.1';
GRANT SELECT, UPDATE, DELETE, INSERT, ALTER, CREATE, DROP, INDEX, GRANT OPTION ON graphium.document_access_rights TO 'MarkPaulSuperAdmin'@'127.0.0.1';
GRANT SELECT, UPDATE, DELETE, INSERT, ALTER, CREATE, DROP, INDEX, GRANT OPTION ON graphium.users TO 'MarkPaulSuperAdmin'@'127.0.0.1';
GRANT SELECT, UPDATE, DELETE, INSERT, ALTER, CREATE, DROP, INDEX, GRANT OPTION ON graphium.authorities TO 'MarkPaulSuperAdmin'@'127.0.0.1';
GRANT SELECT, UPDATE, DELETE, INSERT, ALTER, CREATE, DROP, INDEX, GRANT OPTION ON graphium.organisations TO 'MarkPaulSuperAdmin'@'127.0.0.1';
GRANT SELECT, UPDATE, DELETE, INSERT, ALTER, CREATE, DROP, INDEX, GRANT OPTION ON graphium.access_audit_actions TO 'MarkPaulSuperAdmin'@'127.0.0.1';
GRANT SELECT, UPDATE, DELETE, INSERT, ALTER, CREATE, DROP, INDEX, GRANT OPTION ON access_audit_reports TO 'MarkPaulSuperAdmin'@'127.0.0.1';
GRANT SELECT, ALTER, CREATE, GRANT OPTION ON graphium.documents_audit TO 'MarkPaulSuperAdmin'@'127.0.0.1';
GRANT SELECT, ALTER, CREATE, GRANT OPTION ON graphium.users_audit TO 'MarkPaulSuperAdmin'@'127.0.0.1';

-- Senior Developer 
-- Primarily creates new-features and works on back-end code uses the database to test code and assist customers
CREATE USER 'KevinWilliamsSeniorDeveloper'@'127.0.0.1' IDENTIFIED BY 'HorseLightPlane';

GRANT SELECT, UPDATE, DELETE, INSERT, INDEX ON graphium.documents TO 'KevinWilliamsSeniorDeveloper'@'127.0.0.1';
GRANT SELECT, UPDATE, DELETE, INSERT, INDEX ON graphium.document_access_rights TO 'KevinWilliamsSeniorDeveloper'@'127.0.0.1';
GRANT SELECT, UPDATE, DELETE, INSERT, INDEX ON graphium.users TO 'KevinWilliamsSeniorDeveloper'@'127.0.0.1';
GRANT SELECT, UPDATE, DELETE, INSERT, INDEX ON graphium.authorities TO 'KevinWilliamsSeniorDeveloper'@'127.0.0.1';
GRANT SELECT, UPDATE, DELETE, INSERT, INDEX ON graphium.organisations TO 'KevinWilliamsSeniorDeveloper'@'127.0.0.1';
GRANT SELECT, UPDATE, DELETE, INSERT, INDEX ON graphium.access_audit_actions TO 'KevinWilliamsSeniorDeveloper'@'127.0.0.1';
GRANT SELECT, UPDATE, DELETE, INSERT, INDEX ON access_audit_reports TO 'KevinWilliamsSeniorDeveloper'@'127.0.0.1';
GRANT SELECT ON graphium.documents_audit TO 'KevinWilliamsSeniorDeveloper'@'127.0.0.1';
GRANT SELECT ON graphium.users_audit TO 'KevinWilliamsSeniorDeveloper'@'127.0.0.1';



-- -- -- Junior Developer (Given less priviledges)
-- Primarily creates new-features and works on back-end code uses the database to test code and assist customers would need senior developer support 
-- for more sensitive issues 
CREATE USER 'ChrisHansenJuniorDeveloper'@'127.0.0.1' IDENTIFIED BY 'DogSpeakerMarmalade';

GRANT SELECT, UPDATE, DELETE ON graphium.documents TO 'ChrisHansenJuniorDeveloper'@'127.0.0.1';
GRANT SELECT, UPDATE, DELETE ON graphium.document_access_rights TO 'ChrisHansenJuniorDeveloper'@'127.0.0.1';
GRANT SELECT, UPDATE, DELETE ON graphium.users TO 'ChrisHansenJuniorDeveloper'@'127.0.0.1';
GRANT SELECT, UPDATE, DELETE ON graphium.authorities TO 'ChrisHansenJuniorDeveloper'@'127.0.0.1';
GRANT SELECT, UPDATE, DELETE ON graphium.organisations TO 'ChrisHansenJuniorDeveloper'@'127.0.0.1';
GRANT SELECT ON graphium.access_audit_actions TO 'ChrisHansenJuniorDeveloper'@'127.0.0.1';
GRANT SELECT ON graphium.access_audit_reports TO 'ChrisHansenJuniorDeveloper'@'127.0.0.1';
GRANT SELECT ON graphium.documents_audit TO 'ChrisHansenJuniorDeveloper'@'127.0.0.1';
GRANT SELECT ON graphium.users_audit TO 'ChrisHansenJuniorDeveloper'@'127.0.0.1';


-- Mark Strange represents a user who should only be able to view database data for example he may be a 
-- customer support assistant who should be able to look up a users account, documents, users actions without updating them
-- the decisions to not allow MarkStrange to update,delete or insert data was on the basis that Sam may not have developer expierence 
-- so should not have access to potentially disruptive or destructive commands.
CREATE USER 'MarkStrangeMarketingExpert'@'127.0.0.1' IDENTIFIED BY 'HorseLightPlane';
GRANT SELECT ON graphium.users TO 'MarkStrangeMarketingExpert'@'127.0.0.1';
GRANT SELECT ON graphium.documents TO 'MarkStrangeMarketingExpert'@'127.0.0.1';

-- -- System 
-- The user the system uses when accessing the databsae 
-- Minimum permissions granted to minimise damage incase an attacker tries to use the system to attack the database
CREATE USER 'GraphiumApplication'@'localhost' IDENTIFIED BY 'PrincePandaDepot';
GRANT SELECT, UPDATE, DELETE, INSERT, CREATE, DROP, INDEX ON graphium.documents TO 'GraphiumApplication'@'localhost';
GRANT SELECT, UPDATE, DELETE, INSERT, CREATE, DROP, INDEX ON graphium.document_access_rights TO 'GraphiumApplication'@'localhost';
GRANT SELECT, UPDATE, DELETE, INSERT, CREATE, DROP, INDEX ON graphium.users TO 'GraphiumApplication'@'localhost';
GRANT SELECT, UPDATE, DELETE, INSERT, CREATE, DROP, INDEX ON graphium.authorities TO 'GraphiumApplication'@'localhost';
GRANT SELECT, UPDATE, DELETE, INSERT, CREATE, DROP, INDEX ON graphium.organisations TO 'GraphiumApplication'@'localhost';
GRANT INSERT, CREATE, DROP ON graphium.access_audit_actions TO 'GraphiumApplication'@'localhost';
GRANT INSERT, CREATE, DROP ON graphium.access_audit_reports TO 'GraphiumApplication'@'localhost';


-- ==============================
-- End of section to comment out 
-- ==============================

-- ------------------------------------
-- Database 'graphium'
-- ------------------------------------

create schema if not exists `graphium`;
use `graphium`;

-- Drop tables that have foreign key constraint with another table first
-- All tables are dropped before dropping the database as errors occured otherwise
SET FOREIGN_KEY_CHECKS = 0;
drop table if exists `users`;
drop table if exists `organisations`;
drop table if exists `authorities`;
drop table if exists `document_access_rights`;
drop table if exists `documents`;
drop table if exists `authorities`;
drop table if exists `access_audit_reports`;
drop table if exists `access_audit_actions`;
SET FOREIGN_KEY_CHECKS = 1;

drop schema if exists `graphium`;
create schema `graphium`;
use `graphium`;


-- ------------------------------------
-- organisations
-- -----------------------------------
CREATE TABLE IF NOT EXISTS `organisations`(
    `id` INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `name` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100)
);

-- ------------------------------------
-- Table 'users'
-- ------------------------------------
CREATE TABLE IF NOT EXISTS `users` (
    `username` VARCHAR(50) NOT NULL PRIMARY KEY, 
    `password` VARCHAR(100) NOT NULL, 
    `enabled` BOOLEAN NOT NULL, 
    `fk_organisation_id` INT NOT NULL, 
    `first_name` VARCHAR(50) NOT NULL, 
    `last_name` VARCHAR(50) NOT NULL, 
    `email` VARCHAR(100) NOT NULL, 
    -- set to NOT NULL until issue is resolved
    `authority_set_date` DATETIME,
    
    CONSTRAINT `fk_users_organisations` FOREIGN KEY (`fk_organisation_id`) REFERENCES organisations(`id`)
    ON UPDATE CASCADE
	ON DELETE CASCADE
	);


-- ------------------------------------
-- Table `documents`
-- ------------------------------------
CREATE TABLE IF NOT EXISTS `documents` (
    `id` INT(4) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `fk_creator` VARCHAR(50) NOT NULL,
    `title` VARCHAR(100) NOT NULL,
    `date` VARCHAR(20) NOT NULL,
    `file_type` VARCHAR(20) NOT NULL,
    -- need to make Not NUll for testing as creating instances 
    -- with valid blob data from mySQL requires hard-coding paths which is not ideal 
    `file_data` LONGBLOB,

    CONSTRAINT `fk_documents_users` FOREIGN KEY (`fk_creator`) REFERENCES users(`username`)
	ON UPDATE CASCADE
	ON DELETE CASCADE
);


-- ------------------------------------
-- Table 'authorities'
-- ------------------------------------
CREATE TABLE IF NOT EXISTS `authorities` (
    `fk_username` VARCHAR(50) NOT NULL,
    `authority` VARCHAR(50) NOT NULL,

    CONSTRAINT `fk_authorities_users` FOREIGN KEY (`fk_username`) REFERENCES users(`username`)
);


-- ------------------------------------
-- Table 'access'
-- ------------------------------------
CREATE TABLE IF NOT EXISTS `access_audit_actions` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `action_description` VARCHAR(200) NOT NULL
);


-- ------------------------------------
-- Table `document_access_rights`
-- ------------------------------------
CREATE TABLE IF NOT EXISTS `document_access_rights` (
    `id` INT(5) NOT NULL AUTO_INCREMENT,
    `fk_document_id` INT(4) NOT NULL,
    `fk_organisation_id` INT NULL,
    `fk_user_id` VARCHAR(50) NULL,

	PRIMARY KEY (`id`),
    FOREIGN KEY (`fk_document_id`) REFERENCES documents(`id`)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (`fk_organisation_id`) REFERENCES organisations(`id`)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (`fk_user_id`) REFERENCES users(`username`)
        ON UPDATE CASCADE
        ON DELETE CASCADE
    );


-- ------------------------------------
-- Table 'access_audit_reports'
-- ------------------------------------
CREATE TABLE IF NOT EXISTS `access_audit_reports` (
	`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `fk_username` VARCHAR(50) NOT NULL,
    `fk_document_id` INT,
    `fk_organisation_id` INT,
    `fk_action_id` INT NOT NULL,
    `action_date` VARCHAR(50) NOT NULL,
    `action_description` VARCHAR(400) NOT NULL,

     CONSTRAINT `fk_access_audit_users` FOREIGN KEY (`fk_username`) REFERENCES users(`username`)
     ON UPDATE CASCADE
	 ON DELETE CASCADE,
     CONSTRAINT `fk_document_id` FOREIGN KEY (`fk_document_id`) REFERENCES documents(`id`)
     ON UPDATE CASCADE
	 ON DELETE CASCADE,
     CONSTRAINT `fk_organisation_id` FOREIGN KEY (`fk_organisation_id`) REFERENCES organisations(`id`)
     ON UPDATE CASCADE
	 ON DELETE CASCADE,
     CONSTRAINT `fk_action_id` FOREIGN KEY (`fk_action_id`) REFERENCES access_audit_actions(`id`)
     ON UPDATE CASCADE
	 ON DELETE CASCADE
);


-- ------------------------------------
-- Unique Usernames - ensures that all values in a coloumn are different
-- ------------------------------------
CREATE UNIQUE INDEX `ix_auth_username`
    on authorities(`fk_username`,`authority`);

-- ==========================================================
-- ========================================================
-- Auditing tables 
-- Tables designed to track time, user and operations (insert, update and delete) performed on databases and the data after the change
-- (For delete statements data is tracked before it is deleted) 
-- ========================================================
-- ==========================================================


-- ======================================
-- Users Auditing 
-- ======================================

DROP TABLE IF EXISTS `users_audit`;
CREATE TABLE IF NOT EXISTS `users_audit` (
    `audit_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `timestamp` DATETIME NOT NULL ,
    `type` VARCHAR(20) NOT NULL,
    `db_user` VARCHAR(250) NOT NULL,
    `username` VARCHAR(50) NOT NULL, 
    `password` VARCHAR(100) NOT NULL, 
    `enabled` BOOLEAN NOT NULL, 
    `fk_organisation_id` INT NOT NULL, 
    `first_name` VARCHAR(50) NOT NULL, 
    `last_name` VARCHAR(50) NOT NULL, 
    `email` VARCHAR(100) NOT NULL, 
    -- set to NOT NULL until issue is resolved
    `authority_set_date` DATETIME
);

DROP TRIGGER IF EXISTS users_insert_audit_trigger;
DELIMITER $$ 
CREATE TRIGGER users_insert_audit_trigger
AFTER INSERT ON users FOR EACH ROW 
BEGIN
    INSERT INTO users_audit (
        audit_id, timestamp, type, db_user, username, password, enabled, fk_organisation_id, 
        first_name, last_name, email, authority_set_date
	)
    VALUES(
		null, CURRENT_TIMESTAMP, 'INSERT', user(), NEW.username, NEW.password,
		NEW.enabled, NEW.fk_organisation_id, NEW.first_name, NEW.last_name, NEW.email, NEW.authority_set_date
	);
END $$ 
DELIMITER ;

DROP TRIGGER IF EXISTS users_update_audit_trigger;
DELIMITER $$ 
CREATE TRIGGER users_update_audit_trigger
AFTER UPDATE ON users FOR EACH ROW 
BEGIN
    INSERT INTO users_audit (
        audit_id, timestamp, type, db_user, username, password, enabled, fk_organisation_id, 
        first_name, last_name, email, authority_set_date
	)
    VALUES(
		null, CURRENT_TIMESTAMP, 'UPDATE', user(), NEW.username, NEW.password,
		NEW.enabled, NEW.fk_organisation_id, NEW.first_name, NEW.last_name, NEW.email ,NEW.authority_set_date
	);
END $$ 
DELIMITER ;

DROP TRIGGER IF EXISTS users_delete_audit_trigger;
DELIMITER $$ 
CREATE TRIGGER users_delete_audit_trigger
AFTER DELETE ON users FOR EACH ROW 
BEGIN
    INSERT INTO users_audit (
        audit_id, timestamp, type, db_user, username, password, enabled, fk_organisation_id, 
        first_name, last_name, email, authority_set_date
	)
    VALUES(
		null, CURRENT_TIMESTAMP, 'DELETE', user(), OLD.username, OLD.password,
		OLD.enabled, OLD.fk_organisation_id, OLD.first_name, OLD.last_name, OLD.email , OLD.authority_set_date
	);
END $$ 
DELIMITER ;

-- -- =========================================================
-- -- Documents Auditing 
-- -- =========================================================

DROP TABLE IF EXISTS `documents_audit`;
CREATE TABLE IF NOT EXISTS `documents_audit` (
    `audit_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `timestamp` DATETIME NOT NULL ,
    `type` VARCHAR(20) NOT NULL,
    `db_user` VARCHAR(250) NOT NULL,
    `fk_creator` VARCHAR(50) NOT NULL,
    `title` VARCHAR(100) NOT NULL,
    `date` VARCHAR(20) NOT NULL,
    `file_type` VARCHAR(20) NOT NULL,
    `file_data` LONGBLOB
); 

DROP TRIGGER IF EXISTS documents_insert_audit_trigger;
DELIMITER $$ 
CREATE TRIGGER documents_insert_audit_trigger
AFTER INSERT ON documents FOR EACH ROW 
BEGIN
    INSERT INTO documents_audit (
        audit_id, timestamp, type, db_user, 
        fk_creator, title, date, file_type, file_data
	)
    VALUES(
		null, CURRENT_TIMESTAMP, 'INSERT', user(), NEW.fk_creator, NEW.title,
		NEW.date, NEW.file_type, NEW.file_data
	);
END $$ 
DELIMITER ;

DROP TRIGGER IF EXISTS documents_update_audit_trigger;
DELIMITER $$ 
CREATE TRIGGER documents_update_audit_trigger
AFTER UPDATE ON documents FOR EACH ROW 
BEGIN
    INSERT INTO documents_audit (
        audit_id, timestamp, type, db_user, 
        fk_creator, title, date, file_type, file_data
	)
    VALUES(
		null, CURRENT_TIMESTAMP, 'UPDATE', user(), NEW.fk_creator, NEW.title,
		NEW.date, NEW.file_type, NEW.file_data
	);
END $$ 
DELIMITER ;

DROP TRIGGER IF EXISTS documents_delete_audit_trigger;
DELIMITER $$ 
CREATE TRIGGER documents_delete_audit_trigger
AFTER DELETE ON documents FOR EACH ROW 
BEGIN
    INSERT INTO documents_audit (
        audit_id, timestamp, type, db_user, 
        fk_creator, title, date, file_type, file_data
	)
    VALUES(
		null, CURRENT_TIMESTAMP, 'DELETE', user(), OLD.fk_creator, OLD.title,
		OLD.date, OLD.file_type, OLD.file_data
	);
END $$ 
DELIMITER ;

-- -- =========================================================
-- -- Organisations Auditing 
-- -- =========================================================

DROP TABLE IF EXISTS `organisations_audit`;
CREATE TABLE IF NOT EXISTS `organisations_audit` (
    `audit_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `timestamp` DATETIME NOT NULL ,
    `type` VARCHAR(20) NOT NULL,
    `db_user` VARCHAR(250) NOT NULL,
    `org_id` int,
    `name` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100)
    
);

DROP TRIGGER IF EXISTS organisations_insert_audit_trigger;
DELIMITER $$ 
CREATE TRIGGER organisations_insert_audit_trigger
AFTER INSERT ON organisations FOR EACH ROW 
BEGIN
    INSERT INTO organisations_audit (
        audit_id, timestamp, type, db_user, 
        org_id, name, email
	)
    VALUES(
		null, CURRENT_TIMESTAMP, 'INSERT', user(), NEW.id, NEW.name,
		NEW.email
	);
END $$ 
DELIMITER ;

DROP TRIGGER IF EXISTS organisations_update_audit_trigger;
DELIMITER $$ 
CREATE TRIGGER organisations_update_audit_trigger
AFTER UPDATE ON organisations FOR EACH ROW 
BEGIN
    INSERT INTO organisations_audit (
       audit_id, timestamp, type, db_user, 
        org_id, name, email
	)
    VALUES(
		null, CURRENT_TIMESTAMP, 'UPDATE', user(), NEW.id, NEW.name,
		NEW.email
	);
END $$ 
DELIMITER ;

DROP TRIGGER IF EXISTS organisations_delete_audit_trigger;
DELIMITER $$ 
CREATE TRIGGER organisations_delete_audit_trigger
AFTER DELETE ON organisations FOR EACH ROW 
BEGIN
    INSERT INTO organisations_audit (
        audit_id, timestamp, type, db_user, 
        org_id, name, email
	)
    VALUES(
		null, CURRENT_TIMESTAMP, 'DELETE', user(), OLD.id, OLD.name,
		OLD.email
	);
END $$ 
DELIMITER ;

-- =============================
-- Authorities Audit
-- =============================

DROP TABLE IF EXISTS `authorities_audit`;
CREATE TABLE IF NOT EXISTS `authorities_audit` (
    `audit_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `timestamp` DATETIME NOT NULL ,
    `type` VARCHAR(20) NOT NULL,
    `db_user` VARCHAR(250) NOT NULL,
    `fk_username` VARCHAR(50) NOT NULL,
    `authority` VARCHAR(50) NOT NULL
);

DROP TRIGGER IF EXISTS authorities_insert_audit_trigger;
DELIMITER $$ 
CREATE TRIGGER authorities_insert_audit_trigger
AFTER INSERT ON authorities FOR EACH ROW 
BEGIN
    INSERT INTO authorities_audit (
        audit_id, timestamp, type, db_user, 
        fk_username, authority
	)
    VALUES(
		null, CURRENT_TIMESTAMP, 'INSERT', user(), NEW.fk_username, NEW.authority
	);
END $$ 
DELIMITER ;

DROP TRIGGER IF EXISTS authorities_update_audit_trigger;
DELIMITER $$ 
CREATE TRIGGER authorities_update_audit_trigger
AFTER UPDATE ON authorities FOR EACH ROW 
BEGIN
    INSERT INTO authorities_audit (
       audit_id, timestamp, type, db_user, 
        fk_username, authority
	)
    VALUES(
		null, CURRENT_TIMESTAMP, 'UPDATE', user(), NEW.fk_username, NEW.authority
	);
END $$ 
DELIMITER ;

DROP TRIGGER IF EXISTS authorities_delete_audit_trigger;
DELIMITER $$ 
CREATE TRIGGER authorities_delete_audit_trigger
AFTER DELETE ON authorities FOR EACH ROW 
BEGIN
    INSERT INTO authorities_audit (
        audit_id, timestamp, type, db_user, 
        fk_username, authority
	)
    VALUES(
		null, CURRENT_TIMESTAMP, 'DELETE', user(), OLD.fk_username, OLD.authority
	);
END $$ 
DELIMITER ;


-- =============================
-- Document_Access_Rights Audit 
-- =============================

DROP TABLE IF EXISTS `document_access_rights_audit`;
CREATE TABLE IF NOT EXISTS `document_access_rights_audit` (
    `audit_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `timestamp` DATETIME NOT NULL ,
    `type` VARCHAR(20) NOT NULL,
    `db_user` VARCHAR(250) NOT NULL,
	`right_id` INT(5),
    `fk_document_id` INT(4) NOT NULL,
    `fk_organisation_id` INT NULL,
    `fk_user_id` VARCHAR(50) NULL
);

DROP TRIGGER IF EXISTS document_access_rights_insert_audit_trigger;
DELIMITER $$ 
CREATE TRIGGER document_access_rights_insert_audit_trigger
AFTER INSERT ON document_access_rights FOR EACH ROW 
BEGIN
    INSERT INTO document_access_rights_audit (
        audit_id, timestamp, type, db_user, 
        right_id, fk_document_id, fk_organisation_id, fk_user_id
	)
    VALUES(
		null, CURRENT_TIMESTAMP, 'INSERT', user(), NEW.id, NEW.fk_document_id, NEW.fk_organisation_id, NEW.fk_user_id
	);
END $$ 
DELIMITER ;

DROP TRIGGER IF EXISTS document_access_rights_update_audit_trigger;
DELIMITER $$ 
CREATE TRIGGER document_access_rights_update_audit_trigger
AFTER UPDATE ON document_access_rights FOR EACH ROW 
BEGIN
    INSERT INTO document_access_rights_audit (
       audit_id, timestamp, type, db_user, 
        right_id, fk_document_id, fk_organisation_id, fk_user_id
	)
    VALUES(
		null, CURRENT_TIMESTAMP, 'UPDATE', user(), NEW.id, NEW.fk_document_id, NEW.fk_organisation_id, NEW.fk_user_id
	);
END $$ 
DELIMITER ;

DROP TRIGGER IF EXISTS document_access_rights_delete_audit_trigger;
DELIMITER $$ 
CREATE TRIGGER document_access_rights_delete_audit_trigger
AFTER DELETE ON document_access_rights FOR EACH ROW 
BEGIN
    INSERT INTO document_access_rights_audit (
        audit_id, timestamp, type, db_user, 
        right_id, fk_document_id, fk_organisation_id, fk_user_id
	)
    VALUES(
		null, CURRENT_TIMESTAMP, 'DELETE', user(), OLD.id, OLD.fk_document_id, OLD.fk_organisation_id, OLD.fk_user_id
	);
END $$ 
END $$ 
DELIMITER ;


-- ==============================
-- End of Schema Script 
-- ==============================


-- ===============================
-- Beginning of Data-maria Script
-- ==================================

-- ------------------------------------
-- Database 'graphium'
-- ------------------------------------
use `graphium`;
-- Foreign key checks are set to 0 to avoid constraint errors while dropping data 
-- All tables are dropped for the automated tests otherwise they will fail 
SET FOREIGN_KEY_CHECKS = 0;
truncate table `document_access_rights`;
truncate table `users`;
truncate table `authorities`;
truncate table `documents`;
truncate table `organisations`;
-- Reenabling key checks 
SET FOREIGN_KEY_CHECKS = 1;

-- -----------------------------------
-- Organisations 
-- -----------------------------------
INSERT INTO `organisations` (`id`,`name`,`email`) VALUES (null, 'Cardiff University', 'Cardiff@CardiffEmail.com');
INSERT INTO `organisations` (`id`,`name`,`email`) VALUES (null, 'Swansea University', 'Swansea@SwanseaEmail.com');

-- ------------------------------------
-- Users
-- ------------------------------------
-- All passwords decrypt to just be password (using bcrypt)
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


-- Used for storing data where an invalid username is submitted as access_audit_reports table uses username as a primary key.
INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('invalid_username','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 1, 'John','Smith','John@Cardiff.ac.uk',NOW());

-- Used to test how services react to requests from an invalid authority type 
-- INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
-- VALUES ('security_tests','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 1, 'John','Smith','John@Cardiff.ac.uk',NOW());
-- INSERT INTO `authorities` (`fk_username`, `authority`) VALUES ('security_tests', 'test');
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
-- All documents have null file_data otherwise data would have to added by providing hard coded paths. 
-- This would be very cumbersome for running tests on eachothers computers unless we all used a shared file structure so null file_data is used instead
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
-- This table is used to categorise the different audit logs
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
-- AccessAuditReports
-- --------------------------
INSERT INTO `access_audit_reports` (`id`, `fk_username`, `fk_document_id`,`fk_organisation_id`, `fk_action_id`, `action_date`, `action_description`)
VALUES (null, 'testUser', null, null,1, NOW(), 'User Logged in (Test Data)');

INSERT INTO `access_audit_reports` (`id`, `fk_username`, `fk_document_id`,`fk_organisation_id`, `fk_action_id`, `action_date`, `action_description`)
VALUES (null, 'testUser', null, 1,6, NOW(), 'Organisation Documents Accessed Failed Attempt (Test Data)');

INSERT INTO `access_audit_reports` (`id`, `fk_username`, `fk_document_id`,`fk_organisation_id`, `fk_action_id`, `action_date`, `action_description`)
VALUES (null, 'testUser', 1, null,7, NOW(), 'Document Downloaded (Test Data)');









