package uniandes.isis2304.parranderos.persistencia;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Servicio;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto SERVICIO de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author 
 */
class SQLServicio 
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
	public SQLServicio (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un SERVICIO a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param codigo - El código del servicio
	 * @param nombre - El nombre del servicio
	 * @param descripcion - La descripción del servicio
	 * @param idTipo - El id del tipo de servicio
	 * @param idProveedor - El id del proveedor del servicio
	 * @param capacidad - La capacidad del servicio
	 * @return El número de tuplas insertadas
	 */
	public long adicionarServicio (PersistenceManager pm, long codigo, String nombre, String descripcion)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO SERVICIO (CODIGO, NOMBRE, DESCRIPCION) values (?, ?, ?)");
		q.setParameters(codigo, nombre, descripcion);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN SERVICIO de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param codigo - El código del servicio
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarServicioPorCodigo (PersistenceManager pm, long codigo)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM SERVICIO WHERE CODIGO = ?");
		q.setParameters(codigo);
		return (long) q.executeUnique();
	}

    public Servicio darServicioPorCodigo (PersistenceManager pm, long codigo) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM SERVICIO WHERE CODIGO = ?");
        q.setResultClass(Servicio.class);
        q.setParameters(codigo);
        return (Servicio) q.executeUnique();
    }

    public List<Servicio> darServicios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM  Servicio");
		q.setResultClass(Servicio.class);
		return (List<Servicio>) q.executeList();
	}
}
