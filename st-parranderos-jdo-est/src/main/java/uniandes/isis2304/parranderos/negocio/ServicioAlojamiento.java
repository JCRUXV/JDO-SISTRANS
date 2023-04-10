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
 * Clase para modelar el concepto BAR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class ServicioAlojamiento implements VOServicioAlojamiento
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador del tipo de bebida
	 */
	private long id;

	/**
	 * El nombre del tipo de bebida
	 */
	private long oferta;


	private long precio;


	private long disponibilidad;
	

	private long servicio;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public ServicioAlojamiento() 
	{
		this.id = 0;
		this.oferta = 0;
		this.precio=0;
		this.disponibilidad=0;
		this.servicio=0;
	}

	/**
	 * Constructor con valores
	 * @param id - El identificador del tipo de bebida
	 * @param nombre - El nombre del tipo de bebida
	 */
	public ServicioAlojamiento(long id, long oferta, long precio, long disponibilidad, long servicio) 
	{
		this.id = id;
		this.oferta = oferta;
		this.precio = precio;
		this.disponibilidad = disponibilidad;
		this.servicio = servicio;
	}

	/**
	 * @return El id del tipo de bebida
	 */
	public long getId() 
	{
		return id;
	}

	/**
	 * @param id - El nuevo id del tipo de bebida
	 */
	public void setId(long id) 
	{
		this.id = id;
	}

	/**
	 * @return El nombre del tipo de bebida
	 */
	public long getOferta() 
	{
		return oferta;
	}

	/**
	 * @param nombre - El nuevo nombre del tipo de bebida
	 */
	public void setOferta(long oferta) 
	{
		this.oferta = oferta;
	}

	public long getPrecio() 
	{
		return precio;
	}

	/**
	 * @param nombre - El nuevo nombre del tipo de bebida
	 */
	public void setPrecio(long precio) 
	{
		this.precio = precio;
	}


	public long getDisponibilidad() 
	{
		return disponibilidad;
	}

	/**
	 * @param nombre - El nuevo nombre del tipo de bebida
	 */
	public void setDisponibilidad(long disponibilidad) 
	{
		this.disponibilidad = disponibilidad;
	}


	public long getServicio() 
	{
		return servicio;
	}

	/**
	 * @param nombre - El nuevo nombre del tipo de bebida
	 */
	public void setServicio(long servicio) 
	{
		this.servicio = servicio;
	}






	/**
	 * @return Una cadena de caracteres con la información del tipo de bebida
	 */
	@Override
	public String toString() 
	{
		return "TipoBebida [id=" + id + ", oferta=" + oferta + ", precio=" + precio + ", disponibilidad=" + disponibilidad + ", servicio=" + servicio + "]";
	}



}
