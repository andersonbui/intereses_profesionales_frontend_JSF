/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "Usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario")
    , @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario")
    , @NamedQuery(name = "Usuario.findByClave", query = "SELECT u FROM Usuario u WHERE u.clave = :clave")
    , @NamedQuery(name = "Usuario.findByEstado", query = "SELECT u FROM Usuario u WHERE u.estado = :estado")
    , @NamedQuery(name = "Usuario.findByFechaCreacion", query = "SELECT u FROM Usuario u WHERE u.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "Usuario.findByTokenAcesso", query = "SELECT u FROM Usuario u WHERE u.tokenAcesso = :tokenAcesso")
    , @NamedQuery(name = "Usuario.findByFechaExpiracionToken", query = "SELECT u FROM Usuario u WHERE u.fechaExpiracionToken = :fechaExpiracionToken")
    , @NamedQuery(name = "Usuario.findByTokenRecuperacion", query = "SELECT u FROM Usuario u WHERE u.tokenRecuperacion = :tokenRecuperacion")
    , @NamedQuery(name = "Usuario.findByFechaExpTokenRecuperacion", query = "SELECT u FROM Usuario u WHERE u.fechaExpTokenRecuperacion = :fechaExpTokenRecuperacion")
    , @NamedQuery(name = "Usuario.esTipoUsuario", query = "SELECT u FROM Usuario u LEFT JOIN u.grupoUsuarioList AS gu WHERE gu.tipoUsuario.tipo = :tipo AND u.idUsuario = :idUsuario")
})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUsuario")
    private Integer idUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "clave")
    private String clave;
    @Size(max = 45)
    @Column(name = "estado")
    private String estado;
    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 100)
    @Column(name = "tokenAcesso")
    private String tokenAcesso;
    @Column(name = "fechaExpiracionToken")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaExpiracionToken;
    @Size(max = 200)
    @Column(name = "tokenRecuperacion")
    private String tokenRecuperacion;
    @Column(name = "fechaExpTokenRecuperacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaExpTokenRecuperacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Persona> personaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario1")
    private List<GrupoUsuario> grupoUsuarioList;

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(Integer idUsuario, String usuario, String clave) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.clave = clave;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTokenAcesso() {
        return tokenAcesso;
    }

    public void setTokenAcesso(String tokenAcesso) {
        this.tokenAcesso = tokenAcesso;
    }

    public Date getFechaExpiracionToken() {
        return fechaExpiracionToken;
    }

    public void setFechaExpiracionToken(Date fechaExpiracionToken) {
        this.fechaExpiracionToken = fechaExpiracionToken;
    }

    @XmlTransient
    public List<Persona> getPersonaList() {
        return personaList;
    }

    public void setPersonaList(List<Persona> personaList) {
        this.personaList = personaList;
    }

    @XmlTransient
    public List<GrupoUsuario> getGrupoUsuarioList() {
        return grupoUsuarioList;
    }

    public void setGrupoUsuarioList(List<GrupoUsuario> grupoUsuarioList) {
        this.grupoUsuarioList = grupoUsuarioList;
    }

    public String getTokenRecuperacion() {
        return tokenRecuperacion;
    }

    public void setTokenRecuperacion(String tokenRecuperacion) {
        this.tokenRecuperacion = tokenRecuperacion;
    }

    public Date getFechaExpTokenRecuperacion() {
        return fechaExpTokenRecuperacion;
    }

    public void setFechaExpTokenRecuperacion(Date fechaExpTokenRecuperacion) {
        this.fechaExpTokenRecuperacion = fechaExpTokenRecuperacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuario[ idUsuario=" + idUsuario + "; usuario: " + usuario + "; estado: " + estado + " ]";
    }
    
}
