/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores;

import com.ingesoft.suideal.encuesta.ambiente.controladores.EstadisticaAmbienteController;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.controladores.RespuestaEstilosController;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.controladores.PreguntaEstilosAprendizajeController;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.controladores.EstiloController;
import com.ingesoft.interpro.controladores.util.CredencialesGF;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.facades.AbstractFacade;
import com.ingesoft.suideal.encuesta.chaside.controladores.ChasideController;
import com.ingesoft.suideal.encuesta.chaside.controladores.PreguntaChasideController;
import com.ingesoft.suideal.encuesta.chaside.controladores.RespuestaChasideController;
import com.ingesoft.suideal.encuesta.chaside.controladores.ResultadoChasideController;
import com.ingesoft.suideal.encuesta.chaside.controladores.TipoClaseChasideController;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.controladores.EstadisticaEstiloController;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.controladores.RespuestaPorEstilosController;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.controladores.EncuestaInteligenciasMultiplesController;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.controladores.EstadisticaInteligenciasMultiplesController;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.controladores.PreguntaInteligenciasMultiplesController;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.controladores.RespuestaInteligenciasMultiplesController;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.controladores.RespuestaPorInteligenciaController;
import com.ingesoft.suideal.encuesta.personalidad.controladores.EstadisticaPersonalidadController;
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
public abstract class Controllers implements Serializable {

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

    protected Object persist(JsfUtil.PersistAction persistAction, String successMessage, Object selected)  {
        return persist(persistAction, successMessage, selected, true );
    }
    
    protected Object persist(JsfUtil.PersistAction persistAction, String successMessage, Object selected, boolean mostrarmsg ) {
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
                if(mostrarmsg){
                    JsfUtil.addSuccessMessage(successMessage);
                }
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    if(mostrarmsg){
                        JsfUtil.addErrorMessage(msg);
                    }
                } else {
                    if(mostrarmsg){
                        JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                    }
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
    public MensajeYLoginView getMensajeYLoginView() {
        FacesContext context = FacesContext.getCurrentInstance();
        MensajeYLoginView mnsajeYLoginView = (MensajeYLoginView) context.getApplication().getELResolver().
                        getValue(context.getELContext(), null, "mensajeYLoginView");
        return mnsajeYLoginView;
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
    
    public EstadisticaPersonalidadController getEstadisticaPersonalidadController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        EstadisticaPersonalidadController estadisticaPersonalidadController = (EstadisticaPersonalidadController) elResolver.getValue(facesContext.getELContext(), null, "estadisticaPersonalidadController");
        return estadisticaPersonalidadController;
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
    public PreguntaInteligenciasMultiplesController getPreguntaInteligenciasMultiplesController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        PreguntaInteligenciasMultiplesController preguntaInteligenciasMultiplesController = (PreguntaInteligenciasMultiplesController) elResolver.getValue(facesContext.getELContext(), null, "preguntaInteligenciasMultiplesController");
        return preguntaInteligenciasMultiplesController;
    }
    
    public RespuestaInteligenciasMultiplesController getRespuestaInteligenciasMultiplesController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        RespuestaInteligenciasMultiplesController respuestaInteligenciasMultiplesController = (RespuestaInteligenciasMultiplesController) elResolver.getValue(facesContext.getELContext(), null, "respuestaInteligenciasMultiplesController");
        return respuestaInteligenciasMultiplesController;
    }
    
