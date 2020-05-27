package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Usuario;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.GrupoUsuario;
import com.ingesoft.interpro.facades.UsuarioFacade;

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

@ManagedBean(name = "usuarioController")
@SessionScoped
public class UsuarioController extends Controller implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.UsuarioFacade ejbFacade;
    private List<Usuario> items = null;
    private Usuario selected;

    public static final String EN_PROCESO = "EN_PROCESO";
    public static final String EN_ESPERA = "EN_ESPERA";
    public static final String ACTIVO = "ACTIVO";
    public static final String INAACTIVO = "INACTIVO";

    public static final String TIPO_ESTUDIANTE = "ESTUDIANTE";
    public static final String TIPO_DOCENTE = "DOCENTE";
    public static final String TIPO_ADMINISTRADOR = "ADMINISTRADOR";

    public UsuarioController() {
    }

    public Usuario getSelected() {
        return selected;
    }

    public void setSelected(Usuario selected) {
        this.selected = selected;
    }

    @Override
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    protected UsuarioFacade getFacade() {
        return ejbFacade;
    }

    public Usuario prepareCreate() {
        selected = new Usuario();
        initializeEmbeddableKey();
        getGrupoUsuarioController().prepareCreate();
        selected.setEstado(EN_ESPERA);
        return selected;
    }

    public void create() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioCreated"), selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        Usuario unselected = (Usuario) persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdated"), selected);
        GrupoUsuarioController grupoUsuarioController = getGrupoUsuarioController();
        
        for (GrupoUsuario grupoUsuario : unselected.getGrupoUsuarioList()) {
            grupoUsuario.setUsuario(unselected.getUsuario());
            grupoUsuarioController.setSelected(grupoUsuario);
            grupoUsuarioController.create();
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UsuarioDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Usuario> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<Usuario> items) {
        this.items = items;
    }
 
    public boolean esEstudiante(java.lang.Integer idUsuario) {
        return getFacade().esEstudiante(idUsuario);
    }
    
    public Usuario getUsuario(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public Usuario obtUsuarioPorToken(String token) {
        return getFacade().obtUsuarioPorToken(token);
    }
    
    public Usuario obtUsuarioPorTokenRecuperacion(String token) {
        return getFacade().obtUsuarioPorTokenRecuperacion(token);
    }
    
    public Usuario obtUsuarioPorEmail(String email) {
        return getFacade().obtUsuarioPorEmail(email);
    }
    
    public List<Usuario> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Usuario> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
            return controller.getUsuario(getKey(value));
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
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getIdUsuario());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Usuario.class.getName()});
                return null;
            }
        }

    }

}
