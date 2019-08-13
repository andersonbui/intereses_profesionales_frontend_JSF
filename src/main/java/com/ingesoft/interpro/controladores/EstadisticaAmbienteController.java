package com.ingesoft.interpro.controladores;

import be.ceau.chart.BarChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import com.ingesoft.interpro.controladores.util.DatosAmbiente;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.entidades.EstudianteGrado;
import com.ingesoft.interpro.entidades.Grado;
import com.ingesoft.interpro.entidades.Institucion;
import com.ingesoft.interpro.entidades.ResultadoPorAmbiente;
import com.ingesoft.interpro.facades.AbstractFacade;
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
//import org.primefaces.model.chart.BarChartModel;
//import org.primefaces.model.chart.ChartSeries;

@ManagedBean(name = "estadisticaAmbienteController")
@SessionScoped
public class EstadisticaAmbienteController extends Controller implements Serializable {

    Institucion institucion;
    Grado grado;
    Estudiante estudiante;
    Encuesta encuesta;
    List<Encuesta> listaTotalEncuestas;
    Date fechaInicio;
    Date fechafin;
    private String string_grafico;
    String[] colores = {"008000", "FF0000", "FFD42A", "0000FF", "FFFF00", "00FFFF"};
    List<Color> lista_colores;
    String tiempo;

    private String personalidad;

    public EstadisticaAmbienteController() {
        lista_colores = new ArrayList(6);
        lista_colores.add(new Color(255, 0, 0, 0.7));//rojo-investigativo
        lista_colores.add(new Color(0, 0, 255, 0.7));//azul-social
        lista_colores.add(new Color(255, 170, 0, 0.7));//anaranjado-artistico
        lista_colores.add(new Color(0, 255, 255, 0.7));//cyan-convencional
        lista_colores.add(new Color(255, 255, 0, 0.7));//amarillo-emprendedor
        lista_colores.add(new Color(0, 255, 0, 0.7));//verde-realista

    }

    public String getPersonalidad() {
        return personalidad;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public void reiniciar() {

    }

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
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
//        System.out.println("getGraficoModelo: " + string_grafico);
        return string_grafico;
    }

    public String cargarGraficoResultadoAmbiente() {
        int opcion = detectarTipoEstadistica();
        System.out.println("opcion: " + opcion);
        listaTotalEncuestas = new ArrayList();
        EncuestaController encuestaController = getEncuestaController();
//         getEstadisticaAmbienteController().cargarGraficoResultadoEncuestaEstudiante(estudiante);

        String result = cargarGraficoResultadoEncuesta(opcion);

        encuestaController.setItems(listaTotalEncuestas);
        personalidad = encuestaController.obtenerPromedioPersonalidad(listaTotalEncuestas);

        return result;
    }