    public EncuestaInteligenciasMultiplesController getEncuestaInteligenciasMultiplesController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        EncuestaInteligenciasMultiplesController encuestaInteligenciasMultiplesController = (EncuestaInteligenciasMultiplesController) elResolver.getValue(facesContext.getELContext(), null, "encuestaInteligenciasMultiplesController");
        return encuestaInteligenciasMultiplesController;
    }
    
    public RespuestaPorInteligenciaController getRespuestaPorInteligenciaController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        RespuestaPorInteligenciaController respuestaPorInteligenciaController = (RespuestaPorInteligenciaController) elResolver.getValue(facesContext.getELContext(), null, "respuestaPorInteligenciaController");
        return respuestaPorInteligenciaController;
    }
    
    public EstadisticaInteligenciasMultiplesController getEstadisticaInteligenciasMultiplesController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        EstadisticaInteligenciasMultiplesController estadisticaInteligenciasMultiplesController = (EstadisticaInteligenciasMultiplesController) elResolver.getValue(facesContext.getELContext(), null, "estadisticaInteligenciasMultiplesController");
        return estadisticaInteligenciasMultiplesController;
    }
    
    public EncuestaPersonalidadController getEncuestaPersonalidadController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        EncuestaPersonalidadController encuestaPersonalidadController = (EncuestaPersonalidadController) elResolver.getValue(facesContext.getELContext(), null, "encuestaPersonalidadController");
        return encuestaPersonalidadController;
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
    
    public EstadisticaController getEstadisticaController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        EstadisticaController estadisticaController = (EstadisticaController) elResolver.getValue(facesContext.getELContext(), null, "estadisticaController");
        return estadisticaController;
    }

    public EstiloController getEstiloController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        EstiloController estiloController = (EstiloController) elResolver.getValue(facesContext.getELContext(), null, "estiloController");
        return estiloController;
    }
    
    public RespuestaPorEstilosController getRespuestaPorEstilosController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        RespuestaPorEstilosController respuestaPorEstilosController = (RespuestaPorEstilosController) elResolver.getValue(facesContext.getELContext(), null, "respuestaPorEstilosController");
        return respuestaPorEstilosController;
    }

    
    public EstadisticaEstiloController getEstadisticaEstiloController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        EstadisticaEstiloController estadisticaEstiloController = (EstadisticaEstiloController) elResolver.getValue(facesContext.getELContext(), null, "estadisticaEstiloController");
        return estadisticaEstiloController;
    }

    public PreguntaEstilosAprendizajeController getPreguntaEstilosAprendizajeController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        PreguntaEstilosAprendizajeController preguntaEstilosAprendizajeController = (PreguntaEstilosAprendizajeController) elResolver.getValue(facesContext.getELContext(), null, "preguntaEstilosAprendizajeController");
        return preguntaEstilosAprendizajeController;
    }
    
    public RespuestaEstilosController getRespuestaEstilosControllerController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        RespuestaEstilosController respuestaEstilosController = (RespuestaEstilosController) elResolver.getValue(facesContext.getELContext(), null, "respuestaEstilosController");
        return respuestaEstilosController;
    }
    
    public PreguntaPersonalidadController getPreguntaPersonalidadController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        PreguntaPersonalidadController preguntaPersonalidadController = (PreguntaPersonalidadController) elResolver.getValue(facesContext.getELContext(), null, "preguntaPersonalidadController");
        return preguntaPersonalidadController;
    }
    
    
    public ChasideController getChasideController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        ChasideController chasideController = (ChasideController) elResolver.getValue(facesContext.getELContext(), null, "chasideController");
        return chasideController;
    }
    
    public ResultadoChasideController getResultadoChasideController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        ResultadoChasideController ResultadoChasideController = (ResultadoChasideController) elResolver.getValue(facesContext.getELContext(), null, "resultadoChasideController");
        return ResultadoChasideController;
    }

//    public EstadisticaChasideController getEstadisticaChasideController() {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        ELResolver elResolver = facesContext.getApplication().getELResolver();
//        EstadisticaChasideController estadisticaChasideController = (EstadisticaChasideController) elResolver.getValue(facesContext.getELContext(), null, "estadisticaChasideController");
//        return estadisticaChasideController;
//    }
    
    public TipoClaseChasideController getTipoClaseChasideController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        TipoClaseChasideController tipoClaseChasideController = (TipoClaseChasideController) elResolver.getValue(facesContext.getELContext(), null, "tipoClaseChasideController");
        return tipoClaseChasideController;
    }

    public PreguntaChasideController getPreguntaChasideController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        PreguntaChasideController preguntaChasideController = (PreguntaChasideController) elResolver.getValue(facesContext.getELContext(), null, "preguntaChasideController");
        return preguntaChasideController;
    }
    
    public RespuestaChasideController getRespuestaChasideController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elResolver = facesContext.getApplication().getELResolver();
        RespuestaChasideController respuestaChasideController = (RespuestaChasideController) elResolver.getValue(facesContext.getELContext(), null, "respuestaChasideController");
        return respuestaChasideController;
    }
    
}
