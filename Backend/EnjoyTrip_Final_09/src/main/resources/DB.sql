-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema final_gwantong
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema final_gwantong
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `final_gwantong` DEFAULT CHARACTER SET utf8 ;
USE `final_gwantong` ;

-- -----------------------------------------------------
-- Table `final_gwantong`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_gwantong`.`users` (
  `user_seq` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NOT NULL,
  `user_email` VARCHAR(100) NOT NULL,
  `user_password` VARCHAR(50) NOT NULL,
  `user_nickname` VARCHAR(10) NOT NULL,
  `user_profile_image_url` VARCHAR(500) NULL DEFAULT '/img/noprofile.png',
  `user_register_date` DATE NULL DEFAULT NULL,
  `user_clsf` CHAR(3) NULL DEFAULT NULL,
  PRIMARY KEY (`user_seq`),
  UNIQUE INDEX `user_email_UNIQUE` (`user_email` ASC) VISIBLE,
  UNIQUE INDEX `user_nickname_UNIQUE` (`user_nickname` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `final_gwantong`.`mypage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_gwantong`.`mypage` (
  `card_seq` INT NOT NULL AUTO_INCREMENT,
  `user_seq` INT NOT NULL,
  PRIMARY KEY (`card_seq`, `user_seq`),
  INDEX `from_users_to_mypage_user_seq_idx` (`user_seq` ASC) VISIBLE,
  CONSTRAINT `from_users_to_mypage_user_seq`
    FOREIGN KEY (`user_seq`)
    REFERENCES `final_gwantong`.`users` (`user_seq`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `final_gwantong`.`mypage_contnets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_gwantong`.`mypage_contnets` (
  `card_seq` INT NOT NULL,
  `content_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`card_seq`, `content_id`),
  CONSTRAINT `from_mypage_to_mypage_contents_card_seq`
    FOREIGN KEY (`card_seq`)
    REFERENCES `final_gwantong`.`mypage` (`card_seq`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
