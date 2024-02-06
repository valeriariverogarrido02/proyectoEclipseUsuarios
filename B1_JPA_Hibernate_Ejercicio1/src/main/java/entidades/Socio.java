package entidades;

import java.io.Serializable;


import javax.persistence.*;



@Entity
@Table(name="SOCIOS", catalog = "ejercicio1", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID_SOCIO")
})


public class Socio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_SOCIO", unique = true, nullable = false)
	private int idSocio;
	
	@Column(name="APELLIDOS")
	private String apellidos;

	@Column(name="NOMBRE")
	private String nombre;


	public Socio() {
	}

	public int getIdSocio() {
		return this.idSocio;
	}

	public void setIdSocio(int idSocio) {
		this.idSocio = idSocio;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}