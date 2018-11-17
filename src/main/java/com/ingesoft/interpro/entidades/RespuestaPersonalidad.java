/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "RespuestaPersonalidad", catalog = "interpro2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RespuestaPersonalidad.findAll", query = "SELECT r FROM RespuestaPersonalidad r")
    , @NamedQuery(name = "RespuestaPersonalidad.findByIdPreguntaPersonalidad", query = "SELECT r FROM RespuestaPersonalidad r WHERE r.respuestaPersonalidadPK.idPreguntaPersonalidad = :idPreguntaPersonalidad")
    , @NamedQuery(name = "RespuestaPersonalidad.findByRespuesta", query = "SELECT r FROM RespuestaPersonalidad r WHERE r.respuesta = :respuesta")
    , @NamedQuery(name = "RespuestaPersonalidad.findByIdEncuesta", query = "SELECT r FROM RespuestaPersonalidad r WHERE r.respuestaPersonalidadPK.idEncuesta = :idEncuesta")})
public class RespuestaPersonalidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RespuestaPersonalidadPK respuestaPersonalidadPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "respuesta")
    private float respuesta;
    @JoinColumn(name = "idEncuesta", referencedColumnName = "idEncuesta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Encuesta encuesta;
    @JoinColumn(name = "idPreguntaPersonalidad", referencedColumnName = "idPregunta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PreguntaPersonalidad preguntaPersonalidad;

    public RespuestaPersonalidad() {
    }

    public RespuestaPersonalidad(RespuestaPersonalidadPK respuestaPersonalidadPK) {
        this.respuestaPersonalidadPK = respuestaPersonalidadPK;
    }

    public RespuestaPersonalidad(RespuestaPersonalidadPK respuestaPersonalidadPK, float respuesta) {
        this.respuestaPersonalidadPK = respuestaPersonalidadPK;
        this.respuesta = respuesta;
    }

    public RespuestaPersonalidad(int idPreguntaPersonalidad, int idEncuesta) {
        this.respuestaPersonalidadPK = new RespuestaPersonalidadPK(idPreguntaPersonalidad, idEncuesta);
    }

    public RespuestaPersonalidadPK getRespuestaPersonalidadPK() {
        return respuestaPersonalidadPK;
    }

    public void setRespuestaPersonalidadPK(RespuestaPersonalidadPK respuestaPersonalidadPK) {
        this.respuestaPersonalidadPK = respuestaPersonalidadPK;
    }

    public float getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(float respuesta) {
        this.respuesta = respuesta;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }

    public PreguntaPersonalidad getPreguntaPersonalidad() {
        return preguntaPersonalidad;
    }

    public void setPreguntaPersonalidad(PreguntaPersonalidad preguntaPersonalidad) {
        this.preguntaPersonalidad = preguntaPersonalidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (respuestaPersonalidadPK != null ? respuestaPersonalidadPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaPersonalidad)) {
            return false;
        }
        RespuestaPersonalidad other = (RespuestaPersonalidad) object;
        if ((this.respuestaPersonalidadPK == null && other.respuestaPersonalidadPK != null) || (this.respuestaPersonalidadPK != null && !this.respuestaPersonalidadPK.equals(other.respuestaPersonalidadPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingeniosoft.interpro.entidades.RespuestaPersonalidad[ respuestaPersonalidadPK=" + respuestaPersonalidadPK + " ]";
    }
    
}
