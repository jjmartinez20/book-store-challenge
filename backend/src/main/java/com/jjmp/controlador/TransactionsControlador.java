package com.jjmp.controlador;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jjmp.dao.SaleDAO;
import com.jjmp.dto.TransactionBookDTO;
import com.jjmp.servicio.SaleServicio;

/**
 * Controlador que mapea los endpoints de las transacciones de los libros.
 * 
 * @author Jefry Martínezs
 *
 */
@RestController
@RequestMapping("transactions")
@CrossOrigin
public class TransactionsControlador {

	/**
	 * Servicio que administra las acciones de la entidad de libros.
	 */
	@Autowired
	private SaleServicio saleServicio;

	/**
	 * Genera el estado de cuenta de todas las cuentas de un cliente en un rango de
	 * fechas determinado.
	 * 
	 * @param from   Fecha a partir de la cual se hará la búsqueda
	 * @param to     Fecha hasta la cual se hará la búsqueda
	 * @param bookId Id del libro del cual se buscarán las transacciones
	 * 
	 * @return Registros del estado de cuenta
	 * 
	 */
	@GetMapping("/books/{bookId}")
	public ResponseEntity<Object> buscarTransacciones(
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate from,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate to, @PathVariable Long bookId) {
		if (from != null && to != null && from.isAfter(to))
			throw new IllegalArgumentException("Rango de fechas no válido");
		List<SaleDAO> ventas = saleServicio.buscarVentasLibro(bookId, from, to);
		if (ventas.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		TransactionBookDTO respuesta = new TransactionBookDTO();
		respuesta.setBookId(bookId);
		respuesta.setSales(ventas.stream().map(SaleDAO::getSaleDate).toList());
		double montoRedondeado = Double
				.parseDouble(String.format("%.2f", ventas.stream().mapToDouble(SaleDAO::getPrice).sum()));
		respuesta.setTotalRevenue(montoRedondeado);
		respuesta.setCustomers(ventas.stream().map(SaleDAO::getCustomerEmail).toList());
		return ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}

}
