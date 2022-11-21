package com.jjmp.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que mapea la entidad de la tabla de likes de libros en la base de
 * datos.
 * 
 * @author Jefry Martínez
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "likes")
public class LikeDAO {

	/**
	 * Identificador único.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * Libro al que se le da like.
	 */
	@ManyToOne
	@JoinColumn(name = "bookId", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BookDAO book;

	/**
	 * Email del cliente que le da like al libro.
	 */
	@Column
	@Email(message = "El correo electrónico debe tener un formato válido")
	@NotBlank(message = "El correo electrónico del cliente es requerido")
	private String customerEmail;

}
