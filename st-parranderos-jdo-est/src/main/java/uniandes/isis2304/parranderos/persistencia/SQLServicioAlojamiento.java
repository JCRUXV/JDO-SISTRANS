
package uniandes.isis2304.parranderos.persistencia;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.ServicioAlojamiento;


class SQLServicioAlojamiento 
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
	public SQLServicioAlojamiento (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un SERVICIO_ALOJAMIENTO a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param oferta - El id de la oferta a la que pertenece el servicio alojamiento
	 * @param precio - El precio del servicio alojamiento
	 * @param disponibilidad - La disponibilidad del servicio alojamiento
	 * @param servicio - El id del servicio asociado al servicio alojamiento
	 * @return El número de tuplas insertadas
	 */
	public long adicionarServicioAlojamiento (PersistenceManager pm, long oferta, double precio, int disponibilidad, long servicio)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO SERVICIO_ALOJAMIENTO (OFERTA, PRECIO, DISPONIBILIDAD, SERVICIO) values (?, ?, ?, ?)");
		q.setParameters(oferta, precio, disponibilidad, servicio);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN SERVICIO_ALOJAMIENTO de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El id del servicio alojamiento
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarServicioAlojamientoPorId (PersistenceManager pm, long id)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM SERVICIO_ALOJAMIENTO WHERE ID = ?");
		q.setParameters(id);
		return (long) q.executeUnique();
	}

    public ServicioAlojamiento darServicioAlojamientoPorId (PersistenceManager pm, long id) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM SERVICIO_ALOJAMIENTO WHERE ID = ?");
        q.setResultClass(ServicioAlojamiento.class);
        q.setParameters(id);
        return (ServicioAlojamiento) q.executeUnique();
    }


    public List<ServicioAlojamiento> darserviciodeoferta (PersistenceManager pm, long idOferta) 
{
	Query q = pm.newQuery(SQL, "SELECT * FROM SERVICIO_ALOJAMIENTO  WHERE OFERTA = ?");
	q.setResultClass(ServicioAlojamiento.class);
	q.setParameters(idOferta);
	return (List<ServicioAlojamiento>) q.executeList();
}

   

    }



