package com.ingesoft.suideal.encuesta.chaside.controladores;

import com.ingesoft.interpro.controladores.util.RespuestaControllerAbstract;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.suideal.encuesta.chaside.entidades.EncuestaChaside;
import com.ingesoft.suideal.encuesta.chaside.entidades.RespuestaChaside;
import com.ingesoft.suideal.encuesta.chaside.entidades.RespuestaChasidePK;
import com.ingesoft.suideal.encuesta.chaside.facade.RespuestaChasideFacade;
import java.util.Date;

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

@ManagedBean(name = "respuestaChasideController")
@SessionScoped
public class RespuestaChasideController extends RespuestaControllerAbstract <
            RespuestaChaside, 
            RespuestaChasideFacade, 
            EncuestaChaside> {

    @EJB
    private RespuestaChasideFacade ejbFacade;
    
    public RespuestaChasideController() {
    }

    public void inicializar(Encuesta selected) {

    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    protected RespuestaChasideFacade getFacade() {
        return ejbFacade;
    }

    @Override
    public RespuestaChaside prepareCreate() {
        setSelected(new RespuestaChaside());
        getSelected().setFecha(new Date());
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
    public RespuestaChasideFacade getEjbFacade() {
        return ejbFacade;
    }
    /**
     * 
     * @return 
     */
    @Override
    public String getStringCreated(){
        return "RespuestaChasideCreated";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringUpdated(){
        return "RespuestaChasideUpdated";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringDeleted(){
        return "RespuestaChasideDeleted";
    }
    
    
    public List<RespuestaChaside> getItemsXEncuesta(EncuestaChaside encuestaChaside) {
        return getFacade().getItemsXEncuesta(encuestaChaside);
    }
    
    public RespuestaChaside getRespuestaChaside(RespuestaChasidePK id) {
        return getFacade().find(id);
    }

    /**
     * @param encuestaChaside
     * @return 
     */
    @Override
    public List<RespuestaChaside> obtenerTodosPorEncuesta(EncuestaChaside encuestaChaside) {
        return getEjbFacade().getItemsXEncuesta(encuestaChaside);
    }
    
    /************************************************************************
     * CONVERTER
     ************************************************************************/
    
    @FacesConverter(forClass = RespuestaChaside.class)
    public static class RespuestaChasideControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RespuestaChasideController controller = (RespuestaChasideController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "respuestaChasideController");
            return controller.getRespuestaChaside(getKey(value));
        }

        RespuestaChasidePK getKey(String value) {
            RespuestaChasidePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new RespuestaChasidePK();
            key.setIdPreguntaChaside(Integer.parseInt(values[0]));
            key.setIdEncuestaChaside(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(RespuestaChasidePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdPreguntaChaside());
            sb.append(SEPARATOR);
            sb.append(value.getIdEncuestaChaside());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RespuestaChaside) {
                RespuestaChaside o = (RespuestaChaside) object;
                return getStringKey(o.getRespuestaChasidePK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RespuestaChaside.class.getName()});
                return null;
            }
        }

    }
  
}
