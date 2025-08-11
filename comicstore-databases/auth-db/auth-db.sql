-- MySQL Workbench Forward Engineering

-- Deshabilitar temporalmente las comprobaciones de claves foráneas para evitar errores al eliminar.
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema auth-db
-- -----------------------------------------------------

-- Elimina el esquema si ya existe para asegurar una creación limpia.
DROP SCHEMA IF EXISTS `auth-db` ;

-- -----------------------------------------------------
-- Schema auth-db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `auth-db` DEFAULT CHARACTER SET utf8 ;
USE `auth-db` ;

-- -----------------------------------------------------
-- Tabla: user
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auth-db`.`user` (
  `user_id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_email` VARCHAR(320) NOT NULL,
  `user_password` VARCHAR(1000) NOT NULL,
  `user_first_name` VARCHAR(250) NOT NULL,
  `user_last_name` VARCHAR(250) NOT NULL,
  `user_address` VARCHAR(250) NOT NULL,
  `user_bio` VARCHAR(1000) NULL,
  `user_picture_id` BIGINT NULL,
  `user_enabled` BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_email_UNIQUE` (`user_email` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabla: role (anteriormente rol)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auth-db`.`role` (
  `role_id` BIGINT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(250) NOT NULL UNIQUE,
  `role_description` VARCHAR(1000) NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabla: user_has_role (anteriormente user_has_rol)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auth-db`.`user_has_role` (
  `user_role_user_id` BIGINT NOT NULL,
  `user_role_role_id` BIGINT NOT NULL,
  PRIMARY KEY (`user_role_user_id`, `user_role_role_id`),
  INDEX `fk_user_has_role_role1_idx` (`user_role_role_id` ASC) VISIBLE,
  INDEX `fk_user_has_role_user1_idx` (`user_role_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_role_user1`
    FOREIGN KEY (`user_role_user_id`)
    REFERENCES `auth-db`.`user` (`user_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_user_has_role_role1`
    FOREIGN KEY (`user_role_role_id`)
    REFERENCES `auth-db`.`role` (`role_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB;


-- Restaurar la configuración original.
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;