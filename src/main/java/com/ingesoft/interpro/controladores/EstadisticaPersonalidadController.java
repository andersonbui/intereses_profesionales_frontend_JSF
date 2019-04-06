package com.ingesoft.interpro.controladores;

import be.ceau.chart.BarChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.entidades.EstudianteGrado;
import com.ingesoft.interpro.entidades.Grado;
import com.ingesoft.interpro.entidades.Institucion;
import com.ingesoft.interpro.entidades.ResultadoPorAmbiente;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "estadisticaPersonalidadController")
@SessionScoped
public class EstadisticaPersonalidadController implements Serializable {

    Institucion institucion;
    Grado grado;
    Estudiante estudiante;
    Date fechaInicio;
    Date fechafin;
    private String string_grafico;
    String[] personalidades = {"iirj","isej","isep","iiep","iirp","isrp","iiej","isrp",
        "eiej","eirj","esej","esep","eiep","eirp","esrp","esrj" };

    public EstadisticaPersonalidadController() {

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

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante Estidiante) {
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

    public List<Estudiante> obternerEstudiante(Grado un_grado) {

        if (un_grado == null) {
            return null;
        }
        List<Estudiante> listaEstudiantes = new ArrayList();
        List<EstudianteGrado> listaEstudiantesGrado = un_grado.getEstudianteGradoList();
//        Set set_estudiantes = new HashSet();
        for (EstudianteGrado estudianteGrado : listaEstudiantesGrado) {
            Estudiante un_estudiante = estudianteGrado.getEstudiante();
            if (!listaEstudiantes.contains(un_estudiante)) {
                listaEstudiantes.add(un_estudiante);
            }
        }
        return listaEstudiantes;
    }

    public void reiniciarEstadistica() {
        string_grafico = null;
    }

    public String getGraficoModelo() {
        System.out.println("getGraficoModelo: " + string_grafico);
        return string_grafico;
    }

    public String cargarGraficoResultadoAmbiente() {
        int opcion = detectarTipoEstadistica();
        System.out.println("opcion: " + opcion);
        List<ResultadoPorAmbiente> listaResultados = null;
        switch (opcion) {
            case 111:
                listaResultados = resultadosPorEstudiante(estudiante);
                break;
            case 11:
                listaResultados = resultadosAmbientePorGrado(grado);
                break;
            case 1:
                listaResultados = resultadosAmbientePorInstitucion(institucion);
                break;
            default:
                string_grafico = null;
                System.out.println("opcion incorrecta");
        }

        if (listaResultados != null && !listaResultados.isEmpty()) {
            Datos[] listaBarras = null;
            listaBarras = promedioResultados(listaResultados);
            System.out.println("listaBarras: ");
            System.out.println(listaBarras);
            string_grafico = obtenerGrafico(listaBarras);
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay suficientes datos para crear la estadistica", "");
            context.addMessage(null, msg);
        }
        return string_grafico;
    }

    private String obtenerGrafico(Datos[] listaDatos) {
        List datas = new ArrayList();
        List<Color> colors = new ArrayList();
        BarData data = new BarData();
        for (Datos dato : listaDatos) {
            double valor = Math.round(dato.valor * 1000D) / 1000D;
            datas.add(valor);
            colors.add(dato.color);
            data.addLabel(dato.label);
        }

        BarDataset dataset = new BarDataset()
                .setLabel("puntuación")
                .setData(datas)
                .setBackgroundColor(colors)
                .setBorderColor(Color.DARK_GRAY)
                .setBorderWidth(2);
        data.addDataset(dataset);

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

    public List<ResultadoPorAmbiente> resultadosAmbientePorInstitucion(Institucion una_institucion) {
        if (una_institucion != null) {
            List<ResultadoPorAmbiente> listaResultados = new ArrayList();
            List<Grado> listEstudianteGrado = una_institucion.getGradoList();
            List<ResultadoPorAmbiente> listaResult_aux;
            for (Grado un_grado : listEstudianteGrado) {
                listaResult_aux = resultadosAmbientePorGrado(un_grado);
                if (listaResult_aux == null || listaResult_aux.isEmpty()) {
                    continue;
                }
                listaResultados.addAll(listaResult_aux);
            }
            return listaResultados;
        }
        return null;
    }

    public List<ResultadoPorAmbiente> resultadosAmbientePorGrado(Grado un_grado) {
        if (un_grado != null) {
            List<ResultadoPorAmbiente> listaResultados = new ArrayList();
            List<EstudianteGrado> listEstudianteGrado = un_grado.getEstudianteGradoList();
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
            return listaResultados;
        }
        return null;
    }

    public List<ResultadoPorAmbiente> resultadosPorEstudiante(Estudiante un_estudiante) {
        if (un_estudiante != null) {
            List<ResultadoPorAmbiente> listaResultados = new ArrayList();
            List<EstudianteGrado> listEstudianteGrado = un_estudiante.getEstudianteGradoList();
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
            return listaResultados;
        }
        return null;
    }

    /**
     * por ahora trae todos los resultados de las encuestas sin importar si
     * pretenecen al mismo es
     *
     * @param una_institucion
     * @return
     */
//    public Datos[] estadisticaPorInstitucion(Institucion una_institucion) {
//        Datos[] listaBarras = null;
//        List<ResultadoPorAmbiente> listaResultados;
//        listaResultados = resultadosAmbientePorInstitucion(una_institucion);
//
//        if (listaResultados != null && listaResultados.isEmpty()) {
//            FacesContext context = FacesContext.getCurrentInstance();
//            FacesMessage msg;
//            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay suficientes datos para crear la estadistica", "");
//            context.addMessage(null, msg);
//        } else {
//            listaBarras = promedioResultados(listaResultados);
//        }
//        System.out.println("listaBarras: " + listaBarras);
//        return listaBarras;
//    }
    /**
     * por ahora trae todos los resultados de las encuestas sin importar si
     * pretenecen al mismo es
     *
     * @param un_grado
     * @return
     */
//    public Datos[] estadisticaPorGrado(Grado un_grado) {
//        Datos[] listaBarras = null;
//        List<ResultadoPorAmbiente> listaResultados;
//        listaResultados = resultadosAmbientePorGrado(un_grado);
//
//        if (listaResultados != null && listaResultados.isEmpty()) {
//            FacesContext context = FacesContext.getCurrentInstance();
//            FacesMessage msg;
//            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay suficientes datos para crear la estadistica", "");
//            context.addMessage(null, msg);
//        } else {
//            listaBarras = promedioResultados(listaResultados);
//        }
//        System.out.println("listaBarras: " + listaBarras);
//        return listaBarras;
//    }
    /**
     *
     * @param listaResultadosPorAmbiente
     * @return
     */
    public Datos[] promedioResultados(List<ResultadoPorAmbiente> listaResultadosPorAmbiente) {
        if (listaResultadosPorAmbiente == null || listaResultadosPorAmbiente.isEmpty()) {
            return null;
        }
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
