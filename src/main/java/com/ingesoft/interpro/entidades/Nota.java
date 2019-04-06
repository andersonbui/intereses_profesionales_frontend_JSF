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
import javax.persistence.JoinColumns;
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
@Table(name = "Nota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nota.findAll", query = "SELECT n FROM Nota n")
    , @NamedQuery(name = "Nota.findByIdMateria", query = "SELECT n FROM Nota n WHERE n.notaPK.idMateria = :idMateria")
    , @NamedQuery(name = "Nota.findByNota", query = "SELECT n FROM Nota n WHERE n.nota = :nota")
    , @NamedQuery(name = "Nota.findByIdGrado", query = "SELECT n FROM Nota n WHERE n.notaPK.idGrado = :idGrado")
    , @NamedQuery(name = "Nota.findByIdEstudiante", query = "SELECT n FROM Nota n WHERE n.notaPK.idEstudiante = :idEstudiante")})
public class Nota implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NotaPK notaPK;
    @Size(max = 45)
    @Column(name = "nota")
    private String nota;
    @JoinColumn(name = "idMateria", referencedColumnName = "idMateria", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Materia materia;
    @JoinColumns({
        @JoinColumn(name = "idGrado", referencedColumnName = "idGrado", insertable = false, updatable = false)
        , @JoinColumn(name = "idEstudiante", referencedColumnName = "idEstudiante", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private EstudianteGrado estudianteGrado;

    public Nota() {
    }

    public Nota(NotaPK notaPK) {
        this.notaPK = notaPK;
    }

    public Nota(int idMateria, int idGrado, int idEstudiante) {
        this.notaPK = new NotaPK(idMateria, idGrado, idEstudiante);
    }

    public NotaPK getNotaPK() {
        return notaPK;
    }

    public void setNotaPK(NotaPK notaPK) {
        this.notaPK = notaPK;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public EstudianteGrado getEstudianteGrado() {
        return estudianteGrado;
    }

    public void setEstudianteGrado(EstudianteGrado estudianteGrado) {
        this.estudianteGrado = estudianteGrado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notaPK != null ? notaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nota)) {
            return false;
        }
        Nota other = (Nota) object;
        if ((this.notaPK == null && other.notaPK != null) || (this.notaPK != null && !this.notaPK.equals(other.notaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Nota[ notaPK=" + notaPK + " ]";
    }
    
}
