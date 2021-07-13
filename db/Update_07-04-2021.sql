/**
 * Author:  anderson
 * Created: 7/04/2021
 */

CREATE TABLE `PreguntaEstilosAprendizaje` (
  `id` int(11) NOT NULL,
  `enunciado` varchar(150) DEFAULT NULL,
  `urlimagen` varchar(45) DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `TipoEstilo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(12) CHARACTER SET utf8mb4 DEFAULT NULL,
  `definicion` varchar(500) CHARACTER SET utf8mb4 DEFAULT NULL,
  `color` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;


CREATE TABLE `TipoEstilo_Pregunta` (
  `indice` char(1) NOT NULL,
  `opcion` char(150) NOT NULL,
  `idTipoEstilo` int(11) NOT NULL,
  `idPreguntaEstilosAprendizaje` int(11) NOT NULL,
  PRIMARY KEY (`idTipoEstilo`,`idPreguntaEstilosAprendizaje`),
  KEY `fk_TipoEstilo_Pregunta_TipoEstilo1_idx` (`idTipoEstilo`),
  KEY `fk_TipoEstilo_Pregunta_pregunta_estilos_aprendizaje_FS1_idx` (`idPreguntaEstilosAprendizaje`),
  KEY `fk_TipoEstilo_Pregunta_PreguntaEstilosAprendizaje_idx` (`idPreguntaEstilosAprendizaje`),
  CONSTRAINT `fk_TipoEstilo_Pregunta_TipoEstilo1` FOREIGN KEY (`idTipoEstilo`) REFERENCES `TipoEstilo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_TipoEstilo_Pregunta_pregunta_estilos_aprendizaje_FS1` FOREIGN KEY (`idPreguntaEstilosAprendizaje`) REFERENCES `PreguntaEstilosAprendizaje` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `RespuestaEstilo` (
  `idEncuestaEstilosAprendizaje` int(11) NOT NULL,
  `respuesta` char(1) DEFAULT NULL,
  `idPreguntaEstilosAprendizaje` int(11) NOT NULL,
  PRIMARY KEY (`idEncuestaEstilosAprendizaje`,`idPreguntaEstilosAprendizaje`),
  KEY `fk_RespuestaEstilo_Encuesta1_idx` (`idEncuestaEstilosAprendizaje`),
  KEY `fk_RespuestaEstilo_pregunta_estilos_aprendizaje_FS1_idx` (`idPreguntaEstilosAprendizaje`),
  CONSTRAINT `fk_RespuestaEstilo_EncuestaEstilosAprendizaje1` FOREIGN KEY (`idEncuestaEstilosAprendizaje`) REFERENCES `EncuestaEstilosAprendizaje` (`idEncuesta`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_RespuestaEstilo_PreguntaEstilosAprendizaje` FOREIGN KEY (`idPreguntaEstilosAprendizaje`) REFERENCES `PreguntaEstilosAprendizaje` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `RespuestaPorEstilo` (
  `idTipoEstilo` int(11) NOT NULL,
  `idEncuestaEstilosAprendizaje` int(11) NOT NULL,
  `respuesta` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`idTipoEstilo`,`idEncuestaEstilosAprendizaje`),
  KEY `fk_RespuestaPorEstilo_EncuestaEstilosAprendizaje1_idx` (`idEncuestaEstilosAprendizaje`),
  CONSTRAINT `fk_RespuestaPorEstilo_EncuestaEstilosAprendizaje1` FOREIGN KEY (`idEncuestaEstilosAprendizaje`) REFERENCES `EncuestaEstilosAprendizaje` (`idEncuesta`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_RespuestaPorEstilo_TipoEstilo1` FOREIGN KEY (`idTipoEstilo`) REFERENCES `TipoEstilo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `EncuestaEstilosAprendizaje` (
  `idEncuesta` int(11) NOT NULL AUTO_INCREMENT,
  `fechaCreacion` datetime DEFAULT NULL,
  `fechaFinalizada` datetime DEFAULT NULL,
  `estado` enum('PENDINENTE','FINALIZADA') DEFAULT NULL,
  PRIMARY KEY (`idEncuesta`),
  UNIQUE KEY `idEncuesta_UNIQUE` (`idEncuesta`),
  CONSTRAINT `fk_EncuestaEstilosAprendizaje_Encuesta1` FOREIGN KEY (`idEncuesta`) REFERENCES `Encuesta` (`idEncuesta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1089 DEFAULT CHARSET=utf8;