package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.ResultadoEstMultiple;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.facades.AbstractFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "estadisticaController")
@SessionScoped
public class EstadisticaController  extends Controllers implements Serializable {

    private List<ResultadoEstMultiple> items;
    public String email;
    List<EstadisticasControllerInterface> listaEstadisticas;

    @Override
    protected AbstractFacade getFacade() {
        return null;
    }
    
    public EstadisticaController() {
        listaEstadisticas = new ArrayList();
        listaEstadisticas.add(getEstadisticaEstiloController());
        listaEstadisticas.add(getEstadisticaPersonalidadController());
        listaEstadisticas.add(getEstadisticaAmbienteController());
    }

    public void inicializar(Encuesta selected) {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ResultadoEstMultiple> getItemsByEmail() {
        return items;
    }
    
    public List<ResultadoEstMultiple> calcularItemsByEmail() {
        items = calcularItemsByEmail(email);
        return items;
    }
    public List<ResultadoEstMultiple> calcularItemsByEmail(String email) {
            List<Estudiante> listEst = getEstudianteController().getEstudiantePorEmail(email);
            System.out.println("listEst: "+listEst);
            List<ResultadoEstMultiple> listadoResultado = new ArrayList();
                
            if(listEst != null && !listEst.isEmpty()) {
                Estudiante est = listEst.get(0);
                List listadoResp = calcularItemsByEstudiante(est);
                listadoResultado.addAll(listadoResp);
            }
            
        return listadoResultado;
    }

    public List<ResultadoEstMultiple> calcularItemsByEstudiante(Estudiante estudiante) {
        List<ResultadoEstMultiple> listadoResultado = new ArrayList();

        List<Encuesta> listEncuestas = getEncuestaController().listarEncuestasSelected(estudiante);

        for (Encuesta encuesta : listEncuestas) {
            ResultadoEstMultiple resEstMultiple = calcularItemsByEncuesta(encuesta);
            listadoResultado.add(resEstMultiple);
        }
        return listadoResultado;
    }
    
    public ResultadoEstMultiple calcularItemsByEncuesta(Encuesta encuesta) {
        ResultadoEstMultiple resEstMultiple = new ResultadoEstMultiple();
        resEstMultiple.setEncuesta(encuesta);
        
        for (EstadisticasControllerInterface listaEstadistica : listaEstadisticas) {
            listaEstadistica.setResultados(resEstMultiple);
        }
        
        return resEstMultiple;
    }

}
