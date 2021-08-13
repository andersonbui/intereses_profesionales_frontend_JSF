package com.ingesoft.suideal.encuesta.estilos_aprendizaje.controladores;

import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaPorEstilo;
import com.ingesoft.interpro.controladores.util.RespuestaControllerAbstract;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.EncuestaEstilosAprendizaje;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaPorEstiloPK;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.facade.RespuestaPorEstiloFacade;

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

@ManagedBean(name = "respuestaPorEstilosController")
@SessionScoped
public class RespuestaPorEstilosController extends RespuestaControllerAbstract <
            RespuestaPorEstilo, 
            RespuestaPorEstiloFacade, 
            EncuestaEstilosAprendizaje> {

    @EJB
    private RespuestaPorEstiloFacade ejbFacade;
    
    public RespuestaPorEstilosController() {
        
    }

    public void inicializar(Encuesta selected) {

    }

    @Override
    protected void setEmbeddableKeys() {
        selected.getRespuestaPorEstiloPK().setIdEncuestaEstilosAprendizaje(selected.getEncuestaEstilosAprendizaje().getIdEncuesta());
        selected.getRespuestaPorEstiloPK().setIdTipoEstilo(selected.getTipoEstilo().getId());
    }

    protected void initializeEmbeddableKey() {
        selected.setRespuestaPorEstiloPK(new RespuestaPorEstiloPK());
    }
  

    @Override
    protected RespuestaPorEstiloFacade getFacade() {
        return ejbFacade;
    }

    @Override
    public RespuestaPorEstilo prepareCreate() {
        setSelected(new RespuestaPorEstilo());
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
    public RespuestaPorEstiloFacade getEjbFacade() {
        return ejbFacade;
    }
    /**
     * 
     * @return 
     */
    @Override
    public String getStringCreated(){
        return "RespuestaPorEstiloCreated";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringUpdated(){
        return "RespuestaPorEstiloUpdated";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringDeleted(){
        return "RespuestaPorEstiloDeleted";
    }
    
    /**
     * 
     * @param encuestaEstilosAprendizaje
     * @return 
     */
    public List<RespuestaPorEstilo> getItemsXEncuesta(EncuestaEstilosAprendizaje encuestaEstilosAprendizaje) {
        return getFacade().getItemsXEncuesta(encuestaEstilosAprendizaje);
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public RespuestaPorEstilo getRespuestaPorEstilo(RespuestaPorEstiloPK id) {
        return getFacade().find(id);
    }

    /**
     * @param encuestaEstilosAprendizaje
     * @return 
     */
    @Override
    public List<RespuestaPorEstilo> obtenerTodosPorEncuesta(EncuestaEstilosAprendizaje encuestaEstilosAprendizaje) {
        return getEjbFacade().getItemsXEncuesta(encuestaEstilosAprendizaje);
    }
    
    /************************************************************************
     * CONVERTER
     ************************************************************************/
    
    @FacesConverter(forClass = RespuestaPorEstilo.class)
    public static class RespuestaPorEstiloControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RespuestaPorEstilosController controller = (RespuestaPorEstilosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "respuestaPorEstilosController");
            return controller.getRespuestaPorEstilo(getKey(value));
        }

        com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaPorEstiloPK getKey(String value) {
            com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaPorEstiloPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaPorEstiloPK();
            key.setIdTipoEstilo(Integer.parseInt(values[0]));
            key.setIdEncuestaEstilosAprendizaje(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaPorEstiloPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdTipoEstilo());
            sb.append(SEPARATOR);
            sb.append(value.getIdEncuestaEstilosAprendizaje());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RespuestaPorEstilo) {
                RespuestaPorEstilo o = (RespuestaPorEstilo) object;
                return getStringKey(o.getRespuestaPorEstiloPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RespuestaPorEstilo.class.getName()});
                return null;
            }
        }

    }
  
}
