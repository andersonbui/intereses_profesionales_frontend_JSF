package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.EscribirArchivo;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.ResultadoPorAmbiente;
import java.io.Serializable;
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
    String nombreArchivo = "mineria.arff";
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

    public void sacarDatos() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        LoginController loginController = (LoginController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "loginController");

        EncuestaController encuestaController = (EncuestaController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "encuestaController");
        List<Encuesta> encuestas = encuestaController.getItems();
        StringBuilder sb = null;
        String ingenieria = "";
        // para indicar si algo salio mal en los campos obtenidos
        boolean algoMal = false;

        System.out.println("Archivo generado: " + nombreArchivo);
        EscribirArchivo ea = new EscribirArchivo();
        ea.abrir(nombreArchivo);
        ea.escribir(cabecera);
        for (Encuesta encuesta : encuestas) {
            sb = new StringBuilder();
            String sexo = encuesta.getEstudianteGrado().getEstudiante().getIdPersona().getSexo().toUpperCase();
            if (sexo == null || "".equals(sexo) || !camposActivos.get("sexo")) {
                continue;
            }

            long anos = obtenerEdad(encuesta);
            if (anos == 0 || !camposActivos.get("edad")) {
                continue;
            }

            if (encuesta.getIdAreaProfesional() == null) {
//                continue;
            } else {
                ingenieria = (encuesta.getIdAreaProfesional().getIdAreaProfesional() == 0) ? "SI" : "NO";
            }

            sb.append(sexo);
            sb.append(",");
            sb.append(anos);
            sb.append(",");
            sb.append(ingenieria);
            if (encuesta.getResultadoPorAmbienteList().isEmpty()) {
                continue;
            }
            for (ResultadoPorAmbiente resultAmbi : encuesta.getResultadoPorAmbienteList()) {
                sb.append(",");
                sb.append(resultAmbi.getValor());
            }
            ea.escribir(sb.toString());
        }
        ea.terminar();
//        facesContext.addMessage(null, new FacesMessage("You can't delete it.","archivo: "));
//        facesContext.addMessage("hola2", new FacesMessage("You can't delete it.","archivo: "+ea.getNombre()));
//facesContext.getApplication().getELResolver()
        facesContext.getApplication().setMessageBundle("carambas esto es un mensaje");  
        RequestContext requestContext  = RequestContext.getCurrentInstance();
        requestContext.showMessageInDialog(new FacesMessage("dataset generado exitosamente", "ubicacion: <br/>"+ea.getNombre()));
//        requestContext.openDialog("este es otro mensaje");
//        requestContext.execute("PF('#molestia').setvalue('carambas');");
//        requestContext.execute("PF('dlg1').show();");
        System.out.println("llego hasta aqui");
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
        return fechaNacim.get(Calendar.YEAR)- inicial.get(Calendar.YEAR);
    }
}
