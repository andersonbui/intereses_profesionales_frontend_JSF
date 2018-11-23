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
@Table(name = "PreguntaAmbiente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreguntaAmbiente.findAll", query = "SELECT p FROM PreguntaAmbiente p")
    , @NamedQuery(name = "PreguntaAmbiente.findByIdPreguntaAmbiente", query = "SELECT p FROM PreguntaAmbiente p WHERE p.idPreguntaAmbiente = :idPreguntaAmbiente")
    , @NamedQuery(name = "PreguntaAmbiente.findByEnunciado", query = "SELECT p FROM PreguntaAmbiente p WHERE p.enunciado = :enunciado")
    , @NamedQuery(name = "PreguntaAmbiente.findByUrlImagen", query = "SELECT p FROM PreguntaAmbiente p WHERE p.urlImagen = :urlImagen")
    , @NamedQuery(name = "PreguntaAmbiente.findByOrden", query = "SELECT p FROM PreguntaAmbiente p WHERE p.orden = :orden")})
public class PreguntaAmbiente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPreguntaAmbiente")
    private Integer idPreguntaAmbiente;
    @Size(max = 50)
    @Column(name = "enunciado")
    private String enunciado;
    @Size(max = 100)
    @Column(name = "urlImagen")
    private String urlImagen;
    @Column(name = "orden")
    private Integer orden;
    @JoinColumn(name = "idTipoAmbiente", referencedColumnName = "idTipoAmbiente")
    @ManyToOne(optional = false)
    private TipoAmbiente idTipoAmbiente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preguntaAmbiente")
    private List<RespuestaAmbiente> respuestaAmbienteList;

    public PreguntaAmbiente() {
    }

    public PreguntaAmbiente(Integer idPreguntaAmbiente) {
        this.idPreguntaAmbiente = idPreguntaAmbiente;
    }

    public Integer getIdPreguntaAmbiente() {
        return idPreguntaAmbiente;
    }

    public void setIdPreguntaAmbiente(Integer idPreguntaAmbiente) {
        this.idPreguntaAmbiente = idPreguntaAmbiente;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public TipoAmbiente getIdTipoAmbiente() {
        return idTipoAmbiente;
    }

    public void setIdTipoAmbiente(TipoAmbiente idTipoAmbiente) {
        this.idTipoAmbiente = idTipoAmbiente;
    }
    
    @XmlTransient
    public List<RespuestaAmbiente> getRespuestaAmbienteList() {
        return respuestaAmbienteList;
    }

    public void setRespuestaAmbienteList(List<RespuestaAmbiente> respuestaAmbienteList) {
        this.respuestaAmbienteList = respuestaAmbienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPreguntaAmbiente != null ? idPreguntaAmbiente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreguntaAmbiente)) {
            return false;
        }
        PreguntaAmbiente other = (PreguntaAmbiente) object;
        if ((this.idPreguntaAmbiente == null && other.idPreguntaAmbiente != null) || (this.idPreguntaAmbiente != null && !this.idPreguntaAmbiente.equals(other.idPreguntaAmbiente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.PreguntaAmbiente[ idPreguntaAmbiente=" + idPreguntaAmbiente + " ]";
    }
    
}
