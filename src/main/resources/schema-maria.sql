-- ------------------------------------
-- Database 'graphium'
-- ------------------------------------
drop schema if exists `graphium`;
create schema `graphium`;
use `graphium`;

drop table if exists `users`;
drop table if exists `authorities`;
drop table if exists `documents`;

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
    `title` VARCHAR(100) NOT NULL,
    `type_pdf` BOOLEAN NOT NULL,

    PRIMARY KEY(`id`)
);


-- ------------------------------------
-- Unique Usernames - ensures that all values in a coloumn are different
-- ------------------------------------
CREATE UNIQUE INDEX `ix_auth_username`
    on authorities(`username`,`authority`);