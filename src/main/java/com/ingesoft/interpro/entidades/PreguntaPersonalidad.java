/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "PreguntaPersonalidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreguntaPersonalidad.findAll", query = "SELECT p FROM PreguntaPersonalidad p")
    , @NamedQuery(name = "PreguntaPersonalidad.findByIdPreguntaPersonalidad", query = "SELECT p FROM PreguntaPersonalidad p WHERE p.idPreguntaPersonalidad = :idPreguntaPersonalidad")
    , @NamedQuery(name = "PreguntaPersonalidad.findByEnunciado1", query = "SELECT p FROM PreguntaPersonalidad p WHERE p.enunciado1 = :enunciado1")
    , @NamedQuery(name = "PreguntaPersonalidad.findBySuma", query = "SELECT p FROM PreguntaPersonalidad p WHERE p.suma = :suma")
    , @NamedQuery(name = "PreguntaPersonalidad.findByOrden", query = "SELECT p FROM PreguntaPersonalidad p WHERE p.orden = :orden")
    , @NamedQuery(name = "PreguntaPersonalidad.findByEnunciado2", query = "SELECT p FROM PreguntaPersonalidad p WHERE p.enunciado2 = :enunciado2")})
public class PreguntaPersonalidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPreguntaPersonalidad")
    private Integer idPreguntaPersonalidad;
    @Size(max = 50)
    @Column(name = "enunciado1")
    private String enunciado1;
    @Column(name = "suma")
    private Boolean suma;
    @Column(name = "orden")
    private Integer orden;
    @Size(max = 50)
    @Column(name = "enunciado2")
    private String enunciado2;
    @JoinColumn(name = "idTipoPersonalidad", referencedColumnName = "idTipoPersonalidad")
    @ManyToOne(optional = false)
    private TipoPersonalidad idTipoPersonalidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preguntaPersonalidad")
    private List<RespuestaPersonalidad> respuestaPersonalidadList;

    public PreguntaPersonalidad() {
    }

    public PreguntaPersonalidad(Integer idPreguntaPersonalidad) {
        this.idPreguntaPersonalidad = idPreguntaPersonalidad;
    }

    public Integer getIdPreguntaPersonalidad() {
        return idPreguntaPersonalidad;
    }

    public void setIdPreguntaPersonalidad(Integer idPreguntaPersonalidad) {
        this.idPreguntaPersonalidad = idPreguntaPersonalidad;
    }

    public String getEnunciado1() {
        return enunciado1;
    }

    public void setEnunciado1(String enunciado1) {
        this.enunciado1 = enunciado1;
    }

    public Boolean getSuma() {
        return suma;
    }

    public void setSuma(Boolean suma) {
        this.suma = suma;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getEnunciado2() {
        return enunciado2;
    }

    public void setEnunciado2(String enunciado2) {
        this.enunciado2 = enunciado2;
    }

    public TipoPersonalidad getIdTipoPersonalidad() {
        return idTipoPersonalidad;
    }

    public void setIdTipoPersonalidad(TipoPersonalidad idTipoPersonalidad) {
        this.idTipoPersonalidad = idTipoPersonalidad;
    }

    @XmlTransient
    public List<RespuestaPersonalidad> getRespuestaPersonalidadList() {
        return respuestaPersonalidadList;
    }

    public void setRespuestaPersonalidadList(List<RespuestaPersonalidad> respuestaPersonalidadList) {
        this.respuestaPersonalidadList = respuestaPersonalidadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPreguntaPersonalidad != null ? idPreguntaPersonalidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreguntaPersonalidad)) {
            return false;
        }
        PreguntaPersonalidad other = (PreguntaPersonalidad) object;
        if ((this.idPreguntaPersonalidad == null && other.idPreguntaPersonalidad != null) || (this.idPreguntaPersonalidad != null && !this.idPreguntaPersonalidad.equals(other.idPreguntaPersonalidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.PreguntaPersonalidad[ idPreguntaPersonalidad=" + idPreguntaPersonalidad + " ]";
    }
    
}
