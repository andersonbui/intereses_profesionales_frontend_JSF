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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author anderson
 */
@Entity
@Table(name = "PreguntaEstilosAprendizaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreguntaEstilosAprendizaje.findAll", query = "SELECT p FROM PreguntaEstilosAprendizaje p"),
    @NamedQuery(name = "PreguntaEstilosAprendizaje.findById", query = "SELECT p FROM PreguntaEstilosAprendizaje p WHERE p.id = :id"),
    @NamedQuery(name = "PreguntaEstilosAprendizaje.findByEnunciado", query = "SELECT p FROM PreguntaEstilosAprendizaje p WHERE p.enunciado = :enunciado"),
    @NamedQuery(name = "PreguntaEstilosAprendizaje.findByUrlimagen", query = "SELECT p FROM PreguntaEstilosAprendizaje p WHERE p.urlimagen = :urlimagen"),
    @NamedQuery(name = "PreguntaEstilosAprendizaje.findByOrden", query = "SELECT p FROM PreguntaEstilosAprendizaje p WHERE p.orden = :orden")})
public class PreguntaEstilosAprendizaje implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 150)
    @Column(name = "enunciado")
    private String enunciado;
    
    @Size(max = 45)
    @Column(name = "urlimagen")
    private String urlimagen;
    
    @Column(name = "orden")
    private Integer orden;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preguntaEstilosAprendizaje")
    private List<RespuestaEstilo> respuestaEstiloList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preguntaEstilosAprendizaje")
    private List<TipoEstiloPregunta> tipoEstiloPreguntaList;

    public PreguntaEstilosAprendizaje() {
    }

    public PreguntaEstilosAprendizaje(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getUrlimagen() {
        return urlimagen;
    }

    public void setUrlimagen(String urlimagen) {
        this.urlimagen = urlimagen;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    @XmlTransient
    public List<RespuestaEstilo> getRespuestaEstiloList() {
        return respuestaEstiloList;
    }

    public void setRespuestaEstiloList(List<RespuestaEstilo> respuestaEstiloList) {
        this.respuestaEstiloList = respuestaEstiloList;
    }

    @XmlTransient
    public List<TipoEstiloPregunta> getTipoEstiloPreguntaList() {
        return tipoEstiloPreguntaList;
    }

    public void setTipoEstiloPreguntaList(List<TipoEstiloPregunta> tipoEstiloPreguntaList) {
        this.tipoEstiloPreguntaList = tipoEstiloPreguntaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreguntaEstilosAprendizaje)) {
            return false;
        }
        PreguntaEstilosAprendizaje other = (PreguntaEstilosAprendizaje) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PreguntaEstilosAprendizaje[ id=" + id + " ]";
    }
    
}
