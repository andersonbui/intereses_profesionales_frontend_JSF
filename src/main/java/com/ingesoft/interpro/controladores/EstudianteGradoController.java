package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.EstudianteGrado;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.EstudianteGradoPK;
import com.ingesoft.interpro.facades.EstudianteGradoFacade;

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

@ManagedBean(name = "estudianteGradoController")
@SessionScoped
public class EstudianteGradoController implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.EstudianteGradoFacade ejbFacade;
    private List<EstudianteGrado> items = null;
    private EstudianteGrado selected;

    public EstudianteGradoController() {
    }

    public EstudianteGrado getSelected() {
        return selected;
    }

    public void setSelected(EstudianteGrado selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getEstudianteGradoPK().setIdGrado(selected.getGrado().getIdGrado());
        selected.getEstudianteGradoPK().setIdEstudiante(selected.getEstudiante().getIdEstudiante());
    }

    protected void initializeEmbeddableKey() {
        selected.setEstudianteGradoPK(new EstudianteGradoPK());
    }

    private EstudianteGradoFacade getFacade() {
        return ejbFacade;
    }

    public EstudianteGrado prepareCreate() {
        selected = new EstudianteGrado();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EstudianteGradoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EstudianteGradoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EstudianteGradoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EstudianteGrado> getItems() {
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

    public EstudianteGrado obtenerUltimoEstudianteGrado(int idEstudiante){
        List<EstudianteGrado> lista = getFacade().buscarPorIdEstudiante(idEstudiante);
         selected = lista.get(0);
        return selected;
    }
    
    public EstudianteGrado getEstudianteGrado(EstudianteGradoPK id) {
        return getFacade().find(id);
    }

    public List<EstudianteGrado> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EstudianteGrado> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = EstudianteGrado.class)
    public static class EstudianteGradoControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EstudianteGradoController controller = (EstudianteGradoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "estudianteGradoController");
            return controller.getEstudianteGrado(getKey(value));
        }

        EstudianteGradoPK getKey(String value) {
            EstudianteGradoPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new EstudianteGradoPK();
            key.setIdGrado(Integer.parseInt(values[0]));
            key.setIdEstudiante(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(EstudianteGradoPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdGrado());
            sb.append(SEPARATOR);
            sb.append(value.getIdEstudiante());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof EstudianteGrado) {
                EstudianteGrado o = (EstudianteGrado) object;
                return getStringKey(o.getEstudianteGradoPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EstudianteGrado.class.getName()});
                return null;
            }
        }

    }

}
