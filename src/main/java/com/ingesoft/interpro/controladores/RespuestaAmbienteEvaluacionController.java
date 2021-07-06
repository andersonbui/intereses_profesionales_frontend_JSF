package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.RespuestaAmbiente;
import com.ingesoft.interpro.controladores.util.Variables;
import com.ingesoft.interpro.entidades.Evaluacion;
import com.ingesoft.interpro.entidades.PreguntaAmbiente;
import com.ingesoft.interpro.entidades.ResultadoPorAmbiente;
import com.ingesoft.interpro.entidades.TipoAmbiente;
import com.ingesoft.interpro.facades.RespuestaAmbienteFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "respuestaAmbienteEvaluacionController")
@SessionScoped
public class RespuestaAmbienteEvaluacionController extends Controllers implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.RespuestaAmbienteFacade ejbFacade;
    private RespuestaAmbiente selected;
    private PreguntaAmbiente selectedPregunta;
    private List<PreguntaAmbiente> listaPreguntasAmbiente = null;

    private final int tamGrupo; // evaluacion dentro de las preguntas ambiente
    private int pasoActual; // evaluacion dentro de las preguntas ambiente
    private int number;
    private int puntos;
    private List<String> images;
    private boolean finalizo;
    List<ResultadoPorAmbiente> listaResultadosPorAmbiente;
    List<Evaluacion> listaEvaluacionItem; // 
    List<TipoAmbiente> listaTipoAmbienteItem;
    private boolean isEvaluacion;
    int activeIndex = 0;
    static int MAX_RESPUESTAS_IMAGEN;

    String definicion;
    String enunciado;
    int tipoAmbiente;
    List<String> opciones;
    int correcta;
    int contGrupo;
    int indiceActual;
    boolean respuesta;
    int contCorrect;
    int contCorrectImg;

    int[] vecIdImages = {1, 3, 5, 7, 9, 11, 31, 33, 35, 37, 39, 41, 61, 63, 65, 67, 69, 71, 91, 93, 95, 97, 99, 101, 121, 123, 125, 127, 129, 131, 151, 153, 155, 157, 159, 161};
    int[] vecIdPreguntas = {1, 2, 5, 13, 14, 37, 38, 61, 39, 86, 40, 42, 43, 45, 48, 72, 31, 32, 43, 44, 73, 75, 78, 130, 139, 158, 160, 162, 167, 165};
    int[] correctas = {5, 39, 42, 72, 130, 167};
    private boolean isEvaluacionImagenes;
    int contadorRespuestasImagenes;
    int contadorRespuestasPreguntas;
    boolean[] respuestasImagenes;
