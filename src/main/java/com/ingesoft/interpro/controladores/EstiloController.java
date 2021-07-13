package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.Contador;
import com.ingesoft.interpro.entidades.RespuestaEstilo;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.controladores.util.Utilidades;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.EncuestaEstilosAprendizaje;
import com.ingesoft.interpro.entidades.PreguntaEstilosAprendizaje;
import com.ingesoft.interpro.entidades.TipoEstiloPregunta;
import com.ingesoft.interpro.facades.EncuestaEstilosAprendizajeFacade;
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
public class EstiloController extends Controllers implements Serializable, EncuestaControllerInterface {

    @EJB
    private com.ingesoft.interpro.facades.EncuestaEstilosAprendizajeFacade ejbFacade;
    private EncuestaEstilosAprendizaje selected;
    
    private List<RespuestaEstilo> grupo = null;
    private List<RespuestaEstilo> itemsRespuestas = null;
    private List<PreguntaEstilosAprendizaje> listaPreguntas = null;
    
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
    
    Contador[] estadisticaEncuentaEstiloApren;
    
    public EstiloController() {
        tamGrupo = 3;
        pasoActual = 0;
        numGrupos = 0;
    }

    public void inicializar(Encuesta selected) {

    }

    public int getNumGrupos() {
        itemsRespuestas = getRespuestas();
        numGrupos = itemsRespuestas.size() / tamGrupo;
        numGrupos += (itemsRespuestas.size() % tamGrupo == 0 ? 0 : 1);
        return numGrupos;
    }
    
    public List<RespuestaEstilo> getGrupo() {
        return grupo;
    }

    private void setGrupo(List<RespuestaEstilo> grupo) {
        this.grupo = grupo;
    }

    public EncuestaEstilosAprendizaje getSelected() {
        return selected;
    }

