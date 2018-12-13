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
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

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
    private boolean isEvaluacion;

    String definicion;
    String enunciado;
    List<String> opciones;
    int correcta;

    public RespuestaAmbienteEvaluacionController() {
        tamGrupo = 6;
        pasoActual = -1;
        numGrupos = 1;
        puntos = 0;
        gruposPreguntas = null;
        listaResultadosPorAmbiente = null;
        getItemPreguntaEvaluacion();
        definicion = getItemDefinicion().get(0).getDefinicion();
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

    public int anteriorPaso() {
        pasoActual -= 1;
        grupo = getGrupoItems(pasoActual + 1);
        return pasoActual;
    }

    public int siguientePaso(ActionEvent actionEvent) {
        System.out.println("siguientes paso: " + pasoActual);
        int cont = 0;
        if (pasoActual == -1) {
            cont++;
            definicion = getItemDefinicion().get(cont).getDefinicion();
        }
        pasoActual += 1;
        number = 0;
        return pasoActual;
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

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaAmbienteCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
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
            RespuestaAmbienteEvaluacionController controller = (RespuestaAmbienteEvaluacionController) facesContext.getApplication().getELResolver().
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

    public List<Definicion> getItemDefinicion() {

        ArrayList<Definicion> itemsDefinicion;
        itemsDefinicion = new ArrayList();
        itemsDefinicion.add(new Definicion(
                "<p> A las personas con intereses realistas les gustan actividades que incluyan problemas y soluciones prácticas y directas. Con frecuencia, a las personas con intereses realistas no les gustan las carreras que requieren trabajo de oficina o trabajar junto a otras personas.\n"
                + "                Les gusta: </p>\n"
                + "            <p>\n"
                + "                <ul>\n"
                + "                    <li>Trabajar con plantas y animales</li>\n"
                + "                    <li>Materiales reales, tales como madera, herramientas y maquinaria</li>\n"
                + "                    <li>Trabajo al aire libres</li>\n"
                + "                </ul>\n"
                + "            </p>"));
        itemsDefinicion.add(new Definicion(
                "<h4>El Ambiente de profesiones u ocupaciones de tipo Artístico se define así: </h4>"
                + "<p> A las personas con intereses artísticos, les gustan trabajos relacionados con el lado artístico de las cosas, tales como actuar, música, arte y diseño. "
                + "Les gusta: </p>\n"
                + "            <p>\n"
                + "                <ul>\n"
                + "                    <li>Creatividad en su trabajo</li>\n"
                + "                    <li>Trabajo que puede hacerse sin seguir una serie de reglas</li>\n"
                + "                </ul>\n"
                + "            </p>"));
        itemsDefinicion.add(new Definicion(
                "<h4>El Ambiente de profesiones u ocupaciones de tipo de investigación se define así: </h4>"
                + "<p> A las personas con intereses de investigación, le gustan trabajos relacionados con ideas y razonamiento en lugar de actividades fisicas o liderazgo de personal. "
                + "Les gusta: </p>\n"
                + "            <p>\n"
                + "                <ul>\n"
                + "                    <li>Búsqueda de hechos</li>\n"
                + "                    <li>Resolución de problemas</li>\n"
                + "                </ul>\n"
                + "            </p>"));

        itemsDefinicion.add(new Definicion(
                "<h4>El Ambiente de profesiones u ocupaciones de tipo Social se define así: </h4>"
                + "<p> A las personas con intereses sociales les gusta trabajar con otros para ayudarlos a aprender y a desarrollar su conocimiento. Les gusta trabajar con personas más que trabajar con objetos, maquinaria o información. "
                + "Les gusta: </p>\n"
                + "            <p>\n"
                + "                <ul>\n"
                + "                    <li>Enseñanza</li>\n"
                + "                    <li>Dar consejos</li>\n"
                + "                    <li>Ayudar y dar consejos a otros</li>\n"
                + "                </ul>\n"
                + "            </p>"));
        itemsDefinicion.add(new Definicion(
                "<h4>El Ambiente de profesiones u ocupaciones de tipo Emprendedor se define así: </h4>"
                + "<p> A las personas con intereses empresariales les gustan trabajos que tengan que ver con el comienzo y continuación de proyectos de negocios. A estas personas les gusta tomar acciones en lugar de pensar sobre las cosas. "
                + "Les gusta: </p>\n"
                + "            <p>\n"
                + "                <ul>\n"
                + "                    <li>Persuadir y dirigir personal</li>\n"
                + "                    <li>Tomar decisiones</li>\n"
                + "                    <li>Tomar riesgos para obtener ganancias</li>\n"
                + "                </ul>\n"
                + "            </p>"));
        itemsDefinicion.add(new Definicion(
                "<h4>El Ambiente de profesiones u ocupaciones de tipo Convencional se define así: </h4>"
                + "<p>A las personas con intereses convencionales les gustan trabajos que siguen procedimientos y rutinas. Prefieren trabajar con información y poner atención a detalles en lugar de trabajar con ideas."
                + "Les gusta: </p>\n"
                + "            <p>\n"
                + "                <ul>\n"
                + "                    <li>Trabajar con reglas claras</li>\n"
                + "                    <li>Seguir a un líder influyente</li>\n"
                + "                </ul>\n"
                + "            </p>"));

        return itemsDefinicion;
    }

    public void getItemPreguntaEvaluacion() {
        ArrayList<String> listaOpciones;
        listaOpciones = new ArrayList();
        listaOpciones.add("Trabajar con plantas y animales");
        listaOpciones.add("Materiales reales, tales como madera, herramientas y maquinaria");
        listaOpciones.add("Trabajo al aire libres");
        listaOpciones.add("Les gusta trabajar con teorías científicas");
        listaOpciones.add("Les gusta la composición musical");
        enunciado = "<h5>Según la afirmación anterior, cual de las siguientes opciones no describe ocupaciones del ambiente de tipo Realista: </h5><br/>";
        opciones = listaOpciones;
        correcta = 0;

//        enunciado ="<p>Lea con atención el siguiente enunciado y seleccione la respuesta que considere correctacorrecta: </p><br/>"
//                + "<h5>Según la afirmación anterior, seleccione la opción adecuada que describe ocupaciones del Ambiente de tipo investigativo: </h5><br/>";
//        enunciado ="<p>Lea con atención el siguiente enunciado y seleccione la respuesta que considere correctacorrecta: </p><br/>"
//                + "<h5>Según la afirmación anterior, seleccione la opción adecuada que describe ocupaciones del Ambiente de tipo Artístico: </h5><br/>";
//        enunciado ="<p>Lea con atención el siguiente enunciado y seleccione la respuesta que considere correctacorrecta: </p><br/>"
//                + "<h5>Según la afirmación anterior, seleccione la opción adecuada que no describe ocupaciones del Ambiente de tipo Social: </h5><br/>";
//        enunciado ="<p>Lea con atención el siguiente enunciado y seleccione la respuesta que considere correctacorrecta: </p><br/>"
//                + "<h5>Según la afirmación anterior, seleccione la opción adecuada que describe ocupaciones del Ambiente de tipo Emprendedor: </h5>";
//        enunciado ="<p>Lea con atención el siguiente enunciado y seleccione la respuesta que considere correctacorrecta: </p><br/>"
//                + "<h5>Según la afirmación anterior, seleccione la opción adecuada que describe ocupaciones del Ambiente de tipo Convencional </h5>";
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
