SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `sistemabancario-fg` ;
CREATE SCHEMA IF NOT EXISTS `sistemabancario-fg` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;

-- -----------------------------------------------------
-- Table `sistemabancario-fg`.`bancos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sistemabancario-fg`.`bancos` ;

CREATE  TABLE IF NOT EXISTS `sistemabancario-fg`.`bancos` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `numero` INT NULL ,
  `nome` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sistemabancario-fg`.`agencias`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sistemabancario-fg`.`agencias` ;

CREATE  TABLE IF NOT EXISTS `sistemabancario-fg`.`agencias` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `bancos_id` INT NOT NULL ,
  `numero` INT NULL ,
  `nome` VARCHAR(45) NOT NULL ,
  `endereco` VARCHAR(45) NOT NULL ,
  `nome_gerente` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_agencias_bancos` (`bancos_id` ASC) ,
  CONSTRAINT `fk_agencias_bancos`
    FOREIGN KEY (`bancos_id` )
    REFERENCES `sistemabancario-fg`.`bancos` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sistemabancario-fg`.`tiposclientes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sistemabancario-fg`.`tiposclientes` ;

CREATE  TABLE IF NOT EXISTS `sistemabancario-fg`.`tiposclientes` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sistemabancario-fg`.`clientes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sistemabancario-fg`.`clientes` ;

CREATE  TABLE IF NOT EXISTS `sistemabancario-fg`.`clientes` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `tiposcliente_id` INT NOT NULL ,
  `nome` VARCHAR(45) NOT NULL ,
  `telefone` VARCHAR(15) NOT NULL ,
  `email` VARCHAR(45) NOT NULL ,
  `cpf` VARCHAR(14) NULL ,
  `cnpj` VARCHAR(45) NULL ,
  `nome_fantasia` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_clientes_tipoclientes1` (`tiposcliente_id` ASC) ,
  CONSTRAINT `fk_clientes_tipoclientes1`
    FOREIGN KEY (`tiposcliente_id` )
    REFERENCES `sistemabancario-fg`.`tiposclientes` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sistemabancario-fg`.`tiposcontas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sistemabancario-fg`.`tiposcontas` ;

CREATE  TABLE IF NOT EXISTS `sistemabancario-fg`.`tiposcontas` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sistemabancario-fg`.`contas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sistemabancario-fg`.`contas` ;

CREATE  TABLE IF NOT EXISTS `sistemabancario-fg`.`contas` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ContaNumero' ,
  `tiposconta_id` INT NOT NULL ,
  `agencia_id` INT NOT NULL ,
  `cliente_id` INT NOT NULL ,
  `saldo` DOUBLE NULL DEFAULT 0 ,
  `limite` DOUBLE NULL DEFAULT 0 ,
  `aniversario` DATE NULL ,
  `ativa` TINYINT(1) NULL DEFAULT 1 ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_contas_agencias1` (`agencia_id` ASC) ,
  INDEX `fk_contas_clientes1` (`cliente_id` ASC) ,
  INDEX `fk_contas_tiposcontas1` (`tiposconta_id` ASC) ,
  CONSTRAINT `fk_contas_agencias1`
    FOREIGN KEY (`agencia_id` )
    REFERENCES `sistemabancario-fg`.`agencias` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_contas_clientes1`
    FOREIGN KEY (`cliente_id` )
    REFERENCES `sistemabancario-fg`.`clientes` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_contas_tiposcontas1`
    FOREIGN KEY (`tiposconta_id` )
    REFERENCES `sistemabancario-fg`.`tiposcontas` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `sistemabancario-fg`.`tiposclientes`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
INSERT INTO `sistemabancario-fg`.`tiposclientes` (`id`, `nome`) VALUES (1, 'Gerente');
INSERT INTO `sistemabancario-fg`.`tiposclientes` (`id`, `nome`) VALUES (2, 'Pessoa Jurídica');
INSERT INTO `sistemabancario-fg`.`tiposclientes` (`id`, `nome`) VALUES (3, 'Pessoa Física');

COMMIT;

-- -----------------------------------------------------
-- Data for table `sistemabancario-fg`.`tiposcontas`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
INSERT INTO `sistemabancario-fg`.`tiposcontas` (`id`, `nome`) VALUES (1, 'Conta Poupança');
INSERT INTO `sistemabancario-fg`.`tiposcontas` (`id`, `nome`) VALUES (2, 'Conta Corrente');

COMMIT;
