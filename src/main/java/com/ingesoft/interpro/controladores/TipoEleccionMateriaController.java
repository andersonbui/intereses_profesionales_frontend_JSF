package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.TipoEleccionMateria;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.facades.TipoEleccionMateriaFacade;

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

@ManagedBean(name = "tipoEleccionMateriaController")
@SessionScoped
public class TipoEleccionMateriaController implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.TipoEleccionMateriaFacade ejbFacade;
    private List<TipoEleccionMateria> items = null;
    private TipoEleccionMateria selected;
    public static int TIPO_ELECCION_MATERIA_MAYOR = 2;
    public static int TIPO_ELECCION_MATERIA_MENOR = 1;
    public static int TIPO_ELECCION_MATERIA_NOTA = 3;

    public TipoEleccionMateriaController() {
    }

    public TipoEleccionMateria getSelected() {
        return selected;
    }

    public void setSelected(TipoEleccionMateria selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipoEleccionMateriaFacade getFacade() {
        return ejbFacade;
    }

    public TipoEleccionMateria prepareCreate() {
        selected = new TipoEleccionMateria();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoEleccionMateriaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoEleccionMateriaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoEleccionMateriaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoEleccionMateria> getItems() {
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

    public TipoEleccionMateria getTipoEleccionMateria(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public TipoEleccionMateria getTipoEleccionMateriaMayor() {
        return getFacade().find(TIPO_ELECCION_MATERIA_MAYOR);
    }
    
    public TipoEleccionMateria getTipoEleccionMateriaMenor() {
        return getFacade().find(TIPO_ELECCION_MATERIA_MENOR);
    }

    public TipoEleccionMateria getTipoEleccionMateriaPorNota() {
        return getFacade().find(TIPO_ELECCION_MATERIA_NOTA);
    }

    public List<TipoEleccionMateria> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TipoEleccionMateria> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TipoEleccionMateria.class)
    public static class TipoEleccionMateriaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoEleccionMateriaController controller = (TipoEleccionMateriaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoEleccionMateriaController");
            return controller.getTipoEleccionMateria(getKey(value));
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
            if (object instanceof TipoEleccionMateria) {
                TipoEleccionMateria o = (TipoEleccionMateria) object;
                return getStringKey(o.getIdTipoEleccionMateria());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoEleccionMateria.class.getName()});
                return null;
            }
        }

    }

}
