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
public class EstudianteGradoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idGrado")
    private int idGrado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idEstudiante")
    private int idEstudiante;

    public EstudianteGradoPK() {
    }

    public EstudianteGradoPK(int idGrado, int idEstudiante) {
        this.idGrado = idGrado;
        this.idEstudiante = idEstudiante;
    }

    public int getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(int idGrado) {
        this.idGrado = idGrado;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idGrado;
        hash += (int) idEstudiante;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstudianteGradoPK)) {
            return false;
        }
        EstudianteGradoPK other = (EstudianteGradoPK) object;
        if (this.idGrado != other.idGrado) {
            return false;
        }
        if (this.idEstudiante != other.idEstudiante) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.EstudianteGradoPK[ idGrado=" + idGrado + ", idEstudiante=" + idEstudiante + " ]";
    }
    
}
