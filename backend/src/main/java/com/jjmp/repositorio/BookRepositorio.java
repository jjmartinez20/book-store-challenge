package com.jjmp.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jjmp.dao.BookDAO;

/**
 * Repositorio que se encarga de la persistencia y recuperación de la entidad de
 * libros.
 * 
 * @author Jefry Martínez
 *
 */
@Repository
public interface BookRepositorio extends JpaRepository<BookDAO, Long> {

	/**
	 * Recupera una página con los libros disponibles.
	 * 
	 * @param page Objeto con los criterios de paginación.
	 * @return Página con los datos encontrados según los criterios proporcionados.
	 */
	public Page<BookDAO> findByAvailableTrue(Pageable page);

	/**
	 * Recupera una página con los libros disponibles en base al nombre de título
	 * proporcionado.
	 * 
	 * @param title Nombre o parte del nombre del título del libro a buscar.
	 * @param page  Objeto con los criterios de paginación.
	 * @return Página con los datos encontrados según los criterios proporcionados.
	 */
	public Page<BookDAO> findByAvailableTrueAndTitleIgnoreCaseContaining(String title, Pageable page);

	/**
	 * Recupera una página con los libros en base al nombre de título proporcionado,
	 * sin importar si están disponibles o no.
	 * 
	 * @param title Nombre o parte del nombre del título del libro a buscar.
	 * @param page  Objeto con los criterios de paginación.
	 * @return Página con los datos encontrados según los criterios proporcionados.
	 */
	public Page<BookDAO> findByTitleIgnoreCaseContaining(String title, Pageable page);

}
