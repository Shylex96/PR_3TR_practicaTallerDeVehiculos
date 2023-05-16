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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Login implements ActionListener, WindowListener, KeyListener {

	// --- General Components ---
	Frame windowLogin = new Frame("Login");
	Label lblUser = new Label ("Usuario:");
	Label lblPassword = new Label ("Clave:");
	TextField txtUser = new TextField(10);
	TextField txtPassword = new TextField(10);
	Button btnAccess = new Button("Acceder");

	// --- Error/Notice: Dialog ---
	Dialog dlgWindow = new Dialog(windowLogin, "Error", true);
	Label lblMessage = new Label ("Credenciales incorrectas.");

	// --- Instantiate object conexion ---
	Conexion conexion = new Conexion ();
	int tipoUsuario;

	Login() {

		// --- Configuración del panel principal ---
		windowLogin.setLayout(null);

		// --- Main panel configuration ---
		windowLogin.addWindowListener(this);
		windowLogin.setResizable(false);
		windowLogin.setSize(300, 180); 
		windowLogin.setLocationRelativeTo(null);
		windowLogin.setBackground(Color.cyan);

		// --- Component configuration ---
		//txtUser.setText("admin");
		//txtPassword.setText("admin");

		// --- User ---
		lblUser.setBounds(30, 60, 80, 25);
		windowLogin.add(lblUser);
		txtUser.setBounds(110, 60, 160, 25);
		txtUser.addActionListener(this);
		windowLogin.add(txtUser);

		// --- Password ---
		lblPassword.setBounds(30, 90, 80, 25);
		windowLogin.add(lblPassword);
		txtPassword.setBounds(110, 90, 160, 25);
		txtPassword.setEchoChar('•'); // ASCII 7
		txtPassword.addActionListener(this);
		windowLogin.add(txtPassword);


		txtUser.setText("admin");
		txtPassword.setText("admin");

		// --- Access Button ---
		btnAccess.setBounds(135, 130, 100, 25);
		btnAccess.addActionListener(this);
		windowLogin.add(btnAccess);

		// --- Key Listener ---
		txtUser.addKeyListener(this);
		txtPassword.addKeyListener(this);

		// --- Set window visible --- 
		windowLogin.setVisible(true);


	}

	public static void main(String[] args) 
	{
		new Login();
	}

	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) 
	{
		if (dlgWindow.isActive()) 
		{
			dlgWindow.setVisible(false);
		}else {
			System.exit(0);
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {	}
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

		// --- Check if the access button was clicked ---
		if (e.getSource().equals(btnAccess)) {
			checkCredentials();
		}
	}

	// --- Method to check if the credentials are correct ---
	public void checkCredentials()
	{
		String user = txtUser.getText();
		String password = txtPassword.getText();

		// --- Check the credentials using the object: Connection ---
		// --- Also the type of user (0 or 1 - user or admin)
		tipoUsuario = conexion.checkingCredentials(user, password);
		// --- If user is one of those, log in ---
		if (tipoUsuario !=-1) 
		{
			// --- Open the main menu and close the login window ---
			new MenuPrincipal(tipoUsuario, user);
			windowLogin.setVisible(false);

			File sf = new File("login.wav");
			AudioFileFormat aff;
			AudioInputStream ais;

			try {

				aff = AudioSystem.getAudioFileFormat(sf);
				ais = AudioSystem.getAudioInputStream(sf);

				AudioFormat af = aff.getFormat();
				DataLine.Info info = new DataLine.Info(Clip.class,
						ais.getFormat(), ((int) ais.getFrameLength() * af.getFrameSize()));
				Clip ol = (Clip) AudioSystem.getLine(info);
				ol.open(ais);
				ol.loop(1);
				// Damos tiempo para que el sonido sea escuchado
				Thread.sleep(100);
				ol.close();
			}

			catch(UnsupportedAudioFileException ee)
			{
				System.out.println(ee.getMessage());
			}
			catch(IOException ea)
			{
				System.out.println(ea.getMessage());
			}
			catch(LineUnavailableException LUE)
			{
				System.out.println(LUE.getMessage());
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		// --- If the credentials are wrong: Show error dialog ---
		else {

			dlgWindow.setLayout(new FlowLayout());
			dlgWindow.addWindowListener(this);
			dlgWindow.setSize(300, 75); 
			dlgWindow.setResizable(false);
			dlgWindow.setLocationRelativeTo(null);

			dlgWindow.setBackground(Color.BLACK);
			lblMessage.setForeground(Color.red);
			dlgWindow.add(lblMessage);
			txtUser.setText("");
			txtPassword.setText("");

			dlgWindow.setVisible(true);
		}
	}

	@Override
	public void keyTyped(KeyEvent e){}
	@Override
	public void keyReleased(KeyEvent e){}
	@Override
	public void keyPressed(KeyEvent e)
	{
		// --- Run the method if the 'Enter' key was pressed ---
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			checkCredentials();
		}
	}

}



