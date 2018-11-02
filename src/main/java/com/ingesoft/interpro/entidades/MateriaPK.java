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
public class MateriaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idMateria")
    private int idMateria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Area_idArea")
    private int areaidArea;

    public MateriaPK() {
    }

    public MateriaPK(int idMateria, int areaidArea) {
        this.idMateria = idMateria;
        this.areaidArea = areaidArea;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public int getAreaidArea() {
        return areaidArea;
    }

    public void setAreaidArea(int areaidArea) {
        this.areaidArea = areaidArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idMateria;
        hash += (int) areaidArea;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MateriaPK)) {
            return false;
        }
        MateriaPK other = (MateriaPK) object;
        if (this.idMateria != other.idMateria) {
            return false;
        }
        if (this.areaidArea != other.areaidArea) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.MateriaPK[ idMateria=" + idMateria + ", areaidArea=" + areaidArea + " ]";
    }
    
}
