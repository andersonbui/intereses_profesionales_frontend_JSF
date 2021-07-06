package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.EstadosEncuesta;
import com.ingesoft.interpro.entidades.GestionEncuestas;
import com.ingesoft.interpro.facades.GestionEncuestaFacade;

import java.io.Serializable;
import java.util.Date;
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

@ManagedBean(name = "gestionEncuestaController")
@SessionScoped
public class GestionEncuestaController extends Controllers implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.GestionEncuestaFacade ejbFacade;
    private List<GestionEncuestas> items = null;
    private GestionEncuestas selected;
    public GestionEncuestaController() {
        
    }

    public void setItems(List<GestionEncuestas> items) {
        this.items = items;
    }

    public GestionEncuestas getSelected() {
        return selected;
    }

    public void setSelected(GestionEncuestas selected) {
        this.selected = selected;
    }

    @Override
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    protected GestionEncuestaFacade getFacade() {
        return ejbFacade;
    }

    public void guardarSelected() {
        getFacade().edit(getSelected());
    }

    public String prueba() {
        GestionEncuestas ee = new GestionEncuestas();
        ee.setEstado(EstadosEncuesta.ACTIVO);
        ee.setDescripcion("una descripcion");
        ee.setFechaCreacion(new Date());
        ee = getFacade().edit(ee);
        return "hola: "+ee;
    }

    public void create() {
        selected = (GestionEncuestas) persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EncuestaCreated"), selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        selected = (GestionEncuestas) persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EncuestaUpdated"), selected);
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EncuestaDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<GestionEncuestas> actualesItems() {
        return items;
    }

    public List<GestionEncuestas> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public GestionEncuestas getEncuesta(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<GestionEncuestas> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<GestionEncuestas> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = GestionEncuestas.class)
    public static class EncuestaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GestionEncuestaController controller = (GestionEncuestaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "gestionEncuestaController");
            return controller.getEncuesta(getKey(value));
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
            if (object instanceof GestionEncuestas) {
                GestionEncuestas o = (GestionEncuestas) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Encuesta.class.getName()});
                return null;
            }
        }

    }

}
