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
 * @author Personal
 */
@Embeddable
public class TipoEstiloPreguntaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idTipoEstilo")
    private int idTipoEstilo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idpregunta_estilos")
    private int idpreguntaEstilos;

    public TipoEstiloPreguntaPK() {
    }

    public TipoEstiloPreguntaPK(int idTipoEstilo, int idpreguntaEstilos) {
        this.idTipoEstilo = idTipoEstilo;
        this.idpreguntaEstilos = idpreguntaEstilos;
    }

    public int getIdTipoEstilo() {
        return idTipoEstilo;
    }

    public void setIdTipoEstilo(int idTipoEstilo) {
        this.idTipoEstilo = idTipoEstilo;
    }

    public int getIdpreguntaEstilos() {
        return idpreguntaEstilos;
    }

    public void setIdpreguntaEstilos(int idpreguntaEstilos) {
        this.idpreguntaEstilos = idpreguntaEstilos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idTipoEstilo;
        hash += (int) idpreguntaEstilos;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEstiloPreguntaPK)) {
            return false;
        }
        TipoEstiloPreguntaPK other = (TipoEstiloPreguntaPK) object;
        if (this.idTipoEstilo != other.idTipoEstilo) {
            return false;
        }
        if (this.idpreguntaEstilos != other.idpreguntaEstilos) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.TipoestiloPreguntaPK[ idTipoEstilo=" + idTipoEstilo + ", idpreguntaEstilos=" + idpreguntaEstilos + " ]";
    }
    
}
