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
                       username VARCHAR_IGNORECASE(50) NOT NULL PRIMARY KEY,
                       password VARCHAR_IGNORECASE(100) NOT NULL,
                       enabled BOOLEAN NOT NULL
);


-- ------------------------------------
-- Table authorities
-- ------------------------------------

CREATE TABLE IF NOT EXISTS authorities (
                             username VARCHAR_IGNORECASE(50) NOT NULL,
                             authority VARCHAR_IGNORECASE(50) NOT NULL,
                             CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
);
-- ------------------------------------
-- Unique Usernames - ensures that all values in a coloumn are different
-- ------------------------------------

CREATE UNIQUE INDEX ix_auth_username
    on authorities (username,authority);


