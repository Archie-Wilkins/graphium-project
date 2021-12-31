-- ------------------------------------
-- Database Accounts
-- ------------------------------------

use mysql;
SELECT * FROM user;

-- Made up names for sake of assessment 
-- User can access database from localhost as we only want users to be able to access the database from known hosts.
-- Passwords follows 3 random words best practice (automatically hashed by mySQL)
-- For the sake of demonstration I have created various users with different roles 

-- Senior Developer (Trusted with more privilidges such as drop table) 
CREATE USER 'RonSwansonSuperAdmin'@'127.0.0.1' IDENTIFIED BY 'PlantBridgeTrain';
GRANT SELECT, CREATE, DELETE, DROP, INSERT, UPDATE ON graphium.* TO 'JackCooperSuperAdmin'@'127.0.0.1';

CREATE USER 'JackCooperSeniorDeveloper'@'127.0.0.1' IDENTIFIED BY 'HorseLightPlane';
GRANT SELECT, CREATE, INSERT, UPDATE ON graphium.* TO 'JackCooperSeniorDeveloper'@'127.0.0.1';

-- Junior Developer (Given less priviledges)
CREATE USER 'TomScott'@'127.0.0.1' IDENTIFIED BY 'DogSpeakerMarmalade';

-- access_audit_actions 
GRANT SELECT, INSERT, UPDATE ON graphium.access_audit_actions TO 'TomScott'@'127.0.0.1';
-- access_audit_reports
GRANT SELECT ON graphium.access_audit_reports TO 'TomScott'@'127.0.0.1';
-- authorities 
GRANT SELECT, INSERT, UPDATE ON graphium.authorities TO 'TomScott'@'127.0.0.1';
-- document_access_rights
GRANT SELECT ON graphium.document_access_rights TO 'TomScott'@'127.0.0.1';
-- documents 
GRANT SELECT ON graphium.documents TO 'TomScott'@'127.0.0.1';
-- organisations 
GRANT SELECT, INSERT, UPDATE ON graphium.organisations TO 'TomScott'@'127.0.0.1';
-- users 
GRANT SELECT, INSERT, UPDATE ON graphium.users TO 'TomScott'@'127.0.0.1';

SHOW GRANTS FOR 'TomScott'@'127.0.0.1';

-- Sam Robin represents a user who should only be able to view database data for example he may be a 
-- customer support assistant who should be able to look up a users account, documents, users actions without updating them
-- the decisions to not allow Sam to update,delete or insert data was on the basis that Sam may not have developer expierence 
-- so should not have access to potentially disruptive or destructive commands.
CREATE USER 'SamRobbin'@'127.0.0.1' IDENTIFIED BY 'HorseLightPlane';
GRANT SELECT ON graphium.* TO 'SamRobbin'@'127.0.0.1';



-- ------------------------------------
-- Database 'graphium'
-- ------------------------------------

create schema if not exists `graphium`;
use `graphium`;

-- Drop tables that have foreign key constraint with another table first
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
    -- need to make Not NUll just for testing
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

-- ----------------------------------
-- Sample Improved Auditing System 
-- ----------------------------------
DROP TABLE IF EXISTS `users_audit`;

CREATE TABLE IF NOT EXISTS `users_audit` (
    `audit_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `timestamp` DATETIME NOT NULL ,
    `type` VARCHAR(20) NOT NULL,
    `creator` VARCHAR(250) NOT NULL,
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
        audit_id, timestamp, type, creator, username, password, enabled, fk_organisation_id, 
        first_name, last_name, email, authority_set_date
	)
    VALUES(
		null, CURRENT_TIMESTAMP, 'INSERT', user(), new.username, new.password,
		new.enabled, new.fk_organisation_id, new.first_name, new.last_name, new.email ,new.authority_set_date
	);
END $$ 
DELIMITER ;

DROP TRIGGER IF EXISTS users_update_audit_trigger;
DELIMITER $$ 
CREATE TRIGGER users_update_audit_trigger
AFTER UPDATE ON users FOR EACH ROW 
BEGIN
    INSERT INTO users_audit (
        audit_id, timestamp, type, creator, username, password, enabled, fk_organisation_id, 
        first_name, last_name, email, authority_set_date
	)
    VALUES(
		null, CURRENT_TIMESTAMP, 'UPDATE', user(), new.username, new.password,
		new.enabled, new.fk_organisation_id, new.first_name, new.last_name, new.email ,new.authority_set_date
	);
END $$ 
DELIMITER ;

INSERT INTO `users` (`username`,`password`, `enabled`, `fk_organisation_id`,`first_name`, `last_name`, `email`, `authority_set_date`)
VALUES ('audit_test4','$2a$10$9ch3QV3gYNS7lPW/m.TUr.LcH9uEynCbmbGGocRkBAavzRzU0mYa.', 1, 1, 'John','Smith','John@Audit.ac.uk',NOW());

UPDATE users
SET first_name= 'Bill'
WHERE username = 'audit_test4';
