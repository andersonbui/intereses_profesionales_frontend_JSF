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
public class ResultadoPorAmbientePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idEncuesta")
    private int idEncuesta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idTipoAmbiente")
    private int idTipoAmbiente;

    public ResultadoPorAmbientePK() {
    }

    public ResultadoPorAmbientePK(int idEncuesta, int idTipoAmbiente) {
        this.idEncuesta = idEncuesta;
        this.idTipoAmbiente = idTipoAmbiente;
    }

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public int getIdTipoAmbiente() {
        return idTipoAmbiente;
    }

    public void setIdTipoAmbiente(int idTipoAmbiente) {
        this.idTipoAmbiente = idTipoAmbiente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEncuesta;
        hash += (int) idTipoAmbiente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResultadoPorAmbientePK)) {
            return false;
        }
        ResultadoPorAmbientePK other = (ResultadoPorAmbientePK) object;
        if (this.idEncuesta != other.idEncuesta) {
            return false;
        }
        if (this.idTipoAmbiente != other.idTipoAmbiente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.ResultadoPorAmbientePK[ idEncuesta=" + idEncuesta + ", idTipoAmbiente=" + idTipoAmbiente + " ]";
    }
    
}