    public void setSelected(EncuestaEstilosAprendizaje selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    protected EncuestaEstilosAprendizajeFacade getFacade() {
        return ejbFacade;
    }

    public EncuestaEstilosAprendizaje prepareCreate() {
        selected = new EncuestaEstilosAprendizaje();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EncuestaEstiloCreated"), selected);
        if (!JsfUtil.isValidationFailed()) {
            itemsRespuestas = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EncuestaEstiloUpdated"), selected);
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EncuestaEstiloDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            itemsRespuestas = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RespuestaEstilo> getRespuestas() {
        if (itemsRespuestas == null) {
            if(encuesta == null){
                this.encuesta = getEncuestaController().getSelected();
            }
            if(listaPreguntas == null){
                listaPreguntas = getPreguntaEstilosAprendizajeController().getItems();
            }
            int contPuntosRecuperados = 0;
            if(encuesta.getPuntajeEncuesta() != null && encuesta.getPuntajeEncuesta() >= 0) {
                contPuntosRecuperados = encuesta.getPuntajeEncuesta();
            }
            int tamListPreguntas = listaPreguntas.size();
            itemsRespuestas = new ArrayList<>(tamListPreguntas);
            vecContadorRespuestasEstiloApren =  new int[tamListPreguntas];
            vecContadorRespuestasEstiloApren = new int[tamListPreguntas]; // puntos
            if(encuesta.getEncuestaEstilosAprendizaje() == null){
                selected = new EncuestaEstilosAprendizaje(this.encuesta);
//                this.update();
                this.encuesta.setEncuestaEstilosAprendizaje(selected);
                getEncuestaController().update();
                this.encuesta = getEncuestaController().getSelected();
            } else {
                 selected = encuesta.getEncuestaEstilosAprendizaje();
            }
            List<RespuestaEstilo> items_recuperados = getRespuestaEstilosControllerController().obtenerTodosPorEncuesta(selected);
            RespuestaEstilo unaRespuestaEstilo;
            for (PreguntaEstilosAprendizaje pregunta : listaPreguntas) {
                unaRespuestaEstilo = new RespuestaEstilo(pregunta, selected);
                Character respuesta = null;
                if (items_recuperados != null && !items_recuperados.isEmpty()) {
                    int indice = items_recuperados.indexOf(unaRespuestaEstilo);
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
                    unaRespuestaEstilo.setRespuesta(respuesta);
                }
                itemsRespuestas.add(unaRespuestaEstilo);
            }
            encuesta.setPuntajeEncuesta(contPuntosRecuperados);
        }
        
        //ubicar la encuesta en la ultima pagina respondida
        // TODO
        
        return itemsRespuestas;
    }
    
    public EncuestaEstilosAprendizaje getRespuestaEstilo(Integer id) {
        return getFacade().find(id);
    }

    public List<EncuestaEstilosAprendizaje> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EncuestaEstilosAprendizaje> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public Contador[] getEstadisticaEncuentaEstiloApren() {
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
            HiloGuardado hilo = new HiloGuardado(listaRespuestas, getRespuestaEstilosControllerController());
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
        itemsRespuestas = getRespuestas();
        
        guardarRespuestas(grupo);
        
        List<RespuestaEstilo> listaRespuestas = null;
        if (itemsRespuestas != null) {
            listaRespuestas = new ArrayList<>();
            for (int i = tamGrupo * (numGrupo - 1); i < tamGrupo * numGrupo; i++) {
                if (i >= 0 && i < itemsRespuestas.size()) {
                    listaRespuestas.add(itemsRespuestas.get(i));
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
    }

    @Override
    public void prepararEncuesta(Encuesta encuesta) {
        listaPreguntas = getPreguntaEstilosAprendizajeController().getItems();
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
        RespuestaEstilosController estilosControleler;

        public HiloGuardado(List<RespuestaEstilo> itemsRespuestas, RespuestaEstilosController estilosControleler) {
            this.itemsRespuestas = itemsRespuestas;
            this.estilosControleler = estilosControleler;
        }

        @Override
        public void run() {
            for (RespuestaEstilo respuesta : itemsRespuestas) {
                this.estilosControleler.getFacade().edit(respuesta);
            }
            System.out.println("Termino de guardar Respuestas Estilo A");
        }

    }
    
    @FacesConverter(forClass = EncuestaEstilosAprendizaje.class)
    public static class RespuestaEstiloControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EstiloController controller = (EstiloController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "estiloController");
            return controller.getRespuestaEstilo(getKey(value));
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
            if (object instanceof EncuestaEstilosAprendizaje) {
                EncuestaEstilosAprendizaje o = (EncuestaEstilosAprendizaje) object;
                return getStringKey(o.getIdEncuesta());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EncuestaEstilosAprendizaje.class.getName()});
                return null;
            }
        }

    }
    
    /**
     * Funciones de encuesta
     * @return 
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
    
    @Override
    public String getName() {
        return "Estilo aprendizaje";
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
        // colocar como finalizada y guarda cambios
        getSelected().setEstado(EncuestaEstilosAprendizaje.FINALIZADA);
        update();
        
        estadisticaEncuentaEstiloApren = estadisticaEncuesta(encuesta.getEncuestaEstilosAprendizaje());
        
        pasoActual += 1;
        getEncuestaController().finalizarEncuesta();
        return true;
    }
    
        
    public List<RespuestaEstilo> actualizarTodasRespuestas() throws IOException, InterruptedException {
        RespuestaEstilosController estilosControleler = getRespuestaEstilosControllerController();
        estilosControleler.actualizarTodasRespuestas(itemsRespuestas);
        pasoActual = (numGrupos - 1);
        finalizarEncuesta();
        return itemsRespuestas;
    }
    
    /**
     * Calcula Estadistica de tipo estilo de una encuesta
     * @param encuesta
     * @return 
     */
    public Contador[] estadisticaEncuesta(EncuestaEstilosAprendizaje encuesta){
        this.itemsRespuestas = getRespuestaEstilosControllerController().getItemsXEncuesta(encuesta);
        
        if(this.itemsRespuestas == null ){
            return null;
        }
        Contador[][] contador = new Contador[2][4]; /** 8 es la cantidad de tipos de estilo */
        contador[0] = new Contador[4];
        contador[1] = new Contador[4];
        int indice;
        int columna;
        int fila;
        
        /** Sumatoria de tipos de estilo de las respuestas */
        for (RespuestaEstilo item : this.itemsRespuestas) {
            PreguntaEstilosAprendizaje pregunta = item.getIdpreguntaEstilos();
            List<TipoEstiloPregunta> listaTiposEstiloPregunta = pregunta.getTipoEstiloPreguntaList();
            TipoEstiloPregunta obj = (listaTiposEstiloPregunta.get(0).getIndice().equals(item.getRespuesta()))?listaTiposEstiloPregunta.get(0):listaTiposEstiloPregunta.get(1);
                 
            indice = obj.getTipoEstilo().getId()-1;
            columna = indice % 2;
            fila = indice / 2;
            if(contador[columna][fila] == null) {
                contador[columna][fila] = new Contador();
            }
            contador[columna][fila].aumentarContador();
            contador[columna][fila].setTipo(obj.getTipoEstilo());
            
        }
        Contador[] vectorRes = new Contador[4];
        int resta;
        /** Calculo de grupos de tipos de estilo */
        for (int i = 0; i < 4; i++) {
            
            indice = contador[0][i].getContador() > contador[1][i].getContador() ? 0 : 1;
            resta = Math.abs(contador[0][i].getContador() - contador[1][i].getContador());
            
            if(vectorRes[i] == null) {
                vectorRes[i] = new Contador();
            }
            vectorRes[i].setTipo(contador[indice][i].getTipo());
            vectorRes[i].setContador(resta);
            
        }
        return vectorRes;
    }

}
