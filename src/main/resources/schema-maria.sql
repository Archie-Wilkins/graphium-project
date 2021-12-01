-- ------------------------------------
-- Database 'graphium'
-- ------------------------------------
drop table if exists `users` CASCADE;
drop table if exists `authorities` CASCADE;
drop table if exists `documents` CASCADE;

drop schema if exists `graphium`;
create schema `graphium`;
use `graphium`;

-- Code based on from https://www.baeldung.com/spring-security-jdbc-authentication tutorial

-- ------------------------------------
-- Table 'users'
-- ------------------------------------
CREATE TABLE IF NOT EXISTS `users` (
    `username` VARCHAR(50) NOT NULL PRIMARY KEY,
    `password` VARCHAR(500) NOT NULL,
    `enabled` BOOLEAN NOT NULL
);


-- ------------------------------------
-- Table 'authorities'
-- ------------------------------------
CREATE TABLE IF NOT EXISTS `authorities` (
    `username` VARCHAR(50) NOT NULL,
    `authority` VARCHAR(50) NOT NULL,
    CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES users(`username`)
);


-- ------------------------------------
-- Table `documents`
-- ------------------------------------
CREATE TABLE IF NOT EXISTS `documents` (
    `id` INT(4) NOT NULL AUTO_INCREMENT,
    `fk_username` VARCHAR(50) NOT NULL,
    `title` VARCHAR(100) NOT NULL,
    `file_type` VARCHAR(20) NOT NULL,
    `visibility` VARCHAR(45) NOT NULL,
    `file_data` LONGBLOB NOT NULL,

    PRIMARY KEY(`id`),
    FOREIGN KEY (`fk_username`) REFERENCES users(`username`)
);


-- ------------------------------------
-- Unique Usernames - ensures that all values in a coloumn are different
-- ------------------------------------
CREATE UNIQUE INDEX `ix_auth_username`
    on authorities(`username`,`authority`);