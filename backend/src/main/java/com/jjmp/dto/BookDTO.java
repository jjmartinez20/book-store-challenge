package com.jjmp.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que almacena los datos de un libro para su transferencia entre
 * procesos.
 * 
 * @author Jefry Martínez
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

	/**
	 * Identificador único del libro.
	 */
	private long bookId;

	/**
	 * Título del libro.
	 */
	@NotBlank(message = "El título (title) del libro es requerido")
	private String title;

	/**
	 * Información acerca del libro.
	 */
	private String description = "";

	/**
	 * Cuántos items de este libro están disponibles.
	 */
	@Min(value = 0, message = "La cantidad de stock debe ser mayor o igual a 0")
	private int stock;

	/**
	 * Cuánto cuesta el libro.
	 */
	@DecimalMin(value = "0.01", message = "El valor de venta (salePrice) debe ser mayor a 0")
	private double salePrice;

	/**
	 * Indicador que muestra si el libro puede ser vendido.
	 */
	private boolean available = true;

}
