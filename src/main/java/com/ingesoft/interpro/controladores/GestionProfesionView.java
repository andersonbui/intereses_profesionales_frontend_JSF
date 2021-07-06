package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.AreaProfesional;
import com.ingesoft.interpro.entidades.ConfigMineria;
import com.ingesoft.interpro.facades.ConfigMineriaFacade;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "gestionProfesionView")
@SessionScoped
public class GestionProfesionView extends Controllers implements Serializable {

    @EJB
    public ConfigMineriaFacade ejbFacade;
    public ConfigMineria gestionProfesionSeleccionada;
    public AreaProfesional anterior;
    public ConfigMineria seleccionado;

    public GestionProfesionView() {
    }
    
    /**
     * Guargdar Objeto seleccionado
     */
    public void actualizarProfesionSeleccionada() {
        System.out.println("Actualizar varibles");
        anterior = seleccionado.getIdAreaProfesional();
        if(seleccionado != null){
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProfesionSeleccionadaUpdated"), seleccionado);
        }
    }
    
    public AreaProfesional getVariableSeleccionada() {
        seleccionado = recuperarConfigMineria();
        if(seleccionado != null){
            anterior = seleccionado.getIdAreaProfesional();
            return seleccionado.getIdAreaProfesional();
        }
        return null;
    }

    public void setVariableSeleccionada(AreaProfesional variableSeleccionada) {
//        System.out.println("setVariableSeleccionada - cambiandos: "+variableSeleccionada);
        this.seleccionado.setIdAreaProfesional(variableSeleccionada);
        
    }
    
    public boolean noCambioProf() {
        if(anterior != null & seleccionado != null){
            return anterior.equals(seleccionado.getIdAreaProfesional());
        }
        return false;
    }
    
    @Override
    protected ConfigMineriaFacade getFacade() {
        return ejbFacade;
    }
    
    public ConfigMineria recuperarConfigMineria() {
        if (seleccionado == null) {
            List<ConfigMineria> lista = getFacade().findAll();
            if(lista!= null && !lista.isEmpty()) {
                seleccionado = lista.get(0);
            }
        }
        return seleccionado;
    }

}
