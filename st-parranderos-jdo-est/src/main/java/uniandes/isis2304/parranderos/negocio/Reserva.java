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

import java.sql.Timestamp;

/**
 * Clase para modelar el concepto BEBIDA del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class Reserva implements VOReserva
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
	private Timestamp fecha;

	
	
	/**
	 * El grado de alcohol de la bebida (Mayor que 0)
	 */
	private int duracion;


	private int numero_p;


	private int oferta;


	private int cliente;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Reserva() 
	{
		this.id = 0;
		this.fecha = new Timestamp(0);
		this.duracion = 0;
		this.numero_p = 0;
		this.oferta=0;
		this.cliente=0;

	}

	/**
	 * Constructor con valores
	 * @param id - El id de la bebida
	 * @param nombre - El nombre de la bebida
	 * @param tipo - El identificador del tipo de bebida
	 * @param gradoAlcohol - El graddo de alcohol de la bebida (Mayor que 0)
	 */
	public Reserva(long id, long costo, Timestamp fecha, int duracion, int numero_p, int oferta, int cliente) 
	{
		this.id = id;
		this.fecha = fecha;
		this.duracion = duracion;
		this.numero_p= numero_p;
		this.oferta = oferta;
		this.cliente = oferta;
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
	public Timestamp getFecha() 
	{
		return fecha;
	}

	/**
	 * @param tipoBebida El nuevo identificador de tipo de bebida
	 */
	public void setFecha(Timestamp fecha) 
	{
		this.fecha = fecha;
	}


	public int getDuracion()
	{
		return duracion;
	}


	public void setDuracion(int duracion)
	{
		this.duracion = duracion;
	}


	public int getNumero_p()
	{
		return numero_p;
	}


	public void setNumero_p(int numero_p)
	{
		this.numero_p = numero_p;
	}



	public int getOferta()
	{
		return oferta;
	}


	public void setOferta(int oferta)
	{
		this.oferta = oferta;
	}


	public int getCliente()
	{
		return cliente;
	}


	public void setCliente(int cliente)
	{
		this.cliente = cliente;
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
		return "Bebida [id=" + id +  ", fecha=" + fecha + ", duracion=" + duracion + ", numero_p=" + numero_p + ", oferta=" + oferta + ", cliente=" + cliente + "]";
	}

}
