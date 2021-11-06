/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.chaside.entidades;

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
public class ResultadoChasidePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idClaseChaside")
    private int idClaseChaside;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idTipoChaside")
    private int idTipoChaside;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idEncuesta")
    private int idEncuesta;

    public ResultadoChasidePK() {
    }

    public ResultadoChasidePK(int idClaseChaside, int idTipoChaside, int idEncuesta) {
        this.idClaseChaside = idClaseChaside;
        this.idTipoChaside = idTipoChaside;
        this.idEncuesta = idEncuesta;
    }

    public int getIdClaseChaside() {
        return idClaseChaside;
    }

    public void setIdClaseChaside(int idClaseChaside) {
        this.idClaseChaside = idClaseChaside;
    }

    public int getIdTipoChaside() {
        return idTipoChaside;
    }

    public void setIdTipoChaside(int idTipoChaside) {
        this.idTipoChaside = idTipoChaside;
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
        hash += (int) idClaseChaside;
        hash += (int) idTipoChaside;
        hash += (int) idEncuesta;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResultadoChasidePK)) {
            return false;
        }
        ResultadoChasidePK other = (ResultadoChasidePK) object;
        if (this.idClaseChaside != other.idClaseChaside) {
            return false;
        }
        if (this.idTipoChaside != other.idTipoChaside) {
            return false;
        }
        if (this.idEncuesta != other.idEncuesta) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResultadoChasidePK[ idClaseChaside=" + idClaseChaside + ", idTipoChaside=" + idTipoChaside + ", idEncuesta=" + idEncuesta + " ]";
    }
    
}
