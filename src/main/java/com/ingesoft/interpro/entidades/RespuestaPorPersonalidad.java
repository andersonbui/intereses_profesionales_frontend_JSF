/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import javax.persistence.Column;
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
@Table(name = "RespuestaPorPersonalidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RespuestaPorPersonalidad.findAll", query = "SELECT r FROM RespuestaPorPersonalidad r")
    , @NamedQuery(name = "RespuestaPorPersonalidad.findByIdEncuesta", query = "SELECT r FROM RespuestaPorPersonalidad r WHERE r.respuestaPorPersonalidadPK.idEncuesta = :idEncuesta")
    , @NamedQuery(name = "RespuestaPorPersonalidad.findByIdTipoPersonalidad", query = "SELECT r FROM RespuestaPorPersonalidad r WHERE r.respuestaPorPersonalidadPK.idTipoPersonalidad = :idTipoPersonalidad")
    , @NamedQuery(name = "RespuestaPorPersonalidad.findByPuntaje", query = "SELECT r FROM RespuestaPorPersonalidad r WHERE r.puntaje = :puntaje")})
public class RespuestaPorPersonalidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RespuestaPorPersonalidadPK respuestaPorPersonalidadPK;
    @Column(name = "puntaje")
    private Integer puntaje;
    @JoinColumn(name = "idEncuesta", referencedColumnName = "idEncuesta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Encuesta encuesta;
    @JoinColumn(name = "idTipoPersonalidad", referencedColumnName = "idTipoPersonalidad", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TipoPersonalidad tipoPersonalidad;

    public RespuestaPorPersonalidad() {
    }

    public RespuestaPorPersonalidad(RespuestaPorPersonalidadPK respuestaPorPersonalidadPK) {
        this.respuestaPorPersonalidadPK = respuestaPorPersonalidadPK;
    }

    public RespuestaPorPersonalidad(int idEncuesta, int idTipoPersonalidad) {
        this.respuestaPorPersonalidadPK = new RespuestaPorPersonalidadPK(idEncuesta, idTipoPersonalidad);
    }

    public RespuestaPorPersonalidadPK getRespuestaPorPersonalidadPK() {
        return respuestaPorPersonalidadPK;
    }

    public void setRespuestaPorPersonalidadPK(RespuestaPorPersonalidadPK respuestaPorPersonalidadPK) {
        this.respuestaPorPersonalidadPK = respuestaPorPersonalidadPK;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }

    public TipoPersonalidad getTipoPersonalidad() {
        return tipoPersonalidad;
    }

    public void setTipoPersonalidad(TipoPersonalidad tipoPersonalidad) {
        this.tipoPersonalidad = tipoPersonalidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (respuestaPorPersonalidadPK != null ? respuestaPorPersonalidadPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaPorPersonalidad)) {
            return false;
        }
        RespuestaPorPersonalidad other = (RespuestaPorPersonalidad) object;
        if ((this.respuestaPorPersonalidadPK == null && other.respuestaPorPersonalidadPK != null) || (this.respuestaPorPersonalidadPK != null && !this.respuestaPorPersonalidadPK.equals(other.respuestaPorPersonalidadPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.RespuestaPorPersonalidad[ respuestaPorPersonalidadPK=" + respuestaPorPersonalidadPK + " ]";
    }
    
}
