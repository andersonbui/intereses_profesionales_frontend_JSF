package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.ResultadoPorAmbiente;
import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.JsfUtil.PersistAction;
import com.ingesoft.interpro.entidades.ResultadoPorAmbientePK;
import com.ingesoft.interpro.facades.ResultadoPorAmbienteFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean(name = "resultadoPorAmbienteController")
@SessionScoped
public class ResultadoPorAmbienteController implements Serializable {

    @EJB
    private com.ingesoft.interpro.facades.ResultadoPorAmbienteFacade ejbFacade;
    private List<ResultadoPorAmbiente> items = null;
    private ResultadoPorAmbiente selected;

    private BarChartModel graficoModelo;
//    private PieChartModel pieModel1;

    public ResultadoPorAmbienteController() {
        graficoModelo = new BarChartModel();
//        pieModel1 = new PieChartModel();
    }

    @PostConstruct
    public void init() {
        graficoModelo = new BarChartModel();
//        pieModel1 = new PieChartModel();
        final ChartSeries barra1 = new ChartSeries("barra 1");
        final ChartSeries barra2 = new ChartSeries("barra 2");
        final ChartSeries barra3 = new ChartSeries("barra 3");
        final ChartSeries barra4 = new ChartSeries("barra 4");
        final ChartSeries barra5 = new ChartSeries("barra 5");
        final ChartSeries barra6 = new ChartSeries("barra 6");
        graficoModelo.setShowPointLabels(true);
//        pieModel1.setShowPointLabels(true);

        barra1.set("Realista", 5);
        barra2.set("de Investigación", 7);
        barra3.set("Artístico", 7);
        barra4.set("Social", 3);
        barra5.set("Empresariales", 1);
        barra6.set("Convencionales", 1);
        
        graficoModelo.addSeries(barra1);
        graficoModelo.addSeries(barra2);
        graficoModelo.addSeries(barra3);
        graficoModelo.addSeries(barra4);
        graficoModelo.addSeries(barra5);
        graficoModelo.addSeries(barra6);

        graficoModelo.setShowPointLabels(true);
    }

    public ResultadoPorAmbiente getSelected() {
        return selected;
    }

    public BarChartModel getGraficoModelo() {
        return graficoModelo;
    }

    public void setGraficoModelo(BarChartModel graficoModelo) {
        this.graficoModelo = graficoModelo;
    }

    public void setSelected(ResultadoPorAmbiente selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getResultadoPorAmbientePK().setIdTipoAmbiente(selected.getTipoAmbiente().getIdTipoAmbiente());
        selected.getResultadoPorAmbientePK().setIdEncuesta(selected.getEncuesta().getIdEncuesta());
    }

    protected void initializeEmbeddableKey() {
        selected.setResultadoPorAmbientePK(new ResultadoPorAmbientePK());
    }

    private ResultadoPorAmbienteFacade getFacade() {
        return ejbFacade;
    }

    public ResultadoPorAmbiente prepareCreate() {
        selected = new ResultadoPorAmbiente();
        initializeEmbeddableKey();
        return selected;
    }

    public void cargarDatosGrafica() {

    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ResultadoPorAmbienteCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ResultadoPorAmbienteUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ResultadoPorAmbienteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ResultadoPorAmbiente> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
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

    public ResultadoPorAmbiente getResultadoPorAmbiente(ResultadoPorAmbientePK id) {
        return getFacade().find(id);
    }

    public List<ResultadoPorAmbiente> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ResultadoPorAmbiente> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ResultadoPorAmbiente.class)
    public static class ResultadoPorAmbienteControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ResultadoPorAmbienteController controller = (ResultadoPorAmbienteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "resultadoPorAmbienteController");
            return controller.getResultadoPorAmbiente(getKey(value));
        }

        ResultadoPorAmbientePK getKey(String value) {
            ResultadoPorAmbientePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new ResultadoPorAmbientePK();
            key.setIdEncuesta(Integer.parseInt(values[0]));
            key.setIdTipoAmbiente(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(ResultadoPorAmbientePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdEncuesta());
            sb.append(SEPARATOR);
            sb.append(value.getIdTipoAmbiente());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ResultadoPorAmbiente) {
                ResultadoPorAmbiente o = (ResultadoPorAmbiente) object;
                return getStringKey(o.getResultadoPorAmbientePK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ResultadoPorAmbiente.class.getName()});
                return null;
            }
        }

    }

}
