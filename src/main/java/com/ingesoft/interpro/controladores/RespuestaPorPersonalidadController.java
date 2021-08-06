package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.RespuestaPorPersonalidad;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.EncuestaPersonalidad;
import com.ingesoft.interpro.entidades.RespuestaPorPersonalidadPK;
import com.ingesoft.interpro.facades.RespuestaPorPersonalidadFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "respuestaPorPersonalidadController")
@SessionScoped
public class RespuestaPorPersonalidadController implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.RespuestaPorPersonalidadFacade ejbFacade;
    private List<RespuestaPorPersonalidad> items = null;
    private RespuestaPorPersonalidad selected;

    public RespuestaPorPersonalidadController() {
    }

    public RespuestaPorPersonalidad getSelected() {
        return selected;
    }

    public void setSelected(RespuestaPorPersonalidad selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        if(selected.getRespuestaPorPersonalidadPK() == null) {
            selected.setRespuestaPorPersonalidadPK(new RespuestaPorPersonalidadPK());
        }
        selected.getRespuestaPorPersonalidadPK().setIdEncuesta(selected.getEncuestaPersonalidad().getIdEncuesta());
        selected.getRespuestaPorPersonalidadPK().setIdTipoPersonalidad(selected.getTipoPersonalidad().getIdTipoPersonalidad());
    }

    protected void initializeEmbeddableKey() {
        selected.setRespuestaPorPersonalidadPK(new com.ingesoft.interpro.entidades.RespuestaPorPersonalidadPK());
    }

    private RespuestaPorPersonalidadFacade getFacade() {
        return ejbFacade;
    }

    public RespuestaPorPersonalidad prepareCreate() {
        selected = new RespuestaPorPersonalidad();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaPorPersonalidadCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaPorPersonalidadUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RespuestaPorPersonalidadDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RespuestaPorPersonalidad> getItems() {
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

    public List<RespuestaPorPersonalidad> buscarRespuestaPorPersonalidadPorEncuesta(Encuesta encuesta) {
        EncuestaPersonalidad encuPerson = encuesta.getEncuestaPersonalidad();
        return getFacade().buscarRespuestaPorPersonalidadPorEncuesta(encuPerson);
    }

    public RespuestaPorPersonalidad getRespuestaPorPersonalidad(com.ingesoft.interpro.entidades.RespuestaPorPersonalidadPK id) {
        return getFacade().find(id);
    }

    public List<RespuestaPorPersonalidad> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RespuestaPorPersonalidad> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RespuestaPorPersonalidad.class)
    public static class RespuestaPorPersonalidadControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RespuestaPorPersonalidadController controller = (RespuestaPorPersonalidadController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "respuestaPorPersonalidadController");
            return controller.getRespuestaPorPersonalidad(getKey(value));
        }

        com.ingesoft.interpro.entidades.RespuestaPorPersonalidadPK getKey(String value) {
            com.ingesoft.interpro.entidades.RespuestaPorPersonalidadPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.ingesoft.interpro.entidades.RespuestaPorPersonalidadPK();
            key.setIdEncuesta(Integer.parseInt(values[0]));
            key.setIdTipoPersonalidad(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(com.ingesoft.interpro.entidades.RespuestaPorPersonalidadPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdEncuesta());
            sb.append(SEPARATOR);
            sb.append(value.getIdTipoPersonalidad());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RespuestaPorPersonalidad) {
                RespuestaPorPersonalidad o = (RespuestaPorPersonalidad) object;
                return getStringKey(o.getRespuestaPorPersonalidadPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RespuestaPorPersonalidad.class.getName()});
                return null;
            }
        }

    }

}
