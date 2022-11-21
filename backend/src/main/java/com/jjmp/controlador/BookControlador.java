package com.jjmp.controlador;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jjmp.converter.BookConverter;
import com.jjmp.dao.BookDAO;
import com.jjmp.dto.BookDTO;
import com.jjmp.servicio.BookServicio;
import com.jjmp.utilidad.ValidadorEntidad;

/**
 * Controlador que mapea los endpoints de libros.
 * 
 * @author Jefry Martínez
 *
 */
@RestController
@RequestMapping("books")
@CrossOrigin
public class BookControlador {

	/**
	 * Servicio que administra las acciones de la entidad de libros.
	 */
	@Autowired
	private BookServicio bookServicio;

	/**
	 * Clase que convierte entre entidades de transferencia de datos (DTO) a
	 * entidades de persistencia de datos (DAO) y viceversa.
	 */
	@Autowired
	private BookConverter bookConverter;

	/**
	 * Endopoint que devuelve todos los libros de la base de datos.
	 * 
	 * @param size        Tamaño de la página a recuperar
	 * @param page        Página a buscar. Si no se especifica devuelve todos los
	 *                    libros registrados.
	 * @param unavailable Bandera que indica si se deben devolver los libros
	 *                    inhabilitados o no.
	 * @param title       Título o parte del título de un libro para su búsqueda.
	 * @param sort        Criterios de ordenamiento de los datos.
	 * @return Listado con los libros.
	 */
	@GetMapping
	public ResponseEntity<Object> getBooks(@RequestParam(required = false, defaultValue = "12") Integer size,
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "false") Boolean unavailable,
			@RequestParam(required = false, defaultValue = "") String title,
			@RequestParam(required = false) String[] sort) {
		if (page == 0) {
			return ResponseEntity.ok().body(bookServicio.obtenerLibros());
		} else {
			if (sort == null) {
				sort = new String[] { "title,asc" };
			}
			Page<BookDAO> pagina = bookServicio.buscarPaginaLibros(page, size, unavailable, title, sort);
			Map<String, Object> respuesta = new HashMap<>();
			respuesta.put("content", pagina.getContent());
			respuesta.put("size", pagina.getSize());
			respuesta.put("numberOfElements", pagina.getNumberOfElements());
			respuesta.put("totalElements", pagina.getTotalElements());
			respuesta.put("totalPages", pagina.getTotalPages());
			respuesta.put("number", pagina.getNumber() + 1);
			return ResponseEntity.ok().body(respuesta);
		}
	}

	/**
	 * Endpoint que agrega un nuevo libro a la base de datos.
	 * 
	 * @param dto Datos del libro a registrar
	 * @return Información del libro si fue correctamente registrado.
	 * 
	 */
	@PostMapping
	public ResponseEntity<Object> agregarBook(@RequestBody BookDTO dto) {
		dto.setTitle(dto.getTitle().trim());
		dto.setDescription(dto.getDescription().trim());
		ValidadorEntidad.validarEntidad(dto);
		BookDAO book = bookConverter.convertirDTOEntidad(dto);
		book = bookServicio.agregarLibro(book);
		return ResponseEntity.status(HttpStatus.CREATED).body(book);
	}

	/**
	 * Endpoint que actualiza un libro en la base de datos si el id especificado es
	 * encontrado. En caso contrario inserta el nuevo registro.
	 * 
	 * @param dto Datos del libro aactualizado o registrado.
	 * @return Información del libro si fue correctamente registrado.
	 * 
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Object> actualizarLibro(@RequestBody BookDTO dto, @PathVariable Long id) {
		dto.setTitle(dto.getTitle().trim());
		dto.setDescription(dto.getDescription().trim());
		ValidadorEntidad.validarEntidad(dto);
		BookDAO book = bookConverter.convertirDTOEntidad(dto);
		book = bookServicio.actualizarLibro(book, id);
		return ResponseEntity.status(book != null ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(book);
	}

	/**
	 * Endpoint que actualiza la información de atributos específicos de un libro
	 * que es buscado a partir de su id.
	 * 
	 * @param id      Id del cliente a parchar
	 * @param paylaod Atributos específicos a ser actualizados
	 * @return Información del libro si fue correctamente actualizado
	 * 
	 */
	@PatchMapping("/{id}")
	public ResponseEntity<Object> actualizarLibro(@RequestBody Map<String, Object> paylaod, @PathVariable Long id) {
		BookDAO book = bookServicio.actualizarLibro(paylaod, id);
		if (book != null) {
			return ResponseEntity.ok(book);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Endpoint que elimina un libro de la base de datos.
	 * 
	 * @param id Id del libro a eliminar
	 * @return Respuesta con código HTTP 200 si el registro fue eliminado
	 *         correctamente u otro código de error en caso de algún fallo.
	 * 
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> eliminarLibro(@PathVariable Long id) {
		if (bookServicio.eliminarLibro(id)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Método que sobreescribe el comportamiento por defecto que usa la coma como
	 * separador de parámetros.
	 * 
	 * @param binder WebDataBinder que está inicializado en el contexto de la
	 *               aplicación.
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String[].class, new StringArrayPropertyEditor(null));
	}

}
