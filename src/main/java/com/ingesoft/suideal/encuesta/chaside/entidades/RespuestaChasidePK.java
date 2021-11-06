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
public class RespuestaChasidePK implements Serializable {


    @Basic(optional = false)
    @NotNull
    @Column(name = "idEncuestaChaside")
    private int idEncuestaChaside;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPreguntaChaside")
    private int idPreguntaChaside;

    public RespuestaChasidePK() {
    }

    public RespuestaChasidePK(int idPreguntaChaside, int idEncuestaChaside) {
        this.idPreguntaChaside = idPreguntaChaside;
        this.idEncuestaChaside = idEncuestaChaside;
    }

    public int getIdEncuestaChaside() {
        return idEncuestaChaside;
    }

    public void setIdEncuestaChaside(int idEncuestaChaside) {
        this.idEncuestaChaside = idEncuestaChaside;
    }

    public int getIdPreguntaChaside() {
        return idPreguntaChaside;
    }

    public void setIdPreguntaChaside(int idPreguntaChaside) {
        this.idPreguntaChaside = idPreguntaChaside;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPreguntaChaside;
        hash += (int) idEncuestaChaside;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaChasidePK)) {
            return false;
        }
        RespuestaChasidePK other = (RespuestaChasidePK) object;
        if (this.idPreguntaChaside != other.idPreguntaChaside) {
            return false;
        }
        if (this.idEncuestaChaside != other.idEncuestaChaside) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RespuestaChasidePK{" + "idEncuesta=" + idEncuestaChaside + ", idPreguntaChaside=" + idPreguntaChaside + '}';
    }
    
}
