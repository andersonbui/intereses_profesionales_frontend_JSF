package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.controladores.util.Utilidades;
import com.ingesoft.interpro.controladores.util.Vistas;
import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.entidades.EstudianteGrado;
import com.ingesoft.interpro.entidades.Persona;
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

@ManagedBean(name = "encuestaController")
@SessionScoped
public class EncuestaController extends Controller implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.EncuestaFacade ejbFacade;
    private List<Encuesta> items = null;
    private Encuesta selected;
    private int pasoActivo;

    private boolean evaluacion;
    private int tiempo;
    private int puntos_eval;
    private int puntos_encuesta;
    boolean detener_reloj;

    public boolean esDesarrollo() {
        return Utilidades.esDesarrollo();
    }

    public EncuestaController() {
        detener_reloj = true;
        puntos_encuesta = 0;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo_eval) {
        this.tiempo = tiempo_eval;
    }

    public int getPuntos() {
        if (evaluacion) {
            return puntos_eval;
        }
        return puntos_encuesta;
    }

    public int getPuntosEval() {
        return puntos_eval;
    }

    public int getPuntosEncuesta() {
        return puntos_encuesta;
    }

    public void aumentarPuntos() {
        if (evaluacion) {
            this.puntos_eval++;
        } else {
            this.puntos_encuesta++;
        }
    }
    public List<Encuesta> listarEncuestasSelected(Estudiante estudiante){
        return getFacade().buscarPorEstudiante(estudiante);
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
                if (puntos_eval > 0) {
                    puntos_eval--;
                } else {
                }
            } else {
                if (puntos_encuesta > 0) {
                    puntos_encuesta--;
                } else {
                }
            }
        }
    }

    public void incrementPuntaje() {
        detener_reloj = !detener_reloj;
//        System.out.println("mostrar_reloj: " + detener_reloj);
//        System.out.println("tiempo: " + tiempo);
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
        String personalidad = getRespuestaPersonalidadController().finalizarEncuesta();
        selected.setPersonalidad(personalidad);
        selected.setPuntajeEncuesta(puntos_encuesta);
        selected.setPuntajeEvaluacion(puntos_eval);
        detenerReloj();
        update();
    }

    public int getIdEncuesta() {
        Integer valor = ejbFacade.autogenerarIdEncuesta();
        return valor == null ? 1 : valor;
    }

    public Encuesta actualizarSelected() {
        selected = getFacade().find(selected.getIdEncuesta());
        return selected;
    }

    /**
     * Prepara y crea una encuesta con fecha y esudiante
     *
     * @throws java.io.IOException
     */
    public void prepararYCrear() throws IOException {
        pasoActivo = 0;
        detener_reloj = true;
        puntos_encuesta = 0;
        puntos_eval = 0;
        getRespuestaAmbienteEvaluacionController().reiniciarEvaluacion();
        getRespuestaAmbienteController().reiniciar();
        getAreaEncuestaController().inicializar();

        // @TODO : Falta obtener el usuario
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        ELResolver elOtroResolver = facesContext.getApplication().getELResolver();
//        AreaEncuestaController areaEncuestaController = (AreaEncuestaController) elOtroResolver.getValue(facesContext.getELContext(), null, "areaEncuestaController");
//        areaEncuestaController.prepararParaEncuesta();
        LoginController loginController = getLoginController();
        Usuario usu = loginController.getActual();
        Persona persona = loginController.getPersonaActual();
        System.out.println("usuario: " + usu);
        try {
            Estudiante estud = getEstudianteController().getEstudiantePorPersona(persona);
            EstudianteGrado estudianteGrado = getEstudianteGradoController().obtenerUltimoEstudianteGrado(estud);
            if (estudianteGrado == null) {
                System.out.println();
                JsfUtil.addSuccessMessage("Usted no ha seleccionado su grado");
            } else {
                selected = new Encuesta();
                initializeEmbeddableKey();
                selected.setFecha(new Date());
                selected.setEstudianteGrado(estudianteGrado);
                selected.setIdEncuesta(getIdEncuesta()); //depronto aqui este el rpoblema, quitar el idencuesta
//            selected.setEstudianteGrado(estudianteGrado);
                System.out.println("antes encuesta creada: " + selected);
//            selected = getEncuesta(selected.toString());
                System.out.println("despues encuesta creada: " + selected);
                FacesContext.getCurrentInstance().getExternalContext().redirect("/intereses_profesionales_frontend_JSF/faces/vistas/encuesta/welcomePrimefaces.xhtml");

                // @desarrollo
                if (Utilidades.esDesarrollo()) {
                    selected.setIdAreaProfesional(getAreaProfesionalController().getItems().get(1));
                }
                create();

                AreaController areaController = getAreaController();
                areaController.inicializar();
            }
        } catch (Exception e) {
            System.out.println("No se ha encontrado la persona o estudiante correspondiente.");
            e.printStackTrace();
        }

    }

    public String resultado_personalidad(int i) {
//        String result_personalidad = "IIEJ";
        String result_personalidad = selected.getPersonalidad();
        String url = "img/resultado_test_personalidad/" + i + result_personalidad.charAt(i) + ".jpg";
        System.out.println(url);

        return url;

    }

    public String resultado_personalidad_descripcion(int i) {
//        String result_personalidad = "IIEJ";
        String result_personalidad = selected.getPersonalidad();
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
