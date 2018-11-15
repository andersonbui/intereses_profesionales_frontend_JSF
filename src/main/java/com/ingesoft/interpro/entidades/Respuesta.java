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
@Table(name = "Respuesta", catalog = "interpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Respuesta.findAll", query = "SELECT r FROM Respuesta r")
    , @NamedQuery(name = "Respuesta.findByIdPregunta", query = "SELECT r FROM Respuesta r WHERE r.respuestaPK.idPregunta = :idPregunta")
    , @NamedQuery(name = "Respuesta.findByRespuesta", query = "SELECT r FROM Respuesta r WHERE r.respuesta = :respuesta")
    , @NamedQuery(name = "Respuesta.findByIdEncuesta", query = "SELECT r FROM Respuesta r WHERE r.respuestaPK.idEncuesta = :idEncuesta")})
public class Respuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RespuestaPK respuestaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "respuesta")
    private float respuesta;
    @JoinColumn(name = "idEncuesta", referencedColumnName = "idEncuesta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Encuesta encuesta;
    @JoinColumn(name = "idPregunta", referencedColumnName = "idPregunta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pregunta pregunta;

    public Respuesta() {
    }

    public Respuesta(RespuestaPK respuestaPK) {
        this.respuestaPK = respuestaPK;
    }

    public Respuesta(RespuestaPK respuestaPK, float respuesta) {
        this.respuestaPK = respuestaPK;
        this.respuesta = respuesta;
    }

    public Respuesta(int idPregunta, int idEncuesta) {
        this.respuestaPK = new RespuestaPK(idPregunta, idEncuesta);
    }

    public RespuestaPK getRespuestaPK() {
        return respuestaPK;
    }

    public void setRespuestaPK(RespuestaPK respuestaPK) {
        this.respuestaPK = respuestaPK;
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

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (respuestaPK != null ? respuestaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Respuesta)) {
            return false;
        }
        Respuesta other = (Respuesta) object;
        if ((this.respuestaPK == null && other.respuestaPK != null) || (this.respuestaPK != null && !this.respuestaPK.equals(other.respuestaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.Respuesta[ respuestaPK=" + respuestaPK + "; respuesta: " + this.respuesta + " ]";
    }

}
