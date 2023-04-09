package uniandes.isis2304.parranderos.persistencia;



import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.OperadorPersonaNatural;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto OPERADOR_PN de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 */
class SQLOperadorPN 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaParranderos.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaParranderos pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLOperadorPN (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un OPERADOR_PN a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del operador
	 * @param nombre - El nombre del operador
	 * @param correo - El correo electrónico del operador
	 * @param tipo - El tipo de operador
	 * @param registro - La fecha de registro del operador
	 * @return El número de tuplas insertadas
	 */
	public long adicionarOperadorPN (PersistenceManager pm, long id, String vinculacion)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO OPERADOR_PN (ID, VINCULACION) values (?, ?)");
		q.setParameters(id, vinculacion);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN OPERADOR_PN de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del operador
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarOperadorPNPorId (PersistenceManager pm, long id)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM OPERADOR_PN WHERE ID = ?");
		q.setParameters(id);
		return (long) q.executeUnique();
	}

    public OperadorPersonaNatural darOperadorPNPorId (PersistenceManager pm, long id) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM OPERADOR_PN WHERE id = ?");
        q.setResultClass(OperadorPersonaNatural.class);
        q.setParameters(id);
        return (OperadorPersonaNatural) q.executeUnique();
    }

}
