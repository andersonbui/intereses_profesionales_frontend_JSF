/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.chaside.entidades;

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
 * @author debian
 */
@Entity
@Table(name = "ClaseChaside")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClaseChaside.findAll", query = "SELECT c FROM ClaseChaside c"),
    @NamedQuery(name = "ClaseChaside.findByIdClaseChaside", query = "SELECT c FROM ClaseChaside c WHERE c.idClaseChaside = :idClaseChaside"),
    @NamedQuery(name = "ClaseChaside.findByNombre", query = "SELECT c FROM ClaseChaside c WHERE c.nombre = :nombre")})
public class ClaseChaside implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idClaseChaside")
    private Integer idClaseChaside;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "claseChaside")
    private List<TipoClaseChaside> tipoClaseChasideList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClaseChaside")
    private List<PreguntaChaside> preguntaChasideList;

    public ClaseChaside() {
    }

    public ClaseChaside(Integer idClaseChaside) {
        this.idClaseChaside = idClaseChaside;
    }

    public ClaseChaside(Integer idClaseChaside, String nombre) {
        this.idClaseChaside = idClaseChaside;
        this.nombre = nombre;
    }

    public Integer getIdClaseChaside() {
        return idClaseChaside;
    }

    public void setIdClaseChaside(Integer idClaseChaside) {
        this.idClaseChaside = idClaseChaside;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<TipoClaseChaside> getTipoClaseChasideList() {
        return tipoClaseChasideList;
    }

    public void setTipoClaseChasideList(List<TipoClaseChaside> tipoClaseChasideList) {
        this.tipoClaseChasideList = tipoClaseChasideList;
    }

    @XmlTransient
    public List<PreguntaChaside> getPreguntaChasideList() {
        return preguntaChasideList;
    }

    public void setPreguntaChasideList(List<PreguntaChaside> preguntaChasideList) {
        this.preguntaChasideList = preguntaChasideList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClaseChaside != null ? idClaseChaside.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClaseChaside)) {
            return false;
        }
        ClaseChaside other = (ClaseChaside) object;
        if ((this.idClaseChaside == null && other.idClaseChaside != null) || (this.idClaseChaside != null && !this.idClaseChaside.equals(other.idClaseChaside))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.suideal.encuesta.chaside.entidades.ClaseChaside[ idClaseChaside=" + idClaseChaside + " ]";
    }
    
}
