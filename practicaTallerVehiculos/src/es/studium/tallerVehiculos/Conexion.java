package es.studium.tallerVehiculos;

import java.awt.Choice;
import java.awt.TextArea;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// Object "Conexion" used to establish a connection to
// a MySQL database and perform SQL queries on it.
public class Conexion
{
	// --- Declare instance variables to store database connection information ---
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/practicatallervehiculos";
	String login = "usuariotallervehiculos";
	String password = "usuariotallervehiculos";
	String sentence = ""; // SQL statement to be executed

	// Declare instance variables for database connection, statement, and result set
	Connection connection = null; 
	Statement statement = null; 
	ResultSet rs = null; 

	Conexion() {
		// Call the "conectar()" method to establish a database connection
		connection = this.conectar();
	}

	// Method to establish a database connection
	public Connection conectar() {

		try {
			// Load the MySQL driver
			Class.forName(driver);
			// Use the driver manager to create a connection to the database
			return(DriverManager.getConnection(url, login, password));
		}
		catch (ClassNotFoundException cnfe){
			// Display an error message if the driver cannot be loaded
			System.out.println("Error 1-"+cnfe.getMessage());
		}
		catch (SQLException sqle) {
			// Display an error message if the driver cannot be established
			System.out.println("Error 2-"+sqle.getMessage());
		}
		// If connection cannot be established, return null
		return null;
	}

	// Method to check user credentials against the database
	public int checkingCredentials(String user, String password)
	{
		// Define SQL query to check user credentials
		String cadena = "SELECT * FROM usuarios WHERE nombreUsuario = '"+ user + "' AND claveUsuario = SHA2('"+ password +"',256);";

		try {
			// Create a statement to execute the SQL query
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Execute the query and store the result set
			rs = statement.executeQuery(cadena);

			// If the query returns a result, return the user's "tipoUsuario" value
			if (rs.next()) {
				logs("[+] " +user, " has successfully logged.");
				return rs.getInt("tipoUsuario");

			} else {
				// If the query does not return a result, return -1
				return -1;
			}


		} catch (SQLException sqle) {
			// Display an error message if an exception is thrown during query execution
			System.out.println("Error 3-"+sqle.getMessage());
		}
		// If an exception is thrown during query execution, return -1
		return -1;
	}

	// --- Method to register a new client  ---
	public int altaCliente(String sentencia) {
		try {

			// Create a statement to execute the SQL query
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			statement.executeUpdate(sentencia);
			return 0;

		} catch (SQLException sqle) {
			System.out.println("Error 4-"+sqle.getMessage());
			return 1;
		}
	}

	// --- Method to fill client list ---
	public void fillListadoClientes(TextArea areaDatos, String user)
	{
		String sentencia = "SELECT idCliente, nombreCliente, telefonoCliente, correoCliente FROM practicatallervehiculos.clientes;";
		try {

			// Create a statement to execute the SQL query
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet resultado = statement.executeQuery(sentencia);
			while(resultado.next()) {
				// Append add
				areaDatos.append(resultado.getString("idCliente")+"         \t");
				areaDatos.append(resultado.getString("nombreCliente")+"         \t");
				if (resultado.getString("telefonoCliente").equals("")) {
					areaDatos.append("\t\t");
				} else {
					areaDatos.append(resultado.getString("telefonoCliente")+"         \t");
				}

				areaDatos.append(resultado.getString("correoCliente")+"\n");
			}

		} catch (SQLException sqle) {
			System.out.println("Error 5-"+sqle.getMessage());
		}
	}

	// --- Method to fill client choice ---
	public void fillChoiceClientes(Choice choClientes) {
		choClientes.removeAll();
		String sentencia = "SELECT idCliente, nombreCliente, telefonoCliente, correoCliente FROM practicatallervehiculos.clientes ORDER BY 1;";
		try {
			// Create a statement to execute the SQL query
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet resultado = statement.executeQuery(sentencia);
			choClientes.add("Elegir cliente..");
			while(resultado.next()) {
				// Append add
				choClientes.add(resultado.getString("idCliente")+"-"+resultado.getString("nombreCliente"));
			}


		} catch (SQLException sqle) {
			System.out.println("Error 6-"+sqle.getMessage());
		}
	}

	// --- Method to delete clients ---
	public int borrarCliente(String idCliente, String user) {
		String sentencia = "DELETE FROM clientes WHERE idCliente = " + idCliente;

		try {
			// Create a statement to execute the SQL query
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			statement.executeUpdate(sentencia);
			logs("[+] " +user, " " + sentencia);
			return 0;

		} catch (SQLException sqle) {
			System.out.println("Error 7-"+sqle.getMessage());
			return 1;

		}
	}

	// --- Method to get data from client table ---
	public String getDatosEdicionClients(String idCliente) {
		String resultado = "";
		String sentencia = "SELECT * FROM clientes WHERE idCliente = " + idCliente;
		try
		{
			// Create a statement to execute the SQL query
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Create an object called "ResultSet" to save obtained and execute SQL sentence
			ResultSet resultSet = statement.executeQuery(sentencia);
			resultSet.next();
			resultado =(resultSet.getString("idCliente")+"-"+resultSet.getString("nombreCliente")+
					"-"+resultSet.getString("telefonoCliente")+"-"+resultSet.getString("correoCliente"));
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 8-"+sqle.getMessage());
		}
		return resultado;
	}

	// --- Method to modify client data ---
	public int modificarCliente(String sentencia) {
		try
		{
			// Create a statement to execute the SQL query
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Execute SQL sentence
			statement.executeUpdate(sentencia);
			return 0;
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 9-"+sqle.getMessage());
			return 1;
		}
	}

