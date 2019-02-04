package com.ingesoft.interpro.controladores;

import be.ceau.chart.BarChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
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
//import org.primefaces.model.chart.BarChartModel;
//import org.primefaces.model.chart.ChartSeries;

@ManagedBean(name = "estadisticaController")
@SessionScoped
public class EstadisticaController implements Serializable {

    Institucion institucion;
    Grado grado;
    Institucion estudiante;
    Date fechaInicio;
    Date fechafin;
    private String string_grafico;
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
        string_grafico = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void reiniciarEstadistica() {
        string_grafico = null;
    }

    public String getGraficoModelo(){
        System.out.println("getGraficoModelo: "+string_grafico);
        return string_grafico;
    }
    
    public String cargarGrafico() {
        if (grado != null && string_grafico == null) {
            System.out.println("grado: " + grado);
            int opcion = detectarTipoEstadistica();
            System.out.println("opcion: " + opcion);
            Datos[] listaBarras = null;
            switch (opcion) {
                case 11:
                    listaBarras = estadisticaPorGrado();
                    break;
                default:
                    string_grafico = null;
                    FacesContext context = FacesContext.getCurrentInstance();
                    FacesMessage msg;
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay suficientes datos para crear la estadsitica", "");
                    context.addMessage(null, msg);
            }
            if (listaBarras != null) {
                string_grafico = obtenerGrafico(listaBarras);
            }else{
                string_grafico = null;
            }
//            System.out.println("graficoModelo: " + string_grafico);
            return string_grafico;
        }
        System.out.println("getGraficoModelo derecho" + string_grafico);
        return string_grafico;
    }

    private String obtenerGrafico(Datos[] listaBarras) {
        List datas = new ArrayList();
        datas.add(12.0);
        datas.add(19.0);
        datas.add(3.0);
        datas.add(5.0);
        datas.add(2.0);
        datas.add(32.0);
        List<Color> colors = new ArrayList();
        colors.add(new Color(255, 0, 0, 0.7));
        colors.add(new Color(0, 0, 255, 0.7));
        colors.add(new Color(255, 170, 0, 0.7));
        colors.add(new Color(0, 255, 255, 0.7));
        colors.add(new Color(255, 255, 0, 0.7));
        colors.add(new Color(0, 255, 0, 0.7));

        BarDataset dataset = new BarDataset()
                .setLabel("sample chart")
                .setData(datas)
                .setBackgroundColor(colors)
                .setBorderWidth(0);
//        dataset.s
        BarData data = new BarData();
        data.setLabels("Rojo", "Azul", "Wednesday", "Thursday", "Friday", "Saturday")
                .addDataset(dataset);
        
        return new BarChart(data).toJson();
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

    /**
     * por ahora trae todos los resultados de las encuestas sin importar si
     * pretenecen al mismo es
     *
     * @return
     */
    public Datos[] estadisticaPorGrado() {
        Datos[] listaBarras = null;
        List<ResultadoPorAmbiente> listaResultados = new ArrayList();
        if (grado != null && string_grafico == null) {
            List<EstudianteGrado> listEstudianteGrado = grado.getEstudianteGradoList();
            for (EstudianteGrado estudianteGrado : listEstudianteGrado) {

                List<Encuesta> listaEncuestas = estudianteGrado.getEncuestaList();
                for (Encuesta encuesta : listaEncuestas) {

                    List<ResultadoPorAmbiente> listaResultadosPorAmbiente = encuesta.getResultadoPorAmbienteList();
                    if (listaResultadosPorAmbiente.isEmpty()) {
                        continue;
                    }
                    listaResultados.addAll(listaResultadosPorAmbiente);
                }
            }

            if (listaResultados.isEmpty()) {
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay suficientes datos para crear la estadistica", "");
                context.addMessage(null, msg);
            } else {
                listaBarras = promedioResultados(listaResultados);
            }
        }
        System.out.println("listaBarras: "+listaBarras);
        return listaBarras;
    }

    public Datos[] promedioResultados(List<ResultadoPorAmbiente> listaResultadosPorAmbiente) {
        Datos[] listaDatos = new Datos[6];
        Datos datos;
        System.out.println("result: " + listaResultadosPorAmbiente);
        for (ResultadoPorAmbiente result : listaResultadosPorAmbiente) {
            int tipo = result.getTipoAmbiente().getIdTipoAmbiente();
            if (listaDatos[tipo - 1] == null) {
                listaDatos[tipo - 1] = new Datos();
                listaDatos[tipo - 1].tipo = result.getTipoAmbiente().getIdTipoAmbiente();
                listaDatos[tipo - 1].label = result.getTipoAmbiente().getTipo();
                String[] color = result.getTipoAmbiente().getColor().split(",");
                int r = Integer.parseInt(color[0]);
                int g = Integer.parseInt(color[1]);
                int b = Integer.parseInt(color[2]);
                double a = Double.parseDouble(color[3]);
                listaDatos[tipo - 1].color = new Color(r, g, b, a);
            }
            datos = listaDatos[tipo - 1];
//            System.out.println("datos: " + datos);
//            System.out.println("result: " + result);
            datos.valor += (double) result.getValor();
        }
        for (Datos listaDato : listaDatos) {
            listaDato.valor /= listaResultadosPorAmbiente.size();
        }
        return listaDatos;
    }

    public class Datos {

        Color color;
        String label;
        int tipo;
        Double valor;

        public Datos() {
            label = "";
            valor = 0.0;
        }

        @Override
        public String toString() {
            return "Datos{" + "color=" + color + ", label=" + label + ", tipo=" + tipo + ", valor=" + valor + '}';
        }

    }
}
