/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "EstudianteGrado", catalog = "interpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstudianteGrado.findAll", query = "SELECT e FROM EstudianteGrado e")
    , @NamedQuery(name = "EstudianteGrado.findByIdEstudiante", query = "SELECT e FROM EstudianteGrado e WHERE e.estudianteGradoPK.idEstudiante = :idEstudiante")
    , @NamedQuery(name = "EstudianteGrado.findByIdGrado", query = "SELECT e FROM EstudianteGrado e WHERE e.estudianteGradoPK.idGrado = :idGrado")
    , @NamedQuery(name = "EstudianteGrado.findByDate", query = "SELECT e FROM EstudianteGrado e WHERE e.estudianteGradoPK.date = :date")})
public class EstudianteGrado implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EstudianteGradoPK estudianteGradoPK;
    @JoinColumn(name = "idEstudiante", referencedColumnName = "idEstudiante", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Estudiante estudiante;
    @JoinColumn(name = "idGrado", referencedColumnName = "idGrado", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Grado grado;

    public EstudianteGrado() {
    }

    public EstudianteGrado(EstudianteGradoPK estudianteGradoPK) {
        this.estudianteGradoPK = estudianteGradoPK;
    }

    public EstudianteGrado(int idEstudiante, int idGrado, Date date) {
        this.estudianteGradoPK = new EstudianteGradoPK(idEstudiante, idGrado, date);
    }

    public EstudianteGradoPK getEstudianteGradoPK() {
        return estudianteGradoPK;
    }

    public void setEstudianteGradoPK(EstudianteGradoPK estudianteGradoPK) {
        this.estudianteGradoPK = estudianteGradoPK;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Grado getGrado() {
        return grado;
    }

    public void setGrado(Grado grado) {
        this.grado = grado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estudianteGradoPK != null ? estudianteGradoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstudianteGrado)) {
            return false;
        }
        EstudianteGrado other = (EstudianteGrado) object;
        if ((this.estudianteGradoPK == null && other.estudianteGradoPK != null) || (this.estudianteGradoPK != null && !this.estudianteGradoPK.equals(other.estudianteGradoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.EstudianteGrado[ estudianteGradoPK=" + estudianteGradoPK + " ]";
    }
    
}
