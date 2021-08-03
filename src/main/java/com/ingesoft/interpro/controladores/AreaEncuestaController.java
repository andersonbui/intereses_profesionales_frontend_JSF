package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.AreaEncuesta;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.Area;
import com.ingesoft.interpro.entidades.AreaEncuestaPK;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.TipoEleccionMateria;
import com.ingesoft.interpro.facades.AreaEncuestaFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "areaEncuestaController")
@SessionScoped
public class AreaEncuestaController extends Controllers implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.AreaEncuestaFacade ejbFacade;
    private List<AreaEncuesta> items = null;
    private AreaEncuesta selected;

    public AreaEncuestaController() {
    }

    public void inicializar() {
    }

    public AreaEncuesta getSelected() {
        return selected;
    }

    public void setSelected(AreaEncuesta selected) {
        this.selected = selected;
    }

    @Override
    protected void setEmbeddableKeys() {
//        selected.getAreaEncuestaPK().setIdArea(selected.getArea().getIdArea());
        selected.getAreaEncuestaPK().setIdEncuesta(selected.getEncuesta().getIdEncuesta());
        selected.getAreaEncuestaPK().setIdTipoEleccionMateria(selected.getTipoEleccionMateria().getIdTipoEleccionMateria());
    }

    protected void initializeEmbeddableKey() {
        selected.setAreaEncuestaPK(new com.ingesoft.interpro.entidades.AreaEncuestaPK());
    }

    /**
     *
     * @return
     */
    @Override
    protected AreaEncuestaFacade getFacade() {
        return ejbFacade;
    }

    /**
     * 
     * @return 
     */
    public AreaEncuesta prepareCreate() {
        selected = new AreaEncuesta();
        initializeEmbeddableKey();
        return selected;
    }

    /**
     * 
     */
    public void almacenarEncuestaAreasMas() {
        AreaController areaController = getAreaController();
        TipoEleccionMateriaController tipoEleccionMateriaController = getTipoEleccionMateriaController();

        Encuesta encuesta = getEncuestaController().getSelected();
        
        Area[] areas = areaController.getItemsMas();
        TipoEleccionMateria tipoEleccionMateria = tipoEleccionMateriaController.getTipoEleccionMateriaMayor();
        almacenarAreasEncuesta(areas, encuesta, tipoEleccionMateria);
    }
    
    /**
     * 
     */
    public void almacenarEncuestaAreasMenos() {
        AreaController areaController = getAreaController();
        TipoEleccionMateriaController tipoEleccionMateriaController = getTipoEleccionMateriaController();

        Encuesta encuesta = getEncuestaController().getSelected();
        
        Area[] areas = areaController.getItemsMenos();
        TipoEleccionMateria tipoEleccionMateria = tipoEleccionMateriaController.getTipoEleccionMateriaMenor();
        almacenarAreasEncuesta(areas, encuesta, tipoEleccionMateria);
    }
    
    /**
     * 
     */
    public void almacenarEncuestaAreasNota() {
        AreaController areaController = getAreaController();
        TipoEleccionMateriaController tipoEleccionMateriaController = getTipoEleccionMateriaController();

        Encuesta encuesta = getEncuestaController().getSelected();
        
        Area[] areas = areaController.getItemsNota();
        TipoEleccionMateria tipoEleccionMateria = tipoEleccionMateriaController.getTipoEleccionMateriaPorNota();
        almacenarAreasEncuesta(areas, encuesta, tipoEleccionMateria);
    }

    protected void almacenarAreasEncuesta(Area[] areas, Encuesta encuesta, TipoEleccionMateria tipoEleccionMateria) {
        for (int i = 0; i < areas.length; i++) {
            Area area = areas[i];
            if(area != null){
                selected = obtenerAreaEncuesta(encuesta, tipoEleccionMateria, (short) i, area);
            }
        }
    }

    public AreaEncuesta obtenerAreaEncuesta(Encuesta encuesta, TipoEleccionMateria tipoEleccionMateria, short posicion, Area area) {
        AreaEncuesta areaEncuesta = getAreaEncuesta(new AreaEncuestaPK(posicion, encuesta.getIdEncuesta(), tipoEleccionMateria.getIdTipoEleccionMateria()));
        if (areaEncuesta == null) {
            prepareCreate();
            selected.setEncuesta(encuesta);
            selected.getAreaEncuestaPK().setPosicion((short) posicion);
            selected.setTipoEleccionMateria(tipoEleccionMateria);
        } else {
            selected = areaEncuesta;
        }
        selected.setIdArea(area);
        selected = this.update();
        return selected;
    }

    public AreaEncuesta obtenerItem(int index) {
        return items.get(index);
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AreaEncuestaCreated"), selected);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    /**
     * 
     * @return 
     */
    public AreaEncuesta update() {
        return (AreaEncuesta) persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AreaEncuestaUpdated"), selected);
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AreaEncuestaDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<AreaEncuesta> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public AreaEncuesta getAreaEncuesta(com.ingesoft.interpro.entidades.AreaEncuestaPK id) {
        return getFacade().find(id);
    }

    public List<AreaEncuesta> getItemsPorEncuesta(Encuesta encuesta) {
        return getFacade().obtenerResultadoPorAmbiente(encuesta);
    }
    
    public List<AreaEncuesta> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<AreaEncuesta> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = AreaEncuesta.class)
    public static class AreaEncuestaControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AreaEncuestaController controller = (AreaEncuestaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "areaEncuestaController");
            return controller.getAreaEncuesta(getKey(value));
        }

        com.ingesoft.interpro.entidades.AreaEncuestaPK getKey(String value) {
            com.ingesoft.interpro.entidades.AreaEncuestaPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.ingesoft.interpro.entidades.AreaEncuestaPK();
            key.setPosicion(Short.parseShort(values[0]));
            key.setIdEncuesta(Integer.parseInt(values[1]));
            key.setIdTipoEleccionMateria(Integer.parseInt(values[2]));
            return key;
        }

        String getStringKey(com.ingesoft.interpro.entidades.AreaEncuestaPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPosicion());
            sb.append(SEPARATOR);
            sb.append(value.getIdEncuesta());
            sb.append(SEPARATOR);
            sb.append(value.getIdTipoEleccionMateria());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof AreaEncuesta) {
                AreaEncuesta o = (AreaEncuesta) object;
                return getStringKey(o.getAreaEncuestaPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AreaEncuesta.class.getName()});
                return null;
            }
        }

    }

}
