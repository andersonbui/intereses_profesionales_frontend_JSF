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
        listaEstadisticas.add(getEstadisticaInteligenciasMultiplesController());
        listaEstadisticas.add(getEstadisticaChasideController());
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
        return calcularItemsByEmail(email, ResultadoEstMultiple.METODO_INDIV);
    }
   
    private List<ResultadoEstMultiple> calcularItemsByEmail(String email, String metodo ) {
            List<Estudiante> listEst = getEstudianteController().getEstudiantePorEmail(email);
            System.out.println("listEst: "+listEst);
            List<ResultadoEstMultiple> listadoResultado = new ArrayList();
            
            if(listEst != null && !listEst.isEmpty()) {
                Estudiante est = listEst.get(0);
                List listadoResp = calcularEstadisticas(est, metodo);
                listadoResultado.addAll(listadoResp);
            }
            
        return listadoResultado;
    }

    public List<ResultadoEstMultiple> calcularEstadisticas(Estudiante estudiante, String metodo ) {
        List<ResultadoEstMultiple> listadoResultado = null;

        List<Encuesta> listEncuestas = getEncuestaController().listarEncuestasSelected(estudiante);
        listadoResultado = EstadisticaController.this.calcularEstadisticas(listEncuestas, metodo);
        
        return listadoResultado;
    }
    
    public List<ResultadoEstMultiple> calcularEstadisticas(List<Encuesta> listEncuestas, String metodo ) {
        List<ResultadoEstMultiple> listadoResultado = new ArrayList();
        
        if(metodo != null && !"".equals(metodo) && !ResultadoEstMultiple.METODO_INDIV.equals(metodo)){
            ResultadoEstMultiple resEstMultiple = new ResultadoEstMultiple();
            resEstMultiple.setListaEncuestas(listEncuestas);
            resEstMultiple.setMetodo(metodo);
            resEstMultiple = calcularItemsByEncuesta(resEstMultiple);
            listadoResultado.add(resEstMultiple);
        } else {
            for (Encuesta encuesta : listEncuestas) {
                List<Encuesta> auxlistaEncuesta = new ArrayList<>();
                auxlistaEncuesta.add(encuesta);
                ResultadoEstMultiple resEstMultiple = new ResultadoEstMultiple();
                resEstMultiple.setListaEncuestas(auxlistaEncuesta);
                resEstMultiple = calcularItemsByEncuesta(resEstMultiple);
                listadoResultado.add(resEstMultiple);
            }
        }
        
        return listadoResultado;
    }
    
    private ResultadoEstMultiple calcularItemsByEncuesta(ResultadoEstMultiple resEstMultiple ) {
        
        listaEstadisticas.forEach(unaEstadistica -> {
            unaEstadistica.setResultados(resEstMultiple);
        });
        
        return resEstMultiple;
    }
    
}
