/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author debian
 */
@Embeddable
public class EstudianteGradoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idEstudiante")
    private int idEstudiante;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idGrado")
    private int idGrado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    public EstudianteGradoPK() {
    }

    public EstudianteGradoPK(int idEstudiante, int idGrado, Date date) {
        this.idEstudiante = idEstudiante;
        this.idGrado = idGrado;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEstudiante;
        hash += (int) idGrado;
        hash += (date != null ? date.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstudianteGradoPK)) {
            return false;
        }
        EstudianteGradoPK other = (EstudianteGradoPK) object;
        if (this.idEstudiante != other.idEstudiante) {
            return false;
        }
        if (this.idGrado != other.idGrado) {
            return false;
        }
        if ((this.date == null && other.date != null) || (this.date != null && !this.date.equals(other.date))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.EstudianteGradoPK[ idEstudiante=" + idEstudiante + ", idGrado=" + idGrado + ", date=" + date + " ]";
    }
    
}
