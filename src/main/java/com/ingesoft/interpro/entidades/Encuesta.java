/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.EncuestaInteligenciasMultiples;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    , @NamedQuery(name = "Encuesta.findByFechaCreacion", query = "SELECT e FROM Encuesta e WHERE e.fechaCreacion = :fecha")
    , @NamedQuery(name = "Encuesta.findByFechaFinalizada", query = "SELECT e FROM Encuesta e WHERE e.fechaFinalizada = :fecha")
    , @NamedQuery(name = "Encuesta.maxIdEncuesta", query = "SELECT max(e.idEncuesta) FROM Encuesta e")
    , @NamedQuery(name = "Encuesta.findByPersonalidad", query = "SELECT e FROM Encuesta e WHERE e.encuestaPersonalidad = :encuestaPersonalidad")
    , @NamedQuery(name = "Encuesta.findByEstudiante", query = "SELECT e FROM Encuesta e WHERE e.estudiante = :estudiante order by e.fechaCreacion asc")
    , @NamedQuery(name = "Encuesta.findByGrado", query = "SELECT e FROM Encuesta e WHERE e.grado = :grado order by e.fechaCreacion asc")
    , @NamedQuery(name = "Encuesta.findByEstudianteGrado", query = "SELECT e FROM Encuesta e WHERE e.grado = :grado AND e.estudiante = :estudiante order by e.fechaCreacion asc")
})
public class Encuesta implements Serializable {

    public static String PENDINENTE = "PENDINENTE";
    public static String FINALIZADA = "FINALIZADA";
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEncuesta")
    private Integer idEncuesta;
    
    @JoinColumn(name = "idAreaProfesional", referencedColumnName = "idAreaProfesional")
    @ManyToOne
    private AreaProfesional idAreaProfesional;
    
    @JoinColumn(name = "idGrado", referencedColumnName = "idGrado")
    @ManyToOne(optional = false)
    private Grado grado;
    
    @JoinColumn(name = "idEstudiante", referencedColumnName = "idEstudiante")
    @ManyToOne(optional = false)
    private Estudiante estudiante;
    
    @Column(name = "puntajeEncuesta")
    private Integer puntajeEncuesta;
    
    @Column(name = "puntajeEvaluacion")
    private Integer puntajeEvaluacion;
    
    @Size(max = 15)
    @Column(name = "estado")
    private String estado;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @Size(max = 45)
    @Column(name = "fechaFinalizada")
    private String fechaFinalizada;
    
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "encuesta", fetch=FetchType.EAGER)
    private EncuestaEstilosAprendizaje encuestaEstilosAprendizaje;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "encuesta")
    private EncuestaPersonalidad encuestaPersonalidad;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "encuesta")
    private EncuestaInteligenciasMultiples encuestaInteligenciasMultiples;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encuesta")
    private List<AreaEncuesta> areaEncuestaList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encuesta")
    private List<RespuestaAmbiente> respuestaAmbienteList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encuesta", fetch=FetchType.EAGER)
    private List<ResultadoPorAmbiente> resultadoPorAmbienteList;
    
    public Encuesta() {
    }

    public Encuesta(Integer idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public Encuesta(Integer idEncuesta, Date fechaCreacion) {
        this.idEncuesta = idEncuesta;
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(Integer idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

    public AreaProfesional getIdAreaProfesional() {
        return idAreaProfesional;
    }

    public void setIdAreaProfesional(AreaProfesional idAreaProfesional) {
        this.idAreaProfesional = idAreaProfesional;
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
    
    public String getFechaFinalizada() {
        return fechaFinalizada;
    }

    public void setFechaFinalizada(String fechaFinalizada) {
        this.fechaFinalizada = fechaFinalizada;
    }

    public EncuestaEstilosAprendizaje getEncuestaEstilosAprendizaje() {
        return encuestaEstilosAprendizaje;
    }

    public void setEncuestaEstilosAprendizaje(EncuestaEstilosAprendizaje encuestaEstilosAprendizaje) {
        this.encuestaEstilosAprendizaje = encuestaEstilosAprendizaje;
    }

    public EncuestaPersonalidad getEncuestaPersonalidad() {
        return encuestaPersonalidad;
    }

    public void setEncuestaPersonalidad(EncuestaPersonalidad encuestaPersonalidad) {
        this.encuestaPersonalidad = encuestaPersonalidad;
    }

    public EncuestaInteligenciasMultiples getEncuestaInteligenciasMultiples() {
        return encuestaInteligenciasMultiples;
    }

    public void setEncuestaInteligenciasMultiples(EncuestaInteligenciasMultiples encuestaInteligenciasMultiples) {
        this.encuestaInteligenciasMultiples = encuestaInteligenciasMultiples;
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
