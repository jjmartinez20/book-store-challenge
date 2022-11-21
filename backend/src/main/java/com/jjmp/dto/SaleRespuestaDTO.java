package com.jjmp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Estructura con los datos que se mandan al cliente como respuesta.
 * 
 * @author Jefry Martínez
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleRespuestaDTO {

	/**
	 * Identificador único del libro.
	 */
	private long bookId;

	/**
	 * Email del cliente que compra el libro.
	 */
	private String customerEmail;

	/**
	 * Precio del libro que ha sido vendido.
	 */
	private double price;

}
