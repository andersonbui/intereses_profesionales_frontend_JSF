package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.PreguntaAmbiente;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Usuario;
import com.ingesoft.interpro.facades.PreguntaAmbienteFacade;

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

@ManagedBean(name = "preguntaAmbienteController")
@SessionScoped
public class PreguntaAmbienteController extends Controllers implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private com.ingesoft.interpro.facades.PreguntaAmbienteFacade ejbFacade;
    private List<PreguntaAmbiente> itemsPreguntas = null;
    private PreguntaAmbiente selected;
    
    public PreguntaAmbienteController() {
    }
    
    public PreguntaAmbiente getSelected() {
        return selected;
    }

    public void setSelected(PreguntaAmbiente selected) {
        this.selected = selected;
    }

    @Override
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    protected PreguntaAmbienteFacade getFacade() {
        return ejbFacade;
    }

    public PreguntaAmbiente prepareCreate() {
        selected = new PreguntaAmbiente();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PreguntaAmbienteCreated"),selected);
        if (!JsfUtil.isValidationFailed()) {
            itemsPreguntas = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PreguntaAmbienteUpdated"),selected);
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PreguntaAmbienteDeleted"),selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            itemsPreguntas = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PreguntaAmbiente> getItems() {
        if (itemsPreguntas == null) {
            itemsPreguntas = getFacade().findAll();
        }
        return itemsPreguntas;
    }


    public PreguntaAmbiente getPreguntaAmbiente(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<PreguntaAmbiente> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PreguntaAmbiente> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PreguntaAmbiente.class)
    public static class PreguntaAmbienteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PreguntaAmbienteController controller = (PreguntaAmbienteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "preguntaAmbienteController");
            return controller.getPreguntaAmbiente(getKey(value));
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
            if (object instanceof PreguntaAmbiente) {
                PreguntaAmbiente o = (PreguntaAmbiente) object;
                return getStringKey(o.getIdPreguntaAmbiente());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PreguntaAmbiente.class.getName()});
                return null;
            }
        }

    }

}

