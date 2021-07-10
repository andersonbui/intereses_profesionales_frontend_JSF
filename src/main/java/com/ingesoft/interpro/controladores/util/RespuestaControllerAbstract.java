/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores.util;

import java.util.List;

/**
 *
 * @author anderson
 * @param <RespuestaEncuesta>
 * @param <RespuestaEncuestaFacade>
 * @param <EncuestaPersonalizada>
 */
public abstract class RespuestaControllerAbstract<
        RespuestaEncuesta, 
        RespuestaEncuestaFacade, 
        EncuestaPersonalizada>  extends ControllerAbstract<RespuestaEncuesta, RespuestaEncuestaFacade> {
    
    public abstract List<RespuestaEncuesta> obtenerTodosPorEncuesta(EncuestaPersonalizada encuestaPersonalizada);
    
    public List<RespuestaEncuesta> actualizarTodasRespuestas(List<RespuestaEncuesta> respuestas) {
        for (RespuestaEncuesta item : respuestas) {
            this.getFacade().edit(item);
        }
        return respuestas;
    }
    
}
