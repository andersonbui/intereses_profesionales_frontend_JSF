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
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "PreguntaChaside")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreguntaChaside.findAll", query = "SELECT p FROM PreguntaChaside p"),
    @NamedQuery(name = "PreguntaChaside.findByIdPreguntaChaside", query = "SELECT p FROM PreguntaChaside p WHERE p.idPreguntaChaside = :idPreguntaChaside"),
    @NamedQuery(name = "PreguntaChaside.findByEnunciado", query = "SELECT p FROM PreguntaChaside p WHERE p.enunciado = :enunciado")})
public class PreguntaChaside implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPreguntaChaside")
    private Integer idPreguntaChaside;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 180)
    @Column(name = "enunciado")
    private String enunciado;
    
    @JoinColumn(name = "idClaseChaside", referencedColumnName = "idClaseChaside")
    @ManyToOne(optional = false)
    private ClaseChaside idClaseChaside;
    
    @JoinColumn(name = "idTipoChaside", referencedColumnName = "idTipoChaside")
    @ManyToOne(optional = false)
    private TipoChaside idTipoChaside;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preguntaChaside")
    private List<RespuestaChaside> respuestaChasideList;
    
    public PreguntaChaside() {
    }

    public PreguntaChaside(Integer idPreguntaChaside) {
        this.idPreguntaChaside = idPreguntaChaside;
    }

    public PreguntaChaside(Integer idPreguntaChaside, String enunciado) {
        this.idPreguntaChaside = idPreguntaChaside;
        this.enunciado = enunciado;
    }

    public Integer getIdPreguntaChaside() {
        return idPreguntaChaside;
    }

    public void setIdPreguntaChaside(Integer idPreguntaChaside) {
        this.idPreguntaChaside = idPreguntaChaside;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public ClaseChaside getIdClaseChaside() {
        return idClaseChaside;
    }

    public void setIdClaseChaside(ClaseChaside idClaseChaside) {
        this.idClaseChaside = idClaseChaside;
    }

    public TipoChaside getIdTipoChaside() {
        return idTipoChaside;
    }

    public void setIdTipoChaside(TipoChaside idTipoChaside) {
        this.idTipoChaside = idTipoChaside;
    }

    @XmlTransient
    public List<RespuestaChaside> getRespuestaChasideList() {
        return respuestaChasideList;
    }

    public void setRespuestaChasideList(List<RespuestaChaside> respuestaChasideList) {
        this.respuestaChasideList = respuestaChasideList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPreguntaChaside != null ? idPreguntaChaside.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreguntaChaside)) {
            return false;
        }
        PreguntaChaside other = (PreguntaChaside) object;
        if ((this.idPreguntaChaside == null && other.idPreguntaChaside != null) || (this.idPreguntaChaside != null && !this.idPreguntaChaside.equals(other.idPreguntaChaside))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PreguntaChaside[ idPreguntaChaside=" + idPreguntaChaside + " ]";
    }
    
}
