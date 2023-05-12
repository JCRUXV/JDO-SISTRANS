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

package uniandes.isis2304.parranderos.interfazApp;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.parranderos.negocio.Parranderos;
import uniandes.isis2304.parranderos.negocio.VOCliente;
import uniandes.isis2304.parranderos.negocio.VOOferta;
import uniandes.isis2304.parranderos.negocio.VOOperador;
import uniandes.isis2304.parranderos.negocio.VOOperadorPersonaNatural;
import uniandes.isis2304.parranderos.negocio.VOReserva;
import uniandes.isis2304.parranderos.negocio.VOServicio;
import uniandes.isis2304.parranderos.negocio.VOServicioAlojamiento;


/**
 * Clase principal de la interfaz
 * @author Germán Bravo
 */
@SuppressWarnings("serial")

public class InterfazParranderosApp extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazParranderosApp.class.getName());
	
	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
    /**
     * Asociación a la clase principal del negocio.
     */
    private Parranderos parranderos;
    
	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
    /**
     * Objeto JSON con la configuración de interfaz de la app.
     */
    private JsonObject guiConfig;
    
    /**
     * Panel de despliegue de interacción para los requerimientos
     */
    private PanelDatos panelDatos;
    
    /**
     * Menú de la aplicación
     */
    private JMenuBar menuBar;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
    /**
     * Construye la ventana principal de la aplicación. <br>
     * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
     */
    public InterfazParranderosApp( )
    {
        // Carga la configuración de la interfaz desde un archivo JSON
        guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
        
        // Configura la apariencia del frame que contiene la interfaz gráfica
        configurarFrame ( );
        if (guiConfig != null) 	   
        {
     	   crearMenu( guiConfig.getAsJsonArray("menuBar") );
        }
        
        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
        parranderos = new Parranderos (tableConfig);
        
    	String path = guiConfig.get("bannerPath").getAsString();
        panelDatos = new PanelDatos ( );

        setLayout (new BorderLayout());
        add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
        add( panelDatos, BorderLayout.CENTER );        
    }
    
	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String tipo, String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Parranderos App", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }
    
    /**
     * Método para configurar el frame principal de la aplicación
     */
    private void configurarFrame(  )
    {
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	
    	if ( guiConfig == null )
    	{
    		log.info ( "Se aplica configuración por defecto" );			
			titulo = "Parranderos APP Default";
			alto = 300;
			ancho = 500;
    	}
    	else
    	{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
    		titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
    	}
    	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocation (50,50);
        setResizable( true );
        setBackground( Color.WHITE );

        setTitle( titulo );
		setSize ( ancho, alto);        
    }

    /**
     * Método para crear el menú de la aplicación con base em el objeto JSON leído
     * Genera una barra de menú y los menús con sus respectivas opciones
     * @param jsonMenu - Arreglo Json con los menùs deseados
     */
    private void crearMenu(  JsonArray jsonMenu )
    {    	
    	// Creación de la barra de menús
        menuBar = new JMenuBar();       
        for (JsonElement men : jsonMenu)
        {
        	// Creación de cada uno de los menús
        	JsonObject jom = men.getAsJsonObject(); 

        	String menuTitle = jom.get("menuTitle").getAsString();        	
        	JsonArray opciones = jom.getAsJsonArray("options");
        	
        	JMenu menu = new JMenu( menuTitle);
        	
        	for (JsonElement op : opciones)
        	{       	
        		// Creación de cada una de las opciones del menú
        		JsonObject jo = op.getAsJsonObject(); 
        		String lb =   jo.get("label").getAsString();
        		String event = jo.get("event").getAsString();
        		
        		JMenuItem mItem = new JMenuItem( lb );
        		mItem.addActionListener( this );
        		mItem.setActionCommand(event);
        		
        		menu.add(mItem);
        	}       
        	menuBar.add( menu );
        }        
        setJMenuBar ( menuBar );	
    }
    
	/* ****************************************************************
	 * 			CRUD de Cliente
	 *****************************************************************/
    /**
     * Adiciona un tipo de bebida con la información dada por el usuario
     * Se crea una nueva tupla de tipoBebida en la base de datos, si un tipo de bebida con ese nombre no existía
     */
    public void adicionarCLiente( )
    {
    	try 
    	{
    		String codigo = JOptionPane.showInputDialog (this, "codigo?", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
    		String vinculacion = JOptionPane.showInputDialog (this, "vinculacion?", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
    		
			if (codigo != null && vinculacion != null)
    		{
				long valorCodigo = Long.parseLong(codigo);
        		VOCliente tb = parranderos.adicionarCliente(valorCodigo, vinculacion);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear un cliente con codigo: " + codigo);
        		}
        		String resultado = "En adicionarCliente\n\n";
        		resultado += "Cliente adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

	public void adicionarOferta() {
		try {
			String tipo = JOptionPane.showInputDialog(this, "Tipo?", "Adicionar oferta", JOptionPane.QUESTION_MESSAGE);
			String capacidadStr = JOptionPane.showInputDialog(this, "Capacidad?", "Adicionar oferta", JOptionPane.QUESTION_MESSAGE);
			String ubicacion = JOptionPane.showInputDialog(this, "Ubicación?", "Adicionar oferta", JOptionPane.QUESTION_MESSAGE);
			String viviendaStr = JOptionPane.showInputDialog(this, "Vivienda?", "Adicionar oferta", JOptionPane.QUESTION_MESSAGE);
			String disponibilidad = JOptionPane.showInputDialog(this, "Disponibilidad?", "Adicionar oferta", JOptionPane.QUESTION_MESSAGE);
			String fechaStr = JOptionPane.showInputDialog(this, "Fecha?", "Adicionar oferta", JOptionPane.QUESTION_MESSAGE);
			String cantDiasStr = JOptionPane.showInputDialog(this, "Cantidad de días?", "Adicionar oferta", JOptionPane.QUESTION_MESSAGE);
			String precioStr = JOptionPane.showInputDialog(this, "Precio?", "Adicionar oferta", JOptionPane.QUESTION_MESSAGE);
			String operadorStr = JOptionPane.showInputDialog(this, "Operador?", "Adicionar oferta", JOptionPane.QUESTION_MESSAGE);
	
			if (tipo != null && capacidadStr != null && ubicacion != null && viviendaStr != null && disponibilidad != null
					&& fechaStr != null && cantDiasStr != null && precioStr != null && operadorStr != null) {
				
				// Convertir los valores de cadena a los tipos de datos adecuados
				long capacidad = Long.parseLong(capacidadStr);
				long vivienda = Long.parseLong(viviendaStr);
				Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
                long time = utilDate.getTime();
				java.sql.Date fecha = new java.sql.Date(time);
				long cantDias = Long.parseLong(cantDiasStr);
				long precio = Long.parseLong(precioStr);
				long operador = Long.parseLong(operadorStr);
	
				// Añadir la oferta
				VOOferta oferta = parranderos.adicionarOferta(tipo, capacidad, ubicacion, vivienda, disponibilidad, fecha, cantDias, precio, operador);
	
				if (oferta == null) {
					throw new Exception("No se pudo crear una oferta con los datos proporcionados.");
				}
	
				String resultado = "En adicionarOferta\n\n";
				resultado += "Oferta adicionada exitosamente: " + oferta;
				resultado += "\nOperación terminada.";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void adicionarReserva() {
		try {
			String fechaStr = JOptionPane.showInputDialog(this, "Fecha?", "Adicionar reserva", JOptionPane.QUESTION_MESSAGE);
			String duracionStr = JOptionPane.showInputDialog(this, "Duración?", "Adicionar reserva", JOptionPane.QUESTION_MESSAGE);
			String numero_pStr = JOptionPane.showInputDialog(this, "Número de personas?", "Adicionar reserva", JOptionPane.QUESTION_MESSAGE);
			String ofertaStr = JOptionPane.showInputDialog(this, "Oferta?", "Adicionar reserva", JOptionPane.QUESTION_MESSAGE);
			String clienteStr = JOptionPane.showInputDialog(this, "Cliente?", "Adicionar reserva", JOptionPane.QUESTION_MESSAGE);
	
			if (fechaStr != null && duracionStr != null && numero_pStr != null && ofertaStr != null && clienteStr != null) {
				// Convertir los valores de cadena a los tipos de datos adecuados
				Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
				long time = utilDate.getTime();
				java.sql.Date fecha = new java.sql.Date(time);
				long duracion = Long.parseLong(duracionStr);
				int numero_p = Integer.parseInt(numero_pStr);
				long oferta = Long.parseLong(ofertaStr);
				long cliente = Long.parseLong(clienteStr);
	
				// Añadir la reserva
				VOReserva reserva = parranderos.adicionarReserva(fecha, duracion, numero_p, oferta, cliente);
	
				if (reserva == null) {
					throw new Exception("No se pudo crear una reserva con los datos proporcionados.");
				}
	
				String resultado = "En adicionarReserva\n\n";
				resultado += "Reserva adicionada exitosamente: " + reserva;
				resultado += "\nOperación terminada.";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}


	public void adicionarServicio() {
		try {
		String nombre = JOptionPane.showInputDialog(this, "Nombre?", "Adicionar servicio", JOptionPane.QUESTION_MESSAGE);
		String descripcion = JOptionPane.showInputDialog(this, "Descripción?", "Adicionar servicio", JOptionPane.QUESTION_MESSAGE);
		if (nombre != null && descripcion != null) {
			// Añadir el servicio
			VOServicio servicio = parranderos.adicionarServicio(nombre, descripcion);
	
			if (servicio == null) {
				throw new Exception("No se pudo crear un servicio con los datos proporcionados.");
			}
	
			String resultado = "En adicionarServicio\n\n";
			resultado += "Servicio adicionado exitosamente: " + servicio;
			resultado += "\nOperación terminada.";
			panelDatos.actualizarInterfaz(resultado);
		} else {
			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
		}
	} catch (Exception e) {
		String resultado = generarMensajeError(e);
		panelDatos.actualizarInterfaz(resultado);
	}
}

public void adicionarServicio_alojamiento() {
    try {
        String ofertaStr = JOptionPane.showInputDialog(this, "ID de la oferta?", "Adicionar servicio de alojamiento", JOptionPane.QUESTION_MESSAGE);
        String servicioStr = JOptionPane.showInputDialog(this, "ID del servicio?", "Adicionar servicio de alojamiento", JOptionPane.QUESTION_MESSAGE);
        String precioStr = JOptionPane.showInputDialog(this, "Precio?", "Adicionar servicio de alojamiento", JOptionPane.QUESTION_MESSAGE);
        String disponibilidadStr = JOptionPane.showInputDialog(this, "Disponibilidad?", "Adicionar servicio de alojamiento", JOptionPane.QUESTION_MESSAGE);

        if (ofertaStr != null && servicioStr != null && precioStr != null && disponibilidadStr != null) {
            long oferta = Long.parseLong(ofertaStr);
            long servicio = Long.parseLong(servicioStr);
            int precio = Integer.parseInt(precioStr);
            int disponibilidad = Integer.parseInt(disponibilidadStr);

            // Añadir el servicio de alojamiento
            VOServicioAlojamiento servicio_alojamiento = parranderos.adicionarServicioAlojamiento(oferta, servicio, precio, disponibilidad);

            if (servicio_alojamiento == null) {
                throw new Exception("No se pudo crear un servicio de alojamiento con los datos proporcionados.");
            }

            String resultado = "En adicionarServicio_alojamiento\n\n";
            resultado += "Servicio de alojamiento adicionado exitosamente: " + servicio_alojamiento;
            resultado += "\nOperación terminada.";
            panelDatos.actualizarInterfaz(resultado);
        } else {
            panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
        }
    } catch (Exception e) {
        String resultado = generarMensajeError(e);
        panelDatos.actualizarInterfaz(resultado);
    }
}

	
	

	public void adicionarOperador() {
		try {
			String nombre = JOptionPane.showInputDialog(this, "Nombre?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
			String tipo = JOptionPane.showInputDialog(this, "Tipo?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
			String disponibilidad = JOptionPane.showInputDialog(this, "Disponibilidad?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
	
			if (nombre != null && tipo != null && disponibilidad != null) {
				// Añadir el operador
				VOOperador operador = parranderos.adicionarOperador(nombre, tipo, disponibilidad);
	
				if (operador == null) {
					throw new Exception("No se pudo crear un operador con los datos proporcionados.");
				}
	
				String resultado = "En adicionarOperador\n\n";
				resultado += "Operador adicionado exitosamente: " + operador;
				resultado += "\nOperación terminada.";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void adicionarOperadorPN() {
		try {
			String vinculacion = JOptionPane.showInputDialog(this, "Vinculación?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
			String idStr = JOptionPane.showInputDialog(this, "ID?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
	
			if (vinculacion != null && idStr != null) {
				long id = Long.parseLong(idStr);
				// Añadir el operador
				VOOperadorPersonaNatural operador = parranderos.adicionarOperadorPN(vinculacion, id);
	
				if (operador == null) {
					throw new Exception("No se pudo crear un operador con los datos proporcionados.");
				}
	
				String resultado = "En adicionarOperador\n\n";
				resultado += "Operador adicionado exitosamente: " + operador;
				resultado += "\nOperación terminada.";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	

    /**
     * Consulta en la base de datos los tipos de bebida existentes y los muestra en el panel de datos de la aplicación
     */
    public void listarOferta( )
    {
    	try 
    	{
			List <VOOferta> lista = parranderos.darVOOfertas();

			String resultado = "En listarOferta";
			resultado +=  "\n" + listarOferta(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    

    public void eliminarOfertaPorId( )
    {
    	try 
    	{
    		String idTipoStr = JOptionPane.showInputDialog (this, "Id de reserva?", "Borrar reserva por Id", JOptionPane.QUESTION_MESSAGE);
    		if (idTipoStr != null)
    		{
    			long idTipo = Long.valueOf (idTipoStr);
    			long tbEliminados = parranderos.eliminarOfertaPorId(idTipo);

    			String resultado = "En eliminar Oferta\n\n";
    			resultado += tbEliminados + " Ofertas						 eliminados\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

	public void RF9( )
    {
    	try 
    	{
    		String idOfertaStr = JOptionPane.showInputDialog (this, "Id de reserva?", "Borrar reserva por Id", JOptionPane.QUESTION_MESSAGE);
    		if (idOfertaStr != null)
    		{
    			long idTipo = Long.valueOf (idOfertaStr);
    			String tbEliminados = parranderos.RF9(idTipo);

    			String resultado = "cambiando disponibilidad de  Oferta\n\n";
    			resultado += tbEliminados ;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

	public void RF10( )
    {
    	try 
    	{
    		String idOfertaStr = JOptionPane.showInputDialog (this, "Id de reserva?", "Borrar reserva por Id", JOptionPane.QUESTION_MESSAGE);
    		if (idOfertaStr != null)
    		{
    			long idTipo = Long.valueOf (idOfertaStr);
    			String tbEliminados = parranderos.RF10(idTipo);

    			String resultado = "cambiando disponibilidad de  Oferta\n\n";
    			resultado += tbEliminados ;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    public void eliminarReservaPorId( )
    {
    	try 
    	{
    		String idTipoStr = JOptionPane.showInputDialog (this, "Id de reserva?", "Borrar reserva por Id", JOptionPane.QUESTION_MESSAGE);
    		if (idTipoStr != null)
    		{
    			long idTipo = Long.valueOf (idTipoStr);
    			long tbEliminados = parranderos.eliminarReservaPorId(idTipo);

    			String resultado = "En eliminar Reserva\n\n";
    			resultado += tbEliminados + " Reservas eliminados\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Busca el tipo de bebida con el nombre indicado por el usuario y lo muestra en el panel de datos
     */
    public void rfc1( )
    {
    	try 
    	{
    		
    			List<Object[]> lista = parranderos.darDineroPropietario();
    			String resultado = "En dinero recibido por cada provietario\n\n";
    			if (lista != null)
    			{
        			resultado += lista;
    			}
    			else
    			{
        			resultado += "no se pudo hacer la consulta\n";    				
    			}
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		
    		
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
	public void rfc2( )
    {
		try 
    	{
			List <VOOferta> lista = parranderos.darVOOfertasPopulares();

			String resultado = "En listarOferta";
			resultado +=  "\n" + listarOferta(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
	
	public void rfc4( )
    {
		String servicio = JOptionPane.showInputDialog(this, "servicio?", "consultar ofertas de servicio", JOptionPane.QUESTION_MESSAGE);
		
		try 
    	{
			List <VOOferta> lista = parranderos.darVOOfertasServicio(servicio);

			String resultado = "En listarOferta";
			resultado +=  "\n" + listarOferta(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

	public void rfc3( )
    {
    	try 
    	{
    		
    			List<Object[]> lista = parranderos.darIndiceOcupacion();
    			String resultado = "ofertas mas populares\n\n";
    			if (lista != null)
    			{
        			resultado += lista;
    			}
    			else
    			{
        			resultado += "no se pudo hacer la consulta\n";    				
    			}
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		
    		
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }


	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Parranderos
	 */
	public void mostrarLogParranderos ()
	{
		mostrarArchivo ("parranderos.log");
	}
	
	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}
	
	/**
	 * Limpia el contenido del log de parranderos
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogParranderos ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("parranderos.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de parranderos ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de parranderos
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try 
		{
    		// Ejecución de la demo y recolección de los resultados
			long eliminados [] = parranderos.limpiarParranderos();
			
			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados [0] + " Gustan eliminados\n";
			resultado += eliminados [1] + " Sirven eliminados\n";
			resultado += eliminados [2] + " Visitan eliminados\n";
			resultado += eliminados [3] + " Bebidas eliminadas\n";
			resultado += eliminados [4] + " Tipos de bebida eliminados\n";
			resultado += eliminados [5] + " Bebedores eliminados\n";
			resultado += eliminados [6] + " Bares eliminados\n";
			resultado += "\nLimpieza terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-ParranderosJDO.pdf");
	}
	
	/**
	 * Muestra el modelo conceptual de Parranderos
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual Parranderos.pdf");
	}
	
	/**
	 * Muestra el esquema de la base de datos de Parranderos
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Esquema BD Parranderos.pdf");
	}
	
	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemaParranderos.sql");
	}
	
	/**
	 * Muestra la arquitectura de referencia para Parranderos
	 */
	public void mostrarArqRef ()
	{
		mostrarArchivo ("data/ArquitecturaReferencia.pdf");
	}
	
	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}
	
	/**
     * Muestra la información acerca del desarrollo de esta apicación
     */
    public void acercaDe ()
    {
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * Licenciado	bajo	el	esquema	Academic Free License versión 2.1\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: Parranderos Uniandes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Germán Bravo\n";
		resultado += " * Julio de 2018\n";
		resultado += " * \n";
		resultado += " * Revisado por: Claudia Jiménez, Christian Ariza\n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
    }
    

	/* ****************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/
    /**
     * Genera una cadena de caracteres con la lista de los tipos de bebida recibida: una línea por cada tipo de bebida
     * @param lista - La lista con los tipos de bebida
     * @return La cadena con una líea para cada tipo de bebida recibido
     */
    private String listarOferta(List<VOOferta> lista) 
    {
    	String resp = "Las ofertas son:\n";
    	int i = 1;
        for (VOOferta tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
     * @param e - La excepción recibida
     * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
    /**
     * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
     * Invoca al método correspondiente según el evento recibido
     * @param pEvento - El evento del usuario
     */
    @Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
        try 
        {
			Method req = InterfazParranderosApp.class.getMethod ( evento );			
			req.invoke ( this );
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		} 
	}
    
	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args Arreglo de argumentos que se recibe por línea de comandos
     */
    public static void main( String[] args )
    {
        try
        {
        	
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            InterfazParranderosApp interfaz = new InterfazParranderosApp( );
            interfaz.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }
}
