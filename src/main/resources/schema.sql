-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema ratos3
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ratos3
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ratos3` DEFAULT CHARACTER SET utf8 ;
USE `ratos3` ;

-- -----------------------------------------------------
-- Table `ratos3`.`theme`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratos3`.`theme` ;

CREATE TABLE IF NOT EXISTS `ratos3`.`theme` (
  `theme_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`theme_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ratos3`.`resource`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratos3`.`resource` ;

CREATE TABLE IF NOT EXISTS `ratos3`.`resource` (
  `resource_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `hyperlink` VARCHAR(200) NOT NULL,
  `description` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`resource_id`),
  UNIQUE INDEX `resource_link_UNIQUE` (`hyperlink` ASC),
  UNIQUE INDEX `resource_description_UNIQUE` (`description` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ratos3`.`question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratos3`.`question` ;

CREATE TABLE IF NOT EXISTS `ratos3`.`question` (
  `question_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `DTYPE` VARCHAR(31) NOT NULL,
  `title` VARCHAR(1000) NOT NULL,
  `level` TINYINT(1) NOT NULL DEFAULT 1,
  `theme_id` INT UNSIGNED NOT NULL,
  `resource_id` INT UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`question_id`),
  INDEX `fk_question_theme_theme_id_idx` (`theme_id` ASC),
  INDEX `fk_question_resource_resource_id_idx` (`resource_id` ASC),
  CONSTRAINT `fk_question_theme_theme_id`
    FOREIGN KEY (`theme_id`)
    REFERENCES `ratos3`.`theme` (`theme_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_question_resource_resource_id`
    FOREIGN KEY (`resource_id`)
    REFERENCES `ratos3`.`resource` (`resource_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ratos3`.`answer_mcq`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratos3`.`answer_mcq` ;

CREATE TABLE IF NOT EXISTS `ratos3`.`answer_mcq` (
  `answer_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `answer` VARCHAR(500) NOT NULL,
  `percent` TINYINT(3) NOT NULL,
  `is_required` TINYINT(1) NOT NULL DEFAULT 0,
  `resource_id` INT UNSIGNED NULL DEFAULT NULL,
  `question_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`answer_id`),
  INDEX `fk_answer_mcq_resource_resource_id_idx` (`resource_id` ASC),
  INDEX `fk_answer_mcq_question_question_id_idx` (`question_id` ASC),
  CONSTRAINT `fk_answer_mcq_resource_resource_id`
    FOREIGN KEY (`resource_id`)
    REFERENCES `ratos3`.`resource` (`resource_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_answer_mcq_question_question_id`
    FOREIGN KEY (`question_id`)
    REFERENCES `ratos3`.`question` (`question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ratos3`.`settings_fbq`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratos3`.`settings_fbq` ;

CREATE TABLE IF NOT EXISTS `ratos3`.`settings_fbq` (
  `set_id` INT UNSIGNED NOT NULL,
  `lang` VARCHAR(5) NOT NULL,
  `words_limit` INT UNSIGNED NOT NULL,
  `symbols_limit` INT UNSIGNED NOT NULL,
  `is_numeric` TINYINT(1) NOT NULL,
  `is_typo_allowed` TINYINT(1) NOT NULL,
  `is_case_sensitive` TINYINT(1) NOT NULL,
  PRIMARY KEY (`set_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ratos3`.`answer_fbsq`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratos3`.`answer_fbsq` ;

CREATE TABLE IF NOT EXISTS `ratos3`.`answer_fbsq` (
  `answer_id` INT UNSIGNED NOT NULL,
  `set_id` INT UNSIGNED NOT NULL,
  INDEX `fk_answer_fbsq_settings_set_id_idx` (`set_id` ASC),
  PRIMARY KEY (`answer_id`),
  CONSTRAINT `fk_answer_fbsq_settings_set_id`
    FOREIGN KEY (`set_id`)
    REFERENCES `ratos3`.`settings_fbq` (`set_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_answer_fbsq_question_answer_id`
    FOREIGN KEY (`answer_id`)
    REFERENCES `ratos3`.`question` (`question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ratos3`.`answer_mq`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratos3`.`answer_mq` ;

CREATE TABLE IF NOT EXISTS `ratos3`.`answer_mq` (
  `answer_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `left_phrase` VARCHAR(200) NOT NULL,
  `right_phrase` VARCHAR(200) NOT NULL,
  `right_phrase_resource_id` INT UNSIGNED NULL DEFAULT NULL,
  `question_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`answer_id`),
  INDEX `fk_answer_mq_resource_resource_id_idx` (`right_phrase_resource_id` ASC),
  INDEX `fk_answer_mq_question_question_id_idx` (`question_id` ASC),
  CONSTRAINT `fk_answer_mq_resource_resource_id`
    FOREIGN KEY (`right_phrase_resource_id`)
    REFERENCES `ratos3`.`resource` (`resource_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_answer_mq_question_question_id`
    FOREIGN KEY (`question_id`)
    REFERENCES `ratos3`.`question` (`question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ratos3`.`answer_sq`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratos3`.`answer_sq` ;

CREATE TABLE IF NOT EXISTS `ratos3`.`answer_sq` (
  `answer_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `element` VARCHAR(200) NOT NULL,
  `order` TINYINT(2) NOT NULL,
  `resource_id` INT UNSIGNED NULL DEFAULT NULL,
  `question_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`answer_id`),
  INDEX `fk_answer_sq_resource_resource_id_idx` (`resource_id` ASC),
  INDEX `fk_answer_sq_question_question_id_idx` (`question_id` ASC),
  CONSTRAINT `fk_answer_sq_resource_resource_id`
    FOREIGN KEY (`resource_id`)
    REFERENCES `ratos3`.`resource` (`resource_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_answer_sq_question_question_id`
    FOREIGN KEY (`question_id`)
    REFERENCES `ratos3`.`question` (`question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ratos3`.`help`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratos3`.`help` ;

CREATE TABLE IF NOT EXISTS `ratos3`.`help` (
  `help_id` INT UNSIGNED NOT NULL,
  `text` VARCHAR(500) NOT NULL,
  `help_resource_id` INT UNSIGNED NULL DEFAULT NULL,
  INDEX `fk_help_resource_resource_id_idx` (`help_resource_id` ASC),
  INDEX `fk_help_question_help_id_idx` (`help_id` ASC),
  PRIMARY KEY (`help_id`),
  CONSTRAINT `fk_help_resource_resource_id`
    FOREIGN KEY (`help_resource_id`)
    REFERENCES `ratos3`.`resource` (`resource_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_help_question_help_id`
    FOREIGN KEY (`help_id`)
    REFERENCES `ratos3`.`question` (`question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ratos3`.`accepted_phrase`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratos3`.`accepted_phrase` ;

CREATE TABLE IF NOT EXISTS `ratos3`.`accepted_phrase` (
  `phrase_id` INT UNSIGNED NOT NULL,
  `phrase` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`phrase_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ratos3`.`fbsq_phrase`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratos3`.`fbsq_phrase` ;

CREATE TABLE IF NOT EXISTS `ratos3`.`fbsq_phrase` (
  `phrase_id` INT UNSIGNED NOT NULL,
  `answer_id` INT UNSIGNED NOT NULL,
  INDEX `fk_bfsq_phrase_accepted_phrase_phrase_id_idx` (`phrase_id` ASC),
  INDEX `fk_fbsq_phrase_answer_fbsq_answer_id_idx` (`answer_id` ASC),
  CONSTRAINT `fk_bfsq_phrase_accepted_phrase_phrase_id`
    FOREIGN KEY (`phrase_id`)
    REFERENCES `ratos3`.`accepted_phrase` (`phrase_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_fbsq_phrase_answer_fbsq_answer_id`
    FOREIGN KEY (`answer_id`)
    REFERENCES `ratos3`.`answer_fbsq` (`answer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ratos3`.`answer_fbmq`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratos3`.`answer_fbmq` ;

CREATE TABLE IF NOT EXISTS `ratos3`.`answer_fbmq` (
  `answer_id` INT UNSIGNED NOT NULL,
  `phrase` VARCHAR(100) NOT NULL,
  `occurrence` INT NOT NULL,
  `set_id` INT UNSIGNED NOT NULL,
  `question_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`answer_id`),
  INDEX `fk_answer_fbmq_settings_set_id_idx` (`set_id` ASC),
  INDEX `fk_answer_fbmq_question_question_id_idx` (`question_id` ASC),
  CONSTRAINT `fk_answer_fbmq_settings_set_id`
    FOREIGN KEY (`set_id`)
    REFERENCES `ratos3`.`settings_fbq` (`set_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_answer_fbmq_question_question_id`
    FOREIGN KEY (`question_id`)
    REFERENCES `ratos3`.`question` (`question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ratos3`.`fbmq_phrase`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratos3`.`fbmq_phrase` ;

CREATE TABLE IF NOT EXISTS `ratos3`.`fbmq_phrase` (
  `answer_id` INT UNSIGNED NOT NULL,
  `phrase_id` INT UNSIGNED NOT NULL,
  INDEX `fk_fbmq_phrase_answer_fbmq_answer_id_idx` (`answer_id` ASC),
  INDEX `fk_fbmq_phrase_accepted_phrase_phrase_id_idx` (`phrase_id` ASC),
  CONSTRAINT `fk_fbmq_phrase_answer_fbmq_answer_id`
    FOREIGN KEY (`answer_id`)
    REFERENCES `ratos3`.`answer_fbmq` (`answer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_fbmq_phrase_accepted_phrase_phrase_id`
    FOREIGN KEY (`phrase_id`)
    REFERENCES `ratos3`.`accepted_phrase` (`phrase_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
