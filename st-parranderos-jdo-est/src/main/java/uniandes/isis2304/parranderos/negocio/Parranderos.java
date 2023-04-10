/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;
import uniandes.isis2304.parranderos.persistencia.PersistenciaParranderos;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 * @author Germán Bravo
 */
public class Parranderos 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(Parranderos.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaParranderos pp;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public Parranderos ()
	{
		pp = PersistenciaParranderos.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public Parranderos (JsonObject tableConfig)
	{
		pp = PersistenciaParranderos.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los CLIENTES
	 *****************************************************************/
	
	public Cliente adicionarCliente (long codigo, String vinculacion)
	{
        log.info ("Adicionando Cliente con codigo : " + codigo);
        Cliente Cliente = pp.adicionarCliente(codigo,vinculacion);		
        log.info ("Adicionando Cliente con codigo : " + codigo);
        return Cliente;
	}
	
	
	public long eliminarClientePorCodigo (long codigo)
	{
		log.info ("Eliminando Cliente por codigo: " + codigo);
        long resp = pp.eliminarClientePorCodigo(codigo);		
        log.info ("Eliminando Cliente por codigo: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	
	public Cliente darClientePorCodigo (long codigo)
	{
		log.info ("Buscando cliente con codigo: " + codigo);
		Cliente tb = pp.darClientePorCodigo(codigo);
		log.info ("Buscando cliente por codigo: " + tb != null ? tb : "NO EXISTE");
        return tb;
	}

	/* ****************************************************************
	 * 			Métodos para manejar las Ofertas
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una bebida 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre la bebida
	 * @param idTipoBebida - El identificador del tipo de bebida de la bebida - Debe existir un TIPOBEBIDA con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la bebida (Mayor que 0)
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public Oferta adicionarOferta (String tipo, long capacidad,String ubicacion,long vivienda, String disponibilidad,Date fecha,long cant_dias,long precio, long operador) 
	{
		log.info ("Adicionando oferta " + tipo);
		Oferta oferta = pp.adicionarOferta(tipo, capacidad, ubicacion, vivienda, disponibilidad, fecha, cant_dias, precio, operador);
        log.info ("Adicionando oferta: " + tipo);
        return oferta;
	}
	
	
	public long eliminarOfertasPorPropietario (long propietario)
	{
        log.info ("Eliminando oferta de operador : " + propietario);
        long resp = pp.eliminarOfertasPorPropietario(propietario);
        log.info ("Eliminando ofertas de deoperador: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	
	public long eliminarOfertaPorId (long id)
	{
        log.info ("Eliminando oferta por id: " + id);
        long resp = pp.eliminarOfertaPorId(id);
        log.info ("Eliminando oferta por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	
	public List<Oferta> darOfertas ()
	{
        log.info ("Consultando Ofertas");
        List<Oferta> bebidas = pp.darOfertas();	
        log.info ("Consultando Ofertas: " + bebidas.size() + " ofertas existentes");
        return bebidas;
	}

	
	public List<VOOferta> darVOOfertas ()
	{
		log.info ("Generando los VO de las ofertas");       
        List<VOOferta> voBebidas = new LinkedList<VOOferta> ();
        for (Oferta beb : pp.darOfertas())
        {
        	voBebidas.add (beb);
        }
        log.info ("Generando los VO de las ofertas disponibles: " + voBebidas.size() + " existentes");
        return voBebidas;
	}

	public List<Oferta> darOfertasPorCosto (long costo)
	{
        log.info ("Consultando Ofertas por costo");
        List<Oferta> bebidas = pp.darOfertasPorCosto(costo);	
        log.info ("Consultando Ofertas: " + bebidas.size() + " ofertas existentes");
        return bebidas;
	}

	public List<Oferta> darOfertasPorPropietario (long propietario)
	{
        log.info ("Consultando Ofertas por propietario");
        List<Oferta> bebidas = pp.darOfertasPorPropietario(propietario);	
        log.info ("Consultando Ofertas: " + bebidas.size() + " ofertas existentes");
        return bebidas;
	}

	public List<Oferta> darOfertasPopulares ()
	{
        log.info ("Consultando Ofertas por costo");
        List<Oferta> bebidas = pp.darOfertasPopulares();
        log.info ("Consultando Ofertas: " + bebidas.size() + " ofertas existentes");
        return bebidas;
	}

	public Oferta darOfertaPorId (long id)
	{
		log.info ("Buscando oferta con id: " + id);
		Oferta tb = pp.darOfertaPorid(id);
		log.info ("Buscando oferta por id: " + tb != null ? tb : "NO EXISTE");
        return tb;
	}

	public List<Object []> darDineroPropietario ()
	{
        log.info ("Dinero recaudado por cada propietario");
        List<Object []> tuplas = pp.dardineroPropietario();
        log.info ("Dinero recaudado por cada propietario");
        return tuplas;
	}

	public List<Object []> darIndiceOcupacion ()
	{
        log.info ("Indice de Ocupacion de cada Oferta");
        List<Object []> tuplas = pp.darIndiceOcupacion();
        log.info ("Indice de Ocupacion de cada Oferta");
        return tuplas;
	}

	public void cambiarFechaOferta (Date fecha, long id)
	{
        log.info ("cambiar fecha  de oferta: " + id);
        pp.actulizarfechaOferta(id, fecha);
        
		}

		public void cambiardisponibilidad1 ( long id)
		{
			log.info ("cambiar disponibilidad  de oferta: " + id);
			pp.actulizarDisponibilidad1(id);
			
			}

			public void cambiardisponibilidad2 ( long id)
			{
				log.info ("cambiar disponibilidad  de oferta: " + id);
				pp.actulizarDisponibilidad2(id);
				
				}

	/* ****************************************************************
	 * 			Métodos para manejar los Operadores
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un bebedor 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @param ciudad - La ciudad del bebedor
	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
	 */
	public Operador adicionarOperador (String nombre, String tipo, String disponibilidad) 
	{
        log.info ("Adicionando operador: " + nombre);
        Operador bebedor = pp.adicionarOperador(nombre, tipo, disponibilidad);
        log.info ("Adicionando operador: " + bebedor);
        return bebedor;
	}

	

	public long eliminarOperadorPorId (long idOperador)
	{
        log.info ("Eliminando operador por id: " + idOperador);
        long resp = pp.eliminarOperadorPorId(idOperador);
        log.info ("Eliminando operador por Id: " + resp + " tuplas eliminadas");
        return resp;
	}

	/**
	 * Encuentra un bebedor y su información básica, según su identificador
	 * @param idBebedor - El identificador del bebedor buscado
	 * @return Un objeto Bebedor que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un bebedor con dicho identificador no existe
	 */
	public Operador darOperadorPorId (long idOperador)
	{
        log.info ("Dar información de un operador por id: " + idOperador);
        Operador bebedor = pp.darOperadorPorId(idOperador);
        log.info ("Buscando operador por Id: " + bebedor != null ? bebedor : "NO EXISTE");
        return bebedor;
	}

	

	/* ****************************************************************
	 * 			Métodos para manejar los Operador Persona Natural 
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un bar 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bar
	 * @param presupuesto - El presupuesto del bar (ALTO, MEDIO, BAJO)
	 * @param ciudad - La ciudad del bar
	 * @param sedes - El número de sedes que tiene el bar en la ciudad (Mayor que 0)
	 * @return El objeto Bar adicionado. null si ocurre alguna Excepción
	 */
	public OperadorPersonaNatural adicionarOperadorPN (String vinculacion, long id)
	{
        log.info ("Adicionando Operador PN : " + vinculacion);
        OperadorPersonaNatural bar = pp.adicionarOperadorPN(vinculacion, id);
        log.info ("Adicionando Operador: " + bar);
        return bar;
	}
	
	/**
	 * Elimina un bar por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bar a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarOperadorPNPorId (long id)
	{
        log.info ("Eliminando operador persona natural por id: " + id);
        long resp = pp.eliminarOperadorPNPorid(id);
        log.info ("Eliminando operador persona natural: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	public OperadorPersonaNatural darOperadorPNPorId (long id)
	{
		log.info ("Buscando oferta con id: " + id);
		OperadorPersonaNatural tb = pp.darOperadorPnPorId(id);
		log.info ("Buscando oferta por id: " + tb != null ? tb : "NO EXISTE");
        return tb;
	}
	
	

	
	
	
	
	/* ****************************************************************
	 * 			Métodos para manejar la relación RESERVA
	 *****************************************************************/

	
	public Reserva adicionarReserva (Date fecha, long duracion, int numero_p, long oferta, long cliente) 
	{
        log.info ("Adicionando Reserva [" + oferta + ", " + cliente + "]");
        Reserva resp = pp.adicionarReserva(fecha, duracion, numero_p, oferta, cliente);
        log.info ("Adicionando Reserva: " + resp + " tuplas insertadas");
        return resp;
	}
	
	/**
	 * Elimina de manera persistente una preferencia de una bebida por un bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarReservaPorId (long id)
	{
        log.info ("Eliminando reserva");
        long resp = pp.eliminarReservaPorId(id);
        log.info ("Eliminando reserva: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los gustan en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Gustan con todos los GUSTAN que conoce la aplicación, llenos con su información básica
	 */
	public List<Reserva> darReservasPorCliente (long cliente)
	{
        log.info ("lista de reservas del cliente");
        List<Reserva> gustan = pp.darReservasPorCliente(cliente);
        log.info ("Listando reservas: " + gustan.size() );
        return gustan;
	}

	public Reserva darReservaPorId (long id)
	{
		log.info ("Buscando reserva con id: " + id);
		Reserva tb = pp.darReservasPorid(id);
		log.info ("Buscando reserva por id: " + tb != null ? tb : "NO EXISTE");
        return tb;
	}

	/* ****************************************************************
	 * 			Métodos para manejar la relación Servicios
	 *****************************************************************/

	
	public Servicio adicionarServicio (String nombre, String descripcion)
	{
        log.info ("Adicionando servicio"+ nombre);
        Servicio resp = pp.adicionarServicio(nombre, descripcion);
        log.info ("Adicionando servicio: " + resp + " tuplas insertadas");
        return resp;
	}
	
	/**
	 * Elimina de manera persistente el hecho que una bebida es servida por un bar
	 * Adiciona entradas al log de la aplicación
	 * @param idBar - El identificador del bar
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarSirven (long idBar, long idBebida)
	{
        log.info ("Eliminando sirven");
        long resp = pp.eliminarSirven (idBar, idBebida);
        log.info ("Eliminando sirven: " + resp + "tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los SIRVEN en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos SIRVEN con todos los GUSTAN que conoce la aplicación, llenos con su información básica
	 */
	public List<Sirven> darSirven ()
	{
        log.info ("Listando Sirven");
        List<Sirven> sirven = pp.darSirven ();	
        log.info ("Listando Sirven: " + sirven.size() + " sirven existentes");
        return sirven;
	}

	/**
	 * Encuentra todos los sirven en Parranderos y los devuelve como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos SIRVEN con todos los SIRVEN que conoce la aplicación, llenos con su información básica
	 */
	public List<VOSirven> darVOSirven ()
	{
		log.info ("Generando los VO de Sirven");
		List<VOSirven> voGustan = new LinkedList<VOSirven> ();
		for (VOSirven sirven: pp.darSirven ())
		{
			voGustan.add (sirven);
		}
		log.info ("Generando los VO de Sirven: " + voGustan.size () + " Sirven existentes");
		return voGustan;
	}

	/* ****************************************************************
	 * 			Métodos para manejar la relación VISITAN
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente el hecho que un bebedor visita un bar
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @param idBar - El identificador del bar
	 * @param fecha - La fecha en la que se realizó la visita
	 * @param horario - El horario en el que se sirve la bebida (DIURNO, NOCTURNO, TODOS)
	 * @return Un objeto Visitan con los valores dados
	 */
	public Visitan adicionarVisitan (long idBebedor, long idBar, Timestamp fecha, String horario)
	{
        log.info ("Adicionando visitan [" + idBebedor + ", " + idBar + "]");
        Visitan resp = pp.adicionarVisitan (idBebedor, idBar, fecha, horario);
        log.info ("Adicionando visitan: " + resp + " tuplas insertadas");
        return resp;
	}
	
	/**
	 * Elimina de manera persistente el hecho que un bebedor visita un bar
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @param idBar - El identificador del bar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarVisitan (long idBebedor, long idBar)
	{
        log.info ("Eliminando visitan");
        long resp = pp.eliminarVisitan (idBebedor, idBar);
        log.info ("Eliminando visitan: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los VISITAN en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VISITAN con todos los GUSTAN que conoce la aplicación, llenos con su información básica
	 */
	public List<Visitan> darVisitan ()
	{
        log.info ("Listando Visitan");
        List<Visitan> visitan = pp.darVisitan ();	
        log.info ("Listando Visitan: Listo!");
        return visitan;
	}

	/**
	 * Encuentra todos los visitan en Parranderos y los devuelve como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Visitan con todos los Visitan que conoce la aplicación, llenos con su información básica
	 */
	public List<VOVisitan> darVOVisitan ()
	{
		log.info ("Generando los VO de Visitan");
		List<VOVisitan> voGustan = new LinkedList<VOVisitan> ();
		for (VOVisitan vis: pp.darVisitan ())
		{
			voGustan.add (vis);
		}
		log.info ("Generando los VO de Visitan: " + voGustan.size () + " Visitan existentes");
		return voGustan;
	}

	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarParranderos ()
	{
        log.info ("Limpiando la BD de Parranderos");
        long [] borrrados = pp.limpiarParranderos();	
        log.info ("Limpiando la BD de Parranderos: Listo!");
        return borrrados;
	}
}
