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

/**
 *
 * @author debian
 */
@Embeddable
public class PersonahasInstitucionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idPersona")
    private int idPersona;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idInstitucion")
    private int idInstitucion;

    public PersonahasInstitucionPK() {
    }

    public PersonahasInstitucionPK(int idPersona, int idInstitucion) {
        this.idPersona = idPersona;
        this.idInstitucion = idInstitucion;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(int idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPersona;
        hash += (int) idInstitucion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonahasInstitucionPK)) {
            return false;
        }
        PersonahasInstitucionPK other = (PersonahasInstitucionPK) object;
        if (this.idPersona != other.idPersona) {
            return false;
        }
        if (this.idInstitucion != other.idInstitucion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.PersonahasInstitucionPK[ idPersona=" + idPersona + ", idInstitucion=" + idInstitucion + " ]";
    }
    
}
