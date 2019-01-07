/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.entidades.Persona;
import com.ingesoft.interpro.facades.AbstractFacade;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.context.FacesContext;

/**
 *
 * @author debian
 */
public abstract class Controller implements Serializable {

    protected abstract AbstractFacade getFacade();

    protected abstract void setEmbeddableKeys();

    protected Object persist(JsfUtil.PersistAction persistAction, String successMessage, Object selected) {
        Object persona = null;
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    persona = getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
//                    persona = selected;
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
        return persona;
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

    public PaisController getPaisController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        PaisController paisController = (PaisController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "paisController");
        return paisController;
    }

    public GrupoUsuarioController getGrupoUsuarioController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        GrupoUsuarioController grupoUsuarioController = (GrupoUsuarioController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "grupoUsuarioController");
        return grupoUsuarioController;
    }
    
    public TipoUsuarioController getTipoUsuarioController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        TipoUsuarioController tipoUsuarioController = (TipoUsuarioController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "tipoUsuarioController");
        return tipoUsuarioController;
    }
}
