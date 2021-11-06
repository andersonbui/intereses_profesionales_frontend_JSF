package com.ingesoft.suideal.encuesta.chaside.controladores;

import com.ingesoft.interpro.controladores.util.PreguntaControllerAbstract;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.suideal.encuesta.chaside.entidades.PreguntaChaside;
import com.ingesoft.suideal.encuesta.chaside.facade.PreguntaChasideFacade;
import java.io.IOException;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "preguntaChasideController")
@SessionScoped
public class PreguntaChasideController  extends PreguntaControllerAbstract <
        PreguntaChaside,
        PreguntaChasideFacade
        >  {

    @EJB
    private PreguntaChasideFacade ejbFacade;
    
    /************************************************************************
     * CONSTRUCTORS
     ************************************************************************/
    
    public PreguntaChasideController() {
    }

    public void inicializar(Encuesta selected) {

    }

    @Override
    protected void setEmbeddableKeys() {
        
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    protected PreguntaChasideFacade getFacade() {
        return ejbFacade;
    }

    /************************************************************************
     * Funciones personalizadas
     ************************************************************************/
    
    @Override
    public PreguntaChaside prepareCreate() {
        setSelected(new PreguntaChaside());
        initializeEmbeddableKey();
        return getSelected();
    }

    public PreguntaChaside getPreguntaChaside(Integer id) {
        return getFacade().find(id);
    }

    /**
     * 
     * @param respuestas
     * @return
     * @throws IOException
     * @throws InterruptedException 
     */
    public List<PreguntaChaside> actualizarTodasRespuestas(List<PreguntaChaside> respuestas) throws IOException, InterruptedException {
        for (PreguntaChaside item : respuestas) {
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
        return "PreguntaChasideCreated";
    }
    
    @Override
    public String getStringUpdated(){
        return "PreguntaChasideUpdated";
    }
    
    @Override
    public String getStringDeleted(){
        return "PreguntaChasideDeleted";
    }
    
    @Override
    public PreguntaChasideFacade getEjbFacade() {
        return ejbFacade;
    }
}
