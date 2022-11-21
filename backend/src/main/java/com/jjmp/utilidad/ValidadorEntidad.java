package com.jjmp.utilidad;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase auxiliar con métodos de validación para las entidades.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
public class ValidadorEntidad {

	@Autowired
	private static Validator validator;

	/**
	 * Método que evalúa una instancia de un modelo contra las restricciones puestas
	 * en las anotaciones de cada uno de sus atributos.
	 * 
	 * @param <T>     Tipo genérico de la clase a evaluar
	 * @param entidad Entidad a ser validada con las anotaciones agregadas en su
	 *                modelo.
	 * @throws ConstraintViolationException Si se encuentra alguna violación en las
	 *                                      restricciones de la entidad evaluada.
	 */
	public static <T> void validarEntidad(T entidad) {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<T>> violaciones = validator.validate(entidad);
		if (!violaciones.isEmpty()) {
			throw new ConstraintViolationException(violaciones);
		}
	}

	private ValidadorEntidad() {

	}

}
