package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.DatosAmbiente;
import com.ingesoft.interpro.controladores.util.Vistas;
import com.ingesoft.interpro.entidades.DatosRiasec;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.entidades.Grado;
import com.ingesoft.interpro.entidades.Institucion;
import com.ingesoft.interpro.entidades.Persona;
import com.ingesoft.interpro.entidades.TipoAmbiente;
import com.ingesoft.interpro.facades.AbstractFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "misResultadosController")
@SessionScoped
public class MisReultadosController extends Controller implements Serializable {

    Institucion institucion;
    Grado grado;
    Estudiante estudiante;
    private Encuesta encuesta;
    private String string_grafico;

    private String personalidad;
    List<DatosRiasec> listaresUnicos;

    public MisReultadosController() {

    }

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta selected) {
        this.encuesta = selected;
    }

    public void reiniciarEstadistica() {
        System.out.println("reiniciando ...");
        string_grafico = null;
        listaresUnicos = null;
    }

    public String getGraficoModelo() {
        if (string_grafico == null) {
            string_grafico = cargarGraficoMisResultado();
        }
//        System.out.println("getGraficoModelo: " + string_grafico);
        return string_grafico;
    }

    /**
     * Carga las encuestas para los resultados de anbiente y personalidad
     *
     * @return
     */
    public String cargarGraficoMisResultado() {
        EncuestaController encuestaController = getEncuestaController();
        encuestaController.setSelected(encuesta);
        EstudianteController estudianteController = getEstudianteController();
        estudiante = estudianteController.getEstudiantePorPersona(getLoginController().getPersonaActual());
//        estudianteController.setEncuesta(estudiante);
        if (encuesta != null) {
            System.out.println("cargarGraficoMisResultado encuesta ");
            List<Encuesta> encuestas = new ArrayList();
            encuestas.add(encuesta);
            personalidad = encuesta.getPersonalidad();
            encuestaController.setItems(encuestas);
            return getEstadisticaAmbienteController().cargarGraficoResultadoEncuesta(encuesta);
        } else {
            List<Encuesta> encuestas = encuestaController.listarEncuestasSelected(estudiante);
            if (encuestas == null) {
                return "";
            }
            encuestaController.setItems(encuestas);
            personalidad = encuestaController.obtenerPromedioPersonalidad(encuestas);
            return getEstadisticaAmbienteController().cargarGraficoResultadoEncuestaEstudiante(estudiante);
        }
    }

    public String prediccion() {
        MineriaController mineriaController = getMineriaController();
        String prediccion = null;
        if (encuesta != null) {
            prediccion = mineriaController.predecir(encuesta);
        }
        return prediccion;
    }

    public List<DatosRiasec> datosRiasec() {
        if (listaresUnicos != null) {
            return listaresUnicos;
        }
        EncuestaController encuestaController = getEncuestaController();
        encuestaController.setSelected(encuesta);
        EstudianteController estudianteController = getEstudianteController();
        estudiante = estudianteController.getEstudiantePorPersona(getLoginController().getPersonaActual());
//        estudianteController.setEncuesta(estudiante);
        String cadena = "";
        DatosAmbiente[] datos = null;
        if (encuesta != null) {
            List<Encuesta> encuestas = new ArrayList();
            encuestas.add(encuesta);
            personalidad = encuesta.getPersonalidad();
            encuestaController.setItems(encuestas);
            datos = getEstadisticaAmbienteController().cargarDatosResultadoPor(encuesta);
        } else {
            List<Encuesta> encuestas = encuestaController.listarEncuestasSelected(estudiante);
            if (encuestas != null) {
                encuestaController.setItems(encuestas);
                personalidad = encuestaController.obtenerPromedioPersonalidad(encuestas);
                datos = getEstadisticaAmbienteController().cargarDatosResultadoPor(estudiante);
            }
        }
        if (datos != null) {
            List<DatosAmbiente> lista = Arrays.asList(datos);
            // escoger los 3 de mas alto puntaje
            Collections.sort(lista);
            TipoAmbiente amb1 = lista.get(0).getTipoAmbiente();
            TipoAmbiente amb2 = lista.get(1).getTipoAmbiente();
            TipoAmbiente amb3 = lista.get(2).getTipoAmbiente();
//        String cad = lista.get(0).getValor() + " - " + lista.get(1).getValor() + " - " + lista.get(2).getValor();
            String ambientes = amb1 + " - " + amb2 + " - " + amb3;
//        cadena = Arrays.toString(datos);
//            System.out.println("cadenacadena = ambientes AMB1: " + amb1 + " AMB2: " + amb2 + " AMB3: " + amb3);

            List<DatosRiasec> listares = getDatosRiasecController().getItemsByTiposAmbiente(amb1, amb2, amb3);
            // Escoger solo los valores unicos
            List<DatosRiasec> listaresUnicos = new ArrayList();
            for (DatosRiasec datosR : listares) {
                if (!listaresUnicos.contains(datosR)) {
                    listaresUnicos.add(datosR);
                }
            }
//            System.out.println("cadenacadena = listares " + listaresUnicos);
            return listaresUnicos;
        }
        return null;
    }

    public String getPersonalidad() {
        return personalidad;
    }

    @Override
    protected AbstractFacade getFacade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String verMisResultados(Persona persona) {
        getEncuestaController().setSelected(encuesta);
        getEstudianteController().cargarSelectedPorPersona(persona);
        return Vistas.misResultados();
    }

}
