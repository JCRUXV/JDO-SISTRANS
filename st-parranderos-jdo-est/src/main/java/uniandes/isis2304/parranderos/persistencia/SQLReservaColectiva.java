package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Reserva;

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

    public long adicionarReservaColectiva(PersistenceManager pm, Date fecha_inicio, long duracion, long oferta, long cliente, int numero_p, long numero_hab) {
        Query q = pm.newQuery(SQL, "INSERT INTO RESERVA_COLECTIVA (fecha,duracion, oferta, cliente, numero_p,numero,numero_hab) values (?, ?, ?, ?, ?, ?)");
        q.setParameters(fecha_inicio, duracion, oferta, cliente, numero_p, numero_hab);
        return (long) q.executeUnique();
    }

    public long eliminarReservaColectivaPorId (PersistenceManager pm, long idReserva)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM Reserva_Colectiva WHERE id = ?");
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

	

    public BigDecimal darId (PersistenceManager pm) 
    {
        Query q = pm.newQuery(SQL, "SELECT MAX(id) id FROM Reserva ");
        q.setResultClass(BigDecimal.class);
        return (BigDecimal) q.executeUnique();
    }

    public List<Reserva> darReservasPorCliente (PersistenceManager pm, long cliente)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM OFERTA WHERE Cliente = ?" );
		q.setResultClass(Reserva.class);
		q.setParameters(cliente);
		return (List<Reserva>) q.executeList();
	}

	public List<Reserva> darReservasPorOferta (PersistenceManager pm, long oferta)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM OFERTA WHERE Cliente = ?" );
		q.setResultClass(Reserva.class);
		q.setParameters(oferta);
		return (List<Reserva>) q.executeList();
	}
}

