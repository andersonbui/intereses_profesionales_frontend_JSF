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

    public String getCalculo(){
        int idEncuesta = 1;
        Encuesta encuesta = this.getEncuestaController().getEncuesta(idEncuesta);
        this.items = this.getRespuestaEstiloController().getItemsXEncuesta(encuesta);
        
        ContadorTiposEstilos[] contador = new ContadorTiposEstilos[8]; /** 8 es la cantidad de tipos de estilo */
        
        for (RespuestaEstilo item : this.items) {
            PreguntaEstilosAprendizajeFs pregunta = item.getIdpreguntaEstilos();
            List<TipoEstiloPregunta> listaTiposEstiloPregunta = pregunta.getTipoestiloPreguntaList();
            TipoEstiloPregunta obj = (listaTiposEstiloPregunta.get(0).getIndice().equals(item.getRespuesta()))?listaTiposEstiloPregunta.get(0):listaTiposEstiloPregunta.get(1);
            System.out.println("enunciado: "+ (pregunta.getEnunciado()));
                 
            int indice = obj.getTipoEstilo().getIdTipoEstilo()-1;
            if(contador[indice] == null) {
                contador[indice] = new ContadorTiposEstilos();
            }
            contador[indice].contador++;
            contador[indice].tipoEstilo = obj.getTipoEstilo();
            
        }
        for (ContadorTiposEstilos contadorTiposEstilos : contador) {
            
            System.out.println("sum: "+ (contadorTiposEstilos.contador) + "| tipoEstilo: "+contadorTiposEstilos.tipoEstilo.getNombre());
        }
        return "";
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
