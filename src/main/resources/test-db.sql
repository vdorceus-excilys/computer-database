-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema cdb-test-db-db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cdb-test-db-db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cdb-test-db-db` DEFAULT CHARACTER SET latin1 ;
USE `cdb-test-db-db` ;

-- -----------------------------------------------------
-- Table `cdb-test-db-db`.`company`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cdb-test-db-db`.`company` ;

CREATE TABLE IF NOT EXISTS `cdb-test-db-db`.`company` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 101
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `cdb-test-db-db`.`computer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cdb-test-db-db`.`computer` ;

CREATE TABLE IF NOT EXISTS `cdb-test-db-db`.`computer` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `introduced` TIMESTAMP NULL DEFAULT NULL,
  `discontinued` TIMESTAMP NULL DEFAULT NULL,
  `company_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `ix_computer_company_1` (`company_id` ASC),
  CONSTRAINT `fk_computer_company_1`
    FOREIGN KEY (`company_id`)
    REFERENCES `cdb-test-db-db`.`company` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 101
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
