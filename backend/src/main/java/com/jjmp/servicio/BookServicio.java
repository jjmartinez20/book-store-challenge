package com.jjmp.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import com.jjmp.dao.BookDAO;
import com.jjmp.repositorio.BookRepositorio;
import com.jjmp.utilidad.ValidadorEntidad;

/**
 * Servicio que se encarga de realizar las operaciones referentes a los
 * libros.
 * 
 * @author Jefry Martínez
 *
 */
@Service
public class BookServicio {

	/**
	 * Repositorio que maneja la persistencia de datos de la entidad de libros.
	 */
	@Autowired
	private BookRepositorio bookRepository;

	/**
	 * Busca todos los registros de libros en la base de datos.
	 * 
	 * @return Todos los registros encontrados.
	 * 
	 */
	public List<BookDAO> obtenerLibros() {
		return bookRepository.findAll(Sort.by("title").ascending());
	}

	/**
	 * Recupera una página de registros de libros según los parámetros enviados.
	 * 
	 * @param page        Número de página a recuperar.
	 * @param size        Tamaño de la página.
	 * @param unavailable Bandera que indica si se deben traer también los libros
	 *                    inhabilitados o no.
	 * @param title       Nombre del libro a buscar.
	 * @param sort        Criterios de ordenación de la página
	 * @return Página con los registros encontrados.
	 */
	public Page<BookDAO> buscarPaginaLibros(Integer page, Integer size, Boolean unavailable, String title,
			String[] sort) {
		// Valida que el número de página sea válido
		if (page - 1 < 0)
			throw new IllegalArgumentException("El valor de la página (page) debe ser mayor o igual a 1");

		List<Order> ordenes = new ArrayList<>();
		for (String s : sort) { // Itera los criterios de ordenación para agregarlos en la lista
			if (!s.contains(",")) { // El campo no tiene dirección, por lo que se establece ascendente por defecto.
				ordenes.add(new Order(Sort.Direction.ASC, s.trim()));
			} else { // Dirección especificada en el criterio de ordenación
				String[] separado = s.split(",");
				Direction direccion = separado[1].trim().equalsIgnoreCase("desc") ? Sort.Direction.DESC
						: Sort.Direction.ASC;
				ordenes.add(new Order(direccion, separado[0].trim()));
			}
		}
		Pageable paginacion = PageRequest.of(page - 1, size, Sort.by(ordenes));
		if (title != null && !title.equals("")) { // Se ha proporcionado nombre de título para buscar.
			return unavailable.booleanValue() ? bookRepository.findByTitleIgnoreCaseContaining(title, paginacion)
					: bookRepository.findByAvailableTrueAndTitleIgnoreCaseContaining(title, paginacion);
		} else { // No se ha proporcionado nombre de título.
			return unavailable.booleanValue() ? bookRepository.findAll(paginacion)
					: bookRepository.findByAvailableTrue(paginacion);
		}
	}

	/**
	 * Busca un libro a partir de un id.
	 * 
	 * @param id Id de libro a buscar
	 * @return Instancia del modelo con los datos del libro si encuentra el registro
	 *         buscado o null en caso contrario.
	 * 
	 */
	public BookDAO buscarLibro(Long id) {
		return bookRepository.findById(id).orElse(null);
	}

	/**
	 * Agrega un nuevo libro en la base de datos.
	 * 
	 * @param book Entidad a registrar.
	 * @return Entidad devuelta por el repositorio al ejecutar la inserción en la
	 *         base de datos.
	 * 
	 */
	public BookDAO agregarLibro(BookDAO book) {
		return bookRepository.save(book);
	}

	/**
	 * Actualiza un libro en la base de datos.
	 * 
	 * @param book Entidad con los datos a actualizar.
	 * @param id   Id del libro a actualizar
	 * @return Entidad devuelta por el repositorio al ejecutar la actualización en
	 *         la base de datos.
	 * 
	 */
	public BookDAO actualizarLibro(BookDAO book, Long id) {
		BookDAO registro = buscarLibro(id);
		if (registro != null) {
			registro.setTitle(book.getTitle());
			registro.setDescription(book.getDescription());
			registro.setStock(book.getStock());
			registro.setSalePrice(book.getSalePrice());
			registro.setAvailable(book.isAvailable());
			return bookRepository.save(registro);
		} else {
			return null;
		}
	}

	/**
	 * Actualiza los atributos proporcionados de un libro en la base de datos.
	 * 
	 * @param id      Id del libro a actualizar
	 * @param cambios Mapa con los campos de los atributos y sus valores a
	 *                actualizar
	 * @return Entidad devuelta por el repositorio al ejecutar la actualización en
	 *         la base de datos o null si el libro no fue encontrado.
	 * 
	 */
	public BookDAO actualizarLibro(Map<String, Object> cambios, Long id) {
		final BookDAO book = buscarLibro(id);
		if (book != null) {
			cambios.forEach((campo, valor) -> {
				switch (campo) {
				case "title":
					book.setTitle(valor.toString().trim());
					break;
				case "description":
					book.setDescription(valor.toString().trim());
					break;
				case "stock":
					book.setStock((int) valor);
					break;
				case "salePrice":
					book.setSalePrice(Double.parseDouble(valor.toString()));
					break;
				case "available":
					book.setAvailable(Boolean.parseBoolean(valor.toString()));
					break;
				default:
					break;
				}
			});
			ValidadorEntidad.validarEntidad(book);
			return bookRepository.save(book);
		} else {
			return null;
		}
	}

	/**
	 * Busca un libro e intenta eliminarlo si lo encuentra.
	 * 
	 * @param id Id del libro a eliminar
	 * @return true si el registro fue eliminado correctamente o false en caso
	 *         contrario.
	 * 
	 */
	public boolean eliminarLibro(Long id) {
		BookDAO book = buscarLibro(id);
		if (book != null) {
			bookRepository.delete(book);
			return true;
		} else {
			return false;
		}
	}

}
