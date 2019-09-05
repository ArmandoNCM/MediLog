-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema medi_log
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema medi_log
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `medi_log` DEFAULT CHARACTER SET utf8 ;
USE `medi_log` ;

-- -----------------------------------------------------
-- Table `medi_log`.`country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`country` (
  `id_country` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_country`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`state_province`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`state_province` (
  `id_state_province` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `country` INT NOT NULL,
  PRIMARY KEY (`id_state_province`),
  INDEX `state_province_country_fk_idx` (`country` ASC),
  CONSTRAINT `state_province_country_fk`
    FOREIGN KEY (`country`)
    REFERENCES `medi_log`.`country` (`id_country`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`city`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`city` (
  `id_city` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `state_province` INT NOT NULL,
  PRIMARY KEY (`id_city`),
  INDEX `city_state_province_fk_idx` (`state_province` ASC),
  CONSTRAINT `city_state_province_fk`
    FOREIGN KEY (`state_province`)
    REFERENCES `medi_log`.`state_province` (`id_state_province`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`person` (
  `id_person` CHAR(15) NOT NULL,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `birth_date` DATE NOT NULL,
  `id_expedition_city` INT NOT NULL,
  `identification_type` CHAR(1) NOT NULL,
  `gender` CHAR(1) NOT NULL,
  PRIMARY KEY (`id_person`),
  INDEX `person_city_fk_idx` (`id_expedition_city` ASC),
  CONSTRAINT `person_city_fk`
    FOREIGN KEY (`id_expedition_city`)
    REFERENCES `medi_log`.`city` (`id_city`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`client` (
  `id_client` CHAR(15) NOT NULL,
  `city` INT NULL,
  `academic_level` CHAR(1) NULL,
  `social_level` TINYINT NULL,
  `civil_status` CHAR(1) NULL,
  `address` VARCHAR(254) NULL,
  `phone` VARCHAR(15) NULL,
  PRIMARY KEY (`id_client`),
  INDEX `client_city_fk_idx` (`city` ASC),
  CONSTRAINT `client_person_fk`
    FOREIGN KEY (`id_client`)
    REFERENCES `medi_log`.`person` (`id_person`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `client_city_fk`
    FOREIGN KEY (`city`)
    REFERENCES `medi_log`.`city` (`id_city`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`employee` (
  `id_employee` CHAR(15) NOT NULL,
  `role` TINYINT NOT NULL,
  `password_salt` BINARY(128) NOT NULL,
  `password_hash` BINARY(32) NOT NULL,
  PRIMARY KEY (`id_employee`),
  CONSTRAINT `employee_person_fk`
    FOREIGN KEY (`id_employee`)
    REFERENCES `medi_log`.`person` (`id_person`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`company` (
  `id_company` CHAR(15) NOT NULL,
  `name` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`id_company`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`informed_consent`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`informed_consent` (
  `id_informed_consent` INT NOT NULL AUTO_INCREMENT,
  `client` CHAR(15) NOT NULL,
  `employee` CHAR(15) NOT NULL,
  `contracting_company` CHAR(15) NULL,
  `check_type` CHAR(1) NOT NULL,
  `work_in_heights` TINYINT(1) NOT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`id_informed_consent`),
  INDEX `informed_consent_person_fk_idx` (`client` ASC),
  INDEX `informed_consent_employee_fk_idx` (`employee` ASC),
  INDEX `informed_consent_company_idx` (`contracting_company` ASC),
  CONSTRAINT `informed_consent_client_fk`
    FOREIGN KEY (`client`)
    REFERENCES `medi_log`.`client` (`id_client`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `informed_consent_employee_fk`
    FOREIGN KEY (`employee`)
    REFERENCES `medi_log`.`employee` (`id_employee`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `informed_consent_company`
    FOREIGN KEY (`contracting_company`)
    REFERENCES `medi_log`.`company` (`id_company`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`occupational_record`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`occupational_record` (
  `id_occupational_record` INT NOT NULL AUTO_INCREMENT,
  `client` CHAR(15) NOT NULL,
  `company` CHAR(15) NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NULL,
  `role` VARCHAR(50) NOT NULL,
  `registered_on` DATE NOT NULL,
  PRIMARY KEY (`id_occupational_record`),
  INDEX `ocupational_record_client_fk_idx` (`client` ASC),
  INDEX `ocupational_record_company_fk_idx` (`company` ASC),
  CONSTRAINT `ocupational_record_client_fk`
    FOREIGN KEY (`client`)
    REFERENCES `medi_log`.`client` (`id_client`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ocupational_record_company_fk`
    FOREIGN KEY (`company`)
    REFERENCES `medi_log`.`company` (`id_company`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`professional_risk`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`professional_risk` (
  `id_professional_risk` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `type` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_professional_risk`),
  UNIQUE INDEX `professional_risk_unique_key` (`name` ASC, `type` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`medical_case_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`medical_case_type` (
  `id_medical_case_type` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_medical_case_type`),
  UNIQUE INDEX `medical_case_type_unique_key` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`case_history`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`case_history` (
  `case_type` INT NOT NULL,
  `client` CHAR(15) NOT NULL,
  `background_type` CHAR(1) NOT NULL,
  `registered_on` DATE NOT NULL,
  INDEX `case_history_medical_case_type_idx` (`case_type` ASC),
  INDEX `case_history_client_fk_idx` (`client` ASC),
  PRIMARY KEY (`case_type`, `client`),
  CONSTRAINT `case_history_medical_case_type_fk`
    FOREIGN KEY (`case_type`)
    REFERENCES `medi_log`.`medical_case_type` (`id_medical_case_type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `case_history_client_fk`
    FOREIGN KEY (`client`)
    REFERENCES `medi_log`.`client` (`id_client`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`contact_information`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`contact_information` (
  `id_contact_information` INT NOT NULL AUTO_INCREMENT,
  `type` CHAR(15) NOT NULL,
  `name` VARCHAR(30) NOT NULL,
  `contact` VARCHAR(45) NOT NULL,
  `company` CHAR(15) NOT NULL,
  PRIMARY KEY (`id_contact_information`),
  UNIQUE INDEX `contact_information_unique_key` (`name` ASC, `company` ASC),
  INDEX `FK_CONTACT_INFORMATION_idx` (`company` ASC),
  CONSTRAINT `FK_CONTACT_INFORMATION`
    FOREIGN KEY (`company`)
    REFERENCES `medi_log`.`company` (`id_company`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`laboratory_exam`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`laboratory_exam` (
  `id_laboratory_exam` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `informed_consent` INT NOT NULL,
  PRIMARY KEY (`id_laboratory_exam`),
  INDEX `laboratory_exam_pk_idx` (`informed_consent` ASC),
  UNIQUE INDEX `laboratory_exam_unique_key` (`name` ASC, `informed_consent` ASC),
  CONSTRAINT `laboratory_exam_informed_consent_fk`
    FOREIGN KEY (`informed_consent`)
    REFERENCES `medi_log`.`informed_consent` (`id_informed_consent`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`laboratory_exam_attachment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`laboratory_exam_attachment` (
  `id_laboratory_exam_attachment` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `uri` VARCHAR(256) NOT NULL,
  `laboratory_exam` INT NOT NULL,
  PRIMARY KEY (`id_laboratory_exam_attachment`),
  INDEX `laboratory_exam_attachment_fk_idx` (`laboratory_exam` ASC),
  UNIQUE INDEX `laboratory_exam_attachment_unique_key` (`name` ASC, `laboratory_exam` ASC),
  CONSTRAINT `laboratory_exam_attachment_laboratory_exam_fk`
    FOREIGN KEY (`laboratory_exam`)
    REFERENCES `medi_log`.`laboratory_exam` (`id_laboratory_exam`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`informed_consent_professional_risk`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`informed_consent_professional_risk` (
  `informed_consent` INT NOT NULL,
  `professional_risk` INT NOT NULL,
  INDEX `informed_consent_professional_risk_informed_consent_fk_idx` (`informed_consent` ASC),
  INDEX `informed_consent_professional_risk_professional_risk_fk_idx` (`professional_risk` ASC),
  PRIMARY KEY (`informed_consent`, `professional_risk`),
  CONSTRAINT `informed_consent_professional_risk_informed_consent_fk`
    FOREIGN KEY (`informed_consent`)
    REFERENCES `medi_log`.`informed_consent` (`id_informed_consent`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `informed_consent_professional_risk_professional_risk_fk`
    FOREIGN KEY (`professional_risk`)
    REFERENCES `medi_log`.`professional_risk` (`id_professional_risk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`trauma_history`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`trauma_history` (
  `id_trauma_history` INT NOT NULL AUTO_INCREMENT,
  `client` CHAR(15) NOT NULL,
  `trauma_nature` VARCHAR(254) NOT NULL,
  `sequels` TEXT NULL,
  `occurrence_date` DATE NULL,
  `registered_on` DATE NOT NULL,
  PRIMARY KEY (`id_trauma_history`),
  INDEX `trauma_history_client_idx` (`client` ASC),
  CONSTRAINT `trauma_history_client`
    FOREIGN KEY (`client`)
    REFERENCES `medi_log`.`client` (`id_client`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`habit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`habit` (
  `id_habit` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_habit`),
  UNIQUE INDEX `habit_unique_key` (`description` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`client_habit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`client_habit` (
  `client` CHAR(15) NOT NULL,
  `habit` INT NOT NULL,
  `weekly_hours_intensity` INT NOT NULL,
  `registered_on` DATE NOT NULL,
  INDEX `client_habits_client_fk_idx` (`client` ASC),
  INDEX `client_habits_habit_type_fk_idx` (`habit` ASC),
  PRIMARY KEY (`client`, `habit`),
  CONSTRAINT `client_habits_client_fk`
    FOREIGN KEY (`client`)
    REFERENCES `medi_log`.`client` (`id_client`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `client_habits_habit_fk`
    FOREIGN KEY (`habit`)
    REFERENCES `medi_log`.`habit` (`id_habit`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`physical_check`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`physical_check` (
  `id_physical_check` INT NOT NULL,
  `employee` CHAR(15) NOT NULL,
  `weight_kilograms` DECIMAL(5,2) NOT NULL,
  `height_centimeters` SMALLINT NOT NULL,
  `pulse_beats_per_minute` SMALLINT NOT NULL,
  `respiratory_frequency_per_minute` SMALLINT NOT NULL,
  `body_temperature` DECIMAL(5,2) NOT NULL,
  `blood_pressure_standing` DECIMAL(6,2) NOT NULL,
  `blood_pressure_laying_down` DECIMAL(6,2) NOT NULL,
  `handedness` CHAR(1) NOT NULL,
  `diagnostics` TEXT(20000) NULL,
  `conclusions` TEXT(20000) NULL,
  `recommendations` TEXT(20000) NULL,
  PRIMARY KEY (`id_physical_check`),
  INDEX `physical_check_employee_fk_idx` (`employee` ASC),
  CONSTRAINT `physical_check_employee_fk`
    FOREIGN KEY (`employee`)
    REFERENCES `medi_log`.`employee` (`id_employee`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `physical_check_informed_consent_fk`
    FOREIGN KEY (`id_physical_check`)
    REFERENCES `medi_log`.`informed_consent` (`id_informed_consent`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`medical_anomaly`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`medical_anomaly` (
  `id_medical_anomaly` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_medical_anomaly`),
  UNIQUE INDEX `medical_anomaly_unique_key` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`physical_check_medical_anomaly`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`physical_check_medical_anomaly` (
  `physical_check` INT NOT NULL,
  `medical_anomaly` INT NOT NULL,
  `observations` VARCHAR(200) NULL,
  INDEX `physical_check_medical_anomaly_fk_idx` (`medical_anomaly` ASC),
  INDEX `physical_check_medical_anomaly_physical_check_fk_idx` (`physical_check` ASC),
  PRIMARY KEY (`physical_check`, `medical_anomaly`),
  CONSTRAINT `physical_check_medical_anomaly_fk`
    FOREIGN KEY (`medical_anomaly`)
    REFERENCES `medi_log`.`medical_anomaly` (`id_medical_anomaly`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `physical_check_medical_anomaly_physical_check_fk`
    FOREIGN KEY (`physical_check`)
    REFERENCES `medi_log`.`physical_check` (`id_physical_check`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`work_aptitude_concept`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`work_aptitude_concept` (
  `id_work_aptitude_concept` INT NOT NULL,
  `employee` CHAR(15) NOT NULL,
  `work_aptitude` CHAR(1) NOT NULL,
  `work_in_heights_aptitude` CHAR(1) NULL,
  `concept` CHAR(1) NOT NULL,
  `concept_observations` VARCHAR(250) NULL,
  `psychotechnic_test` CHAR(1) NOT NULL,
  `recommendations` TEXT(20000) NULL,
  PRIMARY KEY (`id_work_aptitude_concept`),
  INDEX `work_aptitude_concept_employee_fk_idx` (`employee` ASC),
  CONSTRAINT `work_aptitude_concept_informed_consent_fk`
    FOREIGN KEY (`id_work_aptitude_concept`)
    REFERENCES `medi_log`.`informed_consent` (`id_informed_consent`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `work_aptitude_concept_employee_fk`
    FOREIGN KEY (`employee`)
    REFERENCES `medi_log`.`employee` (`id_employee`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`post_exam_action`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`post_exam_action` (
  `id_post_exam_action` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `type` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_post_exam_action`),
  UNIQUE INDEX `post_exam_action_unique_key` (`name` ASC, `type` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medi_log`.`work_aptitude_concept_post_exam_action`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medi_log`.`work_aptitude_concept_post_exam_action` (
  `work_aptitude_concept` INT NOT NULL,
  `post_exam_action` INT NOT NULL,
  `observations` VARCHAR(200) NULL,
  INDEX `work_aptitude_concept_post_exam_action_work_aptitude_concep_idx` (`work_aptitude_concept` ASC),
  INDEX `work_aptitude_concept_post_exam_action_post_exam_action_fk_idx` (`post_exam_action` ASC),
  PRIMARY KEY (`post_exam_action`, `work_aptitude_concept`),
  CONSTRAINT `work_aptitude_concept_post_exam_action_work_aptitude_concept_fk`
    FOREIGN KEY (`work_aptitude_concept`)
    REFERENCES `medi_log`.`work_aptitude_concept` (`id_work_aptitude_concept`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `work_aptitude_concept_post_exam_action_post_exam_action_fk`
    FOREIGN KEY (`post_exam_action`)
    REFERENCES `medi_log`.`post_exam_action` (`id_post_exam_action`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
