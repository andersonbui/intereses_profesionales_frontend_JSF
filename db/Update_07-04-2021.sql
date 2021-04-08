/**
 * Author:  anderson
 * Created: 7/04/2021
 */

ALTER TABLE interpro.RespuestaEstilo DROP COLUMN idRespuestaEstilo;
ALTER TABLE interpro.RespuestaEstilo ADD CONSTRAINT RespuestaEstilo_PK PRIMARY KEY (Encuesta_idEncuesta,idpregunta_estilos);
