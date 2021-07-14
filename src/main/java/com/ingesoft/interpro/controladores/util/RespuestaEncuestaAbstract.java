/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores.util;

import java.io.Serializable;
import javax.persistence.Transient;

/**
 *
 * @author anderson
 * @param <EncuestaPersonalizada>
 * @param <PreguntaEncuesta>
 */
public abstract class RespuestaEncuestaAbstract <
            EncuestaPersonalizada, 
            PreguntaEncuesta>
        implements Serializable {
    
    @Transient
    private boolean estaRespondida;
    
    public boolean isRespondida() {
        return estaRespondida;
    }

    public void setEstaRespondida(boolean estaRespondida) {
        this.estaRespondida = estaRespondida;
    }
    
    public void responder() {
        setEstaRespondida(true);
    }
    
    public abstract void inicializar(EncuestaPersonalizada encuestaPersonalizada, 
            PreguntaEncuesta preguntaEncuesta);
    
}
