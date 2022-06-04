-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema orders_management
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema orders_management
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `orders_management` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `orders_management` ;

-- -----------------------------------------------------
-- Table `orders_management`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `orders_management`.`client` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `address` VARCHAR(70) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1260
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `orders_management`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `orders_management`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `orders_management`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `orders_management`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idProduct` INT NOT NULL,
  `idClient` INT NOT NULL,
  `productQuantity` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Order_Product_idx` (`idProduct` ASC) VISIBLE,
  INDEX `fk_Order_Client1_idx` (`idClient` ASC) VISIBLE,
  CONSTRAINT `fk_Order_Client1`
    FOREIGN KEY (`idClient`)
    REFERENCES `orders_management`.`client` (`id`),
  CONSTRAINT `fk_Order_Product`
    FOREIGN KEY (`idProduct`)
    REFERENCES `orders_management`.`product` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
