package es.studium.tallerVehiculos;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.awt.Toolkit;


public class MenuPrincipal extends Frame implements WindowListener, ActionListener, KeyListener
{

	private static final long serialVersionUID = 1L;
	Image menuPrincipal;
	Image superAdmin;
	Toolkit herramienta;
	// --- General components ---
	MenuBar barraMenu = new MenuBar();

	// --- Main menus ---
	Menu menuClientes = new Menu("Clientes");
	Menu menuServicios = new Menu("Servicios");
	Menu menuRealizan = new Menu("Realizan");
	Menu menuAyuda = new Menu("Ayuda");

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

	// --- Menu items for "Realizan" ---
	MenuItem menuRealizanNuevo = new MenuItem("Nuevo");
	MenuItem menuRealizanListado = new MenuItem("Listado");
	MenuItem menuRealizanBaja = new MenuItem("Baja");
	MenuItem menuRealizanModificar = new MenuItem("Modificar");

	// --- Menu items for "Ayuda" ---
	MenuItem menuAyudaHelp = new MenuItem("Manual");

	// --- Declared a custom dialog ---
	Dialog dlgWindow = new Dialog(this, "Aviso", false);
	Label lblAviso = new Label ("");

	// --- Create a variable to store the type of user ---
	int tipoUsuario;

	// --- Create a variable to manage modes
	boolean superAdminMode = false;

	Conexion conexion = new Conexion();

	// -- Cast user --
	String user;

	public MenuPrincipal(int t, String user) {

		// -- Create user --
		this.user = user;

		// --- Set the type of user ---
		tipoUsuario = t;

		// --- Set the layout of the main window to a flow layout ---
		setLayout(new FlowLayout());
		// --- Set the menu bar of the main window ---
		setMenuBar(barraMenu);		

		// --- Set general window options ---
		setTitle("Menú Principal");
		addWindowListener(this);
		setResizable(false); 
		setSize(450, 450); 
		setLocationRelativeTo(null);
		setBackground(Color.white);

		// --- KeyListener to register key combinations ---
		addKeyListener(this);

		// --- Paint background with an image ---
		herramienta = getToolkit();
		menuPrincipal = herramienta.getImage("images/menuPrincipal.png");
		superAdmin = herramienta.getImage("images/superAdmin.png");

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

		// --- Add an action listener to each menu item for "Realizan" ---
		menuRealizanNuevo.addActionListener(this);
		menuRealizanListado.addActionListener(this);
		menuRealizanBaja.addActionListener(this);
		menuRealizanModificar.addActionListener(this);

		// --- Add an action listener to "Ayuda" item
		menuAyudaHelp.addActionListener(this);

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

		menuRealizan.add(menuRealizanNuevo);
		if(tipoUsuario==1)
		{
			menuRealizan.addSeparator();
			menuRealizan.add(menuRealizanListado);
			menuRealizan.add(menuRealizanBaja);
			menuRealizanBaja.setEnabled(false);
			menuRealizan.add(menuRealizanModificar);
			menuRealizanModificar.setEnabled(false);
		}
		barraMenu.add(menuRealizan);

		menuAyuda.add(menuAyudaHelp);
		barraMenu.add(menuAyuda);

		// --- Set window visible --- 
		setVisible(true); 
	}

	public void paint(Graphics g) {

		if (superAdminMode) {
			g.drawImage(superAdmin, -25, 0, this);
		} else {
			g.drawImage(menuPrincipal, 0, 0, this);
		}
	}

	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we)
	{
		if (dlgWindow.isActive()) {
			dlgWindow.setVisible(false);
		} else {
			// -- Generate message to log file  -- 
			conexion.logs("[+] " +user, " has successfully logged out.\n--------------------------------------------------------------------------------");
			System.exit(0);
		}
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
			conexion.logs("[+] " +user, " has successfully generated Client List.");
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
			conexion.logs("[+] " +user, " has successfully generated Service List.");
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
		else if (evento.getSource().equals(menuRealizanNuevo)) 
		{
			new AltaRealizan(user);
			conexion.logs("[+] " +user, " has opened 'Alta Realizan' window.");
		}
		else if (evento.getSource().equals(menuRealizanListado)) 
		{
			new ListadoRealizan(user);
			conexion.logs("[+] " +user, " has opened 'Listado Realizan' window.");
			conexion.logs("[+] " +user, " has successfully generated Realizan List.");
		}
		else if (evento.getSource().equals(menuRealizanBaja)) 
		{
			// new... /// dlg: No ilegals actions..
			//conexion.logs("[+] " +user, " has opened '...' window.");
		}
		else if (evento.getSource().equals(menuRealizanModificar)) 
		{
			//new ...
			//conexion.logs("[+] " +user, " has opened '...' window.");
		} else if (evento.getSource().equals(menuAyudaHelp)) {
			try {
				
				String rutaHTML = "C:\\Users\\Studi\\OneDrive\\Escritorio\\Asignaturas\\PR_3TRIMESTRE\\practicaTallerVehiculos\\index.html";

				// Verificar si el escritorio es compatible y está disponible
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
					// Abrir el archivo HTML en el navegador predeterminado
					Desktop.getDesktop().browse(new File(rutaHTML).toURI());
				} else {
					System.out.println("El escritorio no es compatible o no se puede abrir el navegador.");
				}
			} catch (IOException e) {
				System.out.println("Error al abrir el archivo HTML: " + e.getMessage());
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e){}
	@Override
	public void keyReleased(KeyEvent e){}
	@Override
	public void keyPressed(KeyEvent e) {



		if (tipoUsuario==1) {

			if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_B) {

				superAdminMode = true;
				repaint();
				menuRealizanBaja.setEnabled(true);
				menuRealizanModificar.setEnabled(true);

				// set visible dialog
				dlgWindow.setLayout(new FlowLayout());
				dlgWindow.addWindowListener(this);
				dlgWindow.setResizable(false);
				dlgWindow.setSize(300, 75);
				dlgWindow.setLocationRelativeTo(null);
				dlgWindow.setBackground(Color.black);
				lblAviso.setForeground(Color.white);
				dlgWindow.add(lblAviso);
				lblAviso.setText("El Modo Super Admin ha sido activado.");
				dlgWindow.setVisible(true);

				conexion.logs("[+] " + user, " has activated Super Admin Mode.");
			}

			if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N) {

				superAdminMode = false;
				repaint();
				menuRealizanBaja.setEnabled(false);
				menuRealizanModificar.setEnabled(false);

				// set visible dialog
				dlgWindow.setLayout(new FlowLayout());
				dlgWindow.addWindowListener(this);
				dlgWindow.setResizable(false);
				dlgWindow.setSize(300, 75);
				dlgWindow.setLocationRelativeTo(null);
				dlgWindow.setBackground(Color.black);
				lblAviso.setForeground(Color.white);
				dlgWindow.add(lblAviso);
				lblAviso.setText("El Modo Normal ha sido activado.");
				dlgWindow.setVisible(true);

				conexion.logs("[+] " + user, " has activated Normal Mode.");
			}
		}
	}
}