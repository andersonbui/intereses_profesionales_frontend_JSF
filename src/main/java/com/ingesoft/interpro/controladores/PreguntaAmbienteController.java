package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.PreguntaAmbiente;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.controladores.util.Variables;
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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "preguntaAmbienteController")
@SessionScoped
public class PreguntaAmbienteController implements Serializable {

    private static final long serialVersionUID = 1L;
    int indiceActual;

//    public boolean skip;
    @EJB
    private com.ingesoft.interpro.facades.PreguntaAmbienteFacade ejbFacade;
    private List<PreguntaAmbiente> items = null;
    private List<String> listaPreguntasAmbiente = null;
    private PreguntaAmbiente selected;
    boolean respuesta;
    int puntosPreguntaAmbiente = 0;
    int[] vecIdImages = {1, 3, 5, 7, 9, 11, 31, 33, 35, 37, 39, 41, 61, 63, 65, 67, 69, 71, 91, 93, 95, 97, 99, 101, 121, 123, 125, 127, 129, 131, 151, 153, 155, 157, 159, 161};
    int[] vecIdPreguntas = {1, 2, 5, 13, 14, 37, 38, 61, 39, 86, 40, 42, 43, 45, 48, 72, 31, 32, 43, 44, 73, 75, 78, 130, 139, 158, 160, 162, 167, 165};

    public PreguntaAmbienteController() {
        indiceActual = 0;
    }

    @PostConstruct
    public void init() {
        selected = getPreguntaAmbiente(vecIdImages[indiceActual]);
        listaPreguntasAmbiente = new ArrayList<>();
        obtenerPreguntasAmbiente();
    }

    public void addMessage(String summary, boolean success) {
        FacesMessage message = new FacesMessage(success ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_ERROR, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<String> getListaPreguntasAmbiente() {
        return listaPreguntasAmbiente;
    }

    public void setListaPreguntasAmbiente(List<String> listaPreguntasAmbiente) {
        this.listaPreguntasAmbiente = listaPreguntasAmbiente;
    }

    public int getPuntosPreguntaAmbiente() {
        return puntosPreguntaAmbiente;
    }

    public void setPuntosPreguntaAmbiente(int puntosPreguntaAmbiente) {
        this.puntosPreguntaAmbiente = puntosPreguntaAmbiente;
    }

    public int[] getVecIdImages() {
        return vecIdImages;
    }

    public void setVecIdImages(int[] vecIdImages) {
        this.vecIdImages = vecIdImages;
    }

    public boolean isRespuesta() {
        return respuesta;
    }

    public void setRespuesta(boolean respuesta) {
        this.respuesta = respuesta;
    }

    public int getIndiceActual() {
        return indiceActual;
    }

    public void setIndiceActual(int indiceActual) {
        this.indiceActual = indiceActual;
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
    
    public void buttonAction(boolean resp) {
        if (resp == true) {
            addMessage("Correcto!!", true);
        } else {
            addMessage("Incorrecto!!", false);
        }
    }

    public String obtenerImagenActual() {
        String url = Variables.ubicacionImagenes + getSelected().getUrlImagen();
        return url;
    }
    int contAmbiente = 0;

    public List<String> obtenerPreguntasAmbiente() {
        for (int i = 0; i < vecIdPreguntas.length; i++) {
            listaPreguntasAmbiente.add(getPreguntaAmbiente(vecIdPreguntas[i]).getEnunciado());
            contAmbiente++;
        }
        System.out.println("listaPreguntasAmbiente: " + listaPreguntasAmbiente);
        return listaPreguntasAmbiente;
    }

    public List<String> getGrupoItems(int numGrupo) {
        listaPreguntasAmbiente = new ArrayList();
        getItems();
        int tamGrupo = 4;
        for (int i = tamGrupo * (numGrupo - 1); i < tamGrupo * numGrupo; i++) {
            if (i >= 0 && i < vecIdPreguntas.length) {
                listaPreguntasAmbiente.add(items.get(i).getEnunciado());
            } else {
                break;
            }
        }
        return listaPreguntasAmbiente;
    }

    public void nextImagen() {
        if (indiceActual < vecIdImages.length - 1) {
            indiceActual++;
            selected = getPreguntaAmbiente(vecIdImages[indiceActual]);
        }
        System.out.println("next");
        System.out.println(indiceActual);
    }

    public void previousImagen() {
        if (indiceActual > 0) {
            indiceActual--;
            selected = getPreguntaAmbiente(vecIdImages[indiceActual]);
        }
        System.out.println("previo");
        System.out.println(indiceActual);
    }

    public void comprobarRespuesta(int id) {
        if (selected.getIdTipoAmbiente().getIdTipoAmbiente().equals(id)) {
            respuesta = true;
            puntosPreguntaAmbiente++;
            System.out.println("Respuesta correcta:" + respuesta);
        } else {
            respuesta = false;
            puntosPreguntaAmbiente--;
            System.out.println("Respuesta incorrecta:" + respuesta);
        }
        buttonAction(respuesta);
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

}

