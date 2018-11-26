package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.AreaEncuesta;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.facades.AreaEncuestaFacade;

import java.io.Serializable;
import java.util.ArrayList;
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

@ManagedBean(name = "areaEncuestaController")
@SessionScoped
public class AreaEncuestaController implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.AreaEncuestaFacade ejbFacade;
    private List<AreaEncuesta> items = null;
    private AreaEncuesta selected;
    private List<String> mensajes;

    public AreaEncuestaController() {
    }

    public AreaEncuesta getSelected() {
        return selected;
    }

    public void setSelected(AreaEncuesta selected) {
        this.selected = selected;
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }

    protected void setEmbeddableKeys() {
        selected.getAreaEncuestaPK().setIdArea(selected.getArea().getIdArea());
        selected.getAreaEncuestaPK().setIdEncuesta(selected.getEncuesta().getIdEncuesta());
    }

    protected void initializeEmbeddableKey() {
        selected.setAreaEncuestaPK(new com.ingesoft.interpro.entidades.AreaEncuestaPK());
    }

    private AreaEncuestaFacade getFacade() {
        return ejbFacade;
    }

    public AreaEncuesta prepareCreate() {
        selected = new AreaEncuesta();
        initializeEmbeddableKey();
        return selected;
    }

    public AreaEncuesta prepararParaEncuesta() {
        mensajes = new ArrayList<>();
        items = new ArrayList<>();
        items.add(new AreaEncuesta("Materias de mayor preferencia", "Selecciona en orden descendente las areas que mas te gustan."));
        items.add(new AreaEncuesta("Materias de menor preferencia", "Selecciona como Area #1 la que menos le gusta, como #2 la siguiente que menos le gusta, asi susecivamente."));
        items.add(new AreaEncuesta("Materias de mayor nota", "Escoja como Area #1 el area de las materias en las cuales saca mejor nota, como #2 la siguiente area, asi sucesivamente. "));

        return selected;
    }

    public void actualizar() {
//        for (AreaEncuesta item : items) {
//            getFacade().edit(item);
//        }
    }
    public AreaEncuesta obtenerItem(int index){
        return items.get(index);
    }
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AreaEncuestaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AreaEncuestaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AreaEncuestaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<AreaEncuesta> getItems() {
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

    public AreaEncuesta getAreaEncuesta(com.ingesoft.interpro.entidades.AreaEncuestaPK id) {
        return getFacade().find(id);
    }

    public List<AreaEncuesta> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<AreaEncuesta> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = AreaEncuesta.class)
    public static class AreaEncuestaControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AreaEncuestaController controller = (AreaEncuestaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "areaEncuestaController");
            return controller.getAreaEncuesta(getKey(value));
        }

        com.ingesoft.interpro.entidades.AreaEncuestaPK getKey(String value) {
            com.ingesoft.interpro.entidades.AreaEncuestaPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.ingesoft.interpro.entidades.AreaEncuestaPK();
            key.setIdArea(Integer.parseInt(values[0]));
            key.setIdEncuesta(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(com.ingesoft.interpro.entidades.AreaEncuestaPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdArea());
            sb.append(SEPARATOR);
            sb.append(value.getIdEncuesta());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof AreaEncuesta) {
                AreaEncuesta o = (AreaEncuesta) object;
                return getStringKey(o.getAreaEncuestaPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AreaEncuesta.class.getName()});
                return null;
            }
        }

    }

}
