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
@Table(name = "AmbientePersonalidad", catalog = "interpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbientePersonalidad.findAll", query = "SELECT a FROM AmbientePersonalidad a")
    , @NamedQuery(name = "AmbientePersonalidad.findByIdAmbientePersonalidad", query = "SELECT a FROM AmbientePersonalidad a WHERE a.idAmbientePersonalidad = :idAmbientePersonalidad")
    , @NamedQuery(name = "AmbientePersonalidad.findByNombre", query = "SELECT a FROM AmbientePersonalidad a WHERE a.nombre = :nombre")})
public class AmbientePersonalidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idAmbientePersonalidad")
    private Integer idAmbientePersonalidad;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAmbiente")
    private List<Pregunta> preguntaList;

    public AmbientePersonalidad() {
    }

    public AmbientePersonalidad(Integer idAmbientePersonalidad) {
        this.idAmbientePersonalidad = idAmbientePersonalidad;
    }

    public Integer getIdAmbientePersonalidad() {
        return idAmbientePersonalidad;
    }

    public void setIdAmbientePersonalidad(Integer idAmbientePersonalidad) {
        this.idAmbientePersonalidad = idAmbientePersonalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Pregunta> getPreguntaList() {
        return preguntaList;
    }

    public void setPreguntaList(List<Pregunta> preguntaList) {
        this.preguntaList = preguntaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAmbientePersonalidad != null ? idAmbientePersonalidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbientePersonalidad)) {
            return false;
        }
        AmbientePersonalidad other = (AmbientePersonalidad) object;
        if ((this.idAmbientePersonalidad == null && other.idAmbientePersonalidad != null) || (this.idAmbientePersonalidad != null && !this.idAmbientePersonalidad.equals(other.idAmbientePersonalidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.AmbientePersonalidad[ idAmbientePersonalidad=" + idAmbientePersonalidad + " ]";
    }
    
}
