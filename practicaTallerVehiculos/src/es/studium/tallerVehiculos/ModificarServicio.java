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

public class ModificarServicio implements ActionListener, WindowListener {

	Frame windowModificarServicio = new Frame("Modificar Servicio");
	Label lblPrincipal = new Label("Elegir el servicio a modificar:");
	Choice choServicios = new Choice();
	Button btnModificar = new Button("Modificar");

	Frame windowModificandoServicio = new Frame("Modificando Servicio");
	Label lblCambiarDatos = new Label("Modifique los campos del Servicio:");
	Label lblTipoServicio = new Label("Tipo Servicio:");
	TextField txtTipoServicio = new TextField(10);
	Label lblDescripcionServicio = new Label("Descripci√≥n del Servicio:");
	TextField txtDescripcionServicio = new TextField(10);
	Label lblPrecioServicio = new Label("Precio del Servicio:");
	TextField txtPrecioServicio = new TextField(10);
	Button btnAceptarModificacion = new Button("Modificar");
	Button btnCancelarModificacion = new Button("Cancelar");

	Dialog dlgAviso = new Dialog(windowModificandoServicio, "Aviso", false);
	Label lblAviso = new Label("");
	String idServicio = "";

	// --- Variable "valorDialogo" is initialized to store the value of the user's dialog choice ---
	int valorDialogo;

	// --- Instantiate object conexion ---
	Conexion conexion = new Conexion();
	String user;

	ModificarServicio(String user) {

		// -- Create user --
		this.user = user;

		// --- Main panel configuration ---
		windowModificarServicio.setLayout(null);

		// --- Main window settings ---
		windowModificarServicio.addWindowListener(this);
		windowModificarServicio.setResizable(false);
		windowModificarServicio.setSize(250, 200);
		windowModificarServicio.setLocationRelativeTo(null);
		windowModificarServicio.setBackground(Color.cyan);

		/*
		 * Add components to the main window and establish their respective locations.
		 */

		// --- Title ---
		lblPrincipal.setBounds(50, 45, 180, 25);
		windowModificarServicio.add(lblPrincipal);

		// --- List ---
		choServicios.setBounds(50, 80, 150, 30);
		conexion.fillChoiceServicios(choServicios);
		windowModificarServicio.add(choServicios);

		// --- Modify Button ---
		btnModificar.setBounds(80, 150, 90, 25);
		btnModificar.addActionListener(this);
		windowModificarServicio.add(btnModificar);

		windowModificarServicio.setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (windowModificandoServicio.isActive() && valorDialogo == 1) {
			windowModificandoServicio.setVisible(false);

		}else if (windowModificandoServicio.isActive() && valorDialogo == 2){
			windowModificandoServicio.setVisible(false);

		} else if (dlgAviso.isActive() && valorDialogo == 2) {
			windowModificandoServicio.setVisible(false);
			dlgAviso.setVisible(false);

		} else if (dlgAviso.isActive() && valorDialogo == 3) {
			windowModificandoServicio.setVisible(false);
			dlgAviso.setVisible(false);
			windowModificarServicio.setVisible(true);
		} else if (dlgAviso.isActive() && valorDialogo == 4) {
			dlgAviso.setVisible(false);
		} else {
			windowModificarServicio.setVisible(false);
			conexion.logs("[+] " +user, " has closed 'Modificar Servicio' window.");
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



		// --- If user clic on modify button ---
		if (e.getSource().equals(btnModificar)) {

			btnAceptarModificacion.addActionListener(this);
			btnCancelarModificacion.addActionListener(this);

			// --- If service is not the element in position "0" (Select service..) ---
			// --- Display the new frame to modify service data ---
			if (choServicios.getSelectedIndex() != 0) {

				windowModificandoServicio.setLayout(null);
				windowModificandoServicio.addWindowListener(this);
				windowModificandoServicio.setResizable(false);
				windowModificandoServicio.setSize(350, 250);
				windowModificandoServicio.setLocationRelativeTo(null);
				windowModificandoServicio.setBackground(Color.cyan);

				// --- Title ---
				lblCambiarDatos.setBounds(90, 40, 250, 25);
				windowModificandoServicio.add(lblCambiarDatos);

				// --- Service Type ---
				lblTipoServicio.setBounds(30, 90, 150, 25);
				windowModificandoServicio.add(lblTipoServicio);
				txtTipoServicio.setBounds(190, 90, 120, 25);
				windowModificandoServicio.add(txtTipoServicio);

				// --- Service Description ---
				lblDescripcionServicio.setBounds(30, 120, 150, 25);
				windowModificandoServicio.add(lblDescripcionServicio);
				txtDescripcionServicio.setBounds(190, 120, 120, 25);
				windowModificandoServicio.add(txtDescripcionServicio);

				// --- Service Price ---
				lblPrecioServicio.setBounds(30, 150, 150, 25);
				windowModificandoServicio.add(lblPrecioServicio);
				txtPrecioServicio.setBounds(190, 150, 120, 25);
				windowModificandoServicio.add(txtPrecioServicio);

				// --- Buttons ---
				btnAceptarModificacion.setBounds(70, 200, 70, 25);
				windowModificandoServicio.add(btnAceptarModificacion);
				btnCancelarModificacion.setBounds(200, 200, 70, 25);
				windowModificandoServicio.add(btnCancelarModificacion);

				String tabla[] = choServicios.getSelectedItem().split("-");
				String resultado = conexion.getDatosEdicionServicio(tabla[0]);

				//resultado = resultado + " ";
				String datos[] = resultado.split("-");
				idServicio = datos[0];

				txtTipoServicio.setText(datos[1]);
				txtDescripcionServicio.setText(datos[2]);
				txtPrecioServicio.setText(datos[3]);


			}

			windowModificarServicio.setVisible(false);
			valorDialogo = 1;
			windowModificandoServicio.setVisible(true);

			// --- If user clic to cancel modification button ---
		} else if (e.getSource().equals(btnCancelarModificacion)) {

			lblAviso.setText("ModificaciÛn de Servicio cancelada.");
			dlgAviso.setVisible(true);

			valorDialogo = 2;

			// --- If user clic to accept modification button ---
		} else if (e.getSource().equals(btnAceptarModificacion)) {

			// --- Modify ---
			// --- Construct an SQL statement to update the client's data in the database ---

			String sentencia = "UPDATE servicios SET tipoServicio ='" + txtTipoServicio.getText()
			+ "', descripcionServicio = '" + txtDescripcionServicio.getText() + "', precioServicio = '"
			+ txtPrecioServicio.getText() + "' WHERE idServicio =" + idServicio + ";";
			int respuesta = conexion.modificarServicio(sentencia);

			// --- If the SQL statement did not execute correctly ---
			if (respuesta != 0) {

				valorDialogo = 4;
				// --- Show error dialog ---
				lblAviso.setText("Error en la Modificaci√≥n");
				dlgAviso.setBackground(Color.red);
				dlgAviso.setVisible(true);


			} else {

				// --- If the SQL statement executed correctly ---
				// --- Show success dialog ---
				lblAviso.setText("Cambios realizados con Èxito.");
				lblAviso.setForeground(Color.black);
				dlgAviso.setBackground(Color.green);
				dlgAviso.setVisible(true);

				// -- Generate message to log file  -- 
				conexion.logs("[+] " +user, " " +sentencia);
				
				// --- Fill services choice with the updates ---
				conexion.fillChoiceServicios(choServicios);

				valorDialogo = 3;

			}
		}
	}
}



