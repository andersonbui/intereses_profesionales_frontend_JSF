/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.Utilidades;
import com.ingesoft.interpro.controladores.util.Vistas;
import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.entidades.GrupoUsuario;
import com.ingesoft.interpro.entidades.Persona;
import com.ingesoft.interpro.entidades.Usuario;
import com.ingesoft.interpro.facades.UsuarioFacade;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.Map;
import java.util.Properties;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.util.SocialAuthUtil;
import org.primefaces.context.RequestContext;

/**
 *
 * @author debian
 */
@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController extends Controller implements Serializable {

    private static final long serialVersionUID = 3658300628580536494L;

    @EJB
    UsuarioFacade ejbFacade;

    private Usuario actual;
    private Persona personaActual;
    private SocialAuthManager socialManager;
    private Profile profile;
    String usuario;
    String password;
    boolean logueado;
    private GrupoUsuario grupo;

    private final String mainURL = "http://localhost:8080/login_facebook/faces/index.xhtml";
    private final String redirectURL = "http://localhost:8080/login_facebook/faces/redirectHome.xhtml";
    //private final String redirectURL = "http://www.codewebpro.com/blog";
    private final String provider = "facebook";

    public LoginController() {
        logueado = false;
    }

    public void conectar() {
        Properties prop = System.getProperties();
        prop.put("graph.facebook.com.consumer_key", "329124954489538");
        prop.put("graph.facebook.com.consumer_secret", "4c5e659fd3792cd6acd10e07e67a1855");
        prop.put("graph.facebook.com.custom_permissions", "public_profile,email");

        SocialAuthConfig socialConfig = SocialAuthConfig.getDefault();
        try {
            socialConfig.load(prop);
            socialManager = new SocialAuthManager();
            socialManager.setSocialAuthConfig(socialConfig);
            String URLRetorno = socialManager.getAuthenticationUrl(provider, redirectURL);
            FacesContext.getCurrentInstance().getExternalContext().redirect(URLRetorno);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Persona getPersonaActual() {
        return personaActual;
    }

    public void setPersonaActual(Persona personaActual) {
        this.personaActual = personaActual;
    }

    public boolean isAdmin() {
        return getEstudianteController().esAdmin(personaActual);
    }

    public boolean isEstudiante() {
        return getEstudianteController().isEstudiante(personaActual);
    }

    public boolean permisoEstudiante() {
        String nombreGrupo = grupo.getTipoUsuario().getTipo();
        return nombreGrupo.equals(UsuarioController.TIPO_ESTUDIANTE) || nombreGrupo.equals(UsuarioController.TIPO_ADMINISTRADOR) || nombreGrupo.equals(UsuarioController.TIPO_DOCENTE);
    }

    public String utimoGrado() {
        Estudiante estudiante = getEstudianteController().obtenerEstudiante(personaActual);
        if (estudiante != null) {
            return getEstudianteGradoController().obtenerUltimoEstudianteGrado(estudiante.getIdEstudiante()).getGrado().getCurso();
        }
        return "";
    }

    public boolean isDocente() {
        return getEstudianteController().esDocente(personaActual) || getEstudianteController().esAdmin(personaActual);
    }

    public void getPerfilUsuario() throws Exception {
        ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ex.getRequest();

        Map<String, String> parametros = SocialAuthUtil.getRequestParametersMap(request);

        if (socialManager != null) {
            AuthProvider provider = socialManager.connect(parametros);
            this.setProfile(provider.getUserProfile());
        }

        FacesContext.getCurrentInstance().getExternalContext().redirect(mainURL);
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario getActual() {
        return actual;
    }

    public void setActual(Usuario actual) {
        this.actual = actual;
    }

    public Usuario getUsuario(java.lang.Integer id) {
        return getFacade().find(id);
    }

    @Override
    public UsuarioFacade getFacade() {
        return ejbFacade;
    }

    public void login() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        FacesMessage msg;
        String ruta = Vistas.inicio();
        if (req.getUserPrincipal() == null) {
            try {
                req.login(this.usuario, this.password);
                System.out.println("inicio de sesion con usuario: " + usuario + "; clave: " + password);
                logueado = true;
                Principal principal = req.getUserPrincipal();
                actual = ejbFacade.buscarPorUsuario(principal.getName());
            } catch (ServletException e) {
//                e.printStackTrace();
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o contraseña incorrectos.");
                logueado = false;
                context.addMessage(null, msg);
                return;
            }
            System.out.println("estado usuari: " + actual);
//                System.out.println("estado usuari: " + actual.getGrupoUsuarioList());
            if (UsuarioController.EN_ESPERA.equals(actual.getEstado())) {
                System.out.println("estado usuari: " + actual.getEstado());
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Tu cuenta no ha sido activado. Por favor revise su bandeja de correo");
                eliminarSesion();
            } else if (UsuarioController.INAACTIVO.equals(actual.getEstado())) {
                System.out.println("estado usuari: " + actual.getEstado());
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Tu cuenta fue desactivado. Por favor comuniquese con el administrador.");
                eliminarSesion();
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", this.usuario);
                ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
                Map<String, Object> sessionMap = external.getSessionMap();
                sessionMap.put("usuario", actual);
                System.out.println("estado usuari: " + actual.getEstado());
                grupo = actual.getGrupoUsuarioList().get(0);
            }
            context.addMessage(null, msg);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Que alegria, Has vuelto", this.usuario);
            context.addMessage(null, msg);
            logueado = true;
            String nombreUsuario = req.getUserPrincipal().getName();
            actual = ejbFacade.buscarPorUsuario(nombreUsuario);
        }
        if (actual != null) {
            GrupoUsuario gtu = actual.getGrupoUsuarioList().get(0);
            if (gtu != null) {
                personaActual = actual.getPersonaList().get(0);
                if (UsuarioController.EN_PROCESO.equals(actual.getEstado())) {
                    PersonaController personaController = getPersonaController();
                    personaController.prepareUpdate(personaActual);
                    ruta = Vistas.completarPerfil();
                    FacesContext.getCurrentInstance().getExternalContext().redirect(ruta);
                } else {
                    //            int grupo = gtu.getGrupoUsuarioPK().getIdGrupoUsuario();
//                FacesContext.getCurrentInstance().getExternalContext().redirect(ruta);
                    RequestContext.getCurrentInstance().addCallbackParam("estaLogeado", logueado);
                    RequestContext.getCurrentInstance().addCallbackParam("view", ruta);
                }
            }
        }
    }

    public void guardarEnProceso() throws IOException {
        // @desarrollo
        if (!Utilidades.esDesarrollo()) {
            getUsuarioController().getSelected().setEstado(UsuarioController.ACTIVO);
        }
        PersonaController personaController = getPersonaController();
        personaController.updateConUsuarioEstudiante();

        String ruta = Vistas.inicio();
//        RequestContext.getCurrentInstance().addCallbackParam("estaLogeado", logueado);
//        RequestContext.getCurrentInstance().addCallbackParam("view", ruta);
        FacesContext.getCurrentInstance().getExternalContext().redirect(ruta);
    }

    public void eliminarSesion() {
        actual = null;
        personaActual = null;
        grupo = null;
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        logueado = false;
        try {
            req.logout();
            req.getSession().invalidate();
            fc.getExternalContext().invalidateSession();

        } catch (ServletException e) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILED", "Error al Cerrar Sesion"));
        }
    }

    public String salir() throws IOException {
        eliminarSesion();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/intereses_profesionales_frontend_JSF/faces/login.xhtml");
        return "";
    }

    public boolean logueado() {
        return logueado;
    }
}
