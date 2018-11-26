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
public class PersonaCodigoInstitucionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idPersona")
    private int idPersona;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCodigoInstitucion")
    private int idCodigoInstitucion;

    public PersonaCodigoInstitucionPK() {
    }

    public PersonaCodigoInstitucionPK(int idPersona, int idCodigoInstitucion) {
        this.idPersona = idPersona;
        this.idCodigoInstitucion = idCodigoInstitucion;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getIdCodigoInstitucion() {
        return idCodigoInstitucion;
    }

    public void setIdCodigoInstitucion(int idCodigoInstitucion) {
        this.idCodigoInstitucion = idCodigoInstitucion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPersona;
        hash += (int) idCodigoInstitucion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonaCodigoInstitucionPK)) {
            return false;
        }
        PersonaCodigoInstitucionPK other = (PersonaCodigoInstitucionPK) object;
        if (this.idPersona != other.idPersona) {
            return false;
        }
        if (this.idCodigoInstitucion != other.idCodigoInstitucion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.PersonaCodigoInstitucionPK[ idPersona=" + idPersona + ", idCodigoInstitucion=" + idCodigoInstitucion + " ]";
    }
    
}
