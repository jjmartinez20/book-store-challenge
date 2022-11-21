package com.jjmp.repositorio;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jjmp.dao.SaleDAO;

/**
 * Repositorio que se encarga de la persistencia y recuperación de la entidad de
 * ventas.
 * 
 * @author Jefry Martínez
 *
 */
@Repository
public interface SaleRepositorio extends JpaRepository<SaleDAO, Long> {

	/**
	 * Busca las ventas de un libro.
	 * 
	 * @param bookId Id del libro a buscar.
	 * @return Listado con todas las ventas registradas.
	 */
	public List<SaleDAO> findByBookBookIdOrderBySaleDateDesc(long bookId);

	/**
	 * Busca las ventas de un libro a partir de una fecha determinada.
	 * 
	 * @param id    Id del libro con el cual buscar las ventas.
	 * @param fecha Fecha a partir de la cual se empezará a buscar los registros
	 * @return Listado con todas las ventas registradas a partir de la fecha
	 *         proporcionada.
	 * 
	 */
	@Query("SELECT s FROM SaleDAO s WHERE s.book.bookId = ?1 AND s.saleDate >= ?2 ORDER BY saleDate DESC")
	public List<SaleDAO> ventasLibroDesdeFecha(long id, LocalDate fecha);

	/**
	 * Busca las ventas de un libro hasta una fecha determinada.
	 * 
	 * @param id    Id del libro con el cual buscar las ventas.
	 * @param fecha Fecha hasta donde se buscará los registros.
	 * @return Listado con todas las ventas registradas hasta la fecha
	 *         proporcionada.
	 * 
	 */
	@Query("SELECT s FROM SaleDAO s WHERE s.book.bookId = ?1 AND s.saleDate <= ?2 ORDER BY saleDate DESC")
	public List<SaleDAO> ventasLibroHastaFecha(long id, LocalDate fecha);

	/**
	 * Busca los movimientos en las cuentas que tiene un cliente entre dos fechas
	 * determinadas
	 * 
	 * @param id         Id del libro con el cual buscar las ventas.
	 * @param fechaDesde Fecha a partir de la cual se empezará a buscar los
	 *                   registros
	 * @param fechaHasta Fecha hasta donde se buscarán los registros.
	 * @return Listado con todas las ventas registradas que están dentro del rango
	 *         de fechas propocionadas.
	 * 
	 */
	@Query("SELECT s FROM SaleDAO s WHERE s.book.bookId = ?1 AND s.saleDate BETWEEN ?2 AND ?3 ORDER BY saleDate DESC")
	public List<SaleDAO> ventasLibroEntreFechas(long id, LocalDate fechaDesde, LocalDate fechaHasta);

}
