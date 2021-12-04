package com.ingesoft.suideal.encuesta.estilos_aprendizaje.controladores;

import com.ingesoft.interpro.controladores.Controllers;
import com.ingesoft.interpro.controladores.EstadisticasControllerInterface;
import com.ingesoft.interpro.controladores.util.ResultadoEstMultiple;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.facades.AbstractFacade;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "estadisticaEstiloController")
@SessionScoped
public class EstadisticaEstiloController  extends Controllers implements Serializable, EstadisticasControllerInterface {

    @Override
    protected AbstractFacade getFacade() {
        return null;
    }

    /**
     * 
     * @param resultadosEstMultiple
     */
    @Override
    public void setResultados(ResultadoEstMultiple resultadosEstMultiple) {
        List<Encuesta> lista_encuestas = resultadosEstMultiple.getListaEncuestas();
        System.out.println("lista_encuestas:"+lista_encuestas);
        if(lista_encuestas != null && lista_encuestas.size() > 0){
            Encuesta encuesta = lista_encuestas.get(0);
            if(encuesta != null && encuesta.getEncuestaEstilosAprendizaje() != null){
                resultadosEstMultiple.setRespuestaPorEstilo(encuesta.getEncuestaEstilosAprendizaje().getRespuestaPorEstiloList());
            }
        }
    }

}
