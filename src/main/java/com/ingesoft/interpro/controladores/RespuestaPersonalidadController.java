package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.ElementoPersonalidad;
import com.ingesoft.interpro.entidades.RespuestaPersonalidad;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.controladores.util.Utilidades;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.EncuestaPersonalidad;
import com.ingesoft.interpro.entidades.PreguntaPersonalidad;
import com.ingesoft.interpro.entidades.RespuestaPersonalidadPK;
import com.ingesoft.interpro.entidades.TipoPersonalidad;
import com.ingesoft.interpro.entidades.RespuestaPorPersonalidad;
import com.ingesoft.interpro.facades.RespuestaPersonalidadFacade;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "respuestaPersonalidadController")
@SessionScoped
public class RespuestaPersonalidadController extends Controllers implements Serializable, EncuestaControllerInterface {

    @EJB
    private com.ingesoft.interpro.facades.RespuestaPersonalidadFacade ejbFacade;
    private List<RespuestaPersonalidad> itemsRespuestas = null;
    private RespuestaPersonalidad selected;
    private List<RespuestaPersonalidad> grupo = null;
    private boolean finalizo;
    List<RespuestaPorPersonalidad> listaResultadosPorPersonalidad;
    private List<PreguntaPersonalidad> listaPreguntas = null;
    
    int[] ORDEN_RESPUESTA_PERSONALIDAD = {2, 3, 1, 0};
    
    private final int tamGrupo;
    private int pasoActual;
    private int numGrupos;
    /**
     * contador de puntos por respuesta
     */
    int[] cantidadRespuestasXPregunta;

    public RespuestaPersonalidadController() {
        tamGrupo = 4;
        pasoActual = 0;
        numGrupos = 1;
    }

    public List<RespuestaPersonalidad> getGrupo() {
        return grupo;
    }

    public void setGrupo(List<RespuestaPersonalidad> grupo) {
        this.grupo = grupo;
    }

    public RespuestaPersonalidad getSelected() {
        return selected;
    }

    public void setSelected(RespuestaPersonalidad selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getRespuestaPersonalidadPK().setIdEncuesta(selected.getEncuestaPersonalidad().getIdEncuesta());
        selected.getRespuestaPersonalidadPK().setIdPreguntaPersonalidad(selected.getPreguntaPersonalidad().getIdPreguntaPersonalidad());
    }

    protected void initializeEmbeddableKey() {
        selected.setRespuestaPersonalidadPK(new com.ingesoft.interpro.entidades.RespuestaPersonalidadPK());
    }

    @Override
    protected RespuestaPersonalidadFacade getFacade() {
        return ejbFacade;
    }

