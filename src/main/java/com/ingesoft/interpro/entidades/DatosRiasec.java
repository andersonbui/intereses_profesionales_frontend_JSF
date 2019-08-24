
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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author debian
 */
@Entity
@Table(name = "datos_riasec")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatosRiasec.findAll", query = "SELECT d FROM DatosRiasec d"),
    @NamedQuery(name = "DatosRiasec.findByIddatosRiasec", query = "SELECT d FROM DatosRiasec d WHERE d.iddatosRiasec = :iddatosRiasec"),
    @NamedQuery(name = "DatosRiasec.findByProfesion", query = "SELECT d FROM DatosRiasec d WHERE d.profesion = :profesion"),
    @NamedQuery(name = "DatosRiasec.findByTiposAmbiente", query = "SELECT DISTINCT d FROM DatosRiasec d WHERE d.idTipoAmbiente1 = :amb1 AND d.idTipoAmbiente2 = :amb2 AND d.idTipoAmbiente3 = :amb3")
})
public class DatosRiasec implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "iddatos_riasec")
    private Integer iddatosRiasec;
    @Size(max = 200)
    @Column(name = "profesion")
    private String profesion;
    @JoinColumn(name = "idTipoAmbiente1", referencedColumnName = "idTipoAmbiente")
    @ManyToOne
    private TipoAmbiente idTipoAmbiente1;
    @JoinColumn(name = "idTipoAmbiente2", referencedColumnName = "idTipoAmbiente")
    @ManyToOne
    private TipoAmbiente idTipoAmbiente2;
    @JoinColumn(name = "idTipoAmbiente3", referencedColumnName = "idTipoAmbiente")
    @ManyToOne
    private TipoAmbiente idTipoAmbiente3;

    public DatosRiasec() {
    }

    public DatosRiasec(Integer iddatosRiasec) {
        this.iddatosRiasec = iddatosRiasec;
    }

    public Integer getIddatosRiasec() {
        return iddatosRiasec;
    }

    public void setIddatosRiasec(Integer iddatosRiasec) {
        this.iddatosRiasec = iddatosRiasec;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public TipoAmbiente getIdTipoAmbiente1() {
        return idTipoAmbiente1;
    }

    public void setIdTipoAmbiente1(TipoAmbiente idTipoAmbiente1) {
        this.idTipoAmbiente1 = idTipoAmbiente1;
    }

    public TipoAmbiente getIdTipoAmbiente2() {
        return idTipoAmbiente2;
    }

    public void setIdTipoAmbiente2(TipoAmbiente idTipoAmbiente2) {
        this.idTipoAmbiente2 = idTipoAmbiente2;
    }

    public TipoAmbiente getIdTipoAmbiente3() {
        return idTipoAmbiente3;
    }

    public void setIdTipoAmbiente3(TipoAmbiente idTipoAmbiente3) {
        this.idTipoAmbiente3 = idTipoAmbiente3;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddatosRiasec != null ? iddatosRiasec.hashCode() : 0);
        return hash;
    }

//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof DatosRiasec)) {
//            return false;
//        }
//        DatosRiasec other = (DatosRiasec) object;
//        if ((this.iddatosRiasec == null && other.iddatosRiasec != null) || (this.iddatosRiasec != null && !this.iddatosRiasec.equals(other.iddatosRiasec))) {
//            return false;
//        }
//        return true;
//    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatosRiasec)) {
            return false;
        }
        DatosRiasec other = (DatosRiasec) object;
        if ((this.idTipoAmbiente1 == null && other.idTipoAmbiente1 != null) || 
                (this.idTipoAmbiente2 == null && other.idTipoAmbiente2 != null) || 
                (this.idTipoAmbiente3 == null && other.idTipoAmbiente3 != null) ||
                (this.profesion == null && other.profesion != null) ||
                (this.idTipoAmbiente1 != null && !this.idTipoAmbiente1.equals(other.idTipoAmbiente1)) ||
                (this.idTipoAmbiente2 != null && !this.idTipoAmbiente2.equals(other.idTipoAmbiente2)) ||
                (this.idTipoAmbiente3 != null && !this.idTipoAmbiente3.equals(other.idTipoAmbiente3)) ||
                (this.profesion != null && !this.profesion.equals(other.profesion))
            ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "iddatosRiasec=" + iddatosRiasec + "";
    }

}
