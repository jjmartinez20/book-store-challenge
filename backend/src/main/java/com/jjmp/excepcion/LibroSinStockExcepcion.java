package com.jjmp.excepcion;

/**
 * Excepción lanzada cuando se intenta registrar una transacción con un libro
 * que no tiene stock disponible.
 * 
 * @author Jefry Martínez
 *
 */
public class LibroSinStockExcepcion extends RuntimeException {

	private static final long serialVersionUID = -6753741692878947191L;

	public LibroSinStockExcepcion(String mensajeError) {
		super(mensajeError);
	}

	public LibroSinStockExcepcion(String mensajeError, Exception e) {
		super(mensajeError, e);
	}
	
}
