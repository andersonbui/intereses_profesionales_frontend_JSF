package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.PersonaCodigoInstitucion;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.PersonaCodigoInstitucionPK;
import com.ingesoft.interpro.facades.PersonaCodigoInstitucionFacade;

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

@ManagedBean(name = "personaCodigoInstitucionController")
@SessionScoped
public class PersonaCodigoInstitucionController implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.PersonaCodigoInstitucionFacade ejbFacade;
    private List<PersonaCodigoInstitucion> items = null;
    private PersonaCodigoInstitucion selected;

    public PersonaCodigoInstitucionController() {
    }

    public PersonaCodigoInstitucion getSelected() {
        return selected;
    }

    public void setSelected(PersonaCodigoInstitucion selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getPersonaCodigoInstitucionPK().setIdCodigoInstitucion(selected.getCodigoInstitucion().getIdCodigoInstitucion());
        selected.getPersonaCodigoInstitucionPK().setIdPersona(selected.getPersona().getIdPersona());
    }

    protected void initializeEmbeddableKey() {
        selected.setPersonaCodigoInstitucionPK(new com.ingesoft.interpro.entidades.PersonaCodigoInstitucionPK());
    }

    private PersonaCodigoInstitucionFacade getFacade() {
        return ejbFacade;
    }

    public PersonaCodigoInstitucion prepareCreate() {
        selected = new PersonaCodigoInstitucion();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PersonaCodigoInstitucionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonaCodigoInstitucionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PersonaCodigoInstitucionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PersonaCodigoInstitucion> getItems() {
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

    public PersonaCodigoInstitucion getPersonaCodigoInstitucion(PersonaCodigoInstitucionPK id) {
        return getFacade().find(id);
    }

    public List<PersonaCodigoInstitucion> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PersonaCodigoInstitucion> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PersonaCodigoInstitucion.class)
    public static class PersonaCodigoInstitucionControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PersonaCodigoInstitucionController controller = (PersonaCodigoInstitucionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "personaCodigoInstitucionController");
            return controller.getPersonaCodigoInstitucion(getKey(value));
        }

        PersonaCodigoInstitucionPK getKey(String value) {
            PersonaCodigoInstitucionPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new PersonaCodigoInstitucionPK();
            key.setIdPersona(Integer.parseInt(values[0]));
            key.setIdCodigoInstitucion(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(PersonaCodigoInstitucionPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdPersona());
            sb.append(SEPARATOR);
            sb.append(value.getIdCodigoInstitucion());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PersonaCodigoInstitucion) {
                PersonaCodigoInstitucion o = (PersonaCodigoInstitucion) object;
                return getStringKey(o.getPersonaCodigoInstitucionPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PersonaCodigoInstitucion.class.getName()});
                return null;
            }
        }

    }

}