    public RespuestaPersonalidad prepareCreate() {
        selected = new RespuestaPersonalidad();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaPersonalidadCreated"));
        if (!JsfUtil.isValidationFailed()) {
            itemsRespuestas = null;    // Invalidate list of items to trigger re-query.
        }
    }

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
        grupo = getGrupoItems(pasoActual + 1);
        return pasoActual;
    }
    public Encuesta getEncuestaActual() {
        return getEncuestaController().getSelected();
    }

    public int siguientePaso(ActionEvent actionEvent) {
        System.out.println("siguientes paso");
        pasoActual += 1;
        grupo = getGrupoItems(pasoActual + 1);
        return pasoActual;
    }

    public boolean finalizarEncuesta() {
        getEncuestaController().detenerReloj();
        if(grupo != null){
            for (RespuestaPersonalidad respuesta : grupo) {
                getFacade().edit(respuesta);
            }
        }
        pasoActual += 1;

        realizarEstadisticas();
        Encuesta encuestaActual = getEncuestaActual();
        
        String personalidad = obtenerPersonalidad(encuestaActual);
        encuestaActual.getEncuestaPersonalidad().setPersonalidad(personalidad);
        encuestaActual.getEncuestaPersonalidad().setEstado(EncuestaPersonalidad.FINALIZADA);
        encuestaActual.getEncuestaPersonalidad().setFechaFinalizada(new Date());
        
        getEncuestaController().guardarSelected();
        // realizar estadistica de respuestas
        return true;
    }
    
       /**
     *
     * @param encuestaAcutal
     * @return
     */
    private ElementoPersonalidad[] obtenerValores(Encuesta encuestaAcutal) {

        List<RespuestaPorPersonalidad> lista = getRespuestaPorPersonalidadController().buscarRespuestaPorPersonalidadPorEncuesta(encuestaAcutal);
        if (lista == null) {
            return null;
        }
        if (lista.size() != ORDEN_RESPUESTA_PERSONALIDAD.length) {
            System.out.println("lista getRespuestaPorPersonalidadList: " + lista.size());
            return null;
        }
        ElementoPersonalidad[] valores = new ElementoPersonalidad[ORDEN_RESPUESTA_PERSONALIDAD.length];
        for (RespuestaPorPersonalidad respuestaPorPersonalidad : lista) {
            int indice = respuestaPorPersonalidad.getTipoPersonalidad().getIdTipoPersonalidad() - 1;
            valores[indice] = new ElementoPersonalidad();
            valores[indice].puntaje = respuestaPorPersonalidad.getPuntaje();
            valores[indice].tipo = respuestaPorPersonalidad.getTipoPersonalidad().getTipo();
        }

        ElementoPersonalidad[] valoresAux = new ElementoPersonalidad[ORDEN_RESPUESTA_PERSONALIDAD.length];
        for (int i = 0; i < ORDEN_RESPUESTA_PERSONALIDAD.length; i++) {
            valoresAux[i] = valores[ORDEN_RESPUESTA_PERSONALIDAD[i]];
        }
        return valoresAux;
    }

    
    /**
     *
     * @param encuestas
     * @return
     */
    public String obtenerPromedioPersonalidad(List<Encuesta> encuestas) {
        ElementoPersonalidad[] valores = new ElementoPersonalidad[ORDEN_RESPUESTA_PERSONALIDAD.length];
        int cont_encuestaPerson = 0;
        for (int i = 0; i < valores.length; i++) {
            valores[i] = new ElementoPersonalidad();
            valores[i].puntaje = 0;
        }
        // suma
        for (Encuesta encuesta : encuestas) {
            ElementoPersonalidad[] valoresaux = obtenerValores(encuesta);
            if (valoresaux != null) {
                cont_encuestaPerson++;
                for (int i = 0; i < valoresaux.length; i++) {
                    valores[i].tipo = valoresaux[i].tipo;
                    valores[i].puntaje += valoresaux[i].puntaje;
                }
            }
        }
        if (cont_encuestaPerson == 0) {
            return "";
        }
        // promedio
        for (int i = 0; i < valores.length; i++) {
            valores[i].puntaje /= cont_encuestaPerson;
        }

        String personalidad = "";
        String tipo;

        for (ElementoPersonalidad indice : valores) {
            tipo = indice.tipo;
            personalidad += (indice.puntaje <= 24) ? tipo.charAt(0) : tipo.charAt(1);
        }
        return personalidad;
    }
    
    @Override
    public void reiniciar() {
        return;
    }
    /**
     *
     * @param encuesta
     * @return
     */
    private String obtenerPersonalidad(Encuesta encuesta) {
        ElementoPersonalidad[] valores = obtenerValores(encuesta);

        String personalidad = "";
        String tipo;

        for (ElementoPersonalidad indice : valores) {
            tipo = indice.tipo;
            personalidad += (indice.puntaje <= 24) ? tipo.charAt(0) : tipo.charAt(1);
        }
        return personalidad;
    }


    private boolean realizarEstadisticas() {
        // TODO falta esperar los hilos de guardado para despues realizar la estadistica
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elOtroResolver = facesContext.getApplication().getELResolver();
        RespuestaPorPersonalidadController respuestaPorPersonalidadController = (RespuestaPorPersonalidadController) elOtroResolver.getValue(
                facesContext.getELContext(), null, "respuestaPorPersonalidadController");

        Elemento[] valores = new Elemento[4];

        valores[0] = new Elemento();
        valores[1] = new Elemento();
        valores[2] = new Elemento();
        valores[3] = new Elemento();

        valores[0].valor = 18;
        valores[1].valor = 30;
        valores[2].valor = 30;
        valores[3].valor = 12;
        for (RespuestaPersonalidad respuestaPersonalidad : itemsRespuestas) {
            TipoPersonalidad tipopersonalidad = respuestaPersonalidad.getPreguntaPersonalidad().getIdTipoPersonalidad();
            int indice = tipopersonalidad.getIdTipoPersonalidad() - 1;
            valores[indice].tipoPer = tipopersonalidad;
            int signo = respuestaPersonalidad.getPreguntaPersonalidad().getSuma() ? 1 : -1;
            valores[indice].valor += signo * respuestaPersonalidad.getRespuesta();
        }
        Encuesta encuestaActual = getEncuestaActual();
        for (int i = 0; i < valores.length; i++) {
            respuestaPorPersonalidadController.prepareCreate();
            respuestaPorPersonalidadController.getSelected().setPuntaje(valores[i].valor);
            respuestaPorPersonalidadController.getSelected().setEncuestaPersonalidad(encuestaActual.getEncuestaPersonalidad());
            respuestaPorPersonalidadController.getSelected().setTipoPersonalidad(valores[i].tipoPer);
            respuestaPorPersonalidadController.create();
        }
        return true;
    }

    @Override
    public String getRuta() {
        return "/vistas/preguntaPersonalidad/preguntasPersonalidad.xhtml";
    }

    public class Elemento {
        public TipoPersonalidad tipoPer;
        public int valor;
    }

    public int getTamGrupo() {
        return tamGrupo;
    }
    
    public int getCantidadGrupos() {
        List itemsRespuest = getItemsRespuestas();
        int cantidadGrupos = 0; // numGrupos
        int tamaniogrupo = getTamGrupo();

        cantidadGrupos = itemsRespuest.size() / tamaniogrupo; 
        cantidadGrupos += (itemsRespuest.size() % tamaniogrupo == 0 ? 0 : 1);
        return cantidadGrupos;
    }
    
    public List<Integer> getGrupos() {
        List<Integer> gruposPreguntas = new ArrayList<>();
        itemsRespuestas = getItemsRespuestas();
        numGrupos = itemsRespuestas.size() / tamGrupo;
        numGrupos += (itemsRespuestas.size() % tamGrupo == 0 ? 0 : 1);
        for (int i = 1; i <= numGrupos; i++) {
            gruposPreguntas.add(i);
        }
        return gruposPreguntas;
    }

    
    public List<RespuestaPersonalidad> actualizarTodasRespuestas() throws IOException {
        for (RespuestaPersonalidad item : itemsRespuestas) {
            this.getFacade().edit(item);
        }
        pasoActual = (numGrupos - 1);
        finalizarEncuesta();
        return itemsRespuestas;
    }
    
    /**
     * obtiene las respuestas de un determinado grupo
     *
     * @param numGrupo
     * @return
     */
    public List<RespuestaPersonalidad> getGrupoItems(int numGrupo) {
        getItemsRespuestas();
        // guardar respuestas actuales
        if (grupo != null && !grupo.isEmpty()) {
            HiloGuardado hilo = new HiloGuardado(grupo);
            hilo.start();
        }
        List<RespuestaPersonalidad> listaRespuestas = null;
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

    public void seleccionarPunto(RespuestaPersonalidad respuestaPersonalidad) {
        int posicion = respuestaPersonalidad.getPreguntaPersonalidad().getOrden() - 1;
        if (cantidadRespuestasXPregunta[posicion] == 0) {
            getEncuestaController().aumentarPuntos();
            getEncuestaController().setTiempo(0);//Number(0);
        }
        cantidadRespuestasXPregunta[posicion]++;
    }

    /**
     * 
     * @return 
     */
    private void setListaPreguntas(List<PreguntaPersonalidad> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }
    
    /**
     * 
     * @return 
     */
    private List<PreguntaPersonalidad> getListaPreguntas() {
        return listaPreguntas;
    }
    
    public List<RespuestaPersonalidad> obtenerTodosPorEncuesta(Encuesta encuesta) {
        return getFacade().obtenerTodosPorEncuesta(encuesta);
    }
    
    @Override
    public void prepararEncuesta(Encuesta encuesta) {
        
        reiniciar();
        
        List<PreguntaPersonalidad> preguntas = getPreguntaPersonalidadController().getItems();
        setListaPreguntas(preguntas);
        
        finalizo = false;
        listaResultadosPorPersonalidad = null;
        prepararRespuestasAux();
    }
    
    private List<RespuestaPersonalidad> prepararRespuestasAux() {
        List<PreguntaPersonalidad> preguntas = getListaPreguntas();
        
        if (itemsRespuestas == null && (preguntas = getListaPreguntas()) != null) {
            itemsRespuestas = new ArrayList<>(preguntas.size());
            cantidadRespuestasXPregunta = new int[preguntas.size()];
            Encuesta encuesta = getEncuestaActual();
            RespuestaPersonalidad unaRespAmb;
            List<RespuestaPersonalidad> items_recuperados = obtenerTodosPorEncuesta(encuesta);
            
            int contPuntosRecuperados = 0;
            if(encuesta.getPuntajeEncuesta() != null && encuesta.getPuntajeEncuesta() >= 0) {
                contPuntosRecuperados = encuesta.getPuntajeEncuesta();
            }
            
            EncuestaPersonalidad epe = getEncuestaPersonalidadController().crearEncuestaPersonalidad(encuesta);
            
            for (PreguntaPersonalidad pregunta : preguntas) {
                unaRespAmb = new RespuestaPersonalidad();
                unaRespAmb.setPreguntaPersonalidad(pregunta);
                unaRespAmb.setEncuestaPersonalidad(epe);
                unaRespAmb.setRespuestaPersonalidadPK(new RespuestaPersonalidadPK(pregunta.getIdPreguntaPersonalidad(), epe.getIdEncuesta()));
                
                Integer respuesta = Integer.MIN_VALUE;
                if (items_recuperados != null && !items_recuperados.isEmpty()) {
                    int indice = items_recuperados.indexOf(unaRespAmb);
                    if (indice >= 0) {
                        int i = (pregunta.getOrden() - 1);
                        cantidadRespuestasXPregunta[i]++;
                        respuesta = items_recuperados.get(indice).getRespuesta();
                    }
                }
                // @desarrollo - respuetas para desarrollo
                if (respuesta == Integer.MIN_VALUE && Utilidades.esDesarrollo()) {
                    Random rand = new Random(Calendar.getInstance().getTimeInMillis());
                    respuesta = 1 + rand.nextInt(5);
                    getEncuestaController().addPuntos_eval(1);
                }// @end
                /** 
                 * si se consigue una respuesta almacenado o 
                 * aleatorio(desarrollo), entonces, agregarlo a la pregunta 
                 */
                if(respuesta != Integer.MIN_VALUE){
                    unaRespAmb.setRespuesta(respuesta);
                }
                itemsRespuestas.add(unaRespAmb);
            }

            getGrupos();
            //ubicar la encuesta en la ultima pagina respondida
            if (items_recuperados != null && !items_recuperados.isEmpty()) {
                int imagenesXpagina = tamGrupo;
                int paginaActual = (items_recuperados.size() / imagenesXpagina);
                if(paginaActual == numGrupos) {
                    pasoActual = numGrupos;
                    finalizarEncuesta();
                }
                pasoActual = paginaActual;
            } else {
                pasoActual = 0;
            }
            encuesta.setPuntajeEncuesta(contPuntosRecuperados);
            grupo = getGrupoItems(pasoActual + 1);
        }
        return itemsRespuestas;
    }

    public class HiloGuardado extends Thread {

        private final List<RespuestaPersonalidad> itemsRespuestas;

        public HiloGuardado(List<RespuestaPersonalidad> itemsRespuestas) {
            this.itemsRespuestas = itemsRespuestas;
        }

        @Override
        public void run() {
            for (RespuestaPersonalidad respuesta : itemsRespuestas) {
                getFacade().edit(respuesta);
            }
            System.out.println("----Termino de guardar Respuestas");
        }

    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaPersonalidadUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RespuestaPersonalidadDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            itemsRespuestas = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RespuestaPersonalidad> getItemsRespuestas() {
        if (itemsRespuestas == null) {
            itemsRespuestas = getFacade().findAll();
        }
        return itemsRespuestas;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
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
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getName() {
        return "Personalidad";
    }
    
    public RespuestaPersonalidad getRespuestaPersonalidad(com.ingesoft.interpro.entidades.RespuestaPersonalidadPK id) {
        return getFacade().find(id);
    }

    public List<RespuestaPersonalidad> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RespuestaPersonalidad> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RespuestaPersonalidad.class)
    public static class RespuestaPersonalidadControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RespuestaPersonalidadController controller = (RespuestaPersonalidadController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "respuestaPersonalidadController");
            return controller.getRespuestaPersonalidad(getKey(value));
        }

        com.ingesoft.interpro.entidades.RespuestaPersonalidadPK getKey(String value) {
            com.ingesoft.interpro.entidades.RespuestaPersonalidadPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.ingesoft.interpro.entidades.RespuestaPersonalidadPK();
            key.setIdPreguntaPersonalidad(Integer.parseInt(values[0]));
            key.setIdEncuesta(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(com.ingesoft.interpro.entidades.RespuestaPersonalidadPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdPreguntaPersonalidad());
            sb.append(SEPARATOR);
            sb.append(value.getIdEncuesta());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RespuestaPersonalidad) {
                RespuestaPersonalidad o = (RespuestaPersonalidad) object;
                return getStringKey(o.getRespuestaPersonalidadPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RespuestaPersonalidad.class.getName()});
                return null;
            }
        }

    }

}
