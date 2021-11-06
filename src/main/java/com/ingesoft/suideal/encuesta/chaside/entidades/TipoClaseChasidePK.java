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
public class TipoClaseChasidePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idTipoChaside")
    private int idTipoChaside;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idClaseChaside")
    private int idClaseChaside;

    public TipoClaseChasidePK() {
    }

    public TipoClaseChasidePK(int idTipoChaside, int idClaseChaside) {
        this.idTipoChaside = idTipoChaside;
        this.idClaseChaside = idClaseChaside;
    }

    public int getIdTipoChaside() {
        return idTipoChaside;
    }

    public void setIdTipoChaside(int idTipoChaside) {
        this.idTipoChaside = idTipoChaside;
    }

    public int getIdClaseChaside() {
        return idClaseChaside;
    }

    public void setIdClaseChaside(int idClaseChaside) {
        this.idClaseChaside = idClaseChaside;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idTipoChaside;
        hash += (int) idClaseChaside;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoClaseChasidePK)) {
            return false;
        }
        TipoClaseChasidePK other = (TipoClaseChasidePK) object;
        if (this.idTipoChaside != other.idTipoChaside) {
            return false;
        }
        if (this.idClaseChaside != other.idClaseChaside) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.suideal.encuesta.chaside.entidades.TipoClaseChasidePK[ idTipoChaside=" + idTipoChaside + ", idClaseChaside=" + idClaseChaside + " ]";
    }
    
}
