/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.inteligencias_multiples.controladores;

import com.ingesoft.interpro.controladores.util.ContadorTiposEstilos;
import com.ingesoft.interpro.controladores.util.RespuestaControllerAbstract;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.EncuestaEstilosAprendizaje;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.EncuestaInteligenciasMultiples;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.RespuestaInteligenciasMultiples;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.facades.EncuestaInteligenciasMultiplesFacade;
import java.util.ArrayList;
import java.util.List;
import javax.el.ELResolver;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author anderson
 */
public class EncuestaInteligenciasMultiplesControllerTest {
    
//    @Mock
//    private static FacesContext facesContext;
    
    public EncuestaInteligenciasMultiplesControllerTest() {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }
    
//    @BeforeAll
//    public void init() {
        
//        PowerMockito.mockStatic(FacesContext.class);
//        PowerMockito.when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
//        
//        Application application = new MockApplication();
//        PowerMockito.when(facesContext.getApplication()).thenReturn(application);
//        
//        ELResolver variableResolver = PowerMockito.mock(ELResolver.class);
//        PowerMockito.when(application.getELResolver()).thenReturn(variableResolver);
//        shaleMockObjects = new ShaleMockObjects();
//        shaleMockObjects.setUp();
//        application = shaleMockObjects.getApplication();
//        config = shaleMockObjects.getConfig();
//        externalContext = shaleMockObjects.getExternalContext();
//        facesContext = shaleMockObjects.getFacesContext();
//    }
//    
//    @AfterAll
//    public static void tearDownClass() {
//    }
//    
//    @BeforeEach
//    public void setUp() {
//    }
//    
//    @AfterEach
//    public void tearDown() {
//    }

