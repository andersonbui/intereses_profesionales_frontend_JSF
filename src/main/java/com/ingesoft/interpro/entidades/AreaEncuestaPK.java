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
public class AreaEncuestaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "posicion")
    private short posicion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idEncuesta")
    private int idEncuesta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idTipoEleccionMateria")
    private int idTipoEleccionMateria;

    public AreaEncuestaPK() {
    }

    /**
     *
     * @param posicion
     * @param idEncuesta
     * @param idTipoEleccionMateria
     */
    public AreaEncuestaPK(short posicion, int idEncuesta, int idTipoEleccionMateria) {
        this.posicion = posicion;
        this.idEncuesta = idEncuesta;
        this.idTipoEleccionMateria = idTipoEleccionMateria;
    }

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public short getPosicion() {
        return posicion;
    }

    public void setPosicion(short posicion) {
        this.posicion = posicion;
    }

    public int getIdTipoEleccionMateria() {
        return idTipoEleccionMateria;
    }

    public void setIdTipoEleccionMateria(int idTipoEleccionMateria) {
        this.idTipoEleccionMateria = idTipoEleccionMateria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) posicion;
        hash += (int) idEncuesta;
        hash += (int) idTipoEleccionMateria;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AreaEncuestaPK)) {
            return false;
        }
        AreaEncuestaPK other = (AreaEncuestaPK) object;
        if (this.posicion != other.posicion) {
            return false;
        }
        if (this.idEncuesta != other.idEncuesta) {
            return false;
        }
        if (this.idTipoEleccionMateria != other.idTipoEleccionMateria) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.AreaEncuestaPK[ idEncuesta=" + idEncuesta + ", posicion=" + posicion + ", idTipoEleccionMateria=" + idTipoEleccionMateria + " ]";
    }
    
}
