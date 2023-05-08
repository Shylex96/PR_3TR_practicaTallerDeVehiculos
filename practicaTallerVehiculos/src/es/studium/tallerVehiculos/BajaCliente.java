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


public class BajaCliente implements ActionListener, WindowListener {
	Frame windowBajaCliente = new Frame("Baja de Cliente");
	Label lblPrincipal = new Label ("Elegir el cliente"
			+ " a eliminar:");
	Choice choClientes = new Choice();
	Button btnEliminar = new Button ("Eliminar");

	Dialog dlgAviso = new Dialog(windowBajaCliente, "Aviso", false);
	Label lblAviso = new Label ("");
	Button btnSI = new Button ("SÍ");
	Button btnNO = new Button ("NO");


	Conexion conexion = new Conexion();
	String user;


	BajaCliente(String user) {
		
		// -- Create user --
		this.user = user;

		// --- Main panel configuration ---
		windowBajaCliente.setLayout(null);

		// --- Main window settings ---
		windowBajaCliente.addWindowListener(this);
		windowBajaCliente.setResizable(false);
		windowBajaCliente.setSize(250, 200); 
		windowBajaCliente.setLocationRelativeTo(null);
		windowBajaCliente.setBackground(Color.cyan);


		/*
		 * Add components to the main window and
		 * establish their respective locations.
		 */

		// --- Title ---
		lblPrincipal.setBounds(50, 45, 180, 25);
		windowBajaCliente.add(lblPrincipal);

		// --- List ---
		choClientes.setBounds(50, 80, 150, 30);
		conexion.fillChoiceClientes(choClientes);
		windowBajaCliente.add(choClientes);

		// --- Delete Button ---
		btnEliminar.setBounds(80, 150, 90, 25);
		btnEliminar.addActionListener(this);
		windowBajaCliente.add(btnEliminar);

		// --- Set window visible ---
		windowBajaCliente.setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) 
	{
		if (dlgAviso.isActive()) {
			dlgAviso.setVisible(false);
		} else {
			windowBajaCliente.setVisible(false);
			conexion.logs("[+] " +user, " has closed 'Baja Cliente' window.");
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
			if (choClientes.getSelectedIndex()!=0) {

				// --- If we clicked to delete a dialog asking again will appear ---

				// --- Set positions and text ---
				lblAviso.setBounds(10, 30, 330, 25);
				lblAviso.setText("• ¿Seguro de eliminar a: " +choClientes.getSelectedItem()+ "?"); // ASCII 7
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

		} else if (e.getSource().equals(btnSI)) {

			String tabla[] = choClientes.getSelectedItem().split("-");
			int respuesta = conexion.borrarCliente(tabla[0], user);
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
				lblAviso.setText("Cliente: " +choClientes.getSelectedItem() + " eliminado correctamente.");
				conexion.fillChoiceClientes(choClientes);
				dlgAviso.setVisible(true);

			}
		}
	}
}



