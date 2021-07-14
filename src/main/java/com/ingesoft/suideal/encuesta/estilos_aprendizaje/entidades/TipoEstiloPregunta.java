/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades;

import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.TipoEstiloPreguntaPK;
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
    @NamedQuery(name = "TipoEstiloPregunta.findByIdTipoEstilo", query = "SELECT t FROM TipoEstiloPregunta t WHERE t.tipoEstiloPreguntaPK.idTipoEstilo = :idTipoEstilo"),
    @NamedQuery(name = "TipoEstiloPregunta.findByIdPreguntaEstilos", query = "SELECT t FROM TipoEstiloPregunta t WHERE t.tipoEstiloPreguntaPK.idPreguntaEstilosAprendizaje = :idPreguntaEstilos")})
public class TipoEstiloPregunta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TipoEstiloPreguntaPK tipoEstiloPreguntaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indice")
    private Character indice;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "opcion")
    private String opcion;
    
    @JoinColumn(name = "idTipoEstilo", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TipoEstilo tipoEstilo;
    
    @JoinColumn(name = "idPreguntaEstilosAprendizaje", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PreguntaEstilosAprendizaje preguntaEstilosAprendizaje;

    public TipoEstiloPregunta() {
    }

    public TipoEstiloPregunta(TipoEstiloPreguntaPK tipoEstiloPreguntaPK) {
        this.tipoEstiloPreguntaPK = tipoEstiloPreguntaPK;
    }

    public TipoEstiloPregunta(TipoEstiloPreguntaPK tipoEstiloPreguntaPK, Character indice, String opcion) {
        this.tipoEstiloPreguntaPK = tipoEstiloPreguntaPK;
        this.indice = indice;
        this.opcion = opcion;
    }

    public TipoEstiloPregunta(int idTipoEstilo, int idpreguntaEstilos) {
        this.tipoEstiloPreguntaPK = new TipoEstiloPreguntaPK(idTipoEstilo, idpreguntaEstilos);
    }

    public TipoEstiloPreguntaPK getTipoEstiloPreguntaPK() {
        return tipoEstiloPreguntaPK;
    }

    public void setTipoEstiloPreguntaPK(TipoEstiloPreguntaPK tipoestiloPreguntaPK) {
        this.tipoEstiloPreguntaPK = tipoestiloPreguntaPK;
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
        return tipoEstilo;
    }

    public void setTipoEstilo(TipoEstilo tipoEstilo) {
        this.tipoEstilo = tipoEstilo;
    }

    public PreguntaEstilosAprendizaje getPreguntaEstilosAprendizaje() {
        return preguntaEstilosAprendizaje;
    }

    public void setPreguntaEstilosAprendizaje(PreguntaEstilosAprendizaje preguntaEstilosAprendizaje) {
        this.preguntaEstilosAprendizaje = preguntaEstilosAprendizaje;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoEstiloPreguntaPK != null ? tipoEstiloPreguntaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEstiloPregunta)) {
            return false;
        }
        TipoEstiloPregunta other = (TipoEstiloPregunta) object;
        if ((this.tipoEstiloPreguntaPK == null && other.tipoEstiloPreguntaPK != null) || (this.tipoEstiloPreguntaPK != null && !this.tipoEstiloPreguntaPK.equals(other.tipoEstiloPreguntaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TipoEstiloPregunta[ PK=" + tipoEstiloPreguntaPK + " ]";
    }
    
}
