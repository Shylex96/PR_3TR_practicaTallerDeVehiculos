package es.studium.tallerVehiculos;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;

public class AltaRealizan implements ActionListener, WindowListener, ItemListener {

	// --- General components ---
	Frame windowAltaRealizan = new Frame("Alta de Realización");

	// --- Labels, TextFields & Buttons components declared ---
	Label lblNombreCliente = new Label ("Cliente:");
	Choice choCliente = new Choice();
	Label lblNombreServicio = new Label ("Servicio:");
	Choice choServicio = new Choice();
	Label lblTiempoEmpleado = new Label ("Tiempo empleado:");
	Label lblHoras = new Label("Horas:");
	Choice choHoras = new Choice();
	Label lblMinutos = new Label("Minutos:");
	Choice choMinutos = new Choice();
	Label lblFecha = new Label("Fecha de realización:");
	Label lblDia = new Label("Día:");
	Choice choDia = new Choice();
	Label lblMes = new Label("Mes:");
	Choice choMes = new Choice();
	Label lblAnio = new Label("Año:");
	Choice choAnio = new Choice();

	Button btnAceptar = new Button ("Aceptar");
	Button btnCancelar = new Button ("Cancelar");

	// --- Declared a custom dialog ---
	Dialog dlgWindow = new Dialog(windowAltaRealizan, "Aviso", false);
	Label lblAviso = new Label ("");

	// --- Variable "valorDialogo" is initialized to store the value of the user's dialog choice ---
	int valorDialogo;

	// --- Instantiate object conexion ---
	Conexion conexion = new Conexion();
	String user;

	AltaRealizan(String user) {


		// -- Create user --
		this.user = user;

		// --- Main panel configuration ---
		windowAltaRealizan.setLayout(null);

		// --- Main window settings ---
		windowAltaRealizan.addWindowListener(this);
		windowAltaRealizan.setResizable(false);
		windowAltaRealizan.setSize(350, 300); 
		windowAltaRealizan.setLocationRelativeTo(null);
		windowAltaRealizan.setBackground(Color.cyan);

		/*
		 * Add components to the main window and
		 * establish their respective locations.
		 */

		// --- Client ---
		lblNombreCliente.setBounds(65, 45, 60, 20);
		windowAltaRealizan.add(lblNombreCliente);
		choCliente.setBounds(30, 65, 120, 20);
		windowAltaRealizan.add(choCliente);
		conexion.fillChoiceClientes(choCliente);

		// --- Service ---
		lblNombreServicio.setBounds(225, 45, 60, 20);
		windowAltaRealizan.add(lblNombreServicio);
		choServicio.setBounds(190, 65, 140, 20);
		windowAltaRealizan.add(choServicio);
		conexion.fillChoiceServicios(choServicio);

		// --- Time ---
		lblTiempoEmpleado.setBounds(25, 110, 110, 20);
		windowAltaRealizan.add(lblTiempoEmpleado);

		lblHoras.setBounds(30, 150, 50, 20);
		windowAltaRealizan.add(lblHoras);
		choHoras.setBounds(85, 150, 50, 20);
		windowAltaRealizan.add(choHoras);

		lblMinutos.setBounds(30, 190, 50, 20);
		windowAltaRealizan.add(lblMinutos);
		choMinutos.setBounds(85, 190, 50, 20);
		windowAltaRealizan.add(choMinutos);

		// --- Add hours option (0-71) ---
		for (int i = 0; i <= 71; i++) {
			choHoras.add(Integer.toString(i));
		}

		// --- Add minutes option (1-59) ---
		for (int i = 1; i <= 59; i++) {
			choMinutos.add(Integer.toString(i));
		}

		// --- Date ---
		lblFecha.setBounds(200, 110, 120, 20);
		windowAltaRealizan.add(lblFecha);

		lblAnio.setBounds(205, 140, 30, 20);
		windowAltaRealizan.add(lblAnio);
		choAnio.setBounds(240, 140, 70, 20);
		windowAltaRealizan.add(choAnio);

		lblMes.setBounds(205, 165, 30, 20);
		windowAltaRealizan.add(lblMes);
		choMes.setBounds(240, 165, 70, 20);
		windowAltaRealizan.add(choMes);

		lblDia.setBounds(205, 190, 30, 20);
		windowAltaRealizan.add(lblDia);
		choDia.setBounds(240, 190, 70, 20);
		windowAltaRealizan.add(choDia);

		// --- Set the options for the date fields ---
		// --- Day is on "itemStateChanged" method.
		Calendar calendar = Calendar.getInstance();

		// --- Month ---
		String[] months = {"Elegir..", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
				"Octubre", "Noviembre", "Diciembre"};
		int currentMonth = calendar.get(Calendar.MONTH);

		for (int i = 0; i <= currentMonth +1; i++) {
			choMes.add(months[i]);
		}

		// --- Year ---
		int currentYear = calendar.get(Calendar.YEAR);
		for (int i = 2023; i <= currentYear; i++) {
			choAnio.add(Integer.toString(i));
		}

		// --- Buttons ---
		btnAceptar.setBounds(45, 250, 80, 25);
		windowAltaRealizan.add(btnAceptar);
		btnCancelar.setBounds(220, 250, 80, 25);
		windowAltaRealizan.add(btnCancelar);

		// --- Set the listeners ---
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);
		choMes.addItemListener(this);

