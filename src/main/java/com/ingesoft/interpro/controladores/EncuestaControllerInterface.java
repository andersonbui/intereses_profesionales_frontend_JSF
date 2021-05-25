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
    void reiniciar();
    void prepararEncuesta(Encuesta encuesta);
    String getRuta();
}
