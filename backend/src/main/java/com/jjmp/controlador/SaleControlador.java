package com.jjmp.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jjmp.converter.SaleConverter;
import com.jjmp.dao.SaleDAO;
import com.jjmp.dto.SalePeticionDTO;
import com.jjmp.servicio.SaleServicio;
import com.jjmp.utilidad.ValidadorEntidad;

/**
 * Controlador que mapea los endpoints de las ventas de los libros.
 * 
 * @author Jefry Martínez
 *
 */
@RestController
@RequestMapping("sales")
@CrossOrigin
public class SaleControlador {

	/**
	 * Servicio que administra las acciones de la entidad de venta de libros.
	 */
	@Autowired
	private SaleServicio saleServicio;

	/**
	 * Clase que convierte entre entidades de transferencia de datos (DTO) a
	 * entidades de persistencia de datos (DAO) y viceversa.
	 */
	@Autowired
	private SaleConverter saleConverter;

	
	/**
	 * Endpoint que agregaa un nueva venta a la base de datos.
	 * 
	 * @param dto Datos de la venta de libro a registrar
	 * @return Información de la venta si fue correctamente registrado.
	 * 
	 */
	@PostMapping
	public ResponseEntity<Object> agregarSale(@RequestBody SalePeticionDTO dto) {
		ValidadorEntidad.validarEntidad(dto);
		SaleDAO sale = saleConverter.convertirDTOPeticionEntidad(dto);
		sale = saleServicio.agregarVenta(sale);
		return ResponseEntity.status(HttpStatus.CREATED).body(saleConverter.convertitEntidadDTORespuesta(sale));
	}

}
