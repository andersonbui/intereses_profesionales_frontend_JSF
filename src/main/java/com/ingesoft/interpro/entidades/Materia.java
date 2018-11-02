/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "Materia", catalog = "interpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materia.findAll", query = "SELECT m FROM Materia m")
    , @NamedQuery(name = "Materia.findByIdMateria", query = "SELECT m FROM Materia m WHERE m.materiaPK.idMateria = :idMateria")
    , @NamedQuery(name = "Materia.findByNombre", query = "SELECT m FROM Materia m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "Materia.findByAreaidArea", query = "SELECT m FROM Materia m WHERE m.materiaPK.areaidArea = :areaidArea")})
public class Materia implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MateriaPK materiaPK;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materia")
    private List<Nota> notaList;
    @JoinColumn(name = "Area_idArea", referencedColumnName = "idArea", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Area area;

    public Materia() {
    }

    public Materia(MateriaPK materiaPK) {
        this.materiaPK = materiaPK;
    }

    public Materia(int idMateria, int areaidArea) {
        this.materiaPK = new MateriaPK(idMateria, areaidArea);
    }

    public MateriaPK getMateriaPK() {
        return materiaPK;
    }

    public void setMateriaPK(MateriaPK materiaPK) {
        this.materiaPK = materiaPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Nota> getNotaList() {
        return notaList;
    }

    public void setNotaList(List<Nota> notaList) {
        this.notaList = notaList;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (materiaPK != null ? materiaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Materia)) {
            return false;
        }
        Materia other = (Materia) object;
        if ((this.materiaPK == null && other.materiaPK != null) || (this.materiaPK != null && !this.materiaPK.equals(other.materiaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.Materia[ materiaPK=" + materiaPK + " ]";
    }
    
}
