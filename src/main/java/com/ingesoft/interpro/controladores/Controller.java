/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.CredencialesGF;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.entidades.PreguntaAmbiente;
import com.ingesoft.interpro.facades.AbstractFacade;
import java.io.Serializable;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;

/**
 *
 * @author debian
 */
public abstract class Controller implements Serializable {

    protected abstract AbstractFacade getFacade();

    protected void setEmbeddableKeys() {

    }

    protected SocialAuthManager getFacebookManager() {
        Properties prop = System.getProperties();
        prop.put("graph.facebook.com.consumer_key", CredencialesGF.keyFacebook);
        prop.put("graph.facebook.com.consumer_secret", CredencialesGF.secretFacebook);
//        prop.put("graph.facebook.com.consumer_key", "329124954489538");
//        prop.put("graph.facebook.com.consumer_secret", "4c5e659fd3792cd6acd10e07e67a1855");
        prop.put("graph.facebook.com.custom_permissions", "public_profile,email");

        SocialAuthConfig socialConfig = SocialAuthConfig.getDefault();

        try {
            socialConfig.load(prop);
            SocialAuthManager socialManager;
            socialManager = new SocialAuthManager();
            socialManager.setSocialAuthConfig(socialConfig);
            return socialManager;
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    protected SocialAuthManager getGoogleManager() {
        Properties prop = System.getProperties();
        prop.put("graph.google.com.consumer_key", CredencialesGF.keyGoogle);
        prop.put("graph.google.com.consumer_secret", CredencialesGF.secretGoogle);
//        prop.put("graph.facebook.com.consumer_key", "329124954489538");
//        prop.put("graph.facebook.com.consumer_secret", "4c5e659fd3792cd6acd10e07e67a1855");
        prop.put("graph.google.com.custom_permissions", "public_profile,email");

        SocialAuthConfig socialConfig = SocialAuthConfig.getDefault();

        try {
            socialConfig.load(prop);
            SocialAuthManager socialManager;
            socialManager = new SocialAuthManager();
            socialManager.setSocialAuthConfig(socialConfig);
            return socialManager;
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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

    public MineriaController getMineriaController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        MineriaController mineriaController = (MineriaController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "mineriaController");
        return mineriaController;
    }

    public DatosRiasecController getDatosRiasecController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        DatosRiasecController datosRiasecController = (DatosRiasecController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "datosRiasecController");
        return datosRiasecController;
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

    public EstadisticaAmbienteController getEstadisticaAmbienteController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        EstadisticaAmbienteController estadisticaAmbienteController = (EstadisticaAmbienteController) elResolver.getValue(facesContext.getELContext(), null, "estadisticaAmbienteController");
        return estadisticaAmbienteController;
    }

    public RespuestaPersonalidadController getRespuestaPersonalidadController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        RespuestaPersonalidadController respuestaPersonalidadController = (RespuestaPersonalidadController) elResolver.getValue(facesContext.getELContext(), null, "respuestaPersonalidadController");
        return respuestaPersonalidadController;
    }

    public RespuestaPorPersonalidadController getRespuestaPorPersonalidadController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        RespuestaPorPersonalidadController respuestaPorPersonalidadController = (RespuestaPorPersonalidadController) elResolver.getValue(facesContext.getELContext(), null, "respuestaPorPersonalidadController");
        return respuestaPorPersonalidadController;
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

    public EncuestaController getEncuestaController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        EncuestaController encuestaController = (EncuestaController) elResolver.getValue(facesContext.getELContext(), null, "encuestaController");
        return encuestaController;
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
