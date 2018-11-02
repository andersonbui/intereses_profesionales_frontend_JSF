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
public class GrupoUsuarioPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idGrupoUsuario")
    private int idGrupoUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idUsuario")
    private int idUsuario;

    public GrupoUsuarioPK() {
    }

    public GrupoUsuarioPK(int idGrupoUsuario, int idUsuario) {
        this.idGrupoUsuario = idGrupoUsuario;
        this.idUsuario = idUsuario;
    }

    public int getIdGrupoUsuario() {
        return idGrupoUsuario;
    }

    public void setIdGrupoUsuario(int idGrupoUsuario) {
        this.idGrupoUsuario = idGrupoUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idGrupoUsuario;
        hash += (int) idUsuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoUsuarioPK)) {
            return false;
        }
        GrupoUsuarioPK other = (GrupoUsuarioPK) object;
        if (this.idGrupoUsuario != other.idGrupoUsuario) {
            return false;
        }
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.GrupoUsuarioPK[ idGrupoUsuario=" + idGrupoUsuario + ", idUsuario=" + idUsuario + " ]";
    }
    
}
