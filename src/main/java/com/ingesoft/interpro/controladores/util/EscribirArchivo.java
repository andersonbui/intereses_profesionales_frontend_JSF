package com.ingesoft.interpro.controladores.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author debian
 */
public class EscribirArchivo {

    private FileWriter fw;
    private PrintWriter pw;
    private File archivo;

    public void abrir(String nombreArchivo) {
        try {
            archivo = new File(nombreArchivo);
            fw = new FileWriter(archivo, false);
            pw = new PrintWriter(fw);
        } catch (IOException ex) {
            Logger.getLogger(EscribirArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void escribir(String cadena) {
        pw.println(cadena);
    }

    public void escribir(List lista) {
        for (Object object : lista) {
            escribir(object.toString());
        }
    }

    public void terminar() {
        if (pw != null) {
            pw.close();
        }
    }

    public String getNombre() {
        return archivo.getAbsolutePath();
    }
}
