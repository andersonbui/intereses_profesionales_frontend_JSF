/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.chaside.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "ResultadoChaside")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResultadoChaside.findAll", query = "SELECT r FROM ResultadoChaside r"),
    @NamedQuery(name = "ResultadoChaside.findByIdClaseChaside", query = "SELECT r FROM ResultadoChaside r WHERE r.resultadoChasidePK.idClaseChaside = :idClaseChaside"),
    @NamedQuery(name = "ResultadoChaside.findByIdTipoChaside", query = "SELECT r FROM ResultadoChaside r WHERE r.resultadoChasidePK.idTipoChaside = :idTipoChaside"),
    @NamedQuery(name = "ResultadoChaside.findByIdEncuesta", query = "SELECT r FROM ResultadoChaside r WHERE r.resultadoChasidePK.idEncuesta = :idEncuesta"),
    @NamedQuery(name = "ResultadoChaside.findByResultado", query = "SELECT r FROM ResultadoChaside r WHERE r.resultado = :resultado")})
public class ResultadoChaside implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ResultadoChasidePK resultadoChasidePK;
    @Column(name = "resultado")
    private Short resultado;
    @JoinColumn(name = "idEncuesta", referencedColumnName = "idEncuesta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EncuestaChaside encuestaChaside;
    @JoinColumns({
        @JoinColumn(name = "idClaseChaside", referencedColumnName = "idClaseChaside", insertable = false, updatable = false),
        @JoinColumn(name = "idTipoChaside", referencedColumnName = "idTipoChaside", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private TipoClaseChaside tipoClaseChaside;

    public ResultadoChaside() {
    }

    public ResultadoChaside(ResultadoChasidePK resultadoChasidePK) {
        this.resultadoChasidePK = resultadoChasidePK;
    }

    public ResultadoChaside(int idClaseChaside, int idTipoChaside, int idEncuesta) {
        this.resultadoChasidePK = new ResultadoChasidePK(idClaseChaside, idTipoChaside, idEncuesta);
    }

    public ResultadoChasidePK getResultadoChasidePK() {
        return resultadoChasidePK;
    }

    public void setResultadoChasidePK(ResultadoChasidePK resultadoChasidePK) {
        this.resultadoChasidePK = resultadoChasidePK;
    }

    public Short getResultado() {
        return resultado;
    }

    public void setResultado(Short resultado) {
        this.resultado = resultado;
    }

    public EncuestaChaside getEncuestaChaside() {
        return encuestaChaside;
    }

    public void setEncuestaChaside(EncuestaChaside encuestaChaside) {
        this.encuestaChaside = encuestaChaside;
    }

    public TipoClaseChaside getTipoClaseChaside() {
        return tipoClaseChaside;
    }

    public void setTipoClaseChaside(TipoClaseChaside tipoClaseChaside) {
        this.tipoClaseChaside = tipoClaseChaside;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resultadoChasidePK != null ? resultadoChasidePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResultadoChaside)) {
            return false;
        }
        ResultadoChaside other = (ResultadoChaside) object;
        if ((this.resultadoChasidePK == null && other.resultadoChasidePK != null) || (this.resultadoChasidePK != null && !this.resultadoChasidePK.equals(other.resultadoChasidePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.suideal.encuesta.chaside.entidades.ResultadoChaside[ resultadoChasidePK=" + resultadoChasidePK + " ]";
    }
    
}
