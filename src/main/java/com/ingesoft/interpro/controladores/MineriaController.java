package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.EscribirArchivo;
import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.ResultadoPorAmbiente;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "mineriaController")
@SessionScoped
public class MineriaController implements Serializable {

    public MineriaController() {

    }
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
        StringBuilder sb = new StringBuilder();
        String ingenieria = "";
        for (Encuesta encuesta : encuestas) {
            String sexo = encuesta.getIdEstudiante().getIdPersona().getSexo().toUpperCase();
            Random rand = new Random();
            long anos = 13 + rand.nextInt(6);
//            long result = encuesta.getFecha().getYear()- encuesta.getIdEstudiante().getIdPersona().getFechaNacimiento().getYear();
//            Calendar fechaNac = Calendar.getInstance();
//            fechaNac.setTimeInMillis(result);

            if (encuesta.getIdAreaProfesional() != null) {
                ingenieria = (encuesta.getIdAreaProfesional().getIdAreaProfesional() == 0) ? "SI" : "NO";
            }

            sb.append(sexo);
            sb.append(",");
            sb.append(anos);
            sb.append(",");
            sb.append(ingenieria);
            for (ResultadoPorAmbiente resultAmbi : encuesta.getResultadoPorAmbienteList()) {
                sb.append(",");
                sb.append(resultAmbi.getValor());
            }
            sb.append("\n");
        }
        System.out.println("Archivo generado: " + nombreArchivo);
        EscribirArchivo ea = new EscribirArchivo();
        ea.abrir(nombreArchivo);
        ea.escribir(cabecera);
        ea.escribir(sb.toString());
        ea.terminar();

    }

}
