package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.RespuestaAmbiente;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.controladores.util.Utilidades;
import com.ingesoft.interpro.controladores.util.Variables;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.PreguntaAmbiente;
import com.ingesoft.interpro.entidades.ResultadoPorAmbiente;
import com.ingesoft.interpro.entidades.TipoAmbiente;
import com.ingesoft.interpro.facades.RespuestaAmbienteFacade;
import java.io.IOException;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean(name = "respuestaAmbienteController")
@SessionScoped
public class RespuestaAmbienteController extends Controller implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.RespuestaAmbienteFacade ejbFacade;
    private List<RespuestaAmbiente> items = null;
    private RespuestaAmbiente selected;

    private final int tamGrupo;
    private int pasoActual;
    private int numGrupos;
//    private int tiempo;
//    private int puntos;
    private int[] cantidadRespuestas;
    private List<Integer> gruposPreguntas;
    private List<String> images;
//    private List<Character> listaValoresAmbiente;
    private List<RespuestaAmbiente> grupo = null;
//    Encuesta EncuestaAcutal;
    private boolean finalizo;
    private BarChartModel graficoModelo;
    List<ResultadoPorAmbiente> listaResultadosPorAmbiente;
//    private boolean isEvaluacion;
//    boolean detener_reloj;

    public RespuestaAmbienteController() {
        tamGrupo = 6;
        numGrupos = 1;
        gruposPreguntas = null;
        listaResultadosPorAmbiente = null;
//        listaValoresAmbiente = null;
        pasoActual = 0;
//        puntos = 0;
//        detener_reloj = true;
    }

    public Encuesta getEncuestaAcutal() {
        return getEncuestaController().getSelected();
    }

    public void reiniciar() {
        pasoActual = 0;
//        puntos = 0;
        gruposPreguntas = null;
        listaResultadosPorAmbiente = null;
//        listaValoresAmbiente = null;
        setEvaluacion(false);
//        detener_reloj = true;
    }

    public BarChartModel getGraficoModelo() {
        graficoModelo = new BarChartModel();
//        EncuestaAcutal = grupo.get(0).getEncuesta(); ERROR
        ResultadoPorAmbienteController resultadoPorAmbienteController = getResultadoPorAmbienteController();

        if (listaResultadosPorAmbiente == null) {
            listaResultadosPorAmbiente = resultadoPorAmbienteController.getItemsPorEncuesta(getEncuestaAcutal().getIdEncuesta());
        }
        int i = 0;
        ChartSeries barra = null;
//        System.out.println("lista:" + listaResultadosPorAmbiente);
        Collections.sort(listaResultadosPorAmbiente, new Comparator<ResultadoPorAmbiente>() {
            @Override
            public int compare(ResultadoPorAmbiente r1, ResultadoPorAmbiente r2) {
                return -r1.getValor().compareTo(r2.getValor());
            }
        });
        for (ResultadoPorAmbiente result : listaResultadosPorAmbiente) {
            barra = new ChartSeries("ambiente:--");
            barra.set(result.getTipoAmbiente().getTipo(), result.getValor());
            barra.setLabel(result.getTipoAmbiente().getTipo());
//            System.out.println("result:" + result.getValor());
            graficoModelo.addSeries(barra);
        }
        graficoModelo.setSeriesColors("008000,FF0000,FFD42A,0000FF,FFFF00,00FFFF");
        graficoModelo.setShowPointLabels(true);
        return graficoModelo;
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
        System.out.println("barra de progreso");
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

    public void volverAEncuesta() {
        setEvaluacion(false);
        getEncuestaController().setTiempo(0);
    }

    public void setTiempo(int tiempo) {
        getEncuestaController().setTiempo(tiempo);
    }

    public void aumentarPuntos() {
        getEncuestaController().aumentarPuntos();
    }

    public boolean isEvaluacion() {
        return getEncuestaController().isEvaluacion();
    }

    public void setEvaluacion(boolean isEvaluacion) {
        getEncuestaController().setEvaluacion(isEvaluacion);
    }

    public int siguientePaso() {
        System.out.println("siguientes paso: " + pasoActual);
        int intervaloEvaluacion = 5;
        // @desarrollo
        if (Utilidades.esDesarrollo()) {
            intervaloEvaluacion = 3;
        }// @end
        if ((pasoActual + 1) % intervaloEvaluacion == 0) {
            detenerReloj();
            setEvaluacion(true);
//            System.out.println("reloj: "+relojDetenido());
            reinicioPasoActualEvaluacion();
            getRespuestaAmbienteEvaluacionController().getItemPreguntaEvaluacion();
        }
        pasoActual += 1;
        grupo = getGrupoItems(pasoActual + 1);
        this.setTiempo(0);
        return pasoActual;
    }

    public int getTamGrupo() {
        return tamGrupo;
    }

    public void meGusta(RespuestaAmbiente respuesta) {
        respuesta.setRespuesta((float) 1.0);
        reinicioUnicoPorPregunta(respuesta);
    }

    public void indiferente(RespuestaAmbiente respuesta) {
        respuesta.setRespuesta((float) 0.5);
        reinicioUnicoPorPregunta(respuesta);
    }

    public void noMeGusta(RespuestaAmbiente respuesta) {
        respuesta.setRespuesta((float) 0);
        reinicioUnicoPorPregunta(respuesta);
    }

    public void reinicioUnicoPorPregunta(RespuestaAmbiente respuesta) {
        int indice = (respuesta.getPreguntaAmbiente().getOrden() - 1);
        cantidadRespuestas[indice]++;
        if (cantidadRespuestas[indice] == 1) {
            setTiempo(0);
            aumentarPuntos();
        }
    }

    public void detenerReloj() {
        getEncuestaController().detenerReloj();
    }

    public void casiFinaliza() {
        setEvaluacion(true);
        reinicioPasoActualEvaluacion();
        detenerReloj();
        getRespuestaAmbienteEvaluacionController().getItemPreguntaEvaluacion();
    }

    /**
     * volver a la primer pagina de la evaluacion - asegurar que siempre empieza
     * la evaluacion por la definicion
     */
    public void reinicioPasoActualEvaluacion() {
        RespuestaAmbienteEvaluacionController respuestaAmbienteEvaluacionController = getRespuestaAmbienteEvaluacionController();
        respuestaAmbienteEvaluacionController.setPasoActual(-1);
    }

    public int finalizarEncuesta(ActionEvent actionEvent) {
        for (RespuestaAmbiente respuesta : grupo) {
            getFacade().edit(respuesta);
        }
        pasoActual += 1;
        finalizo = true;
        System.out.println("Paso siguiente finalizar: " + pasoActual);
        // realizar estadistica de respuestas
        realizarEstadisticas();
        return pasoActual;
    }

    /**
     *
     */
    private void realizarEstadisticas() {
        // TODO falta esperar los hilos de guardado para despues realizar la estadistica
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elOtroResolver = facesContext.getApplication().getELResolver();
        ResultadoPorAmbienteController resultadoPorAmbienteController = (ResultadoPorAmbienteController) elOtroResolver.getValue(facesContext.getELContext(), null, "resultadoPorAmbienteController");

        Elemento[] valores = new Elemento[6];

        valores[0] = new Elemento();
        valores[1] = new Elemento();
        valores[2] = new Elemento();
        valores[3] = new Elemento();
        valores[4] = new Elemento();
        valores[5] = new Elemento();

        valores[0].valor = 0.0;
        valores[1].valor = 0.0;
        valores[2].valor = 0.0;
        valores[3].valor = 0.0;
        valores[4].valor = 0.0;
        valores[5].valor = 0.0;

        for (RespuestaAmbiente respuestaAmbiente : items) {
            TipoAmbiente tipoAmb = respuestaAmbiente.getPreguntaAmbiente().getIdTipoAmbiente();
            int indice = tipoAmb.getIdTipoAmbiente() - 1;
            valores[indice].tipoPer = tipoAmb;
            valores[indice].valor += respuestaAmbiente.getRespuesta();
        }

        for (int i = 0; i < valores.length; i++) {
            resultadoPorAmbienteController.prepareCreate();
            resultadoPorAmbienteController.getSelected().setValor((float) valores[i].valor);
            resultadoPorAmbienteController.getSelected().setEncuesta(getEncuestaAcutal());
            resultadoPorAmbienteController.getSelected().setTipoAmbiente(valores[i].tipoPer);
            resultadoPorAmbienteController.create();
        }
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
            // @desarrollo
            if (Utilidades.esDesarrollo()) {
                numGrupos = 6;
            }// @end
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

    @Override
    protected void setEmbeddableKeys() {
        selected.getRespuestaAmbientePK().setIdPreguntasAmbiente(selected.getPreguntaAmbiente().getIdPreguntaAmbiente());
        selected.getRespuestaAmbientePK().setIdEncuesta(selected.getEncuesta().getIdEncuesta());
    }

    protected void initializeEmbeddableKey() {
        selected.setRespuestaAmbientePK(new com.ingesoft.interpro.entidades.RespuestaAmbientePK());
    }

    @Override
    protected RespuestaAmbienteFacade getFacade() {
        return ejbFacade;
    }

    public RespuestaAmbiente prepareCreate() {
        selected = new RespuestaAmbiente();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaAmbienteCreated"), selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public String obtenerColor(String tipo) {
        String color = "#000000";
        switch (tipo) {
            case "REALISTA":
                color = "#008000";
                break;
            case "INVESTIGATIVO":
                color = "#FF0000";
                break;
            case "ARTISTICO":
                color = "#FFD42A";
                break;
            case "SOCIAL":
                color = "#0000FF";
                break;
            case "EMPRENDEDOR":
                color = "#FFFF00";
                break;
            case "CONVENCIONAL":
                color = "#00FFFF";
                break;
        }
        return color;
    }

    public String claseNoSeleccionada(RespuestaAmbiente item) {
        return item.getRespuesta().isNaN() ? "border: 2px solid red; padding: 4px;" : "border: 2px solid white; padding: 4px;";
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaAmbienteUpdated"), selected);
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RespuestaAmbienteDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RespuestaAmbiente> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    /**
     * obtiene las respuestas de un determinado grupo
     *
     * @param numGrupo
     * @return
     */
    public List<RespuestaAmbiente> getGrupoItems(int numGrupo) {
        getItems();
        // guardar respuestas actuales
        if (grupo != null && !grupo.isEmpty()) {
            HiloGuardado hilo = new HiloGuardado(grupo, getEncuestaController());
            hilo.start();
        }
        List<RespuestaAmbiente> listaRespuestas = null;
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

    public List<RespuestaAmbiente> prepararRespuestas(List<PreguntaAmbiente> preguntas) {
        List<Integer> lindicesRecuperados = new ArrayList();
        gruposPreguntas = null;
        Encuesta encuesta = getEncuestaAcutal();
        finalizo = false;
        // PRUEBAS
        double[] valores = {0.0, 0.5, 1.0};
        List<RespuestaAmbiente> items_recuperados = obtenerTodosPorEncuesta(encuesta);
        items = new ArrayList<>(preguntas.size());
        for (PreguntaAmbiente pregunta : preguntas) {
            selected = new RespuestaAmbiente(pregunta.getIdPreguntaAmbiente(), encuesta.getIdEncuesta());
            selected.setPreguntaAmbiente(pregunta);
            selected.setEncuesta(encuesta);
            selected.setRespuesta(Float.NaN);
            if (items_recuperados != null && !items_recuperados.isEmpty()) {
                int indice = items_recuperados.indexOf(selected);
                if (indice >= 0) {
                    int i = (pregunta.getOrden() - 1);
//                    cantidadRespuestas[i]++;
                    lindicesRecuperados.add(i);
                    selected.setRespuesta(items_recuperados.get(indice).getRespuesta());
                }
            }
            items.add(selected);
        }
        cantidadRespuestas = new int[items.size()];
        
        // desactivar puntaje a preguntas ya respondidas
        for (int indi : lindicesRecuperados) {
            cantidadRespuestas[indi]++;
        }
        // @desarrollo
        if (Utilidades.esDesarrollo()) {
            System.out.println("encuesta: " + encuesta);
            Random rand = new Random(Calendar.getInstance().getTimeInMillis());
            for (RespuestaAmbiente item : items) {
                item.setRespuesta((float) valores[rand.nextInt(3)]);
            }
        }// @end
        listaResultadosPorAmbiente = null;
        getGrupos();
        if (items_recuperados != null && !items_recuperados.isEmpty()) {
            pasoActual = (items_recuperados.size() / 6) - 1;
        } else {
            pasoActual = 0;
        }
        grupo = getGrupoItems(pasoActual + 1);
        return items;
    }

    public String obtenerImagen(RespuestaAmbiente respuesta) {
        String url = Variables.ubicacionImagenes + respuesta.getPreguntaAmbiente().getUrlImagen();
        return url;
    }

    public String obtenerEnunciado(RespuestaAmbiente respuesta) {
        String enunciado = respuesta.getPreguntaAmbiente().getEnunciado();
        return enunciado;
    }

    public class HiloGuardado extends Thread {

        private final List<RespuestaAmbiente> itemsRespuestas;
        EncuestaController encuestaController;

        public HiloGuardado(List<RespuestaAmbiente> itemsRespuestas, EncuestaController encuestaController) {
            this.itemsRespuestas = itemsRespuestas;
            this.encuestaController = encuestaController;
        }

        @Override
        public void run() {
            for (RespuestaAmbiente respuesta : itemsRespuestas) {
                getFacade().edit(respuesta);
            }
            encuestaController.getFacade().edit(encuestaController.getSelected());
//            EncuestaAcutal = encuestaController.getSelected();
            System.out.println("----Termino de guardar Respuestas");
        }

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

    public List<RespuestaAmbiente> obtenerTodosPorEncuesta(Encuesta encuesta) {
        return getFacade().obtenerTodosPorEncuesta(encuesta);
    }
    
    public List<RespuestaAmbiente> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RespuestaAmbiente> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RespuestaAmbiente.class)
    public static class RespuestaAmbienteControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RespuestaAmbienteController controller = (RespuestaAmbienteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "respuestaAmbienteController");
            return controller.getRespuestaAmbiente(getKey(value));
        }

        com.ingesoft.interpro.entidades.RespuestaAmbientePK getKey(String value) {
            com.ingesoft.interpro.entidades.RespuestaAmbientePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.ingesoft.interpro.entidades.RespuestaAmbientePK();
            key.setIdPreguntasAmbiente(Integer.parseInt(values[0]));
            key.setIdEncuesta(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(com.ingesoft.interpro.entidades.RespuestaAmbientePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdPreguntasAmbiente());
            sb.append(SEPARATOR);
            sb.append(value.getIdEncuesta());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RespuestaAmbiente) {
                RespuestaAmbiente o = (RespuestaAmbiente) object;
                return getStringKey(o.getRespuestaAmbientePK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RespuestaAmbiente.class.getName()});
                return null;
            }
        }

    }

}
