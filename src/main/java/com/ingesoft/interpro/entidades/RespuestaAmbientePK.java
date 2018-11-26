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
public class RespuestaAmbientePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idPreguntasAmbiente")
    private int idPreguntasAmbiente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idEncuesta")
    private int idEncuesta;

    public RespuestaAmbientePK() {
    }

    public RespuestaAmbientePK(int idPreguntasAmbiente, int idEncuesta) {
        this.idPreguntasAmbiente = idPreguntasAmbiente;
        this.idEncuesta = idEncuesta;
    }

    public int getIdPreguntasAmbiente() {
        return idPreguntasAmbiente;
    }

    public void setIdPreguntasAmbiente(int idPreguntasAmbiente) {
        this.idPreguntasAmbiente = idPreguntasAmbiente;
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
        hash += (int) idPreguntasAmbiente;
        hash += (int) idEncuesta;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaAmbientePK)) {
            return false;
        }
        RespuestaAmbientePK other = (RespuestaAmbientePK) object;
        if (this.idPreguntasAmbiente != other.idPreguntasAmbiente) {
            return false;
        }
        if (this.idEncuesta != other.idEncuesta) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.RespuestaAmbientePK[ idPreguntasAmbiente=" + idPreguntasAmbiente + ", idEncuesta=" + idEncuesta + " ]";
    }
    
}
