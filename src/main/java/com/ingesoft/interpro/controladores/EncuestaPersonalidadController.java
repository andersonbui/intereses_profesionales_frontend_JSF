package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.EncuestaPersonalidad;
import com.ingesoft.interpro.facades.EncuestaPersonalidadFacade;

import java.io.Serializable;
import java.util.Date;
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

@ManagedBean(name = "encuestaPersonalidadController")
@SessionScoped
public class EncuestaPersonalidadController extends Controller implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.EncuestaPersonalidadFacade ejbFacade;
    private List<EncuestaPersonalidad> items = null;
    private EncuestaPersonalidad selected;

    public EncuestaPersonalidadController() {
    }
    
    public void setItems(List<EncuestaPersonalidad> items) {
        this.items = items;
    }

    public EncuestaPersonalidad getSelected() {
        return selected;
    }

    public void setSelected(EncuestaPersonalidad selected) {
        this.selected = selected;
    }

    @Override
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    protected EncuestaPersonalidadFacade getFacade() {
        return ejbFacade;
    }

    public void guardarSelected() {
        getFacade().edit(getSelected());
    }

    public EncuestaPersonalidad crearEncuestaPersonalidad(Encuesta encuesta) {
        selected = new EncuestaPersonalidad(encuesta);
        selected.setFechaCreacion(new Date());
        create();
        return selected;
    }

    public void create() {
        selected = (EncuestaPersonalidad) persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EncuestaCreated"), selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        selected = (EncuestaPersonalidad) persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EncuestaUpdated"), selected);
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EncuestaDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EncuestaPersonalidad> actualesItems() {
        return items;
    }

    public List<EncuestaPersonalidad> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public EncuestaPersonalidad getEncuestaPersonalidad(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<EncuestaPersonalidad> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EncuestaPersonalidad> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = EncuestaPersonalidad.class)
    public static class EncuestaPersonalidadControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EncuestaPersonalidadController controller = (EncuestaPersonalidadController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "encuestaPersonalidadController");
            return controller.getEncuestaPersonalidad(getKey(value));
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
            if (object instanceof EncuestaPersonalidad) {
                EncuestaPersonalidad o = (EncuestaPersonalidad) object;
                return getStringKey(o.getIdEncuesta());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EncuestaPersonalidad.class.getName()});
                return null;
            }
        }

    }

}
