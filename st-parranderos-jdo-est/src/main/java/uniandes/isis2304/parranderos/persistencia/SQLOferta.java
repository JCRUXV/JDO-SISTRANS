package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Oferta;



/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto OFERTA de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLOferta 
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
	public SQLOferta (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una OFERTA a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idOferta - El identificador de la oferta
	 * @param costo - El costo de la oferta
	 * @param tiempoDeInicio - La fecha y hora de inicio de la oferta
	 * @param tiempoDeFin - La fecha y hora de fin de la oferta
	 * @param idPlanDeConsumo - El identificador del plan de consumo al que pertenece la oferta
	 * @return El número de tuplas insertadas
	 */
	public long adicionarOferta (PersistenceManager pm, long idOferta, String tipo,long capacidad,String ubicacion ,long vivienda, String disponibilidad , long precio, Date fechainicio, double tiempo, long propietario) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO OFERTA (tipo,capacidad,ubicacion,vivienda,disponibilidad,fecha_inicial,cant_dias,precio,operador) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(tipo,capacidad,ubicacion,vivienda,disponibilidad,fechainicio,tiempo,precio,propietario);
        return (long) q.executeUnique();
	}

	
	public long eliminarOfertasPorPropietario (PersistenceManager pm, long propietario)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM OFERTA WHERE  operador = ?");
        q.setParameters(propietario);
        return (long) q.executeUnique();
	}

	public long eliminarOfertasPorId (PersistenceManager pm, long ID)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM OFERTA WHERE  ID = ?");
        q.setParameters(ID);
        return (long) q.executeUnique();
	}

	
public Oferta darOfertaPorId (PersistenceManager pm, long idOferta) 
{
	Query q = pm.newQuery(SQL, "SELECT * FROM OFERTA WHERE id = ?");
	q.setResultClass(Oferta.class);
	q.setParameters(idOferta);
	return (Oferta) q.executeUnique();
}

public BigDecimal darId (PersistenceManager pm) 
{
	Query q = pm.newQuery(SQL, "SELECT MAX(id) id FROM OFERTA ");
	q.setResultClass(BigDecimal.class);
    return (BigDecimal) q.executeUnique();
}


public void actualizarFechaInicial(PersistenceManager pm, long idOferta, Date nuevaFechaInicial) {
    Query q = pm.newQuery(SQL, "UPDATE OFERTA SET fecha_inicial = ? WHERE id = ?");
    q.setParameters(nuevaFechaInicial, idOferta);
    q.executeUnique();
}

public void actualizarDisponibilidad1(PersistenceManager pm, long idOferta) {
    Query q = pm.newQuery(SQL, "UPDATE OFERTA SET fecha_inicial = ? WHERE id = ?");
    q.setParameters("NO DISPONIBLE", idOferta);
    q.executeUnique();
}

public void actualizarDisponibilidad2(PersistenceManager pm, long idOferta) {
    Query q = pm.newQuery(SQL, "UPDATE OFERTA SET fecha_inicial = ? WHERE id = ?");
    q.setParameters("DISPONIBLE ", idOferta);
    q.executeUnique();
}



public List<Oferta> darOfertas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM OFERTA WHERE DISPONIBILIDAD = ?" );
		q.setResultClass(Oferta.class);
		q.setParameters("DISPONIBLE ");
		return (List<Oferta>) q.executeList();
	}

public List<Oferta> darOfertasServicio (PersistenceManager pm, String servicio)
{
	Query q = pm.newQuery(SQL, "select o.* from oferta o inner join servicio_alojamiento s on o.id = s.oferta inner join servicio sv on s.servicio=sv.codigo\r\n"
			+ "where sv.nombre = ?    " );
	q.setResultClass(Oferta.class);
	q.setParameters(servicio);
	return (List<Oferta>) q.executeList();
}

	public List<Oferta> darOfertasPorPropietario (PersistenceManager pm, long propietario)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM OFERTA WHERE OPERADOR = ?" );
		q.setResultClass(Oferta.class);
		q.setParameters(propietario);
		return (List<Oferta>) q.executeList();
	}

	public List<Oferta> darOfertasPorTipo (PersistenceManager pm, String tipo)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM OFERTA WHERE TIPO = ?" );
		q.setResultClass(Oferta.class);
		q.setParameters(tipo);
		return (List<Oferta>) q.executeList();
	}

	public List<Oferta> darOfertasMasPopulares (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "select id,tipo,ubicacion,vivienda,disponibilidad,precio from (SELECT o.id, o.tipo, o.capacidad, o.ubicacion, o.vivienda, o.disponibilidad, o.precio, COUNT(*) AS num_reservas  FROM OFERTA o JOIN RESERVA r ON o.id = r.oferta GROUP BY o.id, o.tipo, o.capacidad, o.ubicacion, o.vivienda, o.disponibilidad, o.precio ORDER BY num_reservas DESC) where rownum<=20 ");
		q.setResultClass(Oferta.class);
		return (List<Oferta>) q.executeList();
	}


public List<Oferta> darOfertasPorCosto (PersistenceManager pm, double costo) 
{
	Query q = pm.newQuery(SQL, "SELECT * FROM OFERTA WHERE costo = ?");
	q.setResultClass(Oferta.class);
	q.setParameters(costo);
	return (List<Oferta>) q.executeList();
}

/**
 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS TRES PRODUCTOS MÁS POPULARES de la 
 * base de datos de Parranderos, ordenados por número de veces que han sido consumidos en planes de consumo
 * @param pm - El manejador de persistencia
 * @return Una lista de parejas [nombre de producto, número de veces consumido] que representan los productos más populares
 */
public List<Object[]> darDineroRecibidoProcadaProvedor (PersistenceManager pm)
{
	String sql = "select operador,EXTRACT(YEAR FROM reserva.fecha) ANIO ,sum(oferta.precio) CANT from oferta inner join reserva on oferta.id = reserva.oferta "+
	"group by operador,EXTRACT(YEAR FROM reserva.fecha)";
	
	Query q = pm.newQuery(SQL, sql);
	return q.executeList();
}

public List<Object[]> RFC8 (PersistenceManager pm,String tipo)
{
	String sql = "select *  from (select tipo,fecha fecha_max from(SELECT tipo,fecha, count(*) cant FROM oferta inner join reserva on oferta.id = reserva.oferta WHERE tipo = ? GROUP BY tipo,fecha order by count(*) desc ) where rownum = 1)natural join (select tipo, fecha fecha_menos_o from (select tipo,numero_p,fecha from oferta inner join reserva on oferta.id = reserva.oferta group by tipo,numero_p,fecha order by numero_p) where rownum = 1) natural join (select tipo,fecha fecha_ganancia from(SELECT tipo,fecha, sum(precio) ganancia FROM oferta inner join reserva on oferta.id = reserva.oferta WHERE tipo = ? GROUP BY tipo,fecha order by sum(precio) desc ) where rownum = 1)";
	
	Query q = pm.newQuery(SQL, sql);
	q.setParameters(tipo,tipo);
	return q.executeList();
}

public List<Object[]> darIndiceOcupacion (PersistenceManager pm)
{
	String sql = "SELECT o.id, o.ubicacion, COALESCE(AVG(r.numero_p), 0) AS indice_ocupacion "+
	"FROM oferta o LEFT JOIN reserva r ON o.id = r.oferta "+
	"GROUP BY o.id, o.ubicacion, o.capacidad "+
	"ORDER BY indice_ocupacion DESC";
	
	Query q = pm.newQuery(SQL, sql);
	return q.executeList();
}


}


