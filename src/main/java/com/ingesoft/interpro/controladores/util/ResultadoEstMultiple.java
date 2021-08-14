/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores.util;

import com.ingesoft.interpro.entidades.DatosRiasec;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.entidades.RespuestaPorPersonalidad;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaPorEstilo;
import java.util.List;

/**
 *
 * @author debian
 */
public class ResultadoEstMultiple {

    String grafico;
    String personalidad;
    Estudiante estudiante;
    List<DatosRiasec> listaDatRiasec;
    double promedioPuntajeEncuesta;
    double promedioPuntajeEValuacion;
    List<RespuestaPorEstilo> respuestaPorEstilo;
    List<RespuestaPorPersonalidad> respuestaPorPersonalidad;
    Encuesta encuesta;

    public String getGrafico() {
        return grafico;
    }
    
    public String getGraficoAmbiente() {
        return grafico;
    }

    public void setGrafico(String grafico) {
        this.grafico = grafico;
    }

    public String getPersonalidad() {
        return personalidad;
    }

    public void setPersonalidad(String personalidad) {
        this.personalidad = personalidad;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public List<DatosRiasec> getListaDatRiasec() {
        return listaDatRiasec;
    }

    public void setListaDatRiasec(List<DatosRiasec> listaDatRiasec) {
        this.listaDatRiasec = listaDatRiasec;
    }

    public double getPromedioPuntajeEncuesta() {
        return promedioPuntajeEncuesta;
    }

    public void setPromedioPuntajeEncuesta(double promedioPuntajeEncuesta) {
        this.promedioPuntajeEncuesta = promedioPuntajeEncuesta;
    }

    public double getPromedioPuntajeEValuacion() {
        return promedioPuntajeEValuacion;
    }

    public void setPromedioPuntajeEValuacion(double promedioPuntajeEValuacion) {
        this.promedioPuntajeEValuacion = promedioPuntajeEValuacion;
    }

    public List<RespuestaPorEstilo> getRespuestaPorEstilo() {
        return respuestaPorEstilo;
    }

    public void setRespuestaPorEstilo(List<RespuestaPorEstilo> respuestaPorEstilo) {
        this.respuestaPorEstilo = respuestaPorEstilo;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }

    public List<RespuestaPorPersonalidad> getRespuestaPorPersonalidad() {
        return respuestaPorPersonalidad;
    }

    public void setRespuestaPorPersonalidad(List<RespuestaPorPersonalidad> respuestaPorPersonalidad) {
        this.respuestaPorPersonalidad = respuestaPorPersonalidad;
    }
    
}
