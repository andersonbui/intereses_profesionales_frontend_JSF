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
@Table(name = "GrupoUsuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoUsuario.findAll", query = "SELECT g FROM GrupoUsuario g")
    , @NamedQuery(name = "GrupoUsuario.findByIdGrupoUsuario", query = "SELECT g FROM GrupoUsuario g WHERE g.grupoUsuarioPK.idGrupoUsuario = :idGrupoUsuario")
    , @NamedQuery(name = "GrupoUsuario.findByUsuario", query = "SELECT g FROM GrupoUsuario g WHERE g.usuario = :usuario")
    , @NamedQuery(name = "GrupoUsuario.findByIdUsuario", query = "SELECT g FROM GrupoUsuario g WHERE g.grupoUsuarioPK.idUsuario = :idUsuario")})
public class GrupoUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GrupoUsuarioPK grupoUsuarioPK;
    @Size(max = 45)
    @Column(name = "usuario")
    private String usuario;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario1;

    public GrupoUsuario() {
    }

    public GrupoUsuario(GrupoUsuarioPK grupoUsuarioPK) {
        this.grupoUsuarioPK = grupoUsuarioPK;
    }

    public GrupoUsuario(String idGrupoUsuario, int idUsuario) {
        this.grupoUsuarioPK = new GrupoUsuarioPK(idGrupoUsuario, idUsuario);
    }

    public GrupoUsuarioPK getGrupoUsuarioPK() {
        return grupoUsuarioPK;
    }

    public void setGrupoUsuarioPK(GrupoUsuarioPK grupoUsuarioPK) {
        this.grupoUsuarioPK = grupoUsuarioPK;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupoUsuarioPK != null ? grupoUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoUsuario)) {
            return false;
        }
        GrupoUsuario other = (GrupoUsuario) object;
        if ((this.grupoUsuarioPK == null && other.grupoUsuarioPK != null) || (this.grupoUsuarioPK != null && !this.grupoUsuarioPK.equals(other.grupoUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.GrupoUsuario[ grupoUsuarioPK=" + grupoUsuarioPK + " ]";
    }
    
}
