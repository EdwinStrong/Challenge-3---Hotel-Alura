package Model;

import java.sql.Timestamp;


/**
 * Esta clase representa al modelo de Huesped.
 */
public class Huesped {
	Integer id;
	String nombre;
	String apellido;
	Timestamp nacimiento;
	String nacionalidad;
	String telefono;
	Integer idReserva;
	
	//Constructor por defecto
	public Huesped() {
		
	}
	
	public Huesped(Integer id, String nombre, String apellido, Timestamp nacimiento, String nacionalidad, String telefono,
			Integer idReserva) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.nacimiento = nacimiento;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
		this.idReserva = idReserva;
	}
	
	public Huesped(String nombre, String apellido, Timestamp nacimiento, String nacionalidad, String telefono,
			Integer idReserva) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.nacimiento = nacimiento;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
		this.idReserva = idReserva;
	}

	public Integer getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Timestamp getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(Timestamp nacimiento) {
		this.nacimiento = nacimiento;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Integer getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Integer idReserva) {
		this.idReserva = idReserva;
	}
	
	@Override
	public String toString() {
		return "Huesped [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", nacimiento=" + nacimiento
				+ ", nacionalidad=" + nacionalidad + ", telefono=" + telefono + ", idReserva=" + idReserva + "]";
	}
}
