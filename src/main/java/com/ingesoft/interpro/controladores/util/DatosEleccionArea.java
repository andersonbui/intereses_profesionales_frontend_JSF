/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores.util;

import be.ceau.chart.color.Color;
import com.ingesoft.interpro.entidades.Area;
import com.ingesoft.interpro.entidades.TipoAmbiente;

/**
 *
 * @author debian
 */
public class DatosEleccionArea implements Comparable<DatosEleccionArea> {

    Color color;
    String label;
    int tipo;
    Double valor;
    Area area;

    public DatosEleccionArea() {
        label = "";
        valor = 0.0;
    }

    @Override
    public String toString() {
        return "Datos{" + "color=" + color + ", label=" + label + ", tipo=" + tipo + ", valor=" + valor + '}';
    }

    @Override
    public int compareTo(DatosEleccionArea o) {
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
