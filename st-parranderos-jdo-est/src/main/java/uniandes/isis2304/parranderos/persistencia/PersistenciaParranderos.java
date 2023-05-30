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

package uniandes.isis2304.parranderos.persistencia;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.Consulta;
import uniandes.isis2304.parranderos.negocio.Oferta;
import uniandes.isis2304.parranderos.negocio.OfertaAlojamiento;
import uniandes.isis2304.parranderos.negocio.Operador;
import uniandes.isis2304.parranderos.negocio.OperadorPersonaNatural;
import uniandes.isis2304.parranderos.negocio.Parranderos;
import uniandes.isis2304.parranderos.negocio.Reserva;
import uniandes.isis2304.parranderos.negocio.Servicio;
import uniandes.isis2304.parranderos.negocio.ServicioAlojamiento;
import uniandes.isis2304.parranderos.negocio.ReservaColectiva;
/**
 * Clase para el manejador de persistencia del proyecto Parranderos
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * Se apoya en las clases SQLBar, SQLBebedor, SQLBebida, SQLGustan, SQLSirven, SQLTipoBebida y SQLVisitan, que son 
 * las que realizan el acceso a la base de datos
 * 
 * @author Germán Bravo
 */
public class PersistenciaParranderos 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaParranderos.class.getName());
	
	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaParranderos instance;
	
	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;
	
	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, tipoBebida, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;
	
	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaParranderos
	 */
	private SQLUtil sqlUtil;
	
	/**
	 * Atributo para el acceso a la tabla TIPOBEBIDA de la base de datos
	 */
	private SQLCliente sqlCliente;
	
	/**
	 * Atributo para el acceso a la tabla BEBIDA de la base de datos
	 */
	private SQLOperador sqlOperador;
	
	/**
	 * Atributo para el acceso a la tabla BAR de la base de datos
	 */
	private SQLOferta sqlOferta;
	
	/**
	 * Atributo para el acceso a la tabla BEBIDA de la base de datos
	 */
	private SQLOperadorPN sqlOperadorpn;
	
	/**
	 * Atributo para el acceso a la tabla GUSTAN de la base de datos
	 */
	private SQLReserva sqlReserva;
	
	/**
	 * Atributo para el acceso a la tabla SIRVEN de la base de datos
	 */
	private SQLServicio sqlServicio;
	
	/**
	 * Atributo para el acceso a la tabla VISITAN de la base de datos
	 */
	private SQLServicioAlojamiento sqlServicioAlojamiento;
	
	
	private SQLReservaColectiva sqlReservaColectiva;
	
	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaParranderos ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Parranderos");		
		crearClasesSQL ();
		
		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("CLIENTE");
		tablas.add ("OFERTA");
		tablas.add ("OPERADOR");
		tablas.add ("OPERADOR_PN");
		tablas.add ("RESERVA");
		tablas.add ("SERVICIO");
		tablas.add ("SERVICIO_ALOJAMIENTO");
		tablas.add ("RESERVA_COLECTIVA");
}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaParranderos (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);
		System.out.println(tablas);
		
		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaParranderos getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaParranderos ();
		}
		return instance;
	}
	
	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaParranderos getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaParranderos (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}
	
	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}
		
		return resp;
	}
	
	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		this.sqlCliente = new SQLCliente(this);
		this.sqlOperador = new SQLOperador(this);
		sqlOferta = new SQLOferta(this);
		this.sqlOperadorpn = new SQLOperadorPN(this);
		this.sqlReserva = new SQLReserva(this);
		this.sqlServicio = new SQLServicio (this);
		this.sqlServicioAlojamiento = new SQLServicioAlojamiento(this);	
		this.sqlReservaColectiva = new SQLReservaColectiva(this);
		sqlUtil = new SQLUtil(this);
	}

	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de TipoBebida de parranderos
	 */
	public String darTablaCliente ()
	{
		return tablas.get (0);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebida de parranderos
	 */
	public String darTablaOferta ()
	{
		return tablas.get (1);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bar de parranderos
	 */
	public String darTablaOperador ()
	{
		return tablas.get (2);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebedor de parranderos
	 */
	public String darTablaOperadorPN ()
	{
		return tablas.get (3);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Gustan de parranderos
	 */
	public String darTablaReserva ()
	{
		return tablas.get (4);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Sirven de parranderos
	 */
	public String darTablaServicio ()
	{
		return tablas.get (5);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaServicioAlojamiento ()
	{
		return tablas.get (6);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaReservaColectiva ()
	{
		return tablas.get (7);
	}
	
	
	
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los clientes
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla TipoBebida
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepción
	 */
	public Cliente adicionarCliente(long codigo, String vinculacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = this.sqlCliente.adicionarCliente(pm, codigo, vinculacion);
            tx.commit();
            
            log.trace ("Inserción de cliente: " + codigo + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Cliente(codigo,vinculacion);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla TipoBebida, dado el nombre del tipo de bebida
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarClientePorCodigo (long codigo) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = this.sqlCliente.eliminarClientePorCodigo(pm, codigo);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
 
	public Cliente darClientePorCodigo (long codigo)
	{
		return this.sqlCliente.darClientePorId(pmf.getPersistenceManager(), codigo);
	}

	public long darMaxIdOferta ()
	{
		return (long) this.sqlOferta.darId(pmf.getPersistenceManager()).toBigInteger().intValueExact();
	}
 
	/* ****************************************************************
	 * 			Métodos para manejar las ofertas
	 *****************************************************************/
	
	public Oferta adicionarOferta(String tipo, long capacidad,String ubicacion,long vivienda, String disponibilidad,Date fecha,long cant_dias,long precio, long operador) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();            
            long tuplasInsertadas = this.sqlOferta.adicionarOferta(pm, cant_dias, tipo, capacidad, ubicacion, vivienda, disponibilidad, precio, fecha, precio, operador);
            tx.commit();
            
            log.trace ("Inserción oferta: " + tipo + ": " + tuplasInsertadas + " tuplas insertadas");
			
            return new Oferta(this.darMaxIdOferta(), tipo, capacidad, ubicacion, vivienda, disponibilidad,fecha, cant_dias, precio, operador);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Bebida, dado el nombre de la bebida
	 * Adiciona entradas al log de la aplicación
	 * @param nombreBebida - El nombre de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarOfertasPorPropietario (long propietario) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = this.sqlOferta.eliminarOfertasPorPropietario(pm, propietario);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Bebida, dado el identificador de la bebida
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarOfertaPorId (long idOferta) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = this.sqlOferta.eliminarOfertasPorId(pm, idOferta);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public String RF9 (long id){
		Oferta oferta = this.darOfertaPorid(id);
		this.actulizarDisponibilidad1(id);
		List<Oferta> ofertas =this.darOfertaPorTipo(oferta.getTipo());
		List<Reserva> reservas = this.darReservasPorOferta(id);
		for (int i=0;i<reservas.size();i++){
		Reserva reserva = reservas.get(i);
		this.eliminarReservaPorId(reserva.getId());
		
		if(ofertas.size()>= reservas.size() ){
			LocalDateTime localDateTime = LocalDateTime.parse("2019-11-15T13:15:30");
			Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
			java.util.Date date = Date.from(instant);
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			this.adicionarReserva(sqlDate,reserva.getDuracion(),(int)reserva.getNumero_p(),ofertas.get(i).getId(),reserva.getCliente());
		}
		else{
			return "no hay oferta parecida";
		}
		}
		
		return "se logro exitosamente";
	}

	public void RF10 (long id){
		this.actulizarDisponibilidad2(id);
	}

	public List<Cliente> RFC9 ()
	{
		return this.sqlCliente.RFC9(pmf.getPersistenceManager());
	}

	public List<Cliente> RFC13 ()
	{
		return this.sqlCliente.RFC13(pmf.getPersistenceManager());
	}

	public List<Consulta> RFC12_3 ()
	{
		return this.sqlCliente.consultaRFC12_3(pmf.getPersistenceManager());
	}

	public List<Consulta> RFC12_4 ()
	{
		return this.sqlCliente.consultaRFC12_4(pmf.getPersistenceManager());
	}

	public List<Cliente> clientesofertasyfecha (long oferta,Date fechainc, Date fechafin,String criterio)
	{
		return this.sqlCliente.obtenerClientesPorOfertaYFechas(pmf.getPersistenceManager(),oferta,fechainc,fechafin,criterio);
	}

	public List<Cliente> clientesnoofertasyfecha (long oferta,Date fechainc, Date fechafin,String criterio)
	{
		return this.sqlCliente.obtenerClientesSinReservaPorOfertaYFechas(pmf.getPersistenceManager(),oferta,fechainc,fechafin,criterio);
	}
 
	/**
	 * Método que consulta todas las tuplas en la tabla Bebida
	 * @return La lista de objetos Bebida, construidos con base en las tuplas de la tabla BEBIDA
	 */
	public List<Oferta> darOfertas ()
	{
		return this.sqlOferta.darOfertas(pmf.getPersistenceManager());
	}

	public List<OfertaAlojamiento> RFC12_1 ()
	{
		return this.sqlOferta.consultaRFC12_1(pmf.getPersistenceManager());
	}

	public List<OfertaAlojamiento> RFC12_2 ()
	{
		return this.sqlOferta.consultaRFC12_2(pmf.getPersistenceManager());
	}

	public List<Oferta> RFC10 (String duracion)
	{
		return this.sqlOferta.RF10(pmf.getPersistenceManager(),duracion);
	}
	
	public List<Oferta> darOfertasServicio (String servicio)
	{
		return this.sqlOferta.darOfertasServicio(pmf.getPersistenceManager(), servicio);
	}

	public List<Oferta> darOfertasPorCosto (long costo)
	{
		return this.sqlOferta.darOfertasPorCosto(pmf.getPersistenceManager(), costo);
	}

	public List<Object[]> dardineroPropietario ()
	{
		return this.sqlOferta.darDineroRecibidoProcadaProvedor(pmf.getPersistenceManager());
	}

	public List<Object[]> darIndiceOcupacion ()
	{
		return this.sqlOferta.darIndiceOcupacion(pmf.getPersistenceManager());
	}

	public List<Object[]> RFC8 (String tipo)
	{
		return this.sqlOferta.RFC8(pmf.getPersistenceManager(),tipo);
	}

	public List<Oferta> darOfertasPopulares ()
	{
		return this.sqlOferta.darOfertasMasPopulares(pmf.getPersistenceManager());
	}

	public Oferta darOfertaPorid (long id)
	{
		return this.sqlOferta.darOfertaPorId(pmf.getPersistenceManager(), id);
	}

	public List<Oferta>  darOfertaPorTipo (String tipo)
	{
		return this.sqlOferta.darOfertasPorTipo(pmf.getPersistenceManager(), tipo);
	}

	public List<Oferta> darOfertasPorPropietario( long id )
	{
		return this.sqlOferta.darOfertasPorPropietario(pmf.getPersistenceManager(), id);
	}

	public void actulizarfechaOferta (long id, Date fecha ) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            this.sqlOferta.actualizarFechaInicial(pm, id, fecha);
            tx.commit();

            
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
           
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public void actulizarDisponibilidad1 (long id ) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            this.sqlOferta.actualizarDisponibilidad1(pm, id);
            tx.commit();

            
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
           
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
 


	public void actulizarDisponibilidad2 (long id ) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            this.sqlOferta.actualizarDisponibilidad2(pm, id);
            tx.commit();

            
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
           
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los Operadores
	 *****************************************************************/
	
	 public long darMaxIdOperador ()
	 {
		 return (long) this.sqlOperador.darId(pmf.getPersistenceManager()).toBigInteger().intValueExact();
	 }
	public Operador adicionarOperador(String nombre, String tipo, String disponibilidad) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = this.sqlOperador.adicionarOperador(pm, nombre, tipo, disponibilidad);
            tx.commit();

            log.trace ("Inserción de bebedor: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Operador(this.darMaxIdOperador(), nombre, tipo, disponibilidad);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla BEBEDOR, dado el nombre del bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarOperadorPorId(long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = this.sqlOperador.eliminarOPeradorPorId(pm, id);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	
	public Operador darOperadorPorId (long idOperador) 
	{
		return (Operador) sqlOperador.darOperadorPorId(pmf.getPersistenceManager(), idOperador);
	}

	/* ****************************************************************
	 * 			Métodos para manejar los Operadores Personas naturales
	 *****************************************************************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla BAR
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bar
	 * @param ciudad - La ciudad del bar
	 * @param presupuesto - El presupuesto del bar (ALTO, MEDIO, BAJO)
	 * @param sedes - El número de sedes del bar en la ciudad (Mayor que 0)
	 * @return El objeto Bar adicionado. null si ocurre alguna Excepción
	 */
	public OperadorPersonaNatural adicionarOperadorPN(String vinculacion,long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = this.sqlOperadorpn.adicionarOperadorPN(pm, id, vinculacion);
            tx.commit();

            log.trace ("Inserción de Operador: " + id + ": " + tuplasInsertadas + " tuplas insertadas");

            return new OperadorPersonaNatural(id,vinculacion);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla BAR, dado el nombre del bar
	 * Adiciona entradas al log de la aplicación
	 * @param nombreBar - El nombre del bar
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarOperadorPNPorid (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = this.sqlOperadorpn.eliminarOperadorPNPorId(pm, id);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	
	
 
	
 
	/**
	 * Método que consulta todas las tuplas en la tabla BAR que tienen el identificador dado
	 * @param idBar - El identificador del bar
	 * @return El objeto BAR, construido con base en la tuplas de la tabla BAR, que tiene el identificador dado
	 */
	public OperadorPersonaNatural darOperadorPnPorId (long id)
	{
		return this.sqlOperadorpn.darOperadorPNPorId(pmf.getPersistenceManager(), id);
	}
 
	
	
	/* ****************************************************************
	 * 			Métodos para manejar la relación Reserva
	 *****************************************************************/
	
	 public long darMaxIdReserva ()
	 {
		 return (long) this.sqlReserva.darId(pmf.getPersistenceManager()).toBigInteger().intValueExact();
	 }
	 
	public Reserva adicionarReserva(Date fecha, long duracion, int numero_p, long oferta, long cliente) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = this.sqlReserva.adicionarReserva(pm, fecha, duracion, oferta, cliente,numero_p);
            tx.commit();

            log.trace ("Inserción de reserva: [" + cliente + ", " + oferta + "]. " + tuplasInsertadas + " tuplas insertadas");
			this.actulizarDisponibilidad2(oferta);
            return new Reserva(this.darMaxIdReserva(), tuplasInsertadas, fecha, numero_p, numero_p, numero_p, numero_p);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	
	


	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla GUSTAN, dados los identificadores de bebedor y bebida
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarReservaPorId( long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = this.sqlReserva.eliminarReservaPorId(pm, id);      
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que consulta todas las tuplas en la tabla GUSTAN
	 * @return La lista de objetos GUSTAN, construidos con base en las tuplas de la tabla GUSTAN
	 */
	public List<Reserva> darReservasPorCliente (long codigo)
	{
		return this.sqlReserva.darReservasPorCliente(pmf.getPersistenceManager(), codigo);
	}

	public Reserva darReservasPorid (long id)
	{
		return this.sqlReserva.darReservaPorId(pmf.getPersistenceManager(),id);
	}

	public List<Reserva> darReservasPorOferta (long id)
	{
		return this.sqlReserva.darReservasPorOferta(pmf.getPersistenceManager(),id);
	}
	
	
	/* ****************************************************************
	 * 			Métodos para manejar la relación Reserva colectiva
	 *****************************************************************/
	
	public ReservaColectiva adicionarReservaColectiva(Date fecha, long duracion, long numero_p, long oferta, long cliente, long numero_hab, long costo) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
       
            long tuplasInsertadas = this.sqlReservaColectiva.adicionarReservaColectiva(pm, fecha, duracion, oferta, cliente, numero_p, numero_hab, costo);
            tx.commit();

            log.trace ("Inserción de reserva: [" + cliente + ", " + oferta + "]. " + tuplasInsertadas + " tuplas insertadas");
			this.actulizarDisponibilidad1(oferta);
            return new ReservaColectiva(this.darMaxIdReserva(), tuplasInsertadas, fecha, numero_p, numero_p, numero_p, numero_p, numero_hab);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public long eliminarReservaColectivaPorId( long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = this.sqlReservaColectiva.eliminarReservaColectivaPorId(pm, id) ;    
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

 
 
	/* ****************************************************************
	 * 			Métodos para manejar la relación SERVICIO
	 *****************************************************************/
	public long darMaxIdServicio()
	 {
		
		 return (long) this.sqlServicio.darId(pmf.getPersistenceManager()).toBigInteger().intValueExact();
	 }
	
	public Servicio adicionarServicio(String nombre, String descripcion) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin(); // se inicializa en 0 para que el atributo generado automáticamente sea asignado
			long num =  sqlServicio.adicionarServicio(pm, nombre, descripcion);
			tx.commit();
	
			log.trace("Inserción de servicio: [" + nombre + ", " + descripcion + "]");
			return new Servicio(this.darMaxIdServicio(), nombre, descripcion);
			
		} catch (Exception e) {
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	
 
	public long eliminarServicioPorCodigo (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = this.sqlServicio.eliminarServicioPorCodigo(pm, id);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

 
	/**
	 * Método que consulta todas las tuplas en la tabla SIRVEN
	 * @return La lista de objetos SIRVEN, construidos con base en las tuplas de la tabla SIRVEN
	 */
	public List<Servicio> darServicios ()
	{
		return this.sqlServicio.darServicios (pmf.getPersistenceManager());
	}

	public Servicio darServicioPorCodigo (long codigo)
	{
		return this.sqlServicio.darServicioPorCodigo(pmf.getPersistenceManager(),codigo);
	}
 
	
 
	/* ****************************************************************
	 * 			Métodos para manejar la relación VISITAN
	 *****************************************************************/

	 public ServicioAlojamiento darServicioAlojamientoPorOfertaServicio (long oferta,long servicio)
	 {
		 return this.sqlServicioAlojamiento.darServicioAlojamientoPorOfertaServicio(pmf.getPersistenceManager(), oferta, servicio);
	 }

	public ServicioAlojamiento adicionarServicioAlojamiento (long oferta, long servicio,long precio,int disponibilidad) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlServicioAlojamiento.adicionarServicioAlojamiento(pm, oferta, precio, disponibilidad, servicio);
            tx.commit();

            long id =this.darServicioAlojamientoPorOfertaServicio(oferta, servicio).getId();
            return new ServicioAlojamiento(id, oferta, precio, disponibilidad, servicio);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla VISITAN, dados los identificadores de bebedor y bar
	 * @param idBebedor - El identificador del bebedor
	 * @param idBar - El identificador del bar
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarServicioAlojamientoPorOfertaServicio (long oferta, long servicio) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = this.sqlServicioAlojamiento.eliminarServicioAlojamientoPorOfertaServicio(pm, oferta, servicio);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que consulta todas las tuplas en la tabla VISITAN
	 * @return La lista de objetos VISITAN, construidos con base en las tuplas de la tabla VISITAN
	 */
	public List<ServicioAlojamiento> darVisitan (long oferta)
	{
		return this.sqlServicioAlojamiento.darserviciodeoferta(pmf.getPersistenceManager(), oferta);
	}	

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarParranderos ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long [] resp = sqlUtil.limpiarParranderos (pm);
            tx.commit ();
            log.info ("Borrada la base de datos");
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return new long[] {-1, -1, -1, -1, -1, -1, -1};
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
		
	}
	

 }