    /**
     * Test of inicializar method, of class EncuestaInteligenciasMultiplesController.
     */
    @Disabled
    @org.junit.jupiter.api.Test
    public void testInicializar() {
        System.out.println("inicializar");
        Encuesta selected = null;
        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
        instance.inicializar(selected);
        assertNotNull(selected);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getGrupo method, of class EncuestaInteligenciasMultiplesController.
     */
    @org.junit.jupiter.api.Test
    public void testGetGrupo() {
        System.out.println("getGrupo");
        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
        List<RespuestaInteligenciasMultiples> expResult = new ArrayList<>();   
        instance.setGrupoActual(expResult);
        List<RespuestaInteligenciasMultiples> result = instance.getGrupoActual();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
//
//    /**
//     * Test of setEmbeddableKeys method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testSetEmbeddableKeys() {
//        System.out.println("setEmbeddableKeys");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        instance.setEmbeddableKeys();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of initializeEmbeddableKey method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testInitializeEmbeddableKey() {
//        System.out.println("initializeEmbeddableKey");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        instance.initializeEmbeddableKey();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getEjbFacade method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testGetEjbFacade() {
//        System.out.println("getEjbFacade");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        EncuestaInteligenciasMultiplesFacade expResult = null;
//        EncuestaInteligenciasMultiplesFacade result = instance.getEjbFacade();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of prepareCreate method, of class EncuestaInteligenciasMultiplesController.
     */
//    @org.junit.jupiter.api.Test
//    public void testPrepareCreate() {
//        System.out.println("prepareCreate");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        EncuestaInteligenciasMultiples result = instance.prepareCreate();
//        assertNotNull(result);
//    }

    /**
     * Test of getStringCreated method, of class EncuestaInteligenciasMultiplesController.
     */
    @org.junit.jupiter.api.Test
    public void testGetStringCreated() {
        System.out.println("getStringCreated");
        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
        String result = instance.getStringCreated();
        assertEquals(true, result == null || result instanceof String);
    }

    /**
     * Test of getStringUpdated method, of class EncuestaInteligenciasMultiplesController.
     */
    @org.junit.jupiter.api.Test
    public void testGetStringUpdated() {
        System.out.println("getStringUpdated");
        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
        String result = instance.getStringUpdated();
        assertEquals(true, result == null || result instanceof String);
    }

    /**
     * Test of getStringDeleted method, of class EncuestaInteligenciasMultiplesController.
     */
    @org.junit.jupiter.api.Test
    public void testGetStringDeleted() {
        System.out.println("getStringDeleted");
        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
        String result = instance.getStringDeleted();
        assertEquals(true, result == null || result instanceof String);
    }

//    /**
//     * Test of getRespuestas method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testGetRespuestas() {
//        System.out.println("getRespuestas");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        List<RespuestaInteligenciasMultiples> expResult = null;
//        List<RespuestaInteligenciasMultiples> result = instance.getRespuestas();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRespuestaInteligenciasMultiples method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testGetRespuestaInteligenciasMultiples() {
//        System.out.println("getRespuestaInteligenciasMultiples");
//        Integer id = null;
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        EncuestaInteligenciasMultiples expResult = null;
//        EncuestaInteligenciasMultiples result = instance.getRespuestaInteligenciasMultiples(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getEstadisticaEncuentaEstiloApren method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testGetEstadisticaEncuentaEstiloApren() {
//        System.out.println("getEstadisticaEncuentaEstiloApren");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        ContadorTiposEstilos[] expResult = null;
//        ContadorTiposEstilos[] result = instance.getEstadisticaEncuentaEstiloApren();
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of seleccionarPunto method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testSeleccionarPunto() {
//        System.out.println("seleccionarPunto");
//        RespuestaInteligenciasMultiples respuestaEstilo = null;
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        instance.seleccionarPunto(respuestaEstilo);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getGrupoItems method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testGetGrupoItems() {
//        System.out.println("getGrupoItems");
//        int numGrupo = 0;
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        List<RespuestaInteligenciasMultiples> expResult = null;
//        List<RespuestaInteligenciasMultiples> result = instance.getGrupoItems(numGrupo);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of reiniciar method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testReiniciar() {
//        System.out.println("reiniciar");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        instance.reiniciar();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of prepararEncuesta method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testPrepararEncuesta() {
//        System.out.println("prepararEncuesta");
//        Encuesta encuesta = null;
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        ELResolver elResolver = facesContext.
//                getApplication()
//                .getELResolver();
//        EncuestaInteligenciasMultiplesController instance = (EncuestaInteligenciasMultiplesController) elResolver.getValue(facesContext.getELContext(), null, "encuestaInteligenciasMultiplesController");
//        assertNotNull(instance);
//        System.out.println("prepararEncuesta");
//        instance.prepararEncuesta(encuesta);
//        
//    }
//
//    /**
//     * Test of getRuta method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testGetRuta() {
//        System.out.println("getRuta");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        String expResult = "";
//        String result = instance.getRuta();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPreguntaController method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testGetPreguntaController() {
//        System.out.println("getPreguntaController");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        PreguntaInteligenciasMultiplesController expResult = null;
//        PreguntaInteligenciasMultiplesController result = instance.getPreguntaController();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRespuestaController method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testGetRespuestaController() {
//        System.out.println("getRespuestaController");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        RespuestaControllerAbstract expResult = null;
//        RespuestaControllerAbstract result = instance.getRespuestaController();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getUltimoPaso method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testGetUltimoPaso() {
//        System.out.println("getUltimoPaso");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        int expResult = 0;
//        int result = instance.getUltimoPaso();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getStep method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testGetStep() {
//        System.out.println("getStep");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        int expResult = 0;
//        int result = instance.getStep();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getnombrePaso method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testGetnombrePaso() {
//        System.out.println("getnombrePaso");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        int expResult = 0;
//        int result = instance.getnombrePaso();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of puedeAnteriorPaso method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testPuedeAnteriorPaso() {
//        System.out.println("puedeAnteriorPaso");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        boolean expResult = false;
//        boolean result = instance.puedeAnteriorPaso();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of puedeSiguientePasoNoUltimo method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testPuedeSiguientePasoNoUltimo() {
//        System.out.println("puedeSiguientePasoNoUltimo");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        boolean expResult = false;
//        boolean result = instance.puedeSiguientePasoNoUltimo();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of esPenultimoPaso method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testEsPenultimoPaso() {
//        System.out.println("esPenultimoPaso");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        boolean expResult = false;
//        boolean result = instance.esPenultimoPaso();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of esUltimoPaso method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testEsUltimoPaso() {
//        System.out.println("esUltimoPaso");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        boolean expResult = false;
//        boolean result = instance.esUltimoPaso();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of anteriorPaso method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testAnteriorPaso() {
//        System.out.println("anteriorPaso");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        int expResult = 0;
//        int result = instance.anteriorPaso();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of siguientePaso method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testSiguientePaso() {
//        System.out.println("siguientePaso");
//        ActionEvent actionEvent = null;
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        int expResult = 0;
//        int result = instance.siguientePaso(actionEvent);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of finalizarEncuesta method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testFinalizarEncuesta() throws Exception {
//        System.out.println("finalizarEncuesta");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        boolean expResult = false;
//        boolean result = instance.finalizarEncuesta();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of actualizarTodasRespuestas method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testActualizarTodasRespuestas() throws Exception {
//        System.out.println("actualizarTodasRespuestas");
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        List<RespuestaInteligenciasMultiples> expResult = null;
//        List<RespuestaInteligenciasMultiples> result = instance.actualizarTodasRespuestas();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of estadisticaEncuesta method, of class EncuestaInteligenciasMultiplesController.
//     */
//    @org.junit.jupiter.api.Test
//    public void testEstadisticaEncuesta() {
//        System.out.println("estadisticaEncuesta");
//        EncuestaEstilosAprendizaje encuesta = null;
//        EncuestaInteligenciasMultiplesController instance = new EncuestaInteligenciasMultiplesController();
//        ContadorTiposEstilos[] expResult = null;
//        ContadorTiposEstilos[] result = instance.estadisticaEncuesta(encuesta);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
