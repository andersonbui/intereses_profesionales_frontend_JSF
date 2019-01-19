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
 * @author debian
 */
@Entity
@Table(name = "TipoEleccionMateria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEleccionMateria.findAll", query = "SELECT t FROM TipoEleccionMateria t")
    , @NamedQuery(name = "TipoEleccionMateria.findByIdTipoEleccionMateria", query = "SELECT t FROM TipoEleccionMateria t WHERE t.idTipoEleccionMateria = :idTipoEleccionMateria")
    , @NamedQuery(name = "TipoEleccionMateria.findByTipo", query = "SELECT t FROM TipoEleccionMateria t WHERE t.tipo = :tipo")})
public class TipoEleccionMateria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idTipoEleccionMateria")
    private Integer idTipoEleccionMateria;
    @Size(max = 45)
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoEleccionMateria")
    private List<AreaEncuesta> areaEncuestaList;

    public TipoEleccionMateria() {
    }

    public TipoEleccionMateria(Integer idTipoEleccionMateria) {
        this.idTipoEleccionMateria = idTipoEleccionMateria;
    }

    public Integer getIdTipoEleccionMateria() {
        return idTipoEleccionMateria;
    }

    public void setIdTipoEleccionMateria(Integer idTipoEleccionMateria) {
        this.idTipoEleccionMateria = idTipoEleccionMateria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public List<AreaEncuesta> getAreaEncuestaList() {
        return areaEncuestaList;
    }

    public void setAreaEncuestaList(List<AreaEncuesta> areaEncuestaList) {
        this.areaEncuestaList = areaEncuestaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoEleccionMateria != null ? idTipoEleccionMateria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEleccionMateria)) {
            return false;
        }
        TipoEleccionMateria other = (TipoEleccionMateria) object;
        if ((this.idTipoEleccionMateria == null && other.idTipoEleccionMateria != null) || (this.idTipoEleccionMateria != null && !this.idTipoEleccionMateria.equals(other.idTipoEleccionMateria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TipoEleccionMateria[ idTipoEleccionMateria=" + idTipoEleccionMateria + " ]";
    }
    
}
