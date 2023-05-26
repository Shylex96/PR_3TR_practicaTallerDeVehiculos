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


public class AltaCliente implements ActionListener, WindowListener{

	// --- General components ---
	Frame windowAltaCliente = new Frame("Alta de Cliente");
	Label lblPrincipal = new Label ("Alta de Cliente");

	// --- Labels, TextFields & Buttons components declared ---
	Label lblNombreCliente = new Label ("Nombre del Cliente:");
	TextField txtNombreCliente = new TextField (15);
	Label lblTelefonoCliente = new Label ("Teléfono del Cliente:");
	TextField txtTelefonoCliente = new TextField (15);
	Label lblEmailCliente = new Label ("Email del Cliente:");
	TextField txtEmailCliente = new TextField (15);
	Button btnAceptar = new Button ("Aceptar");
	Button btnCancelar = new Button ("Cancelar");

	// --- Declared a custom dialog ---
	Dialog dlgWindow = new Dialog(windowAltaCliente, "Aviso", false);
	Label lblAviso = new Label ("");

	// --- Variable "valorDialogo" is initialized to store the value of the user's dialog choice ---
	int valorDialogo;

	// --- Instantiate object conexion ---
	Conexion conexion = new Conexion();
	String user;

	AltaCliente(String user) {

		// -- Create user --
		this.user = user;

		// --- Main panel configuration ---
		windowAltaCliente.setLayout(null);

		// --- Main window settings ---
		windowAltaCliente.addWindowListener(this);
		windowAltaCliente.setResizable(false);
		windowAltaCliente.setSize(350, 250); 
		windowAltaCliente.setLocationRelativeTo(null);
		windowAltaCliente.setBackground(Color.cyan);

		/*
		 * Add components to the main window and
		 * establish their respective locations.
		 */

		// --- Title ---
		lblPrincipal.setBounds(135, 40, 100, 25);
		windowAltaCliente.add(lblPrincipal);

		// --- User ---
		lblNombreCliente.setBounds(30, 90, 150, 25);
		windowAltaCliente.add(lblNombreCliente);
		txtNombreCliente.setBounds(185, 90, 130, 25);
		windowAltaCliente.add(txtNombreCliente);

		// --- Phone ---
		lblTelefonoCliente.setBounds(30, 120, 150, 25);
		windowAltaCliente.add(lblTelefonoCliente);
		txtTelefonoCliente.setBounds(185, 120, 130, 25);
		windowAltaCliente.add(txtTelefonoCliente);

		// --- Email ---
		lblEmailCliente.setBounds(30, 150, 150, 25);
		windowAltaCliente.add(lblEmailCliente);
		txtEmailCliente.setBounds(185, 150, 130, 25);
		windowAltaCliente.add(txtEmailCliente);

		// --- Buttons ---
		btnAceptar.setBounds(40, 190, 80, 25);
		windowAltaCliente.add(btnAceptar);
		btnCancelar.setBounds(210, 190, 80, 25);
		windowAltaCliente.add(btnCancelar);

		// --- Set the listeners ---
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);

