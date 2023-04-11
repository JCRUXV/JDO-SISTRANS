package uniandes.isis2304.parranderos.persistencia;


import java.math.BigDecimal;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Operador;



/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto OFERTA de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLOperador 
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
	public SQLOperador (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}

    public long adicionarOperador(PersistenceManager pm, String nombre, String tipo, String disponibilidad) {
        Query q = pm.newQuery(SQL, "INSERT INTO OPERADOR (nombre, tipo, disponibilidad) values (?, ?, ?)");
        q.setParameters(nombre, tipo, disponibilidad);
        return (long) q.executeUnique();
    }

    public long eliminarOPeradorPorId (PersistenceManager pm, long ID)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM Operador WHERE  ID = ?");
        q.setParameters(ID);
        return (long) q.executeUnique();
	}

    public Operador darOperadorPorId (PersistenceManager pm, long id) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM Operador WHERE id = ?");
        q.setResultClass(Operador.class);
        q.setParameters(id);
        return (Operador) q.executeUnique();
    }

    public BigDecimal darId (PersistenceManager pm) 
{
	Query q = pm.newQuery(SQL, "SELECT MAX(id) id FROM Operador ");
	 q.setResultClass(BigDecimal.class);
     return (BigDecimal) q.executeUnique();
}
    

}