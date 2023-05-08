package es.studium.tallerVehiculos;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class MenuPrincipal implements WindowListener, ActionListener
{
	// --- General components ---
	Frame windowPrincipal = new Frame("Menú Principal");
	MenuBar barraMenu = new MenuBar();

	// --- Main menus ---
	Menu menuClientes = new Menu("Clientes");
	Menu menuServicios = new Menu("Servicios");

	// --- Menu items for "Clientes" ---
	MenuItem menuClientesNuevo = new MenuItem("Nuevo");
	MenuItem menuClientesListado = new MenuItem("Listado");
	MenuItem menuClientesBaja = new MenuItem("Baja");
	MenuItem menuClientesModificar = new MenuItem("Modificar");

	// --- Menu items for "Servicios" ---
	MenuItem menuServiciosNuevo = new MenuItem("Nuevo");
	MenuItem menuServiciosListado = new MenuItem("Listado");
	MenuItem menuServiciosBaja = new MenuItem("Baja");
	MenuItem menuServiciosModificar = new MenuItem("Modificar");

	// --- Create a variable to store the type of user ---
	int tipoUsuario;

	Conexion conexion = new Conexion();

	// -- Cast user --
	String user;

	public MenuPrincipal(int t, String user) {

		// -- Create user --
		this.user = user;

		// --- Set the type of user ---
		tipoUsuario = t;

		// --- Set the layout of the main window to a flow layout ---
		windowPrincipal.setLayout(new FlowLayout());
		// --- Set the menu bar of the main window ---
		windowPrincipal.setMenuBar(barraMenu);		

		// --- Set general window options ---
		windowPrincipal.addWindowListener(this);
		windowPrincipal.setResizable(false); 
		windowPrincipal.setSize(450, 450); 
		windowPrincipal.setLocationRelativeTo(null);
		windowPrincipal.setBackground(Color.white);


		// --- Add an action listener to each menu item for "Clientes" ---
		menuClientesNuevo.addActionListener(this);
		menuClientesListado.addActionListener(this);
		menuClientesBaja.addActionListener(this);
		menuClientesModificar.addActionListener(this);

		// --- Add an action listener to each menu item for "Servicios" ---
		menuServiciosNuevo.addActionListener(this);
		menuServiciosListado.addActionListener(this);
		menuServiciosBaja.addActionListener(this);
		menuServiciosModificar.addActionListener(this);

		// --- Add menu items to their corresponding menus ---
		// If I want to disable them: menuClientesListado.setEnabled(false);
		menuClientes.add(menuClientesNuevo);
		if(tipoUsuario==1)
		{
			menuClientes.addSeparator();
			menuClientes.add(menuClientesListado);
			menuClientes.add(menuClientesBaja);
			menuClientes.add(menuClientesModificar);
		}
		barraMenu.add(menuClientes);

		menuServicios.add(menuServiciosNuevo);
		if(tipoUsuario==1)
		{
			menuServicios.addSeparator();
			menuServicios.add(menuServiciosListado);
			menuServicios.add(menuServiciosBaja);
			menuServicios.add(menuServiciosModificar);
		}
		barraMenu.add(menuServicios);

		// --- Set window visible --- 
		windowPrincipal.setVisible(true); 
	}

	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we)
	{
		// -- Generate message to log file  -- 
		conexion.logs("[+] " +user, " succesfully log out.\n--------------------------------------------------------------------------------");
		System.exit(0);

	}
	public void windowDeactivated(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}

	@Override
	public void actionPerformed(ActionEvent evento) 
	{
		// --- The window that opens depends on where the user clicks ---
		if(evento.getSource().equals(menuClientesNuevo))
		{
			new AltaCliente(user);
			conexion.logs("[+] " +user, " has opened 'Alta Cliente' window.");
		}
		else if(evento.getSource().equals(menuClientesListado))
		{
			new ListadoCliente(user);
			conexion.logs("[+] " +user, " has opened 'Listado Cliente' window.");
			conexion.logs("[+] " +user, " has succesfully generated Client List.");
		}
		else if(evento.getSource().equals(menuClientesBaja))
		{
			new BajaCliente(user);
			conexion.logs("[+] " +user, " has opened 'Baja Cliente' window.");
		}
		else if(evento.getSource().equals(menuClientesModificar))
		{
			new ModificarCliente(user);
			conexion.logs("[+] " +user, " has opened 'Modificar Cliente' window.");
		}
		else if (evento.getSource().equals(menuServiciosNuevo)) 
		{
			new AltaServicio(user);
			conexion.logs("[+] " +user, " has opened 'Alta Servicio' window.");
		}
		else if (evento.getSource().equals(menuServiciosListado)) 
		{
			new ListadoServicio(user);
			conexion.logs("[+] " +user, " has opened 'Listado Servicio' window.");
			conexion.logs("[+] " +user, " has succesfully generated Service List.");
		}
		else if (evento.getSource().equals(menuServiciosBaja)) 
		{
			new BajaServicio(user);
			conexion.logs("[+] " +user, " has opened 'Baja Servicio' window.");
		}
		else if (evento.getSource().equals(menuServiciosModificar)) 
		{
			new ModificarServicio(user);
			conexion.logs("[+] " +user, " has opened 'Modificar Servicio' window.");
		}
	}
}