		// --- Set window visible ---
		windowAltaCliente.setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) 
	{
		// --- A dialog will appear depending on the value of the variable ---
		if (dlgWindow.isActive() && valorDialogo == 1) {

			dlgWindow.setVisible(false);
			windowAltaCliente.setVisible(false);
			conexion.logs("[+] " +user, " has canceled 'Alta Cliente' window.");

		} else if ((dlgWindow.isActive() && valorDialogo == 2)){

			dlgWindow.setVisible(false);
			conexion.logs("[+] " +user, " had an error in 'Alta Cliente' window.");

		} else if ((dlgWindow.isActive() && valorDialogo == 3)){

			dlgWindow.setVisible(false);
			conexion.logs("[+] " +user, " has successfully registered in the window 'Alta Cliente'.");

		}else {

			windowAltaCliente.setVisible(false);
			conexion.logs("[+] " +user, " has closed 'Alta Cliente' window.");
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
		dlgWindow.add(lblAviso);

		// --- If user click on cancel button ---
		if (e.getSource().equals(btnCancelar)) {

			valorDialogo = 1;
			lblAviso.setText("Alta de Cliente cancelada.");
			dlgWindow.setVisible(true);
		}

		// --- If user click on accept button ---
		else if (e.getSource().equals (btnAceptar)) {

			// --- Check if "txtNombreCliente" is an empty field ---
			if (txtNombreCliente.getText().equals("")) {

				valorDialogo = 2;
				lblAviso.setText("El campo de nombre no puede estar vacío.");
				dlgWindow.setVisible(true);
			}

			// --- Check if "txtTelefonoCliente" AND "txtEmailCliente" has empty fields ---
			else if (txtTelefonoCliente.getText().equals("") && txtEmailCliente.getText().equals("")) {

				valorDialogo = 2;
				lblAviso.setText("Debe estar relleno algún campo de contacto.");
				dlgWindow.setVisible(true);
			}

			else if (!txtTelefonoCliente.getText().equals("") && txtEmailCliente.getText().equals("")) {

				if (!txtTelefonoCliente.getText().matches("^[0-9]{9}")) {
					valorDialogo = 2;
					lblAviso.setText("El teléfono debe contener 9 números.");
					dlgWindow.setVisible(true);
				} 
				else {
					sendToDB();
				}
			}

			else if (!txtEmailCliente.getText().equals("") && txtTelefonoCliente.getText().equals("")) {
				// --- If email is a valid one, register the user ---	
				if (txtEmailCliente.getText().matches("^[A-Za-z0-9]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

					sendToDB();

				} else {
					// --- If email doesn't match verification ---

					valorDialogo = 2;
					lblAviso.setText("El email introducido tiene formato no vÃ¡lido.");
					dlgWindow.setVisible(true);
				}
			}

			else if (!txtTelefonoCliente.getText().equals("") && !txtEmailCliente.getText().equals("")) {

				if(txtTelefonoCliente.getText().matches("^[0-9]{9}") && txtEmailCliente.getText().matches("^[A-Za-z0-9]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$") ) {

					sendToDB();

				}else if (!(txtTelefonoCliente.getText().matches("^[0-9]{9}") && txtEmailCliente.getText().matches("^[A-Za-z0-9]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$") )){

					// Email & Phone are wrong.
					valorDialogo = 2;
					lblAviso.setText("Los campos de email y telÃ©fono son errÃ³neos.");
					dlgWindow.setVisible(true);

				}else if (!(txtTelefonoCliente.getText().matches("^[0-9]{9}"))) {

					// Phone is wrong
					valorDialogo = 2;
					lblAviso.setText("El telÃ©fono introducido es errÃ³neo.");
					dlgWindow.setVisible(true);

				}else{

					// Email is wrong
					valorDialogo = 2;
					lblAviso.setText("El email introducido es errÃ³neo.");
					dlgWindow.setVisible(true);
				}
			}
		}
	}





	// --- If fields has been filled correctly, run this method ---
	private void sendToDB() {
		String sentencia = "INSERT INTO clientes VALUES (null, '"+txtNombreCliente.getText()+"' ,"
				+ " '"+txtTelefonoCliente.getText()+"' , '"+txtEmailCliente.getText()+"');";
		int respuesta = conexion.altaCliente(sentencia);

		if (respuesta !=0) {
			// Show Error 
			lblAviso.setForeground(Color.red);
			lblAviso.setText("Error en Alta.");
			dlgWindow.setVisible(true);

		} else {

			valorDialogo = 3;
			txtNombreCliente.setText("");
			txtTelefonoCliente.setText("");
			txtEmailCliente.setText("");
			lblAviso.setForeground(Color.green);
			lblAviso.setText("Alta realizada correctamente.");
			dlgWindow.setVisible(true);

			// -- Generate message to log file  -- 
			conexion.logs("[+] " +user, " " + sentencia);
		}
	}
}


