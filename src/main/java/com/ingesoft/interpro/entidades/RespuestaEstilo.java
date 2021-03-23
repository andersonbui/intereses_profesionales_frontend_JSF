/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @NamedQuery(name = "RespuestaEstilo.findByIdRespuestaEstilo", query = "SELECT r FROM RespuestaEstilo r WHERE r.idRespuestaEstilo = :idRespuestaEstilo"),
    @NamedQuery(name = "RespuestaEstilo.findByRespuesta", query = "SELECT r FROM RespuestaEstilo r WHERE r.respuesta = :respuesta"),
    @NamedQuery(name = "RespuestaEstilo.findByEncuesta", query = "SELECT r FROM RespuestaEstilo r WHERE r.idEncuesta = :encuesta")
})
public class RespuestaEstilo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRespuestaEstilo")
    private Integer idRespuestaEstilo;
    @Column(name = "respuesta")
    private Character respuesta;
    @JoinColumn(name = "Encuesta_idEncuesta", referencedColumnName = "idEncuesta")
    @ManyToOne(optional = false)
    private Encuesta idEncuesta;
    @JoinColumn(name = "idpregunta_estilos", referencedColumnName = "idpregunta_estilos")
    @ManyToOne(optional = false)
    private PreguntaEstilosAprendizajeFs idpreguntaEstilos;

    public RespuestaEstilo() {
    }

    public RespuestaEstilo(Integer idRespuestaEstilo) {
        this.idRespuestaEstilo = idRespuestaEstilo;
    }

    public Integer getIdRespuestaEstilo() {
        return idRespuestaEstilo;
    }

    public void setIdRespuestaEstilo(Integer idRespuestaEstilo) {
        this.idRespuestaEstilo = idRespuestaEstilo;
    }

    public Character getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Character respuesta) {
        this.respuesta = respuesta;
    }

    public Encuesta getEncuestaidEncuesta() {
        return idEncuesta;
    }

    public void setEncuestaidEncuesta(Encuesta encuestaidEncuesta) {
        this.idEncuesta = encuestaidEncuesta;
    }

    public PreguntaEstilosAprendizajeFs getIdpreguntaEstilos() {
        return idpreguntaEstilos;
    }

    public void setIdpreguntaEstilos(PreguntaEstilosAprendizajeFs idpreguntaEstilos) {
        this.idpreguntaEstilos = idpreguntaEstilos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRespuestaEstilo != null ? idRespuestaEstilo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaEstilo)) {
            return false;
        }
        RespuestaEstilo other = (RespuestaEstilo) object;
        if ((this.idRespuestaEstilo == null && other.idRespuestaEstilo != null) || (this.idRespuestaEstilo != null && !this.idRespuestaEstilo.equals(other.idRespuestaEstilo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.RespuestaEstilo[ idRespuestaEstilo=" + idRespuestaEstilo + " ]";
    }
    
}
