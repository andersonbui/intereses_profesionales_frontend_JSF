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
@Table(name = "TipoPersonalidad", catalog = "interpro2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPersonalidad.findAll", query = "SELECT t FROM TipoPersonalidad t")
    , @NamedQuery(name = "TipoPersonalidad.findByIdTipoPersonalidad", query = "SELECT t FROM TipoPersonalidad t WHERE t.idTipoPersonalidad = :idTipoPersonalidad")
    , @NamedQuery(name = "TipoPersonalidad.findByTipo", query = "SELECT t FROM TipoPersonalidad t WHERE t.tipo = :tipo")})
public class TipoPersonalidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipoPersonalidad")
    private Integer idTipoPersonalidad;
    @Size(max = 45)
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoPersonalidad")
    private List<PreguntaPersonalidad> preguntaPersonalidadList;

    public TipoPersonalidad() {
    }

    public TipoPersonalidad(Integer idTipoPersonalidad) {
        this.idTipoPersonalidad = idTipoPersonalidad;
    }

    public Integer getIdTipoPersonalidad() {
        return idTipoPersonalidad;
    }

    public void setIdTipoPersonalidad(Integer idTipoPersonalidad) {
        this.idTipoPersonalidad = idTipoPersonalidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public List<PreguntaPersonalidad> getPreguntaPersonalidadList() {
        return preguntaPersonalidadList;
    }

    public void setPreguntaPersonalidadList(List<PreguntaPersonalidad> preguntaPersonalidadList) {
        this.preguntaPersonalidadList = preguntaPersonalidadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoPersonalidad != null ? idTipoPersonalidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPersonalidad)) {
            return false;
        }
        TipoPersonalidad other = (TipoPersonalidad) object;
        if ((this.idTipoPersonalidad == null && other.idTipoPersonalidad != null) || (this.idTipoPersonalidad != null && !this.idTipoPersonalidad.equals(other.idTipoPersonalidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.TipoPersonalidad[ idTipoPersonalidad=" + idTipoPersonalidad + " ]";
    }
    
}
