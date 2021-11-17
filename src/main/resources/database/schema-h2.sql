-- -----------------------------------------------------
-- Table `documents`
-- -----------------------------------------------------
drop schema if exists graphium_testdb;
create schema graphium_testdb;
use graphium_testdb;
drop table if exists documents;

-- -----------------------------------------------------
-- Table `documents`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `documents` (
    `id` INT(4) NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(100) NOT NULL,
    `type_pdf` BOOLEAN NOT NULL,

    PRIMARY KEY(`id`)
);

-- -----------------------------------------------------
-- Insert `documents`
-- -----------------------------------------------------
INSERT INTO `documents` (`title`, `type_pdf`) VALUES ('Test 1', 1);
INSERT INTO `documents` (`title`, `type_pdf`) VALUES ('Test 2', 1);