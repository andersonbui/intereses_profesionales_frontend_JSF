package com.ingesoft.interpro.controladores.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author sperez
 */
@javax.faces.bean.ManagedBean(name = "vistasEstudiante")
@SessionScoped
public class Vistas implements Serializable {

    private static String ruta;
    private static String rutaGeneral = "/intereses_profesionales_frontend_JSF/faces";

    public static String getRuta() {
        return ruta;
    }

    public static String verPaginaPrincipal() {
        ruta = rutaGeneral + "/vistas/pregunta/List.xhtml";
        return ruta;
    }

    public static String verPreguntas() {
        ruta = rutaGeneral + "/vistas/pregunta/List.xhtml";
        return ruta;
    }

    public static String verDatosPersonales() {
        ruta = rutaGeneral + "/usuariosdelsistema/estudiante/VerEstudiante_Est.xhtml";
        return ruta;
    }

    public static String verPublicacion() {
        ruta = rutaGeneral + "/usuariosdelsistema/estudiante/VerPublicacion.xhtml";
        return ruta;
    }

    public static String registrarPublicacion() {
        ruta = rutaGeneral + "/usuariosdelsistema/estudiante/RegistrarPublicacion.xhtml";
        return ruta;
    }

    public static String verPublicaciones() {
        ruta = rutaGeneral + "/usuariosdelsistema/estudiante/ListarPublicaciones.xhtml";
        return ruta;
    }

}
