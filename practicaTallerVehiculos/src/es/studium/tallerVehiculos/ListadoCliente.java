package es.studium.tallerVehiculos;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ListadoCliente implements ActionListener, WindowListener
{
	Frame windowListadoClientes = new Frame("Listado de Clientes");
	Label lblIdCliente = new Label ("ID:");
	Label lblNombreCliente = new Label ("Nombre:");
	Label lblTelefonoCliente = new Label ("Teléfono:");
	Label lblEmailCliente = new Label ("Email:");
	TextArea areaDatos = new TextArea (10, 35);
	Button btnPDF = new Button ("PDF");

	Conexion conexion = new Conexion();
	String user;

	ListadoCliente(String user) {

		// -- Create user --
		this.user = user;

		// --- Main panel configuration ---
		windowListadoClientes.setLayout(null);

		// --- Main window settings ---
		windowListadoClientes.addWindowListener(this);
		windowListadoClientes.setResizable(false);
		windowListadoClientes.setSize(500, 400); 
		windowListadoClientes.setLocationRelativeTo(null);
		windowListadoClientes.setBackground(Color.cyan);

		/*
		 * Add components to the main window and
		 * establish their respective locations.
		 */

		// --- ID ---
		lblIdCliente.setBounds(20, 40, 20, 20);
		windowListadoClientes.add(lblIdCliente);

		// --- Name ---
		lblNombreCliente.setBounds(65, 40, 50, 20);
		windowListadoClientes.add(lblNombreCliente);

		// --- Phone ---
		lblTelefonoCliente.setBounds(170, 40, 55, 20);
		windowListadoClientes.add(lblTelefonoCliente);

		// --- Email ---
		lblEmailCliente.setBounds(280, 40, 50, 20);
		windowListadoClientes.add(lblEmailCliente);

		// --- TextArea ---
		// --- Fill TextArea ---
		conexion.fillListadoClientes(areaDatos, user);
		areaDatos.setEditable(false);
		areaDatos.setBounds(20, 70, 460, 250);
		windowListadoClientes.add(areaDatos);

		// --- PDF Button ---
		btnPDF.setEnabled(false);
		btnPDF.setBounds(225, 330, 50, 50);
		windowListadoClientes.add(btnPDF);


		windowListadoClientes.setVisible(true);
	}

	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e)
	{
		windowListadoClientes.setVisible(false);
		conexion.logs("[+] " +user, " has closed 'Listado Cliente' window.");
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	@Override
	public void actionPerformed(ActionEvent e) {}
}
