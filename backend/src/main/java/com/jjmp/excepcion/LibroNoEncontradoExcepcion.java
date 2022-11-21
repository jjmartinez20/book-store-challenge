package com.jjmp.excepcion;

/**
 * Excepción lanzada cuando se intenta registrar una transacción con un libro
 * que no está registrado.
 * 
 * @author Jefry Martínez
 *
 */
public class LibroNoEncontradoExcepcion extends RuntimeException {

	private static final long serialVersionUID = -2623056841065397908L;

	public LibroNoEncontradoExcepcion(String mensajeError) {
		super(mensajeError);
	}

	public LibroNoEncontradoExcepcion(String mensajeError, Exception e) {
		super(mensajeError, e);
	}

}
