package com.ingesoft.interpro.controladores;

import be.ceau.chart.BarChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.scales.BarScale;
import be.ceau.chart.options.scales.YAxis;
import be.ceau.chart.options.ticks.LinearTicks;
import com.ingesoft.interpro.controladores.util.DatosEleccionArea;
import com.ingesoft.interpro.entidades.AreaEncuesta;
import com.ingesoft.interpro.entidades.DatosRiasec;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.entidades.EstudianteGrado;
import com.ingesoft.interpro.entidades.Grado;
import com.ingesoft.interpro.entidades.Institucion;
import com.ingesoft.interpro.entidades.TipoEleccionMateria;
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

@ManagedBean(name = "estadisticaAreasController")
@SessionScoped
public class EstadisticaAreasController extends Controller implements Serializable {

    Institucion institucion;
    Grado grado;
    Estudiante estudiante;
    Encuesta encuesta;
    List<Encuesta> listaTotalEncuestas;
    Date fechaInicio;
    Date fechafin;
    private String string_grafico;
    String tiempo;

    List<DatosRiasec> listaDatosRaisec;
    private String personalidad;

    public EstadisticaAreasController() {

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
        listaDatosRaisec = null;
    }

    public void reiniciarTodo() {
        string_grafico = null;
        listaDatosRaisec = null;
        listaBarrasGrafico = null;
        institucion = null;
        estudiante = null;
        grado = null;
    }

