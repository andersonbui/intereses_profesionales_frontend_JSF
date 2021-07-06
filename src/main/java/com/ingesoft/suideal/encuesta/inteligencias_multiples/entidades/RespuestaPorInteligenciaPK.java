/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades;

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
public class RespuestaPorInteligenciaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idTipoInteligenciasMultiples")
    private int idTipoInteligenciasMultiples;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idEncuestaInteligenciasMultiples")
    private int idEncuestaInteligenciasMultiples;

    public RespuestaPorInteligenciaPK() {
    }

    public RespuestaPorInteligenciaPK(int idTipoInteligenciasMultiples, int idEncuestaInteligenciasMultiples) {
        this.idTipoInteligenciasMultiples = idTipoInteligenciasMultiples;
        this.idEncuestaInteligenciasMultiples = idEncuestaInteligenciasMultiples;
    }

    public int getIdTipoInteligenciasMultiples() {
        return idTipoInteligenciasMultiples;
    }

    public void setIdTipoInteligenciasMultiples(int idTipoInteligenciasMultiples) {
        this.idTipoInteligenciasMultiples = idTipoInteligenciasMultiples;
    }

    public int getIdEncuestaInteligenciasMultiples() {
        return idEncuestaInteligenciasMultiples;
    }

    public void setIdEncuestaInteligenciasMultiples(int idEncuestaInteligenciasMultiples) {
        this.idEncuestaInteligenciasMultiples = idEncuestaInteligenciasMultiples;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idTipoInteligenciasMultiples;
        hash += (int) idEncuestaInteligenciasMultiples;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaPorInteligenciaPK)) {
            return false;
        }
        RespuestaPorInteligenciaPK other = (RespuestaPorInteligenciaPK) object;
        if (this.idTipoInteligenciasMultiples != other.idTipoInteligenciasMultiples) {
            return false;
        }
        if (this.idEncuestaInteligenciasMultiples != other.idEncuestaInteligenciasMultiples) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RespuestaPorInteligenciaPK[ idTipoInteligenciasMultiples=" + idTipoInteligenciasMultiples + ", idEncuestaInteligenciasMultiples=" + idEncuestaInteligenciasMultiples + " ]";
    }
    
}
