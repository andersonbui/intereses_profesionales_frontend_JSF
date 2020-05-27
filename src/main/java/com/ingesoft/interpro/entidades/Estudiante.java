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
@Table(name = "Estudiante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudiante.findAll", query = "SELECT e FROM Estudiante e")
    , @NamedQuery(name = "Estudiante.findByIdEstudiante", query = "SELECT e FROM Estudiante e WHERE e.idEstudiante = :idEstudiante")
    , @NamedQuery(name = "Estudiante.findByEstrato", query = "SELECT e FROM Estudiante e WHERE e.estrato = :estrato")
    , @NamedQuery(name = "Estudiante.findByCuantosViven", query = "SELECT e FROM Estudiante e WHERE e.cuantosViven = :cuantosViven")
    , @NamedQuery(name = "Estudiante.findByCuantosTrabajan", query = "SELECT e FROM Estudiante e WHERE e.cuantosTrabajan = :cuantosTrabajan")
    , @NamedQuery(name = "Estudiante.findByViveConPadres", query = "SELECT e FROM Estudiante e WHERE e.viveConPadres = :viveConPadres")
    , @NamedQuery(name = "Estudiante.findByDesplazados", query = "SELECT e FROM Estudiante e WHERE e.desplazados = :desplazados")
    , @NamedQuery(name = "Estudiante.findPorPersona", query = "SELECT e FROM Estudiante e WHERE e.idPersona = :persona")
    , @NamedQuery(name = "Estudiante.findByPersonalidad", query = "SELECT e FROM Estudiante e WHERE e.personalidad = :personalidad")
    , @NamedQuery(name = "Estudiante.findByGrado",          query = "SELECT e FROM Estudiante e JOIN e.estudianteGradoList eg WHERE e.personalidad = :personalidad AND eg.grado = :grado")
    , @NamedQuery(name = "Estudiante.buscarPorInstitucion", query = "SELECT e FROM Estudiante e JOIN e.estudianteGradoList eg WHERE e = eg.estudiante AND eg.grado.idInstitucion = :institucion")
            
    })
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEstudiante")
    private Integer idEstudiante;
    @Size(max = 2)
    @Column(name = "estrato")
    private String estrato;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cuantosViven")
    private int cuantosViven;
    @Column(name = "cuantosTrabajan")
    private Integer cuantosTrabajan;
    @Column(name = "viveConPadres")
    private Boolean viveConPadres;
    @Column(name = "desplazados")
    private Boolean desplazados;
    @Size(max = 45)
    @Column(name = "personalidad")
    private String personalidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudiante")
    private List<EstudianteGrado> estudianteGradoList;
    @JoinColumn(name = "idPersona", referencedColumnName = "idPersona")
    @ManyToOne(optional = false)
    private Persona idPersona;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudiante")
    private List<Encuesta> encuestaList;


    public Estudiante() {
    }

    public Estudiante(Integer idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Integer getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Integer idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

    public int getCuantosViven() {
        return cuantosViven;
    }

    public void setCuantosViven(int cuantosViven) {
        this.cuantosViven = cuantosViven;
    }

    public Integer getCuantosTrabajan() {
        return cuantosTrabajan;
    }

    public void setCuantosTrabajan(Integer cuantosTrabajan) {
        this.cuantosTrabajan = cuantosTrabajan;
    }

    public Boolean getViveConPadres() {
        return viveConPadres;
    }

    public void setViveConPadres(Boolean viveConPadres) {
        this.viveConPadres = viveConPadres;
    }

    public Boolean getDesplazados() {
        return desplazados;
    }

    public void setDesplazados(Boolean desplazados) {
        this.desplazados = desplazados;
    }

    public String getPersonalidad() {
        return personalidad;
    }

    public void setPersonalidad(String personalidad) {
        this.personalidad = personalidad;
    }

    @XmlTransient
    public List<EstudianteGrado> getEstudianteGradoList() {
        return estudianteGradoList;
    }

    public void setEstudianteGradoList(List<EstudianteGrado> estudianteGradoList) {
        this.estudianteGradoList = estudianteGradoList;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    public List<Encuesta> getEncuestaList() {
        return encuestaList;
    }

    public void setEncuestaList(List<Encuesta> encuestaList) {
        this.encuestaList = encuestaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstudiante != null ? idEstudiante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudiante)) {
            return false;
        }
        Estudiante other = (Estudiante) object;
        if ((this.idEstudiante == null && other.idEstudiante != null) || (this.idEstudiante != null && !this.idEstudiante.equals(other.idEstudiante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Estudiante[ idEstudiante=" + idEstudiante + ", "+ idPersona + " ]";
    }
    
}
