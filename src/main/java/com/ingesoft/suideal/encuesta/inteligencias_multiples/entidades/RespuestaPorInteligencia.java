/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author anderson
 */
@Entity
@Table(name = "RespuestaPorInteligencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RespuestaPorInteligencia.findAll", query = "SELECT r FROM RespuestaPorInteligencia r"),
    @NamedQuery(name = "RespuestaPorInteligencia.findByIdTipoInteligenciasMultiples", query = "SELECT r FROM RespuestaPorInteligencia r WHERE r.respuestaPorInteligenciaPK.idTipoInteligenciasMultiples = :idTipoInteligenciasMultiples"),
    @NamedQuery(name = "RespuestaPorInteligencia.findByIdEncuestaInteligenciasMultiples", query = "SELECT r FROM RespuestaPorInteligencia r WHERE r.respuestaPorInteligenciaPK.idEncuestaInteligenciasMultiples = :idEncuestaInteligenciasMultiples"),
    @NamedQuery(name = "RespuestaPorInteligencia.findByRespuesta", query = "SELECT r FROM RespuestaPorInteligencia r WHERE r.respuesta = :respuesta")})
public class RespuestaPorInteligencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RespuestaPorInteligenciaPK respuestaPorInteligenciaPK;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "respuesta")
    private short respuesta;
    
    @JoinColumn(name = "idEncuestaInteligenciasMultiples", referencedColumnName = "idEncuesta", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private EncuestaInteligenciasMultiples encuestaInteligenciasMultiples;
    
    @JoinColumn(name = "idTipoInteligenciasMultiples", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private TipoInteligenciasMultiples tipoInteligenciasMultiples;

    public RespuestaPorInteligencia() {
    }

    public RespuestaPorInteligencia(RespuestaPorInteligenciaPK respuestaPorInteligenciaPK) {
        this.respuestaPorInteligenciaPK = respuestaPorInteligenciaPK;
    }

    public RespuestaPorInteligencia(RespuestaPorInteligenciaPK respuestaPorInteligenciaPK, short respuesta) {
        this.respuestaPorInteligenciaPK = respuestaPorInteligenciaPK;
        this.respuesta = respuesta;
    }

    public RespuestaPorInteligencia(int idTipoInteligenciasMultiples, int idEncuestaInteligenciasMultiples) {
        this.respuestaPorInteligenciaPK = new RespuestaPorInteligenciaPK(idTipoInteligenciasMultiples, idEncuestaInteligenciasMultiples);
    }

    public RespuestaPorInteligenciaPK getRespuestaPorInteligenciaPK() {
        return respuestaPorInteligenciaPK;
    }

    public void setRespuestaPorInteligenciaPK(RespuestaPorInteligenciaPK respuestaPorInteligenciaPK) {
        this.respuestaPorInteligenciaPK = respuestaPorInteligenciaPK;
    }

    public short getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(short respuesta) {
        this.respuesta = respuesta;
    }

    public EncuestaInteligenciasMultiples getEncuestaInteligenciasMultiples() {
        return encuestaInteligenciasMultiples;
    }

    public void setEncuestaInteligenciasMultiples(EncuestaInteligenciasMultiples encuestaInteligenciasMultiples) {
        this.encuestaInteligenciasMultiples = encuestaInteligenciasMultiples;
    }

    public TipoInteligenciasMultiples getTipoInteligenciasMultiples() {
        return tipoInteligenciasMultiples;
    }

    public void setTipoInteligenciasMultiples(TipoInteligenciasMultiples tipoInteligenciasMultiples) {
        this.tipoInteligenciasMultiples = tipoInteligenciasMultiples;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (respuestaPorInteligenciaPK != null ? respuestaPorInteligenciaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaPorInteligencia)) {
            return false;
        }
        RespuestaPorInteligencia other = (RespuestaPorInteligencia) object;
        if ((this.respuestaPorInteligenciaPK == null && other.respuestaPorInteligenciaPK != null) || (this.respuestaPorInteligenciaPK != null && !this.respuestaPorInteligenciaPK.equals(other.respuestaPorInteligenciaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RespuestaPorInteligencia[ PK=" + respuestaPorInteligenciaPK + "| tipoInteligenciasMultiples: "+ tipoInteligenciasMultiples+"| encuestaInteligenciasMultiples: "+ encuestaInteligenciasMultiples+" ]";
    }
    
}
