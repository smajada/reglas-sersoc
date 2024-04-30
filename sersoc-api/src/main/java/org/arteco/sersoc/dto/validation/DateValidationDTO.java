package org.arteco.sersoc.dto.validation;

import lombok.Data;
import org.arteco.sersoc.model.entities.NoutRegles;

import java.util.Date;

@Data
public class DateValidationDTO {

    public static GenericValidationDTO checkExpireDate(NoutRegles regla, Date now){
        long numDias = checkNumDias(regla, now);
        if (numDias < 7) {
            return new GenericValidationDTO("Quedan " + numDias + " días antes de la fecha de caducidad" , "WARNING");
        }

        return null;
    }

    private static long checkNumDias(NoutRegles regla, Date now) {
        // Calcular la diferencia en milisegundos
        long diferenciaMilisegundos = Math.abs(regla.getDatFin().getTime() - now.getTime());

        // Devolver la diferencia de milisegundos a días
        return diferenciaMilisegundos / (1000 * 60 * 60 * 24);
    }
}
