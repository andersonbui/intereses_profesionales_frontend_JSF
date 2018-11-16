/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "Grado", catalog = "interpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grado.findAll", query = "SELECT g FROM Grado g")
    , @NamedQuery(name = "Grado.findByIdGrado", query = "SELECT g FROM Grado g WHERE g.idGrado = :idGrado")
    , @NamedQuery(name = "Grado.findByCurso", query = "SELECT g FROM Grado g WHERE g.curso = :curso")
    , @NamedQuery(name = "Grado.findByGrado", query = "SELECT g FROM Grado g WHERE g.grado = :grado")})
public class Grado implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grado")
    private List<Nota> notaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grado")
    private List<EstudianteGrado> estudianteGradoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idGrado")
    private Integer idGrado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "curso")
    private String curso;
    @Size(max = 45)
    @Column(name = "grado")
    private String grado;
    @JoinColumn(name = "idInstitucion", referencedColumnName = "idInstitucion")
    @ManyToOne(optional = false)
    private Institucion idInstitucion;

    public Grado() {
    }

    public Grado(Integer idGrado) {
        this.idGrado = idGrado;
    }

    public Grado(Integer idGrado, String curso) {
        this.idGrado = idGrado;
        this.curso = curso;
    }

    public Integer getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(Integer idGrado) {
        this.idGrado = idGrado;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public Institucion getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(Institucion idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrado != null ? idGrado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grado)) {
            return false;
        }
        Grado other = (Grado) object;
        if ((this.idGrado == null && other.idGrado != null) || (this.idGrado != null && !this.idGrado.equals(other.idGrado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.Grado[ idGrado=" + idGrado + " ]";
    }

    @XmlTransient
    public List<Nota> getNotaList() {
        return notaList;
    }

    public void setNotaList(List<Nota> notaList) {
        this.notaList = notaList;
    }

    @XmlTransient
    public List<EstudianteGrado> getEstudianteGradoList() {
        return estudianteGradoList;
    }

    public void setEstudianteGradoList(List<EstudianteGrado> estudianteGradoList) {
        this.estudianteGradoList = estudianteGradoList;
    }
    
}
