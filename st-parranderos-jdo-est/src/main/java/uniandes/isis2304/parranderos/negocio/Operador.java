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

/**
 * Clase para modelar la relación GUSTAN del negocio de los Parranderos:
 * Cada objeto de esta clase representa el hecho que un bebedor gusta de una bebida y viceversa.
 * Se modela mediante los identificadores del bebedor y de la bebida respectivamente.
 * Debe existir un bebedor con el identificador dado
 * Debe existir una bebida con el identificador dado 
 * 
 * @author Germán Bravo
 */
public class Operador implements VOOperador
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador del bebedor que gusta de la bebida
	 */
	private long id;

	/**
	 * El identificador de la bebida que gusta al bebedor
	 */
	private String nombre;


	private String disponibilidad;

	private String tipo;



	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Operador() 
	{
		this.id= 0;
		this.disponibilidad = "";
		this.nombre="";
		this.tipo="";


	}

	/**
	 * Constructor con valores
	 * @param idBebedor - El identificador del bebedor. Debe exixtir un bebedor con dicho identificador
	 * @param idBebida - El identificador de la bebida. Debe existir una bebida con dicho identificador
	 */
	public Operador(long id, String nombre, String tipo, String disponibilidad) 
	{
		this.id = id;
		this.nombre = nombre;
		this.tipo=tipo;
		this.disponibilidad=disponibilidad;


	}

	/**
	 * @return El idBebedor
	 */
	public long getId() 
	{
		return id;
	}

	/**
	 * @param idBebedor - El nuevo idBebedor. Debe existir un bebedor con dicho identificador
	 */
	public void setId(long id) 
	{
		this.id = id;
	}

	/**
	 * @return El idBebida
	 */
	public String getNombre() 
	{
		return nombre;
	}

	/**
	 * @param idBebida - El nuevo identificador de bebida. Debe existir una bebida con dicho identificador
	 */
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public String getTipo() 
	{
		return tipo;
	}

	/**
	 * @param idBebida - El nuevo identificador de bebida. Debe existir una bebida con dicho identificador
	 */
	public void setTipo(String tipo) 
	{
		this.tipo = tipo;
	}



	public String getDisponibilidad() 
	{
		return disponibilidad;
	}

	/**
	 * @param idBebida - El nuevo identificador de bebida. Debe existir una bebida con dicho identificador
	 */
	public void setDisponibilidad(String disponibilidad) 
	{
		this.disponibilidad = disponibilidad;
	}
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "Gustan [id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + ", disponibilidad=" + disponibilidad + "]";
	}
	
}
