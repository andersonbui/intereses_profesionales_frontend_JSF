/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
 * @author debian
 */
@Entity
@Table(name = "AreaProfesional")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AreaProfesional.findAll", query = "SELECT a FROM AreaProfesional a")
    , @NamedQuery(name = "AreaProfesional.findByIdAreaProfesional", query = "SELECT a FROM AreaProfesional a WHERE a.idAreaProfesional = :idAreaProfesional")
    , @NamedQuery(name = "AreaProfesional.findByArea", query = "SELECT a FROM AreaProfesional a WHERE a.area = :area")})
public class AreaProfesional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idAreaProfesional")
    private Integer idAreaProfesional;
    @Size(max = 45)
    @Column(name = "area")
    private String area;
    @OneToMany(mappedBy = "idAreaProfesional")
    private List<Encuesta> encuestaList;

    public AreaProfesional() {
    }

    public AreaProfesional(Integer idAreaProfesional) {
        this.idAreaProfesional = idAreaProfesional;
    }

    public Integer getIdAreaProfesional() {
        return idAreaProfesional;
    }

    public void setIdAreaProfesional(Integer idAreaProfesional) {
        this.idAreaProfesional = idAreaProfesional;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @XmlTransient
    public List<Encuesta> getEncuestaList() {
        return encuestaList;
    }

    public void setEncuestaList(List<Encuesta> encuestaList) {
        this.encuestaList = encuestaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAreaProfesional != null ? idAreaProfesional.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AreaProfesional)) {
            return false;
        }
        AreaProfesional other = (AreaProfesional) object;
        if ((this.idAreaProfesional == null && other.idAreaProfesional != null) || (this.idAreaProfesional != null && !this.idAreaProfesional.equals(other.idAreaProfesional))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AreaProfesional[ idAreaProfesional=" + idAreaProfesional + " ]";
    }
    
}
