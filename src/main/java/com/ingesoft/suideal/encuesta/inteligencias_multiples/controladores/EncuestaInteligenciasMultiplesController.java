package com.ingesoft.suideal.encuesta.inteligencias_multiples.controladores;

import com.ingesoft.interpro.controladores.EncuestaController;
import com.ingesoft.interpro.controladores.util.ContadorTiposEstilos;
import com.ingesoft.interpro.controladores.util.EncuestaControllerAbstract;
import com.ingesoft.interpro.controladores.util.PreguntaEncuesta;
import com.ingesoft.interpro.controladores.util.RespuestaControllerAbstract;
import com.ingesoft.interpro.controladores.util.RespuestaEncuestaAbstract;
import com.ingesoft.interpro.controladores.util.Utilidades;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.EncuestaEstilosAprendizaje;
import com.ingesoft.interpro.facades.EncuestaEstilosAprendizajeFacade;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.EncuestaInteligenciasMultiples;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.PreguntaInteligenciasMultiples;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.RespuestaInteligenciasMultiples;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.facades.EncuestaInteligenciasMultiplesFacade;
import java.io.IOException;
import java.util.ArrayList;

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
import javax.faces.event.ActionEvent;

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
    
    
    ContadorTiposEstilos[] estadisticaEncuentaIntelMultiples;
    
    public EncuestaInteligenciasMultiplesController() {
        super();
    }
    
    public void inicializar(Encuesta selected) {

    }

    @Override
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    public EncuestaInteligenciasMultiplesFacade getEjbFacade() {
        return ejbFacade;
    }

    /**
     * 
     * @return 
     */
    @Override
    public EncuestaInteligenciasMultiples prepareCreate() {
        Encuesta unaencuesta = getEncuestaGeneral();
        EncuestaInteligenciasMultiples unaencuestaEspe = new EncuestaInteligenciasMultiples(unaencuesta);
        
        unaencuesta.setEncuestaInteligenciasMultiples(unaencuestaEspe);
        getEncuestaController().update();
        setSelected(unaencuestaEspe);
        
        initializeEmbeddableKey();
        return getSelected();
    }

    @Override
    public String getStringCreated(){
        return "EncuestaInteligenciasMultiplesCreated";
    }
    
    @Override
    public String getStringUpdated(){
        return "EncuestaInteligenciasMultiplesUpdated";
    }
    
    @Override
    public String getStringDeleted(){
        return "EncuestaInteligenciasMultiplesDeleted";
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
        if(unaencuesta.getEncuestaEstilosAprendizaje() == null){
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
    
//    @Override
//    public List<RespuestaInteligenciasMultiples> getRespuestas() {
//        /**
//         * TODO: prueba
//         * 
//         */
//        EncuestaController encuestaControl = getEncuestaController();
//        encuestaControl.setSelected(encuestaControl.getEncuesta(1085));
//        // ---------------
//        
//        List<RespuestaInteligenciasMultiples> itemsRespuestas = getItemsRespuestas();
//        if (itemsRespuestas == null || true) { //|| listaPreguntas.size() < itemsRespuestas.size()
//            List<PreguntaInteligenciasMultiples> listaPreguntas = getPreguntas();
//            Encuesta encuesta = getEncuestaGeneral();
//            int contPuntosRecuperados = 0;
//            if(encuesta.getPuntajeEncuesta() != null && encuesta.getPuntajeEncuesta() >= 0) {
//                contPuntosRecuperados = encuesta.getPuntajeEncuesta();
//            }
//            int tamListPreguntas = listaPreguntas.size();
//            itemsRespuestas = new ArrayList<>(tamListPreguntas);
//            if(encuesta.getEncuestaEstilosAprendizaje() == null){
//                prepareCreate();
////                this.update();
//                getSelected().setEncuesta(encuesta);
//                getEncuestaController().update();
//            } else {
//                setSelected(encuesta.getEncuestaInteligenciasMultiples());
//            }
//            RespuestaInteligenciasMultiplesController respuestaContr = (RespuestaInteligenciasMultiplesController) getRespuestaController();
//            List<RespuestaInteligenciasMultiples> items_recuperados = respuestaContr.obtenerTodosPorEncuesta(getSelected());
//            RespuestaInteligenciasMultiples actualRespuesta;
//            RespuestaInteligenciasMultiples unaRespuestaAuxiliar;
//            
//            for (PreguntaInteligenciasMultiples pregunta : listaPreguntas) {
//                actualRespuesta = crearRespuestaEncuesta(pregunta);
//                unaRespuestaAuxiliar = null;
//                if (items_recuperados != null && !items_recuperados.isEmpty()) {
//                    int indice = items_recuperados.indexOf(actualRespuesta);
//                    if (indice >= 0) {
//                        unaRespuestaAuxiliar = items_recuperados.get(indice);
//                    }
//                }
//                if (unaRespuestaAuxiliar == null) {
//                    if(Utilidades.esDesarrollo()){
//                        respuestaAleatoria(actualRespuesta);
//                    }
//                } else {
//                    actualRespuesta = unaRespuestaAuxiliar;
//                    actualRespuesta.responder();
//                }
//                itemsRespuestas.add(actualRespuesta);
//            }
//            encuesta.setPuntajeEncuesta(contPuntosRecuperados);
//            setItemsRespuestas(itemsRespuestas);
//        }
//        
//        //ubicar la encuesta en la ultima pagina respondida
//        // TODO
//        return itemsRespuestas;
//    }
    
    public EncuestaInteligenciasMultiples getRespuestaInteligenciasMultiples(Integer id) {
        return getEjbFacade().find(id);
    }

    public ContadorTiposEstilos[] getEstadisticaEncuentaEstiloApren() {
        return estadisticaEncuentaIntelMultiples;
    }
    
    public void seleccionarPunto(RespuestaInteligenciasMultiples respuestaEstilo) {
        if (!respuestaEstilo.isRespondida()) {
            getEncuestaController().aumentarPuntos();
            getEncuestaController().setTiempo(0);
            respuestaEstilo.responder();
        }
    }
    
    @Override
    public void reiniciar() {
        setPasoActual(0);
        setGrupoActual(getGrupoItems(getPasoActual() + 1));
    }
    
    @Override
    public void prepararEncuesta(Encuesta encuesta) {
        this.setListaPreguntas(getPreguntas());
        this.definirNumeroGrupos();
        this.reiniciar();
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
    

    public boolean finalizarEncuesta() throws InterruptedException {
//        getEncuestaController().detenerReloj();
//        
//        Thread hilo = guardarRespuestas(grupo);
//        hilo.join();
//        // colocar como finalizada y guarda cambios
//        getSelected().setEstado(EncuestaEstilosAprendizaje.FINALIZADA);
//        update();
//        
//        estadisticaEncuentaIntelMultiples = estadisticaEncuesta(encuesta.getEncuestaEstilosAprendizaje());
//        
//        setPasoActual(getPasoActual() + 1);
//        getEncuestaController().finalizarEncuesta();
//        return true;
        return false;
    }
    
        
    public List<RespuestaInteligenciasMultiples> actualizarTodasRespuestas() throws IOException, InterruptedException {
        RespuestaInteligenciasMultiplesController intelMultiplesController = getRespuestaInteligenciasMultiplesController();
        intelMultiplesController.actualizarTodasRespuestas(getItemsRespuestas());
        setPasoActual(getNumGrupos() - 1);
        finalizarEncuesta();
        return getItemsRespuestas();
    }
    
    /**
     * Calcula Estadistica de tipo estilo de una encuesta
     * @param encuesta
     * @return 
     */
    public ContadorTiposEstilos[] estadisticaEncuesta(EncuestaEstilosAprendizaje encuesta){
//        this.itemsRespuestas = getRespuestaInteligenciasMultiplesController().getItemsXEncuesta(encuesta);
        
//        if(this.itemsRespuestas == null ){
            return null;
//        }
//        ContadorTiposEstilos[][] contador = new ContadorTiposEstilos[2][4]; /** 8 es la cantidad de tipos de estilo */
//        contador[0] = new ContadorTiposEstilos[4];
//        contador[1] = new ContadorTiposEstilos[4];
//        int indice;
//        int columna;
//        int fila;
//        
//        /** Sumatoria de tipos de estilo de las respuestas */
//        for (RespuestaInteligenciasMultiples item : this.itemsRespuestas) {
//            PreguntaInteligenciasMultiples pregunta = item.getIdpreguntaEstilos();
//            List<TipoEstiloPregunta> listaTiposEstiloPregunta = pregunta.getTipoEstiloPreguntaList();
//            TipoEstiloPregunta obj = (listaTiposEstiloPregunta.get(0).getIndice().equals(item.getRespuesta()))?listaTiposEstiloPregunta.get(0):listaTiposEstiloPregunta.get(1);
//                 
//            indice = obj.getTipoEstilo().getId()-1;
//            columna = indice % 2;
//            fila = indice / 2;
//            if(contador[columna][fila] == null) {
//                contador[columna][fila] = new ContadorTiposEstilos();
//            }
//            contador[columna][fila].aumentarContador();
//            contador[columna][fila].setTipoEstilo(obj.getTipoEstilo());
//            
//        }
//        ContadorTiposEstilos[] vectorRes = new ContadorTiposEstilos[4];
//        int resta;
//        /** Calculo de grupos de tipos de estilo */
//        for (int i = 0; i < 4; i++) {
//            
//            indice = contador[0][i].getContador() > contador[1][i].getContador() ? 0 : 1;
//            resta = Math.abs(contador[0][i].getContador() - contador[1][i].getContador());
//            
//            if(vectorRes[i] == null) {
//                vectorRes[i] = new ContadorTiposEstilos();
//            }
//            vectorRes[i].setTipoEstilo(contador[indice][i].getTipoEstilo());
//            vectorRes[i].setContador(resta);
//            
//        }
//        return vectorRes;
    }

}
