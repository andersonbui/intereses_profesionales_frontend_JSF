/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.CodigoInstitucion;
import com.ingesoft.interpro.entidades.GrupoUsuario;
import com.ingesoft.interpro.entidades.PersonaCodigoInstitucionPK;
import com.ingesoft.interpro.entidades.Usuario;
import com.ingesoft.interpro.facades.UsuarioFacade;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import javax.ejb.EJB;
import javax.el.ELResolver;
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
@ManagedBean(name = "registroController")
@SessionScoped
public class RegistroController implements Serializable {

    private static final long serialVersionUID = 3658300628580536494L;

    @EJB
    UsuarioFacade ejbFacade;

    private Usuario actual;
    private SocialAuthManager socialManager;
    private Profile profile;
    String codigo;
    String usuario;
    String password;
    boolean verificado;
    CodigoInstitucion codInstitucion;

    private final String mainURL = "http://localhost:8080/login_facebook/faces/index.xhtml";
    private final String redirectURL = "http://localhost:8080/login_facebook/faces/redirectHome.xhtml";
    //private final String redirectURL = "http://www.codewebpro.com/blog";
    private final String provider = "facebook";

    public RegistroController() {
        verificado = false;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public CodigoInstitucionController getCodigoInstitucionController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        CodigoInstitucionController codigoInstitucionController = (CodigoInstitucionController) elResolver.getValue(facesContext.getELContext(), null, "codigoInstitucionController");

        return codigoInstitucionController;

    }

    public void verificarCodigo() {
        CodigoInstitucionController codigoInstitucionController = getCodigoInstitucionController();
        codInstitucion = codigoInstitucionController.getCodigoInstitucion(codigo);
        if (codInstitucion != null) {
            verificado = true;
        }
    }

    public void registrarse() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        PersonaController controllerPersona = (PersonaController) elResolver.getValue(facesContext.getELContext(), null, "personaController");
        PersonaCodigoInstitucionController personaCodigoInstitucionController = (PersonaCodigoInstitucionController) elResolver.getValue(facesContext.getELContext(), null, "personaCodigoInstitucionController");
        if (codInstitucion != null) {
            personaCodigoInstitucionController.create();
            personaCodigoInstitucionController.getSelected().setCodigoInstitucion(codInstitucion);
            personaCodigoInstitucionController.getSelected().setFechaIngreso(new Date());
            personaCodigoInstitucionController.getSelected().setPersona(controllerPersona.getSelected());
            personaCodigoInstitucionController.getSelected().setPersonaCodigoInstitucionPK(new PersonaCodigoInstitucionPK());
        }

    }

    public boolean isVerificado() {
        return verificado;
    }

}
