package com.ingesoft.suideal.encuesta.inteligencias_multiples.controladores;

import com.ingesoft.interpro.controladores.util.PreguntaControllerAbstract;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.EncuestaInteligenciasMultiples;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.PreguntaInteligenciasMultiples;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.facades.PreguntaInteligenciasMultiplesFacade;
import java.io.IOException;

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

@ManagedBean(name = "preguntaInteligenciasMultiplesController")
@SessionScoped
public class PreguntaInteligenciasMultiplesController extends PreguntaControllerAbstract<
        PreguntaInteligenciasMultiples,
        PreguntaInteligenciasMultiplesFacade
        > {

    @EJB
    protected PreguntaInteligenciasMultiplesFacade ejbFacade;
    
    
    /************************************************************************
     * CONSTRUCTORS
     ************************************************************************/
    
    /**
     * 
     */
    public PreguntaInteligenciasMultiplesController() {
    }

    protected void initializeEmbeddableKey() {
    }

    
    /************************************************************************
     * Funciones personalizadas
     ************************************************************************/
    
    /**
     * 
     * @return 
     */
    @Override
    public PreguntaInteligenciasMultiples prepareCreate() {
        setSelected(new PreguntaInteligenciasMultiples());
        initializeEmbeddableKey();
        return getSelected();
    }

    /**
     * 
     * @param respuestas
     * @return
     * @throws IOException
     * @throws InterruptedException 
     */
    public List<PreguntaInteligenciasMultiples> actualizarTodasRespuestas(List<PreguntaInteligenciasMultiples> respuestas) throws IOException, InterruptedException {
        for (PreguntaInteligenciasMultiples item : respuestas) {
            this.getEjbFacade().edit(item);
        }
        return respuestas;
    }
    
    /************************************************************************
     * GETTERS AND SETTERS METHODS
     ************************************************************************/
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringCreated(){
        return "PreguntaInteligenciasMultiplesCreated";
    }
    
    @Override
    public String getStringUpdated(){
        return "PreguntaInteligenciasMultiplesUpdated";
    }
    
    @Override
    public String getStringDeleted(){
        return "PreguntaInteligenciasMultiplesDeleted";
    }
    
//    public List<PreguntaInteligenciasMultiples> obtenerTodosPorEncuesta(EncuestaInteligenciasMultiples encuestaInteligenciasMultiples) {
//        return getEjbFacade().getItemsXEncuesta(encuestaInteligenciasMultiples);
//    }
    
//    public List<PreguntaInteligenciasMultiples> getItemsXEncuesta(EncuestaInteligenciasMultiples encuestaInteligenciasMultiples) {
//        return getEjbFacade().getItemsXEncuesta(encuestaInteligenciasMultiples);
//    }
    
    public PreguntaInteligenciasMultiples getPreguntaInteligenciasMultiples(Integer id) {
        return getEjbFacade().find(id);
    }

    @Override
    public PreguntaInteligenciasMultiplesFacade getEjbFacade() {
        return ejbFacade;
    }

    
    /************************************************************************
     * CONVERTER
     ************************************************************************/
    @FacesConverter(forClass = PreguntaInteligenciasMultiples.class)
    public static class PreguntaInteligenciasMultiplesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PreguntaInteligenciasMultiplesController controller = (PreguntaInteligenciasMultiplesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "preguntaInteligenciasMultiples");
            return controller.getPreguntaInteligenciasMultiples(getKey(value));
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
            if (object instanceof PreguntaInteligenciasMultiples) {
                PreguntaInteligenciasMultiples o = (PreguntaInteligenciasMultiples) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PreguntaInteligenciasMultiples.class.getName()});
                return null;
            }
        }

    }
    
}
