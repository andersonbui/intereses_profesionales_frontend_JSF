/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "CodigoInstitucion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CodigoInstitucion.findAll", query = "SELECT c FROM CodigoInstitucion c")
    , @NamedQuery(name = "CodigoInstitucion.findByCodigoActivacion", query = "SELECT c FROM CodigoInstitucion c WHERE c.codigoActivacion = :codigoActivacion")
    , @NamedQuery(name = "CodigoInstitucion.findByFechaCaducidad", query = "SELECT c FROM CodigoInstitucion c WHERE c.fechaCaducidad = :fechaCaducidad")
    , @NamedQuery(name = "CodigoInstitucion.findByIdCodigoInstitucion", query = "SELECT c FROM CodigoInstitucion c WHERE c.idCodigoInstitucion = :idCodigoInstitucion")
    , @NamedQuery(name = "CodigoInstitucion.findByCantidadMaxima", query = "SELECT c FROM CodigoInstitucion c WHERE c.cantidadMaxima = :cantidadMaxima")})
public class CodigoInstitucion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "codigoActivacion")
    private String codigoActivacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaCaducidad")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCaducidad;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCodigoInstitucion")
    private Integer idCodigoInstitucion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidadMaxima")
    private int cantidadMaxima;
    @JoinColumn(name = "idInstitucion", referencedColumnName = "idInstitucion")
    @ManyToOne(optional = false)
    private Institucion idInstitucion;
    @JoinColumn(name = "idTipoUsuario", referencedColumnName = "idTipoUsuario")
    @ManyToOne(optional = false)
    private TipoUsuario idTipoUsuario;

    public CodigoInstitucion() {
    }

    public CodigoInstitucion(Integer idCodigoInstitucion) {
        this.idCodigoInstitucion = idCodigoInstitucion;
    }

    public CodigoInstitucion(Integer idCodigoInstitucion, String codigoActivacion, Date fechaCaducidad, int cantidadMaxima) {
        this.idCodigoInstitucion = idCodigoInstitucion;
        this.codigoActivacion = codigoActivacion;
        this.fechaCaducidad = fechaCaducidad;
        this.cantidadMaxima = cantidadMaxima;
    }

    public String getCodigoActivacion() {
        return codigoActivacion;
    }

    public void setCodigoActivacion(String codigoActivacion) {
        this.codigoActivacion = codigoActivacion;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Integer getIdCodigoInstitucion() {
        return idCodigoInstitucion;
    }

    public void setIdCodigoInstitucion(Integer idCodigoInstitucion) {
        this.idCodigoInstitucion = idCodigoInstitucion;
    }

    public int getCantidadMaxima() {
        return cantidadMaxima;
    }

    public void setCantidadMaxima(int cantidadMaxima) {
        this.cantidadMaxima = cantidadMaxima;
    }

    public Institucion getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(Institucion idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public TipoUsuario getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(TipoUsuario idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCodigoInstitucion != null ? idCodigoInstitucion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodigoInstitucion)) {
            return false;
        }
        CodigoInstitucion other = (CodigoInstitucion) object;
        if ((this.idCodigoInstitucion == null && other.idCodigoInstitucion != null) || (this.idCodigoInstitucion != null && !this.idCodigoInstitucion.equals(other.idCodigoInstitucion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CodigoInstitucion[ idCodigoInstitucion=" + idCodigoInstitucion + " ]";
    }
    
}
