package es.studium.tallerVehiculos;

import java.awt.Button;
import java.awt.Choice;
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

public class ModificarCliente implements ActionListener, WindowListener {

	Frame windowModificarCliente = new Frame("Modificar Cliente");
	Label lblPrincipal = new Label("Elegir el cliente a modificar:");
	Choice choClientes = new Choice();
	Button btnModificar = new Button("Modificar");

	Frame windowModificandoCliente = new Frame("Modificando Cliente");
	Label lblCambiarDatos = new Label("Modifique los campos del Cliente");
	Label lblNombreNuevo = new Label("Nuevo nombre:");
	TextField txtNombreNuevo = new TextField(10);
	Label lblTelefonoNuevo = new Label("Nuevo teléfono:");
	TextField txtTelefonoNuevo = new TextField(10);
	Label lblCorreoNuevo = new Label("Nuevo correo:");
	TextField txtCorreoNuevo = new TextField(10);
	Button btnAceptarModificacion = new Button("Modificar");
	Button btnCancelarModificacion = new Button("Cancelar");

	Dialog dlgAviso = new Dialog(windowModificarCliente, "Aviso", false);
	Label lblAviso = new Label("");
	String idCliente = "";

	// --- Variable "valorDialogo" is initialized to store the value of the user's dialog choice ---
	int valorDialogo;

	// --- Instantiate object conexion ---
	Conexion conexion = new Conexion();
	String user;

