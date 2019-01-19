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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "ResultadoPorAmbiente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResultadoPorAmbiente.findAll", query = "SELECT r FROM ResultadoPorAmbiente r")
    , @NamedQuery(name = "ResultadoPorAmbiente.findByIdEncuesta", query = "SELECT r FROM ResultadoPorAmbiente r WHERE r.resultadoPorAmbientePK.idEncuesta = :idEncuesta")
    , @NamedQuery(name = "ResultadoPorAmbiente.findByIdTipoAmbiente", query = "SELECT r FROM ResultadoPorAmbiente r WHERE r.resultadoPorAmbientePK.idTipoAmbiente = :idTipoAmbiente")
    , @NamedQuery(name = "ResultadoPorAmbiente.findByValor", query = "SELECT r FROM ResultadoPorAmbiente r WHERE r.valor = :valor")})
public class ResultadoPorAmbiente implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ResultadoPorAmbientePK resultadoPorAmbientePK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Float valor;
    @JoinColumn(name = "idEncuesta", referencedColumnName = "idEncuesta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Encuesta encuesta;
    @JoinColumn(name = "idTipoAmbiente", referencedColumnName = "idTipoAmbiente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TipoAmbiente tipoAmbiente;

    public ResultadoPorAmbiente() {
    }

    public ResultadoPorAmbiente(ResultadoPorAmbientePK resultadoPorAmbientePK) {
        this.resultadoPorAmbientePK = resultadoPorAmbientePK;
    }

    public ResultadoPorAmbiente(int idEncuesta, int idTipoAmbiente) {
        this.resultadoPorAmbientePK = new ResultadoPorAmbientePK(idEncuesta, idTipoAmbiente);
    }

    public ResultadoPorAmbientePK getResultadoPorAmbientePK() {
        return resultadoPorAmbientePK;
    }

    public void setResultadoPorAmbientePK(ResultadoPorAmbientePK resultadoPorAmbientePK) {
        this.resultadoPorAmbientePK = resultadoPorAmbientePK;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }

    public TipoAmbiente getTipoAmbiente() {
        return tipoAmbiente;
    }

    public void setTipoAmbiente(TipoAmbiente tipoAmbiente) {
        this.tipoAmbiente = tipoAmbiente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resultadoPorAmbientePK != null ? resultadoPorAmbientePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResultadoPorAmbiente)) {
            return false;
        }
        ResultadoPorAmbiente other = (ResultadoPorAmbiente) object;
        if ((this.resultadoPorAmbientePK == null && other.resultadoPorAmbientePK != null) || (this.resultadoPorAmbientePK != null && !this.resultadoPorAmbientePK.equals(other.resultadoPorAmbientePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResultadoPorAmbiente[ resultadoPorAmbientePK=" + resultadoPorAmbientePK + " ]";
    }
    
}
