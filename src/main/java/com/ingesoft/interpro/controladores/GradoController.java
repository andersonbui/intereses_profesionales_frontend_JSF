package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Grado;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.facades.GradoFacade;

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

@ManagedBean(name = "gradoController")
@SessionScoped
public class GradoController extends Controllers {

    @EJB
    private com.ingesoft.interpro.facades.GradoFacade ejbFacade;
    private List<Grado> items = null;
    private Grado selected;

    public GradoController() {
    }

    public Grado getSelected() {
        return selected;
    }

    public void setSelected(Grado selected) {
        this.selected = selected;
    }

    protected void initializeEmbeddableKey() {
    }

    public Grado prepareCreate() {
        selected = new Grado();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("GradoCreated"),selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("GradoUpdated"),selected);
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("GradoDeleted"),selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Grado> getItems() {
        items = getFacade().findAll();
        
        return items;
    }

    @Override
    protected GradoFacade getFacade() {
        return this.ejbFacade;
    }
    
    public List<Grado> getItemsInstitucionEstudiante(Estudiante estudiante) {
        items = getFacade().findPorInstitucion(estudiante.getIdPersona().getIdInstitucion());
        return items;
    }
   
    public Grado getGrado(java.lang.Integer id) {
        return (Grado) getFacade().find(id);
    }

    public List<Grado> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Grado> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }


    @FacesConverter(forClass = Grado.class)
    public static class GradoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GradoController controller = (GradoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "gradoController");
            return controller.getGrado(getKey(value));
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
            if (object instanceof Grado) {
                Grado o = (Grado) object;
                return getStringKey(o.getIdGrado());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Grado.class.getName()});
                return null;
            }
        }

    }

}
