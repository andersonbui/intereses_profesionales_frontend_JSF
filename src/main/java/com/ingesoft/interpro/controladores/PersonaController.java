package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Persona;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.controladores.util.Utilidades;
import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.entidades.EstudianteGrado;
import com.ingesoft.interpro.entidades.GrupoUsuario;
import com.ingesoft.interpro.entidades.Usuario;
import com.ingesoft.interpro.facades.PersonaFacade;

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
import org.primefaces.context.RequestContext;

@ManagedBean(name = "personaController")
@SessionScoped
public class PersonaController extends Controller implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.PersonaFacade ejbFacade;
    private List<Persona> items = null;
    private Persona selected;
    private final String[] tiposEstadoUsuario;
    private boolean editar;

    public PersonaController() {
        this.tiposEstadoUsuario = new String[]{UsuarioController.ACTIVO, UsuarioController.INAACTIVO, UsuarioController.EN_ESPERA};
    }

    public boolean isEditar() {
        return editar;
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

        DepartamentoController departamentoController = getDepartamentoController();
        CiudadController ciudadController = getCiudadController();

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
        Persona persona = prepareCreateParaRegistrar();
        LoginController loginController = getLoginController();
        selected.setIdCiudad(loginController.getPersonaActual().getIdCiudad());
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('PersonaEditDialog').show()");
        return persona;
    }

    public Persona prepareCreateParaRegistrar() {
        editar = false;
        UsuarioController usuarioController = getUsuarioController();
        Usuario usuario = usuarioController.prepareCreate();

        selected = new Persona();
        usuario.setClave("Ninguna");
        selected.setIdUsuario(usuario);

        getDepartamentoController().setSelected(null);
        getPaisController().setSelected(null);
        getEstudianteController().setSelected(null);
        initializeEmbeddableKey();
        return selected;
    }

    public Persona prepareUpdate() {
        return prepareUpdate(null);
    }

    public Persona prepareUpdate(Persona persona) {
        if (persona != null) {
            selected = persona;
        }
        editar = true;
        if (selected != null) {
            EstudianteController estudianteController = getEstudianteController();
            if (selected.getEstudianteList() != null && !selected.getEstudianteList().isEmpty()) {
                Estudiante estudiante = selected.getEstudianteList().get(0);
                estudianteController.setSelected(estudiante);
                EstudianteGradoController estudianteGradoController = getEstudianteGradoController();
                EstudianteGrado estudianteGrado = estudianteGradoController.obtenerUltimoEstudianteGrado(estudiante.getIdEstudiante());
                if (estudianteGrado == null) {
                    estudianteGrado = getEstudianteGradoController().prepareCreate();
                    estudianteGrado.setEstudiante(estudiante);
                }
                System.out.println("estudianteGrado: " + estudianteGrado.getGrado());
            } else {
                estudianteController.setSelected(null);
            }
            DepartamentoController deptoController = getDepartamentoController();
            if (selected.getIdCiudad() != null) {
                deptoController.setSelected(selected.getIdCiudad().getIdDepartamento());
            } else {
                deptoController.setSelected(null);
                getPaisController().setSelected(null);
            }

            UsuarioController usuarioController = getUsuarioController();
            usuarioController.setSelected(selected.getIdUsuario());

//            RequestContext requestContext = RequestContext.getCurrentInstance();
//            requestContext.execute("PF('PerfilEditDialog').show()");
        }
        return selected;
    }

    /**
     * Usado para crear un usuario desde una cuenta de docente o administrador
     *
     * @return
     */
    public Persona crearPorAdminDocente() {
        LoginController loginController = getLoginController();
        if (loginController.getActual() == null) {
            if (!loginController.isDocente()) {
                return null;
            }
        }
        selected.setEmail(selected.getIdUsuario().getUsuario());
        //guardar en la base de datos y refrescar cambios en app
        create();
        Usuario un_usuario = selected.getIdUsuario();
        un_usuario.setTokenAcesso(Utilidades.generarToken("" + un_usuario.getIdUsuario()));
        un_usuario.setFechaCreacion(new Date());
        un_usuario.setFechaExpiracionToken(Utilidades.getFechaExpiracion());
        un_usuario.setClave(Utilidades.sha256(un_usuario.getClave()));
        getUsuarioController().setSelected(un_usuario);
        getUsuarioController().update();
        
        GrupoUsuarioController grupoUsuarioController = getGrupoUsuarioController();
        grupoUsuarioController.getSelected().setUsuario(selected.getIdUsuario().getUsuario());
        grupoUsuarioController.getSelected().setUsuario1(selected.getIdUsuario());
        Utilidades.enviarCorreoDeRegistro(un_usuario.getUsuario(), un_usuario.getTokenAcesso());
        grupoUsuarioController.create();
        return null;
    }

    private Persona create() {
        selected = (Persona) persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PersonaCreated"), selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        return selected;
    }

    public Persona createParaRegistrar() {
        Persona perso = create();
        return perso;
    }

    public Persona update() {
        return (Persona) persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonaUpdated"), selected);
    }

    public void updateConUsuarioEstudiante() {
        Persona perso = (Persona) persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonaUpdated"), selected);
        UsuarioController usuarioController = getUsuarioController();
        usuarioController.update();
        if (selected.getEstudianteList() != null && !selected.getEstudianteList().isEmpty()) {
            EstudianteController estudianteController = getEstudianteController();
            estudianteController.update();
            getEstudianteGradoController().create();
        }

    }

    public void destroy() {
        Persona perso = (Persona) persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PersonaDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public String obtenerTipoUsuario(Persona persona) {
        if (persona.getIdUsuario() != null && persona.getIdUsuario().getGrupoUsuarioList() != null) {
            if (!persona.getIdUsuario().getGrupoUsuarioList().isEmpty()) {
                return "" + persona.getIdUsuario().getGrupoUsuarioList().get(0).getTipoUsuario().getTipo();
            }
        }
        return "--";
    }

    public List<Persona> getItems() {
        if (items == null) {
            Persona persona = getLoginController().getPersonaActual();
            List<GrupoUsuario> lista = persona.getIdUsuario().getGrupoUsuarioList();
            for (GrupoUsuario object : lista) {
                String tipo = object.getTipoUsuario().getTipo();
                if (tipo.equals(UsuarioController.TIPO_ADMINISTRADOR)) {
                    items = getFacade().findAll();
                    break;
                } else if (tipo.equals(UsuarioController.TIPO_DOCENTE)) {
                    items = persona.getIdInstitucion().getPersonaList();
                    break;
                }
            }
        }
        return items;
    }

    public Persona getPersona(java.lang.Integer id) {
        return getFacade().find(id);
    }
    
    public Persona getPersona(Usuario id) {
        return getFacade().findPorIdUsuario(id);
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
