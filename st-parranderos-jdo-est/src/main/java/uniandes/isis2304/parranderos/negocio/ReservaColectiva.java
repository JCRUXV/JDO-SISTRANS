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
 * Clase para modelar el concepto BEBIDA del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class ReservaColectiva implements VOReservaColectiva
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de la bebida
	 */
	private long id;
	

	
	/**
	 * El identificador del tipo de bebida de la bebida. Debe existir en la tabla de tipoBebida
	 */
	private Date fecha;

	
	
	/**
	 * El grado de alcohol de la bebida (Mayor que 0)
	 */
	private long duracion;


	private long  numero_p;


	private long  oferta;


	private long  cliente;
	
	private long numero_hab;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public ReservaColectiva() 
	{
		this.id = 0;
		this.fecha = new Date(0);
		this.duracion = 0;
		this.numero_p = 0;
		this.oferta=0;
		this.cliente=0;
		this.numero_hab = 0;
		

	}

	/**
	 * Constructor con valores
	 * @param id - El id de la bebida
	 * @param nombre - El nombre de la bebida
	 * @param tipo - El identificador del tipo de bebida
	 * @param gradoAlcohol - El graddo de alcohol de la bebida (Mayor que 0)
	 */
	public ReservaColectiva(long id, long costo, Date fecha, long duracion, long numero_p, long oferta, long cliente, long numero_hab) 
	{
		this.id = id;
		this.fecha = fecha;
		this.duracion = duracion;
		this.numero_p= numero_p;
		this.oferta = oferta;
		this.cliente = cliente;
		this.numero_hab = numero_hab;
		
	}

	/**
	 * @return El id de la bebida
	 */
	public long getId() 
	{
		return id;
	}

	/**
	 * @param id - El nuevo id de la bebida 
	 */
	public void setId(long id) 
	{
		this.id = id;
	}

	

	/**
	 * @return El id del Tipo de Bebida
	 */
	public Date getFecha() 
	{
		return fecha;
	}

	/**
	 * @param tipoBebida El nuevo identificador de tipo de bebida
	 */
	public void setFecha(Date fecha) 
	{
		this.fecha = fecha;
	}


	public long getDuracion()
	{
		return duracion;
	}


	public void setDuracion(long duracion)
	{
		this.duracion = duracion;
	}


	public long getNumero_p()
	{
		return numero_p;
	}


	public void setNumero_p(long numero_p)
	{
		this.numero_p = numero_p;
	}



	public long getOferta()
	{
		return oferta;
	}


	public void setOferta(long oferta)
	{
		this.oferta = oferta;
	}


	public long getCliente()
	{
		return cliente;
	}


	public void setCliente(long cliente)
	{
		this.cliente = cliente;
	}

	
	
	public long getNumero_hab()
	{
		return numero_hab;
	}


	public void setNumero_hab(long numero_hab)
	{
		this.numero_hab = numero_hab;
	}
	/**
	 * @return El gradoAlcohol de la bebida
	 */
 

	/**
	 * @return Una cadena con la información básica de la bebida
	 */
	@Override
	public String toString() 
	{
		return "Bebida [id=" + id +  ", fecha=" + fecha + ", duracion=" + duracion + ", numero_p=" + numero_p + ", oferta=" + oferta + ", cliente=" + cliente + ", numero_hab=" + numero_hab + "]";
	}

}










