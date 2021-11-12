package com.ingesoft.suideal.encuesta.chaside.controladores;

import com.ingesoft.suideal.encuesta.chaside.entidades.TipoClaseChaside;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.suideal.encuesta.chaside.entidades.TipoClaseChasidePK;
import com.ingesoft.suideal.encuesta.chaside.facade.TipoClaseChasideFacade;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "tipoClaseChasideController")
@SessionScoped
public class TipoClaseChasideController implements Serializable {

    @EJB
    private TipoClaseChasideFacade ejbFacade;
    private TipoClaseChaside selected;
    
    public TipoClaseChasideController() {
    }

    public void inicializar(Encuesta selected) {
    }

    protected TipoClaseChasideFacade getFacade() {
        return ejbFacade;
    }

    /************************************************************************
     * GETTERS AND SETTERS METHODS
     ************************************************************************/

    /**
     * 
     * @return 
     */
    public TipoClaseChasideFacade getEjbFacade() {
        return ejbFacade;
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public TipoClaseChaside getTipoClaseChaside(TipoClaseChasidePK id) {
        return getFacade().find(id);
    }

    
    /************************************************************************
     * CONVERTER
     ************************************************************************/
    
//    @FacesConverter(forClass = TipoClaseChaside.class)
//    public static class TipoClaseChasideControllerConverter implements Converter {
//
//        private static final String SEPARATOR = "#";
//        private static final String SEPARATOR_ESCAPED = "\\#";
//
//        @Override
//        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
//            if (value == null || value.length() == 0) {
//                return null;
//            }
//            TipoClaseChasideController controller = (TipoClaseChasideController) facesContext.getApplication().getELResolver().
//                    getValue(facesContext.getELContext(), null, "tipoClaseChasideController");
//            return controller.getTipoClaseChaside(getKey(value));
//        }
//
//        com.ingesoft.suideal.encuesta.chaside.entidades.TipoClaseChasidePK getKey(String value) {
//            com.ingesoft.suideal.encuesta.chaside.entidades.TipoClaseChasidePK key;
//            String values[] = value.split(SEPARATOR_ESCAPED);
//            key = new com.ingesoft.suideal.encuesta.chaside.entidades.TipoClaseChasidePK();
//            key.setIdTipoEstilo(Integer.parseInt(values[0]));
//            key.setIdEncuestaChaside(Integer.parseInt(values[1]));
//            return key;
//        }
//
//        String getStringKey(com.ingesoft.suideal.encuesta.chaside.entidades.TipoClaseChasidePK value) {
//            StringBuilder sb = new StringBuilder();
//            sb.append(value.getIdTipoEstilo());
//            sb.append(SEPARATOR);
//            sb.append(value.getIdEncuestaChaside());
//            return sb.toString();
//        }
//
//        @Override
//        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
//            if (object == null) {
//                return null;
//            }
//            if (object instanceof TipoClaseChaside) {
//                TipoClaseChaside o = (TipoClaseChaside) object;
//                return getStringKey(o.getTipoClaseChasidePK());
//            } else {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoClaseChaside.class.getName()});
//                return null;
//            }
//        }
//
//    }
  
}
