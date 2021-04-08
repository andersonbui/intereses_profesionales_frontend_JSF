package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.RespuestaEstilo;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.controladores.util.Utilidades;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.PreguntaEstilosAprendizajeFs;
import com.ingesoft.interpro.facades.RespuestaEstiloFacade;

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

@ManagedBean(name = "respuestaEstiloController")
@SessionScoped
public class RespuestaEstiloController extends Controller implements Serializable {

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
    
    Encuesta encuesta;
    
    public RespuestaEstiloController() {
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

    public List<RespuestaEstilo> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public List<RespuestaEstilo> getRespuestas() {
        if (items == null) {
            items = new ArrayList<>(listaPreguntas.size());
//            vecContRespuestasPersonalidad = new int[listaPreguntas.size()]; // puntos
            for (PreguntaEstilosAprendizajeFs pregunta : listaPreguntas) {
                selected = new RespuestaEstilo(pregunta, this.encuesta);
                
                if (Utilidades.esDesarrollo()) {
                    char valuerand = ((new Random()).nextBoolean())? 'A': 'B';
                    selected.setRespuesta(valuerand);
                }
                items.add(selected);
            }
        }
        return items;
    }
    
    public List<RespuestaEstilo> getItemsXEncuesta(Encuesta encuesta) {
        return getFacade().getItemsXEncuesta(encuesta);
    }
    
    public RespuestaEstilo getRespuestaEstilo(Integer id) {
        return getFacade().find(id);
    }

    public List<RespuestaEstilo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RespuestaEstilo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    void prepararRespuestas(List<PreguntaEstilosAprendizajeFs> itemsPreg, Encuesta encuesta) {
        listaPreguntas = itemsPreg;
        this.encuesta = encuesta;
        pasoActual = 0;
        
        getNumGrupos();
        grupo = getGrupoItems(pasoActual + 1);
    }

//    public List<Integer> getGrupos() {
////        List<Integer> gruposPreguntas = new ArrayList<>();
////        for (int i = 1; i <= numGrupos; i++) {
////            gruposPreguntas.add(i);
////        }
////        return gruposPreguntas;
//        return null;
//    }
    
    /**
     * obtiene las respuestas de un determinado grupo
     *
     * @param numGrupo
     * @return
     */
    public List<RespuestaEstilo> getGrupoItems(int numGrupo) {
        items = getRespuestas();
        // guardar respuestas actuales
        if (grupo != null && !grupo.isEmpty()) {
            HiloGuardado hilo = new HiloGuardado(grupo);
            hilo.start();
        }
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
    
    public class HiloGuardado extends Thread {

        private final List<RespuestaEstilo> itemsRespuestas;

        public HiloGuardado(List<RespuestaEstilo> itemsRespuestas) {
            this.itemsRespuestas = itemsRespuestas;
        }

        @Override
        public void run() {
            RespuestaEstilo respAux;
            for (RespuestaEstilo respuesta : itemsRespuestas) {
                respAux = getFacade().edit(respuesta);
                respuesta.setIdRespuestaEstilo(respAux.getIdRespuestaEstilo());
            }
            System.out.println("Termino de guardar Respuestas Estilo A");
        }

    }
    
    @FacesConverter(forClass = RespuestaEstilo.class)
    public static class RespuestaEstiloControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RespuestaEstiloController controller = (RespuestaEstiloController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "respuestaEstiloController");
            return controller.getRespuestaEstilo(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RespuestaEstilo) {
                RespuestaEstilo o = (RespuestaEstilo) object;
                return getStringKey(o.getIdRespuestaEstilo());
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

    public boolean finalizarEncuesta() {
//        for (RespuestaEstilo respuesta : grupo) {
//            getFacade().edit(respuesta);
//        }
//        pasoActual += 1;

        // realizar estadistica de respuestas
//        return realizarEstadisticas();
        return true;
    }
}
