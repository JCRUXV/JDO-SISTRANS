package uniandes.isis2304.parranderos.persistencia;



import java.sql.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.Consulta;

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

public List<Cliente> obtenerClientesPorOfertaYFechas(PersistenceManager pm, long idOferta, Date fechaInicio, Date fechaFin, String criterioOrdenamiento) {
    String query = "SELECT DISTINCT c.* FROM CLIENTE c "
            + "JOIN RESERVA r ON c.CODIGO = r.CLIENTE "
            + "JOIN OFERTA o ON r.OFERTA = o.ID "
            + "WHERE o.ID = ? AND r.FECHA BETWEEN ? AND ?";
    
    if (criterioOrdenamiento.equalsIgnoreCase("cliente")) {
        query += " ORDER BY c.CODIGO";
    } else if (criterioOrdenamiento.equalsIgnoreCase("oferta")) {
        query += " ORDER BY o.ID";
    } else if (criterioOrdenamiento.equalsIgnoreCase("tipo")) {
        query += " ORDER BY o.TIPO";
    }
    
    Query q = pm.newQuery(SQL, query);
    q.setResultClass(Cliente.class);
    q.setParameters(idOferta, fechaInicio, fechaFin);
    
    return (List<Cliente>) q.executeList();
}


public List<Cliente> obtenerClientesSinReservaPorOfertaYFechas(PersistenceManager pm, long idOferta, Date fechaInicio, Date fechaFin, String criterioOrdenamiento) {
    String query = "SELECT c.* FROM CLIENTE c "
            + "WHERE c.CODIGO NOT IN "
            + "(SELECT DISTINCT r.CLIENTE FROM RESERVA r "
            + "JOIN OFERTA o ON r.OFERTA = o.ID "
            + "WHERE o.ID = ? AND r.FECHA BETWEEN ? AND ?)";
    
    if (criterioOrdenamiento.equalsIgnoreCase("cliente")) {
        query += " ORDER BY c.CODIGO";
    } else if (criterioOrdenamiento.equalsIgnoreCase("oferta")) {
        query += " ORDER BY ?";
    } else if (criterioOrdenamiento.equalsIgnoreCase("tipo")) {
        query += " ORDER BY ?";
    }
    
    Query q = pm.newQuery(SQL, query);
    q.setResultClass(Cliente.class);
    q.setParameters(idOferta, fechaInicio, fechaFin);
    
    return (List<Cliente>) q.executeList();
}



public List<Cliente> RFC9 (PersistenceManager pm)
{
	String sql = "select codigo,vinculacion from (select codigo,count(*)cant from cliente inner join reserva on  cliente.codigo = reserva.cliente group by codigo having count(*)>=3) natural inner join cliente";
	Query q = pm.newQuery(SQL, sql);
	return (List<Cliente>) q.executeList();
}

public List<Cliente> RFC13 (PersistenceManager pm)
{
	String sql = "SELECT c.CODIGO AS CLIENTE_CODIGO, c.VINCULACION AS VINCULACION_CLIENTE FROM CLIENTE c JOIN RESERVA r ON c.CODIGO = r.CLIENTE JOIN OFERTA o ON r.OFERTA = o.ID WHERE EXISTS ( SELECT 1 FROM RESERVA r2 WHERE r2.CLIENTE = c.CODIGO GROUP BY TO_CHAR(r2.FECHA, 'YYYY-MM') HAVING COUNT(*) >= 1 ) AND EXISTS ( SELECT 1    FROM OFERTA o2    WHERE o2.ID = r.OFERTA AND o2.PRECIO > 150 ) AND o.TIPO = 'Suit'";
	Query q = pm.newQuery(SQL, sql);
	return (List<Cliente>) q.executeList();
}

public long Uso (PersistenceManager pm, long CODIGO) 
{
	Query q = pm.newQuery(SQL, "SELECT COUNT(*) cant FROM RESERVA WHERE CLIENTE = ?");
	q.setResultClass(long.class);
	q.setParameters(CODIGO);
	return (long) q.executeUnique();
}



public List<Consulta> consultaRFC12_3(PersistenceManager pm) {
    String sql = "SELECT TO_CHAR(r.FECHA, 'IW') AS SEMANA, o.OPERADOR AS OPERADOR, COUNT(*) AS SOLICITUDES " +
                 "FROM RESERVA r JOIN OFERTA o ON r.OFERTA = o.ID " +
                 "GROUP BY TO_CHAR(r.FECHA, 'IW'), o.OPERADOR, o.TIPO, o.CAPACIDAD, o.UBICACION " +
                 "HAVING COUNT(*) = (" +
                 "    SELECT MAX(SOLICITUDES) " +
                 "    FROM (" +
                 "        SELECT TO_CHAR(FECHA, 'IW') AS SEMANA, OPERADOR, COUNT(*) AS SOLICITUDES " +
                 "        FROM RESERVA " +
                 "        GROUP BY TO_CHAR(FECHA, 'IW'), OPERADOR" +
                 "    )" +
                 ")";
                 
    Query q = pm.newQuery(sql);
    q.setResultClass(Consulta.class);
    
    return (List<Consulta>) q.executeList();
}



public List<Consulta> consultaRFC12_4(PersistenceManager pm) {
    String sql = "SELECT TO_CHAR(r.FECHA, 'IW') AS SEMANA, o.OPERADOR AS OPERADOR, COUNT(*) AS SOLICITUDES " +
                 "FROM RESERVA r JOIN OFERTA o ON r.OFERTA = o.ID " +
                 "GROUP BY TO_CHAR(r.FECHA, 'IW'), o.OPERADOR " +
                 "HAVING COUNT(*) = (" +
                 "    SELECT MIN(SOLICITUDES) " +
                 "    FROM (" +
                 "        SELECT TO_CHAR(FECHA, 'IW') AS SEMANA, OPERADOR, COUNT(*) AS SOLICITUDES " +
                 "        FROM RESERVA " +
                 "        GROUP BY TO_CHAR(FECHA, 'IW'), OPERADOR" +
                 "    )" +
                 ")";
                 
    Query q = pm.newQuery(sql);
    q.setResultClass(Consulta.class);
    
    return (List<Consulta>) q.executeList();
}


}

