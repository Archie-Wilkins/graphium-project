-- ------------------------------------
-- Database 'graphium'
-- ------------------------------------
drop schema if exists `graphium`;
create schema `graphium`;
use `graphium`;

drop table if exists `users`;
drop table if exists `authorities`;
drop table if exists `documents`;
drop table if exists `organisations`;

-- Code based on from https://www.baeldung.com/spring-security-jdbc-authentication tutorial

-- ------------------------------------
-- Table 'users'
-- ------------------------------------
CREATE TABLE IF NOT EXISTS `users` (
    `id` INT NOT NULL PRIMARY KEY,
    `username` VARCHAR(50),
    `password` VARCHAR(500) NOT NULL,
    `enabled` BOOLEAN NOT NULL,
    `fk_organisation_id` INT NOT NULL,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `email` VARCHAR(50) NOT NULL,
    `authority_title` VARCHAR(50) NOT NULL, 
    `authority_set_date` DATETIME NOT NULL,
    
     FOREIGN KEY (`fk_organisation_id`) REFERENCES organisations(`id`)
);

-- ------------------------------------
-- Table 'authorities'
-- ------------------------------------
CREATE TABLE IF NOT EXISTS `authorities` (
    `fk_username` VARCHAR(50) NOT NULL,
    `authority` VARCHAR(50) NOT NULL,

    FOREIGN KEY (`fk_username`) REFERENCES users(`username`)
);

-- ------------------------------------
-- Table `documents`
-- ------------------------------------
CREATE TABLE IF NOT EXISTS `documents` (
    `id` INT(4) NOT NULL AUTO_INCREMENT,
    `fk_creator` VARCHAR(50) NOT NULL,
    `title` VARCHAR(100) NOT NULL,
    `file_type` VARCHAR(20) NOT NULL,
    `file_data` LONGBLOB NOT NULL,

    PRIMARY KEY(`id`),
    FOREIGN KEY (`fk_creator`) REFERENCES users(`username`)
);

-- ------------------------------------
-- Unique Usernames - ensures that all values in a coloumn are different
-- ------------------------------------
CREATE UNIQUE INDEX `ix_auth_username`
    on authorities(`fk_username`,`authority`);

-- ------------------------------------
-- organisations
-- -----------------------------------
CREATE TABLE IF NOT EXISTS `organisations`(
    `id` INT AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100)
);