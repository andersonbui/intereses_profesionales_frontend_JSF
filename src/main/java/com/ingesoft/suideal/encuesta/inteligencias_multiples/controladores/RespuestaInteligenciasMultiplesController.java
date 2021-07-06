package com.ingesoft.suideal.encuesta.inteligencias_multiples.controladores;

import com.ingesoft.interpro.controladores.util.RespuestaControllerAbstract;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.EncuestaInteligenciasMultiples;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.RespuestaInteligenciasMultiples;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.RespuestaInteligenciasMultiplesPK;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.facades.RespuestaInteligenciasMultiplesFacade;
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

@ManagedBean(name = "respuestaInteligenciasMultiplesController")
@SessionScoped
public class RespuestaInteligenciasMultiplesController 
        extends RespuestaControllerAbstract<
            RespuestaInteligenciasMultiples, 
            RespuestaInteligenciasMultiplesFacade, 
            EncuestaInteligenciasMultiples> {

    @EJB
    private RespuestaInteligenciasMultiplesFacade ejbFacade;
    
    public RespuestaInteligenciasMultiplesController() {
    }

    public void inicializar(Encuesta selected) {

    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    public RespuestaInteligenciasMultiplesFacade getEjbFacade() {
        return ejbFacade;
    }

    @Override
    public RespuestaInteligenciasMultiples prepareCreate() {
        setSelected(new RespuestaInteligenciasMultiples());
        initializeEmbeddableKey();
        return getSelected();
    }

    @Override
    protected String getStringCreated(){
        return "RespuestaInteligenciasMultiplesCreated";
    }
    
    @Override
    protected String getStringUpdated(){
        return "RespuestaInteligenciasMultiplesUpdated";
    }
    
    @Override
    protected String getStringDeleted(){
        return "RespuestaInteligenciasMultiplesDeleted";
    }

    /**
     * @param encuestaEstilosAprendizaje
     * @return 
     */
    @Override
    public List<RespuestaInteligenciasMultiples> obtenerTodosPorEncuesta(EncuestaInteligenciasMultiples encuestaEstilosAprendizaje) {
        return getEjbFacade().getItemsXEncuesta(encuestaEstilosAprendizaje.getIdEncuesta());
    }
    
    public RespuestaInteligenciasMultiples getRespuestaInteligenciasMultiples(RespuestaInteligenciasMultiplesPK id) {
        return getEjbFacade().find(id);
    }

    @FacesConverter(forClass = RespuestaInteligenciasMultiples.class)
    public static class RespuestaInteligenciasMultiplesControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RespuestaInteligenciasMultiplesController controller = (RespuestaInteligenciasMultiplesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "respuestaEstilosController");
            return controller.getRespuestaInteligenciasMultiples(getKey(value));
        }

        RespuestaInteligenciasMultiplesPK getKey(String value) {
            RespuestaInteligenciasMultiplesPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new RespuestaInteligenciasMultiplesPK();
            key.setIdPreguntaInteligenciasMultiples(Integer.parseInt(values[0]));
            key.setIdEncuestaInteligenciasMultiples(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(RespuestaInteligenciasMultiplesPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdPreguntaInteligenciasMultiples());
            sb.append(SEPARATOR);
            sb.append(value.getIdEncuestaInteligenciasMultiples());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RespuestaInteligenciasMultiples) {
                RespuestaInteligenciasMultiples o = (RespuestaInteligenciasMultiples) object;
                return getStringKey(o.getRespuestaInteligenciasMultiplesPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RespuestaInteligenciasMultiples.class.getName()});
                return null;
            }
        }

    }
    
    public List<RespuestaInteligenciasMultiples> actualizarTodasRespuestas(List<RespuestaInteligenciasMultiples> respuestas) throws IOException, InterruptedException {
        for (RespuestaInteligenciasMultiples item : respuestas) {
            this.getFacade().edit(item);
        }
        return respuestas;
    }
}
