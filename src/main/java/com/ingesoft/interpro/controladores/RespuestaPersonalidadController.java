package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.RespuestaPersonalidad;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.controladores.util.Utilidades;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.PreguntaPersonalidad;
import com.ingesoft.interpro.entidades.RespuestaAmbiente;
import com.ingesoft.interpro.entidades.TipoPersonalidad;
import com.ingesoft.interpro.facades.RespuestaPersonalidadFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
public class RespuestaPersonalidadController extends Controller implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.RespuestaPersonalidadFacade ejbFacade;
    private List<RespuestaPersonalidad> items = null;
    private RespuestaPersonalidad selected;
    private List<RespuestaPersonalidad> grupo = null;
    Encuesta EncuestaAcutal;

    private final int tamGrupo;
    private int pasoActual;
    private int numGrupos;
    int[] vecContRespuestasPersonalidad;

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
        selected.getRespuestaPersonalidadPK().setIdEncuesta(selected.getEncuesta().getIdEncuesta());
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
            items = null;    // Invalidate list of items to trigger re-query.
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

    public int siguientePaso(ActionEvent actionEvent) {
        System.out.println("siguientes paso");
        pasoActual += 1;
        grupo = getGrupoItems(pasoActual + 1);
        return pasoActual;
    }

    public String finalizarEncuesta() {
//        grupo = getGrupoItems(pasoActual + 1);
        for (RespuestaPersonalidad respuesta : grupo) {
            getFacade().edit(respuesta);
        }
        pasoActual += 1;

        // realizar estadistica de respuestas
        return realizarEstadisticas();
    }

    private String realizarEstadisticas() {
        // TODO falta esperar los hilos de guardado para despues realizar la estadistica
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elOtroResolver = facesContext.getApplication().getELResolver();
        RespuestaPorPersonalidadController respuestaPorPersonalidadController = (RespuestaPorPersonalidadController) elOtroResolver.getValue(facesContext.getELContext(), null, "respuestaPorPersonalidadController");

        Elemento[] valores = new Elemento[4];

        valores[0] = new Elemento();
        valores[1] = new Elemento();
        valores[2] = new Elemento();
        valores[3] = new Elemento();

        valores[0].valor = 18;
        valores[1].valor = 30;
        valores[2].valor = 30;
        valores[3].valor = 12;
        for (RespuestaPersonalidad respuestaPersonalidad : items) {
            TipoPersonalidad tipopersonalidad = respuestaPersonalidad.getPreguntaPersonalidad().getIdTipoPersonalidad();
            int indice = tipopersonalidad.getIdTipoPersonalidad() - 1;
            valores[indice].tipoPer = tipopersonalidad;
            int signo = respuestaPersonalidad.getPreguntaPersonalidad().getSuma() ? 1 : -1;
            valores[indice].valor += signo * respuestaPersonalidad.getRespuesta();
        }
        for (int i = 0; i < valores.length; i++) {
            respuestaPorPersonalidadController.prepareCreate();
            respuestaPorPersonalidadController.getSelected().setPuntaje(valores[i].valor);
            respuestaPorPersonalidadController.getSelected().setEncuesta(EncuestaAcutal);
            respuestaPorPersonalidadController.getSelected().setTipoPersonalidad(valores[i].tipoPer);
            respuestaPorPersonalidadController.create();
        }
        return obtenerPersonalidad(valores);
    }

    private String obtenerPersonalidad(Elemento[] valores) {
        String personalidad = "";
        String perso;

        perso = valores[2].tipoPer.getTipo();
        personalidad += (valores[2].valor <= 24) ? perso.charAt(0) : perso.charAt(1);

        perso = valores[3].tipoPer.getTipo();
        personalidad += (valores[3].valor <= 24) ? perso.charAt(0) : perso.charAt(1);

        perso = valores[1].tipoPer.getTipo();
        personalidad += (valores[1].valor <= 24) ? perso.charAt(0) : perso.charAt(1);

        perso = valores[0].tipoPer.getTipo();
        personalidad += (valores[0].valor <= 24) ? perso.charAt(0) : perso.charAt(1);
        System.out.println("personalidad: " + personalidad);
        return personalidad;
    }

    private class Elemento {

        public TipoPersonalidad tipoPer;
        public int valor;
    }

    public int getTamGrupo() {
        return tamGrupo;
    }

    public List<Integer> getGrupos() {
        //System.out.println("Hola mundo");
        List<Integer> gruposPreguntas = new ArrayList<>();
        items = getItems();
//        System.out.println("items: " + items);
        numGrupos = items.size() / tamGrupo;
        numGrupos += (items.size() % tamGrupo == 0 ? 0 : 1);
//        numGrupos = 3;
        for (int i = 1; i <= numGrupos; i++) {
            gruposPreguntas.add(i);
        }
        //System.out.println("gruposPreguntas: " + gruposPreguntas);
        return gruposPreguntas;
    }

    /**
     * obtiene las respuestas de un determinado grupo
     *
     * @param numGrupo
     * @return
     */
    public List<RespuestaPersonalidad> getGrupoItems(int numGrupo) {
        getItems();
        // guardar respuestas actuales
        if (grupo != null && !grupo.isEmpty()) {
            HiloGuardado hilo = new HiloGuardado(grupo);
            hilo.start();
        }
        List<RespuestaPersonalidad> listaRespuestas = null;
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

    public void seleccionarPunto(RespuestaPersonalidad respuestaPersonalidad) {
        int posicion = respuestaPersonalidad.getPreguntaPersonalidad().getOrden() - 1;
        if (vecContRespuestasPersonalidad[posicion] == 0) {
            getEncuestaController().aumentarPuntos();
            getEncuestaController().setTiempo(0);//Number(0);
        }
        vecContRespuestasPersonalidad[posicion]++;
        System.out.println("hola accion;" + respuestaPersonalidad);
    }

    public List<RespuestaPersonalidad> prepararRespuestas(List<PreguntaPersonalidad> preguntas, Encuesta encuesta) {
        System.out.println("encuesta: " + encuesta);
        System.out.println("preguntas: " + preguntas);
        EncuestaAcutal = encuesta;
        items = new ArrayList<>(preguntas.size());
        vecContRespuestasPersonalidad = new int[preguntas.size()];
        for (PreguntaPersonalidad pregunta : preguntas) {
            selected = new RespuestaPersonalidad(pregunta.getIdPreguntaPersonalidad(), encuesta.getIdEncuesta());
            selected.setPreguntaPersonalidad(pregunta);
            selected.setEncuesta(encuesta);
            items.add(selected);
        }

        for (int i = 0; i < vecContRespuestasPersonalidad.length; i++) {
            vecContRespuestasPersonalidad[i] = 0;
        }

        // @desarrollo
        if (Utilidades.esDesarrollo()) {
//            System.out.println("encuesta: " + encuesta);
            Random rand = new Random(Calendar.getInstance().getTimeInMillis());
            for (RespuestaPersonalidad item : items) {
                item.setRespuesta(1 + rand.nextInt(5));
            }
        }// @end
        getGrupos();
        pasoActual = 0;
        grupo = getGrupoItems(pasoActual + 1);

        return items;
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
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RespuestaPersonalidad> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
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
