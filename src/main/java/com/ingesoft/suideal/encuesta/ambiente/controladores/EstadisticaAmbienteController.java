package com.ingesoft.suideal.encuesta.ambiente.controladores;

import be.ceau.chart.BarChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.scales.BarScale;
import be.ceau.chart.options.scales.YAxis;
import be.ceau.chart.options.ticks.LinearTicks;
import com.ingesoft.interpro.controladores.Controllers;
import com.ingesoft.interpro.controladores.EncuestaController;
import com.ingesoft.interpro.controladores.EstadisticasControllerInterface;
import com.ingesoft.interpro.controladores.util.DatosAmbiente;
import com.ingesoft.interpro.controladores.util.ResultadoEstMultiple;
import com.ingesoft.interpro.entidades.DatosRiasec;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.entidades.EstudianteGrado;
import com.ingesoft.interpro.entidades.Grado;
import com.ingesoft.interpro.entidades.Institucion;
import com.ingesoft.interpro.entidades.ResultadoPorAmbiente;
import com.ingesoft.interpro.entidades.TipoAmbiente;
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

@ManagedBean(name = "estadisticaAmbienteController")
@SessionScoped
public class EstadisticaAmbienteController extends Controllers implements Serializable, EstadisticasControllerInterface {

    Institucion institucion;
    Grado grado;
    Estudiante estudiante;
    Encuesta encuesta;
    List<Encuesta> listaTotalEncuestas;
    Date fechaInicio;
    Date fechafin;
    private String string_grafico;
    String[] colores = {"008000", "FF0000", "FFD42A", "0000FF", "FFFF00", "00FFFF"};
    String tiempo;
    String email;

    List<DatosRiasec> listaDatosRaisec;
    private String personalidad;

    List<ResultadoEstMultiple> cadenasgrafico;

