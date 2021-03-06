/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores;

import com.ingesoft.interpro.controladores.util.JsfUtil;
import com.ingesoft.interpro.controladores.util.Utilidades;
import com.ingesoft.interpro.controladores.util.Vistas;
import com.ingesoft.interpro.entidades.CodigoInstitucion;
import com.ingesoft.interpro.entidades.GrupoUsuario;
import com.ingesoft.interpro.entidades.Persona;
import com.ingesoft.interpro.entidades.TipoUsuario;
import com.ingesoft.interpro.entidades.Usuario;
import com.ingesoft.interpro.facades.UsuarioFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.util.AccessGrant;

/**
 *
 * @author debian
 */
@ManagedBean(name = "registroController")
@SessionScoped
public class RegistroController extends Controller implements Serializable {

    private static final long serialVersionUID = 3658300628580536494L;

    @EJB
    UsuarioFacade ejbFacade;
    private Usuario selected;

    private SocialAuthManager socialManager;
    private Profile profile;
    String codigo;
    String usuario;
    String password;
    boolean verificado;
    CodigoInstitucion codInstitucion;
    String token;
    String estadoUsuario;

    private String provider;

    public RegistroController() {
        verificado = false;
        estadoUsuario = null;
    }

    public void registroFacebook() {
        provider = "facebook";
        try {
            socialManager = getFacebookManager();
            String URLRetorno = socialManager.getAuthenticationUrl(provider, Vistas.urlRegistroFacebook());
            FacesContext.getCurrentInstance().getExternalContext().redirect(URLRetorno);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * recibe codigo en registro cuando se loguee por facebook
     *
     * @param code
     */
    public void setCode(String code) {
        System.out.println("code: " + code);
        if (!"".equals(code)) {
            try {
                AccessGrant ag = socialManager.createAccessGrant(provider, code, Vistas.urlRegistroFacebook());
                System.out.println("getSecret: " + ag.getPermission());
                System.out.println("getSecret: " + ag.getPermission().getScope());
                System.out.println("getSecret: " + ag.getSecret());
                System.out.println("getProviderId: " + ag.getProviderId());
                System.out.println("getAttributes: " + ag.getAttributes());
                System.out.println("getSocialAuthConfig: " + socialManager.getSocialAuthConfig());
                AuthProvider ap = socialManager.connect(ag);
                System.out.println("getProviderId: " + ap.getProviderId());
                System.out.println("getCurrentAuthProvider: " + socialManager.getCurrentAuthProvider().getUserProfile());

                // Datos de la cuenta
                password = ap.getUserProfile().getValidatedId();
                usuario = ap.getUserProfile().getEmail();
//                estadoUsuario = UsuarioController.EN_PROCESO;
                Usuario usuEncontrado = getUsuarioController().obtUsuarioPorEmail(usuario);
                if (usuEncontrado == null) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(Vistas.completarRegistroFacebook());
                } else {
                    FacesContext context = FacesContext.getCurrentInstance();
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La cuenta ya esta registrada, intente iniciar sesion.", "");
                    context.addMessage(null, msg);
                }
            } catch (Exception ex) {
                System.out.println("No se pudo crear la cuenta");
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void completarRegistroSocial() throws IOException {
        estadoUsuario = UsuarioController.EN_PROCESO;
        Usuario un_usuario = realizarRegistro();
        if (un_usuario != null) {
            continuarCreacionUsuario(un_usuario);
            FacesContext.getCurrentInstance().getExternalContext().redirect(Vistas.login());
        }
    }

    public String getCode() {
        System.out.println("code: ");
        return "";
    }

//    public void getPerfilUsuario() throws Exception {
//        ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
//        HttpServletRequest request = (HttpServletRequest) ex.getRequest();
//        Map<String, String> parametros = SocialAuthUtil.getRequestParametersMap(request);
//        if (socialManager != null) {
//            AuthProvider provider = socialManager.connect(parametros);
//            this.setProfile(provider.getUserProfile());
//        }
//        FacesContext.getCurrentInstance().getExternalContext().redirect(mainURL);
//    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) throws IOException {
        Usuario unusuario = getUsuarioController().obtUsuarioPorToken(token);
        continuarCreacionUsuario(unusuario);
        System.out.println("unusuario: " + unusuario);
        System.out.println("setToken: " + token);
        this.token = token;
    }

    public void continuarCreacionUsuario(Usuario unusuario) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        String rutaGeneral = Vistas.getRutaGeneral();
        Calendar fechaExpiracion = Calendar.getInstance();
        if (unusuario != null) {
            Calendar fechaActual = Calendar.getInstance();
            Date fechaExp = unusuario.getFechaExpiracionToken();
            fechaExpiracion.setTime(fechaExp);
            boolean antes = fechaActual.before(fechaExpiracion);
            if ((UsuarioController.EN_ESPERA.equals(unusuario.getEstado()) && antes) || estadoUsuario != null) {
                estadoUsuario = UsuarioController.EN_PROCESO;
                Persona persona = getPersonaController().getPersona(unusuario);

                if (getUsuarioController().esEstudiante(unusuario.getIdUsuario())) {
                    EstudianteController estudianteController = getEstudianteController();
                    estudianteController.prepareCreate();
                    estudianteController.getSelected().setIdPersona(persona);
                    estudianteController.getSelected().setCuantosViven(0);
                    estudianteController.create();
                }
                unusuario.setEstado(estadoUsuario);
                estadoUsuario = null;
                selected = unusuario;
                this.create();
                context.getExternalContext().redirect(rutaGeneral + "/continuarRegistro.xhtml");
                return;
            }
        }
        context.getExternalContext().redirect(rutaGeneral + "/registroTokenRechazado.xhtml");
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Usuario getUsuario(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public UsuarioFacade getFacade() {
        return ejbFacade;
    }

    public void create() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioCreated"), selected);
    }

    public void registrarse(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        try {
            Usuario un_usuario = realizarRegistro();

            if (un_usuario != null) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Felicidades", "");
                // enviar email 
                Utilidades.enviarCorreoDeRegistro(getUsuario(), un_usuario.getTokenAcesso());
                
                String rutaGeneral = Vistas.getRutaGeneral();
                context.getExternalContext().redirect(rutaGeneral + "/envioEmailRegistro.xhtml");
//                Utilidades.enviarCorreo("andersonbuitron@unicauca.edu.co", " asunto1", "este es el cuerpo del mensaje");
                //TODO
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Código de registro incorrecto, por favor verifique su código.", "");
            }
        } catch (IOException ex) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrio un Error, Intentalo mas tarde", "");
        }
        context.addMessage(null, msg);

    }

    public Usuario realizarRegistro() {
        System.out.println("codigo:<"+codigo+">");
        if (codigo != null && !"".equals(codigo)) {
            CodigoInstitucionController codigoInstitucionController = getCodigoInstitucionController();
            codInstitucion = codigoInstitucionController.buscarPorCodigoActivacion(codigo);
            if (codInstitucion != null) {
                // Crear persona
                PersonaController personaController = getPersonaController();
                Persona unaPersona = personaController.prepareCreateParaRegistrar();
                unaPersona.setEmail(getUsuario());
                unaPersona.getIdUsuario().setEstado(UsuarioController.EN_ESPERA);
                unaPersona.getIdUsuario().setClave(Utilidades.sha256(getPassword()));
                unaPersona.getIdUsuario().setUsuario(getUsuario());
                unaPersona.getIdUsuario().setFechaCreacion(new Date());
                unaPersona.getIdUsuario().setTokenAcesso("");
                unaPersona.getIdUsuario().setFechaExpiracionToken(Utilidades.getFechaExpiracion());
                unaPersona.setIdInstitucion(codInstitucion.getIdInstitucion());
                unaPersona = personaController.createParaRegistrar();
                // TODO : falta enviar mensaje por usuario repetido
                UsuarioController usuarioController = getUsuarioController();
                Usuario unusuario = unaPersona.getIdUsuario();
                unusuario.setTokenAcesso(Utilidades.generarToken("" + unusuario.getIdUsuario()));
                usuarioController.setSelected(unusuario);
                usuarioController.update();
                // Crear grupo usuario
                GrupoUsuarioController grupoUsuarioController = getGrupoUsuarioController();
                GrupoUsuario grupoUsuario = grupoUsuarioController.prepareCreate();
                grupoUsuario.setUsuario1(unusuario);
                grupoUsuario.setUsuario(unusuario.getUsuario());
                grupoUsuario.setTipoUsuario(codInstitucion.getIdTipoUsuario());
                grupoUsuarioController.create();

                //TODO
                return unusuario;
            } else {
                return null;
            }
        } else {
            // Crear persona
            PersonaController personaController = getPersonaController();
            Persona unaPersona = personaController.prepareCreateParaRegistrar();
            unaPersona.setEmail(getUsuario());
            unaPersona.getIdUsuario().setEstado(UsuarioController.EN_ESPERA);
            unaPersona.getIdUsuario().setClave(Utilidades.sha256(getPassword()));
            unaPersona.getIdUsuario().setUsuario(getUsuario());
            unaPersona.getIdUsuario().setFechaCreacion(new Date());
            unaPersona.getIdUsuario().setTokenAcesso("");
            unaPersona.getIdUsuario().setFechaExpiracionToken(Utilidades.getFechaExpiracion());
         
            unaPersona = personaController.createParaRegistrar();
            // TODO : falta enviar mensaje por usuario repetido
            UsuarioController usuarioController = getUsuarioController();
            Usuario unusuario = unaPersona.getIdUsuario();
            unusuario.setTokenAcesso(Utilidades.generarToken("" + unusuario.getIdUsuario()));
            usuarioController.setSelected(unusuario);
            usuarioController.update();
            // Crear grupo usuario
            GrupoUsuarioController grupoUsuarioController = getGrupoUsuarioController();
            GrupoUsuario grupoUsuario = grupoUsuarioController.prepareCreate();
            grupoUsuario.setUsuario1(unusuario);
            grupoUsuario.setUsuario(unusuario.getUsuario());
            TipoUsuario tipo = getTipoUsuarioController().obtenerPorTipo(UsuarioController.TIPO_ESTUDIANTE);
            grupoUsuario.setTipoUsuario(tipo);
            grupoUsuarioController.create();
            //TODO
            return unusuario;
        }
    }

    public void enviarMensaje() {
        Utilidades.enviarCorreo("andersonbuitron@unicauca.edu.co", " asunto1", "este es el cuerpo del mensaje");
        FacesContext context = FacesContext.getCurrentInstance();
//        System.out.println("ruta: " + FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath());
//        System.out.println("ruta: " + FacesContext.getCurrentInstance().getExternalContext().getContextName());
//        System.out.println("ruta: " + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
//        System.out.println("ruta: " + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName());
//        System.out.println("ruta: " + FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo());
//        System.out.println("ruta: " + FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath());
//        System.out.println("ruta: " + FacesContext.getCurrentInstance().getExternalContext().getRequestScheme());
//        System.out.println("ruta: " + FacesContext.getCurrentInstance().getExternalContext().getResponseContentType());
//        System.out.println("ruta: " + FacesContext.getCurrentInstance().getExternalContext().get);
    }

    public boolean isVerificado() {
        return verificado;
    }

}
