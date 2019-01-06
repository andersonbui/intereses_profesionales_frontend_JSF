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
@Table(name = "TipoUsuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoUsuario.findAll", query = "SELECT t FROM TipoUsuario t")
    , @NamedQuery(name = "TipoUsuario.findByIdTipoUsuario", query = "SELECT t FROM TipoUsuario t WHERE t.idTipoUsuario = :idTipoUsuario")
    , @NamedQuery(name = "TipoUsuario.findByTpo", query = "SELECT t FROM TipoUsuario t WHERE t.tpo = :tpo")})
public class TipoUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idTipoUsuario")
    private Integer idTipoUsuario;
    @Size(max = 45)
    @Column(name = "tpo")
    private String tpo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoUsuario")
    private List<CodigoInstitucion> codigoInstitucionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoUsuario")
    private List<GrupoUsuario> grupoUsuarioList;

    public TipoUsuario() {
    }

    public TipoUsuario(Integer idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public Integer getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(Integer idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getTpo() {
        return tpo;
    }

    public void setTpo(String tpo) {
        this.tpo = tpo;
    }

    @XmlTransient
    public List<CodigoInstitucion> getCodigoInstitucionList() {
        return codigoInstitucionList;
    }

    public void setCodigoInstitucionList(List<CodigoInstitucion> codigoInstitucionList) {
        this.codigoInstitucionList = codigoInstitucionList;
    }

    @XmlTransient
    public List<GrupoUsuario> getGrupoUsuarioList() {
        return grupoUsuarioList;
    }

    public void setGrupoUsuarioList(List<GrupoUsuario> grupoUsuarioList) {
        this.grupoUsuarioList = grupoUsuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoUsuario != null ? idTipoUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoUsuario)) {
            return false;
        }
        TipoUsuario other = (TipoUsuario) object;
        if ((this.idTipoUsuario == null && other.idTipoUsuario != null) || (this.idTipoUsuario != null && !this.idTipoUsuario.equals(other.idTipoUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingeniosoft.entidades.TipoUsuario[ idTipoUsuario=" + idTipoUsuario + " ]";
    }
    
}
