/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.chaside.entidades;

import com.ingesoft.interpro.entidades.Encuesta;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "EncuestaChaside")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncuestaChaside.findAll", query = "SELECT e FROM EncuestaChaside e"),
    @NamedQuery(name = "EncuestaChaside.findByIdEncuestaChaside", query = "SELECT e FROM EncuestaChaside e WHERE e.idEncuesta = :idEncuesta"),
    @NamedQuery(name = "EncuestaChaside.findByFechaCreacion", query = "SELECT e FROM EncuestaChaside e WHERE e.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "EncuestaChaside.findByFechaFinalizada", query = "SELECT e FROM EncuestaChaside e WHERE e.fechaFinalizada = :fechaFinalizada"),
    @NamedQuery(name = "EncuestaChaside.findByEstado", query = "SELECT e FROM EncuestaChaside e WHERE e.estado = :estado")})
public class EncuestaChaside implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
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
    @JoinColumn(name = "idEncuesta", referencedColumnName = "idEncuesta", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Encuesta encuesta;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encuestaChaside")
    private List<RespuestaChaside> respuestaChasideList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encuestaChaside")
    private List<ResultadoChaside> resultadoChasideList;

    public EncuestaChaside() {
    }

    public EncuestaChaside(Integer idEncuesta) {
        this.idEncuesta = idEncuesta;
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

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }

    @XmlTransient
    public List<RespuestaChaside> getRespuestaChasideList() {
        return respuestaChasideList;
    }

    public void setRespuestaChasideList(List<RespuestaChaside> respuestaChasideList) {
        this.respuestaChasideList = respuestaChasideList;
    }

    @XmlTransient
    public List<ResultadoChaside> getResultadoChasideList() {
        return resultadoChasideList;
    }

    public void setResultadoChasideList(List<ResultadoChaside> resultadoChasideList) {
        this.resultadoChasideList = resultadoChasideList;
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
        if (!(object instanceof EncuestaChaside)) {
            return false;
        }
        EncuestaChaside other = (EncuestaChaside) object;
        if ((this.idEncuesta == null && other.idEncuesta != null) || (this.idEncuesta != null && !this.idEncuesta.equals(other.idEncuesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.suideal.encuesta.chaside.entidades.EncuestaChaside[ idEncuesta=" + idEncuesta + " ]";
    }
    
}
