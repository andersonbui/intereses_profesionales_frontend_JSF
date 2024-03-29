package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.EscribirArchivo;
import com.ingesoft.interpro.controladores.util.Vistas;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.ResultadoPorAmbiente;
import com.ingesoft.interpro.facades.AbstractFacade;
import com.interpro.mineria.Mineria;
import com.interpro.mineria.Resultado;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "mineriaController")
@SessionScoped
public class MineriaController extends Controller implements Serializable {

    Map<String, Boolean> camposActivos;
    String ruta_descarga = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/")+"/resources/downloads/";
    String archivo_de_instancias = "mineria.arff";
    public static String nombreModelo = "modelo.min";
    public static String atributo_clase = "ingenieria";

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

    public boolean guardarDatosParaEntrenamiento() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        EncuestaController encuestaController = (EncuestaController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "encuestaController");
        List<Encuesta> encuestas = encuestaController.getItems();
        // para indicar si algo salio mal en los campos obtenidos
        System.out.println("Archivo generado: " + archivo_de_instancias);
        String nombreArchivoCompleto = guardarDatosEncuestas(encuestas, ruta_descarga + archivo_de_instancias);

        //facesContext.getApplication().setMessageBundle("carambas esto es un mensaje");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        
        String rutaGeneral = Vistas.getRutaGeneral();
        requestContext.showMessageInDialog(new FacesMessage("dataset generado exitosamente", "<h3>Click para descargar:</h3>  <br/>"
                + "<h4 align=\"center\"><a target=\"blank\" href=\"https://suideal.co" + rutaGeneral + "/javax.faces.resource/downloads/mineria.arff\">" + archivo_de_instancias + "</a></h4>"));
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
        String sexo = encuesta.getEstudiante().
                getIdPersona().
                getSexo();
        
        if (sexo == null || "".equals(sexo) || !camposActivos.get("sexo")) {
            return null;
        }

        sexo = sexo.toUpperCase();
        long anos = obtenerEdad(encuesta);
        if (anos == 0 || !camposActivos.get("edad")) {
            return null;
        }

        if (encuesta.getIdAreaProfesional() == null) {
            return null;
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

    public String guardarDatosEncuestas(List<Encuesta> encuestas, String nombre_archivo) {
        List<String> lInstancias = new ArrayList<>();
        String[] valores = null;
        for (Encuesta encuesta : encuestas) {
            valores = obtenerDatosUnaEncuesta(encuesta);
            if (valores == null) {
                continue;
            }
            lInstancias.add(registroMineria(valores));
        }
        String nombreArchivoCompleto = crearArchivoInstancia(lInstancias, nombre_archivo);
        return nombreArchivoCompleto;
    }

    public String crearArchivoInstancia(List<String> registros, String archivo_instancia) {
        EscribirArchivo ea = new EscribirArchivo();
        ea.abrir(archivo_instancia);
        ea.escribir(cabecera);
        for (String registro : registros) {
            ea.escribir(registro);
        }
        ea.terminar();
        return ea.getNombre();
    }

    public void entrenarModelo() throws IOException {
        if (guardarDatosParaEntrenamiento()) {
            Mineria mineria = new Mineria();
            String nomArchivoInst  = ruta_descarga + archivo_de_instancias;
            System.out.println("nombreModelo: " + nombreModelo + " - nomArchivoInst: " + nomArchivoInst+ " - atributo_clase: " + atributo_clase );
            mineria.entrenar(nombreModelo, nomArchivoInst, atributo_clase);
        }
    }

    private String registroMineria(String[] registro) {
        StringBuilder sb = new StringBuilder();
        sb.append(registro[0]);
        for (int i = 1; i < registro.length; i++) {
            sb.append(",").append(registro[i]);
        }
        return sb.toString();
    }

    private Integer obtenerEdad(Encuesta encuesta) {
        Date fechaEnc = encuesta.getFecha();
        Date fechaNac = encuesta.getEstudiante().getIdPersona().getFechaNacimiento();
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

    public String predecir(Encuesta encuesta) {
        String[] registro = obtenerDatosUnaEncuesta(encuesta);
        String result = "Ningun dato";
        if (registro != null) {
            Mineria mineria = new Mineria();
            String archivo_instancia = "archivo_instancia.arff";
            List<String> instancia = new ArrayList<>();
            instancia.add(registroMineria(registro));
            archivo_instancia = crearArchivoInstancia(instancia, archivo_instancia);
            Resultado resultadoMin = null;
            resultadoMin = mineria.predecir(nombreModelo, archivo_instancia);
            if (resultadoMin != null) {
                result = " ";
                System.out.println("resultado mineria: " + resultadoMin.getResultados());
                for (String[] resultado : resultadoMin.getResultados()) {
                    System.out.println("resultado mineria: " + Arrays.toString(resultado));
//                result = resultado[0] + " : " + String.format("%.2f", Double.parseDouble(resultado[1])) + " %\n";
                    result += resultado[0] + " : " + resultado[1] + " % -- \n";
                }
            }
            return result;
        }
        return result;
    }

    @Override
    protected AbstractFacade getFacade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
