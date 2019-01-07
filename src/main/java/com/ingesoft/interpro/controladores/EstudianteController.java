package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Persona;
import com.ingesoft.interpro.facades.EstudianteFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;

@ManagedBean(name = "estudianteController")
@SessionScoped
public class EstudianteController extends Controller implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.EstudianteFacade ejbFacade;
    private List<Estudiante> items = null;
    private Estudiante selected;
    private boolean editar;
    private int pasoActual;
    private boolean skip;
    private int number;
    private int puntos;

    public EstudianteController() {
        pasoActual = 0;
        puntos = 0;
    }

    public boolean isEditar() {
        return editar;
    }
    
    public int getPasoActual() {
        return pasoActual;
    }

    public void setPasoActual(int pasoActual) {
        this.pasoActual = pasoActual;
    }

    public Estudiante getSelected() {
        return selected;
    }

    public void setSelected(Estudiante selected) {
        this.selected = selected;
    }

    public boolean puedeAnteriorPaso() {
        return pasoActual > 0;
    }

    public boolean puedeSiguientePaso() {
        return pasoActual < (10);
    }

    public int anteriorPaso() {
        pasoActual -= 1;
        return pasoActual;
    }

    public int siguientePaso(ActionEvent actionEvent) {
        System.out.println("siguientes paso");
        pasoActual += 1;
        return pasoActual;
    }

    @Override
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    protected EstudianteFacade getFacade() {
        return ejbFacade;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public PersonaController getPersonaController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        PersonaController controllerPersona = (PersonaController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "personaController");
        return controllerPersona;
    }

    public Estudiante prepareCreate() {
        selected = new Estudiante();
        PersonaController personaController = getPersonaController();
        Persona persona = personaController.prepareCreate();
        selected.setIdPersona(persona);
        selected.getIdPersona().getIdUsuario().setClave("Ninguna");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('EstudianteEditDialog').show()");
        editar=false;
        return selected;
    }

    public Estudiante prepareUpdate() {
        PersonaController personaController = getPersonaController();
//        personaController.prepareUpdate(selected.getIdPersona());
        editar=true;
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EstudianteCreated"), selected);
        selected = null;
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EstudianteUpdated"), selected);
        PersonaController controllerPersona = getPersonaController();
        controllerPersona.update();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EstudianteDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Estudiante> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public Estudiante getEstudiante(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public Estudiante getEstudiantePorIdUsuario(java.lang.Integer idUsuario) {
        return getFacade().findPorIdUsuario(idUsuario);
    }

    public List<Estudiante> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Estudiante> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Estudiante.class)
    public static class EstudianteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EstudianteController controller = (EstudianteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "estudianteController");
            return controller.getEstudiante(getKey(value));
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
            if (object instanceof Estudiante) {
                Estudiante o = (Estudiante) object;
                return getStringKey(o.getIdEstudiante());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Estudiante.class.getName()});
                return null;
            }
        }

    }

}
