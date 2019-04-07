package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.EscribirArchivo;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.ResultadoPorAmbiente;
import com.interpro.mineria.Mineria;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.avalon.framework.parameters.ParameterException;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "mineriaController")
@SessionScoped
public class MineriaController implements Serializable {

    public MineriaController() {
        camposActivos = new HashMap<>();
        camposActivos.put("sexo", true);
        camposActivos.put("edad", true);
        camposActivos.put("p_realista", true);
        camposActivos.put("p_investigativo", true);
        camposActivos.put("p_artistico", true);
        camposActivos.put("p_social", true);
        camposActivos.put("p_emprendedor", true);
        camposActivos.put("p_convencional", true);
    }
    Map<String, Boolean> camposActivos;
    String archivo_de_instancias = "mineria.arff";
    public static String nombreModelo = "modelo.min";
    public static String atributo_clase = "ingenieria";

    String cabecera = "@relation areasdeconocimiento\n"
            + "\n"
            + "@attribute sexo {F,M}\n"
            + "@attribute edad numeric\n"
            + "@attribute ingenieria {SI,NO}\n"
            + "@attribute realistic numeric\n"
            + "@attribute investigativo numeric\n"
            + "@attribute artistic numeric\n"
            + "@attribute social numeric\n"
            + "@attribute emprendedor numeric\n"
            + "@attribute convencional numeric\n"
            + "\n"
            + "@data\n";

    public boolean obtenerDatos2() {

        List<String> lInstancias = new ArrayList<>();

        FacesContext facesContext = FacesContext.getCurrentInstance();

        EncuestaController encuestaController = (EncuestaController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "encuestaController");
        List<Encuesta> encuestas = encuestaController.getItems();
        String ingenieria = "";
        // para indicar si algo salio mal en los campos obtenidos

        System.out.println("Archivo generado: " + archivo_de_instancias);
        EscribirArchivo ea = new EscribirArchivo();
        ea.abrir(archivo_de_instancias);
        ea.escribir(cabecera);

        String[] valores = null;
        String un_registro;
        int cont;
        for (Encuesta encuesta : encuestas) {
            valores = obtenerDatosUnaEncuesta(encuesta);
            if (valores == null) {
                continue;
            }
            un_registro = registroMineria(valores);
            lInstancias.add(un_registro);
        }
        String nombreArchivoCompleto = crearArchivoInstancia(lInstancias, archivo_de_instancias);
        facesContext.getApplication().setMessageBundle("carambas esto es un mensaje");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.showMessageInDialog(new FacesMessage("dataset generado exitosamente", "ubicacion: <br/>" + nombreArchivoCompleto));
        System.out.println("llego hasta aqui");
        return true;
    }

    public String[] obtenerDatosUnaEncuesta(Encuesta encuesta) {
        if (encuesta == null) {
            throw new IllegalArgumentException("encuesta debe ser diferente de null");
        }
        String[] valores = new String[9];
        int cont = 0;
        String ingenieria = null;
        cont = 0;
        String sexo = encuesta.getEstudianteGrado().getEstudiante().getIdPersona().getSexo().toUpperCase();
        if (sexo == null || "".equals(sexo) || !camposActivos.get("sexo")) {
            return null;
        }

        long anos = obtenerEdad(encuesta);
        if (anos == 0 || !camposActivos.get("edad")) {
            return null;
        }

        if (encuesta.getIdAreaProfesional() == null) {
//                continue;
        } else {
            ingenieria = (encuesta.getIdAreaProfesional().getIdAreaProfesional() == 0) ? "SI" : "NO";
        }
        valores[cont++] = sexo;
        valores[cont++] = "" + anos;
        valores[cont++] = ingenieria;
        if (encuesta.getResultadoPorAmbienteList().isEmpty()) {
            return null;
        }
        for (ResultadoPorAmbiente resultAmbi : encuesta.getResultadoPorAmbienteList()) {
            valores[cont++] = "" + resultAmbi.getValor();
        }
        return valores;
    }

    public String crearArchivoInstancia(List<String> registros, String archivo_instancia) {
        EscribirArchivo ea = new EscribirArchivo();
        ea.abrir(archivo_de_instancias);
        ea.escribir(cabecera);
        for (String registro : registros) {
            ea.escribir(registro);
        }
        ea.terminar();
        return ea.getNombre();
    }

    public void entrenarModelo() throws IOException {
        if (obtenerDatos2()) {
            Mineria mineria = new Mineria();
            mineria.entrenar(nombreModelo, archivo_de_instancias, atributo_clase);
        }
    }

    public String registroMineria(String[] registro) {
        StringBuilder sb = new StringBuilder();
        sb.append(registro[0]);
        for (int i = 1; i < registro.length; i++) {
            sb.append(",").append(registro[i]);
        }
        return sb.toString();
    }

    private Integer obtenerEdad(Encuesta encuesta) {
        Date fechaEnc = encuesta.getFecha();
        Date fechaNac = encuesta.getEstudianteGrado().getEstudiante().getIdPersona().getFechaNacimiento();
        if (fechaEnc == null || fechaNac == null) {
            return 0;
        }
        long diferencia = fechaEnc.getTime() - fechaNac.getTime();
        Date dateAnos = new Date(diferencia);
        Calendar inicial = Calendar.getInstance();
        inicial.setTime(new Date((long) 0));

        Calendar fechaNacim = Calendar.getInstance();
        fechaNacim.setTime(dateAnos);
        return fechaNacim.get(Calendar.YEAR) - inicial.get(Calendar.YEAR);
    }

    public String predecir(String[] registro) {
        Mineria mineria = new Mineria();
        String archivo_instancia = "archivo_instancia.arff";
        List<String> instancia = new ArrayList<>();
        instancia.add(registroMineria(registro));
        crearArchivoInstancia(instancia, archivo_instancia);
        String result = null;
        try {
            result = mineria.predecir(nombreModelo, archivo_instancia);
        } catch (IOException ex) {
            Logger.getLogger(MineriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
