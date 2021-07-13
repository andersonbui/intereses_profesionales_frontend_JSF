package com.ingesoft.suideal.encuesta.estilos_aprendizaje.controladores;

import com.ingesoft.interpro.controladores.Controllers;
import com.ingesoft.interpro.entidades.RespuestaEstilo;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.EncuestaEstilosAprendizaje;
import com.ingesoft.interpro.entidades.RespuestaEstiloPK;
import com.ingesoft.interpro.facades.RespuestaEstiloFacade;
import java.io.IOException;

import java.io.Serializable;
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

@ManagedBean(name = "respuestaEstilosController")
@SessionScoped
public class RespuestaEstilosController extends Controllers implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.RespuestaEstiloFacade ejbFacade;
    private List<RespuestaEstilo> items = null;
    private RespuestaEstilo selected;
    
    public RespuestaEstilosController() {
    }

    public void inicializar(Encuesta selected) {

    }

    public RespuestaEstilo getSelected() {
        return selected;
    }

    public void setSelected(RespuestaEstilo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    public List<RespuestaEstilo> getItems() {
        items = getFacade().findAll();
        return items;
    }
    
    protected void initializeEmbeddableKey() {
    }

    @Override
    protected RespuestaEstiloFacade getFacade() {
        return ejbFacade;
    }

    public RespuestaEstilo prepareCreate() {
        selected = new RespuestaEstilo();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaEstiloCreated"), selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaEstiloUpdated"), selected);
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RespuestaEstiloDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RespuestaEstilo> obtenerTodosPorEncuesta(EncuestaEstilosAprendizaje encuestaEstilosAprendizaje) {
        return getFacade().getItemsXEncuesta(encuestaEstilosAprendizaje);
    }
    
    public List<RespuestaEstilo> getItemsXEncuesta(EncuestaEstilosAprendizaje encuestaEstilosAprendizaje) {
        return getFacade().getItemsXEncuesta(encuestaEstilosAprendizaje);
    }
    
    public RespuestaEstilo getRespuestaEstilo(RespuestaEstiloPK id) {
        return getFacade().find(id);
    }

    public List<RespuestaEstilo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RespuestaEstilo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RespuestaEstilo.class)
    public static class RespuestaEstiloControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RespuestaEstilosController controller = (RespuestaEstilosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "respuestaEstilosController");
            return controller.getRespuestaEstilo(getKey(value));
        }

        com.ingesoft.interpro.entidades.RespuestaEstiloPK getKey(String value) {
            com.ingesoft.interpro.entidades.RespuestaEstiloPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.ingesoft.interpro.entidades.RespuestaEstiloPK();
            key.setIdPreguntaEstilosAprendizaje(Integer.parseInt(values[0]));
            key.setIdEncuestaEstilosAprendizaje(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(com.ingesoft.interpro.entidades.RespuestaEstiloPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdPreguntaEstilosAprendizaje());
            sb.append(SEPARATOR);
            sb.append(value.getIdEncuestaEstilosAprendizaje());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RespuestaEstilo) {
                RespuestaEstilo o = (RespuestaEstilo) object;
                return getStringKey(o.getRespuestaEstiloPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RespuestaEstilo.class.getName()});
                return null;
            }
        }

    }
    
    public List<RespuestaEstilo> actualizarTodasRespuestas(List<RespuestaEstilo> respuestas) throws IOException, InterruptedException {
        for (RespuestaEstilo item : respuestas) {
            this.getFacade().edit(item);
        }
        return respuestas;
    }
}
