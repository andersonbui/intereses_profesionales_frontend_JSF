package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.PreguntaEstilosAprendizajeFs;
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

@ManagedBean(name = "preguntaEstilosAprendizajeFsController")
@SessionScoped
public class PreguntaEstilosAprendizajeFsController extends Controller implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.PreguntaEstilosAprendizajeFsFacade ejbFacade;
    private List<PreguntaEstilosAprendizajeFs> items = null;
    private PreguntaEstilosAprendizajeFs selected;

    public PreguntaEstilosAprendizajeFsController() {
    }

    public void inicializar(Encuesta selected) {

    }

    public PreguntaEstilosAprendizajeFs getSelected() {
        return selected;
    }

    public void setSelected(PreguntaEstilosAprendizajeFs selected) {
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

    public PreguntaEstilosAprendizajeFs prepareCreate() {
        selected = new PreguntaEstilosAprendizajeFs();
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

    public List<PreguntaEstilosAprendizajeFs> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public PreguntaEstilosAprendizajeFs getPreguntaEstilosAprendizajeFs(Integer id) {
        return getFacade().find(id);
    }

    public List<PreguntaEstilosAprendizajeFs> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PreguntaEstilosAprendizajeFs> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

}
