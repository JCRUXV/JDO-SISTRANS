package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.ReservaColectiva;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto RESERVA de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author 
 */
class SQLReservaColectiva
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
	public SQLReservaColectiva (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}

    public long adicionarReservaColectiva(PersistenceManager pm, Date fecha_inicio, long duracion, long oferta, long cliente, long numero_p, long numero_hab,long costo) {
        Query q = pm.newQuery(SQL, "INSERT INTO RESERVA_COLECTIVA (fecha,duracion, oferta, cliente, numero_p,numero_hab,costo) values (?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(fecha_inicio, duracion, oferta, cliente, numero_p, numero_hab,costo);
        return (long) q.executeUnique();
    }

    public long eliminarReservaColectivaPorId (PersistenceManager pm, long idReserva)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM Reserva_Colectiva WHERE id = ?");
        q.setParameters(idReserva);
        return (long) q.executeUnique();
	}

}

