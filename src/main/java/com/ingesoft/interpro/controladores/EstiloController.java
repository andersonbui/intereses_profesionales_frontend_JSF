package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.ContadorTiposEstilos;
import com.ingesoft.interpro.entidades.RespuestaEstilo;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.controladores.util.Utilidades;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.PreguntaEstilosAprendizajeFs;
import com.ingesoft.interpro.entidades.RespuestaEstiloPK;
import com.ingesoft.interpro.facades.RespuestaEstiloFacade;
import com.ingesoft.interpro.entidades.TipoEstiloPregunta;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
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

@ManagedBean(name = "estiloController")
@SessionScoped
public class EstiloController extends Controller implements Serializable, EncuestaControllerInterface {

    @EJB
    private com.ingesoft.interpro.facades.RespuestaEstiloFacade ejbFacade;
    private List<RespuestaEstilo> items = null;
    private RespuestaEstilo selected;
    private List<RespuestaEstilo> grupo = null;
    private List<PreguntaEstilosAprendizajeFs> listaPreguntas = null;
    
    /**
     * Cantidad de preguntas por pagina 
     */
    private final int tamGrupo;
    private int pasoActual;
    /**
     * cantidad de paginas
     */
    private int numGrupos;
    /**
     * contador de puntos por respuesta
     */
    int[] vecContadorRespuestasEstiloApren; // contador de puntos por respuesta
    
    Encuesta encuesta;
    
    ContadorTiposEstilos[] estadisticaEncuentaEstiloApren;
    
    public EstiloController() {
        tamGrupo = 3;
        pasoActual = 0;
        numGrupos = 0;
    }

    public void inicializar(Encuesta selected) {

    }

    public int getNumGrupos() {
        items = getRespuestas();
        numGrupos = items.size() / tamGrupo;
        numGrupos += (items.size() % tamGrupo == 0 ? 0 : 1);
        return numGrupos;
    }
    
    public List<RespuestaEstilo> getGrupo() {
        return grupo;
    }

    private void setGrupo(List<RespuestaEstilo> grupo) {
        this.grupo = grupo;
    }

    public RespuestaEstilo getSelected() {
        return selected;
    }

    public void setSelected(RespuestaEstilo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    protected RespuestaEstiloFacade getFacade() {
        return ejbFacade;
    }

    public RespuestaEstilo prepareCreate() {
        selected = new RespuestaEstilo();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaEstiloCreated"), selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaEstiloUpdated"), selected);
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RespuestaEstiloDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RespuestaEstilo> obtenerTodosPorEncuesta(Encuesta encuesta) {
        return getFacade().getItemsXEncuesta(encuesta);
    }
    
    public List<RespuestaEstilo> getRespuestas() {
        if (items == null) {
            int contPuntosRecuperados = 0;
            if(encuesta.getPuntajeEncuesta() != null && encuesta.getPuntajeEncuesta() >= 0) {
                contPuntosRecuperados = encuesta.getPuntajeEncuesta();
            }
            items = new ArrayList<>(listaPreguntas.size());
            vecContadorRespuestasEstiloApren =  new int[listaPreguntas.size()];
            vecContadorRespuestasEstiloApren = new int[listaPreguntas.size()]; // puntos
            List<RespuestaEstilo> items_recuperados = obtenerTodosPorEncuesta(encuesta);
            for (PreguntaEstilosAprendizajeFs pregunta : listaPreguntas) {
                selected = new RespuestaEstilo(pregunta, this.encuesta);
                Character respuesta = null;
                if (items_recuperados != null && !items_recuperados.isEmpty()) {
                    int indice = items_recuperados.indexOf(selected);
                    if (indice >= 0) {
                        int i = (pregunta.getOrden() - 1);
                        vecContadorRespuestasEstiloApren[i]++;//desactivar puntos a respuestas respondidas anteriormente
                        contPuntosRecuperados ++;
                        respuesta = items_recuperados.get(indice).getRespuesta();
                    }
                }
                if (respuesta == null && Utilidades.esDesarrollo()) {
                    char valuerand = ((new Random()).nextBoolean())? 'A': 'B';
                    respuesta = valuerand;
                    getEncuestaController().addPuntos_eval(1);
                }
                if(respuesta != null){
                    selected.setRespuesta(respuesta);
                }
                items.add(selected);
            }
            encuesta.setPuntajeEncuesta(contPuntosRecuperados);
        }
        
        //ubicar la encuesta en la ultima pagina respondida
        // TODO
        
        return items;
    }
    
