package com.jjmp.dto;

import java.util.ArrayList;
import java.util.List;
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
public class LikeRespuestaDTO {

	/**
	 * Identificador único del libro.
	 */
	private long bookId;

	/**
	 * Cantidad de likes que tiene el libro.
	 */
	private int likes;

	/**
	 * Lista de clientes que le han dado like al libro.
	 */
	private List<String> customers = new ArrayList<>();
}
