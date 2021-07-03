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
 * @author anderson
 */
@Embeddable
public class RespuestaInteligenciasMultiplesPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idEncuestaInteligenciasMultiples")
    private int idEncuestaInteligenciasMultiples;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPreguntaInteligenciasMultiples")
    private int idPreguntaInteligenciasMultiples;

    public RespuestaInteligenciasMultiplesPK() {
    }

    public RespuestaInteligenciasMultiplesPK(int idEncuestaInteligenciasMultiples, int idPreguntaInteligenciasMultiples) {
        this.idEncuestaInteligenciasMultiples = idEncuestaInteligenciasMultiples;
        this.idPreguntaInteligenciasMultiples = idPreguntaInteligenciasMultiples;
    }

    public int getIdEncuestaInteligenciasMultiples() {
        return idEncuestaInteligenciasMultiples;
    }

    public void setIdEncuestaInteligenciasMultiples(int idEncuestaInteligenciasMultiples) {
        this.idEncuestaInteligenciasMultiples = idEncuestaInteligenciasMultiples;
    }

    public int getIdPreguntaInteligenciasMultiples() {
        return idPreguntaInteligenciasMultiples;
    }

    public void setIdPreguntaInteligenciasMultiples(int idPreguntaInteligenciasMultiples) {
        this.idPreguntaInteligenciasMultiples = idPreguntaInteligenciasMultiples;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEncuestaInteligenciasMultiples;
        hash += (int) idPreguntaInteligenciasMultiples;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaInteligenciasMultiplesPK)) {
            return false;
        }
        RespuestaInteligenciasMultiplesPK other = (RespuestaInteligenciasMultiplesPK) object;
        if (this.idEncuestaInteligenciasMultiples != other.idEncuestaInteligenciasMultiples) {
            return false;
        }
        if (this.idPreguntaInteligenciasMultiples != other.idPreguntaInteligenciasMultiples) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RespuestaInteligenciasMultiplesPK[ idEncuestaInteligenciasMultiples=" + idEncuestaInteligenciasMultiples + ", idPreguntaInteligenciasMultiples=" + idPreguntaInteligenciasMultiples + " ]";
    }
    
}
