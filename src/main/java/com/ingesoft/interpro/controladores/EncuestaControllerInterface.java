/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Encuesta;

/**
 *
 * @author anderson
 */
public interface EncuestaControllerInterface {
    
    /**
     * realiza algun reinicio de parametros de esta sub-encuesta
     */
    void reiniciar();
    
    /**
     * Preparar la sub-encuesta para se realizada
     * @param encuesta 
     */
    void prepararEncuesta(Encuesta encuesta);
    
    /**
     * Obtiene la ruta del archivo vista de esta sub-encuesta
     * @return 
     */
    String getRuta();
    
    /**
     * Obtener el nombre de esta sub-encuesta
     * @return 
     */
    String getName();
    
    /**
     * verificar si una Encuesta determinada tiene esta sub-encuesta pendiente de realizar
     * @param encuesta
     * @return 
     */
    public boolean isPending(Encuesta encuesta);
    
}
