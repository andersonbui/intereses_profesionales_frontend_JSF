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
public class NotaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "Materia_idMateria")
    private int materiaidMateria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Estudiante_idEstudiante")
    private int estudianteidEstudiante;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Grado_idGrado")
    private int gradoidGrado;

    public NotaPK() {
    }

    public NotaPK(int materiaidMateria, int estudianteidEstudiante, int gradoidGrado) {
        this.materiaidMateria = materiaidMateria;
        this.estudianteidEstudiante = estudianteidEstudiante;
        this.gradoidGrado = gradoidGrado;
    }

    public int getMateriaidMateria() {
        return materiaidMateria;
    }

    public void setMateriaidMateria(int materiaidMateria) {
        this.materiaidMateria = materiaidMateria;
    }

    public int getEstudianteidEstudiante() {
        return estudianteidEstudiante;
    }

    public void setEstudianteidEstudiante(int estudianteidEstudiante) {
        this.estudianteidEstudiante = estudianteidEstudiante;
    }

    public int getGradoidGrado() {
        return gradoidGrado;
    }

    public void setGradoidGrado(int gradoidGrado) {
        this.gradoidGrado = gradoidGrado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) materiaidMateria;
        hash += (int) estudianteidEstudiante;
        hash += (int) gradoidGrado;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotaPK)) {
            return false;
        }
        NotaPK other = (NotaPK) object;
        if (this.materiaidMateria != other.materiaidMateria) {
            return false;
        }
        if (this.estudianteidEstudiante != other.estudianteidEstudiante) {
            return false;
        }
        if (this.gradoidGrado != other.gradoidGrado) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.NotaPK[ materiaidMateria=" + materiaidMateria + ", estudianteidEstudiante=" + estudianteidEstudiante + ", gradoidGrado=" + gradoidGrado + " ]";
    }
    
}
