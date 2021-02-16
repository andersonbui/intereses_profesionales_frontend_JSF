package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.facades.AbstractFacade;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "gestionVarMinView")
@SessionScoped
public class GestionVarMineriaView extends Controller implements Serializable {

    public String[] variables;
    public String[] variablesSeleccionadas;

    public GestionVarMineriaView() {
        variables = new String[]{"personalidad 16f","Inteligencias multiples","Estilos de aprendizaje","materia con mejor nota","Materia que mas le gusta"};

    }
    
    public String[] getVariables() {
        return variables;
    }
    
    public void actualizarVariablesMineria() {
        System.out.println("Actualizar varibles");
    }
    
    @Override
    protected AbstractFacade getFacade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String[] getVariablesSeleccionadas() {
        if(variablesSeleccionadas == null){
            return new String[]{
                "personalidad 16f"
    //            ,"Inteligencias multiples"
                ,"Estilos de aprendizaje"
    //            ,"materia con mejor nota"
                ,"Materia que mas le gusta"
            };
        } else {
            return variablesSeleccionadas;
        }
    }

    public void setVariablesSeleccionadas(String[] variablesSeleccionadas) {
        this.variablesSeleccionadas = variablesSeleccionadas;
    }
    

}
