package com.ingesoft.suideal.encuesta.estilos_aprendizaje.controladores;

import com.ingesoft.interpro.controladores.Controllers;
import com.ingesoft.interpro.controladores.EstadisticasControllerInterface;
import com.ingesoft.interpro.controladores.util.ResultadoEstMultiple;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.facades.AbstractFacade;

import java.io.Serializable;
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
        Encuesta encuesta = resultadosEstMultiple.getEncuesta();
        if(encuesta != null && encuesta.getEncuestaEstilosAprendizaje() != null){
            resultadosEstMultiple.setRespuestaPorEstilo(encuesta.getEncuestaEstilosAprendizaje().getRespuestaPorEstiloList());
        }
    }

}
