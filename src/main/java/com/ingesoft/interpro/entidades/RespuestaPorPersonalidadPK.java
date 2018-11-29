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
public class RespuestaPorPersonalidadPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idEncuesta")
    private int idEncuesta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idTipoPersonalidad")
    private int idTipoPersonalidad;

    public RespuestaPorPersonalidadPK() {
    }

    public RespuestaPorPersonalidadPK(int idEncuesta, int idTipoPersonalidad) {
        this.idEncuesta = idEncuesta;
        this.idTipoPersonalidad = idTipoPersonalidad;
    }

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public int getIdTipoPersonalidad() {
        return idTipoPersonalidad;
    }

    public void setIdTipoPersonalidad(int idTipoPersonalidad) {
        this.idTipoPersonalidad = idTipoPersonalidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEncuesta;
        hash += (int) idTipoPersonalidad;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaPorPersonalidadPK)) {
            return false;
        }
        RespuestaPorPersonalidadPK other = (RespuestaPorPersonalidadPK) object;
        if (this.idEncuesta != other.idEncuesta) {
            return false;
        }
        if (this.idTipoPersonalidad != other.idTipoPersonalidad) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.RespuestaPorPersonalidadPK[ idEncuesta=" + idEncuesta + ", idTipoPersonalidad=" + idTipoPersonalidad + " ]";
    }
    
}
