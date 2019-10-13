/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "EstudianteGrado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstudianteGrado.findAll", query = "SELECT e FROM EstudianteGrado e")
    , @NamedQuery(name = "EstudianteGrado.findByIdGrado", query = "SELECT e FROM EstudianteGrado e WHERE e.estudianteGradoPK.idGrado = :idGrado")
    , @NamedQuery(name = "EstudianteGrado.findByEstudiante", query = "SELECT e FROM EstudianteGrado e WHERE e.estudiante = :estudiante ORDER BY e.fecha DESC")})
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteGrado")
    private List<Nota> notaList;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public EstudianteGrado() {
    }

    public EstudianteGrado(EstudianteGradoPK estudianteGradoPK) {
        this.estudianteGradoPK = estudianteGradoPK;
    }

    public EstudianteGrado(int idGrado, int idEstudiante) {
        this.estudianteGradoPK = new EstudianteGradoPK(idGrado, idEstudiante);
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

    @XmlTransient
    public List<Nota> getNotaList() {
        return notaList;
    }

    public void setNotaList(List<Nota> notaList) {
        this.notaList = notaList;
    }
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
        return "EstudianteGrado[ estudianteGradoPK=" + estudianteGradoPK + " ]";
    }
    
}
