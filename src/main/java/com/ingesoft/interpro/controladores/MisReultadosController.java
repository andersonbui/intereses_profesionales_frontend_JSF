package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.Vistas;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.entidades.Grado;
import com.ingesoft.interpro.entidades.Institucion;
import com.ingesoft.interpro.entidades.Persona;
import com.ingesoft.interpro.facades.AbstractFacade;
import java.io.Serializable;
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
    }

    public String getGraficoModelo() {
        if (string_grafico == null) {
            string_grafico = cargarGraficoMisResultado();
        }
        System.out.println("getGraficoModelo: " + string_grafico);
        return string_grafico;
    }

    public String cargarGraficoMisResultado() {
        EstudianteController estudianteController = getEstudianteController();
        estudiante = estudianteController.getEstudiantePorPersona(getLoginController().getPersonaActual());
//        estudianteController.setEncuesta(estudiante);
        if (encuesta != null) {
            System.out.println("cargarGraficoMisResultado encuesta ");
            return getEstadisticaAmbienteController().cargarGraficoResultadoEncuesta(encuesta);
        }
        return getEstadisticaAmbienteController().cargarGraficoResultadoEncuestaEstudiante(estudiante);
    }

    @Override
    protected AbstractFacade getFacade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String verMisResultados(Persona persona) {
        getEstudianteController().cargarSelectedPorPersona(persona);
        return Vistas.misResultados();
    }
}
