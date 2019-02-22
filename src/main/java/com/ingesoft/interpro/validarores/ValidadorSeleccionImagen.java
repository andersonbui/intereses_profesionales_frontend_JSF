package com.ingesoft.interpro.validarores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Juan
 */
@FacesValidator(value = "validadorSeleccionImagen")
public class ValidadorSeleccionImagen implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe responder todas las preguntas", "Responda todas las preguntas or favor");
        if (value == null) {
            throw new ValidatorException(msg);
        }
        Double valor = Double.parseDouble(value.toString());
        if (valor < 0 || valor > 2|| valor.isNaN()) {
            throw new ValidatorException(msg);
        }

    }

}
