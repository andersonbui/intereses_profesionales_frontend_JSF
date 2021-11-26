package com.ingesoft.suideal.encuesta.chaside.controladores;

import com.ingesoft.suideal.encuesta.chaside.controladores.*;
import com.ingesoft.interpro.controladores.Controllers;
import com.ingesoft.interpro.controladores.EstadisticasControllerInterface;
import com.ingesoft.interpro.controladores.util.ResultadoEstMultiple;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.facades.AbstractFacade;
import com.ingesoft.suideal.encuesta.chaside.entidades.ResultadoChaside;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "estadisticaChasideController")
@SessionScoped
public class EstadisticaChasideController  extends Controllers implements Serializable, EstadisticasControllerInterface {

    @Override
    protected AbstractFacade getFacade() {
        return null;
    }

    public ResultadoChaside[] obtenerMayorResultado( List<ResultadoChaside> listaResultados){
        
        int indice;
        ResultadoChaside[] listaResultadod = new ResultadoChaside[2];
        for (ResultadoChaside resultado : listaResultados) {
            int clase = resultado.getResultadoChasidePK().getIdClaseChaside();
            int tipo = resultado.getResultadoChasidePK().getIdTipoChaside();
            indice = clase - 1;
            
            if( listaResultadod[indice] == null || listaResultadod[indice].getResultado() < resultado.getResultado()){
                listaResultadod[indice] = resultado;
            }
        }
        
        return listaResultadod;
    }
    
    /**
     * 
     * @param resultadosEstMultiple
     */
    @Override
    public void setResultados(ResultadoEstMultiple resultadosEstMultiple) {
        System.out.println("obteniendo resultado chaside");
        Encuesta encuesta = resultadosEstMultiple.getEncuesta();
        List<ResultadoChaside>  listaResultadoChaside;
        if(encuesta != null && encuesta.getEncuestaChaside() != null){
            listaResultadoChaside = encuesta.getEncuestaChaside().getResultadoChasideList();
            ResultadoChaside[] listaResultadod = obtenerMayorResultado(listaResultadoChaside);
            resultadosEstMultiple.setResultadoChaside(listaResultadod);
        }
    }

}