    public List<RespuestaEstilo> getItemsXEncuesta(Encuesta encuesta) {
        return getFacade().getItemsXEncuesta(encuesta);
    }
    
    public RespuestaEstilo getRespuestaEstilo(RespuestaEstiloPK id) {
        return getFacade().find(id);
    }

    public List<RespuestaEstilo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RespuestaEstilo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public ContadorTiposEstilos[] getEstadisticaEncuentaEstiloApren() {
        return estadisticaEncuentaEstiloApren;
    }
    
    public void seleccionarPunto(RespuestaEstilo respuestaEstilo) {
        int posicion = respuestaEstilo.getIdpreguntaEstilos().getOrden()- 1;
        if (vecContadorRespuestasEstiloApren[posicion] == 0) {
            getEncuestaController().aumentarPuntos();
            getEncuestaController().setTiempo(0);//Number(0);
        }
        vecContadorRespuestasEstiloApren[posicion]++;
    }
    
    /**
     * Guardar respuestas en BD desde una lista de Respuestas de forma asincrona
     * @param listaRespuestas 
     * @return  
     */
    public HiloGuardado guardarRespuestas(List<RespuestaEstilo> listaRespuestas) {
        // guardar respuestas actuales
        if (listaRespuestas != null && !listaRespuestas.isEmpty()) {
            HiloGuardado hilo = new HiloGuardado(listaRespuestas);
            hilo.start();
            return hilo;
        }
        return null;
    }
    /**
     * obtiene las respuestas de un determinado grupo
     *
     * @param numGrupo
     * @return
     */
    public List<RespuestaEstilo> getGrupoItems(int numGrupo){
        items = getRespuestas();
        
        guardarRespuestas(grupo);
        
        List<RespuestaEstilo> listaRespuestas = null;
        if (items != null) {
            listaRespuestas = new ArrayList<>();
            for (int i = tamGrupo * (numGrupo - 1); i < tamGrupo * numGrupo; i++) {
                if (i >= 0 && i < items.size()) {
                    listaRespuestas.add(items.get(i));
                } else {
                    break;
                }
            }
        }
        return listaRespuestas;
    }

    @Override
    public void reiniciar() {
        pasoActual = 0;
        return ;
    }

    @Override
    public void prepararEncuesta(Encuesta encuesta) {
        listaPreguntas = getPreguntaEstilosAprendizajeFsController().getItems();
        this.encuesta = encuesta;
        pasoActual = 0;
        
        getNumGrupos();
        grupo = getGrupoItems(pasoActual + 1);
        
    }

    @Override
    public String getRuta() {
        return "/vistas/preguntasEstilosAprendizaje/index.xhtml";
    }
    
    public class HiloGuardado extends Thread {

        private final List<RespuestaEstilo> itemsRespuestas;

        public HiloGuardado(List<RespuestaEstilo> itemsRespuestas) {
            this.itemsRespuestas = itemsRespuestas;
        }

