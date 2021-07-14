/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades;

import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaPorEstilo;
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
@Table(name = "EncuestaEstilosAprendizaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncuestaEstilosAprendizaje.findAll", query = "SELECT e FROM EncuestaEstilosAprendizaje e"),
    @NamedQuery(name = "EncuestaEstilosAprendizaje.findByIdEncuesta", query = "SELECT e FROM EncuestaEstilosAprendizaje e WHERE e.idEncuesta = :idEncuesta"),
    @NamedQuery(name = "EncuestaEstilosAprendizaje.findByFechaCreacion", query = "SELECT e FROM EncuestaEstilosAprendizaje e WHERE e.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "EncuestaEstilosAprendizaje.findByFechaFinalizada", query = "SELECT e FROM EncuestaEstilosAprendizaje e WHERE e.fechaFinalizada = :fechaFinalizada"),
    @NamedQuery(name = "EncuestaEstilosAprendizaje.findByEstado", query = "SELECT e FROM EncuestaEstilosAprendizaje e WHERE e.estado = :estado")})
public class EncuestaEstilosAprendizaje implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static String PENDINENTE = "PENDINENTE";
    public static String FINALIZADA = "FINALIZADA";
     
    @Id
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encuestaEstilosAprendizaje")
    private List<RespuestaPorEstilo> respuestaPorEstiloList;
    
//    @OneToOne(optional = false)
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "encuestaEstilosAprendizaje")
    private RespuestaEstilo respuestaEstilo;
    
    @JoinColumn(name = "idEncuesta", referencedColumnName = "idEncuesta", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Encuesta encuesta;

    public EncuestaEstilosAprendizaje() {
    }

    public EncuestaEstilosAprendizaje(Integer idEncuesta) {
        this.idEncuesta = idEncuesta;
    }
    
    public EncuestaEstilosAprendizaje(Encuesta encuesta) {
        this.idEncuesta = encuesta.getIdEncuesta();
        this.encuesta = encuesta;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
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

    @XmlTransient
    public List<RespuestaPorEstilo> getRespuestaPorEstiloList() {
        return respuestaPorEstiloList;
    }

    public void setRespuestaPorEstiloList(List<RespuestaPorEstilo> respuestaPorEstiloList) {
        this.respuestaPorEstiloList = respuestaPorEstiloList;
    }

    public RespuestaEstilo getRespuestaEstilo() {
        return respuestaEstilo;
    }

    public void setRespuestaEstilo(RespuestaEstilo respuestaEstilo) {
        this.respuestaEstilo = respuestaEstilo;
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
        if (!(object instanceof EncuestaEstilosAprendizaje)) {
            return false;
        }
        EncuestaEstilosAprendizaje other = (EncuestaEstilosAprendizaje) object;
        if ((this.idEncuesta == null && other.idEncuesta != null) || (this.idEncuesta != null && !this.idEncuesta.equals(other.idEncuesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EncuestaEstilosAprendizaje[ idEncuesta=" + idEncuesta + " ]";
    }
    
}
