/**
 * Author:  anderson
 * Created: 7/06/2021
 */

SELECT * FROM Usuario;

ALTER TABLE `interpro`.`Persona` 
CHANGE COLUMN `tipo` `tipo` VARCHAR(45) NULL DEFAULT NULL COMMENT 'admin\nestudiante\ndocente o representante' ;

ALTER TABLE `interpro`.`RespuestaPersonalidad` 
DROP FOREIGN KEY `fk_Respuesta_Encuesta1`;
ALTER TABLE `interpro`.`RespuestaPersonalidad` 
CHANGE COLUMN `idEncuesta` `idEncuesta` INT(11) NOT NULL FIRST,
DROP INDEX `fk_Respuesta_Encuesta1_idx` ;

CREATE TABLE IF NOT EXISTS `interpro`.`EncuestaPersonalidad` (
  `idEncuesta` INT(11) NOT NULL AUTO_INCREMENT,
  `fechaCreacion` DATETIME NULL DEFAULT NULL,
  `fechaFinalizada` DATETIME NULL DEFAULT NULL,
  `estado` ENUM('PENDINENTE', 'FINALIZADA') NULL DEFAULT NULL,
  `personalidad` VARCHAR(5) NULL DEFAULT NULL,
  PRIMARY KEY (`idEncuesta`),
  UNIQUE INDEX `idEncuesta_UNIQUE` (`idEncuesta` ASC),
  CONSTRAINT `fk_EncuestaPersonalidad_Encuesta1`
    FOREIGN KEY (`idEncuesta`)
    REFERENCES `interpro`.`Encuesta` (`idEncuesta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

INSERT INTO interpro.EncuestaPersonalidad (SELECT  e.idEncuesta , e.fecha, e.fecha, 
	CASE 
		WHEN COUNT(rp.respuesta) = 32  THEN 'FINALIZADA'
		ELSE 'PENDINENTE'
	END,
	e.personalidad
FROM interpro.Encuesta e 
LEFT JOIN interpro.RespuestaPersonalidad rp on rp.idEncuesta = e.idEncuesta 
GROUP BY e.idEncuesta);

ALTER TABLE `interpro`.`RespuestaPersonalidad` 
ADD CONSTRAINT `fk_RespuestaPersonalidad_EncuestaPersonalidad1`
  FOREIGN KEY (`idEncuesta`)
  REFERENCES `interpro`.`EncuestaPersonalidad` (`idEncuesta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `interpro`.`Encuesta` 
DROP COLUMN `personalidad`,
ADD COLUMN `fechaFinalizada` VARCHAR(45) NULL DEFAULT NULL AFTER `estado`,
CHANGE COLUMN `fecha` `fechaCreacion` DATETIME NOT NULL ;


ALTER TABLE `interpro`.`Usuario` 
INSERT_METHOD = NO ;

ALTER TABLE `interpro`.`AreaEncuesta` 
CHANGE COLUMN `posicion` `posicion` TINYINT(4) NOT NULL COMMENT 'posicion de 1 a |lista|\n1 la que mas le gusta\n|lista| l que menos le gsta' ;

ALTER TABLE `interpro`.`TipoEleccionMateria` 
COMMENT = 'Tipo eleccion referente a: \n-menor gusto\n-mayor gusto\n-mayor nota' ;


ALTER TABLE `interpro`.`RespuestaPorPersonalidad` 
DROP FOREIGN KEY `fk_Encuesta_has_TipoPersonalidad_Encuesta1`;
/*
ALTER TABLE `interpro`.`RespuestaPorPersonalidad` 
DROP INDEX `fk_Encuesta_has_TipoPersonalidad_Encuesta1_idx` ;*/

ALTER TABLE `interpro`.`RespuestaPorPersonalidad` 
ADD CONSTRAINT `fk_RespuestaPorPersonalidad_EncuestaPersonalidad1`
  FOREIGN KEY (`idEncuesta`)
  REFERENCES `interpro`.`EncuestaPersonalidad` (`idEncuesta`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
 
ALTER TABLE `interpro`.`ConfigMineria` 
DROP FOREIGN KEY `FK_ConfigMineria_idAreaProfesional`;

ALTER TABLE `interpro`.`ConfigMineria` 
ADD CONSTRAINT `fk_ConfigMineria_AreaProfesional1`
  FOREIGN KEY (`idAreaProfesional`)
  REFERENCES `interpro`.`AreaProfesional` (`idAreaProfesional`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
 
ALTER TABLE `interpro`.`ConfigMineria` 
ADD INDEX `fk_ConfigMineria_AreaProfesional1_idx` (`idAreaProfesional` ASC);

RENAME TABLE interpro.pregunta_estilos_aprendizaje_FS TO interpro.PreguntaEstilosAprendizaje;

ALTER TABLE interpro.PreguntaEstilosAprendizaje CHANGE idpregunta_estilos id int(11) NOT NULL;

ALTER TABLE interpro.TipoEstilo CHANGE idTipoEstilo id int(11) auto_increment NOT NULL;

ALTER TABLE interpro.RespuestaEstilo 
CHANGE idpregunta_estilos idPreguntaEstilosAprendizaje int(11) NOT NULL;

ALTER TABLE `interpro`.`RespuestaEstilo` 
CHANGE COLUMN `Encuesta_idEncuesta` `idEncuestaEstilosAprendizaje` INT(11) NOT NULL FIRST;

ALTER TABLE `interpro`.`RespuestaEstilo` 
DROP FOREIGN KEY `fk_RespuestaEstilo_pregunta_estilos_aprendizaje_FS1`,
DROP FOREIGN KEY `fk_RespuestaEstilo_Encuesta1`;

ALTER TABLE `interpro`.`RespuestaEstilo` 
ADD CONSTRAINT `fk_RespuestaEstilo_PreguntaEstilosAprendizaje`
  FOREIGN KEY (`idPreguntaEstilosAprendizaje`)
  REFERENCES `interpro`.`PreguntaEstilosAprendizaje` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `interpro`.`TipoEstilo_Pregunta` 
DROP FOREIGN KEY `fk_TipoEstilo_Pregunta_TipoEstilo1`,
DROP FOREIGN KEY `fk_TipoEstilo_Pregunta_pregunta_estilos_aprendizaje_FS1`;

ALTER TABLE interpro.TipoEstilo_Pregunta CHANGE idpregunta_estilos idPreguntaEstilosAprendizaje int(11) NOT NULL;

ALTER TABLE `interpro`.`TipoEstilo_Pregunta` 
ADD INDEX `fk_TipoEstilo_Pregunta_PreguntaEstilosAprendizaje_idx` (`idPreguntaEstilosAprendizaje` ASC);

ALTER TABLE `interpro`.`TipoEstilo_Pregunta` 
ADD CONSTRAINT `fk_TipoEstilo_Pregunta_TipoEstilo1`
  FOREIGN KEY (`idTipoEstilo`)
  REFERENCES `interpro`.`TipoEstilo` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_TipoEstilo_Pregunta_pregunta_estilos_aprendizaje_FS1`
  FOREIGN KEY (`idPreguntaEstilosAprendizaje`)
  REFERENCES `interpro`.`PreguntaEstilosAprendizaje` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE IF NOT EXISTS `interpro`.`EncuestaInteligenciasMultiples` (
  `idEncuesta` INT(11) NOT NULL AUTO_INCREMENT,
  `fechaCreacion` DATETIME NULL DEFAULT NULL,
  `fechaFinalizada` DATETIME NULL DEFAULT NULL,
  `estado` ENUM('PENDINENTE', 'FINALIZADA') NULL DEFAULT 'PENDINENTE',
  PRIMARY KEY (`idEncuesta`),
  UNIQUE INDEX `id_UNIQUE` (`idEncuesta` ASC),
  CONSTRAINT `fk_EncuestaInteligenciasMultiples_Encuesta1`
    FOREIGN KEY (`idEncuesta`)
    REFERENCES `interpro`.`Encuesta` (`idEncuesta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `interpro`.`TipoInteligenciasMultiples` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `interpro`.`RespuestaPorInteligencia` (
  `idTipoInteligenciasMultiples` INT(11) NOT NULL,
  `idEncuestaInteligenciasMultiples` INT(11) NOT NULL,
  `respuesta` TINYINT(4) NOT NULL,
  PRIMARY KEY (`idTipoInteligenciasMultiples`, `idEncuestaInteligenciasMultiples`),
  INDEX `fk_RespuestaPosInteligencia_EncuestaInteligenciasMultiples1_idx` (`idEncuestaInteligenciasMultiples` ASC),
  UNIQUE INDEX `idTipoInteligenciasMultiples_UNIQUE` (`idTipoInteligenciasMultiples` ASC),
  UNIQUE INDEX `idEncuesta_UNIQUE` (`idEncuestaInteligenciasMultiples` ASC),
  CONSTRAINT `fk_RespuestaPosInteligencia_TipoInteligenciasMultiples1`
    FOREIGN KEY (`idTipoInteligenciasMultiples`)
    REFERENCES `interpro`.`TipoInteligenciasMultiples` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RespuestaPosInteligencia_EncuestaInteligenciasMultiples1`
    FOREIGN KEY (`idEncuestaInteligenciasMultiples`)
    REFERENCES `interpro`.`EncuestaInteligenciasMultiples` (`idEncuesta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `interpro`.`PreguntaInteligenciasMultiples` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `idTipoInteligenciasMultiples` INT(11) NOT NULL,
  `enunciado` VARCHAR(150) NOT NULL,
  `orden` TINYINT(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `fk_PreguntaInteligenciasMultiples_TipoInteligenciasMultiple_idx` (`idTipoInteligenciasMultiples` ASC) ,
  CONSTRAINT `fk_PreguntaInteligenciasMultiples_TipoInteligenciasMultiples1`
    FOREIGN KEY (`idTipoInteligenciasMultiples`)
    REFERENCES `interpro`.`TipoInteligenciasMultiples` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `interpro`.`RespuestaInteligenciasMultiples` (
  `idEncuestaInteligenciasMultiples` INT(11) NOT NULL,
  `idPreguntaInteligenciasMultiples` INT(11) NOT NULL,
  `respuesta` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`idEncuestaInteligenciasMultiples`, `idPreguntaInteligenciasMultiples`),
  INDEX `fk_RespuestaInteligenciasMultiples_EncuestaInteligenciasMul_idx` (`idEncuestaInteligenciasMultiples` ASC) ,
  INDEX `fk_RespuestaInteligenciasMultiples_PreguntaInteligenciasMul_idx` (`idPreguntaInteligenciasMultiples` ASC) ,
  CONSTRAINT `fk_RespuestaInteligenciasMultiples_EncuestaInteligenciasMulti1`
    FOREIGN KEY (`idEncuestaInteligenciasMultiples`)
    REFERENCES `interpro`.`EncuestaInteligenciasMultiples` (`idEncuesta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RespuestaInteligenciasMultiples_PreguntaInteligenciasMulti1`
    FOREIGN KEY (`idPreguntaInteligenciasMultiples`)
    REFERENCES `interpro`.`PreguntaInteligenciasMultiples` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `interpro`.`EncuestaEstilosAprendizaje` (
  `idEncuesta` INT(11) NOT NULL AUTO_INCREMENT,
  `fechaCreacion` DATETIME NULL DEFAULT NULL,
  `fechaFinalizada` DATETIME NULL DEFAULT NULL,
  `estado` ENUM('PENDINENTE', 'FINALIZADA') NULL DEFAULT NULL,
  PRIMARY KEY (`idEncuesta`),
  UNIQUE INDEX `idEncuesta_UNIQUE` (`idEncuesta` ASC) ,
  CONSTRAINT `fk_EncuestaEstilosAprendizaje_Encuesta1`
    FOREIGN KEY (`idEncuesta`)
    REFERENCES `interpro`.`Encuesta` (`idEncuesta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_EncuestaEstilosAprendizaje_RespuestaEstilo1`
    FOREIGN KEY (`idEncuesta`)
    REFERENCES `interpro`.`RespuestaEstilo` (`idEncuestaEstilosAprendizaje`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `interpro`.`RespuestaPorEstilo` (
  `idTipoEstilo` INT(11) NOT NULL,
  `idEncuestaEstilosAprendizaje` INT(11) NOT NULL,
  `respuesta` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`idTipoEstilo`, `idEncuestaEstilosAprendizaje`),
  INDEX `fk_RespuestaPorEstilo_EncuestaEstilosAprendizaje1_idx` (`idEncuestaEstilosAprendizaje` ASC),
  CONSTRAINT `fk_RespuestaPorEstilo_TipoEstilo1`
    FOREIGN KEY (`idTipoEstilo`)
    REFERENCES `interpro`.`TipoEstilo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RespuestaPorEstilo_EncuestaEstilosAprendizaje1`
    FOREIGN KEY (`idEncuestaEstilosAprendizaje`)
    REFERENCES `interpro`.`EncuestaEstilosAprendizaje` (`idEncuesta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

ALTER TABLE `interpro`.`Persona` 
CHANGE COLUMN `tipo` `tipo` VARCHAR(45) NULL DEFAULT NULL COMMENT 'admin\nestudiante\ndocente o representante';

ALTER TABLE `interpro`.`RespuestaPorPersonalidad` 
DROP INDEX `fk_Encuesta_has_TipoPersonalidad_Encuesta1_idx` ;

ALTER TABLE `interpro`.`TipoEstilo` 
CHARACTER SET = utf8 , COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `interpro`.`GestionEncuestas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descripcion` VARCHAR(100) NOT NULL,
  `identificador` ENUM('personalidad', 'es_aprendizaje', 'int_multiple', 'ambientes') NOT NULL COMMENT '\'personalidad\', \'es_aprendizaje\', \'int_multiple\', \'ambientes\'',
  `estado` ENUM('ACTIVO', 'INACTIVO') NOT NULL DEFAULT 'INACTIVO' COMMENT '\'ACTIVO\', \'INACTIVO\'',
  `fechaCreacion` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;



