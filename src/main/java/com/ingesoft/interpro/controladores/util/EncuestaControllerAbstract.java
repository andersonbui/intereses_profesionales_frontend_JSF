/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores.util;

import com.ingesoft.interpro.controladores.EncuestaController;
import com.ingesoft.interpro.controladores.EncuestaControllerInterface;
import com.ingesoft.interpro.entidades.Encuesta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anderson
 * @param <EncuestaEspecifica>
 * @param <EncuestaEspecificaFacade>
 * @param <Respuesta>
 * @param <Pregunta>
 */
public abstract class EncuestaControllerAbstract <
            EncuestaEspecifica, 
            EncuestaEspecificaFacade, 
            Respuesta extends RespuestaEncuestaAbstract, 
            Pregunta
        > 
        extends ControllerAbstract<EncuestaEspecifica, EncuestaEspecificaFacade>
        implements Serializable, EncuestaControllerInterface {
    
    /**
     * Cantidad de preguntas por pagina 
     */
    private int tamGrupo;
    
    /**
     * Indice de la ventana actual de esta encuesta personalida
     */
    private int pasoActual;
    /**
     * cantidad de paginas
     */
    private int numGrupos;
    
    /**
     * Encuesta general asociada
     */
    Encuesta encuesta;
    
    /**
     * Total de pregutnas a responder
     */
    private List<Pregunta> listaPreguntas = null;
    
    /**
     * Respuestas asociadas a las preguntas a responder
     */
    private List<Respuesta> itemsRespuestas = null;
    
    /**
     * contador de puntos por respuesta
     */
    int[] vecContadorRespuestasEstiloApren; // contador de puntos por respuesta
    
    
    /**
     * grupo actual de respuestas
     */
    private List<Respuesta> grupoActual = null;
    
    
    /************************************************************************
     * Constructor
     ************************************************************************/
    public EncuestaControllerAbstract() {
        tamGrupo = 3;
        pasoActual = 0;
        numGrupos = 0;
    }
    
    /************************************************************************
     * Funciones personalizadas
     ************************************************************************/
    
    /**
     * 
     * @return 
     */
    public int definirNumeroGrupos() {
        List respuestas = getRespuestas();
        int numeroGrupos = respuestas.size() / getTamGrupo();
        numeroGrupos += (respuestas.size() % getTamGrupo() == 0 ? 0 : 1);
        numGrupos = numeroGrupos;
        return numGrupos;
    }
    
    /**
     * Crear nuevo registro en BD
     * @return 
     */
    @Override
    public boolean create() {
        boolean res = super.create();
        if(res){
            itemsRespuestas = null;    // Invalidate list of items to trigger re-query.
        }
        return res;
    }

    /**
     * Eliminar nuevo registro en BD
     * @return 
     */
    @Override
    public boolean destroy() {
        boolean res = super.destroy();
        if(res){
            itemsRespuestas = null;    // Invalidate list of items to trigger re-query.
        }
        return res;
    }
    
    /**
     * Obtener listado de todas las preguntas disponibles en BD
     * @return 
     */
    public List<Pregunta> getPreguntas(){
        PreguntaControllerAbstract preguntaController = getPreguntaController();
        return preguntaController.getItems();
    }
        
    /**
     * Guardar respuestas en BD desde una lista de Respuestas de forma asincrona
     * @param listaRespuestas 
     * @return  
     */
    public HiloGuardado guardarRespuestas(List<Respuesta> listaRespuestas) {
        // guardar respuestas actuales
        if (listaRespuestas != null && !listaRespuestas.isEmpty()) {
            HiloGuardado hilo = new HiloGuardado(listaRespuestas, getRespuestaController());
            hilo.start();
            return hilo;
        }
        return null;
    }

    public class HiloGuardado extends Thread {

        private final List<Respuesta> itemsRespuestas;
        RespuestaControllerAbstract respuestaController;

        public HiloGuardado(List<Respuesta> itemsRespuestas, RespuestaControllerAbstract respuestaController) {
            this.itemsRespuestas = itemsRespuestas;
            this.respuestaController = respuestaController;
        }

        @Override
        public void run() {
            for (Respuesta respuesta : itemsRespuestas) {
                this.respuestaController.getFacade().edit(respuesta);
            }
            System.out.println("Termino de guardar Respuestas Estilo A");
        }

    }
    
       
    /**
     * Obtiene las respuestas de un determinado grupo dado por parametro.
     * @param numGrupo
     * @return ista de respuestas del grupo
     */
    public List<Respuesta> getGrupoItems(int numGrupo){
        List<Respuesta> lsItemsRespuestas = getRespuestas();
        guardarRespuestas(getGrupoActual());
        int tamanioGrupo = getTamGrupo();
        List<Respuesta> listaRespuestas = null;
        if (lsItemsRespuestas != null) {
            listaRespuestas = new ArrayList<>();
            for (int i = tamanioGrupo * (numGrupo - 1); i < tamanioGrupo * numGrupo; i++) {
                if (i >= 0 && i < lsItemsRespuestas.size()) {
                    listaRespuestas.add(lsItemsRespuestas.get(i));
                } else {
                    break;
                }
            }
        }
        return listaRespuestas;
    }

    /**
     * 
     * @return 
     */
    public List<Respuesta> getRespuestas() {
        List<Respuesta> listaItemsRespuestas = getItemsRespuestas();
        if (listaItemsRespuestas == null || true) { //|| listaPreguntas.size() < itemsRespuestas.size()
            List<Pregunta> listaItemsPreguntas = getPreguntas();
            Encuesta encuestaGeneral = getEncuestaGeneral();
            int contPuntosRecuperados = 0;
            if(encuestaGeneral.getPuntajeEncuesta() != null && encuestaGeneral.getPuntajeEncuesta() >= 0) {
                contPuntosRecuperados = encuestaGeneral.getPuntajeEncuesta();
            }
            int tamListPreguntas = listaItemsPreguntas.size();
            listaItemsRespuestas = new ArrayList<>(tamListPreguntas);
            
            obtenerEncuestaEspecifica(encuestaGeneral);

            RespuestaControllerAbstract respuestaContr = getRespuestaController();
            List<Respuesta> items_recuperados = respuestaContr.obtenerTodosPorEncuesta(getSelected());
            Respuesta actualRespuesta;
            Respuesta unaRespuestaAuxiliar;
            
            for (Pregunta pregunta : listaItemsPreguntas) {
                actualRespuesta = crearRespuestaEncuesta(pregunta);
                unaRespuestaAuxiliar = null;
                if (items_recuperados != null && !items_recuperados.isEmpty()) {
                    int indice = items_recuperados.indexOf(actualRespuesta);
                    if (indice >= 0) {
                        unaRespuestaAuxiliar = items_recuperados.get(indice);
                    }
                }
                if (unaRespuestaAuxiliar == null) {
                    if(Utilidades.esDesarrollo()){
                        respuestaAleatoria(actualRespuesta);
                    }
                } else {
                    actualRespuesta = unaRespuestaAuxiliar;
                    actualRespuesta.responder();
                }
                listaItemsRespuestas.add(actualRespuesta);
            }
            encuestaGeneral.setPuntajeEncuesta(contPuntosRecuperados);
            setItemsRespuestas(listaItemsRespuestas);
        }
        
        //ubicar la encuesta en la ultima pagina respondida
        // TODO
        return listaItemsRespuestas;
    }
    
    /**
     * 
     * @return
     * @throws InterruptedException 
     */
    public List<Respuesta> actualizarTodasRespuestas() throws InterruptedException {
        RespuestaControllerAbstract unaRespuestaController = getRespuestaController();
        unaRespuestaController.actualizarTodasRespuestas(getItemsRespuestas());
        setPasoActual(getNumGrupos() - 1);
        finalizarEncuesta();
        return getItemsRespuestas();
    }
    
    public void seleccionarPunto(Respuesta respuestaEstilo) {
        if (!respuestaEstilo.isRespondida()) {
            getEncuestaController().aumentarPuntos();
            getEncuestaController().setTiempo(0);
            respuestaEstilo.responder();
        }
    }
    
    /**************************************************************************
     * Abstracts methods
     **************************************************************************/
    /**
     * 
     * @return 
     */
//    public abstract boolean finalizarEncuesta() throws InterruptedException;

    /**
     * 
     * @return
     * @throws java.lang.InterruptedException
     */
    public abstract boolean finalizarEncuesta() throws InterruptedException ;
    
    /**
     * 
     * @return 
     */
    public abstract RespuestaControllerAbstract getRespuestaController();
    
    /**
     * 
     * @return 
     */
    public abstract PreguntaControllerAbstract getPreguntaController();
    
    /**
     * 
     * @param res
     * @return 
     */
    public abstract Respuesta crearRespuestaEncuesta(Pregunta res);

    /**
     * 
     * @param encuesta
     * @return 
     */
    public abstract EncuestaEspecifica obtenerEncuestaEspecifica(Encuesta encuesta);

    /**
     * 
     * @param actualRespuesta
     * @return 
     */
    public abstract Respuesta respuestaAleatoria(Respuesta actualRespuesta);
    
    
    /**************************************************************************
     * Getter and Setter methods
     **************************************************************************/
    
    /**
     * 
     * @return 
     */
    public int getTamGrupo() {
        return tamGrupo;
    }

    /**
     * 
     * @return 
     */
    public int getNumGrupos() {
        return numGrupos;
    }
    
    /**
     * 
     * @param tamGrupo 
     */
    public void setTamGrupo(int tamGrupo) {
        this.tamGrupo = tamGrupo;
    }

    /**
     * 
     * @return 
     */
    public int getPasoActual() {
        return pasoActual;
    }

    /**
     * 
     * @param pasoActual 
     */
    public void setPasoActual(int pasoActual) {
        this.pasoActual = pasoActual;
    }

    /**
     * 
     * @param numGrupos 
     */
    public void setNumGrupos(int numGrupos) {
        this.numGrupos = numGrupos;
    }

    /**
     * 
     * @return 
     */
    public List<Respuesta> getGrupoActual() {
        return grupoActual;
    }

    /**
     * 
     * @param grupoActual 
     */
    public void setGrupoActual(List<Respuesta> grupoActual) {
        this.grupoActual = grupoActual;
    }

        /**
     * Funciones de encuesta
     * @return
     */
    public int getUltimoPaso() {
        return (getNumGrupos());
    }

    /**
     * 
     * @return 
     */
    public int getStep() {
        return getPasoActual();
    }

    /**
     * 
     * @return 
     */
    public int getnombrePaso() {
        return (getPasoActual() * 100 / getNumGrupos());
    }

    /**
     * 
     * @return 
     */
    public boolean puedeAnteriorPaso() {
        return getPasoActual() > 0;
    }

    /**
     * 
     * @return 
     */
    public boolean puedeSiguientePasoNoUltimo() {
        return getPasoActual() < (getNumGrupos() - 1);
    }

    /**
     * 
     * @return 
     */
    public boolean esPenultimoPaso() {
        return getPasoActual() == (getNumGrupos() - 1);
    }

    /**
     * 
     * @return 
     */
    public boolean esUltimoPaso() {
        return getPasoActual() == getNumGrupos();
    }

    /**
     * 
     * @return 
     */
    public int anteriorPaso() {
        setPasoActual(getPasoActual() - 1);
        setGrupoActual(getGrupoItems(getPasoActual() + 1));
        return getPasoActual();
    }

    /**
     * 
     * @return 
     */
    public int siguientePaso() {
        setPasoActual(getPasoActual() + 1);
        setGrupoActual(getGrupoItems(getPasoActual() + 1));
        return getPasoActual();
    }
    
    /**
     * 
     * @return 
     */
    public Encuesta getEncuestaGeneral() {
        return getEncuestaController().getSelected();
    }

    public List<Pregunta> getListaPreguntas() {
        return listaPreguntas;
    }

    public void setListaPreguntas(List<Pregunta> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }

    public List<Respuesta> getItemsRespuestas() {
        return itemsRespuestas;
    }

    public void setItemsRespuestas(List<Respuesta> itemsRespuestas) {
        this.itemsRespuestas = itemsRespuestas;
    }

    public int[] getVecContadorRespuestasEstiloApren() {
        return vecContadorRespuestasEstiloApren;
    }

    public void setVecContadorRespuestasEstiloApren(int[] vecContadorRespuestasEstiloApren) {
        this.vecContadorRespuestasEstiloApren = vecContadorRespuestasEstiloApren;
    }
    
    
    
}