        @Override
        public void run() {
            for (RespuestaEstilo respuesta : itemsRespuestas) {
                getFacade().edit(respuesta);
            }
            System.out.println("Termino de guardar Respuestas Estilo A");
        }

    }
    @FacesConverter(forClass = RespuestaEstilo.class)
    public static class RespuestaEstiloControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EstiloController controller = (EstiloController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "respuestaAmbienteController");
            return controller.getRespuestaEstilo(getKey(value));
        }

        com.ingesoft.interpro.entidades.RespuestaEstiloPK getKey(String value) {
            com.ingesoft.interpro.entidades.RespuestaEstiloPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.ingesoft.interpro.entidades.RespuestaEstiloPK();
            key.setIdpregunta_estilos(Integer.parseInt(values[0]));
            key.setEncuesta_idEncuesta(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(com.ingesoft.interpro.entidades.RespuestaEstiloPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdpregunta_estilos());
            sb.append(SEPARATOR);
            sb.append(value.getEncuesta_idEncuesta());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RespuestaEstilo) {
                RespuestaEstilo o = (RespuestaEstilo) object;
                return getStringKey(o.getRespuestaEstiloPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RespuestaEstilo.class.getName()});
                return null;
            }
        }

    }
    
    
    /**
     * Funciones de encuesta
     */
      
    public int getPasoActual() {
        return pasoActual;
    }

    public int getUltimoPaso() {
        return (numGrupos);
    }

    public void setPasoActual(int pasoActual) {
        this.pasoActual = pasoActual;
    }

    public int getStep() {
        return pasoActual;
    }

    public int getnombrePaso() {
        return (pasoActual * 100 / numGrupos);
    }

    public boolean puedeAnteriorPaso() {
        return pasoActual > 0;
    }

    public boolean puedeSiguientePasoNoUltimo() {
        return pasoActual < (numGrupos - 1);
    }

    public boolean esPenultimoPaso() {
        return pasoActual == (numGrupos - 1);
    }

    public boolean esUltimoPaso() {
        return pasoActual == numGrupos;
    }

    public int anteriorPaso() {
        pasoActual -= 1;
        setGrupo(getGrupoItems(pasoActual + 1));
        return pasoActual;
    }

    public int siguientePaso(ActionEvent actionEvent) {
        pasoActual += 1;
        setGrupo(getGrupoItems(pasoActual + 1));
        return pasoActual;
    }

    public boolean finalizarEncuesta() throws InterruptedException {
        getEncuestaController().detenerReloj();
        
        Thread hilo = guardarRespuestas(grupo);
        hilo.join();
        
        estadisticaEncuentaEstiloApren = estadisticaEncuesta(encuesta);
        
        pasoActual += 1;
        getEncuestaController().finalizarEncuesta();
        return true;
    }
    
        
    public List<RespuestaEstilo> actualizarTodasRespuestas() throws IOException, InterruptedException {
        for (RespuestaEstilo item : items) {
            this.getFacade().edit(item);
        }
        pasoActual = (numGrupos - 1);
        finalizarEncuesta();
        return items;
    }
    
    
    // public void estadisticaEncuestaPrueba(){
        
    //     int idEncuesta = 727;
    //     Encuesta encuesta = this.getEncuestaController().getEncuesta(idEncuesta);
    //     ContadorTiposEstilos[] listaContadorTiposEstilos = this.estadisticaEncuesta(encuesta);
    //     for (ContadorTiposEstilos item : listaContadorTiposEstilos) {
    //         System.out.println("resta: "+ item.getContador() + "| tipoEstilo: "+item.getTipoEstilo().getNombre());
            
    //     }
        
    // }
    
    /**
     * Calcula Estadistica de tipo estilo de una encuesta
     * @param encuesta
     * @return 
     */
    public ContadorTiposEstilos[] estadisticaEncuesta(Encuesta encuesta){
        this.items = this.getEstiloController().getItemsXEncuesta(encuesta);
        
        if(this.items == null ){
            return null;
        }
        ContadorTiposEstilos[][] contador = new ContadorTiposEstilos[2][4]; /** 8 es la cantidad de tipos de estilo */
        contador[0] = new ContadorTiposEstilos[4];
        contador[1] = new ContadorTiposEstilos[4];
        int indice;
        int columna;
        int fila;
        
        /** Sumatoria de tipos de estilo de las respuestas */
        for (RespuestaEstilo item : this.items) {
            PreguntaEstilosAprendizajeFs pregunta = item.getIdpreguntaEstilos();
            List<TipoEstiloPregunta> listaTiposEstiloPregunta = pregunta.getTipoestiloPreguntaList();
            TipoEstiloPregunta obj = (listaTiposEstiloPregunta.get(0).getIndice().equals(item.getRespuesta()))?listaTiposEstiloPregunta.get(0):listaTiposEstiloPregunta.get(1);
                 
            indice = obj.getTipoEstilo().getIdTipoEstilo()-1;
            columna = indice % 2;
            fila = indice / 2;
            if(contador[columna][fila] == null) {
                contador[columna][fila] = new ContadorTiposEstilos();
            }
            contador[columna][fila].aumentarContador();
            contador[columna][fila].setTipoEstilo(obj.getTipoEstilo());
            
        }
        ContadorTiposEstilos[] vectorRes = new ContadorTiposEstilos[4];
        int resta;
        /** Calculo de grupos de tipos de estilo */
        for (int i = 0; i < 4; i++) {
            
            indice = contador[0][i].getContador() > contador[1][i].getContador() ? 0 : 1;
            resta = Math.abs(contador[0][i].getContador() - contador[1][i].getContador());
            
            if(vectorRes[i] == null) {
                vectorRes[i] = new ContadorTiposEstilos();
            }
            vectorRes[i].setTipoEstilo(contador[indice][i].getTipoEstilo());
            vectorRes[i].setContador(resta);
            
        }
        return vectorRes;
    }
}
