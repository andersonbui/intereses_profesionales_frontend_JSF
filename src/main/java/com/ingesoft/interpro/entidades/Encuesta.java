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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
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
    , @NamedQuery(name = "Encuesta.maxIdEncuesta", query = "SELECT max(e.idEncuesta) FROM Encuesta e")
    , @NamedQuery(name = "Encuesta.findByPuntaje", query = "SELECT e FROM Encuesta e WHERE e.puntaje = :puntaje")
    , @NamedQuery(name = "Encuesta.findByFecha", query = "SELECT e FROM Encuesta e WHERE e.fecha = :fecha")})
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
    @Column(name = "puntaje")
    private Integer puntaje;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encuesta")
    private List<AreaEncuesta> areaEncuestaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encuesta")
    private List<RespuestaAmbiente> respuestaAmbienteList;
    @JoinColumn(name = "idAreaProfesional", referencedColumnName = "idAreaProfesional")
    @ManyToOne
    private AreaProfesional idAreaProfesional;
    @JoinColumn(name = "idEstudiante", referencedColumnName = "idEstudiante")
    @ManyToOne(optional = false)
    private Estudiante idEstudiante;
    @JoinColumn(name = "idGrado", referencedColumnName = "idGrado")
    @ManyToOne
    private Grado idGrado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encuesta")
    private List<RespuestaPersonalidad> respuestaPersonalidadList;

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

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
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

    public AreaProfesional getIdAreaProfesional() {
        return idAreaProfesional;
    }

    public void setIdAreaProfesional(AreaProfesional idAreaProfesional) {
        this.idAreaProfesional = idAreaProfesional;
    }

    public Estudiante getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Estudiante idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Grado getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(Grado idGrado) {
        this.idGrado = idGrado;
    }

    @XmlTransient
    public List<RespuestaPersonalidad> getRespuestaPersonalidadList() {
        return respuestaPersonalidadList;
    }

    public void setRespuestaPersonalidadList(List<RespuestaPersonalidad> respuestaPersonalidadList) {
        this.respuestaPersonalidadList = respuestaPersonalidadList;
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
        return "com.ingesoft.interpro.entidades.Encuesta[ idEncuesta=" + idEncuesta + " ]";
    }
    
}
