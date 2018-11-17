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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "AreaEncuesta", catalog = "interpro2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AreaEncuesta.findAll", query = "SELECT a FROM AreaEncuesta a")
    , @NamedQuery(name = "AreaEncuesta.findByIdArea", query = "SELECT a FROM AreaEncuesta a WHERE a.areaEncuestaPK.idArea = :idArea")
    , @NamedQuery(name = "AreaEncuesta.findByIdEncuesta", query = "SELECT a FROM AreaEncuesta a WHERE a.areaEncuestaPK.idEncuesta = :idEncuesta")
    , @NamedQuery(name = "AreaEncuesta.findByPosicion", query = "SELECT a FROM AreaEncuesta a WHERE a.posicion = :posicion")
    , @NamedQuery(name = "AreaEncuesta.findByGusto", query = "SELECT a FROM AreaEncuesta a WHERE a.gusto = :gusto")})
public class AreaEncuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AreaEncuestaPK areaEncuestaPK;
    @Size(max = 45)
    @Column(name = "posicion")
    private String posicion;
    @Size(max = 45)
    @Column(name = "gusto")
    private String gusto;
    @JoinColumn(name = "idArea", referencedColumnName = "idArea", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Area area;
    @JoinColumn(name = "idEncuesta", referencedColumnName = "idEncuesta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Encuesta encuesta;
    @JoinColumn(name = "idTipoEleccionMateria", referencedColumnName = "idTipoEleccionMateria")
    @ManyToOne(optional = false)
    private TipoEleccionMateria idTipoEleccionMateria;

    public AreaEncuesta() {
    }

    public AreaEncuesta(AreaEncuestaPK areaEncuestaPK) {
        this.areaEncuestaPK = areaEncuestaPK;
    }

    public AreaEncuesta(int idArea, int idEncuesta) {
        this.areaEncuestaPK = new AreaEncuestaPK(idArea, idEncuesta);
    }

    public AreaEncuestaPK getAreaEncuestaPK() {
        return areaEncuestaPK;
    }

    public void setAreaEncuestaPK(AreaEncuestaPK areaEncuestaPK) {
        this.areaEncuestaPK = areaEncuestaPK;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getGusto() {
        return gusto;
    }

    public void setGusto(String gusto) {
        this.gusto = gusto;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }

    public TipoEleccionMateria getIdTipoEleccionMateria() {
        return idTipoEleccionMateria;
    }

    public void setIdTipoEleccionMateria(TipoEleccionMateria idTipoEleccionMateria) {
        this.idTipoEleccionMateria = idTipoEleccionMateria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (areaEncuestaPK != null ? areaEncuestaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AreaEncuesta)) {
            return false;
        }
        AreaEncuesta other = (AreaEncuesta) object;
        if ((this.areaEncuestaPK == null && other.areaEncuestaPK != null) || (this.areaEncuestaPK != null && !this.areaEncuestaPK.equals(other.areaEncuestaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingeniosoft.interpro.entidades.AreaEncuesta[ areaEncuestaPK=" + areaEncuestaPK + " ]";
    }
    
}