    public String cargarGraficoResultadoEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
        return cargarGraficoResultadoEncuesta(1111);
    }

    public String cargarGraficoResultadoEncuestaEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
        return cargarGraficoResultadoEncuesta(111);
    }

    public DatosAmbiente[] cargarDatosResultadoPor(Estudiante estudiante) {
        List<ResultadoPorAmbiente> listaResultados = null;
        listaResultados = resultadosPorEstudiante(estudiante);
        DatosAmbiente[] listaBarras = null;
        if (listaResultados != null && !listaResultados.isEmpty()) {

            listaBarras = promedioResultados(listaResultados);
        }
        return listaBarras;
    }

    public DatosAmbiente[] cargarDatosResultadoPor(Encuesta encuesta) {
        List<ResultadoPorAmbiente> listaResultados = null;
        listaResultados = resultadosPorEncuesta(encuesta);
        DatosAmbiente[] listaBarras = null;
        if (listaResultados != null && !listaResultados.isEmpty()) {

            listaBarras = promedioResultados(listaResultados);
        }
        return listaBarras;
    }

    public DatosAmbiente[] cargarDatosResultadoEncuesta(int opcion) {
        string_grafico = null;
        List<ResultadoPorAmbiente> listaResultados = null;
        switch (opcion) {
            case 1111:
                listaResultados = resultadosPorEncuesta(encuesta);
                break;
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
                System.out.println("opcion incorrecta");
        }
        DatosAmbiente[] listaBarras = null;
        if (listaResultados != null && !listaResultados.isEmpty()) {

            listaBarras = promedioResultados(listaResultados);
        }
        return listaBarras;
    }

    public String cargarGraficoResultadoEncuesta(int opcion) {
        DatosAmbiente[] listaBarras = cargarDatosResultadoEncuesta(opcion);
        if (listaBarras != null && listaBarras.length != 0) {
            string_grafico = obtenerGrafico(listaBarras);
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay suficientes datos para crear la estadistica", "");
            context.addMessage(null, msg);
        }
        return string_grafico;
    }

    private String obtenerGrafico(DatosAmbiente[] listaDatos) {
        List datas = new ArrayList();
        List<Color> colors = new ArrayList();
        BarData data = new BarData();
        for (DatosAmbiente dato : listaDatos) {
            double valor = Math.round(dato.getValor() * 1000D) / 1000D;
            datas.add(valor);
            colors.add(dato.getColor());
            data.addLabel(dato.getLabel());
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

    public int detectarTipoEstadistica() {
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
                listaTotalEncuestas.addAll(listaEncuestas);
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
        if (listaTotalEncuestas == null) {
            listaTotalEncuestas = new ArrayList();
        }
        if (un_estudiante != null) {
            List<ResultadoPorAmbiente> listaResultados = new ArrayList();
            List<EstudianteGrado> listEstudianteGrado = un_estudiante.getEstudianteGradoList();
            for (EstudianteGrado estudianteGrado : listEstudianteGrado) {

                List<Encuesta> listaEncuestas = estudianteGrado.getEncuestaList();
                listaTotalEncuestas.addAll(listaEncuestas);
                for (Encuesta una_encuesta : listaEncuestas) {

                    List<ResultadoPorAmbiente> listaResultadosPorAmbiente = una_encuesta.getResultadoPorAmbienteList();
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

    public List<ResultadoPorAmbiente> resultadosPorEncuesta(Encuesta encuesta) {
        System.out.println("resultadosPorEncuesta: " + encuesta);
        if (listaTotalEncuestas == null) {
            listaTotalEncuestas = new ArrayList();
        }
        listaTotalEncuestas.add(encuesta);
        if (encuesta != null) {
            List<ResultadoPorAmbiente> listaResultados = getResultadoPorAmbienteController().getItemsPorEncuesta(encuesta.getIdEncuesta());
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
//    public DatosAmbiente[] estadisticaPorInstitucion(Institucion una_institucion) {
//        DatosAmbiente[] listaBarras = null;
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
//    public DatosAmbiente[] estadisticaPorGrado(Grado un_grado) {
//        DatosAmbiente[] listaBarras = null;
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
    public DatosAmbiente[] promedioResultados(List<ResultadoPorAmbiente> listaResultadosPorAmbiente) {
        if (listaResultadosPorAmbiente == null || listaResultadosPorAmbiente.isEmpty()) {
            return null;
        }
        DatosAmbiente[] listaDatos = new DatosAmbiente[6];
        DatosAmbiente datos;
//        System.out.println("result: " + listaResultadosPorAmbiente);
        for (ResultadoPorAmbiente result : listaResultadosPorAmbiente) {
            int tipo = result.getTipoAmbiente().getIdTipoAmbiente();
            if (listaDatos[tipo - 1] == null) {
                listaDatos[tipo - 1] = new DatosAmbiente();
                listaDatos[tipo - 1].setTipo(result.getTipoAmbiente().getIdTipoAmbiente());
                listaDatos[tipo - 1].setLabel(result.getTipoAmbiente().getTipo());
                listaDatos[tipo - 1].setTipoAmbiente(result.getTipoAmbiente());
                String[] color = result.getTipoAmbiente().getColor().split(",");
                int r = Integer.parseInt(color[0]);
                int g = Integer.parseInt(color[1]);
                int b = Integer.parseInt(color[2]);
                double a = Double.parseDouble(color[3]);
                listaDatos[tipo - 1].setColor(new Color(r, g, b, a));
            }
            datos = listaDatos[tipo - 1];
            datos.setValor(datos.getValor() + (double) result.getValor());
        }
        for (DatosAmbiente listaDato : listaDatos) {
            listaDato.setValor(listaDato.getValor() / listaResultadosPorAmbiente.size());
        }
        return listaDatos;
    }

    @Override
    protected AbstractFacade getFacade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
