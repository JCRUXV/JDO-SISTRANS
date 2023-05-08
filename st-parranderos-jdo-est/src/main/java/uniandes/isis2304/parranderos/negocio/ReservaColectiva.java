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
	

	



	private long  cliente;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public ReservaColectiva() 
	{
		this.id = 0;

		this.cliente=0;

	}

	/**
	 * Constructor con valores
	 * @param id - El id de la bebida
	 * @param nombre - El nombre de la bebida
	 * @param tipo - El identificador del tipo de bebida
	 * @param gradoAlcohol - El graddo de alcohol de la bebida (Mayor que 0)
	 */
	public ReservaColectiva(long id, long cliente) 
	{
		this.id = id;

		this.cliente = cliente;
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
















	public long getCliente()
	{
		return cliente;
	}


	public void setCliente(long cliente)
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
		return "Bebida [id=" + id +  ",  cliente=" + cliente + "]";
	}

}
