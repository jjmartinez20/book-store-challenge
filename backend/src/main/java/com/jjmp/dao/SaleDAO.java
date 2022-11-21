package com.jjmp.dao;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que mapea la entidad de la tabla de ventas de libros en la base de datos.
 * 
 * @author Jefry Martínez
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sales")
public class SaleDAO {
	
	/**
	 * Identificador único.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	/**
	 * Libro vendido.
	 */
	@ManyToOne
	@JoinColumn(name = "bookId", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BookDAO book;
	
	/**
	 * Email del cliente que compra el libro.
	 */
	@Column
	@Email(message = "El correo electrónico debe tener un formato válido")
	@NotBlank(message = "El correo electrónico del cliente es requerido")
	private String customerEmail;
	
	/**
	 * Precio del libro que ha sido vendido.
	 */
	@Column
	@DecimalMin(value = "0.01", message = "El valor de venta (price) debe ser mayor a 0")
	private double price;
	
	/**
	 * Fecha en la que se registró la venta
	 */
	@Column(name = "sale_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate saleDate = LocalDate.now();

}