package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.PersonahasInstitucion;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.facades.PersonahasInstitucionFacade;

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

@ManagedBean(name = "personahasInstitucionController")
@SessionScoped
public class PersonahasInstitucionController implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.PersonahasInstitucionFacade ejbFacade;
    private List<PersonahasInstitucion> items = null;
    private PersonahasInstitucion selected;

    public PersonahasInstitucionController() {
    }

    public PersonahasInstitucion getSelected() {
        return selected;
    }

    public void setSelected(PersonahasInstitucion selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getPersonahasInstitucionPK().setIdInstitucion(selected.getInstitucion().getIdInstitucion());
        selected.getPersonahasInstitucionPK().setIdPersona(selected.getPersona().getIdPersona());
    }

    protected void initializeEmbeddableKey() {
        selected.setPersonahasInstitucionPK(new com.ingesoft.interpro.entidades.PersonahasInstitucionPK());
    }

    private PersonahasInstitucionFacade getFacade() {
        return ejbFacade;
    }

    public PersonahasInstitucion prepareCreate() {
        selected = new PersonahasInstitucion();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PersonahasInstitucionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonahasInstitucionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PersonahasInstitucionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PersonahasInstitucion> getItems() {
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

    public PersonahasInstitucion getPersonahasInstitucion(com.ingesoft.interpro.entidades.PersonahasInstitucionPK id) {
        return getFacade().find(id);
    }

    public List<PersonahasInstitucion> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PersonahasInstitucion> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PersonahasInstitucion.class)
    public static class PersonahasInstitucionControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PersonahasInstitucionController controller = (PersonahasInstitucionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "personahasInstitucionController");
            return controller.getPersonahasInstitucion(getKey(value));
        }

        com.ingesoft.interpro.entidades.PersonahasInstitucionPK getKey(String value) {
            com.ingesoft.interpro.entidades.PersonahasInstitucionPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.ingesoft.interpro.entidades.PersonahasInstitucionPK();
            key.setIdPersona(Integer.parseInt(values[0]));
            key.setIdInstitucion(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(com.ingesoft.interpro.entidades.PersonahasInstitucionPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdPersona());
            sb.append(SEPARATOR);
            sb.append(value.getIdInstitucion());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PersonahasInstitucion) {
                PersonahasInstitucion o = (PersonahasInstitucion) object;
                return getStringKey(o.getPersonahasInstitucionPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PersonahasInstitucion.class.getName()});
                return null;
            }
        }

    }

}
