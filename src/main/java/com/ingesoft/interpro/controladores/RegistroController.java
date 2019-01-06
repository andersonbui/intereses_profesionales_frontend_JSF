/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.Utilidades;
import com.ingesoft.interpro.entidades.CodigoInstitucion;
import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.entidades.GrupoUsuario;
import com.ingesoft.interpro.entidades.Persona;
import com.ingesoft.interpro.entidades.PersonaCodigoInstitucion;
import com.ingesoft.interpro.entidades.Usuario;
import com.ingesoft.interpro.facades.UsuarioFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.util.SocialAuthUtil;

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
    String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) throws IOException {

        System.out.println("setToken: " + token);
        if ("dedosdelvalle".equals(token)) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("/intereses_profesionales_frontend_JSF/faces/login.xhtml");
        }
        this.token = token;
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

    public PersonaCodigoInstitucionController getPersonaCodigoInstitucionController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        PersonaCodigoInstitucionController personaCodigoInstitucionController = (PersonaCodigoInstitucionController) elResolver.getValue(facesContext.getELContext(),
                null, "personaCodigoInstitucionController");
        return personaCodigoInstitucionController;
    }

    public CodigoInstitucionController getCodigoInstitucionController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        CodigoInstitucionController codigoInstitucionController = (CodigoInstitucionController) elResolver.getValue(facesContext.getELContext(),
                null, "codigoInstitucionController");
        return codigoInstitucionController;
    }

    public EstudianteController getEstudianteController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        EstudianteController estudianteController = (EstudianteController) elResolver.getValue(facesContext.getELContext(), null, "estudianteController");
        return estudianteController;
    }

    public PersonaController getPersonaController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        PersonaController personaController = (PersonaController) elResolver.getValue(facesContext.getELContext(), null, "personaController");
        return personaController;
    }

    public GrupoUsuarioController getGrupoUsuarioController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        GrupoUsuarioController grupoUsuarioController = (GrupoUsuarioController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "grupoUsuarioController");
        return grupoUsuarioController;
    }

    public void registrarse(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        try {
            CodigoInstitucionController codigoInstitucionController = getCodigoInstitucionController();
            codInstitucion = codigoInstitucionController.buscarPorCodigoActivacion(codigo);

            if (codInstitucion != null) {
                // Crear persona
                PersonaController personaController = getPersonaController();
                Persona unaPersona = personaController.prepareCreate();
                unaPersona.setTipo(codInstitucion.getEstado());
                unaPersona.getIdUsuario().setEstado(UsuarioController.EN_ESPERA);
                unaPersona.getIdUsuario().setClave(Utilidades.sha256(getPassword()));
                unaPersona.getIdUsuario().setUsuario(getUsuario());
                personaController.create();
                unaPersona = personaController.getSelected();

                // crear persona-codigoInstitucion
                PersonaCodigoInstitucionController personaCodigoInstitucionController = getPersonaCodigoInstitucionController();
                PersonaCodigoInstitucion personaCodigoInstitucion = personaCodigoInstitucionController.prepareCreate();
                personaCodigoInstitucion.setPersona(unaPersona);
                personaCodigoInstitucion.setCodigoInstitucion(codInstitucion);
                personaCodigoInstitucion.setFechaIngreso(new Date());
                personaCodigoInstitucionController.create();

                Usuario unusuario = unaPersona.getIdUsuario();
                // Crear grupo usuario
                GrupoUsuarioController grupoUsuarioController = getGrupoUsuarioController();
                GrupoUsuario grupoUsuario = grupoUsuarioController.prepareCreate();
                grupoUsuario.setUsuario1(unusuario);
                grupoUsuario.setUsuario(unusuario.getUsuario());
                grupoUsuario.getGrupoUsuarioPK().setIdGrupoUsuario(codInstitucion.getEstado());
                grupoUsuarioController.create();

                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Felicidades", "");
                context.getExternalContext().redirect("/intereses_profesionales_frontend_JSF/faces/envioEmailRegistro.xhtml");

                // enviar email 
                //TODO
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "C&oacute;digo de registro incorrecto, por favor verifique su c&oacute;digo.", "");
            }
        } catch (IOException ex) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrio un Error, Intentalo mas tarde", "");
        }
        context.addMessage(null, msg);

    }

    public boolean isVerificado() {
        return verificado;
    }

}
