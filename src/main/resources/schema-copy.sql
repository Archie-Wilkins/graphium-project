
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
    -- need to make Not NUll for testing as creating instances 
    -- with valid blob data from mySQL requires hard-coding paths
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



-- ====================================
-- Comment code out before running project from here end point on 256 (Marked) 
-- ====================================

-- ======================================
-- Users
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
-- Authorities
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
-- Document_Access_Rights
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
DELIMITER ;