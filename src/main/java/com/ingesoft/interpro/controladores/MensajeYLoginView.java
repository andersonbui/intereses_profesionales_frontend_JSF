/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
//import javax.faces.view.ViewScoped;

/**
 *
 * @author debian
 */
@SessionScoped
@ManagedBean(name = "mensajeYLoginView")
public class MensajeYLoginView implements Serializable{

    private String mensaje;
    /**
     * Creates a new instance of MensajeYLoginView
     */
    public MensajeYLoginView() {
    }

    public String getMensaje() {
        System.out.println("getMensaje: "+mensaje);
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        System.out.println("setMensaje: "+mensaje);
        this.mensaje = mensaje;
    }
    
}
