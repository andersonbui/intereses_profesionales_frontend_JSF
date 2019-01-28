package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.EstudianteGrado;
import com.ingesoft.interpro.entidades.Grado;
import com.ingesoft.interpro.entidades.Institucion;
import com.ingesoft.interpro.entidades.ResultadoPorAmbiente;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean(name = "estadisticaController")
@SessionScoped
public class EstadisticaController implements Serializable {

    Institucion institucion;
    Grado grado;
    Institucion Estidiante;
    Date fechaInicio;
    Date fechafin;
    private BarChartModel graficoModelo;

    public EstadisticaController() {

    }

    public void setGraficoModelo(BarChartModel graficoModelo) {
        this.graficoModelo = graficoModelo;
    }

    public Institucion getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    public Grado getGrado() {
        return grado;
    }

    public void setGrado(Grado grado) {
        this.grado = grado;
    }

    public Institucion getEstidiante() {
        return Estidiante;
    }

    public void setEstidiante(Institucion Estidiante) {
        this.Estidiante = Estidiante;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }
    
    public void onDateSelect(SelectEvent event) {
        fechafin = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
    public BarChartModel getGraficoModelo() {
        if (grado != null) {
            graficoModelo = new BarChartModel();

            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay suficientes datos para crear la estadsitica", "");

            List<EstudianteGrado> listEstudianteGrado = grado.getEstudianteGradoList();
            if (listEstudianteGrado.isEmpty()) {
                context.addMessage(null, msg);
                return null;
            }
            List<Encuesta> listaEncuestas = listEstudianteGrado.get(0).getEncuestaList();
            if (listaEncuestas.isEmpty()) {

                context.addMessage(null, msg);
                return null;
            }
            List<ResultadoPorAmbiente> listaResultadosPorAmbiente = listaEncuestas.get(0).getResultadoPorAmbienteList();

            int i = 0;
            ChartSeries barra = null;
//        System.out.println("lista:" + listaResultadosPorAmbiente);
            Collections.sort(listaResultadosPorAmbiente, new Comparator<ResultadoPorAmbiente>() {
                @Override
                public int compare(ResultadoPorAmbiente r1, ResultadoPorAmbiente r2) {
                    return -r1.getValor().compareTo(r2.getValor());
                }
            });
            for (ResultadoPorAmbiente result : listaResultadosPorAmbiente) {
                barra = new ChartSeries("ambiente:--");
                barra.set(result.getTipoAmbiente().getTipo(), result.getValor());
                barra.setLabel(result.getTipoAmbiente().getTipo());
//            System.out.println("result:" + result.getValor());
                graficoModelo.addSeries(barra);
            }
            graficoModelo.setSeriesColors("008000,FF0000,FFD42A,0000FF,FFFF00,00FFFF");
            graficoModelo.setShowPointLabels(true);
            return graficoModelo;
        }
        return null;
    }
}
