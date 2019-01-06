package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Persona;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.entidades.Usuario;
import com.ingesoft.interpro.facades.PersonaFacade;

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

@ManagedBean(name = "personaController")
@SessionScoped
public class PersonaController extends Controller implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.PersonaFacade ejbFacade;
    private List<Persona> items = null;
    private Persona selected;
    private final String[] tiposEstadoUsuario;

    public PersonaController() {
        this.tiposEstadoUsuario = new String[]{UsuarioController.ACTIVO, UsuarioController.INAACTIVO, UsuarioController.EN_ESPERA};
    }

    public String[] getTiposEstadoUsuario() {
        return tiposEstadoUsuario;
    }

    public Persona getSelected() {
        return selected;
    }

    public void setSelected(Persona selected) {
        this.selected = selected;
    }

    public void estudianteSeleccionado(Persona persona) {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        DepartamentoController departamentoController = getDepartamentoController();
        CiudadController ciudadController = (CiudadController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "ciudadController");

        if (persona.getIdCiudad() != null) {
            ciudadController.setSelected(persona.getIdCiudad());
            if (persona.getIdCiudad().getIdDepartamento() != null) {
                departamentoController.setSelected(persona.getIdCiudad().getIdDepartamento());
            }

        }
    }

    @Override
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    public PersonaFacade getFacade() {
        return ejbFacade;
    }

    public Persona prepareCreate() {
        UsuarioController usuarioController = getUsuarioController();
        Usuario usuario = usuarioController.prepareCreate();
        selected = new Persona();
        selected.setIdUsuario(usuario);
        initializeEmbeddableKey();
        return selected;
    }

    public UsuarioController getUsuarioController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UsuarioController usuarioController = (UsuarioController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "usuarioController");
        return usuarioController;
    }

    public EstudianteController getEstudianteController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        EstudianteController estudianteController = (EstudianteController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "estudianteController");
        return estudianteController;
    }

    public DepartamentoController getDepartamentoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        DepartamentoController controllerPersona = (DepartamentoController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "departamentoController");
        return controllerPersona;
    }

    public Persona prepareUpdate(Estudiante estudiante) {
        if (estudiante != null) {
            return prepareUpdate(estudiante.getIdPersona());
        }
        return null;
    }

    public Persona prepareUpdate(Persona persona) {
        selected = persona;

        if (selected != null) {
            if (!selected.getEstudianteList().isEmpty()) {
                EstudianteController estudianteController = getEstudianteController();
                estudianteController.setSelected(selected.getEstudianteList().get(0));
            }
            if (selected.getIdCiudad() != null) {
                DepartamentoController deptoController = getDepartamentoController();
                deptoController.setSelected(selected.getIdCiudad().getIdDepartamento());
            }
        }

        return selected;
    }

    public Persona create() {
        Persona perso = (Persona) persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PersonaCreated"), selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        return perso;
    }

    public void update() {
        Persona perso = (Persona) persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonaUpdated"), selected);
//        UsuarioController usuarioController = getUsuarioController();
    }

    public void destroy() {
        Persona perso = (Persona) persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PersonaDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Persona> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public Persona getPersona(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Persona> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Persona> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Persona.class)
    public static class PersonaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PersonaController controller = (PersonaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "personaController");
            return controller.getPersona(getKey(value));
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
            if (object instanceof Persona) {
                Persona o = (Persona) object;
                return getStringKey(o.getIdPersona());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Persona.class.getName()});
                return null;
            }
        }

    }

}
