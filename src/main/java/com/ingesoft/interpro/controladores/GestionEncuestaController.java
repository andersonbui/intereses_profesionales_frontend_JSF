package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.EstadosEncuesta;
import com.ingesoft.interpro.entidades.GestionEncuesta;
import com.ingesoft.interpro.facades.EncuestaFacade;
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
public class GestionEncuestaController extends Controller implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.GestionEncuestaFacade ejbFacade;
    private List<GestionEncuesta> items = null;
    private GestionEncuesta selected;
    public GestionEncuestaController() {
        
    }

    public void setItems(List<GestionEncuesta> items) {
        this.items = items;
    }

    public GestionEncuesta getSelected() {
        return selected;
    }

    public void setSelected(GestionEncuesta selected) {
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
        GestionEncuesta ee = new GestionEncuesta();
        ee.setEstado(EstadosEncuesta.ACTIVO);
        ee.setDescripcion("una descripcion");
        ee.setFechaCreacion(new Date());
        ee = getFacade().edit(ee);
        return "hola: "+ee;
    }

    public void create() {
        selected = (GestionEncuesta) persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EncuestaCreated"), selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        selected = (GestionEncuesta) persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EncuestaUpdated"), selected);
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EncuestaDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<GestionEncuesta> actualesItems() {
        return items;
    }

    public List<GestionEncuesta> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public GestionEncuesta getEncuesta(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<GestionEncuesta> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<GestionEncuesta> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = GestionEncuesta.class)
    public static class EncuestaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GestionEncuestaController controller = (GestionEncuestaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "encuestaController");
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
            if (object instanceof GestionEncuesta) {
                GestionEncuesta o = (GestionEncuesta) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Encuesta.class.getName()});
                return null;
            }
        }

    }

}
