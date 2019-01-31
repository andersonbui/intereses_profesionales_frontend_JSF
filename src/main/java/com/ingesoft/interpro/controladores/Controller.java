/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.entidades.PreguntaAmbiente;
import com.ingesoft.interpro.facades.AbstractFacade;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;

/**
 *
 * @author debian
 */
public abstract class Controller implements Serializable {

    protected abstract AbstractFacade getFacade();

    protected void setEmbeddableKeys() {

    }

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

    public EstudianteGradoController getEstudianteGradoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        EstudianteGradoController estudianteGradoController = (EstudianteGradoController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "estudianteGradoController");
        return estudianteGradoController;
    }

    public GradoController getGradoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        GradoController gradoController = (GradoController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "gradoController");
        return gradoController;
    }

    public DepartamentoController getDepartamentoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        DepartamentoController controllerPersona = (DepartamentoController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "departamentoController");
        return controllerPersona;
    }

    public CiudadController getCiudadController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        CiudadController ciudadController = (CiudadController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "ciudadController");
        return ciudadController;
    }

    public LoginController getLoginController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        LoginController loginController = (LoginController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "loginController");
        return loginController;
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

    public CodigoInstitucionController getCodigoInstitucionController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        CodigoInstitucionController codigoInstitucionController = (CodigoInstitucionController) elResolver.getValue(facesContext.getELContext(),
                null, "codigoInstitucionController");
        return codigoInstitucionController;
    }

    public PersonaController getPersonaController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        PersonaController personaController = (PersonaController) elResolver.getValue(facesContext.getELContext(), null, "personaController");
        return personaController;
    }

    public TipoAmbienteController getTipoAmbienteController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        TipoAmbienteController tipoAmbienteController = (TipoAmbienteController) elResolver.getValue(facesContext.getELContext(), null, "tipoAmbienteController");
        return tipoAmbienteController;
    }

    public PreguntaAmbienteController getPreguntaAmbienteController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        PreguntaAmbienteController preguntaAmbienteController = (PreguntaAmbienteController) elResolver.getValue(facesContext.getELContext(), null, "preguntaAmbienteController");
        return preguntaAmbienteController;
    }

    public RespuestaAmbienteEvaluacionController getRespuestaAmbienteEvaluacionController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        RespuestaAmbienteEvaluacionController respuestaAmbienteEvaluacionController = (RespuestaAmbienteEvaluacionController) elResolver.getValue(facesContext.getELContext(), null, "respuestaAmbienteEvaluacionController");
        return respuestaAmbienteEvaluacionController;
    }

    public RespuestaAmbienteController getRespuestaAmbienteController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        RespuestaAmbienteController respuestaAmbienteController = (RespuestaAmbienteController) elResolver.getValue(facesContext.getELContext(), null, "respuestaAmbienteController");
        return respuestaAmbienteController;
    }

    public EvaluacionController getEvaluacionController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        EvaluacionController evaluacionController = (EvaluacionController) elResolver.getValue(facesContext.getELContext(), null, "evaluacionController");
        return evaluacionController;
    }

    public AreaEncuestaController getAreaEncuestaController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        AreaEncuestaController areaEncuestaController = (AreaEncuestaController) elResolver.getValue(facesContext.getELContext(), null, "areaEncuestaController");
        return areaEncuestaController;
    }

    public AreaController getAreaController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        AreaController areaController = (AreaController) elResolver.getValue(facesContext.getELContext(), null, "areaController");
        return areaController;
    }

    public AreaProfesionalController getAreaProfesionalController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        AreaProfesionalController areaProfesionalController = (AreaProfesionalController) elResolver.getValue(facesContext.getELContext(), null, "areaProfesionalController");
        return areaProfesionalController;
    }

    public TipoEleccionMateriaController getTipoEleccionMateriaController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        TipoEleccionMateriaController tipoEleccionMateriaController = (TipoEleccionMateriaController) elResolver.getValue(facesContext.getELContext(), null, "tipoEleccionMateriaController");
        return tipoEleccionMateriaController;
    }

    public ResultadoPorAmbienteController getResultadoPorAmbienteController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        ResultadoPorAmbienteController resultadoPorAmbienteController = (ResultadoPorAmbienteController) elResolver.getValue(facesContext.getELContext(), null, "resultadoPorAmbienteController");
        return resultadoPorAmbienteController;
    }
}
