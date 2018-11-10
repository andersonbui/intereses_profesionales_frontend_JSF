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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "Pregunta", catalog = "interpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pregunta.findAll", query = "SELECT p FROM Pregunta p")
    , @NamedQuery(name = "Pregunta.findByIdPregunta", query = "SELECT p FROM Pregunta p WHERE p.idPregunta = :idPregunta")
    , @NamedQuery(name = "Pregunta.findByEnunciado", query = "SELECT p FROM Pregunta p WHERE p.enunciado = :enunciado")
    , @NamedQuery(name = "Pregunta.findBySegundoEnunciado", query = "SELECT p FROM Pregunta p WHERE p.segundoEnunciado = :segundoEnunciado")
    , @NamedQuery(name = "Pregunta.findBySuma", query = "SELECT p FROM Pregunta p WHERE p.suma = :suma")
    , @NamedQuery(name = "Pregunta.findByOrden", query = "SELECT p FROM Pregunta p WHERE p.orden = :orden")
    , @NamedQuery(name = "Pregunta.findByIdTipo", query = "SELECT p FROM Pregunta p WHERE p.idTipo.idTipo = :idTipo")
    , @NamedQuery(name = "Pregunta.findByUrlImagen", query = "SELECT p FROM Pregunta p WHERE p.urlImagen = :urlImagen")})

public class Pregunta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPregunta")
    private Integer idPregunta;
    @Size(max = 100)
    @Column(name = "enunciado")
    private String enunciado;
    @Size(max = 100)
    @Column(name = "segundo_enunciado")
    private String segundoEnunciado;
    @Column(name = "suma")
    private Boolean suma;
    @Column(name = "orden")
    private Integer orden;
    @Size(max = 100)
    @Column(name = "urlImagen")
    private String urlImagen;
    @JoinColumn(name = "idTipo", referencedColumnName = "idTipo")
    @ManyToOne(optional = false)
    private Tipo idTipo;

    public Pregunta() {
    }

    public Pregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Integer getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getSegundoEnunciado() {
        return segundoEnunciado;
    }

    public void setSegundoEnunciado(String segundoEnunciado) {
        this.segundoEnunciado = segundoEnunciado;
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

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Tipo getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Tipo idTipo) {
        this.idTipo = idTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPregunta != null ? idPregunta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pregunta)) {
            return false;
        }
        Pregunta other = (Pregunta) object;
        if ((this.idPregunta == null && other.idPregunta != null) || (this.idPregunta != null && !this.idPregunta.equals(other.idPregunta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.Pregunta[ idPregunta=" + idPregunta + " ]";
    }
    
}
