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
public class NotaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idMateria")
    private int idMateria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idEstudiante")
    private int idEstudiante;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idGrado")
    private int idGrado;

    public NotaPK() {
    }

    public NotaPK(int idMateria, int idEstudiante, int idGrado) {
        this.idMateria = idMateria;
        this.idEstudiante = idEstudiante;
        this.idGrado = idGrado;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(int idGrado) {
        this.idGrado = idGrado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idMateria;
        hash += (int) idEstudiante;
        hash += (int) idGrado;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotaPK)) {
            return false;
        }
        NotaPK other = (NotaPK) object;
        if (this.idMateria != other.idMateria) {
            return false;
        }
        if (this.idEstudiante != other.idEstudiante) {
            return false;
        }
        if (this.idGrado != other.idGrado) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "NotaPK[ idMateria=" + idMateria + ", idEstudiante=" + idEstudiante + ", idGrado=" + idGrado + " ]";
    }
    
}
