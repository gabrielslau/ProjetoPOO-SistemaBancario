/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 5.5.16 : Database - sistemabancario
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sistemabancario` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_swedish_ci */;

USE `sistemabancario`;

/*Table structure for table `agencia` */

DROP TABLE IF EXISTS `agencia`;

CREATE TABLE `agencia` (
  `AgenciaCodigo` smallint(4) NOT NULL,
  `AgenciaDescricao` varchar(30) COLLATE utf8_swedish_ci NOT NULL,
  PRIMARY KEY (`AgenciaCodigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;

/*Data for the table `agencia` */

/*Table structure for table `cliente` */

DROP TABLE IF EXISTS `cliente`;

CREATE TABLE `cliente` (
  `ClienteCodigo` smallint(4) NOT NULL,
  `ClienteNome` varchar(50) COLLATE utf8_swedish_ci NOT NULL,
  PRIMARY KEY (`ClienteCodigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;

/*Data for the table `cliente` */

/*Table structure for table `conta` */

DROP TABLE IF EXISTS `conta`;

CREATE TABLE `conta` (
  `AgenciaCodigo` smallint(4) NOT NULL,
  `ClienteCodigo` smallint(4) NOT NULL,
  `ContaNumero` smallint(4) NOT NULL,
  `ContaDigito` tinyint(4) NOT NULL,
  PRIMARY KEY (`ContaNumero`),
  KEY `conta_agencia` (`AgenciaCodigo`),
  KEY `conta_cliente` (`ClienteCodigo`),
  CONSTRAINT `conta_cliente` FOREIGN KEY (`ClienteCodigo`) REFERENCES `cliente` (`ClienteCodigo`),
  CONSTRAINT `conta_agencia` FOREIGN KEY (`AgenciaCodigo`) REFERENCES `agencia` (`AgenciaCodigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;

/*Data for the table `conta` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
