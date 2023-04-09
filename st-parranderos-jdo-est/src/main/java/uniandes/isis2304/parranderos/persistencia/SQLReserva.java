package uniandes.isis2304.parranderos.persistencia;

import java.sql.Date;


import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Reserva;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto RESERVA de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author 
 */
class SQLReserva 
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
	public SQLReserva (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}

    public long adicionarReserva(PersistenceManager pm, Date fecha_inicio, long duracion, long oferta, long cliente, int numero_p, int costo_total) {
        Query q = pm.newQuery(SQL, "INSERT INTO RESERVA (fecha,duracion, oferta, cliente, numero_p) values (?, ?, ?, ?, ?)");
        q.setParameters(fecha_inicio, duracion, oferta, cliente, numero_p);
        return (long) q.executeUnique();
    }

    public long eliminarReservaPorId (PersistenceManager pm, long idReserva)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM Reserva WHERE id = ?");
        q.setParameters(idReserva);
        return (long) q.executeUnique();
	}

    public Reserva darReservaPorId (PersistenceManager pm, long id) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM Reserva WHERE id = ?");
        q.setResultClass(Reserva.class);
        q.setParameters(id);
        return (Reserva) q.executeUnique();
    }
}
