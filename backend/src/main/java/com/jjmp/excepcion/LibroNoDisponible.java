package com.jjmp.excepcion;

/**
 * Excepción lanzada cuando se intenta registrar una transacción con un libro
 * que no está registrado.
 * 
 * @author Jefry Martínez
 *
 */
public class LibroNoDisponible extends RuntimeException {

	private static final long serialVersionUID = 3127093699241210624L;

	public LibroNoDisponible(String mensajeError) {
		super(mensajeError);
	}

	public LibroNoDisponible(String mensajeError, Exception e) {
		super(mensajeError, e);
	}

}