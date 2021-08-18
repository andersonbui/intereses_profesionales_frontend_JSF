/**
 * Author:  anderson
 * Created: 7/06/2021
 */

SELECT * FROM Usuario;

ALTER TABLE `Persona` 
CHANGE COLUMN `tipo` `tipo` VARCHAR(45) NULL DEFAULT NULL COMMENT 'admin\nestudiante\ndocente o representante' ;

ALTER TABLE `RespuestaPersonalidad` 
DROP FOREIGN KEY `fk_Respuesta_Encuesta1`;
ALTER TABLE `RespuestaPersonalidad` 
CHANGE COLUMN `idEncuesta` `idEncuesta` INT(11) NOT NULL FIRST,
DROP INDEX `fk_Respuesta_Encuesta1_idx` ;

CREATE TABLE IF NOT EXISTS `EncuestaPersonalidad` (
  `idEncuesta` INT(11) NOT NULL,
  `fechaCreacion` DATETIME NULL DEFAULT NULL,
  `fechaFinalizada` DATETIME NULL DEFAULT NULL,
  `estado` ENUM('PENDINENTE', 'FINALIZADA') NULL DEFAULT NULL,
  `personalidad` VARCHAR(5) NULL DEFAULT NULL,
  PRIMARY KEY (`idEncuesta`),
  UNIQUE INDEX `idEncuesta_UNIQUE` (`idEncuesta` ASC),
  CONSTRAINT `fk_EncuestaPersonalidad_Encuesta1`
    FOREIGN KEY (`idEncuesta`)
    REFERENCES `Encuesta` (`idEncuesta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

INSERT INTO EncuestaPersonalidad (SELECT  e.idEncuesta , e.fecha, e.fecha, 
    CASE 
        WHEN COUNT(rp.respuesta) = 32  THEN 'FINALIZADA'
        ELSE 'PENDINENTE'
    END,
    e.personalidad
FROM Encuesta e 
LEFT JOIN RespuestaPersonalidad rp on rp.idEncuesta = e.idEncuesta 
GROUP BY e.idEncuesta);

/**1**/
ALTER TABLE `RespuestaPersonalidad` 
ADD CONSTRAINT `fk_RespuestaPersonalidad_EncuestaPersonalidad1`
  FOREIGN KEY (`idEncuesta`)
  REFERENCES `EncuestaPersonalidad` (`idEncuesta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `Encuesta` 
DROP COLUMN `personalidad`,
ADD COLUMN `fechaFinalizada` DATETIME NULL DEFAULT NULL AFTER `estado`,
CHANGE COLUMN `fecha` `fechaCreacion` DATETIME NOT NULL ;


ALTER TABLE `Usuario` 
INSERT_METHOD = NO ;

ALTER TABLE `AreaEncuesta` 
CHANGE COLUMN `posicion` `posicion` TINYINT(4) NOT NULL COMMENT 'posicion de 1 a |lista|\n1 la que mas le gusta\n|lista| l que menos le gsta' ;

ALTER TABLE `TipoEleccionMateria` 
COMMENT = 'Tipo eleccion referente a: \n-menor gusto\n-mayor gusto\n-mayor nota' ;


ALTER TABLE `RespuestaPorPersonalidad` 
DROP FOREIGN KEY `fk_Encuesta_has_TipoPersonalidad_Encuesta1`;
/*
ALTER TABLE `RespuestaPorPersonalidad` 
DROP INDEX `fk_Encuesta_has_TipoPersonalidad_Encuesta1_idx` ;*/

/**2**/
ALTER TABLE `RespuestaPorPersonalidad` 
ADD CONSTRAINT `fk_RespuestaPorPersonalidad_EncuestaPersonalidad1`
  FOREIGN KEY (`idEncuesta`)
  REFERENCES `EncuestaPersonalidad` (`idEncuesta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

 /*
ALTER TABLE `ConfigMineria` 
DROP FOREIGN KEY `FK_ConfigMineria_idAreaProfesional`;

ALTER TABLE `ConfigMineria` 
ADD CONSTRAINT `fk_ConfigMineria_AreaProfesional1`
  FOREIGN KEY (`idAreaProfesional`)
  REFERENCES `AreaProfesional` (`idAreaProfesional`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
 
ALTER TABLE `ConfigMineria` 
ADD INDEX `fk_ConfigMineria_AreaProfesional1_idx` (`idAreaProfesional` ASC);
*/

CREATE TABLE IF NOT EXISTS `EncuestaInteligenciasMultiples` (
  `idEncuesta` INT(11) NOT NULL,
  `fechaCreacion` DATETIME NULL DEFAULT NULL,
  `fechaFinalizada` DATETIME NULL DEFAULT NULL,
  `estado` ENUM('PENDINENTE', 'FINALIZADA') NULL DEFAULT 'PENDINENTE',
  PRIMARY KEY (`idEncuesta`),
  UNIQUE INDEX `id_UNIQUE` (`idEncuesta` ASC),
  CONSTRAINT `fk_EncuestaInteligenciasMultiples_Encuesta1`
    FOREIGN KEY (`idEncuesta`)
    REFERENCES `Encuesta` (`idEncuesta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `TipoInteligenciasMultiples` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `RespuestaPorInteligencia` (
  `idTipoInteligenciasMultiples` INT(11) NOT NULL,
  `idEncuestaInteligenciasMultiples` INT(11) NOT NULL,
  `respuesta` TINYINT(4) NOT NULL,
  PRIMARY KEY (`idTipoInteligenciasMultiples`, `idEncuestaInteligenciasMultiples`),
  INDEX `fk_RespuestaPosInteligencia_EncuestaInteligenciasMultiples1_idx` (`idEncuestaInteligenciasMultiples` ASC),
  CONSTRAINT `fk_RespuestaPosInteligencia_TipoInteligenciasMultiples1`
    FOREIGN KEY (`idTipoInteligenciasMultiples`)
    REFERENCES `TipoInteligenciasMultiples` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RespuestaPosInteligencia_EncuestaInteligenciasMultiples1`
    FOREIGN KEY (`idEncuestaInteligenciasMultiples`)
    REFERENCES `EncuestaInteligenciasMultiples` (`idEncuesta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `PreguntaInteligenciasMultiples` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `idTipoInteligenciasMultiples` INT(11) NOT NULL,
  `enunciado` VARCHAR(150) NOT NULL,
  `orden` TINYINT(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `fk_PreguntaInteligenciasMultiples_TipoInteligenciasMultiple_idx` (`idTipoInteligenciasMultiples` ASC) ,
  CONSTRAINT `fk_PreguntaInteligenciasMultiples_TipoInteligenciasMultiples1`
    FOREIGN KEY (`idTipoInteligenciasMultiples`)
    REFERENCES `TipoInteligenciasMultiples` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `RespuestaInteligenciasMultiples` (
  `idEncuestaInteligenciasMultiples` INT(11) NOT NULL,
  `idPreguntaInteligenciasMultiples` INT(11) NOT NULL,
  `respuesta` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`idEncuestaInteligenciasMultiples`, `idPreguntaInteligenciasMultiples`),
  INDEX `fk_RespuestaInteligenciasMultiples_EncuestaInteligenciasMul_idx` (`idEncuestaInteligenciasMultiples` ASC) ,
  INDEX `fk_RespuestaInteligenciasMultiples_PreguntaInteligenciasMul_idx` (`idPreguntaInteligenciasMultiples` ASC) ,
  CONSTRAINT `fk_RespuestaInteligenciasMultiples_EncuestaInteligenciasMulti1`
    FOREIGN KEY (`idEncuestaInteligenciasMultiples`)
    REFERENCES `EncuestaInteligenciasMultiples` (`idEncuesta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RespuestaInteligenciasMultiples_PreguntaInteligenciasMulti1`
    FOREIGN KEY (`idPreguntaInteligenciasMultiples`)
    REFERENCES `PreguntaInteligenciasMultiples` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `EncuestaEstilosAprendizaje` (
  `idEncuesta` INT(11) NOT NULL,
  `fechaCreacion` DATETIME NULL DEFAULT NULL,
  `fechaFinalizada` DATETIME NULL DEFAULT NULL,
  `estado` ENUM('PENDINENTE', 'FINALIZADA') NULL DEFAULT NULL,
  PRIMARY KEY (`idEncuesta`),
  UNIQUE INDEX `idEncuesta_UNIQUE` (`idEncuesta` ASC) ,
  CONSTRAINT `fk_EncuestaEstilosAprendizaje_Encuesta1`
    FOREIGN KEY (`idEncuesta`)
    REFERENCES `Encuesta` (`idEncuesta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `RespuestaPorEstilo` (
  `idTipoEstilo` INT(11) NOT NULL,
  `idEncuestaEstilosAprendizaje` INT(11) NOT NULL,
  `respuesta` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`idTipoEstilo`, `idEncuestaEstilosAprendizaje`),
  INDEX `fk_RespuestaPorEstilo_EncuestaEstilosAprendizaje1_idx` (`idEncuestaEstilosAprendizaje` ASC),
  CONSTRAINT `fk_RespuestaPorEstilo_TipoEstilo1`
    FOREIGN KEY (`idTipoEstilo`)
    REFERENCES `TipoEstilo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RespuestaPorEstilo_EncuestaEstilosAprendizaje1`
    FOREIGN KEY (`idEncuestaEstilosAprendizaje`)
    REFERENCES `EncuestaEstilosAprendizaje` (`idEncuesta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

ALTER TABLE `Persona` 
CHANGE COLUMN `tipo` `tipo` VARCHAR(45) NULL DEFAULT NULL COMMENT 'admin\nestudiante\ndocente o representante';

ALTER TABLE `RespuestaPorPersonalidad` 
DROP INDEX `fk_Encuesta_has_TipoPersonalidad_Encuesta1_idx` ;

ALTER TABLE `TipoEstilo` 
CHARACTER SET = utf8 , COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `GestionEncuestas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descripcion` VARCHAR(100) NOT NULL,
  `identificador` ENUM('personalidad', 'es_aprendizaje', 'int_multiple', 'ambientes') NOT NULL COMMENT '\'personalidad\', \'es_aprendizaje\', \'int_multiple\', \'ambientes\'',
  `estado` ENUM('ACTIVO', 'INACTIVO') NOT NULL DEFAULT 'INACTIVO' COMMENT '\'ACTIVO\', \'INACTIVO\'',
  `fechaCreacion` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;



