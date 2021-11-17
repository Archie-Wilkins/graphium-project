-- -----------------------------------------------------
-- Table `documents`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `documents` (
    `id` INT(4) NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(100) NOT NULL,
    `type_pdf` BOOLEAN NOT NULL,

    PRIMARY KEY(`id`)
);