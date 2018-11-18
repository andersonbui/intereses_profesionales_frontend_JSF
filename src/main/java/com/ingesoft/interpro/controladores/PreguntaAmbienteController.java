package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Pregunta;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.Respuesta;
import com.ingesoft.interpro.entidades.Usuario;
import com.ingesoft.interpro.facades.PreguntaFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
import org.primefaces.context.RequestContext;

@ManagedBean(name = "preguntaAmbienteController")
@SessionScoped
public class PreguntaAmbienteController implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int tamGrupo;
    public boolean skip;
    @EJB
    private com.ingesoft.interpro.facades.PreguntaFacade ejbFacade;
    private List<Pregunta> items = null;
    private Pregunta selected;
    private int pasoActual;
    private int numGrupos;
    private int number;
    private int puntos;
    private int[] cantidadRespuestas;
    private List<String> images;

    public PreguntaAmbienteController() {
        tamGrupo = 6;
        pasoActual = 0;
        numGrupos = 1;
        puntos = 0;
    }
    
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 6; i++) {
            images.add("1-Construir-gabinetes-de-cosina.png");
        }
    }

    public List<String> getImages() {
        return images;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
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

    public int getnombrePaso(int i) {
        return (i * 100 / numGrupos);
    }

    public boolean puedeAnteriorPaso() {
        return pasoActual > 0;
    }

    public boolean puedeSiguientePaso() {
        return pasoActual < (numGrupos);
    }

    public int anteriorPaso() {
        pasoActual -= 1;
        return pasoActual;
    }

    public int siguientePaso(ActionEvent actionEvent) {
        pasoActual += 1;
        System.out.println("siguientes paso: " + pasoActual);
        number = 0;
        return pasoActual;
    }

    public int getTamGrupo() {
        return tamGrupo;
    }

    public Pregunta getSelected() {
        return selected;
    }

    public void setSelected(Pregunta selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PreguntaFacade getFacade() {
        return ejbFacade;
    }

    public void meGusta(Respuesta respuesta) {
        respuesta.setRespuesta(1);
        reinicioUnicoPorPregunta(respuesta);
    }

    public void indiferente(Respuesta respuesta) {
        respuesta.setRespuesta((float) .5);
        reinicioUnicoPorPregunta(respuesta);
    }

    public void noMeGusta(Respuesta respuesta) {
        respuesta.setRespuesta(0);
        reinicioUnicoPorPregunta(respuesta);
    }

    public void reinicioUnicoPorPregunta(Respuesta respuesta){
        
        int indice = (respuesta.getPregunta().getOrden() - 1);
        cantidadRespuestas[indice]++;
        if (cantidadRespuestas[indice] == 1) {
            number = 0;
            puntos++;
        }
    }
    public void increment() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        number++;
        requestContext.execute("PF('knob').setValue(" + number + ")");
        if (number > 15) {
            number = 0;
            puntos--;
        }
    }
    private List<Integer> gruposPreguntas = null;

    public List<Integer> getGrupos() {
        if (gruposPreguntas == null) {
            gruposPreguntas = new ArrayList<>();
            items = getItems();
            numGrupos = items.size() / tamGrupo;
            numGrupos += (items.size() % tamGrupo == 0 ? 0 : 1);

            for (int i = 1; i <= numGrupos; i++) {
                gruposPreguntas.add(i);
            }
        }
        //System.out.println("gruposPreguntas: " + gruposPreguntas);
        return gruposPreguntas;
    }

    public List<Pregunta> getGrupoItems(int grupo) {
        getItems();
        List<Pregunta> listaPreguntas = new ArrayList<>();
        for (int i = tamGrupo * (grupo - 1); i < tamGrupo * grupo; i++) {
            if (i < items.size()) {
                listaPreguntas.add(items.get(i));
            } else {
                break;
            }
        }
        return listaPreguntas;
    }

    public Pregunta prepareCreate() {
        selected = new Pregunta();
        initializeEmbeddableKey();
        pasoActual = 0;
        return selected;
    }

    public Pregunta preparePreguntas() {
        pasoActual = 0;
        return null;
    }

    public void preparePreguntas(Usuario usuario, Encuesta encuesta) {
        gruposPreguntas = null;
        pasoActual = 0;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elOtroResolver = facesContext.getApplication().getELResolver();
        RespuestaController respuestaController = (RespuestaController) elOtroResolver.getValue(facesContext.getELContext(), null, "respuestaController");
        getItems();
        respuestaController.prepararRespuestas(items, encuesta);
        cantidadRespuestas = new int[items.size()];
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PreguntaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PreguntaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PreguntaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Pregunta> getItems() {
        if (items == null) {
            items = getFacade().findAllAmbiente();
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

    public Pregunta getPregunta(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Pregunta> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Pregunta> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Pregunta.class)
    public static class PreguntaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PreguntaAmbienteController controller = (PreguntaAmbienteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "preguntaController");
            return controller.getPregunta(getKey(value));
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
            if (object instanceof Pregunta) {
                Pregunta o = (Pregunta) object;
                return getStringKey(o.getIdPregunta());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Pregunta.class.getName()});
                return null;
            }
        }

    }

}
