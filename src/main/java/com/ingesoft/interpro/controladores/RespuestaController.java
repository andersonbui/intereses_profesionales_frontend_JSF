package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Respuesta;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.Pregunta;
import com.ingesoft.interpro.facades.RespuestaFacade;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "respuestaController")
@SessionScoped
public class RespuestaController implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.RespuestaFacade ejbFacade;
    private List<Respuesta> items = null;
    private List<Respuesta> respuestas = null;
    private Respuesta selected;

    public RespuestaController() {
    }

    public Respuesta getSelected() {
        return selected;
    }

    public void setSelected(Respuesta selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getRespuestaPK().setIdPregunta(selected.getPregunta().getIdPregunta());
        selected.getRespuestaPK().setIdEncuesta(selected.getEncuesta().getIdEncuesta());
    }

    protected void initializeEmbeddableKey() {
        selected.setRespuestaPK(new com.ingesoft.interpro.entidades.RespuestaPK());
    }

    private RespuestaFacade getFacade() {
        return ejbFacade;
    }

    public Respuesta prepareCreate() {
        selected = new Respuesta();
        initializeEmbeddableKey();
        return selected;
    }

    public List<Respuesta> prepararRespuestas(List<Pregunta> preguntas, Encuesta encuesta) {
        System.out.println("encuesta: " + encuesta);
        System.out.println("preguntas: " + preguntas);
        respuestas = new ArrayList(preguntas.size());
        for (Pregunta pregunta : preguntas) {
            selected = new Respuesta(pregunta.getIdPregunta(), encuesta.getIdEncuesta());
            selected.setPregunta(pregunta);
            selected.setEncuesta(encuesta);
            respuestas.add(selected);
            create();
        }
        return respuestas;
    }

    public List<Respuesta> getItems(int idEncuesta, int idTipo) {
        if (respuestas == null) {
            respuestas = getFacade().findAllByIdEncuestaIdTipo(idEncuesta, idTipo);
        }
        return respuestas;
    }

    /**
     * obtiene las respuestas de un determinado grupo
     *
     * @param grupo
     * @param tamGrupo
     * @return
     */
    public List<Respuesta> getGrupoItemsPersonalidad(int grupo, int tamGrupo) {
        List<Respuesta> listaRespuestas = null;
        if (respuestas != null) {
            listaRespuestas = new ArrayList<>();
            for (int i = tamGrupo * (grupo - 1); i < tamGrupo * grupo; i++) {
                if (i >= 0 && i < respuestas.size()) {
                    listaRespuestas.add(respuestas.get(i));
                } else {
                    break;
                }
            }
        }
        return listaRespuestas;
    }
    
    public String obtenerImagen(Respuesta respuesta) {
        String url = "img/ambiente/"+respuesta.getPregunta().getUrlImagen();
        return url;
    }
    public List<Respuesta> getRespuestasPersonalidad(Encuesta encuesta) {

        if (encuesta != null && respuestas == null) {
            respuestas = getFacade().findAllByIdEncuestaIdTipo(encuesta.getIdEncuesta(), 1);
        }
        return respuestas;
    }

    public List<Respuesta> actualizarRespuestas(List<Respuesta> respuestas) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/intereses_profesionales_frontend_JSF/faces/vistas/encuesta/resumen.xhtml");
        return respuestas;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RespuestaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Respuesta> getItems(int idEncuesta) {
        if (items == null) {
            items = getFacade().findAllByIdEncuesta(idEncuesta);
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

    public Respuesta getRespuesta(com.ingesoft.interpro.entidades.RespuestaPK id) {
        return getFacade().find(id);
    }

    public List<Respuesta> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Respuesta> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Respuesta.class)
    public static class RespuestaControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RespuestaController controller = (RespuestaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "respuestaController");
            return controller.getRespuesta(getKey(value));
        }

        com.ingesoft.interpro.entidades.RespuestaPK getKey(String value) {
            com.ingesoft.interpro.entidades.RespuestaPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.ingesoft.interpro.entidades.RespuestaPK();
            key.setIdPregunta(Integer.parseInt(values[0]));
            key.setIdEncuesta(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(com.ingesoft.interpro.entidades.RespuestaPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdPregunta());
            sb.append(SEPARATOR);
            sb.append(value.getIdEncuesta());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Respuesta) {
                Respuesta o = (Respuesta) object;
                return getStringKey(o.getRespuestaPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Respuesta.class.getName()});
                return null;
            }
        }

    }

}
