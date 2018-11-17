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
 * @author debian
 */
@Entity
@Table(name = "CodigoInstitucion", catalog = "interpro2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CodigoInstitucion.findAll", query = "SELECT c FROM CodigoInstitucion c")
    , @NamedQuery(name = "CodigoInstitucion.findByCodigoActivacion", query = "SELECT c FROM CodigoInstitucion c WHERE c.codigoActivacion = :codigoActivacion")
    , @NamedQuery(name = "CodigoInstitucion.findByFechaCaducidad", query = "SELECT c FROM CodigoInstitucion c WHERE c.fechaCaducidad = :fechaCaducidad")
    , @NamedQuery(name = "CodigoInstitucion.findByEstado", query = "SELECT c FROM CodigoInstitucion c WHERE c.estado = :estado")
    , @NamedQuery(name = "CodigoInstitucion.findByIdPersonaInstitucion", query = "SELECT c FROM CodigoInstitucion c WHERE c.idPersonaInstitucion = :idPersonaInstitucion")})
public class CodigoInstitucion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 45)
    @Column(name = "codigoActivacion")
    private String codigoActivacion;
    @Size(max = 45)
    @Column(name = "fechaCaducidad")
    private String fechaCaducidad;
    @Size(max = 45)
    @Column(name = "estado")
    private String estado;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "idPersonaInstitucion")
    private String idPersonaInstitucion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoInstitucion")
    private List<PersonaCodigoInstitucion> personaCodigoInstitucionList;
    @JoinColumn(name = "Institucion_idInstitucion", referencedColumnName = "idInstitucion")
    @ManyToOne(optional = false)
    private Institucion institucionidInstitucion;

    public CodigoInstitucion() {
    }

    public CodigoInstitucion(String idPersonaInstitucion) {
        this.idPersonaInstitucion = idPersonaInstitucion;
    }

    public String getCodigoActivacion() {
        return codigoActivacion;
    }

    public void setCodigoActivacion(String codigoActivacion) {
        this.codigoActivacion = codigoActivacion;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdPersonaInstitucion() {
        return idPersonaInstitucion;
    }

    public void setIdPersonaInstitucion(String idPersonaInstitucion) {
        this.idPersonaInstitucion = idPersonaInstitucion;
    }

    @XmlTransient
    public List<PersonaCodigoInstitucion> getPersonaCodigoInstitucionList() {
        return personaCodigoInstitucionList;
    }

    public void setPersonaCodigoInstitucionList(List<PersonaCodigoInstitucion> personaCodigoInstitucionList) {
        this.personaCodigoInstitucionList = personaCodigoInstitucionList;
    }

    public Institucion getInstitucionidInstitucion() {
        return institucionidInstitucion;
    }

    public void setInstitucionidInstitucion(Institucion institucionidInstitucion) {
        this.institucionidInstitucion = institucionidInstitucion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersonaInstitucion != null ? idPersonaInstitucion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodigoInstitucion)) {
            return false;
        }
        CodigoInstitucion other = (CodigoInstitucion) object;
        if ((this.idPersonaInstitucion == null && other.idPersonaInstitucion != null) || (this.idPersonaInstitucion != null && !this.idPersonaInstitucion.equals(other.idPersonaInstitucion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingeniosoft.interpro.entidades.CodigoInstitucion[ idPersonaInstitucion=" + idPersonaInstitucion + " ]";
    }
    
}
