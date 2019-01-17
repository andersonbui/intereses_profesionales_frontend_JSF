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
import com.ingesoft.interpro.entidades.Usuario;
import com.ingesoft.interpro.facades.RespuestaAmbienteFacade;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.el.ELResolver;
import javax.faces.application.FacesMessage;
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
public class RespuestaAmbienteEvaluacionController extends Controller implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.RespuestaAmbienteFacade ejbFacade;
    private List<RespuestaAmbiente> items = null;
    private RespuestaAmbiente selected;
    private PreguntaAmbiente selectedPregunta;
    private List<String> listaPreguntasAmbiente = null;
    private List<String> listaPreguntasAmbienteSeleccionada = null;

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
    int indiceActual;
    boolean respuesta;
    int contAmbiente = 0;
    int puntosPreguntaAmbiente = 0;

    int[] vecIdImages = {1, 3, 5, 7, 9, 11, 31, 33, 35, 37, 39, 41, 61, 63, 65, 67, 69, 71, 91, 93, 95, 97, 99, 101, 121, 123, 125, 127, 129, 131, 151, 153, 155, 157, 159, 161};
    int[] vecIdPreguntas = {1, 2, 5, 13, 14, 37, 38, 61, 39, 86, 40, 42, 43, 45, 48, 72, 31, 32, 43, 44, 73, 75, 78, 130, 139, 158, 160, 162, 167, 165};

    public RespuestaAmbienteEvaluacionController() {
        cont = 0;
        tamGrupo = 5;
        pasoActual = -1;
        numGrupos = 1;
        puntos = 0;
        gruposPreguntas = null;
        listaPreguntasAmbiente = new ArrayList<>();
        listaPreguntasAmbienteSeleccionada = new ArrayList<>();
        listaEnunciado = new ArrayList<>();
        indiceActual = 0;
    }

    @PostConstruct
    public void init() {
        reiniciarEvaluacion();
        getItemPreguntaEvaluacion();
        obtenerPreguntasAmbiente();
        selectedPregunta = getPreguntaAmbiente(vecIdImages[indiceActual]);
    }

    private PreguntaAmbiente getPreguntaAmbiente(java.lang.Integer id) {
        return getPreguntaAmbienteController().getPreguntaAmbiente(id);
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

    public List<String> getListaPreguntasAmbiente() {
        return listaPreguntasAmbiente;
    }

    public void setListaPreguntasAmbiente(List<String> listaPreguntasAmbiente) {
        this.listaPreguntasAmbiente = listaPreguntasAmbiente;
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

    public void reiniciarEvaluacion(){
        cont = 0;
        pasoActual = -1;
        puntos = 0;
        gruposPreguntas = null;
        indiceActual = 0;
    } 
    
    public int[] getVecIdImages() {
        return vecIdImages;
    }

    public void setVecIdImages(int[] vecIdImages) {
        this.vecIdImages = vecIdImages;
    }

    public boolean isRespuesta() {
        return respuesta;
    }

    public void setRespuesta(boolean respuesta) {
        this.respuesta = respuesta;
    }

    public int getPuntosPreguntaAmbiente() {
        return puntosPreguntaAmbiente;
    }

    public void setPuntosPreguntaAmbiente(int puntosPreguntaAmbiente) {
        this.puntosPreguntaAmbiente = puntosPreguntaAmbiente;
    }

    public int getIndiceActual() {
        return indiceActual;
    }

    public void setIndiceActual(int indiceActual) {
        this.indiceActual = indiceActual;
    }

    public List<String> obtenerPreguntasAmbiente() {
        listaPreguntasAmbiente = new ArrayList<>();
        for (int i = 0; i < vecIdPreguntas.length; i++) {
            listaPreguntasAmbiente.add(getPreguntaAmbiente(vecIdPreguntas[i]).getEnunciado());
            contAmbiente++;
        }
        System.out.println("listaPreguntasAmbiente: " + listaPreguntasAmbiente);
        return listaPreguntasAmbiente;
    }

    public List<String> getGrupoItems(int numGrupo) {
//        listaPreguntasAmbiente = new ArrayList<>();
        for (int i = tamGrupo * (numGrupo - 1); i < tamGrupo * numGrupo; i++) {
            if (i >= 0 && i < vecIdPreguntas.length) {
                listaPreguntasAmbienteSeleccionada.add(listaPreguntasAmbiente.get(i));
//                listaPreguntasAmbiente.add(listaPreguntasAmbiente.get(i));
            } else {
                break;
            }
        }
        System.out.println("listaPreguntasAmbienteGetGrupoItem: " + listaPreguntasAmbiente);
        return listaPreguntasAmbiente;
    }

    public void nextImagen() {
        if (indiceActual < vecIdImages.length - 1) {
            indiceActual++;
            selectedPregunta = getPreguntaAmbiente(vecIdImages[indiceActual]);
        }
        System.out.println("next");
        System.out.println(indiceActual);
    }

    public void previousImagen() {
        if (indiceActual > 0) {
            indiceActual--;
            selectedPregunta = getPreguntaAmbiente(vecIdImages[indiceActual]);
        }
        System.out.println("previo");
        System.out.println(indiceActual);
    }

    public void addMessage(String summary, boolean success) {
        FacesMessage message = new FacesMessage(success ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_ERROR, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void buttonAction(boolean resp) {
        if (resp == true) {
            addMessage("Correcto!!", true);
        } else {
            addMessage("Incorrecto!!", false);
        }
    }

    public void comprobarRespuesta(int id) {
        if (selectedPregunta.getIdTipoAmbiente().getIdTipoAmbiente().equals(id)) {
            respuesta = true;
            puntosPreguntaAmbiente++;
            System.out.println("Respuesta correcta:" + respuesta);
        } else {
            respuesta = false;
            puntosPreguntaAmbiente--;
            System.out.println("Respuesta incorrecta:" + respuesta);
        }
        buttonAction(respuesta);
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
        System.out.println("Contador finalizar:" + cont);
        System.out.println("Paso actual finalizar: " + pasoActual);
        // realizar estadistica de respuestas
        return pasoActual;
    }

    private class Elemento {

        public TipoAmbiente tipoPer;
        public double valor;
    }

//    public List<Integer> getGrupos() {
//        gruposPreguntas = null;
//        if (gruposPreguntas == null) {
//            gruposPreguntas = new ArrayList<>();
//            items = getItems();
//            numGrupos = items.size() / tamGrupo;
//            numGrupos += (items.size() % tamGrupo == 0 ? 0 : 1);
//            numGrupos = 6;
//            for (int i = 1; i <= numGrupos; i++) {
//                gruposPreguntas.add(i);
//            }
//            System.out.println("gruposPreguntas: " + gruposPreguntas);
//        }
//        return gruposPreguntas;
//    }
    public RespuestaAmbiente getSelected() {
        return selected;
    }

    public void setSelected(RespuestaAmbiente selected) {
        this.selected = selected;
    }

    public PreguntaAmbiente getSelectedPregunta() {
        return selectedPregunta;
    }

    public void setSelectedPregunta(PreguntaAmbiente selectedPregunta) {
        this.selectedPregunta = selectedPregunta;
    }

    @Override
    protected void setEmbeddableKeys() {
        selected.getRespuestaAmbientePK().setIdPreguntasAmbiente(selected.getPreguntaAmbiente().getIdPreguntaAmbiente());
        selected.getRespuestaAmbientePK().setIdEncuesta(selected.getEncuesta().getIdEncuesta());
    }

    protected void initializeEmbeddableKey() {
        selected.setRespuestaAmbientePK(new com.ingesoft.interpro.entidades.RespuestaAmbientePK());
    }

    protected RespuestaAmbienteFacade getFacade() {
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

    public String obtenerImagenActual() {
        String url = Variables.ubicacionImagenes + this.selectedPregunta.getUrlImagen();
        return url;
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
            System.out.println("Contador:" + cont);
            System.out.println("siguientes paso: " + pasoActual);
            getItemPreguntaEvaluacion();
            getGrupoItems(cont);
            listaPreguntasAmbiente = getGrupoItems(cont);
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
        System.out.println(enunciado);
        TipoAmbienteController tipoAmbienteControlle = (TipoAmbienteController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "tipoAmbienteController");
        listaTipoAmbienteItem = tipoAmbienteControlle.getItems();
        definicion = listaTipoAmbienteItem.get(cont).getDefinicion();
        System.out.println(definicion);

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
