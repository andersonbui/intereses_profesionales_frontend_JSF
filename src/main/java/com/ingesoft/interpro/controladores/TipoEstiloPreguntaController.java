package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.TipoEstiloPregunta;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.TipoEstiloPreguntaPK;
import com.ingesoft.interpro.facades.TipoEstiloPreguntaFacade;

import java.io.Serializable;
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

@ManagedBean(name = "tipoEstiloPreguntaController")
@SessionScoped
public class TipoEstiloPreguntaController extends Controllers implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.TipoEstiloPreguntaFacade ejbFacade;
    private List<TipoEstiloPregunta> items = null;
    private TipoEstiloPregunta selected;

    public TipoEstiloPreguntaController() {

    }

    public void inicializar(Encuesta selected) {

    }

    public TipoEstiloPregunta getSelected() {
        return selected;
    }

    public void setSelected(TipoEstiloPregunta selected) {
        this.selected = selected;
    }
    
    @Override
    protected void setEmbeddableKeys() {
        selected.getTipoEstiloPreguntaPK().setIdTipoEstilo(selected.getTipoEstilo().getId());
        selected.getTipoEstiloPreguntaPK().setIdPreguntaEstilosAprendizaje(selected.getPreguntaEstilosAprendizaje().getId());
    }

    protected void initializeEmbeddableKey() {
        selected.setTipoEstiloPreguntaPK(new TipoEstiloPreguntaPK());
    }

    @Override
    protected TipoEstiloPreguntaFacade getFacade() {
        return ejbFacade;
    }

    public TipoEstiloPregunta prepareCreate() {
        selected = new TipoEstiloPregunta();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoEstiloPreguntaCreated"), selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoEstiloPreguntaUpdated"), selected);
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoEstiloPreguntaDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoEstiloPregunta> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public TipoEstiloPregunta getTipoEstiloPregunta(TipoEstiloPreguntaPK id) {
        return getFacade().find(id);
    }

    public List<TipoEstiloPregunta> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TipoEstiloPregunta> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TipoEstiloPregunta.class)
    public static class TipoEstiloPreguntaControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoEstiloPreguntaController controller = (TipoEstiloPreguntaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoEstiloPreguntaController");
            return controller.getTipoEstiloPregunta(getKey(value));
        }

        TipoEstiloPreguntaPK getKey(String value) {
            TipoEstiloPreguntaPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new TipoEstiloPreguntaPK();
            key.setIdTipoEstilo(Integer.parseInt(values[0]));
            key.setIdPreguntaEstilosAprendizaje(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(TipoEstiloPreguntaPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdTipoEstilo());
            sb.append(SEPARATOR);
            sb.append(value.getIdPreguntaEstilosAprendizaje());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TipoEstiloPregunta) {
                TipoEstiloPregunta o = (TipoEstiloPregunta) object;
                return getStringKey(o.getTipoEstiloPreguntaPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoEstiloPregunta.class.getName()});
                return null;
            }
        }

    }

}
