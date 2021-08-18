package com.ingesoft.suideal.encuesta.inteligencias_multiples.controladores;

import com.ingesoft.interpro.controladores.util.RespuestaControllerAbstract;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.EncuestaInteligenciasMultiples;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.RespuestaPorInteligencia;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.RespuestaPorInteligenciaPK;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.facades.RespuestaPorInteligenciaFacade;

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

@ManagedBean(name = "respuestaPorInteligenciaController")
@SessionScoped
public class RespuestaPorInteligenciaController extends RespuestaControllerAbstract <
            RespuestaPorInteligencia, 
            RespuestaPorInteligenciaFacade, 
            EncuestaInteligenciasMultiples> {

    @EJB
    private RespuestaPorInteligenciaFacade ejbFacade;
    
    public RespuestaPorInteligenciaController() {
        
    }

    public void inicializar(Encuesta selected) {

    }

    @Override
    protected void setEmbeddableKeys() {
        selected.getRespuestaPorInteligenciaPK().setIdEncuestaInteligenciasMultiples(selected.getEncuestaInteligenciasMultiples().getIdEncuesta());
        selected.getRespuestaPorInteligenciaPK().setIdTipoInteligenciasMultiples(selected.getTipoInteligenciasMultiples().getId());
    }

    protected void initializeEmbeddableKey() {
        selected.setRespuestaPorInteligenciaPK(new RespuestaPorInteligenciaPK());
    }
  

    @Override
    protected RespuestaPorInteligenciaFacade getFacade() {
        return ejbFacade;
    }

    @Override
    public RespuestaPorInteligencia prepareCreate() {
        setSelected(new RespuestaPorInteligencia());
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
    public RespuestaPorInteligenciaFacade getEjbFacade() {
        return ejbFacade;
    }
    /**
     * 
     * @return 
     */
    @Override
    public String getStringCreated(){
        return "RespuestaPorInteligenciaCreated";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringUpdated(){
        return "RespuestaPorInteligenciaUpdated";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringDeleted(){
        return "RespuestaPorInteligenciaDeleted";
    }
    
    /**
     * 
     * @param encuestaInteligenciasMultiple
     * @return 
     */
    public List<RespuestaPorInteligencia> getItemsXEncuesta(EncuestaInteligenciasMultiples encuestaInteligenciasMultiple) {
        return getFacade().getItemsXEncuesta(encuestaInteligenciasMultiple);
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public RespuestaPorInteligencia getRespuestaPorInteligencia(RespuestaPorInteligenciaPK id) {
        return getFacade().find(id);
    }

    /**
     * @param encuestaInteligenciasMultiple
     * @return 
     */
    @Override
    public List<RespuestaPorInteligencia> obtenerTodosPorEncuesta(EncuestaInteligenciasMultiples encuestaInteligenciasMultiple) {
        return getEjbFacade().getItemsXEncuesta(encuestaInteligenciasMultiple);
    }
    
    /************************************************************************
     * CONVERTER
     ************************************************************************/
    
    @FacesConverter(forClass = RespuestaPorInteligencia.class)
    public static class RespuestaPorInteligenciaControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RespuestaPorInteligenciaController controller = (RespuestaPorInteligenciaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "respuestaPorInteligenciaController");
            return controller.getRespuestaPorInteligencia(getKey(value));
        }

        RespuestaPorInteligenciaPK getKey(String value) {
            RespuestaPorInteligenciaPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new RespuestaPorInteligenciaPK();
            key.setIdTipoInteligenciasMultiples(Integer.parseInt(values[0]));
            key.setIdEncuestaInteligenciasMultiples(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(RespuestaPorInteligenciaPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdTipoInteligenciasMultiples());
            sb.append(SEPARATOR);
            sb.append(value.getIdEncuestaInteligenciasMultiples());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RespuestaPorInteligencia) {
                RespuestaPorInteligencia o = (RespuestaPorInteligencia) object;
                return getStringKey(o.getRespuestaPorInteligenciaPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RespuestaPorInteligencia.class.getName()});
                return null;
            }
        }

    }
  
}
