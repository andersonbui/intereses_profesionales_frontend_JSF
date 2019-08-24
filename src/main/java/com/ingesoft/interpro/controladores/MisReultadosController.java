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
            System.out.println("listaresUnicos != null) : " + listaresUnicos);
            return listaresUnicos;
        }
        System.out.println("encuesta : " + encuesta);
        if (encuesta != null) {
            System.out.println("encuesta!= null : " + listaresUnicos);
            listaresUnicos = getEstadisticaAmbienteController().generarDatosRiasec(encuesta);
        } else {
            System.out.println("else : " + listaresUnicos);
            EstudianteController estudianteController = getEstudianteController();
            estudiante = estudianteController.getEstudiantePorPersona(getLoginController().getPersonaActual());
            listaresUnicos = getEstadisticaAmbienteController().generarDatosRiasec(estudiante);
        }
        return listaresUnicos;
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

    public List<DatosRiasec> datosRiasec2() {
        if (listaresUnicos != null) {
            return listaresUnicos;
        }
        EstudianteController estudianteController = getEstudianteController();
        estudiante = estudianteController.getEstudiantePorPersona(getLoginController().getPersonaActual());
        if (encuesta != null) {
            listaresUnicos = getEstadisticaAmbienteController().obtenerDatosRiasec(encuesta);
        } else {
            listaresUnicos = getEstadisticaAmbienteController().obtenerDatosRiasec(estudiante);
        }
        return listaresUnicos;
    }
}
