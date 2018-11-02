/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "Persona_has_Institucion", catalog = "interpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonahasInstitucion.findAll", query = "SELECT p FROM PersonahasInstitucion p")
    , @NamedQuery(name = "PersonahasInstitucion.findByIdPersona", query = "SELECT p FROM PersonahasInstitucion p WHERE p.personahasInstitucionPK.idPersona = :idPersona")
    , @NamedQuery(name = "PersonahasInstitucion.findByIdInstitucion", query = "SELECT p FROM PersonahasInstitucion p WHERE p.personahasInstitucionPK.idInstitucion = :idInstitucion")
    , @NamedQuery(name = "PersonahasInstitucion.findByCodigoActivacion", query = "SELECT p FROM PersonahasInstitucion p WHERE p.codigoActivacion = :codigoActivacion")
    , @NamedQuery(name = "PersonahasInstitucion.findByFechaCaducidad", query = "SELECT p FROM PersonahasInstitucion p WHERE p.fechaCaducidad = :fechaCaducidad")})
public class PersonahasInstitucion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PersonahasInstitucionPK personahasInstitucionPK;
    @Size(max = 45)
    @Column(name = "codigoActivacion")
    private String codigoActivacion;
    @Size(max = 45)
    @Column(name = "fechaCaducidad")
    private String fechaCaducidad;
    @JoinColumn(name = "idInstitucion", referencedColumnName = "idInstitucion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Institucion institucion;
    @JoinColumn(name = "idPersona", referencedColumnName = "idPersona", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Persona persona;

    public PersonahasInstitucion() {
    }

    public PersonahasInstitucion(PersonahasInstitucionPK personahasInstitucionPK) {
        this.personahasInstitucionPK = personahasInstitucionPK;
    }

    public PersonahasInstitucion(int idPersona, int idInstitucion) {
        this.personahasInstitucionPK = new PersonahasInstitucionPK(idPersona, idInstitucion);
    }

    public PersonahasInstitucionPK getPersonahasInstitucionPK() {
        return personahasInstitucionPK;
    }

    public void setPersonahasInstitucionPK(PersonahasInstitucionPK personahasInstitucionPK) {
        this.personahasInstitucionPK = personahasInstitucionPK;
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

    public Institucion getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personahasInstitucionPK != null ? personahasInstitucionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonahasInstitucion)) {
            return false;
        }
        PersonahasInstitucion other = (PersonahasInstitucion) object;
        if ((this.personahasInstitucionPK == null && other.personahasInstitucionPK != null) || (this.personahasInstitucionPK != null && !this.personahasInstitucionPK.equals(other.personahasInstitucionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.PersonahasInstitucion[ personahasInstitucionPK=" + personahasInstitucionPK + " ]";
    }
    
}
