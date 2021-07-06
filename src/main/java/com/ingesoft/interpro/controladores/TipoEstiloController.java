package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.TipoEstilo;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.facades.TipoEstiloFacade;

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

@ManagedBean(name = "tipoEstiloController")
@SessionScoped
public class TipoEstiloController  extends Controllers implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.TipoEstiloFacade ejbFacade;
    private List<TipoEstilo> items = null;
    private TipoEstilo selected;

    public TipoEstiloController() {
        
    }

    public void inicializar(Encuesta selected) {

    }

    public TipoEstilo getSelected() {
        return selected;
    }

    public void setSelected(TipoEstilo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    protected TipoEstiloFacade getFacade() {
        return ejbFacade;
    }

    public TipoEstilo prepareCreate() {
        selected = new TipoEstilo();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoEstiloCreated"), selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoEstiloUpdated"), selected);
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoEstiloDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoEstilo> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public TipoEstilo getTipoEstilo(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<TipoEstilo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TipoEstilo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TipoEstilo.class)
    public static class TipoEstiloControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoEstiloController controller = (TipoEstiloController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoEstiloController");
            return controller.getTipoEstilo(getKey(value));
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
            if (object instanceof TipoEstilo) {
                TipoEstilo o = (TipoEstilo) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoEstilo.class.getName()});
                return null;
            }
        }

    }

}
