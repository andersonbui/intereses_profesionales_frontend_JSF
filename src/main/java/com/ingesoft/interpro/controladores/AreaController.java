package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Area;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.facades.AreaFacade;

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

@ManagedBean(name = "areaController")
@SessionScoped
public class AreaController implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.AreaFacade ejbFacade;
    private List<Area> items = null;
    private Area[] itemsMenos = null;
    private Area[] itemsMas = null;
    private Area[] itemsNota = null;
    private Area selected;
    private Area selected2;
    private Area selected3;

    public AreaController() {
        itemsMenos = new Area[3];  
        itemsMas = new Area[3];  
        itemsNota = new Area[3];  
    }

    public Area[] getItemsMenos() {
        return itemsMenos;
    }

    public void setItemsMenos(Area[] itemsMenos) {
        this.itemsMenos = itemsMenos;
    }

    public Area[] getItemsMas() {
        return itemsMas;
    }

    public void setItemsMas(Area[] itemsMas) {
        this.itemsMas = itemsMas;
    }

    public Area[] getItemsNota() {
        return itemsNota;
    }

    public void setItemsNota(Area[] itemsNota) {
        this.itemsNota = itemsNota;
    }

    public Area getSelected2() {
        return selected2;
    }

    public void setSelected2(Area selected2) {
        this.selected2 = selected2;
    }

    public Area getSelected3() {
        return selected3;
    }

    public void setSelected3(Area selected3) {
        this.selected3 = selected3;
    }

    public Area getSelected() {
        return selected;
    }

    public void setSelected(Area selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AreaFacade getFacade() {
        return ejbFacade;
    }

    public Area prepareCreate() {
        selected = new Area();
        initializeEmbeddableKey();
        return selected;
    }

    public boolean verificarSeleccion(Area area, Area areaActual, Area[] lista,  Area[] otraLista) {
        if (area.equals(areaActual)) {
            return false;
        }
        if(otraLista != null && (area.equals(otraLista[0]) || area.equals(otraLista[1]) || area.equals(otraLista[2]))){
            return true;
        }
        if (area.equals(lista[0]) || area.equals(lista[1]) || area.equals(lista[2])) {
            return true;
        }
        return false;

    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AreaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AreaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AreaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Area> getItems() {
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

    public Area getArea(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Area> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Area> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Area.class)
    public static class AreaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AreaController controller = (AreaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "areaController");
            return controller.getArea(getKey(value));
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
            if (object instanceof Area) {
                Area o = (Area) object;
                return getStringKey(o.getIdArea());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Area.class.getName()});
                return null;
            }
        }

    }

}
