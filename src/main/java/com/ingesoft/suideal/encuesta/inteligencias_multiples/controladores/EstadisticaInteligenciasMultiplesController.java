package com.ingesoft.suideal.encuesta.inteligencias_multiples.controladores;

import com.ingesoft.interpro.controladores.Controllers;
import com.ingesoft.interpro.controladores.EstadisticasControllerInterface;
import com.ingesoft.interpro.controladores.util.ResultadoEstMultiple;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.facades.AbstractFacade;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "estadisticaInteligenciasMultiplesController")
@SessionScoped
public class EstadisticaInteligenciasMultiplesController  extends Controllers implements Serializable, EstadisticasControllerInterface {

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
        if(lista_encuestas != null && lista_encuestas.size() > 0){
            Encuesta encuesta = lista_encuestas.get(0);
            if(encuesta != null && encuesta.getEncuestaInteligenciasMultiples() != null){
                resultadosEstMultiple.setRespuestaPorInteligencia(encuesta.getEncuestaInteligenciasMultiples().getRespuestaPorInteligencia());
            }
        }
    }

}
