package com.jjmp.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.jjmp.dao.BookDAO;
import com.jjmp.dto.BookDTO;

/**
 * Utilidad que convierte entidades (DAO) de libros a estructuras de
 * transferencia de datos (DTO) y viceversa.
 * 
 * @author Jefry Martínez
 *
 */
@Component
public class BookConverter {

	/**
	 * Convierte una entidad de datos de un libro a una estructura de transferencia
	 * de datos.
	 * 
	 * @param bookDAO Entidad a convertir.
	 * @return Estructura de transferencia de datos mapeada.
	 */
	public BookDTO convertitEntidadDTO(BookDAO bookDAO) {
		ModelMapper mapper = new ModelMapper();
		return mapper.map(bookDAO, BookDTO.class);
	}

	/**
	 * Convierte una estructura de transferencia de datos de un libro a una entidad
	 * de datos.
	 * 
	 * @param BookDTO Estructura a convertir.
	 * @return Entidad de datos mapeada.
	 */
	public BookDAO convertirDTOEntidad(BookDTO bookDTO) {
		ModelMapper mapper = new ModelMapper();
		return mapper.map(bookDTO, BookDAO.class);
	}

}
