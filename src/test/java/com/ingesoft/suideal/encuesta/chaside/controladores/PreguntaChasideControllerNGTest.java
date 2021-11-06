/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.chaside.controladores;

import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.PreguntaEstilosAprendizaje;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.facade.PreguntaEstilosAprendizajeFsFacade;
import java.util.List;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.testng.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author debian
 */
public class PreguntaChasideControllerNGTest {
    
    public PreguntaChasideControllerNGTest() {
    }

    @org.testng.annotations.BeforeClass
    public static void setUpClass() throws Exception {
    }

    @org.testng.annotations.AfterClass
    public static void tearDownClass() throws Exception {
    }

    @org.testng.annotations.BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @org.testng.annotations.AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of inicializar method, of class PreguntaChasideController.
     */
    @org.testng.annotations.Test
    public void testInicializar() {
        System.out.println("inicializar");
        Encuesta selected = null;
        PreguntaChasideController instance = new PreguntaChasideController();
        instance.inicializar(selected);
        
//        FacesContext context = ContextMocker.mockFacesContext();
//        Map<String, Object> session = new HashMap<String, Object>();
//        ExternalContext ext = mock(ExternalContext.class);
//        when(ext.getSessionMap()).thenReturn(session);
//        when(context.getExternalContext()).thenReturn(ext);
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        
//        PreguntaChasideController bean = (PreguntaChasideController) facesContext.getApplication().getELResolver().
//                getValue(facesContext.getELContext(), null, "preguntaChasideController");
//        
//        System.out.println("pregunta 1:"+bean.getPreguntaChaside(1));
//            bean.incrementFoo();
        System.out.println("Error en preginta=========================>");
    }

    /**
     * Test of setEmbeddableKeys method, of class PreguntaChasideController.
     */
    @org.testng.annotations.Test
    public void testSetEmbeddableKeys() {
        System.out.println("setEmbeddableKeys");
        PreguntaChasideController instance = new PreguntaChasideController();
//        instance.setEmbeddableKeys();
    }

    /**
     * Test of initializeEmbeddableKey method, of class PreguntaChasideController.
     */
    @org.testng.annotations.Test
    public void testInitializeEmbeddableKey() {
        System.out.println("initializeEmbeddableKey");
        PreguntaChasideController instance = new PreguntaChasideController();
//        instance.initializeEmbeddableKey();
    }

    /**
     * Test of getFacade method, of class PreguntaChasideController.
     */
    @org.testng.annotations.Test
    public void testGetFacade() {
        System.out.println("getFacade");
//        PreguntaChasideController myObjectMock = PowerMockito.mock(PreguntaChasideController.class);
        System.out.println("myObjectMock");
//        PreguntaChasideController instance = new PreguntaChasideController();
//        PreguntaEstilosAprendizajeFsFacade expResult = null;
//        PreguntaEstilosAprendizajeFsFacade result = myObjectMock.getFacade();
//        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of prepareCreate method, of class PreguntaChasideController.
     */
    @org.testng.annotations.Test
    public void testPrepareCreate() {
        System.out.println("prepareCreate");
        PreguntaChasideController instance = new PreguntaChasideController();
        PreguntaEstilosAprendizaje expResult = null;
//        PreguntaEstilosAprendizaje result = instance.prepareCreate();
//        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getPreguntaEstilosAprendizajeFs method, of class PreguntaChasideController.
     */
    @org.testng.annotations.Test
    public void testGetPreguntaEstilosAprendizajeFs() {
        System.out.println("getPreguntaEstilosAprendizajeFs");
        Integer id = null;
        PreguntaChasideController instance = new PreguntaChasideController();
        PreguntaEstilosAprendizaje expResult = null;
//        PreguntaEstilosAprendizaje result = instance.getPreguntaEstilosAprendizajeFs(id);
//        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of actualizarTodasRespuestas method, of class PreguntaChasideController.
     */
    @org.testng.annotations.Test
    public void testActualizarTodasRespuestas() throws Exception {
        System.out.println("actualizarTodasRespuestas");
        List<PreguntaEstilosAprendizaje> respuestas = null;
        PreguntaChasideController instance = new PreguntaChasideController();
        List expResult = null;
//        List result = instance.actualizarTodasRespuestas(respuestas);
//        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getStringCreated method, of class PreguntaChasideController.
     */
    @org.testng.annotations.Test
    public void testGetStringCreated() {
        System.out.println("getStringCreated");
        PreguntaChasideController instance = new PreguntaChasideController();
        String expResult = "";
        String result = instance.getStringCreated();
//        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getStringUpdated method, of class PreguntaChasideController.
     */
    @org.testng.annotations.Test
    public void testGetStringUpdated() {
        System.out.println("getStringUpdated");
        PreguntaChasideController instance = new PreguntaChasideController();
        String expResult = "";
        String result = instance.getStringUpdated();
//        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getStringDeleted method, of class PreguntaChasideController.
     */
    @org.testng.annotations.Test
    public void testGetStringDeleted() {
        System.out.println("getStringDeleted");
        PreguntaChasideController instance = new PreguntaChasideController();
        String expResult = "";
        String result = instance.getStringDeleted();
//        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getEjbFacade method, of class PreguntaChasideController.
     */
    @org.testng.annotations.Test
    public void testGetEjbFacade() {
        System.out.println("getEjbFacade");
        PreguntaChasideController instance = new PreguntaChasideController();
        PreguntaEstilosAprendizajeFsFacade expResult = null;
//        PreguntaEstilosAprendizajeFsFacade result = instance.getEjbFacade();
//        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
