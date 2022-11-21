package com.jjmp.excepcion;

/**
 * Excepción lanzada cuando se intenta registrar like de un cliente a un libro
 * al que ya lo ha hecho anteriormente.
 * 
 * @author Jefry Martínez
 *
 */
public class LikeExistenteExcepcion extends RuntimeException {
	
	private static final long serialVersionUID = -8669692261006595796L;

	public LikeExistenteExcepcion(String mensajeError) {
		super(mensajeError);
	}

	public LikeExistenteExcepcion(String mensajeError, Exception e) {
		super(mensajeError, e);
	}

}
