/**
 * Author:  anderson
 * Created: 7/04/2021
 */

ALTER TABLE interpro.RespuestaEstilo DROP COLUMN idRespuestaEstilo;
ALTER TABLE interpro.RespuestaEstilo ADD CONSTRAINT RespuestaEstilo_PK PRIMARY KEY (Encuesta_idEncuesta,idpregunta_estilos);
ALTER TABLE interpro.pregunta_estilos_aprendizaje_FS MODIFY COLUMN ordenint INT DEFAULT NULL NULL;
ALTER TABLE interpro.pregunta_estilos_aprendizaje_FS CHANGE ordenint orden int(11) DEFAULT NULL NULL;
ALTER TABLE interpro.TipoEstilo MODIFY COLUMN definicion varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL NULL;
