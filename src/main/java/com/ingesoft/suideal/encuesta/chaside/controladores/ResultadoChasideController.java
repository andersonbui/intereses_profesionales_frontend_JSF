package com.ingesoft.suideal.encuesta.chaside.controladores;

import com.ingesoft.interpro.controladores.util.RespuestaControllerAbstract;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.suideal.encuesta.chaside.entidades.ResultadoChaside;
import com.ingesoft.suideal.encuesta.chaside.entidades.ResultadoChasidePK;
import com.ingesoft.suideal.encuesta.chaside.entidades.EncuestaChaside;
import com.ingesoft.suideal.encuesta.chaside.facade.ResultadoChasideFacade;

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

@ManagedBean(name = "resultadoChasideController")
@SessionScoped
public class ResultadoChasideController extends RespuestaControllerAbstract <
            ResultadoChaside,
            ResultadoChasideFacade,
            EncuestaChaside> {

    @EJB
    private ResultadoChasideFacade ejbFacade;
    
    public ResultadoChasideController() {
        
    }

    public void inicializar(Encuesta selected) {

    }

    @Override
    protected void setEmbeddableKeys() {
        selected.getResultadoChasidePK().setIdEncuesta(selected.getEncuestaChaside().getIdEncuesta());
        selected.getResultadoChasidePK().setIdTipoChaside(selected.getTipoClaseChaside().getTipoChaside().getIdTipoChaside());
        selected.getResultadoChasidePK().setIdClaseChaside(selected.getTipoClaseChaside().getClaseChaside().getIdClaseChaside());
    }

    protected void initializeEmbeddableKey() {
        selected.setResultadoChasidePK(new ResultadoChasidePK());
    }
  

    @Override
    protected ResultadoChasideFacade getFacade() {
        return ejbFacade;
    }

    @Override
    public ResultadoChaside prepareCreate() {
        setSelected(new ResultadoChaside());
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
    public ResultadoChasideFacade getEjbFacade() {
        return ejbFacade;
    }
    /**
     * 
     * @return 
     */
    @Override
    public String getStringCreated(){
        return "ResultadoChasideCreated";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringUpdated(){
        return "ResultadoChasideUpdated";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringDeleted(){
        return "ResultadoChasideDeleted";
    }
    
    /**
     * 
     * @param encuestaChaside
     * @return 
     */
    public List<ResultadoChaside> getItemsXEncuesta(EncuestaChaside encuestaChaside) {
        return getFacade().getItemsXEncuesta(encuestaChaside);
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public ResultadoChaside getResultadoChaside(ResultadoChasidePK id) {
        return getFacade().find(id);
    }

    /**
     * @param encuestaInteligenciasMultiple
     * @return 
     */
    @Override
    public List<ResultadoChaside> obtenerTodosPorEncuesta(EncuestaChaside encuestaInteligenciasMultiple) {
        return getEjbFacade().getItemsXEncuesta(encuestaInteligenciasMultiple);
    }
    
    /************************************************************************
     * CONVERTER
     ************************************************************************/
    
    @FacesConverter(forClass = ResultadoChaside.class)
    public static class ResultadoChasideControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ResultadoChasideController controller = (ResultadoChasideController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "resultadoChasideController");
            return controller.getResultadoChaside(getKey(value));
        }

        ResultadoChasidePK getKey(String value) {
            ResultadoChasidePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new ResultadoChasidePK();
            key.setIdClaseChaside(Integer.parseInt(values[0]));
            key.setIdTipoChaside(Integer.parseInt(values[1]));
            key.setIdEncuesta(Integer.parseInt(values[2]));
            return key;
        }

        String getStringKey(ResultadoChasidePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdClaseChaside());
            sb.append(SEPARATOR);
            sb.append(value.getIdTipoChaside());
            sb.append(SEPARATOR);
            sb.append(value.getIdClaseChaside());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ResultadoChaside) {
                ResultadoChaside o = (ResultadoChaside) object;
                return getStringKey(o.getResultadoChasidePK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ResultadoChaside.class.getName()});
                return null;
            }
        }

    }
  
}
