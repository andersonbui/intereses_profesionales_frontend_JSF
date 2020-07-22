/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "ConfigMineria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfigMineria.findAll", query = "SELECT g FROM ConfigMineria g")
    , @NamedQuery(name = "ConfigMineria.findByIdConfigMineria", query = "SELECT cm FROM ConfigMineria cm WHERE cm.idConfigMineria = :id")
})
public class ConfigMineria implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idConfigMineria")
    protected Integer idConfigMineria;
    
    @JoinColumn(name = "idAreaProfesional", referencedColumnName = "idAreaProfesional")
    @ManyToOne(optional = false)
    private AreaProfesional idAreaProfesional;
    
    public Integer getIdConfigMineria() {
        return idConfigMineria;
    }

    public void setIdConfigMineria(Integer idConfigMineria) {
        this.idConfigMineria = idConfigMineria;
    }

    public AreaProfesional getIdAreaProfesional() {
        return idAreaProfesional;
    }

    public void setIdAreaProfesional(AreaProfesional idAreaProfesional) {
        this.idAreaProfesional = idAreaProfesional;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfigMineria != null ? idConfigMineria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigMineria)) {
            return false;
        }
        ConfigMineria other = (ConfigMineria) object;
        if ((this.idConfigMineria == null && other.idConfigMineria != null) || (this.idConfigMineria != null && !this.idConfigMineria.equals(other.idConfigMineria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ConfigMineria[ idConfigMineria=" + idConfigMineria + " - idAreaProfesional=" + this.idAreaProfesional + " ]";
    }
    
}
