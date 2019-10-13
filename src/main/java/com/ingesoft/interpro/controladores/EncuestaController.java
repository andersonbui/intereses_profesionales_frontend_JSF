package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.controladores.util.Utilidades;
import com.ingesoft.interpro.controladores.util.Vistas;
import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.entidades.Grado;
import com.ingesoft.interpro.entidades.Persona;
import com.ingesoft.interpro.entidades.RespuestaPorPersonalidad;
import com.ingesoft.interpro.entidades.Usuario;
import com.ingesoft.interpro.facades.EncuestaFacade;
import java.io.IOException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import org.primefaces.context.RequestContext;

@ManagedBean(name = "encuestaController")
@SessionScoped
public class EncuestaController extends Controller implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.EncuestaFacade ejbFacade;
    private List<Encuesta> items = null;
    private Encuesta selected;
    private Encuesta sinterminar;
    private int pasoActivo;

    int[] ORDEN_RESPUESTA_PERSONALIDAD = {2, 3, 1, 0};

    private boolean evaluacion;
    private int tiempo;
    boolean detener_reloj;
    private final String ESTADO_FINALIZADA = "FINALIZADA";

    public boolean esDesarrollo() {
        return Utilidades.esDesarrollo();
    }

    public EncuestaController() {
        detener_reloj = true;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo_eval) {
        this.tiempo = tiempo_eval;
    }

    public int getPuntos() {
        if (evaluacion) {
            return getPuntos_eval();
        }
        return getPuntos_encuesta();
    }

    public void setPuntos_eval(int puntos_eval) {
        selected.setPuntajeEvaluacion(puntos_eval);
    }

    public void setPuntos_encuesta(int puntos_encuesta) {
        selected.setPuntajeEncuesta(puntos_encuesta);
    }

    public Integer getPuntos_eval() {
        Integer valor = selected.getPuntajeEvaluacion();
        if (valor != null) {
            return valor;
        }
        return 0;
    }

    public Integer getPuntos_encuesta() {
        System.out.println("getPuntos_encuesta encuesta: " + selected);
        Integer valor = selected.getPuntajeEncuesta();
        if (valor != null) {
            return valor;
        }
        return 0;
    }

    public void aumentarPuntos() {
        if (evaluacion) {
            setPuntos_eval(getPuntos_eval() + 1);
        } else {
            setPuntos_encuesta(getPuntos_encuesta() + 1);
        }
    }

    public List<Encuesta> listarEncuestasSelected(Estudiante estudiante) {
        return getFacade().buscarPorEstudiante(estudiante);
    }

    public double promedioPuntajeEncuesta() {
        if (items != null && !items.isEmpty()) {
            int cantidad = 0;
            double suma = 0;
            for (Encuesta item : items) {
                if (item != null && item.getPuntajeEncuesta() != null) {
                    suma += item.getPuntajeEncuesta();
                    cantidad++;
                }
            }
            if (cantidad != 0) {
                return suma / cantidad;
            }
        }
        return 0;
    }

    public double promedioPuntajeEvaluacion() {
        if (items != null && !items.isEmpty()) {
            int cantidad = 0;
            double suma = 0;
            for (Encuesta item : items) {
                
                if (item != null && item.getPuntajeEvaluacion() != null) {
                    suma += item.getPuntajeEvaluacion();
                    cantidad++;
                }
            }
            if (cantidad != 0) {
                return suma / cantidad;
            }
        }
        return 0;
    }

    public String prediccion() {
        MineriaController mineriaController = getMineriaController();
        String prediccion = null;
        Encuesta encuesta = getSelected();
        if (encuesta != null) {
            prediccion = mineriaController.predecir(encuesta);
            prediccion = prediccion.replaceAll("--", "\n");
            System.out.println("prediccion: <" + prediccion + ">");
        }
        return prediccion;
    }

    public void setItems(List<Encuesta> items) {
        this.items = items;
    }

    public Encuesta getSelected() {
        return selected;
    }

    public void setSelected(Encuesta selected) {
        this.selected = selected;
    }

    @Override
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    protected EncuestaFacade getFacade() {
        return ejbFacade;
    }

    public int getPasoActivo() {
        return pasoActivo;
    }

    public void setPasoActivo(int pasoActivo) {
        this.pasoActivo = pasoActivo;
    }

    public boolean relojDetenido() {
        return detener_reloj;
    }

    public EncuestaController arrancarReloj() {
        detener_reloj = false;
        return this;
    }

    public void detenerReloj() {
        detener_reloj = true;
    }

    public void incrementTiempo() {
        tiempo++;
        if (tiempo > 15) {
            tiempo = 0;
            if (evaluacion) {
                if (getPuntos_eval() > 0) {
                    setPuntos_eval(getPuntos_eval() - 1);
                } else {
                }
            } else {
                if (getPuntos_encuesta() > 0) {
                    setPuntos_encuesta(getPuntos_encuesta() - 1);
                } else {
                }
            }
        }
    }

    public void disminuirPuntosEncuesta() {

    }

    public void incrementPuntaje() {
        detener_reloj = !detener_reloj;
    }

    public boolean isEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(boolean isEvaluacion) {
        this.evaluacion = isEvaluacion;
    }

    public void pasoPreguntasAmbiente() throws IOException {
        this.pasoActivo = 1;
        getAreaEncuestaController().almacenarEncuestaAreas(selected);
        System.out.println("encuesta en pasoPreguntasAmbiente(): " + selected);
        Grado grado = getGradoController().getSelected();
        selected.setGrado(grado);
        update();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/intereses_profesionales_frontend_JSF/faces/vistas/preguntaAmbiente/preguntasAmbiente.xhtml");
    }

    public void pasoPreguntasPersonalidad() throws IOException {
        this.pasoActivo = 2;
        FacesContext.getCurrentInstance().getExternalContext().redirect("/intereses_profesionales_frontend_JSF/faces/vistas/preguntaPersonalidad/preguntasPersonalidad.xhtml");
    }

    public void pasoResumen() throws IOException {
        this.pasoActivo = 3;
        actualizarSelected();
        EstadisticaAmbienteController estadisticaAmbienteController = getEstadisticaAmbienteController();
        estadisticaAmbienteController.setEncuesta(selected);
        estadisticaAmbienteController.cargarGraficoResultadoEncuesta(1111);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/intereses_profesionales_frontend_JSF/faces/vistas/encuesta/resumen.xhtml");
    }

    public void finalizar() throws IOException {
        this.pasoActivo = 0;
        FacesContext.getCurrentInstance().getExternalContext().redirect(Vistas.inicio());
    }

    public void finalizarEncuesta() {
        getRespuestaPersonalidadController().finalizarEncuesta();
        String personalidad = obtenerPersonalidad(selected);
        selected.setPersonalidad(personalidad);
        selected.setPuntajeEncuesta(getPuntos_encuesta());
        selected.setPuntajeEvaluacion(getPuntos_eval());
        selected.setEstado(ESTADO_FINALIZADA);
        detenerReloj();
        update();
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

    public class ElementoPersonalidad {

        public String tipo;
        public int puntaje;
    }

    public void tomarEncuestaSinTerminar() {
        System.out.println("tomando encuesta sin terminar");
        selected = sinterminar;
        sinterminar = null;
    }

    private void obtenerEncuestaSinTerminar(Estudiante estudiante) {
        sinterminar = getFacade().encuestaSinTerminar(estudiante);
    }

    public int getIdEncuesta() {
        Integer valor = ejbFacade.autogenerarIdEncuesta();
        return valor == null ? 1 : valor;
    }

    public void guardarSelected() {
        getFacade().edit(getSelected());
    }

    private Encuesta actualizarSelected() {
        selected = getFacade().find(selected.getIdEncuesta());
        return selected;
    }

    public void crearEncuesta() {
        selected = new Encuesta();
        selected.setFecha(new Date());
        System.out.println("se creo nueva id encuesta");
        selected.setIdEncuesta(getIdEncuesta()); //depronto aqui este el rpoblema, quitar el idencuesta
        setPuntos_encuesta(0);
        setPuntos_eval(0);
    }

    /**
     * Prepara y crea una encuesta con fecha y esudiante
     *
     * @throws java.io.IOException
     */
    public void prepararYCrear() throws IOException {
        LoginController loginController = getLoginController();
        Usuario usu = loginController.getActual();
        Persona persona = loginController.getPersonaActual();
        Estudiante estud = getEstudianteController().getEstudiantePorPersona(persona);
        obtenerEncuestaSinTerminar(estud);
        System.out.println("verificar encuesta");
        if (selected == null) {
            System.out.println("encuesta null");
            if (sinterminar != null) {
                System.out.println("encontro sinterminar");
                RequestContext requestContext = RequestContext.getCurrentInstance();
                requestContext.execute("PF('dialog_encuesta_no_terminada').show()");
                return;
            } else {
                crearEncuesta();
            }
        }
        pasoActivo = 0;
        detener_reloj = true;
        getRespuestaAmbienteEvaluacionController().reiniciarEvaluacion();
        getRespuestaAmbienteController().reiniciar();
        getAreaEncuestaController().inicializar();

        try {
            initializeEmbeddableKey();
            selected.setEstudiante(estud);
            // @desarrollo
            if (Utilidades.esDesarrollo() && selected.getIdAreaProfesional() == null) {
                selected.setIdAreaProfesional(getAreaProfesionalController().getItems().get(1));
            }
            create();
            AreaController areaController = getAreaController();
            areaController.inicializar(selected);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/intereses_profesionales_frontend_JSF/faces/vistas/encuesta/welcomePrimefaces.xhtml");

//            }
        } catch (Exception e) {
            System.out.println("No se ha encontrado la persona o estudiante correspondiente.");
            e.printStackTrace();
        }
    }

    public String resultado_personalidad(int i, String personalidad) {
        System.out.println("resultado_personalidad/personalidad: "+personalidad);
        String result_personalidad = personalidad;
        String url = "img/resultado_test_personalidad/" + i + result_personalidad.charAt(i) + ".jpg";
        System.out.println(url);

        return url;

    }

    public String resultado_personalidad_descripcion(int i, String personalidad) {
        String result_personalidad = personalidad;
        String codigo_personalidad = "" + i + result_personalidad.charAt(i);
        if (null == codigo_personalidad) {
            return null;
        } else {
            switch (codigo_personalidad) {
                case "0E":
                    return "Estrovertido";
                case "0I":
                    return "Introvertido";
                case "1I":
                    return "Intuitivo";
                case "1S":
                    return "Sensato";
                case "2E":
                    return "Emocional";
                case "2R":
                    return "Racional";
                case "3J":
                    return "Juzgador";
                case "3P":
                    return "Perceptivo";
                default:
                    return null;
            }
        }

    }

    public void create() {
        selected = (Encuesta) persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EncuestaCreated"), selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        selected = (Encuesta) persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EncuestaUpdated"), selected);
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EncuestaDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Encuesta> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public Encuesta getEncuesta(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Encuesta> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Encuesta> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Encuesta.class)
    public static class EncuestaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EncuestaController controller = (EncuestaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "encuestaController");
            return controller.getEncuesta(getKey(value));
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
            if (object instanceof Encuesta) {
                Encuesta o = (Encuesta) object;
                return getStringKey(o.getIdEncuesta());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Encuesta.class.getName()});
                return null;
            }
        }

    }

}
