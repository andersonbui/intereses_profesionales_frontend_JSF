/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades;

import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.TipoEstiloPregunta;
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
@Table(name = "TipoEstilo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEstilo.findAll", query = "SELECT t FROM TipoEstilo t"),
    @NamedQuery(name = "TipoEstilo.findByIdTipoEstilo", query = "SELECT t FROM TipoEstilo t WHERE t.id = :id"),
    @NamedQuery(name = "TipoEstilo.findByNombre", query = "SELECT t FROM TipoEstilo t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoEstilo.findByDefinicion", query = "SELECT t FROM TipoEstilo t WHERE t.definicion = :definicion"),
    @NamedQuery(name = "TipoEstilo.findByColor", query = "SELECT t FROM TipoEstilo t WHERE t.color = :color")})
public class TipoEstilo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 12)
    @Column(name = "nombre")
    private String nombre;
    
    @Size(max = 500)
    @Column(name = "definicion")
    private String definicion;
    
    @Size(max = 20)
    @Column(name = "color")
    private String color;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoEstilo")
    private List<TipoEstiloPregunta> tipoEstiloPreguntaList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoEstilo")
    private List<RespuestaPorEstilo> respuestaPorEstiloList;
    
    public TipoEstilo() {
    }

    public TipoEstilo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDefinicion() {
        return definicion;
    }

    public void setDefinicion(String definicion) {
        this.definicion = definicion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @XmlTransient
    public List<TipoEstiloPregunta> getTipoEstiloPreguntaList() {
        return tipoEstiloPreguntaList;
    }

    public void setTipoEstiloPreguntaList(List<TipoEstiloPregunta> tipoEstiloPreguntaList) {
        this.tipoEstiloPreguntaList = tipoEstiloPreguntaList;
    }

    @XmlTransient
    public List<RespuestaPorEstilo> getRespuestaPorEstiloList() {
        return respuestaPorEstiloList;
    }

    public void setRespuestaPorEstiloList(List<RespuestaPorEstilo> respuestaPorEstiloList) {
        this.respuestaPorEstiloList = respuestaPorEstiloList;
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
        if (!(object instanceof TipoEstilo)) {
            return false;
        }
        TipoEstilo other = (TipoEstilo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TipoEstilo[ id=" + id + "| nombre: " + nombre + " ]";
    }
    
}
