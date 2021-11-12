package com.ingesoft.suideal.encuesta.chaside.controladores;

import com.ingesoft.interpro.controladores.util.Contador;
import com.ingesoft.interpro.controladores.util.EncuestaControllerAbstract;
import com.ingesoft.interpro.controladores.util.RespuestaControllerAbstract;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.suideal.encuesta.chaside.entidades.EncuestaChaside;
import com.ingesoft.suideal.encuesta.chaside.entidades.PreguntaChaside;
import com.ingesoft.suideal.encuesta.chaside.entidades.RespuestaChaside;
import com.ingesoft.suideal.encuesta.chaside.entidades.ResultadoChaside;
import com.ingesoft.suideal.encuesta.chaside.entidades.TipoChaside;
import com.ingesoft.suideal.encuesta.chaside.entidades.TipoClaseChaside;
import com.ingesoft.suideal.encuesta.chaside.entidades.TipoClaseChasidePK;
import com.ingesoft.suideal.encuesta.chaside.facade.EncuestaChasideFacade;
import java.util.ArrayList;
import java.util.Arrays;
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

@ManagedBean(name = "chasideController")
@SessionScoped
public class ChasideController 
        extends EncuestaControllerAbstract<
            EncuestaChaside, 
            EncuestaChasideFacade,
            RespuestaChaside, 
            PreguntaChaside> {

    @EJB
    private com.ingesoft.suideal.encuesta.chaside.facade.EncuestaChasideFacade ejbFacade;
    
    private List<Contador<ResultadoChaside>> estadisticaEncuentaChaside;
      
    public ChasideController() {
        super();
    }

    public void inicializar(Encuesta selected) {

    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public EncuestaChaside getEncuestaChaside(Integer id) {
        return (EncuestaChaside) getFacade().find(id);
    }

    /**
     * 
     * @param pregunta
     * @return 
     */
    @Override
    public RespuestaChaside crearRespuestaEncuesta(PreguntaChaside pregunta) {
        RespuestaChasideController respuestaContr = getRespuestaChasideController();
        RespuestaChaside respuestaEstApre = respuestaContr.prepareCreate();
        respuestaEstApre.inicializar(getSelected(), pregunta);
        return respuestaEstApre;
    }

    /**
     * 
     * @param unaencuesta
     * @return 
     */
    @Override
    public EncuestaChaside obtenerEncuestaEspecifica(Encuesta unaencuesta) {
        EncuestaChaside unaencuestaEspe;
        if(unaencuesta.getEncuestaChaside() == null){
            prepareCreate();
            unaencuestaEspe = getSelected();
        } else {
            unaencuestaEspe = unaencuesta.getEncuestaChaside();
            setSelected(unaencuestaEspe);
        }
        unaencuesta.setEncuestaChaside(unaencuestaEspe);
        getEncuestaController().update();
        
        return getSelected();
    }

    /**
     * 
     * @param actualRespuesta
     * @return 
     */
    @Override
    public RespuestaChaside respuestaAleatoria(RespuestaChaside actualRespuesta) {
        short valuerand = (short)(((new Random()).nextBoolean())? 1: 0);
        actualRespuesta.setRespuesta(valuerand);
        return actualRespuesta;
    }

    @Override
    public EncuestaChaside prepareCreate() {
        
        Encuesta unaencuesta = getEncuestaGeneral();
        EncuestaChaside unaencuestaEspe = new EncuestaChaside(unaencuesta);
        unaencuestaEspe.setFechaCreacion(new Date());
        setSelected(unaencuestaEspe);
        this.update();
        return getSelected();
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
//        getSelected().setEstado(EncuestaChaside.FINALIZADA); // TODO: descomentar
        update();
        
        estadisticaEncuentaChaside = estadisticaEncuesta(getEncuestaGeneral().getEncuestaChaside());
        
        guardarEstadisticasChaside(estadisticaEncuentaChaside);
        
        setPasoActual(getPasoActual() + 1);
        return true;
    }
    
    private void guardarEstadisticasChaside(List<Contador<ResultadoChaside>> estadisticaEncuentaChaside) {
        
        ResultadoChasideController rpec = getResultadoChasideController();
        
        for (Contador<ResultadoChaside> contador : estadisticaEncuentaChaside) {
            System.out.println("respuestaEstrespuestaEst:"+contador.getTipo());
            rpec.setSelected(contador.getTipo());
            rpec.update();
        }
    }

    /**
     * Calcula Estadistica de tipo estilo de una encuesta
     * @param encuesta
     * @return 
     */
    public List<Contador<ResultadoChaside>> estadisticaEncuesta(EncuestaChaside encuesta){
        List<RespuestaChaside> itemsRespuestas = getRespuestaChasideController().getItemsXEncuesta(encuesta);
        setItemsRespuestas(itemsRespuestas);
        List<Contador<ResultadoChaside>> vectorRes = new ArrayList<>();
        
        TipoClaseChasideController unTipoClaseChasideController = getTipoClaseChasideController();
        ResultadoChasideController unResultadoChasideController = getResultadoChasideController();
        
        if(itemsRespuestas == null ){
            return null;
        }
        int cantClases = 2; // cantidad de tipos
        int cantTipos = 7; // cantidad de tipos
        Contador<ResultadoChaside>[][] contador = new Contador[cantClases][cantTipos]; /** 8 es la cantidad de tipos de estilo */
        contador[0] = new Contador[cantTipos];
        contador[1] = new Contador[cantTipos];
        int idEncuesta = getEncuestaGeneral().getIdEncuesta();
        int idClase;
        int idTipo;
        int columna;
        int fila;
        
        /** Sumatoria de tipos de estilo de las respuestas */
        for (RespuestaChaside item : itemsRespuestas) {
            PreguntaChaside pregunta = item.getPreguntaChaside();
            idClase = pregunta.getIdClaseChaside().getIdClaseChaside();
            idTipo = pregunta.getIdTipoChaside().getIdTipoChaside();
            columna = idClase - 1;
            fila = idTipo - 1;
            
            if(contador[columna][fila] == null) {
                Contador unContador = new Contador<ResultadoChaside>(){
                    @Override
                    public int aumentarContador() {
                        int suma = 1 + getTipo().getResultado();
                        getTipo().setResultado((short)suma);
                        return getTipo().getResultado();
                    }
                };
                unContador.setContador(0);
                
                TipoClaseChaside unTipoClaseChaside = unTipoClaseChasideController.getTipoClaseChaside(new TipoClaseChasidePK(idTipo, idClase));
                ResultadoChaside resultadoChaside = unResultadoChasideController.prepareCreate();
                resultadoChaside.setEncuestaChaside(encuesta);
                resultadoChaside.setTipoClaseChaside(unTipoClaseChaside);
                resultadoChaside.setResultado((short)0);
                unContador.setTipo(resultadoChaside);
                contador[columna][fila] = unContador;
                vectorRes.add(unContador);
            }
            contador[columna][fila].aumentarContador();
        }
        return vectorRes;
    }

    /************************************************************************
     * GETTERS AND SETTERS METHODS
     ************************************************************************/
    
    /**
     * 
     * @return 
     */
    @Override
    public EncuestaChasideFacade getEjbFacade() {
        return ejbFacade;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getStringCreated(){
        return "EncuestaChasideCreated";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringUpdated(){
        return "EncuestaChasideUpdated";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringDeleted(){
        return "EncuestaChasideDeleted";
    }
        /**
     * 
     * @return 
     */
   @Override
    public String getRuta() {
        return "/vistas/chaside/chasideindex.xhtml";
    }
    
    @Override
    public String getName() {
        return "Chaside";
    }

    @Override
    public PreguntaChasideController getPreguntaController() {
        return getPreguntaChasideController();
    }
    
    @Override
    public RespuestaControllerAbstract getRespuestaController() {
        return  getRespuestaChasideController();
    }

    /**
     * 
     * @return 
     */
    public ResultadoChaside[] getEstadisticaEncuentaChaside() {
        ResultadoChaside[] listaResultadod = new ResultadoChaside[2];
        
        // almacena el 
        int[] claseMax = new int[2];
        claseMax[0] = -1;
        claseMax[1] = -1;
        int indice;
        
        for (Contador<ResultadoChaside> contador : estadisticaEncuentaChaside) {
            int clase = contador.getTipo().getResultadoChasidePK().getIdClaseChaside();
            int tipo = contador.getTipo().getResultadoChasidePK().getIdTipoChaside();
            indice = clase - 1;
            
            if( listaResultadod[indice] == null || listaResultadod[indice].getResultado() < contador.getTipo().getResultado()){
                listaResultadod[indice] = contador.getTipo();
            }
        }
        
        return listaResultadod;
    }

//    
    /************************************************************************
     * CONVERTER
     ************************************************************************/
    
    @FacesConverter(forClass = EncuestaChaside.class)
    public static class EncuestaChasideConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ChasideController controller = (ChasideController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "estiloController");
            return controller.getEncuestaChaside(getKey(value));
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
            if (object instanceof EncuestaChaside) {
                EncuestaChaside o = (EncuestaChaside) object;
                return getStringKey(o.getIdEncuesta());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EncuestaChaside.class.getName()});
                return null;
            }
        }

    }
    
}