    public List<DatosRiasec> getListaDatosRaisec() {
        return listaDatosRaisec;
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

    public DatosEleccionArea[] cargarDatosResultadoPor(Estudiante estudiante) {
        List<AreaEncuesta> listaResultados = null;
        listaResultados = resultadosAreaEncuestaPorEstudiante(estudiante);
        DatosEleccionArea[] listaBarras = null;
        if (listaResultados != null && !listaResultados.isEmpty()) {

            listaBarras = promedioResultados(listaResultados);
        }
        return listaBarras;
    }

    public DatosEleccionArea[] cargarDatosResultadoPor(Encuesta encuesta) {
        List<AreaEncuesta> listaResultados = null;
        listaResultados = resultadosPorEncuesta(encuesta);
        DatosEleccionArea[] listaBarras = null;
        if (listaResultados != null && !listaResultados.isEmpty()) {

            listaBarras = promedioResultados(listaResultados);
        }
        return listaBarras;
    }

    public DatosEleccionArea[] cargarDatosResultadoEncuesta(int opcion) {
        string_grafico = null;
        List<AreaEncuesta> listaResultados = null;
        switch (opcion) {
            case 1111:
                listaResultados = resultadosPorEncuesta(encuesta);
                break;
            case 111:
                listaResultados = resultadosAreaEncuestaPorEstudiante(estudiante);
                break;
            case 11:
                listaResultados = resultadosAreaEncuestaPorGrado(grado);
                break;
            case 1:
                listaResultados = resultadosAreaEncuestaPorInstitucion(institucion);
                break;
            default:
                System.out.println("opcion incorrecta");
        }
        DatosEleccionArea[] listaBarras = null;
        if (listaResultados != null && !listaResultados.isEmpty()) {

            listaBarras = promedioResultados(listaResultados);
        }
        return listaBarras;
    }

    DatosEleccionArea[] listaBarrasGrafico;

    public DatosEleccionArea[] getListaBarrasGrafico() {
        if (listaBarrasGrafico != null) {
            System.out.println("rgb:" + listaBarrasGrafico[0].getColor().rgba());
        }
        return listaBarrasGrafico;
    }

    public void setListaBarrasGrafico(DatosEleccionArea[] listaBarrasGrafico) {
        this.listaBarrasGrafico = listaBarrasGrafico;
    }

    public String cargarGraficoResultadoEncuesta(int opcion) {
        listaBarrasGrafico = cargarDatosResultadoEncuesta(opcion);
        if (listaBarrasGrafico != null && listaBarrasGrafico.length != 0) {
            string_grafico = obtenerGrafico(listaBarrasGrafico);
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay suficientes datos para crear la estadistica", "");
            context.addMessage(null, msg);
        }
        return string_grafico;
    }

    private String obtenerGrafico(DatosEleccionArea[] listaDatos) {
        List datas = new ArrayList();
        List<Color> colors = new ArrayList();
        BarData data = new BarData();
        for (DatosEleccionArea dato : listaDatos) {
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
        BarChart unbar = new BarChart(data);
        BarOptions options = new BarOptions();
        options.setResponsive(true);
        // Comenzar el axis Y en 0 (cero)
        options.setScales((new BarScale()).addyAxes((new YAxis<LinearTicks>()).setTicks((new LinearTicks()).setBeginAtZero(Boolean.TRUE))));
        unbar.setOptions(options);
        return unbar.toJson();
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

    public List<AreaEncuesta> resultadosAreaEncuestaPorInstitucion(Institucion una_institucion) {
        if (una_institucion != null) {
            List<AreaEncuesta> listaResultados = new ArrayList();
            List<Grado> listGrado = una_institucion.getGradoList();
            List<AreaEncuesta> listaResult_aux;
            for (Grado un_grado : listGrado) {
                listaResult_aux = resultadosAreaEncuestaPorGrado(un_grado);
                if (listaResult_aux == null || listaResult_aux.isEmpty()) {
                    continue;
                }
                listaResultados.addAll(listaResult_aux);
            }
            return listaResultados;
        }
        return null;
    }

    public List<AreaEncuesta> resultadosAreaEncuestaPorGrado(Grado un_grado) {
        if (un_grado != null) {
            List<AreaEncuesta> listaResultados = new ArrayList();
            List<Encuesta> listaEncuestas = un_grado.getEncuestaList();
            listaTotalEncuestas.addAll(listaEncuestas);
            for (Encuesta uencuesta : listaEncuestas) {

                List<AreaEncuesta> listaResultadosPorAmbiente = uencuesta.getAreaEncuestaList();
                if (listaResultadosPorAmbiente.isEmpty()) {
                    continue;
                }
                listaResultados.addAll(listaResultadosPorAmbiente);
            }
            return listaResultados;
        }
        return null;
    }

    public List<AreaEncuesta> resultadosAreaEncuestaPorEstudiante(Estudiante un_estudiante) {
        if (listaTotalEncuestas == null) {
            listaTotalEncuestas = new ArrayList();
        }
        if (un_estudiante != null) {
            List<AreaEncuesta> listaResultados = new ArrayList();

            List<Encuesta> listaEncuestas = un_estudiante.getEncuestaList();
            listaTotalEncuestas.addAll(listaEncuestas);
            for (Encuesta una_encuesta : listaEncuestas) {

                List<AreaEncuesta> listaResultadosPorAmbiente = una_encuesta.getAreaEncuestaList();
                if (listaResultadosPorAmbiente.isEmpty()) {
                    continue;
                }
                listaResultados.addAll(listaResultadosPorAmbiente);
            }
            return listaResultados;
        }
        return null;
    }

    public List<AreaEncuesta> resultadosPorEncuesta(Encuesta encuesta) {
        System.out.println("resultadosPorEncuesta: " + encuesta);
        if (listaTotalEncuestas == null) {
            listaTotalEncuestas = new ArrayList();
        }
        listaTotalEncuestas.add(encuesta);
        if (encuesta != null) {
            List<AreaEncuesta> listaResultados = getAreaEncuestaController().getItemsPorEncuesta(encuesta);
            return listaResultados;
        }
        return null;
    }

    /**
     *
     * @param listaAreaEncuesta
     * @return
     */
    public DatosEleccionArea[] promedioResultados(List<AreaEncuesta> listaAreaEncuesta) {
        if (listaAreaEncuesta == null || listaAreaEncuesta.isEmpty()) {
            return null;
        }
        int maxIdArea = getAreaController().maxIdArea();
        int[][] resultado = new int[maxIdArea][3];
        /**
         * 1: mas me gusta 2: menos me gusta 3: por nota
         */
        // contar elecciones por timpo de eleccion por area
        int k = 0;

        for (AreaEncuesta areaEnc : listaAreaEncuesta) {
            k++;
            if (areaEnc == null || areaEnc.getIdArea() == null) {
                continue;
            }
            int indiceArea = areaEnc.
                    getIdArea().
                    getIdArea() - 1;
            int indiceIdTipoEleccion = areaEnc.getTipoEleccionMateria().getIdTipoEleccionMateria() - 1;
            resultado[indiceArea][indiceIdTipoEleccion]++;
        }

        List<TipoEleccionMateria> listaTipoEleccion = getTipoEleccionMateriaController().getItems();
        int[] indicesMaximos = new int[listaTipoEleccion.size()];
        // inicializar los indices
        for (int j = 0; j < indicesMaximos.length; j++) {
            indicesMaximos[j] = 0;
        }
        // buscar maximo contador por materia por tipo de eleccion
        for (int i = 1; i < maxIdArea; i++) {
            for (int j = 0; j < indicesMaximos.length; j++) {
                if (resultado[indicesMaximos[j]][j] < resultado[i][j]) {
                    indicesMaximos[j] = i;
                }
            }
        }

        DatosEleccionArea[] listaDatos = new DatosEleccionArea[listaTipoEleccion.size()];
        for (int j = 0; j < listaTipoEleccion.size(); j++) {
            TipoEleccionMateria untipoEM = listaTipoEleccion.get(j);
            int tipo = untipoEM.getIdTipoEleccionMateria();
            if (listaDatos[tipo - 1] == null) {
                listaDatos[tipo - 1] = new DatosEleccionArea();
            }
            listaDatos[tipo - 1].setTipo(1);
            listaDatos[tipo - 1].setLabel(untipoEM.getTipo());
            String[] color = listaTipoEleccion.get(j).getColor().split(",");
            int r = Integer.parseInt(color[0]);
            int g = Integer.parseInt(color[1]);
            int b = Integer.parseInt(color[2]);
            double a = Double.parseDouble(color[3]);
            listaDatos[tipo - 1].setColor(new Color(r, g, b, a));
            listaDatos[tipo - 1].setValor((double) resultado[indicesMaximos[j]][j]);
            listaDatos[tipo - 1].setArea(getAreaController().getArea(indicesMaximos[j] + 1));
        }

        return listaDatos;
    }

    @Override
    protected AbstractFacade getFacade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
