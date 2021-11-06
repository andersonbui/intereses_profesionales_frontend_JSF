/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.chaside.entidades;

import com.ingesoft.interpro.controladores.util.RespuestaEncuestaAbstract;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "RespuestaChaside")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RespuestaChaside.findAll", query = "SELECT r FROM RespuestaChaside r"),
    @NamedQuery(name = "RespuestaChaside.findByIdPreguntaChaside", query = "SELECT r FROM RespuestaChaside r WHERE r.respuestaChasidePK.idPreguntaChaside = :idPreguntaChaside"),
    @NamedQuery(name = "RespuestaChaside.findByIdEncuestaChaside", query = "SELECT r FROM RespuestaChaside r WHERE r.respuestaChasidePK.idEncuestaChaside = :idEncuestaChaside"),
    @NamedQuery(name = "RespuestaChaside.findByRespuesta", query = "SELECT r FROM RespuestaChaside r WHERE r.respuesta = :respuesta"),
    @NamedQuery(name = "RespuestaChaside.findByFecha", query = "SELECT r FROM RespuestaChaside r WHERE r.fecha = :fecha")})
public class RespuestaChaside  extends RespuestaEncuestaAbstract
    < EncuestaChaside, PreguntaChaside> {


    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private RespuestaChasidePK respuestaChasidePK;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "respuesta")
    private short respuesta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    @JoinColumn(name="idPreguntaChaside", referencedColumnName = "idPreguntaChaside", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PreguntaChaside preguntaChaside;
    
    @JoinColumn(name="idEncuestaChaside", referencedColumnName = "idEncuesta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EncuestaChaside encuestaChaside;

    public RespuestaChaside() {
    }

    public RespuestaChaside(RespuestaChasidePK respuestaChasidePK) {
        this.respuestaChasidePK = respuestaChasidePK;
    }

    public RespuestaChaside(RespuestaChasidePK respuestaChasidePK, short respuesta, Date fecha) {
        this.respuestaChasidePK = respuestaChasidePK;
        this.respuesta = respuesta;
        this.fecha = fecha;
    }

    /**
     * 
     * @param encuestaChaside
     * @param preguntaChaside
     */
    @Override
    public void inicializar(EncuestaChaside encuestaChaside, PreguntaChaside preguntaChaside) {
        
        this.encuestaChaside = encuestaChaside;
        this.preguntaChaside = preguntaChaside;
        this.respuestaChasidePK = new RespuestaChasidePK(
            preguntaChaside.getIdPreguntaChaside(),
            encuestaChaside.getIdEncuesta()
        );
    }
    
    public RespuestaChasidePK getRespuestaChasidePK() {
        return respuestaChasidePK;
    }

    public void setRespuestaChasidePK(RespuestaChasidePK respuestaChasidePK) {
        this.respuestaChasidePK = respuestaChasidePK;
    }

    public short getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(short respuesta) {
        this.respuesta = respuesta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public PreguntaChaside getPreguntaChaside() {
        return preguntaChaside;
    }

    public void setPreguntaChaside(PreguntaChaside preguntaChaside) {
        this.preguntaChaside = preguntaChaside;
    }

    public EncuestaChaside getEncuestaChaside() {
        return encuestaChaside;
    }

    public void setEncuestaChaside(EncuestaChaside encuestaChaside) {
        this.encuestaChaside = encuestaChaside;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (respuestaChasidePK != null ? respuestaChasidePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaChaside)) {
            return false;
        }
        RespuestaChaside other = (RespuestaChaside) object;
        if ((this.respuestaChasidePK == null && other.respuestaChasidePK != null) || (this.respuestaChasidePK != null && !this.respuestaChasidePK.equals(other.respuestaChasidePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RespuestaChaside[ PK=" + respuestaChasidePK + " ]";
    }
    
}