//    private List<RespuestaAmbiente> items = null;
//    private int numGrupos;
//    private List<Integer> gruposPreguntas;
//    private List<RespuestaAmbiente> grupo = null;
//    int puntosPreguntaAmbiente = 0;
//    boolean[] respuestasPreguntas;

    public RespuestaAmbienteEvaluacionController() {
        contGrupo = 0;
        tamGrupo = 5;
        pasoActual = -1;
        puntos = 0;
        indiceActual = 0;
        tipoAmbiente = 0;
        isEvaluacionImagenes = false;
        contadorRespuestasImagenes = 0;
        contadorRespuestasPreguntas = 0;
        MAX_RESPUESTAS_IMAGEN = 3;
//        numGrupos = 1;
//        gruposPreguntas = null;
//        listaPreguntasAmbiente = new ArrayList<>();

    }

    public boolean isIsEvaluacionImagenes() {
        return isEvaluacionImagenes;
    }

    public void cambiarEvaluacionDeImagenes() {
        isEvaluacionImagenes = true;
        getEncuestaController().setTiempo(0);
    }

    @PostConstruct
    public void init() {
        reiniciarEvaluacion();
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

//    public List<RespuestaAmbiente> getGrupo() {
//        return grupo;
//    }
    public List<String> getImages() {
        return images;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<PreguntaAmbiente> getListaPreguntasAmbiente() {
        return listaPreguntasAmbiente;
    }

    public PreguntaAmbiente getPreguntasAmbienteEval(int indice) {
        return listaPreguntasAmbiente.get(indice);
    }

    public void setListaPreguntasAmbiente(List<PreguntaAmbiente> listaPreguntasAmbiente) {
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

    public void setPasoActual(int pasoActual) {
        if (pasoActual == -1) {
            contCorrect = 0;
        }
        this.pasoActual = pasoActual;
    }

    public void reiniciarEvaluacion() {
        contGrupo = 0;
        pasoActual = -1;
        puntos = 0;
        indiceActual = 0;
        isEvaluacionImagenes = false;
        contCorrect = 0;
        contadorRespuestasImagenes = 0;
        contadorRespuestasPreguntas = 0;
        contCorrectImg = 0;
        respuestasImagenes = new boolean[6];
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

    public int getIndiceActual() {
        return indiceActual;
    }

    public void setIndiceActual(int indiceActual) {
        this.indiceActual = indiceActual;
    }

    public List<PreguntaAmbiente> getGrupoItems(int numGrupo) {
        System.out.println("cont: " + contGrupo);
        List<PreguntaAmbiente> listaPreguntasAmbienteSeleccionada = new ArrayList<>();
        for (int i = tamGrupo * (numGrupo); i < tamGrupo * (numGrupo + 1); i++) {
            if (i >= 0 && i < vecIdPreguntas.length) {
                PreguntaAmbiente preguntaAmbiente = getPreguntaAmbiente(vecIdPreguntas[i]);
                preguntaAmbiente.setSeleccionado(false);
                listaPreguntasAmbienteSeleccionada.add(preguntaAmbiente);
            } else {
                break;
            }
        }
        System.out.println("listaPreguntasAmbienteGetGrupoItem: " + listaPreguntasAmbienteSeleccionada);
        return listaPreguntasAmbienteSeleccionada;
    }

    public void nextImagen() {
        if (indiceActual < vecIdImages.length - 1) {
            indiceActual++;
        }
        System.out.println("next");
        System.out.println(indiceActual);
        contCorrectImg = 0;
        respuestasImagenes = new boolean[6];
        contadorRespuestasImagenes = 0;
    }

    public void previousImagen() {
        if (indiceActual > 0) {
            indiceActual--;
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

    public boolean hayMasImagenes() {
        return indiceActual < vecIdImages.length - 1;
    }

    public boolean botonImagenDesactivado(int id) {
        return respuestasImagenes[id - 1];
    }

    public String claseCorrecta(int id) {
        if (contCorrectImg > 0) {
            return selectedPregunta.getIdTipoAmbiente().getIdTipoAmbiente().equals(id) ? " btn-success " : " btn-danger ";
        }
        return respuestasImagenes[id - 1] ? " btn-danger " : " btn-primary ";
    }

    public boolean maxRespuestasImagen() {
        return contadorRespuestasImagenes >= MAX_RESPUESTAS_IMAGEN || contCorrectImg > 0;
    }

    public void comprobarRespuesta(int id) throws InterruptedException {
        if (selectedPregunta.getIdTipoAmbiente().getIdTipoAmbiente().equals(id)) {
            respuesta = true;
            if (contCorrectImg == 0) {
                getEncuestaController().aumentarPuntos();
                getEncuestaController().setTiempo(0);
                contCorrectImg++;
//                if (indiceActual < vecIdImages.length - 1) {
//                    nextImagen();
//                }
            }
            System.out.println("Respuesta correcta:" + respuesta);
        } else {
            respuesta = false;
            System.out.println("Respuesta incorrecta:" + respuesta);
        }
        buttonAction(respuesta);
        contadorRespuestasImagenes++;
        respuestasImagenes[id - 1] = true;
        if (contadorRespuestasImagenes >= MAX_RESPUESTAS_IMAGEN || contCorrectImg > 0) {
            for (int i = 0; i < respuestasImagenes.length; i++) {
                respuestasImagenes[i] = true;
            }
//            Thread.sleep(3000);
//            nextImagen();
        }
    }

    public boolean botonRespuestaDesactivado(PreguntaAmbiente item) {
        if (contCorrect > 0) {
            return true;
        }
        return item.getSeleccionado() && !item.getIdPreguntaAmbiente().equals(correctas[contGrupo - 1]);
    }

    public String claseRepuestaACorrecta(PreguntaAmbiente item) {
        if (contCorrect > 0) {
            return item.getIdPreguntaAmbiente().equals(correctas[contGrupo - 1]) ? " btn-success " : " btn-danger ";
        }
        return item.getSeleccionado() ? " btn-danger " : " btn-primary ";
    }

    public void comprobarRespuestaSeleccion(PreguntaAmbiente item) {
        contadorRespuestasPreguntas++;
        if (contadorRespuestasPreguntas >= 3) {
            for (PreguntaAmbiente preguntaAmbiente : listaPreguntasAmbiente) {
                preguntaAmbiente.setSeleccionado(true);
            }
        }
        item.setSeleccionado(true);
        if (item.getIdPreguntaAmbiente().equals(correctas[contGrupo - 1])) {
            respuesta = true;
            if (contCorrect == 0) {
                getEncuestaController().aumentarPuntos();
                getEncuestaController().setTiempo(0);
//                puntosPreguntaAmbiente++;
                contCorrect++;
            }
            System.out.println("Respuesta correcta:" + respuesta);
        } else {
            respuesta = false;
            System.out.println("Respuesta incorrecta:" + respuesta);
        }
        buttonAction(respuesta);
    }

    public void incrementTiempo() {
        getEncuestaController().incrementTiempo();
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

    @Override
    protected RespuestaAmbienteFacade getFacade() {
        return ejbFacade;
    }

//    public List<RespuestaAmbiente> getTodasImages() {
//        List<RespuestaAmbiente> listaRespuestas;
//        listaRespuestas = null;
//        if (items != null) {
//            listaRespuestas = new ArrayList<>();
//            for (int i = 1; i < items.size(); i++) {
//                items.get(i).getPreguntaAmbiente().getOrden();
//                listaRespuestas.add(items.get(i));
//            }
//        }
//        return listaRespuestas;
//    }
    public String obtenerImagenActual() {
        selectedPregunta = getPreguntaAmbiente(vecIdImages[indiceActual]);
        String url = Variables.ubicacionImagenes + this.selectedPregunta.getUrlImagen();
        return url;
    }

    public int siguientePaso(ActionEvent actionEvent) {
        System.out.println("siguientes paso: " + pasoActual);
        pasoActual += 1;
        getEncuestaController().arrancarReloj();

        contadorRespuestasPreguntas = 0;
        number = 0;
        return pasoActual;
    }

    public void getItemPreguntaEvaluacion() {
//        ArrayList<String> listaOpciones;
        EvaluacionController evaluacionController = getEvaluacionController();
        listaEvaluacionItem = evaluacionController.getItems();
        enunciado = listaEvaluacionItem.get(contGrupo).getEnunciado();
        System.out.println(enunciado);
        TipoAmbienteController tipoAmbienteControlle = getTipoAmbienteController();
        listaTipoAmbienteItem = tipoAmbienteControlle.getItems();
        tipoAmbiente = listaTipoAmbienteItem.get(contGrupo).getIdTipoAmbiente();
        definicion = listaTipoAmbienteItem.get(contGrupo).getDefinicion();
        System.out.println(definicion);
        listaPreguntasAmbiente = getGrupoItems(contGrupo);
        correcta = 0;
        contGrupo++;
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
