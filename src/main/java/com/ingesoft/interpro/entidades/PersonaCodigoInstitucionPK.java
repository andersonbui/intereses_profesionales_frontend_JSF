/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author debian
 */
@Embeddable
public class PersonaCodigoInstitucionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "idCodigoInstitucion")
    private String idCodigoInstitucion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Persona_idPersona")
    private int personaidPersona;

    public PersonaCodigoInstitucionPK() {
    }

    public PersonaCodigoInstitucionPK(String idCodigoInstitucion, int personaidPersona) {
        this.idCodigoInstitucion = idCodigoInstitucion;
        this.personaidPersona = personaidPersona;
    }

    public String getIdCodigoInstitucion() {
        return idCodigoInstitucion;
    }

    public void setIdCodigoInstitucion(String idCodigoInstitucion) {
        this.idCodigoInstitucion = idCodigoInstitucion;
    }

    public int getPersonaidPersona() {
        return personaidPersona;
    }

    public void setPersonaidPersona(int personaidPersona) {
        this.personaidPersona = personaidPersona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCodigoInstitucion != null ? idCodigoInstitucion.hashCode() : 0);
        hash += (int) personaidPersona;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonaCodigoInstitucionPK)) {
            return false;
        }
        PersonaCodigoInstitucionPK other = (PersonaCodigoInstitucionPK) object;
        if ((this.idCodigoInstitucion == null && other.idCodigoInstitucion != null) || (this.idCodigoInstitucion != null && !this.idCodigoInstitucion.equals(other.idCodigoInstitucion))) {
            return false;
        }
        if (this.personaidPersona != other.personaidPersona) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingeniosoft.interpro.entidades.PersonaCodigoInstitucionPK[ idCodigoInstitucion=" + idCodigoInstitucion + ", personaidPersona=" + personaidPersona + " ]";
    }
    
}
