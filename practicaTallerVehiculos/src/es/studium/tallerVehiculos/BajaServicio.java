package es.studium.tallerVehiculos;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class BajaServicio implements ActionListener, WindowListener {
	Frame windowBajaServicio = new Frame("Baja de Servicio");
	Label lblPrincipal = new Label ("Elegir el servicio a eliminar:");
	Choice choServicios = new Choice();
	Button btnEliminar = new Button ("Eliminar");

	Dialog dlgAviso = new Dialog(windowBajaServicio, "Aviso", false);
	Label lblAviso = new Label ("");
	Button btnSI = new Button ("SÍ");
	Button btnNO = new Button ("NO");


	Conexion conexion = new Conexion();
	String user;


	BajaServicio(String user) {

		// -- Create user --
		this.user = user;

		// --- Main panel configuration ---
		windowBajaServicio.setLayout(null);

		// --- Main window settings ---
		windowBajaServicio.addWindowListener(this);
		windowBajaServicio.setResizable(false);
		windowBajaServicio.setSize(250, 200); 
		windowBajaServicio.setLocationRelativeTo(null);
		windowBajaServicio.setBackground(Color.cyan);


		/*
		 * Add components to the main window and
		 * establish their respective locations.
		 */

		// --- Title ---
		lblPrincipal.setBounds(50, 45, 180, 25);
		windowBajaServicio.add(lblPrincipal);

		// --- List ---
		choServicios.setBounds(40, 80, 180, 30);
		conexion.fillChoiceServicios(choServicios);
		windowBajaServicio.add(choServicios);

		// --- Delete Button ---
		btnEliminar.setBounds(80, 150, 90, 25);
		btnEliminar.addActionListener(this);
		windowBajaServicio.add(btnEliminar);

		// --- Set window visible ---
		windowBajaServicio.setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) 
	{
		if (dlgAviso.isActive()) {
			dlgAviso.setVisible(false);
		} else {
			windowBajaServicio.setVisible(false);
			conexion.logs("[+] " +user, " has closed 'Baja Servicio' window.");
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
	@Override
	public void actionPerformed(ActionEvent e) {

		// --- If delete button has been clicked ---
		if (e.getSource().equals(btnEliminar)) {

			// --- Create dialog but not visible yet ---
			dlgAviso.setSize(350, 110); 
			dlgAviso.setLayout(null);
			dlgAviso.addWindowListener(this);
			dlgAviso.setResizable(false);
			dlgAviso.setLocationRelativeTo(null);
			dlgAviso.setBackground(Color.red);
			lblAviso.setForeground(Color.black);

			// --- If user selected isn't "0" index, then delete is available ---
			if (choServicios.getSelectedIndex()!=0) {

				// --- If user click to delete button a dialog asking again will appear ---

				// --- Set positions and text ---
				lblAviso.setBounds(10, 30, 330, 25);
				lblAviso.setText("• ¿Seguro de eliminar : " +choServicios.getSelectedItem()+ "?"); // ASCII 7
				dlgAviso.add(lblAviso);

				btnSI.setBounds(100, 60, 50, 25);
				btnSI.addActionListener(this);
				dlgAviso.add(btnSI);
				btnNO.setBounds(180, 60, 50, 25);
				btnNO.addActionListener(this);
				dlgAviso.add(btnNO);

				dlgAviso.setVisible(true);
			}

		} else if (e.getSource().equals(btnNO)) {

			dlgAviso.setVisible(false);

			// --- If user confirm, then delete process will run ---
		} else if (e.getSource().equals(btnSI)) {

			String tabla[] = choServicios.getSelectedItem().split("-");
			int respuesta = conexion.borrarServicio(tabla[0], user);
			if (respuesta==0) {

				dlgAviso.setSize(350, 110); 
				dlgAviso.setLayout(null);
				dlgAviso.addWindowListener(this);
				dlgAviso.setResizable(false);
				dlgAviso.setLocationRelativeTo(null);
				dlgAviso.setBackground(Color.green);
				dlgAviso.remove(btnSI);
				dlgAviso.remove(btnNO);

				dlgAviso.add(lblAviso);
				lblAviso.setText("Servicio: " +choServicios.getSelectedItem() + " eliminado correctamente.");
				conexion.logs("[+] " +user, " has successfully performed a delete of the Service '" + choServicios.getSelectedItem() + "' in the window 'Baja Servicio'." );
				conexion.fillChoiceServicios(choServicios);
				dlgAviso.setVisible(true);

			}
		}
	}
}

