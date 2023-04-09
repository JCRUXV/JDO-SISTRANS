/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;



/**
 * Clase para modelar el concepto BEBEDOR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class Oferta implements VOOferta
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO del bebedor
	 */
	private long id;	
	
	/**
	 * El nombre del bebedor
	 */
	private String tipo;
	
	/**
	 * La ciudad del bebedor
	 */
	private long capacidad;
	
	/**
	 * El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 */
	private String ubicacion;
	
	/**
	 * Las visitas realizadas por el bebedor. 
	 * Cada visita es una tripleta de objetos [Bar, Timestamp, String], que representan el bar, la fecha 
	 * y el horario en que el bebedor realizó la visita
	 */
	private long vivienda;

	/**
	 * Las bebidas que le gustan el bebedor. 
	 * Cada visita es una pareja de objetos [Bebida, String], que representan la bebida y el nombre del 
	 * tipo de bebida que le gustan al bebedor 
	 */
	private String disponibilidad;
	
	
	
	
	private Date fecha_inicial;
	
	
	
	
	private long cant_dias;
	
	
	
	private long precio;
	
	
	
	private long operador;
	
	
	
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Oferta() 
	{
		this.id = 0;
		this.tipo = "";
		this.capacidad = 0;
		this.cant_dias = 0;
		this.disponibilidad = "";
		this.fecha_inicial = new Date(0);
		this.precio=0;
		this.operador=0;
		this.ubicacion="";
		this.vivienda=0;
	}

	/**
	 * Constructor con valores
	 * @param id - El id del bebedor
	 * @param nombre - El nombre del bebedor
	 * @param ciudad - La ciudad del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 */


	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */



	public Oferta(long id, String tipo, long capacidad, String ubicacion, long vivienda, String disponibilidad,
			Date fecha_inicial, long cant_dias, long precio, long operador) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.capacidad = capacidad;
		this.ubicacion = ubicacion;
		this.vivienda = vivienda;
		this.disponibilidad = disponibilidad;
		this.fecha_inicial = fecha_inicial;
		this.cant_dias = cant_dias;
		this.precio = precio;
		this.operador = operador;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public long getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(long capacidad) {
		this.capacidad = capacidad;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public long getVivienda() {
		return vivienda;
	}

	public void setVivienda(long vivienda) {
		this.vivienda = vivienda;
	}

	public String getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(String disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public  Date getFecha_inicial() {
		return fecha_inicial;
	}

	public void setFecha_inicial(Date fecha_inicial) {
		this.fecha_inicial = fecha_inicial;
	}

	public long getCant_dias() {
		return cant_dias;
	}

	public void setCant_dias(long cant_dias) {
		this.cant_dias = cant_dias;
	}

	public long getPrecio() {
		return precio;
	}

	public void setPrecio(long precio) {
		this.precio = precio;
	}

	public long getOperador() {
		return operador;
	}

	public void setOperador(long operador) {
		this.operador = operador;
	}
	
	@Override
	public String toString() 
	{
		return "Oferta [id=" + id + ", tipo=" + tipo + ", capacidad=" + capacidad + ", ubicacion=" + ubicacion + ", vivienda=" + vivienda + ", disponibilidad=" + disponibilidad +", fecha_inicial=" + fecha_inicial + ", cant_dias=" + cant_dias + ", precio=" + precio + ", operador=" + operador + " ]";
	}
	
	
	

	/**
	 * @return Una cadena de caracteres con la información COMPLEtA del bebedor.
	 * Además de la información básica, contiene las visitas realizadas (una por línea) y 
	 * las bebidas que le gustan al bebedor (una por línea)
	 */


}
