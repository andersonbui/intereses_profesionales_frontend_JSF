/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.Utilidades;
import com.ingesoft.interpro.entidades.Persona;
import com.ingesoft.interpro.entidades.Usuario;
import com.ingesoft.interpro.facades.UsuarioFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthManager;

/**
 *
 * @author debian
 */
@ManagedBean(name = "loginFacebookController")
@SessionScoped
public class LoginFacebookController extends Controller implements Serializable {

    private static final long serialVersionUID = 3658300628580536494L;

    @EJB
    UsuarioFacade ejbFacade;
    private Usuario selected;
    
//    private Usuario actual;
    private SocialAuthManager socialManager;
    String token;

    public LoginFacebookController() {
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) throws IOException {
        Usuario unusuario = getUsuarioController().obtUsuarioPorToken(token);
        FacesContext context = FacesContext.getCurrentInstance();
        Calendar fechaExpiracion = Calendar.getInstance();
        if (unusuario != null) {
            Calendar fechaActual = Calendar.getInstance();
            Date fechaExp = unusuario.getFechaExpiracionToken();
            fechaExpiracion.setTime(fechaExp);
            boolean antes = fechaActual.before(fechaExpiracion);
            if (UsuarioController.EN_ESPERA.equals(unusuario.getEstado()) && antes) {
                Persona persona = getPersonaController().getPersona(unusuario);
                
                if (getUsuarioController().esEstudiante(unusuario.getIdUsuario())) {
                    EstudianteController estudianteController = getEstudianteController();
                    estudianteController.prepareCreate();
                    estudianteController.getSelected().setIdPersona(persona);
                    estudianteController.create();
                }
                unusuario.setEstado(UsuarioController.EN_PROCESO);
                selected = unusuario;
                this.create();
                context.getExternalContext().redirect("/intereses_profesionales_frontend_JSF/faces/continuarRegistro.xhtml");
                return;
            }

        }
        context.getExternalContext().redirect("/intereses_profesionales_frontend_JSF/faces/registroTokenRechazado.xhtml");

        System.out.println("setToken: " + token);
        this.token = token;
    }

    public Usuario getUsuario(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public UsuarioFacade getFacade() {
        return ejbFacade;
    }

    public void create() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioCreated"), selected);
    }

    public void enviarMensaje() {
        Utilidades.enviarCorreo("andersonbuitron@unicauca.edu.co", " asunto1", "este es el cuerpo del mensaje");
        FacesContext context = FacesContext.getCurrentInstance();
//        System.out.println("ruta: " + FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath());
//        System.out.println("ruta: " + FacesContext.getCurrentInstance().getExternalContext().getContextName());
//        System.out.println("ruta: " + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
//        System.out.println("ruta: " + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName());
//        System.out.println("ruta: " + FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo());
//        System.out.println("ruta: " + FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath());
//        System.out.println("ruta: " + FacesContext.getCurrentInstance().getExternalContext().getRequestScheme());
//        System.out.println("ruta: " + FacesContext.getCurrentInstance().getExternalContext().getResponseContentType());
//        System.out.println("ruta: " + FacesContext.getCurrentInstance().getExternalContext().get);
    }


}
