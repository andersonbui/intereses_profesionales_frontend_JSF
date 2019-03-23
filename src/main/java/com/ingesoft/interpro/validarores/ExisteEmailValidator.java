/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.validarores;

import com.ingesoft.interpro.controladores.UsuarioController;
import java.util.Map;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.validate.ClientValidator;

/**
 *
 * @author debian
 */
@FacesValidator(value = "existeEmailValidator")
public class ExisteEmailValidator implements Validator, ClientValidator {
 
    public ExisteEmailValidator() {
    }
 
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(value == null) {
            return;
        }
        String email = value.toString();
        UsuarioController usuarioController = (UsuarioController) context.getApplication().getELResolver().
                getValue(context.getELContext(), null, "usuarioController");
        if(usuarioController.obtUsuarioPorEmail(email) != null) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error", 
                        "El email ya existe, por favor ingrese otro."));
        }
    }
 
    public Map<String, Object> getMetadata() {
        return null;
    }
 
    public String getValidatorId() {
        return "custom.emailValidator";
    }
     
}
