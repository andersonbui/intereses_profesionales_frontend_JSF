package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.RespuestaEstilo;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.facades.RespuestaEstiloFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "respuestaEstiloController")
@SessionScoped
public class RespuestaEstiloController extends Controller implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.RespuestaEstiloFacade ejbFacade;
    private List<RespuestaEstilo> items = null;
    private RespuestaEstilo selected;

    public RespuestaEstiloController() {

    }

    public void inicializar(Encuesta selected) {

    }

    public RespuestaEstilo getSelected() {
        return selected;
    }

    public void setSelected(RespuestaEstilo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    protected RespuestaEstiloFacade getFacade() {
        return ejbFacade;
    }

    public RespuestaEstilo prepareCreate() {
        selected = new RespuestaEstilo();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaEstiloCreated"), selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaEstiloUpdated"), selected);
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RespuestaEstiloDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RespuestaEstilo> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public List<RespuestaEstilo> getItemsXEncuesta(Encuesta encuesta) {
        return getFacade().getItemsXEncuesta(encuesta);
    }
    
    public RespuestaEstilo getRespuestaEstilo(Integer id) {
        return getFacade().find(id);
    }

    public List<RespuestaEstilo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RespuestaEstilo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RespuestaEstilo.class)
    public static class RespuestaEstiloControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RespuestaEstiloController controller = (RespuestaEstiloController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "respuestaEstiloController");
            return controller.getRespuestaEstilo(getKey(value));
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
            if (object instanceof RespuestaEstilo) {
                RespuestaEstilo o = (RespuestaEstilo) object;
                return getStringKey(o.getIdRespuestaEstilo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RespuestaEstilo.class.getName()});
                return null;
            }
        }

    }

}
