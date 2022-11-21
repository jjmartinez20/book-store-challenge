package com.jjmp.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que mapea la entidad de la tabla de libros de la base de datos.
 * 
 * @author Jefry Martínez
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class BookDAO {

	/**
	 * Identificador único del libro.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long bookId;

	/**
	 * Título del libro.
	 */
	@Column
	@NotBlank(message = "El título (title) del libro es requerido")
	private String title;

	/**
	 * Información acerca del libro.
	 */
	@Column(columnDefinition = "text")
	private String description;

	/**
	 * Cuántos items de este libro están disponibles.
	 */
	@Column
	@Min(value = 0, message = "La cantidad de stock debe ser mayor o igual a 0")
	private int stock;

	/**
	 * Cuánto cuesta el libro.
	 */
	@Column(name = "sale_price")
	@DecimalMin(value = "0.01", message = "El valor de venta (salePrice) debe ser mayor a 0")
	private double salePrice;

	/**
	 * Indicador que muestra si el libro puede ser vendido.
	 */
	@Column
	private boolean available = true;

}
