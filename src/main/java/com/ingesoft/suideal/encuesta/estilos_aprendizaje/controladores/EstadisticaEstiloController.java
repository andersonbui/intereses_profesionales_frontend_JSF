package com.ingesoft.suideal.encuesta.estilos_aprendizaje.controladores;

import com.ingesoft.interpro.controladores.Controllers;
import com.ingesoft.interpro.controladores.util.ResultadoEstMultiple;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.facades.AbstractFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "estadisticaEstiloController")
@SessionScoped
public class EstadisticaEstiloController  extends Controllers implements Serializable {

    private List<ResultadoEstMultiple> items;
    public String email;

    @Override
    protected AbstractFacade getFacade() {
        return null;
    }
    
    public EstadisticaEstiloController() {
        
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
        if(encuesta.getEncuestaEstilosAprendizaje() != null){
            resEstMultiple.setRespuestaPorEstilo(encuesta.getEncuestaEstilosAprendizaje().getRespuestaPorEstiloList());
            resEstMultiple.setEncuesta(encuesta);
//            System.out.println("encuesta"+encuesta);
        }
        resEstMultiple.setPersonalidad("ISEJ");
        return resEstMultiple;
    }

}
