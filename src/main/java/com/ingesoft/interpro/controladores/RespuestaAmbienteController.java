package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.RespuestaAmbiente;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.PreguntaAmbiente;
import com.ingesoft.interpro.entidades.ResultadoPorAmbiente;
import com.ingesoft.interpro.entidades.TipoAmbiente;
import com.ingesoft.interpro.facades.RespuestaAmbienteFacade;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean(name = "respuestaAmbienteController")
@SessionScoped
public class RespuestaAmbienteController implements Serializable {

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
    private List<Character> listaValoresAmbiente;
    private List<RespuestaAmbiente> grupo = null;
    Encuesta EncuestaAcutal;
    private boolean finalizo;
    private BarChartModel graficoModelo;
    List<ResultadoPorAmbiente> listaResultadosPorAmbiente;
    private boolean isEvaluacion;

    public RespuestaAmbienteController() {
        tamGrupo = 6;
        pasoActual = 0;
        numGrupos = 1;
        puntos = 0;
        gruposPreguntas = null;
        listaResultadosPorAmbiente = null;
        listaValoresAmbiente = null;
    }

    public boolean isIsEvaluacion() {
        return isEvaluacion;
    }

    public void setIsEvaluacion(boolean isEvaluacion) {
        this.isEvaluacion = isEvaluacion;
    }

    public BarChartModel getGraficoModelo() {
        graficoModelo = new BarChartModel();

        EncuestaAcutal = grupo.get(0).getEncuesta();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResultadoPorAmbienteController resultadoPorAmbienteController = (ResultadoPorAmbienteController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "resultadoPorAmbienteController");

        if (listaResultadosPorAmbiente == null) {
            listaResultadosPorAmbiente = resultadoPorAmbienteController.getItemsPorEncuesta(EncuestaAcutal.getIdEncuesta());
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

    public int anteriorPaso() {
        pasoActual -= 1;
        grupo = getGrupoItems(pasoActual + 1);
        return pasoActual;
    }

    public void volverAEncuesta() {
        isEvaluacion = false;
    }

    public int siguientePaso(ActionEvent actionEvent) {
        System.out.println("siguientes paso: " + pasoActual);
        if ((pasoActual + 1) % 3 == 0) {
            isEvaluacion = true;
            reinicioPasoActualEvaluacion();
        }
        pasoActual += 1;
        grupo = getGrupoItems(pasoActual + 1);
        number = 0;
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

    public void casiFinaliza() {
        isEvaluacion = true;
        reinicioPasoActualEvaluacion();
    }
    
    public void reinicioPasoActualEvaluacion(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RespuestaAmbienteEvaluacionController respuestaAmbienteEvaluacionController = (RespuestaAmbienteEvaluacionController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "respuestaAmbienteEvaluacionController");

        respuestaAmbienteEvaluacionController.setPasoActual(-1);
    }

    public int finalizarEncuesta(ActionEvent actionEvent) {
//        grupo = getGrupoItems(pasoActual + 1);
        for (RespuestaAmbiente respuesta : grupo) {
            getFacade().edit(respuesta);
        }
        pasoActual += 1;
        finalizo = true;
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
            resultadoPorAmbienteController.getSelected().setEncuesta(EncuestaAcutal);
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

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaAmbienteCreated"));
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

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaAmbienteUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RespuestaAmbienteDeleted"));
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
            HiloGuardado hilo = new HiloGuardado(grupo);
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

    public List<RespuestaAmbiente> prepararRespuestas(List<PreguntaAmbiente> preguntas, Encuesta encuesta) {
        System.out.println("encuesta: " + encuesta);
        System.out.println("preguntas: " + preguntas);
        gruposPreguntas = null;
        EncuestaAcutal = encuesta;
        finalizo = false;
        // PRUEBAS
        double[] valores = {0.0, 0.5, 1.0};
        Random rand = new Random(5);
        //
        items = new ArrayList<>(preguntas.size());
        for (PreguntaAmbiente pregunta : preguntas) {
            selected = new RespuestaAmbiente(pregunta.getIdPreguntaAmbiente(), encuesta.getIdEncuesta());
            selected.setPreguntaAmbiente(pregunta);
            selected.setEncuesta(encuesta);
            selected.setRespuesta((float) valores[rand.nextInt(3)]);
            items.add(selected);
        }
        listaResultadosPorAmbiente = null;
        cantidadRespuestas = new int[items.size()];
        getGrupos();
        pasoActual = 0;
        grupo = getGrupoItems(pasoActual + 1);
        return items;
    }

    public String obtenerImagen(RespuestaAmbiente respuesta) {
        String url = "img/ambiente/" + respuesta.getPreguntaAmbiente().getUrlImagen();
        return url;
    }

    public String obtenerEnunciado(RespuestaAmbiente respuesta) {
        String enunciado = respuesta.getPreguntaAmbiente().getEnunciado();
        return enunciado;
    }

    public class HiloGuardado extends Thread {

        private final List<RespuestaAmbiente> itemsRespuestas;

        public HiloGuardado(List<RespuestaAmbiente> itemsRespuestas) {
            this.itemsRespuestas = itemsRespuestas;
        }

        @Override
        public void run() {
            for (RespuestaAmbiente respuesta : itemsRespuestas) {
                getFacade().edit(respuesta);
            }
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

    public RespuestaAmbiente getRespuestaAmbiente(com.ingesoft.interpro.entidades.RespuestaAmbientePK id) {
        return getFacade().find(id);
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
