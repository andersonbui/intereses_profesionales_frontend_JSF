/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "GrupoUsuario", catalog = "interpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoUsuario.findAll", query = "SELECT g FROM GrupoUsuario g")
    , @NamedQuery(name = "GrupoUsuario.findByIdGrupoUsuario", query = "SELECT g FROM GrupoUsuario g WHERE g.idGrupoUsuario = :idGrupoUsuario")
    , @NamedQuery(name = "GrupoUsuario.findByUsuario", query = "SELECT g FROM GrupoUsuario g WHERE g.usuario = :usuario")})
public class GrupoUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idGrupoUsuario")
    private Integer idGrupoUsuario;
    @Size(max = 45)
    @Column(name = "usuario")
    private String usuario;

    public GrupoUsuario() {
    }

    public GrupoUsuario(Integer idGrupoUsuario) {
        this.idGrupoUsuario = idGrupoUsuario;
    }

    public Integer getIdGrupoUsuario() {
        return idGrupoUsuario;
    }

    public void setIdGrupoUsuario(Integer idGrupoUsuario) {
        this.idGrupoUsuario = idGrupoUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrupoUsuario != null ? idGrupoUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoUsuario)) {
            return false;
        }
        GrupoUsuario other = (GrupoUsuario) object;
        if ((this.idGrupoUsuario == null && other.idGrupoUsuario != null) || (this.idGrupoUsuario != null && !this.idGrupoUsuario.equals(other.idGrupoUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.GrupoUsuario[ idGrupoUsuario=" + idGrupoUsuario + " ]";
    }
    
}
