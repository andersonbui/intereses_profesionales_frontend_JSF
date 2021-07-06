package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.TipoUsuario;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.GrupoUsuario;
import com.ingesoft.interpro.entidades.Persona;
import com.ingesoft.interpro.facades.TipoUsuarioFacade;

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

@ManagedBean(name = "tipoUsuarioController")
@SessionScoped
public class TipoUsuarioController extends Controllers implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.TipoUsuarioFacade ejbFacade;
    private List<TipoUsuario> items = null;
    private TipoUsuario selected;

    public TipoUsuarioController() {
    }

    public TipoUsuario getSelected() {
        return selected;
    }

    public void setSelected(TipoUsuario selected) {
        this.selected = selected;
    }

    @Override
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    protected TipoUsuarioFacade getFacade() {
        return ejbFacade;
    }

    public boolean esAdmin(String tipo) {
//        return "ADMINISTRADOR".equals(tipo);
        return false;
    }

    public TipoUsuario prepareCreate() {
        selected = new TipoUsuario();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoUsuarioCreated"), selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoUsuarioUpdated"), selected);
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoUsuarioDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoUsuario> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<TipoUsuario> getItemsFiltrados(List<GrupoUsuario> lista) {
        if (items == null) {
            items = getFacade().findAll();

            TipoUsuarioController tipoUsuarioController = getTipoUsuarioController();
            TipoUsuario tipoUsuarioAdmin = tipoUsuarioController.obtenerPorTipo(UsuarioController.TIPO_ADMINISTRADOR);
            boolean isadmin = false;
            for (GrupoUsuario item : lista) {
                if (item.getTipoUsuario().equals(tipoUsuarioAdmin)) {
                    isadmin = true;
                }
            }
            if (!isadmin) {
                items.remove(tipoUsuarioAdmin);
            }
        }
        return items;
    }

    public TipoUsuario getTipoUsuario(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public TipoUsuario obtenerPorTipo(String tipo) {
        return getFacade().obtenerPorTipo(tipo);
    }

    public List<TipoUsuario> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TipoUsuario> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TipoUsuario.class)
    public static class TipoUsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoUsuarioController controller = (TipoUsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoUsuarioController");
            return controller.getTipoUsuario(getKey(value));
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
            if (object instanceof TipoUsuario) {
                TipoUsuario o = (TipoUsuario) object;
                return getStringKey(o.getIdTipoUsuario());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoUsuario.class.getName()});
                return null;
            }
        }

    }

}
