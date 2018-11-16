/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.GrupoUsuario;
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
public class LoginController implements Serializable {

    private static final long serialVersionUID = 3658300628580536494L;

    @EJB
    UsuarioFacade ejbFacade;
    
    private Usuario actual;
    private SocialAuthManager socialManager;
    private Profile profile;
    String usuario;
    String password;
    boolean logueado;

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

    public UsuarioFacade getFacade() {
        return ejbFacade;
    }

    public void login() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        FacesMessage msg;
        String ruta = "/intereses_profesionales_frontend_JSF/faces/vistas/pregunta/List.xhtml";
        if (req.getUserPrincipal() == null) {
            try {
                req.login(this.usuario, this.password);
                System.out.println("inicio de sesion con usuario: "+usuario+"; clave: "+password);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", this.usuario);
                logueado = true;
            } catch (ServletException e) {
                e.printStackTrace();
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o contraseña incorrectos.");
                logueado = false;
                context.addMessage(null, msg);
                return;
            }
            Principal principal = req.getUserPrincipal();
            actual = ejbFacade.buscarPorUsuario(principal.getName());
            ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = external.getSessionMap();
            sessionMap.put("usuario", actual);
            context.addMessage(null, msg);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", this.usuario);
            context.addMessage(null, msg);
            logueado = true;
            String nombreUsuario = req.getUserPrincipal().getName();
            actual = ejbFacade.buscarPorUsuario(nombreUsuario);
        }
        GrupoUsuario gtu = actual.getGrupoUsuarioList().get(0);
        if (gtu != null) {
            int grupo = gtu.getGrupoUsuarioPK().getIdGrupoUsuario();

            RequestContext.getCurrentInstance().addCallbackParam("estaLogeado", logueado);
            RequestContext.getCurrentInstance().addCallbackParam("view", ruta);
        }
    }

    public String salir() throws IOException {

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        logueado = false;
        try {
            req.logout();
            req.getSession().invalidate();
            fc.getExternalContext().invalidateSession();

        } catch (ServletException e) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILED", "Cerrar Sesion"));
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("/intereses_profesionales_frontend_JSF/faces/login.xhtml");
        return "";
    }

    public boolean logueado() {
        return logueado;
    }
}
