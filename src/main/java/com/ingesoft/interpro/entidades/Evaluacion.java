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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Evaluacion", catalog = "interpro2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evaluacion.findAll", query = "SELECT e FROM Evaluacion e")
    , @NamedQuery(name = "Evaluacion.findByIdEvaluacion", query = "SELECT e FROM Evaluacion e WHERE e.idEvaluacion = :idEvaluacion")
    , @NamedQuery(name = "Evaluacion.findByEnunciado", query = "SELECT e FROM Evaluacion e WHERE e.enunciado = :enunciado")})
public class Evaluacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idEvaluacion")
    private Integer idEvaluacion;
    @Size(max = 200)
    @Column(name = "enunciado")
    private String enunciado;
    @JoinColumn(name = "idTipoAmbiente", referencedColumnName = "idTipoAmbiente")
    @ManyToOne(optional = false)
    private TipoAmbiente idTipoAmbiente;

    public Evaluacion() {
    }

    public Evaluacion(Integer idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public Integer getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(Integer idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public TipoAmbiente getIdTipoAmbiente() {
        return idTipoAmbiente;
    }

    public void setIdTipoAmbiente(TipoAmbiente idTipoAmbiente) {
        this.idTipoAmbiente = idTipoAmbiente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvaluacion != null ? idEvaluacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evaluacion)) {
            return false;
        }
        Evaluacion other = (Evaluacion) object;
        if ((this.idEvaluacion == null && other.idEvaluacion != null) || (this.idEvaluacion != null && !this.idEvaluacion.equals(other.idEvaluacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingeniosoft.interpro.entidades.Evaluacion[ idEvaluacion=" + idEvaluacion + " ]";
    }
    
}
