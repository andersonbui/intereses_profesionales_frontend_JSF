package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Institucion;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "estadisticaController")
@SessionScoped
public class EstadisticaController implements Serializable {

    Institucion institucion;
    Institucion Grado;
    Institucion Estidiante;
    Date fechaInicio;
    Date fechafin;
    
    public EstadisticaController() {
        
    }

    public Institucion getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    public Institucion getGrado() {
        return Grado;
    }

    public void setGrado(Institucion Grado) {
        this.Grado = Grado;
    }

    public Institucion getEstidiante() {
        return Estidiante;
    }

    public void setEstidiante(Institucion Estidiante) {
        this.Estidiante = Estidiante;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

}
