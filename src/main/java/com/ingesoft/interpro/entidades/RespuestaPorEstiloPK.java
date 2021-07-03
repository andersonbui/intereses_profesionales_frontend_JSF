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
public class RespuestaPorEstiloPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idTipoEstilo")
    private int idTipoEstilo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idEncuestaEstilosAprendizaje")
    private int idEncuestaEstilosAprendizaje;

    public RespuestaPorEstiloPK() {
    }

    public RespuestaPorEstiloPK(int idTipoEstilo, int idEncuestaEstilosAprendizaje) {
        this.idTipoEstilo = idTipoEstilo;
        this.idEncuestaEstilosAprendizaje = idEncuestaEstilosAprendizaje;
    }

    public int getIdTipoEstilo() {
        return idTipoEstilo;
    }

    public void setIdTipoEstilo(int idTipoEstilo) {
        this.idTipoEstilo = idTipoEstilo;
    }

    public int getIdEncuestaEstilosAprendizaje() {
        return idEncuestaEstilosAprendizaje;
    }

    public void setIdEncuestaEstilosAprendizaje(int idEncuestaEstilosAprendizaje) {
        this.idEncuestaEstilosAprendizaje = idEncuestaEstilosAprendizaje;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idTipoEstilo;
        hash += (int) idEncuestaEstilosAprendizaje;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaPorEstiloPK)) {
            return false;
        }
        RespuestaPorEstiloPK other = (RespuestaPorEstiloPK) object;
        if (this.idTipoEstilo != other.idTipoEstilo) {
            return false;
        }
        if (this.idEncuestaEstilosAprendizaje != other.idEncuestaEstilosAprendizaje) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cRespuestaPorEstiloPK[ idTipoEstilo=" + idTipoEstilo + ", idEncuestaEstilosAprendizaje=" + idEncuestaEstilosAprendizaje + " ]";
    }
    
}
