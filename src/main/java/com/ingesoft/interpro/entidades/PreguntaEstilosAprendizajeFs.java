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

/**
 *
 * @author Personal
 */
@Entity
@Table(name = "pregunta_estilos_aprendizaje_fs")
@NamedQueries({
    @NamedQuery(name = "PreguntaEstilosAprendizajeFs.findAll", query = "SELECT p FROM PreguntaEstilosAprendizajeFs p"),
    @NamedQuery(name = "PreguntaEstilosAprendizajeFs.findByIdpreguntaEstilos", query = "SELECT p FROM PreguntaEstilosAprendizajeFs p WHERE p.idpreguntaEstilos = :idpreguntaEstilos"),
    @NamedQuery(name = "PreguntaEstilosAprendizajeFs.findByEnunciado", query = "SELECT p FROM PreguntaEstilosAprendizajeFs p WHERE p.enunciado = :enunciado"),
    @NamedQuery(name = "PreguntaEstilosAprendizajeFs.findByUrlimagen", query = "SELECT p FROM PreguntaEstilosAprendizajeFs p WHERE p.urlimagen = :urlimagen"),
    @NamedQuery(name = "PreguntaEstilosAprendizajeFs.findByOrdenint", query = "SELECT p FROM PreguntaEstilosAprendizajeFs p WHERE p.ordenint = :ordenint")})
public class PreguntaEstilosAprendizajeFs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idpregunta_estilos")
    private Integer idpreguntaEstilos;
    @Size(max = 150)
    @Column(name = "enunciado")
    private String enunciado;
    @Size(max = 45)
    @Column(name = "urlimagen")
    private String urlimagen;
    @Size(max = 45)
    @Column(name = "ordenint")
    private String ordenint;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preguntaEstilosAprendizajeFs")
    private List<TipoEstiloPregunta> tipoestiloPreguntaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpreguntaEstilos")
    private List<RespuestaEstilo> respuestaestiloList;

    public PreguntaEstilosAprendizajeFs() {
    }

    public PreguntaEstilosAprendizajeFs(Integer idpreguntaEstilos) {
        this.idpreguntaEstilos = idpreguntaEstilos;
    }

    public Integer getIdpreguntaEstilos() {
        return idpreguntaEstilos;
    }

    public void setIdpreguntaEstilos(Integer idpreguntaEstilos) {
        this.idpreguntaEstilos = idpreguntaEstilos;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getUrlimagen() {
        return urlimagen;
    }

    public void setUrlimagen(String urlimagen) {
        this.urlimagen = urlimagen;
    }

    public String getOrdenint() {
        return ordenint;
    }

    public void setOrdenint(String ordenint) {
        this.ordenint = ordenint;
    }

    public List<TipoEstiloPregunta> getTipoestiloPreguntaList() {
        return tipoestiloPreguntaList;
    }

    public void setTipoestiloPreguntaList(List<TipoEstiloPregunta> tipoestiloPreguntaList) {
        this.tipoestiloPreguntaList = tipoestiloPreguntaList;
    }

    public List<RespuestaEstilo> getRespuestaestiloList() {
        return respuestaestiloList;
    }

    public void setRespuestaestiloList(List<RespuestaEstilo> respuestaestiloList) {
        this.respuestaestiloList = respuestaestiloList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpreguntaEstilos != null ? idpreguntaEstilos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreguntaEstilosAprendizajeFs)) {
            return false;
        }
        PreguntaEstilosAprendizajeFs other = (PreguntaEstilosAprendizajeFs) object;
        if ((this.idpreguntaEstilos == null && other.idpreguntaEstilos != null) || (this.idpreguntaEstilos != null && !this.idpreguntaEstilos.equals(other.idpreguntaEstilos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PreguntaEstilosAprendizajeFs[ idpreguntaEstilos=" + idpreguntaEstilos + "|" + enunciado + " ]";
    }
    
}
