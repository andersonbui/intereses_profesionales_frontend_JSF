/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades;

import com.ingesoft.interpro.controladores.util.PreguntaEncuesta;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author anderson
 */
@Entity
@Table(name = "PreguntaInteligenciasMultiples")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreguntaInteligenciasMultiples.findAll", query = "SELECT p FROM PreguntaInteligenciasMultiples p"),
    @NamedQuery(name = "PreguntaInteligenciasMultiples.findById", query = "SELECT p FROM PreguntaInteligenciasMultiples p WHERE p.id = :id"),
    @NamedQuery(name = "PreguntaInteligenciasMultiples.findByEnunciado", query = "SELECT p FROM PreguntaInteligenciasMultiples p WHERE p.enunciado = :enunciado"),
    @NamedQuery(name = "PreguntaInteligenciasMultiples.findByOrden", query = "SELECT p FROM PreguntaInteligenciasMultiples p WHERE p.orden = :orden")})
public class PreguntaInteligenciasMultiples extends PreguntaEncuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "enunciado")
    private String enunciado;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "orden")
    private short orden;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preguntaInteligenciasMultiples")
    private List<RespuestaInteligenciasMultiples> respuestaInteligenciasMultiplesList;
    
    @JoinColumn(name = "idTipoInteligenciasMultiples", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoInteligenciasMultiples idTipoInteligenciasMultiples;

    public PreguntaInteligenciasMultiples() {
    }

    public PreguntaInteligenciasMultiples(Integer id) {
        this.id = id;
    }

    public PreguntaInteligenciasMultiples(Integer id, String enunciado, short orden) {
        this.id = id;
        this.enunciado = enunciado;
        this.orden = orden;
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

    public short getOrden() {
        return orden;
    }

    public void setOrden(short orden) {
        this.orden = orden;
    }

    @XmlTransient
    public List<RespuestaInteligenciasMultiples> getRespuestaInteligenciasMultiplesList() {
        return respuestaInteligenciasMultiplesList;
    }

    public void setRespuestaInteligenciasMultiplesList(List<RespuestaInteligenciasMultiples> respuestaInteligenciasMultiplesList) {
        this.respuestaInteligenciasMultiplesList = respuestaInteligenciasMultiplesList;
    }

    public TipoInteligenciasMultiples getIdTipoInteligenciasMultiples() {
        return idTipoInteligenciasMultiples;
    }

    public void setIdTipoInteligenciasMultiples(TipoInteligenciasMultiples idTipoInteligenciasMultiples) {
        this.idTipoInteligenciasMultiples = idTipoInteligenciasMultiples;
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
        if (!(object instanceof PreguntaInteligenciasMultiples)) {
            return false;
        }
        PreguntaInteligenciasMultiples other = (PreguntaInteligenciasMultiples) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.PreguntaInteligenciasMultiples[ id=" + id + " ]";
    }
    
}
