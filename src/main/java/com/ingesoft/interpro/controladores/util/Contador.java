package com.ingesoft.interpro.controladores.util;

/**
 *
 * @author anderson
 * @param <Tipo>
 */
public class Contador<Tipo> implements Comparable<Contador> {
    Integer contador;
    Tipo tipo;

    public Contador() {
        contador = 0;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public Tipo getTipo() {
        return tipo;
    }
    
    public int aumentarContador() {
        return contador++;
    }
    
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Contador{" + "contador=" + contador + ", tipoEstilo=" + tipo + '}';
    }

    @Override
    public int compareTo(Contador o) {
        return - getContador().compareTo(o.getContador());
    }
    
}