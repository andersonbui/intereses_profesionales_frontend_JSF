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
public class RespuestaPersonalidadPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idPreguntaPersonalidad")
    private int idPreguntaPersonalidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idEncuesta")
    private int idEncuesta;

    public RespuestaPersonalidadPK() {
    }

    public RespuestaPersonalidadPK(int idPreguntaPersonalidad, int idEncuesta) {
        this.idPreguntaPersonalidad = idPreguntaPersonalidad;
        this.idEncuesta = idEncuesta;
    }

    public int getIdPreguntaPersonalidad() {
        return idPreguntaPersonalidad;
    }

    public void setIdPreguntaPersonalidad(int idPreguntaPersonalidad) {
        this.idPreguntaPersonalidad = idPreguntaPersonalidad;
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
        hash += (int) idPreguntaPersonalidad;
        hash += (int) idEncuesta;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaPersonalidadPK)) {
            return false;
        }
        RespuestaPersonalidadPK other = (RespuestaPersonalidadPK) object;
        if (this.idPreguntaPersonalidad != other.idPreguntaPersonalidad) {
            return false;
        }
        if (this.idEncuesta != other.idEncuesta) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingeniosoft.interpro.entidades.RespuestaPersonalidadPK[ idPreguntaPersonalidad=" + idPreguntaPersonalidad + ", idEncuesta=" + idEncuesta + " ]";
    }

}
