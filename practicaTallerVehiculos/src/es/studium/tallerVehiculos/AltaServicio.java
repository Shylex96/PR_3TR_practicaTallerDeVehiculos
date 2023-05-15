package es.studium.tallerVehiculos;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AltaServicio implements ActionListener, WindowListener{

	// --- General components ---
	Frame windowAltaServicio = new Frame("Alta de Servicio");
	Label lblPrincipal = new Label ("Alta de Servicio");

	// --- Labels, TextFields & Buttons components declared ---
	Label lblTipoServicio = new Label ("Tipo de Servicio:");
	TextField txtTipoServicio = new TextField (15);
	Label lblDescripcionServicio = new Label ("Descripción del Servicio:");
	TextField txtDescripcionServicio = new TextField (15);
	Label lblPrecioServicio = new Label ("Precio del Servicio:");
	TextField txtPrecioServicio = new TextField (15);
	Button btnAceptar = new Button ("Aceptar");
	Button btnCancelar = new Button ("Cancelar");

	// --- Declared a custom dialog ---
	Dialog dlgWindow = new Dialog(windowAltaServicio, "Aviso", false);
	Label lblAviso = new Label ("");
	Label lblAviso2 = new Label ("");

	// --- Variable "valorDialogo" is initialized to store the value of the user's dialog choice ---
	int valorDialogo;

	// --- Instantiate object conexion ---
	Conexion conexion = new Conexion();
	String user;

	AltaServicio(String user) {

		// -- Create user --
		this.user = user;

		// --- Main panel configuration ---
		windowAltaServicio.setLayout(null);

		// --- Main window settings ---
		windowAltaServicio.addWindowListener(this);
		windowAltaServicio.setResizable(false);
		windowAltaServicio.setSize(350, 250); 
		windowAltaServicio.setLocationRelativeTo(null);
		windowAltaServicio.setBackground(Color.cyan);

		/*
		 * Add components to the main window and
		 * establish their respective locations.
		 */

		// --- Title ---
		lblPrincipal.setBounds(135, 40, 100, 25);
		windowAltaServicio.add(lblPrincipal);

		// --- Service Type ---
		lblTipoServicio.setBounds(30, 90, 150, 25);
		windowAltaServicio.add(lblTipoServicio);
		txtTipoServicio.setBounds(185, 90, 130, 25);
		windowAltaServicio.add(txtTipoServicio);

		// --- Service Description ---
		lblDescripcionServicio.setBounds(30, 120, 150, 25);
		windowAltaServicio.add(lblDescripcionServicio);
		txtDescripcionServicio.setBounds(185, 120, 130, 25);
		windowAltaServicio.add(txtDescripcionServicio);

		// --- Service Cost ---
		lblPrecioServicio.setBounds(30, 150, 150, 25);
		windowAltaServicio.add(lblPrecioServicio);
		txtPrecioServicio.setBounds(185, 150, 130, 25);
		windowAltaServicio.add(txtPrecioServicio);

		// --- Buttons ---
		btnAceptar.setBounds(40, 190, 80, 25);
		windowAltaServicio.add(btnAceptar);
		btnCancelar.setBounds(210, 190, 80, 25);
		windowAltaServicio.add(btnCancelar);

		// --- Set the listeners ---
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);

		// --- Set window visible ---
		windowAltaServicio.setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) 
	{
		// --- A dialog will appear depending on the value of the variable ---
		if ((dlgWindow.isActive() && valorDialogo == 1)) {

			dlgWindow.setVisible(false);
			windowAltaServicio.setVisible(false);
			conexion.logs("[+] " +user, " has canceled 'Alta Servicio' window.");

		} else if ((dlgWindow.isActive() && valorDialogo == 2)){

			dlgWindow.setVisible(false);
			conexion.logs("[+] " +user, " had an error in 'Alta Servicio' window.");

		} else if ((dlgWindow.isActive() && valorDialogo == 3)){

			dlgWindow.setVisible(false);
			conexion.logs("[+] " +user, " has successfully registered in the window 'Alta Servicio'.");

		}else {

			windowAltaServicio.setVisible(false);
			conexion.logs("[+] " +user, " has closed 'Alta Servicio' window.");
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {	}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
	@Override
	public void actionPerformed(ActionEvent e) {

		// --- Set dialog config ---
		dlgWindow.setLayout(new FlowLayout());
		dlgWindow.addWindowListener(this);
		dlgWindow.setResizable(false);
		dlgWindow.setSize(300, 75);
		dlgWindow.setLocationRelativeTo(null);
		dlgWindow.setBackground(Color.black);
		lblAviso.setForeground(Color.white);
		lblAviso2.setForeground(Color.white);
		dlgWindow.add(lblAviso);
		dlgWindow.add(lblAviso2);

		// --- If user click on cancel button ---
		if (e.getSource().equals(btnCancelar)) {

			valorDialogo = 1;
			lblAviso.setText("Alta de Servicio cancelada.");
			dlgWindow.setVisible(true);
		}

		// --- If user click on accept button ---
		else if (e.getSource().equals (btnAceptar)) {

			// --- Check if "txtTipoServicio" is an empty field ---
			if (txtTipoServicio.getText().equals("") || txtDescripcionServicio.getText().equals("") || txtPrecioServicio.getText().equals("")) {

				valorDialogo = 2;
				lblAviso.setText("No puede haber campos vacíos.");
				dlgWindow.setVisible(true);

			} else if (!txtPrecioServicio.getText().equals("")) {

				// 1-4 digits with dot or coma and 1-2 decimals. It can be with no decimals until 4 digits.
				if (!txtPrecioServicio.getText().matches("^[0-9]{1,4}([.][0-9]{1,2})?$")) {
					valorDialogo = 2;
					dlgWindow.setSize(300, 100);
					lblAviso.setText("El precio tiene un formato no permitido.");
					lblAviso2.setText("Usa puntos en vez de comas para decimales.");
					dlgWindow.setVisible(true);

				} else {
					sendToDB();
				}
			} else {

				sendToDB();
			}
		}
	}


	// --- If fields has been filled correctly, run this method ---
	private void sendToDB() {
		String sentencia = "INSERT INTO servicios VALUES (null, '"+txtTipoServicio.getText()+"' ,"
				+ " '"+txtDescripcionServicio.getText()+"' , '"+txtPrecioServicio.getText()+"');";
		int respuesta = conexion.altaServicio(sentencia);

		if (respuesta !=0) {
			// Show Error 
			lblAviso.setForeground(Color.red);
			lblAviso.setText("Error en Alta.");
			dlgWindow.setVisible(true);

		} else {
			valorDialogo = 3;
			txtTipoServicio.setText("");
			txtDescripcionServicio.setText("");
			txtPrecioServicio.setText("");
			lblAviso.setForeground(Color.green);
			lblAviso.setText("Alta realizada correctamente.");
			dlgWindow.setVisible(true);

			// -- Generate message to log file  -- 
			conexion.logs("[+] " +user, " " + sentencia);
		}
	}
}
