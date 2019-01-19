/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores.util;

import com.ingesoft.interpro.entidades.Persona;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Properties;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author debian
 */
public class Utilidades {

    public static void enviarCorreoDeRegistro(String destino, String token) {
        Utilidades.enviarCorreo(destino,
                "Registro en Intereses profesionales",
                "<br><b>Cordial Saludo \nEl registro en el sistema de intereses profesionalesfue exitoso</b><br/>"
                + "Verifique su cuenta dando click al siguinte enlace "
                + "http://" + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName()
                + ":8080/intereses_profesionales_frontend_JSF/faces/continuarRegistro.xhtml?token=" + token + "");

    }

    public static void enviarCorreoEdicion(Persona usu) {

        enviarCorreo("" + usu.getEmail(),
                "El sistema de Intereses profesionales",
                "Cordial Saludo. " + "\n" + "\n" + "El sistema de Intereses profesionales le da la bienvenida"
                + "y le informa que su registro se completado correctamente." + "\n"
                + "Solo necesita activar la cuenta dando click en el siguiente enlace: ");

    }

    public static boolean enviarCorreo(String destinatario, String asunto, String mensaje) {
        final String de = "interesprofesionales@gmail.com";
        final String clave = "qjjcuweflsdobjfl";
        String para = destinatario;

        boolean resultado = false;

        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", 587);

            Session session = Session.getInstance(prop,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(de, clave);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(de));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(para));
            message.setSubject(asunto);
            message.setText(mensaje, "utf-8", "html");
//            simpleMessage.setContent(text, "text/html; charset=utf-8");
            Transport.send(message);
            resultado = true;
            System.out.println("========== CORREO ENVIADO CON ÉXITO ============");
        } catch (Exception e) {
            System.out.println("========== ERROR AL ENVIAR CORREO ============ " + e.getMessage());
        }

        return resultado;
    }

    public static String sha256(String cadena) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(cadena.getBytes());

            byte[] mb = md.digest();
            for (int i = 0; i < mb.length; i++) {
                sb.append(Integer.toString((mb[i] & 0xff) + 0x100, 16).substring(1));
            }

        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error-Utilidades -- " + ex.getMessage());
        }
        return sb.toString();
    }

    public static int[] getListaAnios() {
        Calendar cal = Calendar.getInstance();
        int anioInicio = 1999;
        int anioActual = cal.get(Calendar.YEAR);
        int[] vectorA = new int[anioActual - anioInicio];
        for (int i = 0; i < vectorA.length; i++) {
            vectorA[i] = anioActual--;
        }
        return vectorA;
    }
}
