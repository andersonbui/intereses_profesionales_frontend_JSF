/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "Encuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Encuesta.findAll", query = "SELECT e FROM Encuesta e")
    , @NamedQuery(name = "Encuesta.findByIdEncuesta", query = "SELECT e FROM Encuesta e WHERE e.idEncuesta = :idEncuesta")
    , @NamedQuery(name = "Encuesta.findByFecha", query = "SELECT e FROM Encuesta e WHERE e.fecha = :fecha")
    , @NamedQuery(name = "Encuesta.maxIdEncuesta", query = "SELECT max(e.idEncuesta) FROM Encuesta e")
    , @NamedQuery(name = "Encuesta.findByPersonalidad", query = "SELECT e FROM Encuesta e WHERE e.personalidad = :personalidad")})
public class Encuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEncuesta")
    private Integer idEncuesta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 5)
    @Column(name = "personalidad")
    private String personalidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encuesta")
    private List<AreaEncuesta> areaEncuestaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encuesta")
    private List<RespuestaAmbiente> respuestaAmbienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encuesta", fetch=FetchType.EAGER)
    private List<ResultadoPorAmbiente> resultadoPorAmbienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encuesta")
    private List<RespuestaPorPersonalidad> respuestaPorPersonalidadList;
    @JoinColumn(name = "idAreaProfesional", referencedColumnName = "idAreaProfesional")
    @ManyToOne
    private AreaProfesional idAreaProfesional;
    @JoinColumns({
        @JoinColumn(name = "idGrado", referencedColumnName = "idGrado")
        , @JoinColumn(name = "idEstudiante", referencedColumnName = "idEstudiante")})
    @ManyToOne(optional = false)
    private EstudianteGrado estudianteGrado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encuesta")
    private List<RespuestaPersonalidad> respuestaPersonalidadList;
    @Column(name = "puntajeEncuesta")
    private Integer puntajeEncuesta;
    @Column(name = "puntajeEvaluacion")
    private Integer puntajeEvaluacion;

    public Encuesta() {
    }

    public Encuesta(Integer idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public Encuesta(Integer idEncuesta, Date fecha) {
        this.idEncuesta = idEncuesta;
        this.fecha = fecha;
    }

    public Integer getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(Integer idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getPersonalidad() {
        return personalidad;
    }

    public void setPersonalidad(String personalidad) {
        this.personalidad = personalidad;
    }

    @XmlTransient
    public List<AreaEncuesta> getAreaEncuestaList() {
        return areaEncuestaList;
    }

    public void setAreaEncuestaList(List<AreaEncuesta> areaEncuestaList) {
        this.areaEncuestaList = areaEncuestaList;
    }

    @XmlTransient
    public List<RespuestaAmbiente> getRespuestaAmbienteList() {
        return respuestaAmbienteList;
    }

    public void setRespuestaAmbienteList(List<RespuestaAmbiente> respuestaAmbienteList) {
        this.respuestaAmbienteList = respuestaAmbienteList;
    }

    @XmlTransient
    public List<ResultadoPorAmbiente> getResultadoPorAmbienteList() {
        return resultadoPorAmbienteList;
    }

    public void setResultadoPorAmbienteList(List<ResultadoPorAmbiente> resultadoPorAmbienteList) {
        this.resultadoPorAmbienteList = resultadoPorAmbienteList;
    }

    @XmlTransient
    public List<RespuestaPorPersonalidad> getRespuestaPorPersonalidadList() {
        return respuestaPorPersonalidadList;
    }

    public void setRespuestaPorPersonalidadList(List<RespuestaPorPersonalidad> respuestaPorPersonalidadList) {
        this.respuestaPorPersonalidadList = respuestaPorPersonalidadList;
    }

    public AreaProfesional getIdAreaProfesional() {
        return idAreaProfesional;
    }

    public void setIdAreaProfesional(AreaProfesional idAreaProfesional) {
        this.idAreaProfesional = idAreaProfesional;
    }

    public EstudianteGrado getEstudianteGrado() {
        return estudianteGrado;
    }

    public void setEstudianteGrado(EstudianteGrado estudianteGrado) {
        this.estudianteGrado = estudianteGrado;
    }

    @XmlTransient
    public List<RespuestaPersonalidad> getRespuestaPersonalidadList() {
        return respuestaPersonalidadList;
    }

    public void setRespuestaPersonalidadList(List<RespuestaPersonalidad> respuestaPersonalidadList) {
        this.respuestaPersonalidadList = respuestaPersonalidadList;
    }


    public Integer getPuntajeEncuesta() {
        return puntajeEncuesta;
    }

    public void setPuntajeEncuesta(Integer puntajeEncuesta) {
        this.puntajeEncuesta = puntajeEncuesta;
    }

    public Integer getPuntajeEvaluacion() {
        return puntajeEvaluacion;
    }

    public void setPuntajeEvaluacion(Integer puntajeEvaluacion) {
        this.puntajeEvaluacion = puntajeEvaluacion;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEncuesta != null ? idEncuesta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Encuesta)) {
            return false;
        }
        Encuesta other = (Encuesta) object;
        if ((this.idEncuesta == null && other.idEncuesta != null) || (this.idEncuesta != null && !this.idEncuesta.equals(other.idEncuesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Encuesta[ idEncuesta=" + idEncuesta + " ]";
    }
    
}
