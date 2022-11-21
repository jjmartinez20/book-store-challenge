package com.jjmp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Estrucura con los datos necesarios en la petición para registrar una venta.
 * 
 * @author Jefry Martínez
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalePeticionDTO {

	/**
	 * Identificador único del libro.
	 */
	@Min(value = 1, message = "El id del libro debe ser mayor o igual a 1")
	private long bookId;

	/**
	 * Email del cliente que compra el libro.
	 */
	@Email(message = "El correo electrónico debe tener un formato válido")
	@NotBlank(message = "El correo electrónico del cliente es requerido")
	private String customerEmail;

}
