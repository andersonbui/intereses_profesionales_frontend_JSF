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
@Table(name = "Pregunta", catalog = "interpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pregunta.findAll", query = "SELECT p FROM Pregunta p")
    , @NamedQuery(name = "Pregunta.findByIdPregunta", query = "SELECT p FROM Pregunta p WHERE p.idPregunta = :idPregunta")
    , @NamedQuery(name = "Pregunta.findByEnunciado", query = "SELECT p FROM Pregunta p WHERE p.enunciado = :enunciado")
    , @NamedQuery(name = "Pregunta.findBySegundoEnunciado", query = "SELECT p FROM Pregunta p WHERE p.segundoEnunciado = :segundoEnunciado")
    , @NamedQuery(name = "Pregunta.findBySuma", query = "SELECT p FROM Pregunta p WHERE p.suma = :suma")})
public class Pregunta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPregunta")
    private Integer idPregunta;
    @Size(max = 100)
    @Column(name = "enunciado")
    private String enunciado;
    @Size(max = 100)
    @Column(name = "segundo_enunciado")
    private String segundoEnunciado;
    @Column(name = "suma")
    private Boolean suma;
    @JoinColumn(name = "idAmbiente", referencedColumnName = "idAmbientePersonalidad")
    @ManyToOne(optional = false)
    private AmbientePersonalidad idAmbiente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pregunta")
    private List<Respuesta> respuestaList;

    public Pregunta() {
    }

    public Pregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Integer getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getSegundoEnunciado() {
        return segundoEnunciado;
    }

    public void setSegundoEnunciado(String segundoEnunciado) {
        this.segundoEnunciado = segundoEnunciado;
    }

    public Boolean getSuma() {
        return suma;
    }

    public void setSuma(Boolean suma) {
        this.suma = suma;
    }

    public AmbientePersonalidad getIdAmbiente() {
        return idAmbiente;
    }

    public void setIdAmbiente(AmbientePersonalidad idAmbiente) {
        this.idAmbiente = idAmbiente;
    }

    @XmlTransient
    public List<Respuesta> getRespuestaList() {
        return respuestaList;
    }

    public void setRespuestaList(List<Respuesta> respuestaList) {
        this.respuestaList = respuestaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPregunta != null ? idPregunta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pregunta)) {
            return false;
        }
        Pregunta other = (Pregunta) object;
        if ((this.idPregunta == null && other.idPregunta != null) || (this.idPregunta != null && !this.idPregunta.equals(other.idPregunta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ingesoft.interpro.entidades.Pregunta[ idPregunta=" + idPregunta + " ]";
    }
    
}
