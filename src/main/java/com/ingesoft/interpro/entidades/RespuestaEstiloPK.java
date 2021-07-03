/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author debian
 */
@Embeddable
public class RespuestaEstiloPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idEncuestaEstilosAprendizaje")
    private int idEncuestaEstilosAprendizaje;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPreguntaEstilosAprendizaje")
    private int idPreguntaEstilosAprendizaje;

    public RespuestaEstiloPK() {
    }

    public RespuestaEstiloPK(int idpregunta_estilos, int idEncuestaEstilosAprendizaje) {
        this.idPreguntaEstilosAprendizaje = idpregunta_estilos;
        this.idEncuestaEstilosAprendizaje = idEncuestaEstilosAprendizaje;
    }

    public int getIdEncuestaEstilosAprendizaje() {
        return idEncuestaEstilosAprendizaje;
    }

    public void setIdEncuestaEstilosAprendizaje(int idEncuestaEstilosAprendizaje) {
        this.idEncuestaEstilosAprendizaje = idEncuestaEstilosAprendizaje;
    }

    public int getIdPreguntaEstilosAprendizaje() {
        return idPreguntaEstilosAprendizaje;
    }

    public void setIdPreguntaEstilosAprendizaje(int idPreguntaEstilosAprendizaje) {
        this.idPreguntaEstilosAprendizaje = idPreguntaEstilosAprendizaje;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPreguntaEstilosAprendizaje;
        hash += (int) idEncuestaEstilosAprendizaje;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaEstiloPK)) {
            return false;
        }
        RespuestaEstiloPK other = (RespuestaEstiloPK) object;
        if (this.idPreguntaEstilosAprendizaje != other.idPreguntaEstilosAprendizaje) {
            return false;
        }
        if (this.idEncuestaEstilosAprendizaje != other.idEncuestaEstilosAprendizaje) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RespuestaEstiloPK{" + "Encuesta_idEncuesta=" + idEncuestaEstilosAprendizaje + ", idpregunta_estilos=" + idPreguntaEstilosAprendizaje + '}';
    }
    
}
