package com.jjmp.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jjmp.dao.LikeDAO;

/**
 * Repositorio que se encarga de la persistencia y recuperación de la entidad de
 * likes de los libros.
 * 
 * @author Jefry Martínez
 *
 */
@Repository
public interface LikeRepositorio extends JpaRepository<LikeDAO, Long> {

	/**
	 * Busca los likes que ha recibido un libro.
	 * 
	 * @param id Id del libro a buscar.
	 * @return Lista con los registros de los likes que tiene el libro indicado.
	 */
	public List<LikeDAO> findByBookBookId(long id);

	/**
	 * Busca un registro en particular de un cliente y un libro al que le haya dado
	 * like.
	 * 
	 * @param bookId        Id del libro a buscar.
	 * @param customerEmail Correo electrónico del cliente a buscar.
	 * @return Entidad con los datos del registro si es encontrado o
	 *         <strong>null</strong> si no hay ningún registro que cumpla con los
	 *         criterios de búsqueda.
	 */
	public LikeDAO findByBookBookIdAndCustomerEmail(long bookId, String customerEmail);

}
