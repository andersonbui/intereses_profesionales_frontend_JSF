package com.ingesoft.suideal.encuesta.estilos_aprendizaje.controladores;

import com.ingesoft.interpro.controladores.Controllers;
import com.ingesoft.interpro.entidades.PreguntaEstilosAprendizaje;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.facades.PreguntaEstilosAprendizajeFsFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "preguntaEstilosAprendizajeController")
@SessionScoped
public class PreguntaEstilosAprendizajeController extends Controllers implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.PreguntaEstilosAprendizajeFsFacade ejbFacade;
    private List<PreguntaEstilosAprendizaje> items = null;
    private PreguntaEstilosAprendizaje selected;

    public PreguntaEstilosAprendizajeController() {
    }

    public void inicializar(Encuesta selected) {

    }

    public PreguntaEstilosAprendizaje getSelected() {
        return selected;
    }

    public void setSelected(PreguntaEstilosAprendizaje selected) {
        this.selected = selected;
    }

    @Override
    protected void setEmbeddableKeys() {
        
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    protected PreguntaEstilosAprendizajeFsFacade getFacade() {
        return ejbFacade;
    }

    public PreguntaEstilosAprendizaje prepareCreate() {
        selected = new PreguntaEstilosAprendizaje();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PreguntaEstilosAprendizajeFsCreated"), selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PreguntaEstilosAprendizajeFsUpdated"), selected);
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PreguntaEstilosAprendizajeFsDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PreguntaEstilosAprendizaje> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public PreguntaEstilosAprendizaje getPreguntaEstilosAprendizajeFs(Integer id) {
        return getFacade().find(id);
    }

    public List<PreguntaEstilosAprendizaje> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PreguntaEstilosAprendizaje> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

}
