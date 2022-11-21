package com.jjmp.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jjmp.dao.LikeDAO;
import com.jjmp.excepcion.LibroNoDisponible;
import com.jjmp.excepcion.LikeExistenteExcepcion;
import com.jjmp.repositorio.LikeRepositorio;

/**
 * Servicio que se encarga de realizar las operaciones referentes a los likes de
 * los libros.
 * 
 * @author Jefry Martínez
 *
 */
@Service
public class LikeServicio {

	/**
	 * Repositorio que maneja la persistencia de datos de la entidad de likes.
	 */
	@Autowired
	private LikeRepositorio likeRepositorio;

	/**
	 * Agrega una nuevo like de un libro en la base de datos.
	 * 
	 * @param like Entidad a registrar.
	 * @return Entidad devuelta por el repositorio al ejecutar la inserción en la
	 *         base de datos.
	 * @throws LibroNoDisponible      Si el libro indicado no está disponible para
	 *                                darle like.
	 * @throws LikeExistenteExcepcion Si el cliente ya le ha dado like al libro.
	 */
	public LikeDAO agregarLike(LikeDAO like) {
		if (!like.getBook().isAvailable())
			throw new LibroNoDisponible(
					String.format("El libro '%s' no está disponible para darle like", like.getBook().getTitle()));
		if (verificarLikeClienteBook(like.getBook().getBookId(), like.getCustomerEmail()))
			throw new LikeExistenteExcepcion(String.format("El cliente con correo %s ya le ha dado like al libro '%s'",
					like.getCustomerEmail(), like.getBook().getTitle()));
		return likeRepositorio.save(like);
	}

	/**
	 * Recupera todos los registros de likes que tiene un libro determinado.
	 * 
	 * @param bookId Id del libro a buscar
	 * @return Lista de los registros de likes que tiene el libro indicado.
	 */
	public List<LikeDAO> recuperarLikesBook(long bookId) {
		return likeRepositorio.findByBookBookId(bookId);
	}

	/**
	 * Busca si un cliente le ha dado like a un libro determinado.
	 * 
	 * @param bookId Id del libro a buscar.
	 * @param email  Correo electrónico del cliente a buscar.
	 * @return <strong>true</strong> si existe un registro con los parámetros
	 *         indicado o <strong>false</strong> en caso contrario.
	 */
	public boolean verificarLikeClienteBook(long bookId, String email) {
		return likeRepositorio.findByBookBookIdAndCustomerEmail(bookId, email) != null;
	}

}
