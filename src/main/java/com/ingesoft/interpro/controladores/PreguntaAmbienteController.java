package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.PreguntaAmbiente;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.RespuestaAmbiente;
import com.ingesoft.interpro.entidades.Usuario;
import com.ingesoft.interpro.facades.PreguntaAmbienteFacade;

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

//    public boolean skip;
    @EJB
    private com.ingesoft.interpro.facades.PreguntaAmbienteFacade ejbFacade;
    private List<PreguntaAmbiente> items = null;
    private PreguntaAmbiente selected;

    public PreguntaAmbienteController() {

    }

    public PreguntaAmbiente getSelected() {
        return selected;
    }

    public void setSelected(PreguntaAmbiente selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PreguntaAmbienteFacade getFacade() {
        return ejbFacade;
    }

    public PreguntaAmbiente prepareCreate() {
        selected = new PreguntaAmbiente();
        initializeEmbeddableKey();
        return selected;
    }

    public void preparePreguntas(Usuario usuario, Encuesta encuesta) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elOtroResolver = facesContext.getApplication().getELResolver();
        RespuestaAmbienteController respuestaController = (RespuestaAmbienteController) elOtroResolver.getValue(facesContext.getELContext(), null, "respuestaAmbienteController");
        getItems();
        respuestaController.prepararRespuestas(items, encuesta);
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PreguntaAmbienteCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PreguntaAmbienteUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PreguntaAmbienteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PreguntaAmbiente> getItems() {
        if (items == null) {
            items = getFacade().findAll();
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

    public PreguntaAmbiente getPreguntaAmbiente(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<PreguntaAmbiente> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PreguntaAmbiente> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PreguntaAmbiente.class)
    public static class PreguntaAmbienteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PreguntaAmbienteController controller = (PreguntaAmbienteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "preguntaAmbienteController");
            return controller.getPreguntaAmbiente(getKey(value));
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
            if (object instanceof PreguntaAmbiente) {
                PreguntaAmbiente o = (PreguntaAmbiente) object;
                return getStringKey(o.getIdPreguntaAmbiente());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PreguntaAmbiente.class.getName()});
                return null;
            }
        }

    }

    public List<Definicion> getItemDefinicion() {

        ArrayList<Definicion> itemsDefinicion;
        itemsDefinicion = new ArrayList();
        itemsDefinicion.add(new Definicion(
                "<h6> Lea con atención el siguiente enunciado y seleccione la respuesta que considere correctacorrecta: </h6></br>"
                + "<h4>Según la afirmación anterior, cual de las siguientes opciones no describe ocupaciones del ambiente de tipo Realista: </h4>"
                + "<p> A las personas con intereses realistas les gustan actividades que incluyan problemas y soluciones prácticas y directas. Con frecuencia, a las personas con intereses realistas no les gustan las carreras que requieren trabajo de oficina o trabajar junto a otras personas.\n"
                + "                Les gusta: </p>\n"
                + "            <p>\n"
                + "                <ul>\n"
                + "                    <li>Trabajar con plantas y animales</li>\n"
                + "                    <li>Materiales reales, tales como madera, herramientas y maquinaria</li>\n"
                + "                    <li>Trabajo al aire libres</li>\n"
                + "                </ul>\n"
                + "            </p>"));
        itemsDefinicion.add(new Definicion("<h6> Lea con atención el siguiente enunciado y seleccione la respuesta que considere correctacorrecta: </h6></br>"
                + "<h4>El Ambiente de profesiones u ocupaciones de tipo Artístico se define así: </h4>"
                + "<p> A las personas con intereses artísticos, les gustan trabajos relacionados con el lado artístico de las cosas, tales como actuar, música, arte y diseño. "
                + "Les gusta: </p>\n"
                + "            <p>\n"
                + "                <ul>\n"
                + "                    <li>Creatividad en su trabajo</li>\n"
                + "                    <li>Trabajo que puede hacerse sin seguir una serie de reglas</li>\n"
                + "                </ul>\n"
                + "            </p>"));
        itemsDefinicion.add(new Definicion("<h6> Lea con atención el siguiente enunciado y seleccione la respuesta que considere correctacorrecta: </h6></br>"
                + "<h4>El Ambiente de profesiones u ocupaciones de tipo de investigación se define así: </h4>"
                + "<p> A las personas con intereses de investigación, le gustan trabajos relacionados con ideas y razonamiento en lugar de actividades fisicas o liderazgo de personal. "
                + "Les gusta: </p>\n"
                + "            <p>\n"
                + "                <ul>\n"
                + "                    <li>Búsqueda de hechos</li>\n"
                + "                    <li>Resolución de problemas</li>\n"
                + "                </ul>\n"
                + "            </p>"));

        itemsDefinicion.add(new Definicion("<h6> Lea con atención el siguiente enunciado y seleccione la respuesta que considere correctacorrecta: </h6></br>"
                + "<h4>El Ambiente de profesiones u ocupaciones de tipo Social se define así: </h4>"
                + "<p> A las personas con intereses sociales les gusta trabajar con otros para ayudarlos a aprender y a desarrollar su conocimiento. Les gusta trabajar con personas más que trabajar con objetos, maquinaria o información. "
                + "Les gusta: </p>\n"
                + "            <p>\n"
                + "                <ul>\n"
                + "                    <li>Enseñanza</li>\n"
                + "                    <li>Dar consejos</li>\n"
                + "                    <li>Ayudar y dar consejos a otros</li>\n"
                + "                </ul>\n"
                + "            </p>"));
        itemsDefinicion.add(new Definicion("<h6> Lea con atención el siguiente enunciado y seleccione la respuesta que considere correctacorrecta: </h6></br>"
                + "<h4>El Ambiente de profesiones u ocupaciones de tipo Emprendedor se define así: </h4>"
                + "<p> A las personas con intereses empresariales les gustan trabajos que tengan que ver con el comienzo y continuación de proyectos de negocios. A estas personas les gusta tomar acciones en lugar de pensar sobre las cosas. "
                + "Les gusta: </p>\n"
                + "            <p>\n"
                + "                <ul>\n"
                + "                    <li>Persuadir y dirigir personal</li>\n"
                + "                    <li>Tomar decisiones</li>\n"
                + "                    <li>Tomar riesgos para obtener ganancias</li>\n"
                + "                </ul>\n"
                + "            </p>"));
        itemsDefinicion.add(new Definicion("<h6> Lea con atención el siguiente enunciado y seleccione la respuesta que considere correctacorrecta: </h6></br>"
                + "<h4>El Ambiente de profesiones u ocupaciones de tipo Convencional se define así: </h4>"
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

    public List<Definicion> getItemPreguntaEvaluacion() {

        ArrayList<Definicion> itemsPreguntaEvaluacion;
        itemsPreguntaEvaluacion = new ArrayList();
        itemsPreguntaEvaluacion.add(new Definicion(
                "<p>Lea con atención el siguiente enunciado y seleccione la respuesta que considere correctacorrecta: </p><br/>\n"
                + "     <h5>Según la afirmación anterior, cual de las siguientes opciones no describe ocupaciones del ambiente de tipo Realista: </h5><br/>"));
        itemsPreguntaEvaluacion.add(new Definicion("<p>Lea con atención el siguiente enunciado y seleccione la respuesta que considere correctacorrecta: </p><br/>"
                + "<h5>Según la afirmación anterior, seleccione la opción adecuada que describe ocupaciones del Ambiente de tipo investigativo: </h5><br/>"));
        itemsPreguntaEvaluacion.add(new Definicion("<p>Lea con atención el siguiente enunciado y seleccione la respuesta que considere correctacorrecta: </p><br/>"
                + "<h5>Según la afirmación anterior, seleccione la opción adecuada que describe ocupaciones del Ambiente de tipo Artístico: </h5><br/>"));
        itemsPreguntaEvaluacion.add(new Definicion("<p>Lea con atención el siguiente enunciado y seleccione la respuesta que considere correctacorrecta: </p><br/>"
                + "<h5>Según la afirmación anterior, seleccione la opción adecuada que no describe ocupaciones del Ambiente de tipo Social: </h5><br/>"));
        itemsPreguntaEvaluacion.add(new Definicion("<p>Lea con atención el siguiente enunciado y seleccione la respuesta que considere correctacorrecta: </p><br/>"
                + "<h5>Según la afirmación anterior, seleccione la opción adecuada que describe ocupaciones del Ambiente de tipo Emprendedor: </h5>"));
        itemsPreguntaEvaluacion.add(new Definicion("<p>Lea con atención el siguiente enunciado y seleccione la respuesta que considere correctacorrecta: </p><br/>"
                + "<h5>Según la afirmación anterior, seleccione la opción adecuada que describe ocupaciones del Ambiente de tipo Convencional </h5>"));

        return itemsPreguntaEvaluacion;
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

}