    public EstadisticaAmbienteController() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        cadenasgrafico = null;
    }

    public void reiniciarTodo() {
        string_grafico = null;
        listaDatosRaisec = null;
        cadenasgrafico = null;
        personalidad = null;
        institucion = null;
        estudiante = null;
        grado = null;
        getEncuestaController().setItems(null);
    }


    public List<DatosRiasec> getListaDatosRaisec() {
        return listaDatosRaisec;
    }

    public String getGraficoModelo() {
//        System.out.println("getGraficoModelo: " + string_grafico);
        return string_grafico;
    }

    public String[] getGraficoMultiple() {
        String[] graficas = new String[2];
//        System.out.println("getGraficoModelo: " + string_grafico);
        return graficas;
    }

    /**
     * Ejecutar cuando se ingresar email para busqueda de estudiante
     */
    public void ingresarEmailBusqueda() {
        System.out.println("ingresarEmailBusqueda");
        cadenasgrafico = null;
        string_grafico = null;
        listaDatosRaisec = null;
        personalidad = null;
        institucion = null;
        estudiante = null;
        grado = null;
        getEncuestaController().setItems(null);
    }
    
    /**
     * 
     * @return 
     */
    public String cargarGraficoResultadoAmbiente() {
        int opcion = detectarTipoEstadistica();
        System.out.println("opcion: " + opcion);
        listaTotalEncuestas = new ArrayList();
        EncuestaController encuestaController = getEncuestaController();

        String result = cargarGraficoResultadoEncuesta(opcion);

        encuestaController.setItems(listaTotalEncuestas);
        personalidad = getEstadisticaPersonalidadController().obtenerPromedioPersonalidad(listaTotalEncuestas);

        return result;
    }

    /**
     * 
     * @return 
     */
    public List<ResultadoEstMultiple> cargarEstadisticasPorCadaEstudiante() {
        if (getInstitucion() != null) {
            cadenasgrafico = null;
            List<Estudiante> listaEst = getEstudianteController().getItems(getInstitucion());
            if (listaEst != null && !listaEst.isEmpty()) {
                for (Estudiante unestudiante : listaEst) {
                    ResultadoEstMultiple unres = cargarGraficoResultadoAmbiente(unestudiante);
                    if( unres != null ) {
                        if( cadenasgrafico == null ) {
                            cadenasgrafico = new ArrayList<>();
                        }
                        cadenasgrafico.add(unres);
                    }
                }
            }
            return cadenasgrafico;
        }
        if( getEmail() != null && !"".equals(getEmail()) ) {
            List<Estudiante> listaEst = getEstudianteController().getEstudiantePorEmail(getEmail());
            if (listaEst != null && !listaEst.isEmpty()) {
                for (Estudiante unestudiante : listaEst) {
                    ResultadoEstMultiple unres = cargarGraficoResultadoAmbiente(unestudiante);
                    if( unres != null ) {
                        if( cadenasgrafico == null ) {
                            cadenasgrafico = new ArrayList<>();
                        }
                        cadenasgrafico.add(unres);
                    }
                }
            }
            return cadenasgrafico;
        }
        return cadenasgrafico;
    }
    
    public List<ResultadoEstMultiple> getEstadisticasPorCadaEstudiante() {
        return cadenasgrafico;
    }

    public ResultadoEstMultiple cargarGraficoResultadoAmbiente(Estudiante estudiante) {
        
        List<Encuesta> lisEncuestas = estudiante.getEncuestaList();
        listaTotalEncuestas.addAll(lisEncuestas);
        List<ResultadoPorAmbiente> listaResultados = resultadosPorEncuestas(lisEncuestas);
        
        ResultadoEstMultiple resul = cargarGraficoResultadoAmbiente(listaResultados);
        
        resul.setEstudiante(estudiante);
        return resul;
    }
    
    public ResultadoEstMultiple cargarGraficoResultadoAmbiente(List<ResultadoPorAmbiente> listaResultados) {
        ResultadoEstMultiple resul = new ResultadoEstMultiple();
        List listaEncuestas = new ArrayList();
        String stringgrafico = null;

        List<Encuesta> lisEncuestas = estudiante.getEncuestaList();
        listaTotalEncuestas.addAll(lisEncuestas);
        listaResultados = resultadosPorEncuestas(lisEncuestas);
        
        DatosAmbiente[] listaBarras = null;
        List<DatosRiasec> listaRiasec = null;
        if (listaResultados != null && !listaResultados.isEmpty()) {
            listaBarras = promedioResultados(listaResultados);
            stringgrafico = obtenerGrafico(listaBarras);
            // datos riasec
            listaRiasec = generarDatosRiasec(listaBarras);
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay suficientes datos para crear la estadistica", "");
            context.addMessage(null, msg);
            return null;
        }
        
        resul.setGrafico(stringgrafico);
        resul.setEstudiante(estudiante);
        resul.setListaDatRiasec(listaRiasec);

        EncuestaController encuestaController = getEncuestaController();
        encuestaController.setItems(listaEncuestas);
        
        resul.setPromedioPuntajeEValuacion(encuestaController.promedioPuntajeEvaluacion());
        resul.setPromedioPuntajeEncuesta(encuestaController.promedioPuntajeEncuesta());
        
        String unapersonalidad = getEstadisticaPersonalidadController().obtenerPromedioPersonalidad(listaEncuestas);
        resul.setPersonalidad(unapersonalidad);
        return resul;
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
//        listaResultados = resultadosPorEstudiante(estudiante, listaTotalEncuestas);
        List<Encuesta> lisEncuestas = estudiante.getEncuestaList();
        if(listaTotalEncuestas == null){
            listaTotalEncuestas = new ArrayList<>();
        }
        listaTotalEncuestas.addAll(lisEncuestas);
        listaResultados = resultadosPorEncuestas(lisEncuestas);
        
        DatosAmbiente[] listaBarras = null;
        if (listaResultados != null && !listaResultados.isEmpty()) {

            listaBarras = promedioResultados(listaResultados);
        }
        return listaBarras;
    }

    public List<DatosRiasec> obtenerDatosRiasec(Encuesta encuesta) {
        if (listaDatosRaisec != null) {
            return listaDatosRaisec;
        }
        return listaDatosRaisec = generarDatosRiasec(encuesta);
    }

    public List<DatosRiasec> generarDatosRiasec(Encuesta encuesta) {
        DatosAmbiente[] datos = cargarDatosResultadoPor(encuesta);
        return generarDatosRiasec(datos);
    }

    public List<DatosRiasec> obtenerDatosRiasec(Estudiante estudiante) {
        if (listaDatosRaisec != null) {
            return listaDatosRaisec;
        }
        return listaDatosRaisec = generarDatosRiasec(estudiante);
    }

    public List<DatosRiasec> generarDatosRiasec(Estudiante estudiante) {
        DatosAmbiente[] datos = cargarDatosResultadoPor(estudiante);
        return generarDatosRiasec(datos);
    }

    /**
     * 
     * @param datos
     * @return 
     */
    public List<DatosRiasec> generarDatosRiasec(DatosAmbiente[] datos) {
        if (datos == null) {
            return null;
        }
        TipoAmbiente amb1 = null;

        for (DatosAmbiente undato : datos) {
            if (amb1 == null || datos[undato.getTipoAmbiente().getIdTipoAmbiente() - 1].getValor() > datos[amb1.getIdTipoAmbiente() - 1].getValor()) {
                amb1 = datos[undato.getTipoAmbiente().getIdTipoAmbiente() - 1].getTipoAmbiente();
            }
        }

        List<DatosRiasec> listares = getDatosRiasecController().getItemsByTiposAmbiente(amb1);
        // segundo ambiente
        TipoAmbiente amb2 = null;
        double valorDato = 0;
        double mayorValor = -10;
        for (DatosRiasec undato : listares) {
            if (undato.getIdTipoAmbiente2() == null) {
                if (0 > mayorValor) {
                    amb2 = null;
                    mayorValor = 0;
                }
            } else {
                valorDato = datos[undato.getIdTipoAmbiente2().getIdTipoAmbiente() - 1].getValor();
                if (valorDato == 0) {
                    valorDato = -1;
                }
                if ((mayorValor < 0 && amb2 == null) || valorDato > mayorValor) {
                    amb2 = datos[undato.getIdTipoAmbiente2().getIdTipoAmbiente() - 1].getTipoAmbiente();
                    mayorValor = valorDato;
                }
            }
        }

        listares = getDatosRiasecController().getItemsByTiposAmbiente(amb1, amb2);
        // tercer ambiente
        TipoAmbiente amb3 = null;
        mayorValor = -10;
        for (DatosRiasec undato : listares) {
            if (undato.getIdTipoAmbiente3() == null) {
                if (0 > mayorValor) {
                    amb3 = null;
                    mayorValor = 0;
                }
            } else {
                valorDato = datos[undato.getIdTipoAmbiente3().getIdTipoAmbiente() - 1].getValor();
                if (valorDato == 0) {
                    valorDato = -1;
                }
                if ((mayorValor < 0 && amb3 == null) || valorDato > mayorValor) {
                    amb3 = datos[undato.getIdTipoAmbiente3().getIdTipoAmbiente() - 1].getTipoAmbiente();
                    mayorValor = valorDato;
                }
            }
        }

        List<DatosRiasec> unaListaresUnicos = new ArrayList();
        // Escoger solo los valores unicos
//        System.out.println("Sin datos riasec que coincidan: amb1: " + amb1 + "; amb2: " + amb2 + "; amb3:" + amb3 + " => listaprof " + listares);
        listares = getDatosRiasecController().getItemsByTiposAmbiente(amb1, amb2, amb3);
//        System.out.println("Nuevos ambientes escogidos: amb1: " + amb1 + "; amb2: " + amb2 + "; amb3:" + amb3 + " => listaprof " + listares);

        if (listares != null) {
            for (DatosRiasec datosR : listares) {
                if (!unaListaresUnicos.contains(datosR)) {
                    unaListaresUnicos.add(datosR);
                }
            }
        }

        return unaListaresUnicos;
    }

    /**
     * 
     * @param resultadosEstMultiple 
     */
    @Override
    public void setResultados(ResultadoEstMultiple resultadosEstMultiple) {
        
        List<Encuesta> lista_encuestas = resultadosEstMultiple.getListaEncuestas();
        if(lista_encuestas != null && lista_encuestas.size() > 0){
            System.out.println("lista_encuestas Ambiente:"+lista_encuestas.size());
            Encuesta una_encuesta = lista_encuestas.get(0);
            String grafico = obtenergraficoPorEncuesta(una_encuesta);
            resultadosEstMultiple.setGrafico(grafico);
            resultadosEstMultiple.setListaDatRiasec(generarDatosRiasec(una_encuesta));
            System.out.println("Ambiente:"+grafico);
        }
    }
    
    /**
     * 
     * @param encuesta
     * @return 
     */
    public String obtenergraficoPorEncuesta(Encuesta encuesta){
        List<ResultadoPorAmbiente> listaResultados = resultadosPorEncuesta( encuesta );
        DatosAmbiente[] listaBarras = null;
        String grafico = "";
        if ( listaResultados != null && !listaResultados.isEmpty() ) {
            listaBarras = promedioResultados(listaResultados);
            if(listaBarras != null){
                grafico = obtenerGrafico( listaBarras );
                return grafico;
            }
        }
        
        return grafico;
    }
    
    /**
     * 
     * @param encuesta
     * @return 
     */
    public DatosAmbiente[] cargarDatosResultadoPor(Encuesta encuesta) {
        List<ResultadoPorAmbiente> listaResultados = null;
        listaResultados = resultadosPorEncuesta(encuesta);
        DatosAmbiente[] listaBarras = null;
        if (listaResultados != null && !listaResultados.isEmpty()) {

            listaBarras = promedioResultados(listaResultados);
        }
        return listaBarras;
    }

    /**
     * 
     * @param opcion
     * @return 
     */
    private List<ResultadoPorAmbiente> cargarDatosResultadoEncuesta(int opcion) {
        string_grafico = null;
        List<ResultadoPorAmbiente> listaResultados = null;
        switch (opcion) {
            case 1111:
                listaResultados = resultadosPorEncuesta(encuesta);
                break;
            case 111:
                List<Encuesta> listaEncuestas  = estudiante.getEncuestaList();
                if(listaTotalEncuestas == null){
                    listaTotalEncuestas =  new ArrayList();
                }
                listaTotalEncuestas.addAll(listaEncuestas);
                listaResultados = resultadosPorEncuestas(listaEncuestas);
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
        return listaResultados;
    }

    /**
     * 
     * @param opcion
     * @return 
     */
    public String cargarGraficoResultadoEncuesta(int opcion) {
        List<ResultadoPorAmbiente> listaResultados = cargarDatosResultadoEncuesta(opcion);
        
        DatosAmbiente[] listaBarras = null;
        if (listaResultados != null && !listaResultados.isEmpty()) {

            listaBarras = promedioResultados(listaResultados);
        }
        
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
            List<Encuesta> listaEncuestas = un_grado.getEncuestaList();
            listaTotalEncuestas.addAll(listaEncuestas);
            for (Encuesta uencuesta : listaEncuestas) {

                List<ResultadoPorAmbiente> listaResultadosPorAmbiente = uencuesta.getResultadoPorAmbienteList();
                if (listaResultadosPorAmbiente.isEmpty()) {
                    continue;
                }
                listaResultados.addAll(listaResultadosPorAmbiente);
            }
            return listaResultados;
        }
        return null;
    }

