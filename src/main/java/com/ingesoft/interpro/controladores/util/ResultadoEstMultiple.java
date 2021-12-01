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
import com.ingesoft.suideal.encuesta.chaside.entidades.ResultadoChaside;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaPorEstilo;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.RespuestaPorInteligencia;
import java.util.List;

/**
 *
 * @author debian
 */
public class ResultadoEstMultiple {

    final static public String METODO_INDIV = "INDIVIDUAL";
    final static public String METODO_PROMEDIO = "PROMEDIO";
    
    String grafico;
    String personalidad;
    Estudiante estudiante;
    List<DatosRiasec> listaDatRiasec;
    double promedioPuntajeEncuesta;
    double promedioPuntajeEValuacion;
    List<RespuestaPorEstilo> respuestaPorEstilo;
    List<RespuestaPorPersonalidad> respuestaPorPersonalidad;
    List<RespuestaPorInteligencia> respuestaPorInteligencia;
    ResultadoChaside[] resultadoChaside;
    List<Encuesta> listaEncuestas;
    String metodo;

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
        if(listaEncuestas != null && listaEncuestas.size() > 0){
           return listaEncuestas.get(0);
        }
        return null;
    }
    
    public List<Encuesta> getListaEncuestas() {
        return listaEncuestas;
    }

    public void setListaEncuestas(List<Encuesta> listaEncuestas) {
        this.listaEncuestas = listaEncuestas;
    }

    public List<RespuestaPorPersonalidad> getRespuestaPorPersonalidad() {
        return respuestaPorPersonalidad;
    }

    public void setRespuestaPorPersonalidad(List<RespuestaPorPersonalidad> respuestaPorPersonalidad) {
        this.respuestaPorPersonalidad = respuestaPorPersonalidad;
    }

    public List<RespuestaPorInteligencia> getRespuestaPorInteligencia() {
        return respuestaPorInteligencia;
    }

    public void setRespuestaPorInteligencia(List<RespuestaPorInteligencia> respuestaPorInteligencia) {
        this.respuestaPorInteligencia = respuestaPorInteligencia;
    }

    public ResultadoChaside[] getResultadoChaside() {
        return resultadoChaside;
    }

    public void setResultadoChaside(ResultadoChaside[] resultadoChaside) {
        this.resultadoChaside = resultadoChaside;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

}
