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
@Table(name = "RespuestaAmbiente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RespuestaAmbiente.findAll", query = "SELECT r FROM RespuestaAmbiente r")
    , @NamedQuery(name = "RespuestaAmbiente.findByIdPreguntasAmbiente", query = "SELECT r FROM RespuestaAmbiente r WHERE r.respuestaAmbientePK.idPreguntasAmbiente = :idPreguntasAmbiente")
    , @NamedQuery(name = "RespuestaAmbiente.findByIdEncuesta", query = "SELECT r FROM RespuestaAmbiente r WHERE r.respuestaAmbientePK.idEncuesta = :idEncuesta")
    , @NamedQuery(name = "RespuestaAmbiente.findByEncuesta", query = "SELECT r FROM RespuestaAmbiente r WHERE r.encuesta = :encuesta")
    , @NamedQuery(name = "RespuestaAmbiente.findByRespuesta", query = "SELECT r FROM RespuestaAmbiente r WHERE r.respuesta = :respuesta")})
public class RespuestaAmbiente implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RespuestaAmbientePK respuestaAmbientePK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "respuesta")
    private Float respuesta;
    @JoinColumn(name = "idEncuesta", referencedColumnName = "idEncuesta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Encuesta encuesta;
    @JoinColumn(name = "idPreguntasAmbiente", referencedColumnName = "idPreguntaAmbiente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PreguntaAmbiente preguntaAmbiente;

    public RespuestaAmbiente() {
    }

    public RespuestaAmbiente(PreguntaAmbiente preguntaAmbiente, Encuesta idEncuesta) {
        this.preguntaAmbiente = preguntaAmbiente;
        this.encuesta = idEncuesta;
        this.respuestaAmbientePK = new RespuestaAmbientePK();
        this.respuestaAmbientePK.setIdEncuesta(idEncuesta.getIdEncuesta());
        this.respuestaAmbientePK.setIdPreguntasAmbiente(preguntaAmbiente.getIdPreguntaAmbiente());
        this.setRespuesta(Float.NaN);
    }
    
    public RespuestaAmbiente(RespuestaAmbientePK respuestaAmbientePK) {
        this.respuestaAmbientePK = respuestaAmbientePK;
    }

    public RespuestaAmbiente(int idPreguntasAmbiente, int idEncuesta) {
        this.respuestaAmbientePK = new RespuestaAmbientePK(idPreguntasAmbiente, idEncuesta);
    }

    public RespuestaAmbientePK getRespuestaAmbientePK() {
        return respuestaAmbientePK;
    }

    public void setRespuestaAmbientePK(RespuestaAmbientePK respuestaAmbientePK) {
        this.respuestaAmbientePK = respuestaAmbientePK;
    }

    public Float getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Float respuesta) {
        this.respuesta = respuesta;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }

    public PreguntaAmbiente getPreguntaAmbiente() {
        return preguntaAmbiente;
    }

    public void setPreguntaAmbiente(PreguntaAmbiente preguntaAmbiente) {
        this.preguntaAmbiente = preguntaAmbiente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (respuestaAmbientePK != null ? respuestaAmbientePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaAmbiente)) {
            return false;
        }
        RespuestaAmbiente other = (RespuestaAmbiente) object;
        if ((this.respuestaAmbientePK == null && other.respuestaAmbientePK != null) || (this.respuestaAmbientePK != null && !this.respuestaAmbientePK.equals(other.respuestaAmbientePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RespuestaAmbiente[ respuestaAmbientePK=" + respuestaAmbientePK +", respuesta: "+respuesta +" ]";
    }
    
}
