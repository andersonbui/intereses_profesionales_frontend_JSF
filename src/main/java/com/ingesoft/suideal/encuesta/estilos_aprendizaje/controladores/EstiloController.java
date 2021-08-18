package com.ingesoft.suideal.encuesta.estilos_aprendizaje.controladores;

import com.ingesoft.interpro.controladores.util.Contador;
import com.ingesoft.interpro.controladores.util.EncuestaControllerAbstract;
import com.ingesoft.interpro.controladores.util.RespuestaControllerAbstract;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.EncuestaEstilosAprendizajeFacade;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.EncuestaEstilosAprendizaje;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.PreguntaEstilosAprendizaje;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaEstilo;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaPorEstilo;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.TipoEstilo;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.TipoEstiloPregunta;
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
public class EstiloController 
        extends EncuestaControllerAbstract<
            EncuestaEstilosAprendizaje, 
            EncuestaEstilosAprendizajeFacade,
            RespuestaEstilo, 
            PreguntaEstilosAprendizaje> {

    @EJB
    private com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.EncuestaEstilosAprendizajeFacade ejbFacade;
    
    private List<Contador<TipoEstilo>> estadisticaEncuentaEstiloApren;
      
    public EstiloController() {
        super();
    }

    public void inicializar(Encuesta selected) {

    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public EncuestaEstilosAprendizaje getEncuestaEstilosAprendizaje(Integer id) {
        return (EncuestaEstilosAprendizaje) getFacade().find(id);
    }

    /**
     * 
     * @param pregunta
     * @return 
     */
    @Override
    public RespuestaEstilo crearRespuestaEncuesta(PreguntaEstilosAprendizaje pregunta) {
        RespuestaEstilosController respuestaContr = getRespuestaEstilosControllerController();
        RespuestaEstilo respuestaEstApre = respuestaContr.prepareCreate();
        respuestaEstApre.inicializar(getSelected(), pregunta);
        return respuestaEstApre;
    }

    /**
     * 
     * @param unaencuesta
     * @return 
     */
    @Override
    public EncuestaEstilosAprendizaje obtenerEncuestaEspecifica(Encuesta unaencuesta) {
        EncuestaEstilosAprendizaje unaencuestaEspe;
        if(unaencuesta.getEncuestaEstilosAprendizaje() == null){
            prepareCreate();
            unaencuestaEspe = getSelected();
        } else {
            unaencuestaEspe = unaencuesta.getEncuestaEstilosAprendizaje();
            setSelected(unaencuestaEspe);
        }
        unaencuesta.setEncuestaEstilosAprendizaje(unaencuestaEspe);
        getEncuestaController().update();
        
        return getSelected();
    }

    /**
     * 
     * @param actualRespuesta
     * @return 
     */
    @Override
    public RespuestaEstilo respuestaAleatoria(RespuestaEstilo actualRespuesta) {
        char valuerand = ((new Random()).nextBoolean())? 'A': 'B';
        actualRespuesta.setRespuesta(valuerand);
        return actualRespuesta;
    }

    @Override
    public EncuestaEstilosAprendizaje prepareCreate() {
        
        Encuesta unaencuesta = getEncuestaGeneral();
        EncuestaEstilosAprendizaje unaencuestaEspe = new EncuestaEstilosAprendizaje(unaencuesta);
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
        setTamGrupo(5);
        this.setListaPreguntas(getPreguntas());
        this.definirNumeroGrupos();
        this.reiniciar();
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
        hilo.join();
        // colocar como finalizada y guarda cambios
        getSelected().setEstado(EncuestaEstilosAprendizaje.FINALIZADA);
        update();
        
        estadisticaEncuentaEstiloApren = estadisticaEncuesta(getEncuestaGeneral().getEncuestaEstilosAprendizaje());
        
        guardarEstadisticasEstilo(estadisticaEncuentaEstiloApren);
        
        setPasoActual(getPasoActual() + 1);
        getEncuestaController().finalizarEncuesta();
        return true;
    }
    
    private void guardarEstadisticasEstilo(List<Contador<TipoEstilo>> estadisticaEncuentaEstiloApren) {
        
        RespuestaPorEstilosController rpec = getRespuestaPorEstilosController();
        
        for (Contador<TipoEstilo> contador : estadisticaEncuentaEstiloApren) {
            RespuestaPorEstilo respuestaEst = rpec.prepareCreate();
            respuestaEst.setTipoEstilo(contador.getTipo());
            respuestaEst.setEncuestaEstilosAprendizaje(this.getSelected());
            respuestaEst.setRespuesta(contador.getContador().shortValue());
            System.out.println("respuestaEstrespuestaEst:"+respuestaEst);
            rpec.update();
        }
    }

    /**
     * Calcula Estadistica de tipo estilo de una encuesta
     * @param encuesta
     * @return 
     */
    public List<Contador<TipoEstilo>> estadisticaEncuesta(EncuestaEstilosAprendizaje encuesta){
        List<RespuestaEstilo> itemsRespuestas = getRespuestaEstilosControllerController().getItemsXEncuesta(encuesta);
        setItemsRespuestas(itemsRespuestas);
        
        if(itemsRespuestas == null ){
            return null;
        }
        int cantDatos = 4;
        Contador<TipoEstilo>[][] contador = new Contador[2][cantDatos]; /** 8 es la cantidad de tipos de estilo */
        contador[0] = new Contador[cantDatos];
        contador[1] = new Contador[cantDatos];
        int indice;
        int columna;
        int fila;
        
        /** Sumatoria de tipos de estilo de las respuestas */
        for (RespuestaEstilo item : itemsRespuestas) {
            PreguntaEstilosAprendizaje pregunta = item.getIdpreguntaEstilos();
            List<TipoEstiloPregunta> listaTiposEstiloPregunta = pregunta.getTipoEstiloPreguntaList();
            TipoEstiloPregunta obj = (listaTiposEstiloPregunta.get(0).getIndice().equals(item.getRespuesta()))?listaTiposEstiloPregunta.get(0):listaTiposEstiloPregunta.get(1);
                 
            indice = obj.getTipoEstilo().getId()-1;
            columna = indice % 2;
            fila = indice / 2;
            if(contador[columna][fila] == null) {
                contador[columna][fila] = new Contador();
            }
            contador[columna][fila].aumentarContador();
            contador[columna][fila].setTipo(obj.getTipoEstilo());
            
        }
        List<Contador<TipoEstilo>> vectorRes = new ArrayList<>();
        
        for (int i = 0; i < cantDatos; i++) {
            vectorRes.add(new Contador());
        }
        int resta;
        /** Calculo de grupos de tipos de estilo */
        for (int i = 0; i < vectorRes.size(); i++) {
            
            indice = contador[0][i].getContador() > contador[1][i].getContador() ? 0 : 1;
            resta = Math.abs(contador[0][i].getContador() - contador[1][i].getContador());
            
            vectorRes.get(i).setTipo(contador[indice][i].getTipo());
            vectorRes.get(i).setContador(resta);
            
        }
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
    public EncuestaEstilosAprendizajeFacade getEjbFacade() {
        return ejbFacade;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getStringCreated(){
        return "EncuestaEstiloCreated";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringUpdated(){
        return "EncuestaEstiloUpdated";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getStringDeleted(){
        return "EncuestaEstiloDeleted";
    }
        /**
     * 
     * @return 
     */
   @Override
    public String getRuta() {
        return "/vistas/preguntasEstilosAprendizaje/index.xhtml";
    }
    
    @Override
    public String getName() {
        return "Estilo aprendizaje";
    }

    @Override
    public PreguntaEstilosAprendizajeController getPreguntaController() {
        return getPreguntaEstilosAprendizajeController();
    }
    
    @Override
    public RespuestaControllerAbstract getRespuestaController() {
        return  getRespuestaEstilosControllerController();
    }

    /**
     * 
     * @return 
     */
    public List<Contador<TipoEstilo>> getEstadisticaEncuentaEstiloApren() {
        return estadisticaEncuentaEstiloApren;
    }

//    
    /************************************************************************
     * CONVERTER
     ************************************************************************/
    
    @FacesConverter(forClass = EncuestaEstilosAprendizaje.class)
    public static class EncuestaEstilosAprendizajeConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EstiloController controller = (EstiloController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "estiloController");
            return controller.getEncuestaEstilosAprendizaje(getKey(value));
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
            if (object instanceof EncuestaEstilosAprendizaje) {
                EncuestaEstilosAprendizaje o = (EncuestaEstilosAprendizaje) object;
                return getStringKey(o.getIdEncuesta());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EncuestaEstilosAprendizaje.class.getName()});
                return null;
            }
        }

    }
    
}
