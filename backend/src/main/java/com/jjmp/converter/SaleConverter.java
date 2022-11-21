package com.jjmp.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jjmp.dao.BookDAO;
import com.jjmp.dao.SaleDAO;
import com.jjmp.dto.SalePeticionDTO;
import com.jjmp.dto.SaleRespuestaDTO;
import com.jjmp.excepcion.LibroNoEncontradoExcepcion;
import com.jjmp.servicio.BookServicio;

/**
 * Utilidad que convierte entidades (DAO) de ventas a estructuras de
 * transferencia de datos (DTO) y viceversa.
 * 
 * @author Jefry Martínez
 *
 */
@Component
public class SaleConverter {

	/**
	 * Servicio que se encarga de realizar las operaciones referentes a loslibros.
	 */
	@Autowired
	private BookServicio bookServicio;

	/**
	 * Convierte una entidad de datos de una venta a una estructura de transferencia
	 * de datos de respuesta al cliente.
	 * 
	 * @param SaleDAO Entidad a convertir.
	 * @return Estructura de transferencia de datos mapeada.
	 */
	public SaleRespuestaDTO convertitEntidadDTORespuesta(SaleDAO saleDAO) {
		SaleRespuestaDTO respuesta = new SaleRespuestaDTO();
		respuesta.setBookId(saleDAO.getBook().getBookId());
		respuesta.setCustomerEmail(saleDAO.getCustomerEmail());
		respuesta.setPrice(saleDAO.getPrice());
		return respuesta;
	}

	/**
	 * Convierte una estructura de transferencia de datos de la petición de una
	 * venta a una entidad de datos.
	 * 
	 * @param SalePeticionDTO Estructura a convertir.
	 * @return Entidad de datos mapeada.
	 * @throws LibroNoEncontradoExcepcion Cuando se intenta realizar la conversión
	 *                                    usando el id de un libro que no está
	 *                                    registrado en la base de datos.
	 */
	public SaleDAO convertirDTOPeticionEntidad(SalePeticionDTO salePeticionDTO) {
		BookDAO book = bookServicio.buscarLibro(salePeticionDTO.getBookId());
		if (book == null)
			throw new LibroNoEncontradoExcepcion(
					String.format("Libro con id %d no fue encontrado", salePeticionDTO.getBookId()));
		SaleDAO sale = new SaleDAO();
		sale.setBook(book);
		sale.setCustomerEmail(salePeticionDTO.getCustomerEmail());
		sale.setPrice(book.getSalePrice());
		return sale;
	}

}
