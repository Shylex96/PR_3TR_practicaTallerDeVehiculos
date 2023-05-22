package es.studium.tallerVehiculos;

import java.awt.Button;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

public class ListadoRealizan implements ActionListener, WindowListener {

	// Destination File
	public static final String DEST = "Listado_Realizan.pdf";

	Frame windowListadoClientes = new Frame("Listado de Realización");
	Label lblIdCliente = new Label ("Cliente:");
	Label lblIdServicio = new Label ("Servicio:");
	Label lblTiempoEmpleado = new Label ("Tiempo:");
	Label lblFechaRealizacion = new Label ("Fecha:");
	TextArea areaDatosRealizan = new TextArea (10, 35);
	Button btnPDF = new Button ("PDF");

	Conexion conexion = new Conexion();
	String user;

	ListadoRealizan(String user) {

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

		// --- Client ---
		lblIdCliente.setBounds(20, 40, 50, 20);
		windowListadoClientes.add(lblIdCliente);

		// --- Service ---
		lblIdServicio.setBounds(120, 40, 50, 20);
		windowListadoClientes.add(lblIdServicio);

		// --- Time ---
		lblTiempoEmpleado.setBounds(215, 40, 55, 20);
		windowListadoClientes.add(lblTiempoEmpleado);

		// --- Date ---
		lblFechaRealizacion.setBounds(315, 40, 50, 20);
		windowListadoClientes.add(lblFechaRealizacion);

		// --- TextArea ---
		// --- Fill TextArea ---
		conexion.fillListadoRealizan(areaDatosRealizan, user);
		areaDatosRealizan.setEditable(false);
		areaDatosRealizan.setBounds(20, 70, 460, 250);
		windowListadoClientes.add(areaDatosRealizan);

		// --- PDF Button ---
		btnPDF.setEnabled(true);
		btnPDF.setBounds(225, 330, 50, 50);
		btnPDF.addActionListener(this);
		windowListadoClientes.add(btnPDF);

		windowListadoClientes.setVisible(true);
	}
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e)
	{
		windowListadoClientes.setVisible(false);
		conexion.logs("[+] " +user, " has closed 'Listado Realizan' window.");
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (btnPDF.equals(e.getSource())) {
			conexion.logs("[+] " +user, " is trying to generate a PDF of 'Listado Realizan'.");
			try {
				// Initialize PDF writer
				PdfWriter writer = new PdfWriter(DEST);
				// Initialize PDF document
				PdfDocument pdf = new PdfDocument(writer);
				// Initialize document with A4 format
				Document document = new Document(pdf, PageSize.A3.rotate());

				// Create table with 4 responsive columns 
				Table table = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
				table.setHorizontalAlignment(HorizontalAlignment.CENTER);

				// Create table headers
				Cell headerCell1 = createCell("Cliente:");
				Cell headerCell2 = createCell("Tipo de Servicio:");
				Cell headerCell3 = createCell("Tiempo Empleado:");
				Cell headerCell4 = createCell("Fecha de Realización:");

				// Add header cells to the table
				table.addHeaderCell(headerCell1);
				table.addHeaderCell(headerCell2);
				table.addHeaderCell(headerCell3);
				table.addHeaderCell(headerCell4);

				// Apply bold font to header cells
				headerCell1.setBold();
				headerCell2.setBold();
				headerCell3.setBold();
				headerCell4.setBold();

				// Add data rows from the TextArea For Each

				// Get all the information of the rows of the 
				// textarea and separate it with split by "\\n"
				// Store them in "lines" array.
				String[] lines = areaDatosRealizan.getText().split("\\n");

				for (String line : lines) {
					// Split line into row data using tabs as separators "\\t"
					String[] rowData = line.split("\\t");

					// The data is added as a cell to the table using the method "addCell(createCell(data))"
					for (String data : rowData) {
						// The "createCell(data)" method creates a cell with the provided text.
						table.addCell(createCell(data));
					}

					// After the row data is processed, the length of rowData is checked.
					// If it is less than 4, it means that there is insufficient data to 
					// fill all the columns of the table and in that case, a cell with 
					// empty content is added using "addCell(createCell(""))" until all 
					// 4 columns are filled. Remaining cells are filled with empty content.

					for (int i = rowData.length; i < 4; i++) {
						table.addCell(createCell(""));
					}
				}

				// Add the table to the document
				document.add(table);

				// Close the document
				document.close();

				// Open the new PDF document just created
				Desktop.getDesktop().open(new File(DEST));
				conexion.logs("[+] " +user, " has generated a PDF of 'Listado Realizan'.");

			} catch (IOException ioe) {
				conexion.logs("[+] " +user, " has failed to generate a PDF of 'Listado Realizan'.");
				ioe.printStackTrace();
			}
		}
	}

	// Method to create a Cell with centered Paragraph
	private Cell createCell(String text) {
		Cell cell = new Cell();
		cell.add(new Paragraph(text).setTextAlignment(TextAlignment.CENTER));
		return cell;

	}

}

