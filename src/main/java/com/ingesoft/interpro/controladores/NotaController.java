package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Nota;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.facades.NotaFacade;

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

@ManagedBean(name = "notaController")
@SessionScoped
public class NotaController implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.NotaFacade ejbFacade;
    private List<Nota> items = null;
    private Nota selected;

    public NotaController() {
    }

    public Nota getSelected() {
        return selected;
    }

    public void setSelected(Nota selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getNotaPK().setEstudianteidEstudiante(selected.getEstudiante().getIdEstudiante());
        selected.getNotaPK().setMateriaidMateria(selected.getMateria().getIdMateria());
        selected.getNotaPK().setGradoidGrado(selected.getGrado().getIdGrado());
    }

    protected void initializeEmbeddableKey() {
        selected.setNotaPK(new com.ingesoft.interpro.entidades.NotaPK());
    }

    private NotaFacade getFacade() {
        return ejbFacade;
    }

    public Nota prepareCreate() {
        selected = new Nota();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("NotaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("NotaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("NotaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Nota> getItems() {
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

    public Nota getNota(com.ingesoft.interpro.entidades.NotaPK id) {
        return getFacade().find(id);
    }

    public List<Nota> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Nota> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Nota.class)
    public static class NotaControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NotaController controller = (NotaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "notaController");
            return controller.getNota(getKey(value));
        }

        com.ingesoft.interpro.entidades.NotaPK getKey(String value) {
            com.ingesoft.interpro.entidades.NotaPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.ingesoft.interpro.entidades.NotaPK();
            key.setMateriaidMateria(Integer.parseInt(values[0]));
            key.setEstudianteidEstudiante(Integer.parseInt(values[1]));
            key.setGradoidGrado(Integer.parseInt(values[2]));
            return key;
        }

        String getStringKey(com.ingesoft.interpro.entidades.NotaPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getMateriaidMateria());
            sb.append(SEPARATOR);
            sb.append(value.getEstudianteidEstudiante());
            sb.append(SEPARATOR);
            sb.append(value.getGradoidGrado());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Nota) {
                Nota o = (Nota) object;
                return getStringKey(o.getNotaPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Nota.class.getName()});
                return null;
            }
        }

    }

}
