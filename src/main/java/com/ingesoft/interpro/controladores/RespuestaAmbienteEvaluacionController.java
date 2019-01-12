package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.RespuestaAmbiente;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.controladores.util.Variables;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.Evaluacion;
import com.ingesoft.interpro.entidades.PreguntaAmbiente;
import com.ingesoft.interpro.entidades.ResultadoPorAmbiente;
import com.ingesoft.interpro.entidades.TipoAmbiente;
import com.ingesoft.interpro.facades.RespuestaAmbienteFacade;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "respuestaAmbienteEvaluacionController")
@SessionScoped
public class RespuestaAmbienteEvaluacionController implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.RespuestaAmbienteFacade ejbFacade;
    private List<RespuestaAmbiente> items = null;
    private RespuestaAmbiente selected;

    private final int tamGrupo;
    private int pasoActual;
    private int numGrupos;
    private int number;
    private int puntos;
    private int[] cantidadRespuestas;
    private List<Integer> gruposPreguntas;
    private List<String> images;
    private List<RespuestaAmbiente> grupo = null;
    Encuesta EncuestaAcutal;
    private boolean finalizo;
    List<ResultadoPorAmbiente> listaResultadosPorAmbiente;
    List<Evaluacion> listaEvaluacionItem;
    List<TipoAmbiente> listaTipoAmbienteItem;
    List<String> listaEnunciado;
    private boolean isEvaluacion;
    int activeIndex = 0;

    String definicion;
    String enunciado;
    List<String> opciones;
    int correcta;
    int cont;

    public RespuestaAmbienteEvaluacionController() {
        cont = 0;
        tamGrupo = 6;
        pasoActual = -1;
        numGrupos = 1;
        puntos = 0;
        gruposPreguntas = null;
        listaResultadosPorAmbiente = null;
        listaEnunciado = new ArrayList<>();
        getItemPreguntaEvaluacion();
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public List<String> getListaEnunciado() {
        return listaEnunciado;
    }

    public void setListaEnunciado(List<String> listaEnunciado) {
        this.listaEnunciado = listaEnunciado;
    }

    public boolean isIsEvaluacion() {
        return isEvaluacion;
    }

    public void setIsEvaluacion(boolean isEvaluacion) {
        this.isEvaluacion = isEvaluacion;
    }

    public List<ResultadoPorAmbiente> getListaResultadoPorAmbiente() {
        List<ResultadoPorAmbiente> listaResultadosPorValorAmbiente = new ArrayList();
        for (int i = 0; i < 3; i++) {
            listaResultadosPorValorAmbiente.add(listaResultadosPorAmbiente.get(i));
        }
        return listaResultadosPorValorAmbiente;
    }

    public boolean isFinalizo() {
        return finalizo;
    }

    public void setFinalizo(boolean finalizo) {
        this.finalizo = finalizo;
    }

    public List<RespuestaAmbiente> getGrupo() {
        return grupo;
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

    public int getTamGrupo() {
        return tamGrupo;
    }

    public void reinicioUnicoPorPregunta(RespuestaAmbiente respuesta) {
        int indice = (respuesta.getPreguntaAmbiente().getOrden() - 1);
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

    public int finalizarEncuesta(ActionEvent actionEvent) {
//        grupo = getGrupoItems(pasoActual + 1);
        for (RespuestaAmbiente respuesta : grupo) {
            getFacade().edit(respuesta);
        }
        pasoActual += 1;
        finalizo = true;
        // realizar estadistica de respuestas
        return pasoActual;
    }

    private class Elemento {

        public TipoAmbiente tipoPer;
        public double valor;
    }

    public List<Integer> getGrupos() {
        gruposPreguntas = null;
        if (gruposPreguntas == null) {
            gruposPreguntas = new ArrayList<>();
            items = getItems();
            numGrupos = items.size() / tamGrupo;
            numGrupos += (items.size() % tamGrupo == 0 ? 0 : 1);
            numGrupos = 6;
            for (int i = 1; i <= numGrupos; i++) {
                gruposPreguntas.add(i);
            }
            System.out.println("gruposPreguntas: " + gruposPreguntas);
        }
        return gruposPreguntas;
    }

    public RespuestaAmbiente getSelected() {
        return selected;
    }

    public void setSelected(RespuestaAmbiente selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getRespuestaAmbientePK().setIdPreguntasAmbiente(selected.getPreguntaAmbiente().getIdPreguntaAmbiente());
        selected.getRespuestaAmbientePK().setIdEncuesta(selected.getEncuesta().getIdEncuesta());
    }

    protected void initializeEmbeddableKey() {
        selected.setRespuestaAmbientePK(new com.ingesoft.interpro.entidades.RespuestaAmbientePK());
    }

    private RespuestaAmbienteFacade getFacade() {
        return ejbFacade;
    }

    public RespuestaAmbiente prepareCreate() {
        selected = new RespuestaAmbiente();
        initializeEmbeddableKey();
        return selected;
    }

    public List<RespuestaAmbiente> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<RespuestaAmbiente> getTodasImages() {
        List<RespuestaAmbiente> listaRespuestas;
        listaRespuestas = null;
        if (items != null) {
            listaRespuestas = new ArrayList<>();
            for (int i = 1; i < items.size(); i++) {
                items.get(i).getPreguntaAmbiente().getOrden();
                listaRespuestas.add(items.get(i));
            }
        }
        return listaRespuestas;
    }

    public List<RespuestaAmbiente> actualizarRespuestas() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/intereses_profesionales_frontend_JSF/faces/vistas/encuesta/resumen.xhtml");
        for (RespuestaAmbiente item : items) {
            this.getFacade().edit(item);
        }
        return items;
    }

    public RespuestaAmbiente getRespuestaAmbiente(com.ingesoft.interpro.entidades.RespuestaAmbientePK id) {
        return getFacade().find(id);
    }

    public List<RespuestaAmbiente> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RespuestaAmbiente> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public int siguientePaso(ActionEvent actionEvent) {
        System.out.println("siguientes paso: " + pasoActual);
        if (pasoActual == -1) {
            cont++;
        }
        pasoActual += 1;
        number = 0;
        return pasoActual;
    }

    public void getItemPreguntaEvaluacion() {
        ArrayList<String> listaOpciones;
        FacesContext facesContext = FacesContext.getCurrentInstance();

        EvaluacionController evaluacionController = (EvaluacionController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "evaluacionController");
        listaEvaluacionItem = evaluacionController.getItems();
        enunciado = listaEvaluacionItem.get(cont).getEnunciado();

        TipoAmbienteController tipoAmbienteControlle = (TipoAmbienteController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "tipoAmbienteController");
        listaTipoAmbienteItem = tipoAmbienteControlle.getItems();
        definicion = listaTipoAmbienteItem.get(cont).getDefinicion();

        listaOpciones = new ArrayList();
        listaOpciones.add("Trabajar con plantas y animales");
        listaOpciones.add("Materiales reales, tales como madera, herramientas y maquinaria");
        listaOpciones.add("Trabajo al aire libres");
        listaOpciones.add("Les gusta trabajar con teorías científicas");
        listaOpciones.add("Les gusta la composición musical");
        opciones = listaOpciones;
        correcta = 0;
    }

    public class Definicion {

        String definicion;

        public Definicion(String definicion) {
            this.definicion = definicion;
        }

        public String getDefinicion() {
            return definicion;
        }

        public void setDefinicion(String definicion) {
            this.definicion = definicion;
        }

    }

    public String getDefinicion() {
        return definicion;
    }

    public void setDefinicion(String definicion) {
        this.definicion = definicion;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    public int getCorrecta() {
        return correcta;
    }

    public void setCorrecta(int correcta) {
        this.correcta = correcta;
    }

}