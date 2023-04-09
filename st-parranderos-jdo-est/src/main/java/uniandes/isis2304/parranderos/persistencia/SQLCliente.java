package uniandes.isis2304.parranderos.persistencia;



import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Cliente;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CLIENTE de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author 
 */
class SQLCliente 
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
	public SQLCliente (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un CLIENTE a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param codigo - El código del cliente
	 * @param vinculacion - El tipo de vinculación del cliente
	 * @return El número de tuplas insertadas
	 */
	public long adicionarCliente (PersistenceManager pm, long codigo, String vinculacion)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO CLIENTE (CODIGO, VINCULACION) values (?, ?)");
		q.setParameters(codigo, vinculacion);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN CLIENTE de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param codigo - El código del cliente
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarClientePorCodigo (PersistenceManager pm, long codigo)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM CLIENTE WHERE CODIGO = ?");
		q.setParameters(codigo);
		return (long) q.executeUnique();
	}

    public Cliente darClientePorId (PersistenceManager pm, long CODIGO) 
{
	Query q = pm.newQuery(SQL, "SELECT * FROM CLIENTE WHERE CODIGO = ?");
	q.setResultClass(Cliente.class);
	q.setParameters(CODIGO);
	return (Cliente) q.executeUnique();
}
}

