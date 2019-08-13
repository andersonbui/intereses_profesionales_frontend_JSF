/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores.util;

import be.ceau.chart.color.Color;
import com.ingesoft.interpro.entidades.TipoAmbiente;

/**
 *
 * @author debian
 */
public class DatosAmbiente implements Comparable<DatosAmbiente> {

    Color color;
    String label;
    int tipo;
    Double valor;
    TipoAmbiente tipoAmbiente;

    public DatosAmbiente() {
        label = "";
        valor = 0.0;
    }

    @Override
    public String toString() {
        return "Datos{" + "color=" + color + ", label=" + label + ", tipo=" + tipo + ", valor=" + valor + '}';
    }

    @Override
    public int compareTo(DatosAmbiente o) {
        return -valor.compareTo(o.valor);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public TipoAmbiente getTipoAmbiente() {
        return tipoAmbiente;
    }

    public void setTipoAmbiente(TipoAmbiente tipoAmbiente) {
        this.tipoAmbiente = tipoAmbiente;
    }
    
    
}
