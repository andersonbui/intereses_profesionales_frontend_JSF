package com.ingesoft.suideal.encuesta.chaside.controladores;

import com.ingesoft.interpro.controladores.util.Contador;
import com.ingesoft.interpro.controladores.util.EncuestaControllerAbstract;
import com.ingesoft.interpro.controladores.util.RespuestaControllerAbstract;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.suideal.encuesta.chaside.entidades.EncuestaChaside;
import com.ingesoft.suideal.encuesta.chaside.entidades.PreguntaChaside;
import com.ingesoft.suideal.encuesta.chaside.entidades.RespuestaChaside;
import com.ingesoft.suideal.encuesta.chaside.entidades.ResultadoChaside;
import com.ingesoft.suideal.encuesta.chaside.entidades.TipoChaside;
import com.ingesoft.suideal.encuesta.chaside.facade.EncuestaChasideFacade;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "estiloController")
@SessionScoped
public class ChasideController 
        extends EncuestaControllerAbstract<
            EncuestaChaside, 
            EncuestaChasideFacade,
            RespuestaChaside, 
            PreguntaChaside> {

    @EJB
    private com.ingesoft.suideal.encuesta.chaside.facade.EncuestaChasideFacade ejbFacade;
    
    private List<Contador<TipoChaside>> estadisticaEncuentaChasideApren;
      
    public ChasideController() {
        super();
    }

    public void inicializar(Encuesta selected) {

    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public EncuestaChaside getEncuestaChaside(Integer id) {
        return (EncuestaChaside) getFacade().find(id);
    }

    /**
     * 
     * @param pregunta
     * @return 
     */
    @Override
    public RespuestaChaside crearRespuestaEncuesta(PreguntaChaside pregunta) {
        RespuestaChasideController respuestaContr = getRespuestaChasideController();
        RespuestaChaside respuestaEstApre = respuestaContr.prepareCreate();
        respuestaEstApre.inicializar(getSelected(), pregunta);
        return respuestaEstApre;
    }

    /**
     * 
     * @param unaencuesta
     * @return 
     */
    @Override
    public EncuestaChaside obtenerEncuestaEspecifica(Encuesta unaencuesta) {
        EncuestaChaside unaencuestaEspe;
        if(unaencuesta.getEncuestaChaside() == null){
            prepareCreate();
            unaencuestaEspe = getSelected();
        } else {
            unaencuestaEspe = unaencuesta.getEncuestaChaside();
            setSelected(unaencuestaEspe);
        }
        unaencuesta.setEncuestaChaside(unaencuestaEspe);
        getEncuestaController().update();
        
        return getSelected();
    }

    /**
     * 
     * @param actualRespuesta
     * @return 
     */
    @Override
    public RespuestaChaside respuestaAleatoria(RespuestaChaside actualRespuesta) {
        short valuerand = (short)(((new Random()).nextBoolean())? 1: 0);
        actualRespuesta.setRespuesta(valuerand);
        return actualRespuesta;
    }

    @Override
    public EncuestaChaside prepareCreate() {
        
        Encuesta unaencuesta = getEncuestaGeneral();
        EncuestaChaside unaencuestaEspe = new EncuestaChaside(unaencuesta);
        unaencuestaEspe.setFechaCreacion(new Date());
        setSelected(unaencuestaEspe);
        this.update();
        return getSelected();
    }

    /**
     * 
     */
    @Override
    public void reiniciar() {
        setPasoActual(0);
        setGrupoActual(getGrupoItems(getPasoActual() + 1));
    }

    /**
     * 
     * @param encuesta 
     */
    @Override
    public void prepararEncuesta(Encuesta encuesta) {
        this.reiniciar();
        setTamGrupo(5);
        getRespuestas();
    }

    /**
     * 
     * @return
     * @throws InterruptedException 
     */
    @Override
    public boolean finalizarEncuesta() throws InterruptedException {
        getEncuestaController().detenerReloj();
        
        Thread hilo = guardarRespuestas(getGrupoActual());
        if(hilo != null){
            hilo.join();
        }
        // colocar como finalizada y guarda cambios
        getSelected().setEstado(EncuestaChaside.FINALIZADA);
        update();
        
        estadisticaEncuentaChasideApren = estadisticaEncuesta(getEncuestaGeneral().getEncuestaChaside());
        
        guardarEstadisticasChaside(estadisticaEncuentaChasideApren);
        
        setPasoActual(getPasoActual() + 1);
        return true;
    }
    
    private void guardarEstadisticasChaside(List<Contador<TipoChaside>> estadisticaEncuentaChasideApren) {
        
        ResultadoChasideController rpec = getResultadoChasideController();
        
//        for (Contador<TipoChaside> contador : estadisticaEncuentaChasideApren) {
//            ResultadoChaside respuestaEst = rpec.prepareCreate();
//            respuestaEst.setTipoChaside(contador.getTipo());
//            respuestaEst.setEncuestaChaside(this.getSelected());
//            respuestaEst.setRespuesta(contador.getContador().shortValue());
//            System.out.println("respuestaEstrespuestaEst:"+respuestaEst);
//            rpec.update();
//        }
    }

    /**
     * Calcula Estadistica de tipo estilo de una encuesta
     * @param encuesta
     * @return 
     */
    public List<Contador<TipoChaside>> estadisticaEncuesta(EncuestaChaside encuesta){
//        List<RespuestaChaside> itemsRespuestas = getRespuestaChasideController().getItemsXEncuesta(encuesta);
//        setItemsRespuestas(itemsRespuestas);
//        
//        if(itemsRespuestas == null ){
//            return null;
//        }
//        int cantDatos = 4;
//        Contador<TipoChaside>[][] contador = new Contador[2][cantDatos]; /** 8 es la cantidad de tipos de estilo */
//        contador[0] = new Contador[cantDatos];
//        contador[1] = new Contador[cantDatos];
//        int indice;
//        int columna;
//        int fila;
//        
//        /** Sumatoria de tipos de estilo de las respuestas */
//        for (RespuestaChaside item : itemsRespuestas) {
//            PreguntaChaside pregunta = item.getIdpreguntaChaside();
//            List<TipoChasidePregunta> listaTiposChasidePregunta = pregunta.getTipoChasidePreguntaList();
//            TipoChasidePregunta obj = (listaTiposChasidePregunta.get(0).getIndice().equals(item.getRespuesta()))?listaTiposChasidePregunta.get(0):listaTiposChasidePregunta.get(1);
//                 
//            indice = obj.getTipoChaside().getId()-1;
//            columna = indice % 2;
//            fila = indice / 2;
//            if(contador[columna][fila] == null) {
//                contador[columna][fila] = new Contador();
//            }
//            contador[columna][fila].aumentarContador();
//            contador[columna][fila].setTipo(obj.getTipoChaside());
//            
//        }
        List<Contador<TipoChaside>> vectorRes = new ArrayList<>();
//        
//        for (int i = 0; i < cantDatos; i++) {
//            vectorRes.add(new Contador());
//        }
//        int resta;
//        /** Calculo de grupos de tipos de estilo */
//        for (int i = 0; i < vectorRes.size(); i++) {
//            
//            indice = contador[0][i].getContador() > contador[1][i].getContador() ? 0 : 1;
//            resta = Math.abs(contador[0][i].getContador() - contador[1][i].getContador());
//            
//            vectorRes.get(i).setTipo(contador[indice][i].getTipo());
//            vectorRes.get(i).setContador(resta);
//            
//        }
        return vectorRes;
    }

    /************************************************************************
     * GETTERS AND SETTERS METHODS
     ************************************************************************/
    
    /**
     * 
     * @return 
     */
    @Override
    public EncuestaChasideFacade getEjbFacade() {
        return ejbFacade;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getStringCreated(){
        return "EncuestaChasideCreated";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringUpdated(){
        return "EncuestaChasideUpdated";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringDeleted(){
        return "EncuestaChasideDeleted";
    }
        /**
     * 
     * @return 
     */
   @Override
    public String getRuta() {
        return "/vistas/preguntasChaside/index.xhtml";
    }
    
    @Override
    public String getName() {
        return "Chaside aprendizaje";
    }

    @Override
    public PreguntaChasideController getPreguntaController() {
        return getPreguntaChasideController();
    }
    
    @Override
    public RespuestaControllerAbstract getRespuestaController() {
        return  getRespuestaChasideController();
    }

    /**
     * 
     * @return 
     */
    public List<Contador<TipoChaside>> getEstadisticaEncuentaChasideApren() {
        return estadisticaEncuentaChasideApren;
    }

//    
    /************************************************************************
     * CONVERTER
     ************************************************************************/
    
    @FacesConverter(forClass = EncuestaChaside.class)
    public static class EncuestaChasideConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ChasideController controller = (ChasideController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "estiloController");
            return controller.getEncuestaChaside(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
             if (object == null) {
                return null;
            }
            if (object instanceof EncuestaChaside) {
                EncuestaChaside o = (EncuestaChaside) object;
                return getStringKey(o.getIdEncuesta());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EncuestaChaside.class.getName()});
                return null;
            }
        }

    }
    
}
