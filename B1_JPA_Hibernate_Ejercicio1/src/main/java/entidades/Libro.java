package entidades;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the libros database table.
 * 
 */
@Entity
@Table(name="LIBROS", catalog = "ejercicio1", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ISBN")
})


public class Libro implements Serializable {
	private static final long serialVersionUID = 1L;
		
	
	@Id
	@Column(name = "ISBN", unique = true, nullable = false)
	private String isbn;
	
	@Column(name = "AUTOR")
	private String autor;
	
	@Column(name = "TITULO", unique = true, nullable = false)
	private String titulo;
	
	@Column(name = "PRESTADO")
	private byte prestado;


	public Libro(String isbn, String autor, String titulo,byte prestado) {
		
		this.isbn = isbn;
		this.autor = autor;
		this.prestado = prestado;
		this.titulo = titulo;
	}

	
	public Libro() {
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAutor() {
		return this.autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public byte getPrestado() {
		return this.prestado;
	}

	public void setPrestado(byte prestado) {
		this.prestado = prestado;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String toString() {
		return "Libro[isbn=" + isbn + ", titulo=" + titulo + ", autor=" + autor + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.equals(other.autor))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

}