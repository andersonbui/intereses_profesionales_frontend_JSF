package com.ingesoft.interpro.controladores.util;

import com.ingesoft.interpro.entidades.TipoEstilo;

/**
 *
 * @author anderson
 */
public class ContadorTiposEstilos {
    int contador;
    TipoEstilo tipoEstilo;

    public ContadorTiposEstilos() {
        contador = 0;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public TipoEstilo getTipoEstilo() {
        return tipoEstilo;
    }
    
    public int aumentarContador() {
        return contador++;
    }
    
    public void setTipoEstilo(TipoEstilo tipoEstilo) {
        this.tipoEstilo = tipoEstilo;
    }

    @Override
    public String toString() {
        return "ContadorTiposEstilos{" + "contador=" + contador + ", tipoEstilo=" + tipoEstilo + '}';
    }
    
}