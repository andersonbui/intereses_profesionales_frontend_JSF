/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  debian
 * Created: 29/10/2021
 */

CREATE TABLE IF NOT EXISTS `interpro`.`EncuestaChaside` (
  `idEncuesta` INT NOT NULL,
  `fechaCreacion` DATETIME NULL,
  `fechaFinalizada` DATETIME NULL,
  `estado` ENUM('PENDINENTE', 'FINALIZADA') NULL,
  UNIQUE INDEX `idEncuesta_UNIQUE` (`idEncuesta` ASC),
  PRIMARY KEY (`idEncuesta`),
  CONSTRAINT `fk_EncuestaEstilosAprendizaje_Encuesta10`
    FOREIGN KEY (`idEncuesta`)
    REFERENCES `interpro`.`Encuesta` (`idEncuesta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `interpro`.`TipoChaside` (
  `idTipoChaside` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(1) NOT NULL,
  `profesion` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`idTipoChaside`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `interpro`.`ClaseChaside` (
  `idClaseChaside` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`idClaseChaside`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `interpro`.`PreguntaChaside` (
  `idPreguntaChaside` INT NOT NULL,
  `enunciado` VARCHAR(180) NOT NULL,
  `idTipoChaside` INT NOT NULL,
  `idClaseChaside` INT NOT NULL,
  PRIMARY KEY (`idPreguntaChaside`),
  INDEX `fk_PreguntaChaside_TipoChaside1_idx` (`idTipoChaside` ASC),
  INDEX `fk_PreguntaChaside_ClaseChaside1_idx` (`idClaseChaside` ASC),
  CONSTRAINT `fk_PreguntaChaside_TipoChaside1`
    FOREIGN KEY (`idTipoChaside`)
    REFERENCES `interpro`.`TipoChaside` (`idTipoChaside`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PreguntaChaside_ClaseChaside1`
    FOREIGN KEY (`idClaseChaside`)
    REFERENCES `interpro`.`ClaseChaside` (`idClaseChaside`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `interpro`.`RespuestaChaside` (
  `idEncuestaChaside` INT NOT NULL,
  `idPreguntaChaside` INT NOT NULL,
  `respuesta` TINYINT(1) NOT NULL,
  `fecha` DATETIME NOT NULL,
  PRIMARY KEY (`idEncuestaChaside`, `idPreguntaChaside`),
  INDEX `fk_RespuestaChaside_EncuestaChaside1_idx` (`idEncuestaChaside` ASC),
  INDEX `fk_RespuestaChaside_PreguntaChaside1_idx` (`idPreguntaChaside` ASC),
  CONSTRAINT `fk_RespuestaChaside_EncuestaChaside1`
    FOREIGN KEY (`idEncuestaChaside`)
    REFERENCES `interpro`.`EncuestaChaside` (`idEncuesta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RespuestaChaside_PreguntaChaside1`
    FOREIGN KEY (`idPreguntaChaside`)
    REFERENCES `interpro`.`PreguntaChaside` (`idPreguntaChaside`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `interpro`.`TipoClaseChaside` (
  `idTipoChaside` INT NOT NULL,
  `idClaseChaside` INT NOT NULL,
  `descripcion` VARCHAR(80) NOT NULL,
  INDEX `fk_TipoClaseChaside_TipoChaside1_idx` (`idTipoChaside` ASC),
  PRIMARY KEY (`idClaseChaside`, `idTipoChaside`),
  CONSTRAINT `fk_TipoClaseChaside_TipoChaside1`
    FOREIGN KEY (`idTipoChaside`)
    REFERENCES `interpro`.`TipoChaside` (`idTipoChaside`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TipoClaseChaside_ClaseChaside1`
    FOREIGN KEY (`idClaseChaside`)
    REFERENCES `interpro`.`ClaseChaside` (`idClaseChaside`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `interpro`.`ResultadoChaside` (
  `idClaseChaside` INT NOT NULL,
  `idTipoChaside` INT NOT NULL,
  `idEncuesta` INT NOT NULL,
  `resultado` TINYINT NULL,
  PRIMARY KEY (`idClaseChaside`, `idTipoChaside`, `idEncuesta`),
  INDEX `fk_ResultadoChaside_EncuestaChaside1_idx` (`idEncuesta` ASC),
  CONSTRAINT `fk_ResultadoChaside_TipoClaseChaside1`
    FOREIGN KEY (`idClaseChaside` , `idTipoChaside`)
    REFERENCES `interpro`.`TipoClaseChaside` (`idClaseChaside` , `idTipoChaside`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ResultadoChaside_EncuestaChaside1`
    FOREIGN KEY (`idEncuesta`)
    REFERENCES `interpro`.`EncuestaChaside` (`idEncuesta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;