package com.jjmp.converter;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jjmp.dao.BookDAO;
import com.jjmp.dao.LikeDAO;
import com.jjmp.dto.LikePeticionDTO;
import com.jjmp.dto.LikeRespuestaDTO;
import com.jjmp.excepcion.LibroNoEncontradoExcepcion;
import com.jjmp.servicio.BookServicio;
import com.jjmp.servicio.LikeServicio;

/**
 * Utilidad que convierte entidades (DAO) de likes a estructuras de
 * transferencia de datos (DTO) y viceversa.
 * 
 * @author Jefry Martínez
 *
 */
@Component
public class LikeConverter {

	/**
	 * Servicio que se encarga de realizar las operaciones referentes a loslibros.
	 */
	@Autowired
	private BookServicio bookServicio;
	
	@Autowired
	private LikeServicio likeServicio;

	/**
	 * Convierte una entidad de datos de un like a una estructura de transferencia
	 * de datos de respuesta al cliente.
	 * 
	 * @param likeDAO Entidad a convertir.
	 * @return Estructura de transferencia de datos mapeada.
	 */
	public LikeRespuestaDTO convertitEntidadDTORespuesta(LikeDAO likeDAO) {
		LikeRespuestaDTO respuesta = new LikeRespuestaDTO();
		List<LikeDAO> likes = likeServicio.recuperarLikesBook(likeDAO.getBook().getBookId());
		respuesta.setBookId(likeDAO.getBook().getBookId());
		respuesta.setLikes(likes.size());
		respuesta.setCustomers(likes.stream().map(LikeDAO::getCustomerEmail).toList());
		return respuesta;
	}

	/**
	 * Convierte una estructura de transferencia de datos de la petición de un
	 * like a una entidad de datos.
	 * 
	 * @param likePeticionDTO Estructura a convertir.
	 * @return Entidad de datos mapeada.
	 * @throws LibroNoEncontradoExcepcion Cuando se intenta realizar la conversión
	 *                                    usando el id de un libro que no está
	 *                                    registrado en la base de datos.
	 */
	public LikeDAO convertirDTOPeticionEntidad(LikePeticionDTO likePeticionDTO) {
		BookDAO book = bookServicio.buscarLibro(likePeticionDTO.getBookId());
		if (book == null)
			throw new LibroNoEncontradoExcepcion(
					String.format("Libro con id %d no fue encontrado", likePeticionDTO.getBookId()));
		LikeDAO like = new LikeDAO();
		like.setBook(book);
		like.setCustomerEmail(likePeticionDTO.getCustomerEmail());
		return like;
	}
	
}
