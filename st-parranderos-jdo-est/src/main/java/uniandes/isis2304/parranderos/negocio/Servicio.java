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
 * Clase para modelar la relación VISITAN del negocio de los Parranderos:
 * Cada objeto de esta clase representa el hecho que un bebedor visitó un bar y viceversa.
 * Se modela mediante los identificadores del bebedor y del bar respectivamente
 * Debe existir un bebedor con el identificador dado
 * Debe existir un bar con el identificador dado 
 * Adicionalmente contiene la fecha y el horario (DIURNO, NOCTURNO, TODOS) en el cual el bebedor visitó el bar
 * 
 * @author Germán Bravo
 */
public class Servicio implements VOServicio

{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador del bebedor que realiza la visita
	 */
	private long codigo;
	
	/**
	 * El identificador del bar visitado
	 */
	private String nombre;
	
	/**
	 * La fecha de la visita
	 */
	
	
	/**
	 * El horario en que se realizó la visita (DIURNO, NOCTURNO, TODOS)
	 */
	private String descripcion;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Servicio() 
	{
		this.codigo = 0;
		this.nombre = "";
		this.descripcion = "";
		
	}

	/**
	 * Constructor con valores
	 * @param idBebedor - El identificador del b ebedor. Debe existir un bebedor con dicho identificador
	 * @param idBar - El identificador del bar. Debe exixtir un bar con dicho identificador
	 * @param fechaVisita - La fecha en la cual se realiza la visita
	 * @param horario - El horario en el que el bebedor vista el bar (DIURNO, NOCTURNO, TODOS)
	 */
	public Servicio(long codigo, String nombre, String descripcion) 
	{
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	/**
	 * @return El idBebedor
	 */
	public long getCodigo() 
	{
		return codigo;
	}

	/**
	 * @param idBebedor - El nuevo idBebedor. Debe existir un bebedor con dicho identificador
	 */
	public void setCodigo(long codigo) 
	{
		this.codigo = codigo;
	}

	/**
	 * @return El idBar
	 */
	public String getNombre() 
	{
		return nombre;
	}

	/**
	 * @param idBar - El nuevo idBar. Debe exixtir un bar con dicho identificador
	 */
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	/**
	 * @return La fechaVisita
	 */


	/**
	 * @return El horario
	 */
	public String getDescripcion()
	{
		return descripcion;
	}

	/**
	 * @param horario - El nuevo horario en que se realizó la visita (DIURNO, NOCTURNO, TODOS)
	 */
	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}

	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "Servicio [codigo=" + codigo + ", nombre=" + nombre + ", descripcion=" + descripcion + ", ]";
	}
}
