package com.ingesoft.suideal.encuesta.inteligencias_multiples.controladores;

import com.ingesoft.interpro.controladores.util.Contador;
import com.ingesoft.interpro.controladores.util.EncuestaControllerAbstract;
import com.ingesoft.interpro.controladores.util.RespuestaControllerAbstract;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.EncuestaEstilosAprendizaje;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.EncuestaInteligenciasMultiples;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.PreguntaInteligenciasMultiples;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.RespuestaInteligenciasMultiples;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.RespuestaPorInteligencia;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.TipoInteligenciasMultiples;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.facades.EncuestaInteligenciasMultiplesFacade;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "encuestaInteligenciasMultiplesController")
@SessionScoped
public class EncuestaInteligenciasMultiplesController 
        extends EncuestaControllerAbstract <
            EncuestaInteligenciasMultiples, 
            EncuestaInteligenciasMultiplesFacade,
            RespuestaInteligenciasMultiples, 
            PreguntaInteligenciasMultiples> {

    
    @EJB
    private EncuestaInteligenciasMultiplesFacade ejbFacade;
    
    /**
     * 
     */
    List<Contador<TipoInteligenciasMultiples>> estadisticaEncuentaIntelMultiples;
    
    /************************************************************************
     * Constructor
     ************************************************************************/
    public EncuestaInteligenciasMultiplesController() {
        super();
    }
    
    /************************************************************************
     * Funciones personalizadas
     ************************************************************************/

    protected void initializeEmbeddableKey() {
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public EncuestaInteligenciasMultiples prepareCreate() {
        Encuesta unaencuesta = getEncuestaGeneral();
        EncuestaInteligenciasMultiples unaencuestaEspe = new EncuestaInteligenciasMultiples(unaencuesta);
        unaencuestaEspe.setFechaCreacion(new Date());
        setSelected(unaencuestaEspe);
        this.update();
        
        unaencuesta.setEncuestaInteligenciasMultiples(getSelected());
        initializeEmbeddableKey();
        return getSelected();
    }

    /**
     * 
     * @param pregunta
     * @return 
     */
    @Override
    public RespuestaInteligenciasMultiples crearRespuestaEncuesta(PreguntaInteligenciasMultiples pregunta) {
        RespuestaInteligenciasMultiplesController respuestaContr = getRespuestaInteligenciasMultiplesController();
        RespuestaInteligenciasMultiples respuestaInt = respuestaContr.prepareCreate();
        respuestaInt.inicializar(getSelected(), pregunta);
        return respuestaInt;
    }

    @Override
    public EncuestaInteligenciasMultiples obtenerEncuestaEspecifica(Encuesta unaencuesta) {
        EncuestaInteligenciasMultiples unaencuestaEspe;
        if(unaencuesta.getEncuestaInteligenciasMultiples() == null){
            prepareCreate();
            unaencuestaEspe = getSelected();
        } else {
            unaencuestaEspe = unaencuesta.getEncuestaInteligenciasMultiples();
            setSelected(unaencuestaEspe);
        }
        unaencuesta.setEncuestaInteligenciasMultiples(unaencuestaEspe);
        getEncuestaController().update();
        
        return getSelected();
    }
    
    /**
     * 
     * @param restarget
     * @return 
     */
    @Override
    public RespuestaInteligenciasMultiples respuestaAleatoria(RespuestaInteligenciasMultiples restarget) {
        Short valor = (short)(((new Random()).nextBoolean())? 0 : 1);
        restarget.setRespuesta(valor);
        return restarget;
    }
    
    /**
     * 
     */
    @Override
    public void reiniciar() {
        setPasoActual(0);
        setGrupoActual(getGrupoItems(getPasoActual() + 1));
    }
    
    /**
     * 
     * @param encuesta 
     */
    @Override
    public void prepararEncuesta(Encuesta encuesta) {
        this.reiniciar();
        setTamGrupo(5); 
        getRespuestas();
    }

    /**
     * 
     * @return 
     */
    @Override
    public EncuestaInteligenciasMultiples getSelected() {
        if(super.getSelected() == null) {
            super.setSelected(getEncuestaController().getSelected().getEncuestaInteligenciasMultiples());
        }
        return super.getSelected() ;
    }
    
    /**
     * 
     * @return
     * @throws InterruptedException 
     */
    @Override
    public boolean finalizarEncuesta() throws InterruptedException {
        getEncuestaController().detenerReloj();
        
        Thread hilo = guardarRespuestas(getGrupoActual());
        if(hilo != null){
            
            hilo.join();
        }
        // colocar como finalizada y guarda cambios
        setSelected(null);
        getSelected().setEstado(EncuestaInteligenciasMultiples.FINALIZADA);
        getSelected().setFechaFinalizada(new Date());
        this.update();
        
        estadisticaEncuentaIntelMultiples = estadisticaEncuesta(getEncuestaGeneral().getEncuestaInteligenciasMultiples());
        
        guardarEstadisticasInteligencias(estadisticaEncuentaIntelMultiples);
        
        setPasoActual(getPasoActual() + 1);
        return true;
    }
    
    /**
     * Guardar datos de resultados de encuesta en BD
     * @param estadisticaEncuentaIntelMulti 
     */
    private void guardarEstadisticasInteligencias(List<Contador<TipoInteligenciasMultiples>> estadisticaEncuentaIntelMulti) {
        
        RespuestaPorInteligenciaController rpec = getRespuestaPorInteligenciaController();
        for (Contador<TipoInteligenciasMultiples> contador : estadisticaEncuentaIntelMulti) {
            RespuestaPorInteligencia respuestaEst = rpec.prepareCreate();
            respuestaEst.setTipoInteligenciasMultiples(contador.getTipo());
            respuestaEst.setEncuestaInteligenciasMultiples(this.getSelected());
            respuestaEst.setRespuesta(contador.getContador().shortValue());
            
            System.out.println("respuestaEst:"+respuestaEst);
            rpec.update();
        }
    }
    
    /**
     * Calcula Estadistica de tipo estilo de una encuesta
     * @param encuesta
     * @return 
     */
    public List<Contador<TipoInteligenciasMultiples>> estadisticaEncuesta(EncuestaInteligenciasMultiples encuesta){
        List<RespuestaInteligenciasMultiples> itemsRespuestas = getRespuestaInteligenciasMultiplesController().obtenerTodosPorEncuesta(encuesta);
        
        if(itemsRespuestas == null ){
            return null;
        }
        int numTipos = 7;
        List<Contador<TipoInteligenciasMultiples>> contador = new ArrayList<>(numTipos); /** 8 es la cantidad de tipos inteligencias multiples */
        int indice;
        for (int i = 0; i < numTipos; i++) {
            contador.add(new Contador());
        }
        /** Sumatoria de tipos de estilo de las respuestas */
        for (RespuestaInteligenciasMultiples item : itemsRespuestas) {
            PreguntaInteligenciasMultiples pregunta = item.getPreguntaInteligenciasMultiples();
            TipoInteligenciasMultiples tiposIntelMulti = pregunta.getIdTipoInteligenciasMultiples();
            
            indice = tiposIntelMulti.getId() - 1;
            if(item.getRespuesta() == 1){
                contador.get(indice).aumentarContador();
            } 
            contador.get(indice).setTipo(tiposIntelMulti);
        }
        
        contador.sort((Contador uno, Contador dos) -> {
            return - uno.getContador().compareTo(dos.getContador());
        });
        
        return contador;
    }
    
    
    /************************************************************************
     * GETTERS AND SETTERS METHODS
     ************************************************************************/
    
    /**
     * 
     * @return 
     */
    @Override
    public EncuestaInteligenciasMultiplesFacade getEjbFacade() {
        return ejbFacade;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getStringCreated(){
        return "EncuestaInteligenciasMultiplesCreated";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringUpdated(){
        return "EncuestaInteligenciasMultiplesUpdated";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringDeleted(){
        return "EncuestaInteligenciasMultiplesDeleted";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getName() {
        return "Inteligencias Multiples";
    }
    
    @Override
    public String getRuta() {
        return "/vistas/encuestaInteligenciasMultiples/index.xhtml";
    }

    @Override
    public PreguntaInteligenciasMultiplesController getPreguntaController() {
        return getPreguntaInteligenciasMultiplesController();
    }
    
    @Override
    public RespuestaControllerAbstract getRespuestaController() {
        return  getRespuestaInteligenciasMultiplesController();
    }

    public EncuestaInteligenciasMultiples getRespuestaInteligenciasMultiples(Integer id) {
        return getEjbFacade().find(id);
    }

    public List<Contador<TipoInteligenciasMultiples>> getEstadisticaEncuentaEstiloApren() {
        return estadisticaEncuentaIntelMultiples;
    }
    
    
    /************************************************************************
     * CONVERTER
     ************************************************************************/
    
    @FacesConverter(forClass = EncuestaInteligenciasMultiples.class)
    public static class RespuestaInteligenciasMultiplesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EncuestaInteligenciasMultiplesController controller = (EncuestaInteligenciasMultiplesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "encuestaInteligenciasMultiplesController");
            return controller.getRespuestaInteligenciasMultiples(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
             if (object == null) {
                return null;
            }
            if (object instanceof EncuestaInteligenciasMultiples) {
                EncuestaInteligenciasMultiples o = (EncuestaInteligenciasMultiples) object;
                return getStringKey(o.getIdEncuesta());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EncuestaEstilosAprendizaje.class.getName()});
                return null;
            }
        }

    }
    
}
