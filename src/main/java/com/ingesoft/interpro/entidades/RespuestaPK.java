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
public class RespuestaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idPregunta")
    private int idPregunta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idEncuesta")
    private int idEncuesta;

    public RespuestaPK() {
    }

    public RespuestaPK(int idPregunta, int idEncuesta) {
        this.idPregunta = idPregunta;
        this.idEncuesta = idEncuesta;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPregunta;
        hash += (int) idEncuesta;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaPK)) {
            return false;
        }
        RespuestaPK other = (RespuestaPK) object;
        if (this.idPregunta != other.idPregunta) {
            return false;
        }
        if (this.idEncuesta != other.idEncuesta) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.RespuestaPK[ idPregunta=" + idPregunta + ", idEncuesta=" + idEncuesta + " ]";
    }
    
}
