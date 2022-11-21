package com.jjmp.servicio;

import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jjmp.dao.SaleDAO;
import com.jjmp.excepcion.LibroNoDisponible;
import com.jjmp.excepcion.LibroSinStockExcepcion;
import com.jjmp.repositorio.SaleRepositorio;

/**
 * Servicio que se encarga de realizar las operaciones referentes a las ventas
 * de libros.
 * 
 * @author Jefry Martínez
 *
 */
@Service
public class SaleServicio {

	/**
	 * Repositorio que maneja la persistencia de datos de la entidad de ventas.
	 */
	@Autowired
	private SaleRepositorio saleRepositorio;

	/**
	 * Servicio que se encarga de realizar las operaciones referentes a loslibros.
	 */
	@Autowired
	private BookServicio bookServicio;

	/**
	 * Agrega una nueva venta de un libro en la base de datos.
	 * 
	 * @param sale Entidad a registrar.
	 * @return Entidad devuelta por el repositorio al ejecutar la inserción en la
	 *         base de datos.
	 * @throws LibroNoDisponible      Si el libro indicado no está disponible para
	 *                                la venta.
	 * @throws LibroSinStockExcepcion Cuando el libro indicado ya no tiene stock
	 *                                disponible para la venta.
	 */
	@Transactional(rollbackOn = Exception.class)
	public SaleDAO agregarVenta(SaleDAO sale) {
		if (!sale.getBook().isAvailable())
			throw new LibroNoDisponible(
					String.format("El libro '%s' no está disponible para venta", sale.getBook().getTitle()));
		int stock = sale.getBook().getStock();
		if (stock == 0)
			throw new LibroSinStockExcepcion(
					String.format("El libro '%s' no tiene stock disponible", sale.getBook().getTitle()));
		SaleDAO registro = saleRepositorio.save(sale);
		registro.getBook().setStock(stock - 1);
		bookServicio.actualizarLibro(registro.getBook(), registro.getBook().getBookId());
		return registro;
	}

	/**
	 * Recupera todas las ventas de un libro determinado.
	 * 
	 * @param bookId Id del libro a buscar.
	 * @param from   Fecha a partir de la cual se hará la búsqueda
	 * @param to     Fecha hasta la cual se hará la búsqueda
	 * @return Lista con los registros de ventas del libro indicado.
	 */
	public List<SaleDAO> buscarVentasLibro(long bookId, LocalDate from, LocalDate to) {
		if (from != null && to == null) {
			return saleRepositorio.ventasLibroDesdeFecha(bookId, from);
		} else if (from == null && to != null) {
			return saleRepositorio.ventasLibroHastaFecha(bookId, to);
		} else if (from != null && to != null) {
			return saleRepositorio.ventasLibroEntreFechas(bookId, from, to);
		} else {
			return saleRepositorio.findByBookBookIdOrderBySaleDateDesc(bookId);
		}
	}

}
