package com.ingesoft.suideal.encuesta.estilos_aprendizaje.controladores;

import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.PreguntaEstilosAprendizaje;
import com.ingesoft.interpro.controladores.util.PreguntaControllerAbstract;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.facades.PreguntaEstilosAprendizajeFsFacade;
import java.io.IOException;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "preguntaEstilosAprendizajeController")
@SessionScoped
public class PreguntaEstilosAprendizajeController  extends PreguntaControllerAbstract <
        PreguntaEstilosAprendizaje,
        PreguntaEstilosAprendizajeFsFacade
        >  {

    @EJB
    private com.ingesoft.interpro.facades.PreguntaEstilosAprendizajeFsFacade ejbFacade;
    
    public PreguntaEstilosAprendizajeController() {
    }

    public void inicializar(Encuesta selected) {

    }

    @Override
    protected void setEmbeddableKeys() {
        
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    protected PreguntaEstilosAprendizajeFsFacade getFacade() {
        return ejbFacade;
    }

    @Override
    public PreguntaEstilosAprendizaje prepareCreate() {
        setSelected(new PreguntaEstilosAprendizaje());
        initializeEmbeddableKey();
        return getSelected();
    }

    public PreguntaEstilosAprendizaje getPreguntaEstilosAprendizajeFs(Integer id) {
        return getFacade().find(id);
    }

    /**
     * 
     * @param respuestas
     * @return
     * @throws IOException
     * @throws InterruptedException 
     */
    public List<PreguntaEstilosAprendizaje> actualizarTodasRespuestas(List<PreguntaEstilosAprendizaje> respuestas) throws IOException, InterruptedException {
        for (PreguntaEstilosAprendizaje item : respuestas) {
            this.getEjbFacade().edit(item);
        }
        return respuestas;
    }
        
    /************************************************************************
     * GETTERS AND SETTERS METHODS
     ************************************************************************/
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringCreated(){
        return "PreguntaInteligenciasMultiplesCreated";
    }
    
    @Override
    public String getStringUpdated(){
        return "PreguntaInteligenciasMultiplesUpdated";
    }
    
    @Override
    public String getStringDeleted(){
        return "PreguntaInteligenciasMultiplesDeleted";
    }
    
    @Override
    public PreguntaEstilosAprendizajeFsFacade getEjbFacade() {
        return ejbFacade;
    }
}
