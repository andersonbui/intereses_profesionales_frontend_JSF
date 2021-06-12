/**
 * Author:  anderson
 * Created: 7/04/2021
 */

CREATE TABLE IF NOT EXISTS `interpro`.`pregunta_estilos_aprendizaje_FS` (
  `id` INT(11) NOT NULL,
  `enunciado` VARCHAR(150) NULL DEFAULT NULL,
  `urlimagen` VARCHAR(45) NULL DEFAULT NULL,
  `ordenint` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8


ALTER TABLE interpro.RespuestaEstilo DROP COLUMN idRespuestaEstilo;
ALTER TABLE interpro.RespuestaEstilo ADD CONSTRAINT RespuestaEstilo_PK PRIMARY KEY (Encuesta_idEncuesta,idpregunta_estilos);
ALTER TABLE interpro.pregunta_estilos_aprendizaje_FS MODIFY COLUMN ordenint INT DEFAULT NULL NULL;
ALTER TABLE interpro.pregunta_estilos_aprendizaje_FS CHANGE ordenint orden int(11) DEFAULT NULL NULL;
ALTER TABLE interpro.TipoEstilo MODIFY COLUMN definicion varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL NULL;
