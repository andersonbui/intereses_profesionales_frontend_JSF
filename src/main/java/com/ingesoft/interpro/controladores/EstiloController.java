package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.PreguntaEstilosAprendizajeFs;
import com.ingesoft.interpro.entidades.RespuestaEstilo;
import com.ingesoft.interpro.entidades.TipoEstilo;
import com.ingesoft.interpro.entidades.TipoEstiloPregunta;
import com.ingesoft.interpro.facades.RespuestaEstiloFacade;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "estiloController")
@SessionScoped
public class EstiloController  extends Controller implements Serializable {

     @EJB
    private com.ingesoft.interpro.facades.RespuestaEstiloFacade ejbFacade;
    private List<RespuestaEstilo> items = null;
    private RespuestaEstilo selected;

    public EstiloController() {

    }

    public void inicializar(Encuesta selected) {

    }

    public RespuestaEstilo getSelected() {
        return selected;
    }

    public void setSelected(RespuestaEstilo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public void getCalculo(){
        
        int idEncuesta = 1;
        Encuesta encuesta = this.getEncuestaController().getEncuesta(idEncuesta);
        ContadorTiposEstilos[] listaContadorTiposEstilos = this.getCalculoTiposEstilos(encuesta);
        for (ContadorTiposEstilos item : listaContadorTiposEstilos) {
            System.out.println("resta: "+ item.contador + "| tipoEstilo: "+item.tipoEstilo.getNombre());
            
        }
        
    }
    
    /**
     * Calcula Estadistica de tipo estilo de una encuesta
     * @param encuesta
     * @return 
     */
    private ContadorTiposEstilos[] getCalculoTiposEstilos(Encuesta encuesta){
        this.items = this.getRespuestaEstiloController().getItemsXEncuesta(encuesta);
        
        if(this.items == null ){
            return null;
        }
        ContadorTiposEstilos[][] contador = new ContadorTiposEstilos[2][4]; /** 8 es la cantidad de tipos de estilo */
        contador[0] = new ContadorTiposEstilos[4];
        contador[1] = new ContadorTiposEstilos[4];
        int indice;
        int columna;
        int fila;
        
        /** Sumatoria de tipos de estilo de las respuestas */
        for (RespuestaEstilo item : this.items) {
            PreguntaEstilosAprendizajeFs pregunta = item.getIdpreguntaEstilos();
            List<TipoEstiloPregunta> listaTiposEstiloPregunta = pregunta.getTipoestiloPreguntaList();
            TipoEstiloPregunta obj = (listaTiposEstiloPregunta.get(0).getIndice().equals(item.getRespuesta()))?listaTiposEstiloPregunta.get(0):listaTiposEstiloPregunta.get(1);
                 
            indice = obj.getTipoEstilo().getIdTipoEstilo()-1;
            columna = indice % 2;
            fila = indice / 2;
            if(contador[columna][fila] == null) {
                contador[columna][fila] = new ContadorTiposEstilos();
            }
            contador[columna][fila].contador++;
            contador[columna][fila].tipoEstilo = obj.getTipoEstilo();
            
        }
        ContadorTiposEstilos[] vectorRes = new ContadorTiposEstilos[4];
        int resta;
        /** Calculo de grupos de tipos de estilo */
        for (int i = 0; i < 4; i++) {
            
            indice = contador[0][i].contador > contador[1][i].contador ? 0 : 1;
            resta = Math.abs(contador[0][i].contador - contador[1][i].contador);
            
            if(vectorRes[i] == null) {
                vectorRes[i] = new ContadorTiposEstilos();
            }
            vectorRes[i].tipoEstilo = contador[indice][i].tipoEstilo;
            vectorRes[i].contador = resta;
            
//            System.out.println("sum: "+ (contador[0][i].contador) + "| tipoEstilo: "+contador[0][i].tipoEstilo.getNombre());
//            System.out.println("sum: "+ (contador[1][i].contador) + "| tipoEstilo: "+contador[1][i].tipoEstilo.getNombre());
            
        }
        return vectorRes;
    }
    
    class ContadorTiposEstilos {
        int contador;
        TipoEstilo tipoEstilo;
        
        public ContadorTiposEstilos() {
            contador = 0;
        }
    }
    
    @Override
    protected RespuestaEstiloFacade getFacade() {
        return ejbFacade;
    }

    public RespuestaEstilo prepareCreate() {
        selected = new RespuestaEstilo();
        initializeEmbeddableKey();
        return selected;
    }

    public List<RespuestaEstilo> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

}
