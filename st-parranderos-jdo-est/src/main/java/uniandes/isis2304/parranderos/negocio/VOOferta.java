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
 * Interfaz para los métodos get de BEBEDOR.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Germán Bravo
 */
public interface VOOferta 
{
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * @return El id del bebedor
	 */
	public long getId();

	/**
	 * @return El nombre del bebedor
	 */
	public String getTipo();

	/**
	 * @return La ciudad del bebedor
	 */
	public long getCapacidad();

	/**
	 * @return El presupuesto del bebedor
	 */
	public String getUbicacion();
	
	
	public long getVivienda();
	
	
	public String getDisponibilidad();
	
	
	public Timestamp getFecha_inicial();
	
	
	public long getCant_dias();
	
	
	public long getPrecio();
	
	
	public long getOperador();
	
	
	
	
	
	
	

	/**
	 * @return La lista de visitasRealizadas por el bebedor 
	 */
	

	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString();

	/**
	 * @return Una cadena de caracteres con la información COMPLEtA del bebedor.
	 * Además de la información básica, contiene las visitas realizadas (una por línea) y 
	 * las bebidas que le gustan al bebedor (una por línea)
	 */
	
}
