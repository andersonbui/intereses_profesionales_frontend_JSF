/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Personal
 */
@Entity
@Table(name = "TipoEstilo_Pregunta")
@NamedQueries({
    @NamedQuery(name = "TipoEstiloPregunta.findAll", query = "SELECT t FROM TipoEstiloPregunta t"),
    @NamedQuery(name = "TipoEstiloPregunta.findByIndice", query = "SELECT t FROM TipoEstiloPregunta t WHERE t.indice = :indice"),
    @NamedQuery(name = "TipoEstiloPregunta.findByOpcion", query = "SELECT t FROM TipoEstiloPregunta t WHERE t.opcion = :opcion"),
    @NamedQuery(name = "TipoEstiloPregunta.findByIdTipoEstilo", query = "SELECT t FROM TipoEstiloPregunta t WHERE t.tipoestiloPreguntaPK.idTipoEstilo = :idTipoEstilo"),
    @NamedQuery(name = "TipoEstiloPregunta.findByIdpreguntaEstilos", query = "SELECT t FROM TipoEstiloPregunta t WHERE t.tipoestiloPreguntaPK.idpreguntaEstilos = :idpreguntaEstilos")})
public class TipoEstiloPregunta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TipoEstiloPreguntaPK tipoestiloPreguntaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indice")
    private Character indice;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "opcion")
    private String opcion;
    @JoinColumn(name = "idTipoEstilo", referencedColumnName = "idTipoEstilo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TipoEstilo tipoestilo;
    @JoinColumn(name = "idpregunta_estilos", referencedColumnName = "idpregunta_estilos", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PreguntaEstilosAprendizajeFs preguntaEstilosAprendizajeFs;

    public TipoEstiloPregunta() {
    }

    public TipoEstiloPregunta(TipoEstiloPreguntaPK tipoestiloPreguntaPK) {
        this.tipoestiloPreguntaPK = tipoestiloPreguntaPK;
    }

    public TipoEstiloPregunta(TipoEstiloPreguntaPK tipoestiloPreguntaPK, Character indice, String opcion) {
        this.tipoestiloPreguntaPK = tipoestiloPreguntaPK;
        this.indice = indice;
        this.opcion = opcion;
    }

    public TipoEstiloPregunta(int idTipoEstilo, int idpreguntaEstilos) {
        this.tipoestiloPreguntaPK = new TipoEstiloPreguntaPK(idTipoEstilo, idpreguntaEstilos);
    }

    public TipoEstiloPreguntaPK getTipoEstiloPreguntaPK() {
        return tipoestiloPreguntaPK;
    }

    public void setTipoEstiloPreguntaPK(TipoEstiloPreguntaPK tipoestiloPreguntaPK) {
        this.tipoestiloPreguntaPK = tipoestiloPreguntaPK;
    }

    public Character getIndice() {
        return indice;
    }

    public void setIndice(Character indice) {
        this.indice = indice;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public TipoEstilo getTipoEstilo() {
        return tipoestilo;
    }

    public void setTipoestilo(TipoEstilo tipoestilo) {
        this.tipoestilo = tipoestilo;
    }

    public PreguntaEstilosAprendizajeFs getPreguntaEstilosAprendizajeFs() {
        return preguntaEstilosAprendizajeFs;
    }

    public void setPreguntaEstilosAprendizajeFs(PreguntaEstilosAprendizajeFs preguntaEstilosAprendizajeFs) {
        this.preguntaEstilosAprendizajeFs = preguntaEstilosAprendizajeFs;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoestiloPreguntaPK != null ? tipoestiloPreguntaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEstiloPregunta)) {
            return false;
        }
        TipoEstiloPregunta other = (TipoEstiloPregunta) object;
        if ((this.tipoestiloPreguntaPK == null && other.tipoestiloPreguntaPK != null) || (this.tipoestiloPreguntaPK != null && !this.tipoestiloPreguntaPK.equals(other.tipoestiloPreguntaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.TipoEstiloPregunta[ tipoestiloPreguntaPK=" + tipoestiloPreguntaPK + " ]";
    }
    
}
