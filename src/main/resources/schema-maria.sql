drop schema if exists graphium_schema;

create schema graphium_schema;

use graphium_schema;

drop table if exists users;
drop table if exists authorities;

-- Code based on from https://www.baeldung.com/spring-security-jdbc-authentication tutorial

-- ------------------------------------
-- Table users
-- ------------------------------------

CREATE TABLE IF NOT EXISTS users (
                       `username` VARCHAR(50) NOT NULL,
                       `password` VARCHAR(100) NOT NULL,
                       `enabled` TINYINT NOT NULL DEFAULT 1,
                       PRIMARY KEY (`username`)
);
ENGINE = InnoDB;

-- ------------------------------------
-- Table authorities
-- ------------------------------------

CREATE TABLE IF NOT EXISTS authorities (
                             `username` VARCHAR(50) NOT NULL,
                             `authority` VARCHAR(50) NOT NULL,
                             FOREIGN KEY (`username`) REFERENCES users(`username`)
);
ENGINE = InnoDB;
-- ------------------------------------
-- Unique Usernames - ensures that all values in a coloumn are different
-- ------------------------------------

CREATE UNIQUE INDEX ix_auth_username
    on authorities (`username`,`authority`);