//    public List<ResultadoPorAmbiente> resultadosPorEstudiante(Estudiante un_estudiante, List listaTotalEncuestas) {
//        if (listaTotalEncuestas == null) {
//            listaTotalEncuestas = new ArrayList();
//        }
//        if (un_estudiante != null) {
//            List<ResultadoPorAmbiente> listaResultados = new ArrayList();
//
//            List<Encuesta> listaEncuestas = un_estudiante.getEncuestaList();
//            listaTotalEncuestas.addAll(listaEncuestas);
//            for (Encuesta una_encuesta : listaEncuestas) {
//
//                List<ResultadoPorAmbiente> listaResultadosPorAmbiente = una_encuesta.getResultadoPorAmbienteList();
//                if (listaResultadosPorAmbiente.isEmpty()) {
//                    continue;
//                }
//                listaResultados.addAll(listaResultadosPorAmbiente);
//            }
//            return listaResultados;
//        }
//        return null;
//    }
    
    /**
     * 
     * @param listaEncuestas
     * @return 
     */
    public List<ResultadoPorAmbiente> resultadosPorEncuestas( List<Encuesta> listaEncuestas ) {
        List<ResultadoPorAmbiente> listaResultados = new ArrayList();

        for (Encuesta una_encuesta : listaEncuestas) {

            List<ResultadoPorAmbiente> listaResultadosPorAmbiente = una_encuesta.getResultadoPorAmbienteList();
            if (listaResultadosPorAmbiente.isEmpty()) {
                continue;
            }
            listaResultados.addAll(listaResultadosPorAmbiente);
            
        }
        return listaResultados;
    }

    /**
     * 
     * @param encuesta
     * @return 
     */
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
            listaDato.setValor(listaDato.getValor() * 6 / listaResultadosPorAmbiente.size());
        }
        return listaDatos;
    }

    @Override
    protected AbstractFacade getFacade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
