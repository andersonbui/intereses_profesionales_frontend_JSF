/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades;

import com.ingesoft.interpro.controladores.util.RespuestaEncuestaAbstract;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Personal
 */
@Entity
@Table(name = "RespuestaEstilo")
@NamedQueries({
    @NamedQuery(name = "RespuestaEstilo.findAll", query = "SELECT r FROM RespuestaEstilo r"),
    @NamedQuery(name = "RespuestaEstilo.findByIdPreguntaEstilos", query = "SELECT r FROM RespuestaEstilo r WHERE r.respuestaEstiloPK.idPreguntaEstilosAprendizaje = :idPreguntaEstilosAprendizaje"),
    @NamedQuery(name = "RespuestaEstilo.findByIdEncuestaEstilosAprendizaje", query = "SELECT r FROM RespuestaEstilo r WHERE r.respuestaEstiloPK.idEncuestaEstilosAprendizaje = :idEncuestaEstilosAprendizaje"),
    @NamedQuery(name = "RespuestaEstilo.findByRespuesta", query = "SELECT r FROM RespuestaEstilo r WHERE r.respuesta = :respuesta"),
    @NamedQuery(name = "RespuestaEstilo.findByEncuestaEstilosAprendizaje", query = "SELECT r FROM RespuestaEstilo r WHERE r.encuestaEstilosAprendizaje = :encuestaEstilosAprendizaje")
})
public class RespuestaEstilo  extends RespuestaEncuestaAbstract 
    < EncuestaEstilosAprendizaje, PreguntaEstilosAprendizaje >{

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private RespuestaEstiloPK respuestaEstiloPK;
    
    @Column(name = "respuesta")
    private Character respuesta;
    
    @JoinColumn(name = "idPreguntaEstilosAprendizaje", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PreguntaEstilosAprendizaje preguntaEstilosAprendizaje;

    
    @JoinColumn(name = "idEncuestaEstilosAprendizaje", referencedColumnName = "idEncuesta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EncuestaEstilosAprendizaje encuestaEstilosAprendizaje;
    
    public RespuestaEstilo() {
    }
    
    public RespuestaEstilo(PreguntaEstilosAprendizaje idpreguntaEstilos, EncuestaEstilosAprendizaje encuestaEstilosAprendizaje) {
        this.preguntaEstilosAprendizaje = idpreguntaEstilos;
        this.encuestaEstilosAprendizaje = encuestaEstilosAprendizaje;
        this.respuestaEstiloPK = new RespuestaEstiloPK();
        this.respuestaEstiloPK.setIdEncuestaEstilosAprendizaje(encuestaEstilosAprendizaje.getIdEncuesta());
        this.respuestaEstiloPK.setIdPreguntaEstilosAprendizaje(idpreguntaEstilos.getId());
    }
    
    /**
     * 
     * @param encuestaEstilosAprendizaje
     * @param preguntaEstilosAprendizaje
     */
    @Override
    public void inicializar(
            EncuestaEstilosAprendizaje encuestaEstilosAprendizaje, 
            PreguntaEstilosAprendizaje preguntaEstilosAprendizaje) {
        
        this.encuestaEstilosAprendizaje = encuestaEstilosAprendizaje;
        this.preguntaEstilosAprendizaje = preguntaEstilosAprendizaje;
        this.respuestaEstiloPK = new RespuestaEstiloPK(
            preguntaEstilosAprendizaje.getId(),
            encuestaEstilosAprendizaje.getIdEncuesta()
        );
    }

    @Override
    public boolean isValid() {
        return !(respuesta == null || "".equals(respuesta.toString()));
    }
    
    public RespuestaEstiloPK getRespuestaEstiloPK() {
        return respuestaEstiloPK;
    }

    public void setRespuestaEstiloPK(RespuestaEstiloPK respuestaEstiloPK) {
        this.respuestaEstiloPK = respuestaEstiloPK;
    }

    public Character getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Character respuesta) {
        this.respuesta = respuesta;
    }

    public EncuestaEstilosAprendizaje getEncuestaEstilosAprendizaje() {
        return encuestaEstilosAprendizaje;
    }

    public void setEncuestaEstilosAprendizaje(EncuestaEstilosAprendizaje encuestaidEncuesta) {
        this.encuestaEstilosAprendizaje = encuestaidEncuesta;
    }

    public PreguntaEstilosAprendizaje getIdpreguntaEstilos() {
        return preguntaEstilosAprendizaje;
    }

    public void setIdpreguntaEstilos(PreguntaEstilosAprendizaje idpreguntaEstilos) {
        this.preguntaEstilosAprendizaje = idpreguntaEstilos;
    }

    public PreguntaEstilosAprendizaje getPreguntaEstilosAprendizaje() {
        return preguntaEstilosAprendizaje;
    }

    public void setPreguntaEstilosAprendizaje(PreguntaEstilosAprendizaje preguntaEstilosAprendizaje) {
        this.preguntaEstilosAprendizaje = preguntaEstilosAprendizaje;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (respuestaEstiloPK != null ? respuestaEstiloPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaEstilo)) {
            return false;
        }
        RespuestaEstilo other = (RespuestaEstilo) object;
        if ((this.respuestaEstiloPK == null && other.respuestaEstiloPK != null) || (this.respuestaEstiloPK != null && !this.respuestaEstiloPK.equals(other.respuestaEstiloPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RespuestaEstilo[ PK=" + respuestaEstiloPK + " ]";
    }
    
}
