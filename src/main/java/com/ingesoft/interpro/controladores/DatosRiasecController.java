package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.DatosRiasec;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.TipoAmbiente;
import com.ingesoft.interpro.facades.DatosRiasecFacade;

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

@ManagedBean(name = "datosRiasecController")
@SessionScoped
public class DatosRiasecController implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.DatosRiasecFacade ejbFacade;
    private List<DatosRiasec> items = null;
    private DatosRiasec selected;

    public DatosRiasecController() {
    }

    public void inicializar(Encuesta selected) {
    
    }

    public DatosRiasec getSelected() {
        return selected;
    }

    public void setSelected(DatosRiasec selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DatosRiasecFacade getFacade() {
        return ejbFacade;
    }

    public DatosRiasec prepareCreate() {
        selected = new DatosRiasec();
        initializeEmbeddableKey();
        return selected;
    }

    public boolean verificarSeleccion(DatosRiasec datosRiasec, DatosRiasec datosRiasecActual, DatosRiasec[] lista, DatosRiasec[] otraLista) {
        if (datosRiasec.equals(datosRiasecActual)) {
            return false;
        }
        if (otraLista != null && (datosRiasec.equals(otraLista[0]) || datosRiasec.equals(otraLista[1]) || datosRiasec.equals(otraLista[2]))) {
            return true;
        }
        if (datosRiasec.equals(lista[0]) || datosRiasec.equals(lista[1]) || datosRiasec.equals(lista[2])) {
            return true;
        }
        return false;

    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DatosRiasecCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DatosRiasecUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DatosRiasecDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<DatosRiasec> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
//        System.out.println("items riasec: " + items );
        return items;
    }

    public List<DatosRiasec> getItemsByTiposAmbiente(TipoAmbiente amb1, TipoAmbiente amb2, TipoAmbiente amb3) {
        List<DatosRiasec> items = getFacade().findByTiposAmbiente(amb1, amb2, amb3);
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

    public DatosRiasec getDatosRiasec(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<DatosRiasec> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<DatosRiasec> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = DatosRiasec.class)
    public static class DatosRiasecControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DatosRiasecController controller = (DatosRiasecController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "datosRiasecController");
            return controller.getDatosRiasec(getKey(value));
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
            if (object instanceof DatosRiasec) {
                DatosRiasec o = (DatosRiasec) object;
                return getStringKey(o.getIddatosRiasec());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DatosRiasec.class.getName()});
                return null;
            }
        }

    }

}
