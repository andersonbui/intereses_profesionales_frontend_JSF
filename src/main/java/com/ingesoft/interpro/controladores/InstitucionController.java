package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Institucion;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.facades.AbstractFacade;
import com.ingesoft.interpro.facades.InstitucionFacade;

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

@ManagedBean(name = "institucionController")
@SessionScoped
public class InstitucionController extends Controller {

    @EJB
    private com.ingesoft.interpro.facades.InstitucionFacade ejbFacade;
    private List<Institucion> items = null;
    private Institucion selected;

    public InstitucionController() {
    }

    public Institucion getSelected() {
        return selected;
    }

    public void setSelected(Institucion selected) {
        this.selected = selected;
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    protected InstitucionFacade getFacade() {
        return ejbFacade;
    }

    public Institucion prepareCreate() {
        selected = new Institucion();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("InstitucionCreated"),selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("InstitucionUpdated"),selected);
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("InstitucionDeleted"),selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Institucion> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public Institucion getInstitucion(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Institucion> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Institucion> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Institucion.class)
    public static class InstitucionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InstitucionController controller = (InstitucionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "institucionController");
            return controller.getInstitucion(getKey(value));
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
            if (object instanceof Institucion) {
                Institucion o = (Institucion) object;
                return getStringKey(o.getIdInstitucion());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Institucion.class.getName()});
                return null;
            }
        }

    }

}
