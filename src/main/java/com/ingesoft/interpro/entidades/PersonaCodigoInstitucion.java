/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "Persona_CodigoInstitucion", catalog = "interpro2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonaCodigoInstitucion.findAll", query = "SELECT p FROM PersonaCodigoInstitucion p")
    , @NamedQuery(name = "PersonaCodigoInstitucion.findByIdCodigoInstitucion", query = "SELECT p FROM PersonaCodigoInstitucion p WHERE p.personaCodigoInstitucionPK.idCodigoInstitucion = :idCodigoInstitucion")
    , @NamedQuery(name = "PersonaCodigoInstitucion.findByFechaIngreso", query = "SELECT p FROM PersonaCodigoInstitucion p WHERE p.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "PersonaCodigoInstitucion.findByPersonaidPersona", query = "SELECT p FROM PersonaCodigoInstitucion p WHERE p.personaCodigoInstitucionPK.personaidPersona = :personaidPersona")})
public class PersonaCodigoInstitucion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PersonaCodigoInstitucionPK personaCodigoInstitucionPK;
    @Column(name = "fechaIngreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @JoinColumn(name = "Persona_idPersona", referencedColumnName = "idPersona", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Persona persona;
    @JoinColumn(name = "idCodigoInstitucion", referencedColumnName = "idPersonaInstitucion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CodigoInstitucion codigoInstitucion;

    public PersonaCodigoInstitucion() {
    }

    public PersonaCodigoInstitucion(PersonaCodigoInstitucionPK personaCodigoInstitucionPK) {
        this.personaCodigoInstitucionPK = personaCodigoInstitucionPK;
    }

    public PersonaCodigoInstitucion(String idCodigoInstitucion, int personaidPersona) {
        this.personaCodigoInstitucionPK = new PersonaCodigoInstitucionPK(idCodigoInstitucion, personaidPersona);
    }

    public PersonaCodigoInstitucionPK getPersonaCodigoInstitucionPK() {
        return personaCodigoInstitucionPK;
    }

    public void setPersonaCodigoInstitucionPK(PersonaCodigoInstitucionPK personaCodigoInstitucionPK) {
        this.personaCodigoInstitucionPK = personaCodigoInstitucionPK;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public CodigoInstitucion getCodigoInstitucion() {
        return codigoInstitucion;
    }

    public void setCodigoInstitucion(CodigoInstitucion codigoInstitucion) {
        this.codigoInstitucion = codigoInstitucion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personaCodigoInstitucionPK != null ? personaCodigoInstitucionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonaCodigoInstitucion)) {
            return false;
        }
        PersonaCodigoInstitucion other = (PersonaCodigoInstitucion) object;
        if ((this.personaCodigoInstitucionPK == null && other.personaCodigoInstitucionPK != null) || (this.personaCodigoInstitucionPK != null && !this.personaCodigoInstitucionPK.equals(other.personaCodigoInstitucionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingeniosoft.interpro.entidades.PersonaCodigoInstitucion[ personaCodigoInstitucionPK=" + personaCodigoInstitucionPK + " ]";
    }
    
}
