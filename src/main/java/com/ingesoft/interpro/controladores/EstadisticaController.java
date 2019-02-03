package com.ingesoft.interpro.controladores;

import be.ceau.chart.color.Color;
import com.ingesoft.interpro.controladores.util.Utilidades;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.EstudianteGrado;
import com.ingesoft.interpro.entidades.Grado;
import com.ingesoft.interpro.entidades.Institucion;
import com.ingesoft.interpro.entidades.ResultadoPorAmbiente;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    Institucion estudiante;
    Date fechaInicio;
    Date fechafin;
    private BarChartModel graficoModelo;
    String[] colores = {"008000", "FF0000", "FFD42A", "0000FF", "FFFF00", "00FFFF"};
    List<Color> lista_colores;

    public EstadisticaController() {
        lista_colores = new ArrayList(6);
        lista_colores.add(new Color(255, 0, 0, 0.7));//rojo-investigativo
        lista_colores.add(new Color(0, 0, 255, 0.7));//azul-social
        lista_colores.add(new Color(255, 170, 0, 0.7));//anaranjado-artistico
        lista_colores.add(new Color(0, 255, 255, 0.7));//cyan-convencional
        lista_colores.add(new Color(255, 255, 0, 0.7));//amarillo-emprendedor
        lista_colores.add(new Color(0, 255, 0, 0.7));//verde-realista
        
    }

    public void reiniciar() {

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
        return estudiante;
    }

    public void setEstidiante(Institucion Estidiante) {
        this.estudiante = Estidiante;
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
        graficoModelo = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void reiniciarEstadistica() {
        graficoModelo = null;
    }

    public BarChartModel getGraficoModelo() {
        if (grado != null && graficoModelo == null) {
            System.out.println("grado: " + grado);
            int opcion = detectarTipoEstadistica();
            System.out.println("opcion: " + opcion);
            List<Datos> listaBarras = null;
            switch (opcion) {
                case 11:
                    listaBarras = estadisticaPorGrado();
                    break;
                default:
                    graficoModelo = null;
                    FacesContext context = FacesContext.getCurrentInstance();
                    FacesMessage msg;
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay suficientes datos para crear la estadsitica", "");
                    context.addMessage(null, msg);
            }
            if (listaBarras != null && !listaBarras.isEmpty()) {
                graficoModelo = new BarChartModel();
                for (Datos datos : listaBarras) {
                    ChartSeries barra = new ChartSeries("ambiente:--");
                    barra.set(datos.tipo, datos.valor);
                    barra.setLabel(datos.label);
                    graficoModelo.addSeries(barra);
                }
//                graficoModelo.setSeriesColors("");
                graficoModelo.setShowPointLabels(true);
            }
            System.out.println("graficoModelo: " + graficoModelo);
            return graficoModelo;
        }
        System.out.println("getGraficoModelo derecho");
        return graficoModelo;
    }

    private int detectarTipoEstadistica() {
        int opcion = 0;
        if (institucion != null) {
            opcion += 1;
        }
        if (grado != null) {
            opcion += 10;
        }
        if (estudiante != null) {
            opcion += 100;
        }
        return opcion;
    }

    public List<Datos> estadisticaPorGrado() {
        List<Datos> listaBarras = null;
        if (grado != null && graficoModelo == null) {
            listaBarras = new ArrayList<>();
            List<EstudianteGrado> listEstudianteGrado = grado.getEstudianteGradoList();
            for (EstudianteGrado estudianteGrado : listEstudianteGrado) {

                List<Encuesta> listaEncuestas = estudianteGrado.getEncuestaList();
                for (Encuesta encuesta : listaEncuestas) {

                    List<ResultadoPorAmbiente> listaResultadosPorAmbiente = encuesta.getResultadoPorAmbienteList();
                    if (listaResultadosPorAmbiente.isEmpty()) {
                        continue;
                    }
                    int i = 0;
//                  System.out.println("lista:" + listaResultadosPorAmbiente);
//                    Collections.sort(listaResultadosPorAmbiente, new Comparator<ResultadoPorAmbiente>() {
//                        @Override
//                        public int compare(ResultadoPorAmbiente r1, ResultadoPorAmbiente r2) {
//                            return -r1.getValor().compareTo(r2.getValor());
//                        }
//                    });
                    Datos datos;
                    for (ResultadoPorAmbiente result : listaResultadosPorAmbiente) {
                        datos = new Datos();
                        datos.tipo = result.getTipoAmbiente().getIdTipoAmbiente();
                        datos.label = result.getTipoAmbiente().getTipo();
                        datos.valor = (double) result.getValor();
                        datos.color = colores[datos.tipo - 1];
//                      System.out.println("result:" + result.getValor());
                        listaBarras.add(datos);
                    }
                }
            }
            if (listaBarras.isEmpty()) {
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay suficientes datos para crear la estadsitica", "");
                context.addMessage(null, msg);
            }
        }
        return listaBarras;
    }

    public class Datos {

        String color;
        String label;
        int tipo;
        Double valor;
    }
}
