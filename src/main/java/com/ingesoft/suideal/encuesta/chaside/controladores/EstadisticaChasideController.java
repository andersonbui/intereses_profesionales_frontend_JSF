package com.ingesoft.suideal.encuesta.chaside.controladores;

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
        if(listaResultados == null || listaResultados.isEmpty()) {
            return null;
        }
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
        List<Encuesta> lista_encuestas = resultadosEstMultiple.getListaEncuestas();
        if(lista_encuestas != null && lista_encuestas.size() > 0){
//            System.out.println("lista_encuestas chaside:"+lista_encuestas.size());
            Encuesta encuesta = lista_encuestas.get(0);
            if(encuesta.getEncuestaChaside() != null){
                List<ResultadoChaside> listaResultadoChaside = encuesta.getEncuestaChaside().getResultadoChasideList();
                ResultadoChaside[] listaResultadod = obtenerMayorResultado(listaResultadoChaside);
                resultadosEstMultiple.setResultadoChaside(listaResultadod);
            }
        }
    }

}