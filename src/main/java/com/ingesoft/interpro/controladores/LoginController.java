/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.Vistas;
import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.entidades.EstudianteGrado;
import com.ingesoft.interpro.entidades.GrupoUsuario;
import com.ingesoft.interpro.entidades.Persona;
import com.ingesoft.interpro.entidades.Usuario;
import com.ingesoft.interpro.facades.UsuarioFacade;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.Map;
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
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.util.AccessGrant;
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
    private List<GrupoUsuario> grupos;

    private final String provider = "facebook";

    public LoginController() {
        logueado = false;
    }

    // facebook 
    //https://www.javatips.net/api/socialauth-master/socialauth/src/main/java/org/brickred/socialauth/SocialAuthConfig.java
    public void conectar() {

        try {
            socialManager = getFacebookManager();
            String URLRetorno = socialManager.getAuthenticationUrl(provider, Vistas.loginCompleta());
            System.out.println("URLRetorno: " + socialManager.getSocialAuthConfig());
            System.out.println("URLRetorno: " + URLRetorno);

            FacesContext.getCurrentInstance().getExternalContext().redirect(URLRetorno);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCode(String code) {
        System.out.println("code: " + code);
        if (!"".equals(code)) {
            try {
                //        socialManager = new SocialAuthManager();
                AccessGrant ag = socialManager.createAccessGrant(provider, code, Vistas.loginCompleta());
                System.out.println("getSecret: " + ag.getPermission());
                System.out.println("getSecret: " + ag.getPermission().getScope());
                System.out.println("getSecret: " + ag.getSecret());
                System.out.println("getProviderId: " + ag.getProviderId());
                System.out.println("getAttributes: " + ag.getAttributes());
                System.out.println("getSocialAuthConfig: " + socialManager.getSocialAuthConfig());
                AuthProvider ap = socialManager.connect(ag);
                System.out.println("connect: " + ap.getProviderId());
                System.out.println("getCurrentAuthProvider: " + socialManager.getCurrentAuthProvider().getUserProfile());
                System.out.println("getCountry: " + socialManager.getCurrentAuthProvider().getUserProfile().getCountry());
                System.out.println("getGender: " + socialManager.getCurrentAuthProvider().getUserProfile().getGender());
                System.out.println("getFirstName: " + socialManager.getCurrentAuthProvider().getUserProfile().getFirstName()); //2354393101295069

                // datos de usuario
                usuario = ap.getUserProfile().getEmail();
                password = ap.getUserProfile().getValidatedId();
                if (login()) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(Vistas.inicio());
                }
            } catch (Exception ex) {
                System.out.println("no se pudo iniciar sesion");

                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage("Primero registrese, por favor.");
                context.addMessage(null, msg);
//                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getCode() {
        System.out.println("code: ");
        return "";
    }

    public Persona getPersonaActual() {
        return personaActual;
    }

    public void setPersonaActual(Persona personaActual) {
        this.personaActual = personaActual;
    }
    public boolean usuarioEsDeInstitucion() {
        return personaActual.getIdInstitucion() != null;
    }

    public boolean isAdmin() {
        return getEstudianteController().esAdmin(personaActual);
    }

    public boolean isEstudiante() {
        return getUsuarioController().esEstudiante(actual.getIdUsuario());
    }

    public boolean permisoEstudiante() {
        if (grupos != null) {
            for (GrupoUsuario grupo : grupos) {
                String nombreGrupo = grupo.getTipoUsuario().getTipo();
                if (nombreGrupo.equals(UsuarioController.TIPO_ESTUDIANTE) || nombreGrupo.equals(UsuarioController.TIPO_ADMINISTRADOR) || nombreGrupo.equals(UsuarioController.TIPO_DOCENTE)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String utimoGrado() {
        Estudiante estudiante = getEstudianteController().obtenerEstudiante(personaActual);
        if (estudiante != null) {
            EstudianteGrado estudianteGrado = getEstudianteGradoController().obtenerUltimoEstudianteGrado(estudiante);
            if (estudianteGrado != null) {
                return estudianteGrado.getGrado().getCurso();
            }
        }
        return "";
    }

    public boolean isDocente() {
        return getEstudianteController().esDocente(personaActual) || getEstudianteController().esAdmin(personaActual);
    }

//    public void getPerfilUsuario() throws Exception {
//        ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
//        HttpServletRequest request = (HttpServletRequest) ex.getRequest();
//
//        Map<String, String> parametros = SocialAuthUtil.getRequestParametersMap(request);
//
//        if (socialManager != null) {
//            AuthProvider provider = socialManager.connect(parametros);
//            this.setProfile(provider.getUserProfile());
//        }
//
//        FacesContext.getCurrentInstance().getExternalContext().redirect(mainURL);
//    }
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

    public boolean logueado() {
        ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest req = (HttpServletRequest) external.getRequest();
//        System.out.println("getLocalAddr:"+ Vistas.getIP());

        Map<String, Object> sessionMap = external.getSessionMap();
        String session = (String) sessionMap.get("u5u4ri0");
        return session != null;
    }

    @Override
    public UsuarioFacade getFacade() {
        return ejbFacade;
    }

    public boolean login() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest req = (HttpServletRequest) external.getRequest();
        Map<String, Object> sessionMap = external.getSessionMap();
        String session = (String) sessionMap.get("u5u4ri0");
        System.out.println("este es el resultado: " + session);
        FacesMessage msg;
        String ruta = Vistas.inicio();
        String nomUsuario;
//        if (req.getUserPrincipal() == null) {
        try {
            if (session == null || "".equals(session)) {
                Principal principal;
                req.login(this.usuario, this.password);
                System.out.println("inicio de sesion con usuario: " + usuario + "; clave: " + password);
                logueado = true;
                principal = req.getUserPrincipal();
                nomUsuario = principal.getName();
                sessionMap.put("u5u4ri0", principal.getName());
            } else {
                nomUsuario = session;
            }
            actual = ejbFacade.buscarPorUsuario(nomUsuario);
        } catch (ServletException e) {
//                e.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o contraseña incorrectos.");
            logueado = false;
            context.addMessage(null, msg);
            return false;
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
            System.out.println("estado usuari: " + actual.getEstado());
            grupos = getGrupoUsuarioController().getGruposUsuario(actual);
        }
        context.addMessage(null, msg);
//        } else {
//            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Que alegria, Has vuelto", this.usuario);
//            context.addMessage(null, msg);
//            logueado = true;
//            String nombreUsuario = req.getUserPrincipal().getName();
//            actual = ejbFacade.buscarPorUsuario(nombreUsuario);
//        }
        if (actual != null) {
            if (grupos != null && !grupos.isEmpty()) {
                personaActual = getPersonaController().getPersona(actual);
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
            return true;
        }
        return false;
    }

    public void guardarEnProceso() throws IOException {
        getUsuarioController().getSelected().setEstado(UsuarioController.ACTIVO);
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
        grupos = null;
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
        String rutaGeneral = Vistas.getRutaGeneral();
        FacesContext.getCurrentInstance().getExternalContext().redirect(rutaGeneral + "/login.xhtml");
        return "";
    }

}
