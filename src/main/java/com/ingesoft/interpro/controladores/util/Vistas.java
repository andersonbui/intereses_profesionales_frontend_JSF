package com.ingesoft.interpro.controladores.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

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
        ruta = rutaGeneral + "/vistas/preguntaAmbiente/List.xhtml";
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

    public static String verMiPerfil() {
        ruta = rutaGeneral + "/vistas/miperfil/VerPerfil.xhtml";
        return ruta;
    }

    public static String completarPerfil() {
        ruta = rutaGeneral + "/vistas/miperfil/completarPerfil.xhtml";
        return ruta;
    }

    public static String login() {
        ruta = rutaGeneral + "/login.xhtml";
        return ruta;
    }

    public static String inicio() {
        ruta = rutaGeneral + "/vistas/inicio.xhtml";
        return ruta;
    }

    public static String misResultados() {
        return "/vistas/miperfil/misResultados.xhtml";
    }

    public static String urlRegistroFacebook() {
        return getProtocolo() + "://" + Vistas.getIP() + rutaGeneral + "/registro.xhtml";
    }

    public static String completarRegistroFacebook() {
        return getProtocolo() + "://" + Vistas.getIP() + rutaGeneral + "/completarRegistroFacebook.xhtml";
    }

    public static String loginCompleta() {
        return getProtocolo() + "://" + Vistas.getIP() + rutaGeneral + "/login.xhtml";
    }

    public static String urlCorreoRegistro(String token) {
        return getProtocolo() + "://" + getIP() + rutaGeneral + "/continuarRegistro.xhtml?token=" + token + "";
    }
    public static String urlCorreoRecuperar(String token) {
        return getProtocolo() + "://" + getIP() + rutaGeneral + "/vistas/recuperar/continuar_recuperar_clave.xhtml?token=" + token + "";
    }

    public static String getIP() {
        ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest req = (HttpServletRequest) external.getRequest();
        String ip = req.getLocalAddr();
        System.out.println("ip:" + ip);
        System.out.println("LocalName:" + req.getLocalName());
//        System.out.println("ContextPath:"+req.getContextPath());
//        System.out.println("RemoteHost:"+req.getRemoteHost());
        System.out.println("ServerName:" + req.getServerName());

//        if ("10.142.0.3".equals(ip)) {
//            return "35.243.252.249";
//        } else {
        return req.getServerName();
//        }
    }

    public static String getProtocolo() {
        ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest req = (HttpServletRequest) external.getRequest();
        String protocolo = req.getProtocol();
        System.out.println("protocolo:" + protocolo);
        System.out.println("protocolo:" + req.getRequestURL());
        System.out.println("protocolo:" + req.getContextPath());
        System.out.println("protocolo:" + req.getQueryString());
        System.out.println("protocolo:" + req.getPathInfo());
        System.out.println("protocolo:" + req.getRequestURI());
        System.out.println("protocolo:" + req.getContentType());
        System.out.println("protocolo:" + req.changeSessionId());
//        return req.getRequestURL().toString().split(":")[0];
        return "https";
    }

    public static int getPort() {
        ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest req = (HttpServletRequest) external.getRequest();
        int port = req.getLocalPort();
        System.out.println("puerto:" + port);
        return port;
    }
}
