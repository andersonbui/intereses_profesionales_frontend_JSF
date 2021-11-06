/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.chaside.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "TipoClaseChaside")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoClaseChaside.findAll", query = "SELECT t FROM TipoClaseChaside t"),
    @NamedQuery(name = "TipoClaseChaside.findByIdTipoChaside", query = "SELECT t FROM TipoClaseChaside t WHERE t.tipoClaseChasidePK.idTipoChaside = :idTipoChaside"),
    @NamedQuery(name = "TipoClaseChaside.findByIdClaseChaside", query = "SELECT t FROM TipoClaseChaside t WHERE t.tipoClaseChasidePK.idClaseChaside = :idClaseChaside"),
    @NamedQuery(name = "TipoClaseChaside.findByDescripcion", query = "SELECT t FROM TipoClaseChaside t WHERE t.descripcion = :descripcion")})
public class TipoClaseChaside implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TipoClaseChasidePK tipoClaseChasidePK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "idClaseChaside", referencedColumnName = "idClaseChaside", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ClaseChaside claseChaside;
    @JoinColumn(name = "idTipoChaside", referencedColumnName = "idTipoChaside", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TipoChaside tipoChaside;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoClaseChaside")
    private List<ResultadoChaside> resultadoChasideList;

    public TipoClaseChaside() {
    }

    public TipoClaseChaside(TipoClaseChasidePK tipoClaseChasidePK) {
        this.tipoClaseChasidePK = tipoClaseChasidePK;
    }

    public TipoClaseChaside(TipoClaseChasidePK tipoClaseChasidePK, String descripcion) {
        this.tipoClaseChasidePK = tipoClaseChasidePK;
        this.descripcion = descripcion;
    }

    public TipoClaseChaside(int idTipoChaside, int idClaseChaside) {
        this.tipoClaseChasidePK = new TipoClaseChasidePK(idTipoChaside, idClaseChaside);
    }

    public TipoClaseChasidePK getTipoClaseChasidePK() {
        return tipoClaseChasidePK;
    }

    public void setTipoClaseChasidePK(TipoClaseChasidePK tipoClaseChasidePK) {
        this.tipoClaseChasidePK = tipoClaseChasidePK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ClaseChaside getClaseChaside() {
        return claseChaside;
    }

    public void setClaseChaside(ClaseChaside claseChaside) {
        this.claseChaside = claseChaside;
    }

    public TipoChaside getTipoChaside() {
        return tipoChaside;
    }

    public void setTipoChaside(TipoChaside tipoChaside) {
        this.tipoChaside = tipoChaside;
    }

    @XmlTransient
    public List<ResultadoChaside> getResultadoChasideList() {
        return resultadoChasideList;
    }

    public void setResultadoChasideList(List<ResultadoChaside> resultadoChasideList) {
        this.resultadoChasideList = resultadoChasideList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoClaseChasidePK != null ? tipoClaseChasidePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoClaseChaside)) {
            return false;
        }
        TipoClaseChaside other = (TipoClaseChaside) object;
        if ((this.tipoClaseChasidePK == null && other.tipoClaseChasidePK != null) || (this.tipoClaseChasidePK != null && !this.tipoClaseChasidePK.equals(other.tipoClaseChasidePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.suideal.encuesta.chaside.entidades.TipoClaseChaside[ tipoClaseChasidePK=" + tipoClaseChasidePK + " ]";
    }
    
}
