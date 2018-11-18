package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.RespuestaPersonalidad;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.PreguntaPersonalidad;
import com.ingesoft.interpro.facades.RespuestaPersonalidadFacade;
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

@ManagedBean(name = "respuestaPersonalidadController")
@SessionScoped
public class RespuestaPersonalidadController implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.RespuestaPersonalidadFacade ejbFacade;
    private List<RespuestaPersonalidad> items = null;
    private RespuestaPersonalidad selected;

    public RespuestaPersonalidadController() {
    }

    public RespuestaPersonalidad getSelected() {
        return selected;
    }

    public void setSelected(RespuestaPersonalidad selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getRespuestaPersonalidadPK().setIdEncuesta(selected.getEncuesta().getIdEncuesta());
        selected.getRespuestaPersonalidadPK().setIdPreguntaPersonalidad(selected.getPreguntaPersonalidad().getIdPreguntaPersonalidad());
    }

    protected void initializeEmbeddableKey() {
        selected.setRespuestaPersonalidadPK(new com.ingesoft.interpro.entidades.RespuestaPersonalidadPK());
    }

    private RespuestaPersonalidadFacade getFacade() {
        return ejbFacade;
    }

    public RespuestaPersonalidad prepareCreate() {
        selected = new RespuestaPersonalidad();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaPersonalidadCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RespuestaPersonalidadUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RespuestaPersonalidadDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RespuestaPersonalidad> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
//  public List<RespuestaPersonalidad> getRespuestasPersonalidad(Encuesta encuesta) {
//
//        if (encuesta != null && items == null) {
//            items = getFacade().findAll(encuesta.getIdEncuesta());
//        }
//        return items;
//    }
  
    /**
     * obtiene las respuestas de un determinado grupo
     *
     * @param grupo
     * @param tamGrupo
     * @return
     */
    public List<RespuestaPersonalidad> getGrupoItems(int grupo, int tamGrupo) {
        List<RespuestaPersonalidad> listaRespuestas = null;
        if (items != null) {
            listaRespuestas = new ArrayList<>();
            for (int i = tamGrupo * (grupo - 1); i < tamGrupo * grupo; i++) {
                if (i >= 0 && i < items.size()) {
                    listaRespuestas.add(items.get(i));
                } else {
                    break;
                }
            }
        }
        return listaRespuestas;
    }

    public List<RespuestaPersonalidad> actualizarRespuestas() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/intereses_profesionales_frontend_JSF/faces/vistas/encuesta/resumen.xhtml");
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

    public List<RespuestaPersonalidad> prepararRespuestas(List<PreguntaPersonalidad> preguntas, Encuesta encuesta) {
        System.out.println("encuesta: " + encuesta);
        System.out.println("preguntas: " + preguntas);
        items = new ArrayList<>(preguntas.size());
        for (PreguntaPersonalidad pregunta : preguntas) {
            selected = new RespuestaPersonalidad(pregunta.getIdPreguntaPersonalidad(), encuesta.getIdEncuesta());
            selected.setPreguntaPersonalidad(pregunta);
            selected.setEncuesta(encuesta);
            items.add(selected);
        }
        (new HiloGuardado()).start();
        return items;
    }

    public class HiloGuardado extends Thread {

        public HiloGuardado() {
        }

        @Override
        public void run() {
            for (RespuestaPersonalidad respuesta : items) {
                getFacade().edit(respuesta);
            }
            System.out.println("----Termino de guardar Respuestas");
        }

    }

    public RespuestaPersonalidad getRespuestaPersonalidad(com.ingesoft.interpro.entidades.RespuestaPersonalidadPK id) {
        return getFacade().find(id);
    }

    public List<RespuestaPersonalidad> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RespuestaPersonalidad> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RespuestaPersonalidad.class)
    public static class RespuestaPersonalidadControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RespuestaPersonalidadController controller = (RespuestaPersonalidadController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "respuestaPersonalidadController");
            return controller.getRespuestaPersonalidad(getKey(value));
        }

        com.ingesoft.interpro.entidades.RespuestaPersonalidadPK getKey(String value) {
            com.ingesoft.interpro.entidades.RespuestaPersonalidadPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.ingesoft.interpro.entidades.RespuestaPersonalidadPK();
            key.setIdPreguntaPersonalidad(Integer.parseInt(values[0]));
            key.setIdEncuesta(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(com.ingesoft.interpro.entidades.RespuestaPersonalidadPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdPreguntaPersonalidad());
            sb.append(SEPARATOR);
            sb.append(value.getIdEncuesta());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RespuestaPersonalidad) {
                RespuestaPersonalidad o = (RespuestaPersonalidad) object;
                return getStringKey(o.getRespuestaPersonalidadPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RespuestaPersonalidad.class.getName()});
                return null;
            }
        }

    }

}
