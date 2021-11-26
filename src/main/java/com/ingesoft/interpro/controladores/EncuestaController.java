package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.controladores.util.Utilidades;
import com.ingesoft.interpro.controladores.util.Vistas;
import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.entidades.EstudianteGrado;
import com.ingesoft.interpro.entidades.Grado;
import com.ingesoft.interpro.entidades.Persona;
import com.ingesoft.interpro.facades.EncuestaFacade;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;

@ManagedBean(name = "encuestaController")
@SessionScoped
public class EncuestaController extends Controllers implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.EncuestaFacade ejbFacade;
    private List<Encuesta> items = null;
    private Encuesta selected;
    private Encuesta sinterminar;
    private int pasoActivo;

    private boolean evaluacion;
    private int tiempo;
    boolean detener_reloj;
    
    private int contadorEncuesta = 0;
    private List<EncuestaControllerInterface> listaEncuestasController;
    
    protected DefaultMenuModel model;
    
    /************************************************************************
     * CONSTRUCTORS
     ************************************************************************/
    
    public EncuestaController() {
        detener_reloj = true;
    }

    /************************************************************************
     * Funciones personalizadas
     ************************************************************************/
    
    public int getPuntos() {
        if (evaluacion) {
            return getPuntos_eval();
        }
        return getPuntos_encuesta();
    }

    public void setPuntos_eval(int puntos_eval) {
        selected.setPuntajeEvaluacion(puntos_eval);
    }

    public void addPuntos_eval(int puntos_eval) {
        selected.setPuntajeEvaluacion(getPuntos_eval() + puntos_eval);
    }
    
    public void addPuntos_encuesta(int puntos_encuesta) {
        selected.setPuntajeEncuesta(getPuntos_encuesta() + puntos_encuesta);
    }

    public void setPuntos_encuesta(int puntos_encuesta) {
        selected.setPuntajeEncuesta(puntos_encuesta);
    }

    public DefaultMenuModel getModel() {
        
        List<EncuestaControllerInterface> unaListaEncuestasController = getListadoEncuestasController();
        if(model == null && unaListaEncuestasController != null && !unaListaEncuestasController.isEmpty()){
            model = new DefaultMenuModel();
            
            DefaultMenuItem firstSubMenu = new DefaultMenuItem("Informacion Personal");
            model.addElement(firstSubMenu); 
            
            for (EncuestaControllerInterface listaEncuesta : unaListaEncuestasController) {
                firstSubMenu = new DefaultMenuItem(listaEncuesta.getName());
                model.addElement(firstSubMenu); 
            }
            
            firstSubMenu = new DefaultMenuItem("Resumen");
            model.addElement(firstSubMenu); 
        }
        return model;
    }
    
    /**
     * 
     * @return 
     */
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

    public List<Encuesta> listarEncuestasSelected(Estudiante estudiante) {
        return getFacade().buscarPorEstudiante(estudiante);
    }

    public double promedioPuntajeEncuesta() {
        
        return  promedioPuntajeEncuesta(items);
    }
    
    public double promedioPuntajeEncuesta(List<Encuesta> listaItems ) {
        if (listaItems != null && !listaItems.isEmpty()) {
            int cantidad = 0;
            double suma = 0;
            for (Encuesta item : listaItems) {
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
        return promedioPuntajeEvaluacion(items);
    }

    public double promedioPuntajeEvaluacion(List<Encuesta> listaItems) {
        if (listaItems != null && !listaItems.isEmpty()) {
            int cantidad = 0;
            double suma = 0;
            for (Encuesta item : listaItems) {

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

    public EncuestaController arrancarReloj() {
        detener_reloj = false;
        return this;
    }

    public void detenerReloj() {
        detener_reloj = true;
    }

    public void aumentarPuntos() {
        if (evaluacion) {
            setPuntos_eval(getPuntos_eval() + 1);
        } else {
            setPuntos_encuesta(getPuntos_encuesta() + 1);
        }
        this.update();
    }
    
    public void disminuirPunto() {
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
        this.update();
    }
    
    public void incrementTiempo() {
        tiempo++;
        if (tiempo > 15) {
            tiempo = 0;
            this.disminuirPunto();
        }
    }

    public void incrementPuntaje() {
        detener_reloj = !detener_reloj;
    }
    
        
    public void guaradarInfoPersonal() {
//        getAreaEncuestaController().almacenarEncuestaAreas(selected);
//        Grado grado = getGradoController().getSelected();
//        selected.setGrado(grado);
//        update();
    }
    
    public void siguienteEncuesta() throws IOException {
        contadorEncuesta++;
        this.pasoActivo = contadorEncuesta + 1;
        
        List<EncuestaControllerInterface> unaListaEncuestasController = getListadoEncuestasController();
        if(contadorEncuesta >= unaListaEncuestasController.size()) {
            pasoResumen();
            return;
        }
        EncuestaControllerInterface encuesta = unaListaEncuestasController.get(contadorEncuesta);
        encuesta.prepararEncuesta(selected);
        String ruta = encuesta.getRuta();
        System.out.println("encuesta ruta: " + ruta + " | this.pasoActivo: "+this.pasoActivo);
        String rutaGeneral = Vistas.getRutaGeneral();
        FacesContext.getCurrentInstance().getExternalContext().redirect(rutaGeneral+ruta);
    }
    
    public void pasoResumen() throws IOException {
        actualizarSelected();
        String rutaGeneral = Vistas.getRutaGeneral();
        FacesContext.getCurrentInstance().getExternalContext().redirect(rutaGeneral+"/vistas/encuesta/resumen.xhtml");
    }
    
    public void finalizar() throws IOException {
        this.pasoActivo = 0;
        FacesContext.getCurrentInstance().getExternalContext().redirect(Vistas.inicio());
    }

    public void finalizarEncuesta() {
        selected.setPuntajeEncuesta(getPuntos_encuesta());
        selected.setPuntajeEvaluacion(getPuntos_eval());
        selected.setEstado(Encuesta.FINALIZADA);
        selected.setFechaFinalizada(new Date());
        update();
    }

    public void tomarEncuestaSinTerminar() {
        System.out.println("tomando encuesta sin terminar");
        selected = sinterminar;
        sinterminar = null;
    }
    
    /**
     * 
     * @param encuesta
     * @return 
     */
    private boolean haySubEncuestaPendiente(Encuesta encuesta) {
        
        List<EncuestaControllerInterface> unaListaEncuestasController = getListadoEncuestasController();
        
        List<Boolean> listarespuesta = (List<Boolean>) unaListaEncuestasController.stream().filter((EncuestaControllerInterface unaencuesta) -> {
            return unaencuesta.isPending(encuesta);
        }).map(elem -> true).collect(Collectors.toList());
        
        return !listarespuesta.isEmpty();
    }
    
    protected List<Encuesta>  obtenerEncuestaSinTerminar(Estudiante estudiante) {
        
        List<Encuesta> lista = getFacade().buscarPorEstudiante(estudiante);
        
        List<Encuesta> listaSinFinalizar = lista.stream().filter((Encuesta uanaencuesta) -> {
            return haySubEncuestaPendiente(uanaencuesta);
        }).collect(Collectors.toList());
        
        return listaSinFinalizar;
    }
    
    public void guardarSelected() {
        getFacade().edit(getSelected());
    }
    
    public List<EncuestaControllerInterface> getListadoEncuestasController(){
        if(listaEncuestasController == null){
            listaEncuestasController = new ArrayList();
            listaEncuestasController.add(getRespuestaAmbienteController());
            listaEncuestasController.add(getRespuestaPersonalidadController());
            listaEncuestasController.add(getEstiloController());
            listaEncuestasController.add(getEncuestaInteligenciasMultiplesController());
            listaEncuestasController.add(getChasideController());
        }
        return listaEncuestasController;
    }
    
    private Encuesta actualizarSelected() {
        selected = getFacade().find(selected.getIdEncuesta());
        return selected;
    }

    public void crearEncuesta() {
        selected = new Encuesta();
        selected.setFechaCreacion(new Date());
        System.out.println("se creo nueva id encuesta");
        selected.setIdEncuesta(getIdEncuesta()); //depronto aqui este el rpoblema, quitar el idencuesta
        setPuntos_encuesta(0);
        setPuntos_eval(0);
    }

    /**
     * Prepara y crea una encuesta con fecha y esudiante
     *
     * @return 
     * @throws java.io.IOException
     */
    public List<Encuesta> prepararYCrear() throws IOException {
        LoginController loginController = getLoginController();
        Persona persona = loginController.getPersonaActual();
        Estudiante estud = getEstudianteController().getEstudiantePorPersona(persona);
        
        List<Encuesta> listaEncuestasPendientes = obtenerEncuestaSinTerminar(estud);
        System.err.println("listaEncuestas:"+listaEncuestasPendientes.size());
        return listaEncuestasPendientes;
    }
    
    
    public boolean comenzarEncuesta() throws IOException {
        
        LoginController loginController = getLoginController();
        Persona persona = loginController.getPersonaActual();
        Estudiante estud = getEstudianteController().getEstudiantePorPersona(persona);
        
        System.out.println("encuesta recibida encuesta:"+selected);
        if (selected == null) {
            System.out.println("Crear encuesta nueva");
//            if (sinterminar != null) {
//                System.out.println("encontro sinterminar");
//                RequestContext requestContext = RequestContext.getCurrentInstance();
//                requestContext.execute("PF('dialog_encuesta_no_terminada').show()");
//                return;
//            }
            crearEncuesta();
        }
        pasoActivo = -1;
        contadorEncuesta = -1;
                
        detener_reloj = true;
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
            String rutaGeneral = Vistas.getRutaGeneral();
            FacesContext.getCurrentInstance().getExternalContext().redirect(rutaGeneral + "/vistas/encuesta/encuestaInfoPersonal.xhtml");

        } catch (IOException e) {
            System.out.println("No se ha encontrado la persona o estudiante correspondiente.");
//            e.printStackTrace();
        }
        return false;
    }

    /**
     * @deprecated 
     * @param i
     * @param personalidad
     * @return 
     */
    public String resultado_personalidad(int i, String personalidad) {
        if (personalidad == null || "".equals(personalidad)) {
            return null;
        }
        String result_personalidad = personalidad;
        String url = "img/resultado_test_personalidad/" + i + result_personalidad.charAt(i) + ".jpg";

        return url;

    }
    
    /**
     * @deprecated 
     * @param i
     * @param personalidad
     * @return 
     */
    public String resultado_personalidad_descripcion(int i, String personalidad) {
        if (personalidad == null || "".equals(personalidad)) {
            return "";
        }
//        System.out.println("resultado_personalidad_descripcion: "+personalidad);
        Character result_personalidad = null;
        try{
            result_personalidad = personalidad.charAt(i);
        }catch (Exception e) {
            System.out.println("personalidad vacia");
        }
        if (null == result_personalidad) {
            return null;
        } else {
            String codigo_personalidad = "" + i + result_personalidad;
            switch (codigo_personalidad) {
                case "0E":
                    return "Extrovertido";
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
    
    public void actualizarInformacionPersonal() {
    }
    
    public void setGrado(Grado grado) {
        LoginController loginController = getLoginController();
        if(loginController.isEstudiante()) {
            Estudiante estud = loginController.getEstudiante();
            EstudianteGradoController estudGradoCtrl = getEstudianteGradoController();
            EstudianteGrado estudGrado = estudGradoCtrl.prepareCreate();
            estudGrado.setGrado(grado);
            estudGrado.setEstudiante(estud);
            estudGradoCtrl.update();
            
            this.getSelected().setGrado(grado);
            this.update();
            
            loginController.actualizarEstudiante();
            
            System.out.println("grado actualizado" + grado);
        }
    }
    
    public Grado getGrado() {
        LoginController loginController = getLoginController();
        Grado grado = null;
        System.out.println("grado isEstudiante: " + loginController.isEstudiante());
        
        if(loginController.isEstudiante()) {
            grado = loginController.utimoGradoObj();
            System.out.println("ultimo grado: " + grado);
        }
        return grado;
    }

    public void create() {
        selected = (Encuesta) persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EncuestaCreated"), selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public Encuesta update() {
        selected = (Encuesta) persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EncuestaUpdated"), selected);
        return selected;
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EncuestaDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    /************************************************************************
     * GETTERS AND SETTERS METHODS
     ************************************************************************/
    
    public boolean esDesarrollo() {
        return Utilidades.esDesarrollo();
    }
    
    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo_eval) {
        this.tiempo = tiempo_eval;
    }
    
    public void setModel(DefaultMenuModel model) {
        this.model = model;
    }

    /**
     * @param listaEncuestas 
     */
    public void setListaEncuestas(List<EncuestaControllerInterface> listaEncuestas) {
        this.listaEncuestasController = listaEncuestas;
    }
    
    public void setItems(List<Encuesta> items) {
        this.items = items;
    }

    public Encuesta getSelected() {
        return selected;
    }

    public void setSelected(Encuesta selected) {
        System.out.println("nuevo seleccionado:"+selected);
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
    
    public boolean isEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(boolean isEvaluacion) {
        this.evaluacion = isEvaluacion;
    }

    public int getIdEncuesta() {
        Integer valor = ejbFacade.autogenerarIdEncuesta();
        return valor == null ? 1 : valor;
    }

    public List<Encuesta> actualesItems() {
        return items;
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

    /************************************************************************
     * CONVERTER
     ************************************************************************/
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
