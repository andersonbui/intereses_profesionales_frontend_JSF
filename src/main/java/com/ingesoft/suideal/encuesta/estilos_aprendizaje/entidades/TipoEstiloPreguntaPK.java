/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Personal
 */
@Embeddable
public class TipoEstiloPreguntaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idTipoEstilo")
    private int idTipoEstilo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPreguntaEstilosAprendizaje")
    private int idPreguntaEstilosAprendizaje;

    public TipoEstiloPreguntaPK() {
    }

    public TipoEstiloPreguntaPK(int idTipoEstilo, int idPreguntaEstilos) {
        this.idTipoEstilo = idTipoEstilo;
        this.idPreguntaEstilosAprendizaje = idPreguntaEstilos;
    }

    public int getIdTipoEstilo() {
        return idTipoEstilo;
    }

    public void setIdTipoEstilo(int idTipoEstilo) {
        this.idTipoEstilo = idTipoEstilo;
    }

    public int getIdPreguntaEstilosAprendizaje() {
        return idPreguntaEstilosAprendizaje;
    }

    public void setIdPreguntaEstilosAprendizaje(int idPreguntaEstilosAprendizaje) {
        this.idPreguntaEstilosAprendizaje = idPreguntaEstilosAprendizaje;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idTipoEstilo;
        hash += (int) idPreguntaEstilosAprendizaje;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEstiloPreguntaPK)) {
            return false;
        }
        TipoEstiloPreguntaPK other = (TipoEstiloPreguntaPK) object;
        if (this.idTipoEstilo != other.idTipoEstilo) {
            return false;
        }
        if (this.idPreguntaEstilosAprendizaje != other.idPreguntaEstilosAprendizaje) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TipoestiloPreguntaPK[ idTipoEstilo=" + idTipoEstilo + ", idPreguntaEstilosAprendizaje=" + idPreguntaEstilosAprendizaje + " ]";
    }
    
}
