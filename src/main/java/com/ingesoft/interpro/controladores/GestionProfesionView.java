package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.facades.AbstractFacade;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "gestionProfesionView")
@SessionScoped
public class GestionProfesionView extends Controller implements Serializable {

    public String[] variables;
    public String variableSeleccionada;
    public String anterior;

    public GestionProfesionView() {
        variables = new String[]{
            "INGENIERÍA ARQUITECTURA URBANISMO Y AFINES",
            "AGRONOMÍA VETERINARIA Y AFINES",
            "ECONOMIA ADMINISTRACION CONTADURIA Y AFINES",
            "CIENCIAS DE LA SALUD",
            "CIENCIAS SOCIALES Y HUMANAS",
            "BELLAS ARTES",
            "CIENCIAS DE LA EDUCACIÓN",
            "MATEMÁTICAS Y CIENCIAS NATURALES",
            "FUERZA MILITAR y NO SE"
        };
    }
    
    public String[] getVariables() {
        return variables;
    }
    
    public void actualizarProfesionSeleccionada() {
        System.out.println("Actualizar varibles");
        anterior = variableSeleccionada;
    }
    
    @Override
    protected AbstractFacade getFacade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getVariableSeleccionada() {
        if(variableSeleccionada == null){
            String materiaRecuperada = "MATEMÁTICAS Y CIENCIAS NATURALES";
            anterior = materiaRecuperada;
            return materiaRecuperada;
        } else {
            return variableSeleccionada;
        }
    }

    public void setVariableSeleccionada(String variableSeleccionada) {
//        System.out.println("setVariableSeleccionada - cambiandos: "+variableSeleccionada);
        this.variableSeleccionada = variableSeleccionada;
    }
    
    public boolean noCambioProf() {
//        System.out.println("noCambioProf - variableSeleccionada: "+variableSeleccionada+" - anterior: "+anterior);
        return anterior.equals(variableSeleccionada);
    }
}