	ModificarCliente(String user) {

		// -- Create user --
		this.user = user;

		// --- Main panel configuration ---
		windowModificarCliente.setLayout(null);

		// --- Main window settings ---
		windowModificarCliente.addWindowListener(this);
		windowModificarCliente.setResizable(false);
		windowModificarCliente.setSize(250, 200);
		windowModificarCliente.setLocationRelativeTo(null);
		windowModificarCliente.setBackground(Color.cyan);

		/*
		 * Add components to the main window and establish their respective locations.
		 */

		// --- Title ---
		lblPrincipal.setBounds(50, 45, 180, 25);
		windowModificarCliente.add(lblPrincipal);

		// --- List ---
		choClientes.setBounds(50, 80, 150, 30);
		conexion.fillChoiceClientes(choClientes);
		windowModificarCliente.add(choClientes);

		// --- Modify Button ---
		btnModificar.setBounds(80, 150, 90, 25);
		btnModificar.addActionListener(this);
		windowModificarCliente.add(btnModificar);

		windowModificarCliente.setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (windowModificandoCliente.isActive() && valorDialogo == 1) {
			windowModificandoCliente.setVisible(false);

		}else if (windowModificandoCliente.isActive() && valorDialogo == 2){
			windowModificandoCliente.setVisible(false);

		} else if (dlgAviso.isActive() && valorDialogo == 2) {
			windowModificandoCliente.setVisible(false);
			dlgAviso.setVisible(false);

		} else if (dlgAviso.isActive() && valorDialogo == 3) {
			windowModificandoCliente.setVisible(false);
			dlgAviso.setVisible(false);
			windowModificarCliente.setVisible(true);
		} else {
			windowModificarCliente.setVisible(false);
			conexion.logs("[+] " +user, " has closed 'Modificar Cliente' window.");
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

		// --- Set dialog config ---
		dlgAviso.setLayout(new FlowLayout());
		dlgAviso.addWindowListener(this);
		dlgAviso.setSize(300, 75);
		dlgAviso.setLocationRelativeTo(null);
		lblAviso.setForeground(Color.black);
		dlgAviso.add(lblAviso);



		// --- If user clic on delete button ---
		if (e.getSource().equals(btnModificar)) {

			btnAceptarModificacion.addActionListener(this);
			btnCancelarModificacion.addActionListener(this);

			// --- If user is not the element in position "0" (Select client..) ---
			// --- Display the new frame to modify client data ---
			if (choClientes.getSelectedIndex() != 0) {

				windowModificandoCliente.setLayout(new FlowLayout());
				windowModificandoCliente.addWindowListener(this);
				windowModificandoCliente.setResizable(false);
				windowModificandoCliente.setSize(250, 220);
				windowModificandoCliente.setLocationRelativeTo(null);
				windowModificandoCliente.setBackground(Color.cyan);
				windowModificandoCliente.add(lblCambiarDatos);
				windowModificandoCliente.add(lblNombreNuevo);
				windowModificandoCliente.add(txtNombreNuevo);
				windowModificandoCliente.add(lblTelefonoNuevo);
				windowModificandoCliente.add(txtTelefonoNuevo);
				windowModificandoCliente.add(lblCorreoNuevo);
				windowModificandoCliente.add(txtCorreoNuevo);
				windowModificandoCliente.add(btnAceptarModificacion);
				windowModificandoCliente.add(btnCancelarModificacion);

				// --- Get the selected item in a "choClientes" object ---
				String tabla[] = choClientes.getSelectedItem().split("-");
				// --- Call the "getDatosEdicionClients" method on a "conexion" object to obtain data for the selected client ---
				String resultado = conexion.getDatosEdicionClients(tabla[0]);

				// --- Add a space character to the end of "resultado" ---

				/*
				 * In order to avoid a bug where the user did not write a mail, separating with the 
				 * split "-" causes a null exception interpreted by the program and it breaks the program.
				 * So I add a space and now is a "whitespace" instead of "-".
				 */

				resultado = resultado + " ";
				// --- Split "resultado" into an array "datos" using the delimiter "-" ---
				String datos[] = resultado.split("-");
				// --- The first element of "datos" (the client's ID) is assigned to a global variable called "idCliente" ---
				idCliente = datos[0];

				// --- Set the values of the text fields in the user interface using the remaining elements of "datos" ---
				txtNombreNuevo.setText(datos[1]);
				txtTelefonoNuevo.setText(datos[2]);
				// --- If the client's mail contain a space character (improvise bug solution at 160 line),
				// set the "txtCorreoNuevo" text field to non space ---
				if (datos[3].equals(" ")) {
					txtCorreoNuevo.setText("");
				} else {
					txtCorreoNuevo.setText(datos[3]);
				}

				windowModificarCliente.setVisible(false);
				valorDialogo = 1;
				windowModificandoCliente.setVisible(true);
			}

			// --- If user clic to cancel modification button ---
		} else if (e.getSource().equals(btnCancelarModificacion)) {

			lblAviso.setText("Modificación de Servicio cancelada.");
			dlgAviso.setVisible(true);

			valorDialogo = 2;

			// --- If user clic to accept modification button ---
		} else if (e.getSource().equals(btnAceptarModificacion)) {

			// --- Modify ---
			// --- Construct an SQL statement to update the client's data in the database ---

			String sentencia = "UPDATE clientes SET nombreCliente ='" + txtNombreNuevo.getText()
			+ "', telefonoCliente = '" + txtTelefonoNuevo.getText() + "', correoCliente = '"
			+ txtCorreoNuevo.getText() + "' WHERE idCliente =" + idCliente + ";";
			int respuesta = conexion.modificarCliente(sentencia);

			// --- If the SQL statement did not execute correctly ---
			if (respuesta != 0) {

				// --- Show error dialog ---
				lblAviso.setText("Error en la Modificación");
				dlgAviso.setBackground(Color.red);
				dlgAviso.setVisible(true);


			} else {

				// --- If the SQL statement executed correctly ---
				// --- Show success dialog ---
				lblAviso.setText("Cambios realizados con éxito.");
				lblAviso.setForeground(Color.black);
				dlgAviso.setBackground(Color.green);
				dlgAviso.setVisible(true);

				// -- Generate message to log file  -- 
				conexion.logs("[+] " +user, " " +sentencia);

				// --- Fill clients choice with the updates ---
				conexion.fillChoiceClientes(choClientes);

				valorDialogo = 3;

			}
		}
	}
}
