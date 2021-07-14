package com.ingesoft.suideal.encuesta.estilos_aprendizaje.controladores;

import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaEstilo;
import com.ingesoft.interpro.controladores.util.RespuestaControllerAbstract;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.EncuestaEstilosAprendizaje;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaEstiloPK;
import com.ingesoft.interpro.facades.RespuestaEstiloFacade;

import java.util.List;
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
public class RespuestaEstilosController extends RespuestaControllerAbstract <
            RespuestaEstilo, 
            RespuestaEstiloFacade, 
            EncuestaEstilosAprendizaje> {

    @EJB
    private com.ingesoft.interpro.facades.RespuestaEstiloFacade ejbFacade;
    
    public RespuestaEstilosController() {
    }

    public void inicializar(Encuesta selected) {

    }


    protected void initializeEmbeddableKey() {
    }

    @Override
    protected RespuestaEstiloFacade getFacade() {
        return ejbFacade;
    }

    @Override
    public RespuestaEstilo prepareCreate() {
        setSelected(new RespuestaEstilo());
        initializeEmbeddableKey();
        return getSelected();
    }

    /************************************************************************
     * GETTERS AND SETTERS METHODS
     ************************************************************************/

    /**
     * 
     * @return 
     */
    @Override
    public RespuestaEstiloFacade getEjbFacade() {
        return ejbFacade;
    }
    /**
     * 
     * @return 
     */
    @Override
    public String getStringCreated(){
        return "RespuestaEstiloCreated";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringUpdated(){
        return "RespuestaEstiloUpdated";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringDeleted(){
        return "RespuestaEstiloDeleted";
    }
    
    
    public List<RespuestaEstilo> getItemsXEncuesta(EncuestaEstilosAprendizaje encuestaEstilosAprendizaje) {
        return getFacade().getItemsXEncuesta(encuestaEstilosAprendizaje);
    }
    
    public RespuestaEstilo getRespuestaEstilo(RespuestaEstiloPK id) {
        return getFacade().find(id);
    }

    /**
     * @param encuestaEstilosAprendizaje
     * @return 
     */
    @Override
    public List<RespuestaEstilo> obtenerTodosPorEncuesta(EncuestaEstilosAprendizaje encuestaEstilosAprendizaje) {
        return getEjbFacade().getItemsXEncuesta(encuestaEstilosAprendizaje);
    }
    
    /************************************************************************
     * CONVERTER
     ************************************************************************/
    
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

        com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaEstiloPK getKey(String value) {
            com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaEstiloPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaEstiloPK();
            key.setIdPreguntaEstilosAprendizaje(Integer.parseInt(values[0]));
            key.setIdEncuestaEstilosAprendizaje(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaEstiloPK value) {
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
  
}
