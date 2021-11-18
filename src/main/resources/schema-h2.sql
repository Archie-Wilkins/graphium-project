drop schema if exists graphium_schema;

create schema graphium_schema;

use graphium_schema;

drop table if exists users;
drop table if exists authorities;

-- ------------------------------------
-- Table users
-- ------------------------------------

CREATE TABLE IF NOT EXISTS users (
    -- add id
                       username VARCHAR(50) NOT NULL PRIMARY KEY,
                       password VARCHAR(100) NOT NULL,
                       enabled BOOLEAN NOT NULL
);


-- ------------------------------------
-- Table authorities
-- ------------------------------------

CREATE TABLE IF NOT EXISTS authorities (
                             username VARCHAR(50) NOT NULL,
                             authority VARCHAR(50) NOT NULL,
                             FOREIGN KEY (username) REFERENCES users(username));
-- ------------------------------------
-- Unique Usernames - ensures that all values in a coloumn are different
-- ------------------------------------

CREATE UNIQUE INDEX ix_auth_username
    on authorities (username,authority);


