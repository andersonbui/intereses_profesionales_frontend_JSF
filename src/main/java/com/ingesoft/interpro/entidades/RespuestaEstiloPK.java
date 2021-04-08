/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author debian
 */
@Embeddable
public class RespuestaEstiloPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "Encuesta_idEncuesta")
    private int Encuesta_idEncuesta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idpregunta_estilos")
    private int idpregunta_estilos;

    public RespuestaEstiloPK() {
    }

    public RespuestaEstiloPK(int idpregunta_estilos, int Encuesta_idEncuesta) {
        this.idpregunta_estilos = idpregunta_estilos;
        this.Encuesta_idEncuesta = Encuesta_idEncuesta;
    }

    public int getEncuesta_idEncuesta() {
        return Encuesta_idEncuesta;
    }

    public void setEncuesta_idEncuesta(int Encuesta_idEncuesta) {
        this.Encuesta_idEncuesta = Encuesta_idEncuesta;
    }

    public int getIdpregunta_estilos() {
        return idpregunta_estilos;
    }

    public void setIdpregunta_estilos(int idpregunta_estilos) {
        this.idpregunta_estilos = idpregunta_estilos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idpregunta_estilos;
        hash += (int) Encuesta_idEncuesta;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaEstiloPK)) {
            return false;
        }
        RespuestaEstiloPK other = (RespuestaEstiloPK) object;
        if (this.idpregunta_estilos != other.idpregunta_estilos) {
            return false;
        }
        if (this.Encuesta_idEncuesta != other.Encuesta_idEncuesta) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RespuestaEstiloPK{" + "Encuesta_idEncuesta=" + Encuesta_idEncuesta + ", idpregunta_estilos=" + idpregunta_estilos + '}';
    }
    
}
