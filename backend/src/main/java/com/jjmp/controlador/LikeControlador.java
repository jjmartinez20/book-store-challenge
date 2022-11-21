package com.jjmp.controlador;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jjmp.converter.LikeConverter;
import com.jjmp.dao.LikeDAO;
import com.jjmp.dto.LikePeticionDTO;
import com.jjmp.servicio.LikeServicio;
import com.jjmp.utilidad.ValidadorEntidad;

/**
 * Controlador que mapea los endpoints de likes de los libros.
 * 
 * @author Jefry Martínez
 *
 */
@RestController
@RequestMapping("likes")
@CrossOrigin
public class LikeControlador {

	/**
	 * Servicio que administra las acciones de la entidad de likes de libros.
	 */
	@Autowired
	private LikeServicio likeServicio;

	/**
	 * Clase que convierte entre entidades de transferencia de datos (DTO) a
	 * entidades de persistencia de datos (DAO) y viceversa.
	 */
	@Autowired
	private LikeConverter likeConverter;

	/**
	 * Endpoint que agrega un nuevo like a la base de datos.
	 * 
	 * @param dto Datos del like de libro a registrar
	 * @return Información de los likes que tiene el libro.
	 * 
	 */
	@GetMapping("/books/{bookId}/cantidad")
	public ResponseEntity<Object> recuperarLikesBook(@PathVariable Long bookId) {
		Map<String, Object> respuesta = new HashMap<>();
		respuesta.put("cantidad", likeServicio.recuperarLikesBook(bookId).size());
		return ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}

	/**
	 * Endpoint que agrega un nuevo like a la base de datos.
	 * 
	 * @param dto Datos del like de libro a registrar
	 * @return Información del like si fue correctamente registrado.
	 * 
	 */
	@PostMapping
	public ResponseEntity<Object> agregarLike(@RequestBody LikePeticionDTO dto) {
		ValidadorEntidad.validarEntidad(dto);
		LikeDAO like = likeConverter.convertirDTOPeticionEntidad(dto);
		like = likeServicio.agregarLike(like);
		return ResponseEntity.status(HttpStatus.CREATED).body(likeConverter.convertitEntidadDTORespuesta(like));
	}

}
