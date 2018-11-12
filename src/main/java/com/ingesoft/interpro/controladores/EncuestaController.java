package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.controladores.util.Vistas;
import com.ingesoft.interpro.entidades.Estudiante;
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
import javax.ejb.EJBException;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "encuestaController")
@SessionScoped
public class EncuestaController implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.EncuestaFacade ejbFacade;
    private List<Encuesta> items = null;
    private Encuesta selected;
    private int pasoActivo;

    public EncuestaController() {
    }

    public Encuesta getSelected() {
        return selected;
    }

    public void setSelected(Encuesta selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EncuestaFacade getFacade() {
        return ejbFacade;
    }

    public int getPasoActivo() {
        return pasoActivo;
    }

    public void setPasoActivo(int pasoActivo) {
        this.pasoActivo = pasoActivo;
    }

    public void pasoPreguntasAmbiente() throws IOException {
        this.pasoActivo = 1;
        FacesContext.getCurrentInstance().getExternalContext().redirect("/intereses_profesionales_frontend_JSF/faces/vistas/pregunta/preguntasAmbiente.xhtml");
    }

    public void pasoPreguntasPersonalidad() throws IOException {
        this.pasoActivo = 2;
        FacesContext.getCurrentInstance().getExternalContext().redirect("/intereses_profesionales_frontend_JSF/faces/vistas/pregunta/preguntasPersonalidad.xhtml");
    }

    public void pasoResumen() throws IOException {
        this.pasoActivo = 3;
        FacesContext.getCurrentInstance().getExternalContext().redirect("/intereses_profesionales_frontend_JSF/faces/vistas/encuesta/resumen.xhtml");
    }

    public void finalizar() throws IOException {
        this.pasoActivo = 0;
        FacesContext.getCurrentInstance().getExternalContext().redirect(Vistas.verPaginaPrincipal());
    }

    public int getIdEncuesta() {
        Integer valor = ejbFacade.autogenerarIdEncuesta();
        return valor == null ? 1 : valor;
    }

    /**
     * Prepara y crea una encuesta con fecha y esudiante
     *
     * @throws java.io.IOException
     */
    public void prepararYCrear() throws IOException {
        pasoActivo = 0;
        // @TODO : Falta obtener el usuario
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELResolver elOtroResolver = facesContext.getApplication().getELResolver();
        LoginController loginController = (LoginController) elOtroResolver.getValue(facesContext.getELContext(), null, "loginController");
        Usuario usu = loginController.getActual();
        try {
            Estudiante estud = usu.getPersonaList().get(0).getEstudianteList().get(0);
            selected = new Encuesta();
            initializeEmbeddableKey();
            selected.setFecha(new Date());
            selected.setIdEstudiante(estud);
            selected.setIdEncuesta(getIdEncuesta());
            System.out.println("antes encuesta creada: " + selected);
            create();
//            selected = getEncuesta(selected.toString());
            System.out.println("despues encuesta creada: " + selected);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/intereses_profesionales_frontend_JSF/faces/vistas/encuesta/welcomePrimefaces.xhtml");
        } catch (Exception e) {
            System.out.println("No se ha encontrado la persona o estudiante correspondiente.");
            e.printStackTrace();
        }

    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EncuestaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EncuestaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EncuestaDeleted"));
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

    private void persist(PersistAction persistAction, String successMessage) {
        System.out.println("encuesta seleccionada:  " + selected);
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
