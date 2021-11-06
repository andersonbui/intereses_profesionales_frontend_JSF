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
@Table(name = "TipoChaside")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoChaside.findAll", query = "SELECT t FROM TipoChaside t"),
    @NamedQuery(name = "TipoChaside.findByIdTipoChaside", query = "SELECT t FROM TipoChaside t WHERE t.idTipoChaside = :idTipoChaside"),
    @NamedQuery(name = "TipoChaside.findByNombre", query = "SELECT t FROM TipoChaside t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoChaside.findByProfesion", query = "SELECT t FROM TipoChaside t WHERE t.profesion = :profesion")})
public class TipoChaside implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipoChaside")
    private Integer idTipoChaside;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "profesion")
    private String profesion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoChaside")
    private List<TipoClaseChaside> tipoClaseChasideList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoChaside")
    private List<PreguntaChaside> preguntaChasideList;

    public TipoChaside() {
    }

    public TipoChaside(Integer idTipoChaside) {
        this.idTipoChaside = idTipoChaside;
    }

    public TipoChaside(Integer idTipoChaside, String nombre, String profesion) {
        this.idTipoChaside = idTipoChaside;
        this.nombre = nombre;
        this.profesion = profesion;
    }

    public Integer getIdTipoChaside() {
        return idTipoChaside;
    }

    public void setIdTipoChaside(Integer idTipoChaside) {
        this.idTipoChaside = idTipoChaside;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
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
        hash += (idTipoChaside != null ? idTipoChaside.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoChaside)) {
            return false;
        }
        TipoChaside other = (TipoChaside) object;
        if ((this.idTipoChaside == null && other.idTipoChaside != null) || (this.idTipoChaside != null && !this.idTipoChaside.equals(other.idTipoChaside))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.suideal.encuesta.chaside.entidades.TipoChaside[ idTipoChaside=" + idTipoChaside + " ]";
    }
    
}
