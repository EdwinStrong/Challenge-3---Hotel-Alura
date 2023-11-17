package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Controller.HuespedController;
import Controller.ReservaController;
import Model.Huesped;
import Model.Reserva;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Event;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;

	//Variables de apoyo para el id a modificar y eliminar
	Integer idTablaHuesped = null;
	Integer idTablaReserva = null;
	//Permite saber de cuál tabla se quiere modificar o elminar
	Boolean seleccionadaTablaHuesped = false;
	Boolean seleccionadaTablaReserva = false;
	//Fila seleccionada
	Integer seleccionadaFila = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);


		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		
		/*
		 * ---------------TABLA DE RESERVA---------------------
		 * */
		
		
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);

		//Agregar datos
		mostrarReservas(modelo);
		
		//
		tbReservas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if(seleccionadaTablaReserva) {
					tablaReservaSeleccionada();
					
					//Cuando cambia de columna
					System.out.println("Tabla Huespedes cambio seleccion");
					//Indice de la fila seleccionada
					Integer seleccionadaFila = tbReservas.getSelectedRow();

					System.out.println("Fila: " + seleccionadaFila);
					//El Id de esa columna
					idTablaReserva = (Integer) tbReservas.getValueAt(seleccionadaFila, 0);//Seleccionar siempre la primer columna
					
					System.out.println("El id es: " + idTablaReserva);//Id usado para liminar
				}
			}
		});

		
		/*
		 * -------------------TABLA DE HUESPEDES-------------------------
		 */
		
		
		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);

		//Llenar la tabla de huespedes con los registros de la BD
		mostrarHuesedes(modeloHuesped);
		
		//Para editar
		tbHuespedes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if(!seleccionadaTablaHuesped) {
					tablaHuespedSeleccionada();
					
					//Cuando cambia de columna
					System.out.println("Tabla Huespedes cambio seleccion");
					//Indice de la fila seleccionada
					seleccionadaFila = tbHuespedes.getSelectedRow();

					System.out.println("Fila: " + seleccionadaFila);
					//El Id de esa columna
					idTablaHuesped = (Integer) tbHuespedes.getValueAt(seleccionadaFila, 0);//Seleccionar siempre la primer columna
					
					System.out.println("El id es: "+idTablaHuesped);//Id usado para modificar o eliminar
				}
				
			}
		});
		
		/*
		 * ---------------------------------------------------------------------------
		 */
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);

		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);

			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				btnexit.setBackground(Color.white);
				labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);

		JPanel btnbuscar = new JPanel();
		
		/**
		 * ------------------------------ BOTON DE BUSCAR -----------------------------------------
		 */
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					limpiarTabla(modelo);

					Integer idBuscado = Integer.parseInt(txtBuscar.getText());

					if(!buscarPorId(modelo, idBuscado)){
						JOptionPane.showMessageDialog(null, "id no encontrado. ", "NOT FOUND", JOptionPane.INFORMATION_MESSAGE);
						mostrarReservas(modelo);
					}

				}catch(Exception e1) {
					System.out.println(e1.getStackTrace());

					mostrarReservas(modelo);
				}
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		JLabel lblEditar = new JLabel("EDITAR");
		
		/**
		 * --------------------------------------------------- BOTON DE MODIFICAR SEGUN LA SELECCION ---------------------------------------------------
		 */
		lblEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(seleccionadaTablaHuesped) {//Tabla huesped
					
					HuespedController huespedController = new HuespedController();
					
					System.out.println("El indice a modificar de huesped es: " + idTablaHuesped);
					
					//idTablaHuesped = (Integer) tbHuespedes.getValueAt(seleccionadaFila, 0);//Seleccionar siempre la primer columna
					Huesped nuevoHuesped = new Huesped(tbHuespedes.getValueAt(seleccionadaFila, 1).toString(), 
							tbHuespedes.getValueAt(seleccionadaFila, 2).toString(), 
							Timestamp.valueOf(tbHuespedes.getValueAt(seleccionadaFila, 3).toString()), 
							tbHuespedes.getValueAt(seleccionadaFila, 4).toString(), 
							tbHuespedes.getValueAt(seleccionadaFila, 5).toString(), 
							Integer.valueOf(tbHuespedes.getValueAt(seleccionadaFila, 6).toString())
							);
					System.out.println(nuevoHuesped);
					
					JOptionPane.showMessageDialog(null, "Se modificó el huesped.", huespedController.modificarHuesped(idTablaHuesped, nuevoHuesped) + " celda afectada.",
							JOptionPane.INFORMATION_MESSAGE);
					
					mostrarHuesedes(modeloHuesped);
				}
				else if(seleccionadaTablaReserva) {
					System.out.println("El indice a modificar de reserva es: " + idTablaReserva);
				}
				else {
					System.out.println("Ninguna tabla seleccionada.");
				}
			}
		});
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);

		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}

	/***
	 * Este método indica que la tabla de huespedes ha sido seleccionada.
	 */
	public void tablaHuespedSeleccionada() {
		seleccionadaTablaHuesped = true;
		seleccionadaTablaReserva = false;
	}
	
	/**
	 * Este método indica que la tabla de reservas ha sido seleccionada.
	 */
	public void tablaReservaSeleccionada() {
		seleccionadaTablaHuesped = false;
		seleccionadaTablaReserva = true;
	}
	
	public void mostrarReservas(DefaultTableModel modelReserva) {

		//Conexion a bd
		ReservaController reservaController = new ReservaController();

		//Traer la lista de todas las reservas registradas
		List<Reserva> reservaList = new ArrayList<Reserva>(reservaController.obtenerReservas());

		reservaList.forEach(reserva -> {
			imprimirReserva(reserva, modelReserva);
		});
	}
	
	public void mostrarHuesedes(DefaultTableModel modelHuesped) {

		limpiarTabla(modelHuesped);
		
		seleccionadaTablaHuesped = false;
		seleccionadaTablaReserva = false;
		//Conexion a bd
		HuespedController reservaController = new HuespedController();

		//Traer la lista de todas las reservas registradas
		List<Huesped> reservaList = new ArrayList<Huesped>(reservaController.obtenerHuepedes());

		reservaList.forEach(huesped -> {
			imprimirHuesped(huesped, modelHuesped);
		});
	}


	public Boolean buscarPorId(DefaultTableModel modelReserva, Integer id) {
		//Conexion a bd
		ReservaController reservaController = new ReservaController();
		Reserva reserva = reservaController.buscarPorId(id);

		if(reserva != null) {
			imprimirReserva(reserva, modelReserva);
			return true;
		}
		return false;
	}

	public void limpiarTabla(DefaultTableModel modeloTabla) {
		modeloTabla.setRowCount(0);
	}

	/**
	 * Se encarga de imprimir las reservas en la tabla
	 * @param reserva son los datos de la reserva a imprimir
	 * @param modelReserva es la tabla a imprimir.
	 */
	public static void imprimirReserva(Reserva reserva, DefaultTableModel modelReserva) {
		Vector<Object> row = new Vector<Object>();
		row.add(reserva.getId());
		row.add(reserva.getFechaEntrada());
		row.add(reserva.getFechaSalida());
		row.add(reserva.getValor());
		row.add(reserva.getFormaPago());
		modelReserva.addRow(row);
	}
	
	/**
	 * Se encarga de imprimir los huespedes en la tabla
	 * @param huesped son los datos del huped a imprimir
	 * @param modelReserva es la tabla a imprimir.
	 */
	public static void imprimirHuesped(Huesped huesped, DefaultTableModel modelReserva) {
		Vector<Object> row = new Vector<Object>();
		row.add(huesped.getId());
		row.add(huesped.getNombre());
		row.add(huesped.getApellido());
		row.add(huesped.getNacimiento());
		row.add(huesped.getNacionalidad());
		row.add(huesped.getTelefono());
		row.add(huesped.getIdReserva());

		modelReserva.addRow(row);
	}
	
	//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}
}
