package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.PreguntaEstilosAprendizajeFs;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.facades.PreguntaEstilosAprendizajeFsFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

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

    @FacesConverter(forClass = PreguntaEstilosAprendizajeFs.class)
    public static class PreguntaEstilosAprendizajeFsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PreguntaEstilosAprendizajeFsController controller = (PreguntaEstilosAprendizajeFsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "preguntaEstilosAprendizajeFsController");
            return controller.getPreguntaEstilosAprendizajeFs(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PreguntaEstilosAprendizajeFs) {
                PreguntaEstilosAprendizajeFs o = (PreguntaEstilosAprendizajeFs) object;
                return getStringKey(o.getIdpreguntaEstilos());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PreguntaEstilosAprendizajeFs.class.getName()});
                return null;
            }
        }

    }

    public void preparePreguntasEstilosApren( Encuesta encuesta) {
        RespuestaEstiloController respuestaEstiloController = getRespuestaEstiloController();
        items = getItems();
        respuestaEstiloController.prepararRespuestas(items, encuesta);
    }
}
