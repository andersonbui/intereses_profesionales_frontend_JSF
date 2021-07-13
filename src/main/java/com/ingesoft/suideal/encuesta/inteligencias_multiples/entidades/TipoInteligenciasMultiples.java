/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "TipoInteligenciasMultiples")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoInteligenciasMultiples.findAll", query = "SELECT t FROM TipoInteligenciasMultiples t"),
    @NamedQuery(name = "TipoInteligenciasMultiples.findById", query = "SELECT t FROM TipoInteligenciasMultiples t WHERE t.id = :id"),
    @NamedQuery(name = "TipoInteligenciasMultiples.findByTipo", query = "SELECT t FROM TipoInteligenciasMultiples t WHERE t.tipo = :tipo"),
    @NamedQuery(name = "TipoInteligenciasMultiples.findByDescripcion", query = "SELECT t FROM TipoInteligenciasMultiples t WHERE t.descripcion = :descripcion")})
public class TipoInteligenciasMultiples implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipo")
    private String tipo;
    
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoInteligenciasMultiples")
    private List<PreguntaInteligenciasMultiples> preguntaInteligenciasMultiplesList;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "tipoInteligenciasMultiples")
    private RespuestaPorInteligencia respuestaPorInteligencia;

    public TipoInteligenciasMultiples() {
    }

    public TipoInteligenciasMultiples(Integer id) {
        this.id = id;
    }

    public TipoInteligenciasMultiples(Integer id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<PreguntaInteligenciasMultiples> getPreguntaInteligenciasMultiplesList() {
        return preguntaInteligenciasMultiplesList;
    }

    public void setPreguntaInteligenciasMultiplesList(List<PreguntaInteligenciasMultiples> preguntaInteligenciasMultiplesList) {
        this.preguntaInteligenciasMultiplesList = preguntaInteligenciasMultiplesList;
    }

    public RespuestaPorInteligencia getRespuestaPorInteligencia() {
        return respuestaPorInteligencia;
    }

    public void setRespuestaPorInteligencia(RespuestaPorInteligencia respuestaPorInteligencia) {
        this.respuestaPorInteligencia = respuestaPorInteligencia;
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
        if (!(object instanceof TipoInteligenciasMultiples)) {
            return false;
        }
        TipoInteligenciasMultiples other = (TipoInteligenciasMultiples) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TipoInteligenciasMultiples[ id=" + id + "| tipo=" + tipo + "| descripcion:" + descripcion + " ]";
    }
    
}
