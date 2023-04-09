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
public class OperadorPersonaNatural implements VOOperadorPersonaNatural
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de los bares
	 */
	private long id;
	
	/**
	 * El nombre del bar
	 */
	private String vinculacion;

	/**


	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public OperadorPersonaNatural() 
    {
    	this.id = 0;
		this.vinculacion = "";
	
	}

	/**
	 * Constructor con valores
	 * @param id - El id del bart
	 * @param nombre - El nombre del bar
	 * @param ciudad - La ciudad del bar
	 * @param presupuesto - El presupuesto del bar (ALTO, MEDIO, BAJO)
	 * @param cantSedes - Las sedes del bar (Mayor que 0)
	 */
    public OperadorPersonaNatural(long id, String vinculacion) 
    {
    	this.id = id;
		this.vinculacion = vinculacion;


	}

    /**
	 * @return El id del bar
	 */
	public long getId() 
	{
		return id;
	}
	
	/**
	 * @param id - El nuevo id del bar
	 */
	public void setId(long id) 
	{
		this.id = id;
	}
	
	/**
	 * @return el nombre del bar
	 */
	public String getVinculacion() 
	{
		return vinculacion;
	}
	
	/**
	 * @param nombre El nuevo nombre del bar
	 */
	public void setVinculacion(String vinculacion) 
	{
		this.vinculacion = vinculacion;
	}
	

	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString() 
	{
		return "Bar [id=" + id + ", vinculacion=" + vinculacion + " ]";
	}
	

}
