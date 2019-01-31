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
    @Column(name = "idArea")
    private int idArea;
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
     * @param idArea
     * @param idEncuesta
     * @param idTipoEleccionMateria
     */
    public AreaEncuestaPK(int idArea, int idEncuesta, int idTipoEleccionMateria) {
        this.idArea = idArea;
        this.idEncuesta = idEncuesta;
        this.idTipoEleccionMateria = idTipoEleccionMateria;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
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
        hash += (int) idArea;
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
        if (this.idArea != other.idArea) {
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
        return "AreaEncuestaPK[ idArea=" + idArea + ", idEncuesta=" + idEncuesta + ", idTipoEleccionMateria=" + idTipoEleccionMateria + " ]";
    }

}
