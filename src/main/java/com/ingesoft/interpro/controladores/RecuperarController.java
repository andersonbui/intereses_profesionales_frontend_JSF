/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.Utilidades;
import com.ingesoft.interpro.controladores.util.Vistas;
import com.ingesoft.interpro.entidades.Usuario;
import com.ingesoft.interpro.facades.UsuarioFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author debian
 */
@ManagedBean(name = "recuperarController")
@SessionScoped
public class RecuperarController extends Controllers implements Serializable {

    private static final long serialVersionUID = 3658300628580536494L;

    @EJB
    UsuarioFacade ejbFacade;
    private Usuario usuario;

    String email;
    String password;
    String token;

    public RecuperarController() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) throws IOException {
        usuario = getUsuarioController().obtUsuarioPorTokenRecuperacion(token);
        continuarRecuperacionContrasena();
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void continuarRecuperacionContrasena() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        Calendar fechaExpiracion = Calendar.getInstance();
        if (usuario != null) {
            Calendar fechaActual = Calendar.getInstance();
            Date fechaExp = usuario.getFechaExpTokenRecuperacion();
            fechaExpiracion.setTime(fechaExp);
            boolean antes = fechaActual.before(fechaExpiracion);
            if (antes) {
                return;
            }
        }
        
        getMensajeYLoginView().setMensaje("El token de recuperación de contraseña a acaducado.\n Inicie el proceso de recuperación de contraseña nuevamente");
        context.getExternalContext().redirect(Vistas.urlMensaje());

    }

    public void finalizarRecuperacionContrasena() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        Calendar fechaExpiracion = Calendar.getInstance();
        if (usuario != null) {
            Calendar fechaActual = Calendar.getInstance();
            Date fechaExp = usuario.getFechaExpTokenRecuperacion();
            fechaExpiracion.setTime(fechaExp);
            boolean antes = fechaActual.before(fechaExpiracion);
            if (antes) {
                usuario.setFechaExpTokenRecuperacion(fechaActual.getTime());

                usuario.setClave(Utilidades.sha256(getPassword()));
                UsuarioController usuarioController = getUsuarioController();
                usuarioController.setSelected(usuario);
                usuarioController.create();

                getMensajeYLoginView().setMensaje("La contraseña se actualizó exitosamente. Puede iniciar sesión.");
                context.getExternalContext().redirect(Vistas.urlMensaje());
                return;
            }
        }

        getMensajeYLoginView().setMensaje("El token de recuperación de contraseña a acaducado.\n Inicie el proceso de recuperación de contraseña nuevamente");
        context.getExternalContext().redirect(Vistas.urlMensaje());
    }

    public UsuarioFacade getFacade() {
        return ejbFacade;
    }

    public void create() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioCreated"), usuario);
    }

    public void recuperar(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        try {

            UsuarioController usuarioController = getUsuarioController();
            Usuario un_usuario = usuarioController.obtUsuarioPorEmail(email);

            if (un_usuario != null) {
                // recuperar email
                un_usuario.setTokenRecuperacion(Utilidades.generarToken("" + un_usuario.getIdUsuario()));
                un_usuario.setFechaExpTokenRecuperacion(Utilidades.getFechaExpiracion());
                usuarioController.setSelected(un_usuario);
                usuarioController.update();
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Felicidades", "");
                // enviar email
                Utilidades.enviarCorreoDeRecuperar(getEmail(), un_usuario.getTokenRecuperacion());

                getMensajeYLoginView().setMensaje("Se le envió un correo con la información necesaria para recuperar su contraseña. Por favor revise su bandeja.");
                context.getExternalContext().redirect(Vistas.urlMensaje());
//                Utilidades.enviarCorreo("andersonbuitron@unicauca.edu.co", " asunto1", "este es el cuerpo del mensaje");
                //TODO
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Email no existe, por favor verifique.", "");
            }
        } catch (IOException ex) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrio un Error, Intentalo mas tarde", "");
        }
        context.addMessage(null, msg);

    }
}