		// --- Set window visible ---
		windowAltaRealizan.setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e){}
	@Override
	public void windowClosing(WindowEvent e)
	{
		// --- A dialog will appear depending on the value of the variable ---
		if (dlgWindow.isActive() && valorDialogo == 1) {

			dlgWindow.setVisible(false);
			windowAltaRealizan.setVisible(false);
			conexion.logs("[+] " +user, " has canceled 'Alta Realizan' window.");

		} else if ((dlgWindow.isActive() && valorDialogo == 2)){

			dlgWindow.setVisible(false);
			conexion.logs("[+] " +user, " had an error in 'Alta Realizan' window.");

		} else if ((dlgWindow.isActive() && valorDialogo == 3)){

			dlgWindow.setVisible(false);
			conexion.logs("[+] " +user, " has successfully registered in the window 'Alta Realizan'.");

		}else {

			windowAltaRealizan.setVisible(false);
			conexion.logs("[+] " +user, " has closed 'Alta Realizan' window.");
		}
	}
	@Override
	public void windowClosed(WindowEvent e){}
	@Override
	public void windowIconified(WindowEvent e){}
	@Override
	public void windowDeiconified(WindowEvent e){}
	@Override
	public void windowActivated(WindowEvent e){}
	@Override
	public void windowDeactivated(WindowEvent e){}
	@Override
	public void actionPerformed(ActionEvent e){

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
			lblAviso.setText("Alta de Realizaciï¿½n cancelada.");
			dlgWindow.setVisible(true);
		}

		// --- If user click on accept button ---
		else if (e.getSource().equals (btnAceptar)) {

			// --- Check if choice index are '0' ---
			if (choCliente.getSelectedIndex()==0 && choServicio.getSelectedIndex()==0) {

				valorDialogo = 2;
				lblAviso.setText("Seleccione un Cliente y un Servicio de la Lista.");
				dlgWindow.setVisible(true);

			} else if (choCliente.getSelectedIndex()==0) {

				valorDialogo = 2;
				lblAviso.setText("Seleccione un Cliente de la Lista.");
				dlgWindow.setVisible(true);

			} else if (choServicio.getSelectedIndex()==0) {

				valorDialogo = 2;
				lblAviso.setText("Seleccione un Servicio de la Lista.");
				dlgWindow.setVisible(true);

			} else {
				sendToDB();
			}
		} 
	}

	private void sendToDB()
	{
		/*
				String cliente = choCliente.getSelectedItem();
				String[] clienteDefinitivo = cliente.split("-");
				String clientePosicionUno = clienteDefinitivo[0];
		 */

		String cliente = choCliente.getSelectedItem().split("-")[0];
		String servicio = choServicio.getSelectedItem().split("-")[0];

		String horas = choHoras.getSelectedItem();
		String minutos = choMinutos.getSelectedItem();
		String horasYminutos = horas + ":" + minutos;
		String dia = choDia.getSelectedItem();
		String mes = Integer.toString(choMes.getSelectedIndex()+1);
		String anio = choAnio.getSelectedItem();
		String fecha = anio + "-" + mes + "-" + dia;

		// --- Sentence for BD ---
		String sentencia = "INSERT INTO realizan VALUES (null, '" + horasYminutos + "', '" + fecha + "', '" + cliente + "', '" + servicio + "')";
		System.out.println(sentencia);
		int respuesta = conexion.altaRealizan(sentencia);

		if (respuesta !=0) {
			// --- Show Error ---
			lblAviso.setForeground(Color.red);
			lblAviso.setText("Error en Alta.");
			dlgWindow.setVisible(true);

		} else {

			valorDialogo = 3;
			lblAviso.setForeground(Color.green);
			lblAviso.setText("Alta realizada correctamente.");
			dlgWindow.setVisible(true);

			// --- Generate message to log file --- 
			conexion.logs("[+] " +user, " " + sentencia);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource().equals(choMes)) {
			// --- Get the selected index of the month Choice and
			// get the index of the last month in the Choice ---
			int selectedMonth = choMes.getSelectedIndex() -1; // "-1" In order to avoid months with wrong days
			int lastMonthIndex = choMes.getItemCount() - 2; // "-1" In order to select last Month.

			// --- Check if the selected month is the last month in the Choice ---
			if (selectedMonth == lastMonthIndex) {
				Calendar calendar = Calendar.getInstance();
				int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

				// --- Add the days from 1 to the current day to the "choDia" Choice ---
				for (int i = 1; i <= currentDay; i++) {
					choDia.add(Integer.toString(i));
				}
				// --- Update the days Choice selection ---
				updateDaysChoice(currentDay);
			} else {
				// --- Get the number of days in the selected month and update 
				// the days Choice with the correct number of days ---
				int daysInMonth = getDaysInMonth(selectedMonth);
				updateDaysChoice(daysInMonth);
			}
		}
	}

	private void updateDaysChoice(int daysInMonth) {
		// --- Remove all existing items from the days Choice ---
		choDia.removeAll();

		// --- Add the days from 1 to the specified number of days to the days Choice ---
		for (int i = 1; i <= daysInMonth; i++) {
			choDia.add(Integer.toString(i));
		}
		// --- To ensure the changes were made correctly ---
		windowAltaRealizan.validate();
	}

	private int getDaysInMonth(int month) {
		// --- Get the maximum number of days for the specified month ---
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, month);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
}