	// --- Method to register a new service ---
	public int altaServicio(String sentencia) {
		try {

			// Create a statement to execute the SQL query
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			statement.executeUpdate(sentencia);
			return 0;

		} catch (SQLException sqle) {
			System.out.println("Error 10-"+sqle.getMessage());
			return 1;
		}
	}

	// --- Method to fill service list ---
	public void fillListadoServicio(TextArea areaDatos, String user) {
		String sentencia = "SELECT idServicio, tipoServicio, descripcionServicio, precioServicio FROM practicatallervehiculos.servicios;";
		try {

			// Create a statement to execute the SQL query
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet resultado = statement.executeQuery(sentencia);
			while(resultado.next()) {
				// Append add
				areaDatos.append(resultado.getString("idServicio")+"  \t");
				areaDatos.append(resultado.getString("precioServicio")+" €     \t");
				areaDatos.append(resultado.getString("tipoServicio")+"     \t");
				areaDatos.append(resultado.getString("descripcionServicio")+"\n");
			}

		} catch (SQLException sqle) {
			System.out.println("Error 11-"+sqle.getMessage());
		}
	}

	// --- Method to fill service choice ---
	public void fillChoiceServicios(Choice choServicios) {
		choServicios.removeAll();
		String sentencia = "SELECT idServicio, tipoServicio, descripcionServicio, precioServicio FROM practicatallervehiculos.servicios ORDER BY 1;";
		try {
			// Create a statement to execute the SQL query
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet resultado = statement.executeQuery(sentencia);
			choServicios.add("Elegir servicio..");
			while(resultado.next()) {
				// Append add
				choServicios.add(resultado.getString("idServicio")+"-"+resultado.getString("tipoServicio")+"-"+resultado.getString("precioServicio"));
			}


		} catch (SQLException sqle) {
			System.out.println("Error 12-"+sqle.getMessage());
		}
	}

	// --- Method to delete services data ---
	public int borrarServicio(String idServicio, String user) {
		String sentencia = "DELETE FROM servicios WHERE idServicio = " + idServicio;

		try {
			// Create a statement to execute the SQL query
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			statement.executeUpdate(sentencia);
			logs("[+] " +user, " " +sentencia);
			return 0;

		} catch (SQLException sqle) {
			System.out.println("Error 13-"+sqle.getMessage());
			return 1;

		}
	}

	// --- Method to modify services data ---
	public int modificarServicio(String sentencia) {
		try
		{
			// Create a statement to execute the SQL query
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Execute SQL sentence
			statement.executeUpdate(sentencia);
			return 0;
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 14-"+sqle.getMessage());
			return 1;
		}
	}

	// --- Method to get data from service table ---
	public String getDatosEdicionServicio(String idServicio) {
		String resultado = "";
		String sentencia = "SELECT * FROM servicios WHERE idServicio = " + idServicio;
		try
		{
			// Create a statement to execute the SQL query
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Create an object called "ResultSet" to save obtained and execute SQL sentence
			ResultSet resultSet = statement.executeQuery(sentencia);
			resultSet.next();
			resultado =(resultSet.getString("idServicio")+"-"+resultSet.getString("tipoServicio")+
					"-"+resultSet.getString("descripcionServicio")+"-"+resultSet.getString("precioServicio"));
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 15-"+sqle.getMessage());
		}
		return resultado;
	}


	// --- Method to register a new realizan  ---
	public int altaRealizan(String sentencia)
	{
		try {

			// Create a statement to execute the SQL query
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			statement.executeUpdate(sentencia);
			return 0;

		} catch (SQLException sqle) {
			System.out.println("Error 16-"+sqle.getMessage());
			return 1;
		}
	}

	public void fillListadoRealizan(TextArea areaDatosRealizan, String user)
	{
		String sentencia = "SELECT idRealiza, horasEmpleadas, fechaServiciosRealizados, nombreCliente, tipoServicio FROM realizan"
				+ " JOIN clientes ON realizan.idClientesFK = clientes.idCliente JOIN servicios ON realizan.idServiciosFK = servicios.idServicio;";
		try {

			// Create a statement to execute the SQL query
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet resultado = statement.executeQuery(sentencia);
			while(resultado.next()) {
				// Append add
				areaDatosRealizan.append(resultado.getString("nombreCliente")+"      \t");
				areaDatosRealizan.append(resultado.getString("tipoServicio")+"    \t");
				areaDatosRealizan.append(resultado.getString("horasEmpleadas")+"        \t");

				// Convert date format
	            String fechaOriginal = resultado.getString("fechaServiciosRealizados");
	            String fechaFormateada = LocalDate.parse(fechaOriginal).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

				areaDatosRealizan.append(fechaFormateada + "\n");
			}

		} catch (SQLException sqle) {
			System.out.println("Error 17-" + sqle.getMessage());
		}
	}

	// --- Method to open and write a File - LOG ---
	public void logs (String usuario, String mensaje) {

		try {

			FileWriter fw = new FileWriter("registros.log", true);
			LocalDate fechaActual = LocalDate.now();
			LocalTime horaActual = LocalTime.now();
			DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");
			String registro = "[" +fechaActual.format(formatterFecha) + "] - [" + horaActual.format(formatterHora) + "]: " + usuario + mensaje + "\n";
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			pw.write(registro);
			pw.close();
			bw.close();
			fw.close();

		} catch (IOException eLog){
			System.out.println("Error 18-" +eLog.getMessage());
		}
	}


}












