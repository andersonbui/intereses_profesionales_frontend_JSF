package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.GrupoUsuario;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.GrupoUsuarioPK;
import com.ingesoft.interpro.entidades.Usuario;
import com.ingesoft.interpro.facades.GrupoUsuarioFacade;

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

@ManagedBean(name = "grupoUsuarioController")
@SessionScoped
public class GrupoUsuarioController extends Controller implements Serializable{

    @EJB
    private com.ingesoft.interpro.facades.GrupoUsuarioFacade ejbFacade;
    private List<GrupoUsuario> items = null;
    private GrupoUsuario selected;

    public GrupoUsuarioController() {
    }

    public GrupoUsuario getSelected() {
        return selected;
    }

    public void setSelected(GrupoUsuario selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getGrupoUsuarioPK().setIdUsuario(selected.getUsuario1().getIdUsuario());
        selected.getGrupoUsuarioPK().setIdTipoUsuario(selected.getTipoUsuario().getIdTipoUsuario());
    }

    protected void initializeEmbeddableKey() {
        selected.setGrupoUsuarioPK(new GrupoUsuarioPK());
    }

    protected GrupoUsuarioFacade getFacade() {
        return ejbFacade;
    }
    public GrupoUsuario prepareCreate() {
        selected = new GrupoUsuario();
        initializeEmbeddableKey();
        return selected;
    }

//    public GrupoUsuario prepareCreate(String tipoUsuario, Usuario usuario) {
//        selected = new GrupoUsuario();
//        initializeEmbeddableKey();
//        selected.getGrupoUsuarioPK().setIdGrupoUsuario(tipoUsuario);
//        selected.setUsuario1(usuario);
//        return selected;
//    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("GrupoUsuarioCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("GrupoUsuarioUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("GrupoUsuarioDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<GrupoUsuario> getItems() {
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

    public GrupoUsuario getGrupoUsuario(GrupoUsuarioPK id) {
        return getFacade().find(id);
    }

    public List<GrupoUsuario> getGruposUsuario(Usuario usuario) {
        return getFacade().getGruposUsuario(usuario);
    }


    public List<GrupoUsuario> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<GrupoUsuario> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

   @FacesConverter(forClass = GrupoUsuario.class)
    public static class GrupoUsuarioControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GrupoUsuarioController controller = (GrupoUsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "grupoUsuarioController");
            return controller.getGrupoUsuario(getKey(value));
        }

        GrupoUsuarioPK getKey(String value) {
            GrupoUsuarioPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new GrupoUsuarioPK();
            key.setIdUsuario(Integer.parseInt(values[0]));
            key.setIdTipoUsuario(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(GrupoUsuarioPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdUsuario());
            sb.append(SEPARATOR);
            sb.append(value.getIdTipoUsuario());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof GrupoUsuario) {
                GrupoUsuario o = (GrupoUsuario) object;
                return getStringKey(o.getGrupoUsuarioPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), GrupoUsuario.class.getName()});
                return null;
            }
        }

    }

}
