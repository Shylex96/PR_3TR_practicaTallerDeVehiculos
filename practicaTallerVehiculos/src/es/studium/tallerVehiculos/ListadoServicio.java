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

public class ListadoServicio implements ActionListener, WindowListener {
	Frame windowListadoServicios = new Frame("Listado de Servicios");
	Label lblIdServicio = new Label ("ID:");
	Label lblPrecioServicio = new Label ("Precio:");
	Label lblTipoServicio = new Label ("Tipo Servicio:");
	Label lblDescripcion = new Label ("Descripción:");
	TextArea areaDatos = new TextArea (10, 35);
	Button btnPDF = new Button ("PDF");

	Conexion conexion = new Conexion();
	String user;

	ListadoServicio(String user) {

		// -- Create user --
		this.user = user;

		// --- Main panel configuration ---
		windowListadoServicios.setLayout(null);

		// --- Main window settings ---
		windowListadoServicios.addWindowListener(this);
		windowListadoServicios.setResizable(false);
		windowListadoServicios.setSize(500, 400); 
		windowListadoServicios.setLocationRelativeTo(null);
		windowListadoServicios.setBackground(Color.cyan);

		/*
		 * Add components to the main window and
		 * establish their respective locations.
		 */

		// --- ID ---
		lblIdServicio.setBounds(20, 40, 20, 20);
		windowListadoServicios.add(lblIdServicio);

		// --- Service Price ---
		lblPrecioServicio.setBounds(65, 40, 50, 20);
		windowListadoServicios.add(lblPrecioServicio);

		// --- Service Type ---
		lblTipoServicio.setBounds(170, 40, 75, 20);
		windowListadoServicios.add(lblTipoServicio);

		// --- Service Description ---
		lblDescripcion.setBounds(280, 40, 120, 20);
		windowListadoServicios.add(lblDescripcion);

		// --- TextArea ---
		// --- Fill TextArea ---
		conexion.fillListadoServicio(areaDatos, user);
		areaDatos.setEditable(false);
		areaDatos.setBounds(20, 70, 460, 250);
		windowListadoServicios.add(areaDatos);

		// --- PDF Button ---
		btnPDF.setEnabled(false);
		btnPDF.setBounds(225, 330, 50, 50);
		windowListadoServicios.add(btnPDF);


		windowListadoServicios.setVisible(true);
	}

	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e)
	{
		windowListadoServicios.setVisible(false);
		conexion.logs("[+] " +user, " has closed 'Listado Servicio' window.");
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	@Override
	public void actionPerformed(ActionEvent e) {}
}


