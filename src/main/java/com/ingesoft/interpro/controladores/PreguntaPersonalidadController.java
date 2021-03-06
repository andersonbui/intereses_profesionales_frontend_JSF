package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.PreguntaPersonalidad;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.Usuario;
import com.ingesoft.interpro.facades.PreguntaPersonalidadFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "preguntaPersonalidadController")
@SessionScoped
public class PreguntaPersonalidadController implements Serializable {

    private static final long serialVersionUID = 1L;
    @EJB
    private com.ingesoft.interpro.facades.PreguntaPersonalidadFacade ejbFacade;
    private List<PreguntaPersonalidad> items = null;
    private PreguntaPersonalidad selected;

    public PreguntaPersonalidadController() {
    }

   
    public PreguntaPersonalidad getSelected() {
        return selected;
    }

    public void setSelected(PreguntaPersonalidad selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PreguntaPersonalidadFacade getFacade() {
        return ejbFacade;
    }

    public PreguntaPersonalidad prepareCreate() {
        selected = new PreguntaPersonalidad();
        initializeEmbeddableKey();
//        pasoActual = 0;
        return selected;
    }

    public PreguntaPersonalidad preparePreguntas() {
//        pasoActual = 0;
        return null;
    }

    public void preparePreguntasPersonalidad(Usuario usuario, Encuesta encuesta) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elOtroResolver = facesContext.getApplication().getELResolver();
        RespuestaPersonalidadController respuestaPersonalidadController = (RespuestaPersonalidadController) elOtroResolver.getValue(facesContext.getELContext(), null, "respuestaPersonalidadController");
        items = getItems();
//        encuesta.setPersonalidad("IIEJ");
        respuestaPersonalidadController.prepararRespuestas(items, encuesta);
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PreguntaPersonalidadCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PreguntaPersonalidadUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PreguntaPersonalidadDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PreguntaPersonalidad> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public PreguntaPersonalidad getPreguntaPersonalidad(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<PreguntaPersonalidad> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PreguntaPersonalidad> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PreguntaPersonalidad.class)
    public static class PreguntaPersonalidadControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PreguntaPersonalidadController controller = (PreguntaPersonalidadController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "preguntaPersonalidadController");
            return controller.getPreguntaPersonalidad(getKey(value));
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
            if (object instanceof PreguntaPersonalidad) {
                PreguntaPersonalidad o = (PreguntaPersonalidad) object;
                return getStringKey(o.getIdPreguntaPersonalidad());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PreguntaPersonalidad.class.getName()});
                return null;
            }
        }

    }

}
