/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades;

import com.ingesoft.interpro.controladores.util.RespuestaEncuestaAbstract;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author anderson
 */
@Entity
@Table(name = "RespuestaInteligenciasMultiples")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RespuestaInteligenciasMultiples.findAll", query = "SELECT r FROM RespuestaInteligenciasMultiples r"),
    @NamedQuery(name = "RespuestaInteligenciasMultiples.findByIdEncuestaInteligenciasMultiples", query = "SELECT r FROM RespuestaInteligenciasMultiples r WHERE r.respuestaInteligenciasMultiplesPK.idEncuestaInteligenciasMultiples = :idEncuestaInteligenciasMultiples"),
    @NamedQuery(name = "RespuestaInteligenciasMultiples.findByIdPreguntaInteligenciasMultiples", query = "SELECT r FROM RespuestaInteligenciasMultiples r WHERE r.respuestaInteligenciasMultiplesPK.idPreguntaInteligenciasMultiples = :idPreguntaInteligenciasMultiples"),
    @NamedQuery(name = "RespuestaInteligenciasMultiples.findByRespuesta", query = "SELECT r FROM RespuestaInteligenciasMultiples r WHERE r.respuesta = :respuesta")})
public class RespuestaInteligenciasMultiples extends RespuestaEncuestaAbstract  {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected RespuestaInteligenciasMultiplesPK respuestaInteligenciasMultiplesPK;
    
    @Column(name = "respuesta")
    private Short respuesta;
    
    @JoinColumn(name = "idEncuestaInteligenciasMultiples", referencedColumnName = "idEncuesta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EncuestaInteligenciasMultiples encuestaInteligenciasMultiples;
    
    @JoinColumn(name = "idPreguntaInteligenciasMultiples", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PreguntaInteligenciasMultiples preguntaInteligenciasMultiples;
    
    @Transient
    private boolean estaRespondida;

    public RespuestaInteligenciasMultiples() {
        this.estaRespondida = false;
    }

    public RespuestaInteligenciasMultiples(RespuestaInteligenciasMultiplesPK respuestaInteligenciasMultiplesPK) {
        this.respuestaInteligenciasMultiplesPK = respuestaInteligenciasMultiplesPK;
    }

    public void inicializar(
            EncuestaInteligenciasMultiples encuestaInteligenciasMultiples, 
            PreguntaInteligenciasMultiples preguntaInteligenciasMultiples) {
        
        this.encuestaInteligenciasMultiples = encuestaInteligenciasMultiples;
        this.preguntaInteligenciasMultiples = preguntaInteligenciasMultiples;
        this.respuestaInteligenciasMultiplesPK = new RespuestaInteligenciasMultiplesPK(
            encuestaInteligenciasMultiples.getIdEncuesta(), 
            preguntaInteligenciasMultiples.getId()
        );
    }
    public RespuestaInteligenciasMultiples(int idEncuestaInteligenciasMultiples, int idPreguntaInteligenciasMultiples) {
        this.respuestaInteligenciasMultiplesPK = new RespuestaInteligenciasMultiplesPK(idEncuestaInteligenciasMultiples, idPreguntaInteligenciasMultiples);
    }

    public RespuestaInteligenciasMultiplesPK getRespuestaInteligenciasMultiplesPK() {
        return respuestaInteligenciasMultiplesPK;
    }

    public void setRespuestaInteligenciasMultiplesPK(RespuestaInteligenciasMultiplesPK respuestaInteligenciasMultiplesPK) {
        this.respuestaInteligenciasMultiplesPK = respuestaInteligenciasMultiplesPK;
    }

    public Short getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Short respuesta) {
        this.respuesta = respuesta;
    }

    public EncuestaInteligenciasMultiples getEncuestaInteligenciasMultiples() {
        return encuestaInteligenciasMultiples;
    }

    public void setEncuestaInteligenciasMultiples(EncuestaInteligenciasMultiples encuestaInteligenciasMultiples) {
        this.encuestaInteligenciasMultiples = encuestaInteligenciasMultiples;
    }

    public PreguntaInteligenciasMultiples getPreguntaInteligenciasMultiples() {
        return preguntaInteligenciasMultiples;
    }

    public void setPreguntaInteligenciasMultiples(PreguntaInteligenciasMultiples preguntaInteligenciasMultiples) {
        this.preguntaInteligenciasMultiples = preguntaInteligenciasMultiples;
    }

    public boolean isRespondida() {
        return estaRespondida;
    }

    public void setEstaRespondida(boolean estaRespondida) {
        this.estaRespondida = estaRespondida;
    }
    
    @Override
    public void responder() {
        setEstaRespondida(true);
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (respuestaInteligenciasMultiplesPK != null ? respuestaInteligenciasMultiplesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaInteligenciasMultiples)) {
            return false;
        }
        RespuestaInteligenciasMultiples other = (RespuestaInteligenciasMultiples) object;
        if ((this.respuestaInteligenciasMultiplesPK == null && other.respuestaInteligenciasMultiplesPK != null) || (this.respuestaInteligenciasMultiplesPK != null && !this.respuestaInteligenciasMultiplesPK.equals(other.respuestaInteligenciasMultiplesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RespuestaIntelMultiples[ PK=" + respuestaInteligenciasMultiplesPK + "| respuesta:"+respuesta+"|estaRespondida:"+estaRespondida + " ]";
    }
    
}
