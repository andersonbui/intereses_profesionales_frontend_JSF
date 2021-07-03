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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author anderson
 */
@Entity
@Table(name = "EncuestaPersonalidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncuestaPersonalidad.findAll", query = "SELECT e FROM EncuestaPersonalidad e"),
    @NamedQuery(name = "EncuestaPersonalidad.findByEncuesta", query = "SELECT e FROM EncuestaPersonalidad e WHERE e.idEncuesta = :Encuesta"),
    @NamedQuery(name = "EncuestaPersonalidad.findByFechaCreacion", query = "SELECT e FROM EncuestaPersonalidad e WHERE e.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "EncuestaPersonalidad.findByFechaFinalizada", query = "SELECT e FROM EncuestaPersonalidad e WHERE e.fechaFinalizada = :fechaFinalizada"),
    @NamedQuery(name = "EncuestaPersonalidad.findByEstado", query = "SELECT e FROM EncuestaPersonalidad e WHERE e.estado = :estado"),
    @NamedQuery(name = "EncuestaPersonalidad.findByPersonalidad", query = "SELECT e FROM EncuestaPersonalidad e WHERE e.personalidad = :personalidad")})
public class EncuestaPersonalidad implements Serializable {

    public static String PENDINENTE = "PENDINENTE";
    public static String FINALIZADA = "FINALIZADA";
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEncuesta")
    private Integer idEncuesta;
    
    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @Column(name = "fechaFinalizada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinalizada;
    
    @Size(max = 11)
    @Column(name = "estado")
    private String estado;
    
    @Size(max = 5)
    @Column(name = "personalidad")
    private String personalidad;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encuestaPersonalidad")
    private List<RespuestaPorPersonalidad> respuestaPorPersonalidadList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encuestaPersonalidad")
    private List<RespuestaPersonalidad> respuestaPersonalidadList;

    @JoinColumn(name = "idEncuesta", referencedColumnName = "idEncuesta", 
    insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Encuesta encuesta;
    
    
    public EncuestaPersonalidad() {
    }

    public EncuestaPersonalidad(Integer idEncuesta) {
        this.idEncuesta = idEncuesta;
    }
    
    public EncuestaPersonalidad(Encuesta encuesta) {
        this.encuesta = encuesta;
        this.idEncuesta = encuesta.getIdEncuesta();
    }

    public Integer getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(Integer idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }
    
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaFinalizada() {
        return fechaFinalizada;
    }

    public void setFechaFinalizada(Date fechaFinalizada) {
        this.fechaFinalizada = fechaFinalizada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPersonalidad() {
        return personalidad;
    }

    public void setPersonalidad(String personalidad) {
        this.personalidad = personalidad;
    }

    @XmlTransient
    public List<RespuestaPorPersonalidad> getRespuestaPorPersonalidadList() {
        return respuestaPorPersonalidadList;
    }

    public void setRespuestaPorPersonalidadList(List<RespuestaPorPersonalidad> respuestaPorPersonalidadList) {
        this.respuestaPorPersonalidadList = respuestaPorPersonalidadList;
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
        if (!(object instanceof EncuestaPersonalidad)) {
            return false;
        }
        EncuestaPersonalidad other = (EncuestaPersonalidad) object;
        if ((this.idEncuesta == null && other.idEncuesta != null) || (this.idEncuesta != null && !this.idEncuesta.equals(other.idEncuesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.EncuestaPersonalidad[ idEncuesta=" + idEncuesta + " ]";
    }
    
}
