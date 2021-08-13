/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades;

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
 * @author anderson
 */
@Entity
@Table(name = "RespuestaPorEstilo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RespuestaPorEstilo.findAll", query = "SELECT r FROM RespuestaPorEstilo r"),
    @NamedQuery(name = "RespuestaPorEstilo.findByIdTipoEstilo", query = "SELECT r FROM RespuestaPorEstilo r WHERE r.respuestaPorEstiloPK.idTipoEstilo = :idTipoEstilo"),
    @NamedQuery(name = "RespuestaPorEstilo.findByIdEncuestaEstilosAprendizaje", query = "SELECT r FROM RespuestaPorEstilo r WHERE r.respuestaPorEstiloPK.idEncuestaEstilosAprendizaje = :idEncuestaEstilosAprendizaje"),
    @NamedQuery(name = "RespuestaPorEstilo.findByRespuesta", query = "SELECT r FROM RespuestaPorEstilo r WHERE r.respuesta = :respuesta")})
public class RespuestaPorEstilo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected RespuestaPorEstiloPK respuestaPorEstiloPK;
    
    @Column(name = "respuesta")
    private Short respuesta;
    
    @JoinColumn(name = "idEncuestaEstilosAprendizaje", referencedColumnName = "idEncuesta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EncuestaEstilosAprendizaje encuestaEstilosAprendizaje;

    @JoinColumn(name = "idTipoEstilo", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TipoEstilo tipoEstilo;

    public RespuestaPorEstilo() {
    }

    public RespuestaPorEstilo(RespuestaPorEstiloPK respuestaPorEstiloPK) {
        this.respuestaPorEstiloPK = respuestaPorEstiloPK;
    }

    public RespuestaPorEstilo(int idTipoEstilo, int idEncuestaEstilosAprendizaje) {
        this.respuestaPorEstiloPK = new RespuestaPorEstiloPK(idTipoEstilo, idEncuestaEstilosAprendizaje);
    }

    public RespuestaPorEstiloPK getRespuestaPorEstiloPK() {
        return respuestaPorEstiloPK;
    }

    public void setRespuestaPorEstiloPK(RespuestaPorEstiloPK respuestaPorEstiloPK) {
        this.respuestaPorEstiloPK = respuestaPorEstiloPK;
    }

    public Short getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Short respuesta) {
        this.respuesta = respuesta;
    }

    public EncuestaEstilosAprendizaje getEncuestaEstilosAprendizaje() {
        return encuestaEstilosAprendizaje;
    }

    public void setEncuestaEstilosAprendizaje(EncuestaEstilosAprendizaje encuestaEstilosAprendizaje) {
        this.encuestaEstilosAprendizaje = encuestaEstilosAprendizaje;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (respuestaPorEstiloPK != null ? respuestaPorEstiloPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaPorEstilo)) {
            return false;
        }
        RespuestaPorEstilo other = (RespuestaPorEstilo) object;
        if ((this.respuestaPorEstiloPK == null && other.respuestaPorEstiloPK != null) || (this.respuestaPorEstiloPK != null && !this.respuestaPorEstiloPK.equals(other.respuestaPorEstiloPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RespuestaPorEstilo[ PK=" + respuestaPorEstiloPK + " ]";
    }

    public TipoEstilo getTipoEstilo() {
        return tipoEstilo;
    }

    public void setTipoEstilo(TipoEstilo tipoEstilo) {
        this.tipoEstilo = tipoEstilo;
    }
    
}
