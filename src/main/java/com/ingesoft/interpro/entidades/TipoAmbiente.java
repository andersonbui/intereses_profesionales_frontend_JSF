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
@Table(name = "TipoAmbiente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoAmbiente.findAll", query = "SELECT t FROM TipoAmbiente t")
    , @NamedQuery(name = "TipoAmbiente.findByIdTipoAmbiente", query = "SELECT t FROM TipoAmbiente t WHERE t.idTipoAmbiente = :idTipoAmbiente")
    , @NamedQuery(name = "TipoAmbiente.findByTipo", query = "SELECT t FROM TipoAmbiente t WHERE t.tipo = :tipo")
    , @NamedQuery(name = "TipoAmbiente.findByDefinicion", query = "SELECT t FROM TipoAmbiente t WHERE t.definicion = :definicion")})
public class TipoAmbiente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idTipoAmbiente")
    private Integer idTipoAmbiente;
    @Size(max = 45)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 200)
    @Column(name = "definicion")
    private String definicion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoAmbiente")
    private List<PreguntaAmbiente> preguntaAmbienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoAmbiente")
    private List<ResultadoPorAmbiente> resultadoPorAmbienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoAmbiente")
    private List<Evaluacion> evaluacionList;

    public TipoAmbiente() {
    }

    public TipoAmbiente(Integer idTipoAmbiente) {
        this.idTipoAmbiente = idTipoAmbiente;
    }

    public Integer getIdTipoAmbiente() {
        return idTipoAmbiente;
    }

    public void setIdTipoAmbiente(Integer idTipoAmbiente) {
        this.idTipoAmbiente = idTipoAmbiente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDefinicion() {
        return definicion;
    }

    public void setDefinicion(String definicion) {
        this.definicion = definicion;
    }

    @XmlTransient
    public List<PreguntaAmbiente> getPreguntaAmbienteList() {
        return preguntaAmbienteList;
    }

    public void setPreguntaAmbienteList(List<PreguntaAmbiente> preguntaAmbienteList) {
        this.preguntaAmbienteList = preguntaAmbienteList;
    }

    @XmlTransient
    public List<ResultadoPorAmbiente> getResultadoPorAmbienteList() {
        return resultadoPorAmbienteList;
    }

    public void setResultadoPorAmbienteList(List<ResultadoPorAmbiente> resultadoPorAmbienteList) {
        this.resultadoPorAmbienteList = resultadoPorAmbienteList;
    }

    @XmlTransient
    public List<Evaluacion> getEvaluacionList() {
        return evaluacionList;
    }

    public void setEvaluacionList(List<Evaluacion> evaluacionList) {
        this.evaluacionList = evaluacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoAmbiente != null ? idTipoAmbiente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoAmbiente)) {
            return false;
        }
        TipoAmbiente other = (TipoAmbiente) object;
        if ((this.idTipoAmbiente == null && other.idTipoAmbiente != null) || (this.idTipoAmbiente != null && !this.idTipoAmbiente.equals(other.idTipoAmbiente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.TipoAmbiente[ idTipoAmbiente=" + idTipoAmbiente + " ]";
    }
    
}
