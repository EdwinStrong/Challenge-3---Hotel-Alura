package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Controller.HuespedController;
import Controller.ReservaController;
import Model.Huesped;
import Model.Reserva;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Timestamp;

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
	//Fila seleccionada
	Integer seleccionadaFila = null;
	//Identificar selección en el menu
	String tablaActiva = "";

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

		/**
		 * ----------------------------------------IDENTIFICAR LA TABLA SELECCIONADA------------------------------------
		 */

		panel.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();

				int index = sourceTabbedPane.getSelectedIndex();

				tablaActiva = sourceTabbedPane.getTitleAt(index);

				System.out.println("Cambiado a: " + tablaActiva);
			}
		});

		/*
		 * ---------------TABLA DE RESERVA---------------------
		 * */


		//Definir que la primer columna (id) no es editable
		tbReservas = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0;
			} 
		};
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

		/**
		 * 
		 */
		tbReservas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if(tablaReservaSeleccionada()) {
					//Cuando cambia de columna
					System.out.println("Tabla Reservas cambio seleccion");
					//Indice de la fila seleccionada
					seleccionadaFila = tbReservas.getSelectedRow();

					System.out.println("Fila: " + seleccionadaFila);

					if(seleccionadaFila > 0) {
						//El Id de esa columna
						idTablaReserva = (Integer) tbReservas.getValueAt(seleccionadaFila, 0);//Seleccionar siempre la primer columna

						System.out.println("El id es: " + idTablaReserva);//Id usado para liminar
					}

				}

			}
		});


		/*
		 * -------------------TABLA DE HUESPEDES-------------------------
		 */

		//Definir que la primer columna no es editable
		tbHuespedes = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0;
			} 
		};
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
		mostrarHuespedes(modeloHuesped);

		//Para editar
		tbHuespedes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if(tablaHuespedSeleccionada()) {
					//Cuando cambia de columna
					System.out.println("Tabla Huespedes cambio seleccion");
					//Indice de la fila seleccionada
					seleccionadaFila = tbHuespedes.getSelectedRow();

					System.out.println("Fila: " + seleccionadaFila);

					if(seleccionadaFila > 0) {
						//El Id de esa columna
						idTablaHuesped = (Integer) tbHuespedes.getValueAt(seleccionadaFila, 0);//Seleccionar siempre la primer columna

						System.out.println("El id es: "+idTablaHuesped);//Id usado para modificar o eliminar}
					}

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

					Integer idBuscado = Integer.parseInt(txtBuscar.getText());

					if (tablaActiva.equals("Reservas")) {
						limpiarTabla(modelo);

						if(!buscarReservaPorId(modelo, idBuscado)){
							JOptionPane.showMessageDialog(null, "id de reserva no encontrado. ", "NOT FOUND", JOptionPane.INFORMATION_MESSAGE);
							mostrarReservas(modelo);
						}
					}

					else{

						limpiarTabla(modeloHuesped);

						if(!buscarHuespedPorId(modeloHuesped, idBuscado)){
							JOptionPane.showMessageDialog(null, "id de huesped no encontrado. ", "NOT FOUND", JOptionPane.INFORMATION_MESSAGE);
							mostrarHuespedes(modeloHuesped);
						}
					}

				}catch(Exception e1) {

					System.out.println("ERROR EN BUSCAR"+e1.getMessage());

					mostrarReservas(modelo);
					mostrarHuespedes(modeloHuesped);
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

				if(tablaHuespedSeleccionada()) {//Tabla huesped

					HuespedController huespedController = new HuespedController();

					//idTablaHuesped = (Integer) tbHuespedes.getValueAt(seleccionadaFila, 0);//Seleccionar siempre la primer columna
					Huesped nuevoHuesped = new Huesped(tbHuespedes.getValueAt(seleccionadaFila, 1).toString(), 
							tbHuespedes.getValueAt(seleccionadaFila, 2).toString(), 
							Timestamp.valueOf(tbHuespedes.getValueAt(seleccionadaFila, 3).toString()), 
							tbHuespedes.getValueAt(seleccionadaFila, 4).toString(), 
							tbHuespedes.getValueAt(seleccionadaFila, 5).toString(), 
							Integer.valueOf(tbHuespedes.getValueAt(seleccionadaFila, 6).toString())
							);
					System.out.println(nuevoHuesped);

					Integer celdasAfectadas = huespedController.modificarHuesped(idTablaHuesped, nuevoHuesped);
					
					if(celdasAfectadas != null) {
						JOptionPane.showMessageDialog(null, "Se modificó el huesped con el indice: " + idTablaHuesped, 1 + " celda afectada.",
								JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "El nuevo id " + nuevoHuesped.getIdReserva() + " de la reserva del huesped, no existe.","Reserva no existe",
								JOptionPane.ERROR_MESSAGE);
					}
				

					mostrarHuespedes(modeloHuesped);
				}
				else if(tablaReservaSeleccionada()) {
					System.out.println("El indice a modificar de reserva es: " + idTablaReserva);

					ReservaController reservaController = new ReservaController();

					System.out.println("La fila es: " + seleccionadaFila);

					Reserva nuevaReserva = new Reserva(Timestamp.valueOf(tbReservas.getValueAt(seleccionadaFila, 1).toString()),
							Timestamp.valueOf(tbReservas.getValueAt(seleccionadaFila, 2).toString()),
							Double.valueOf(tbReservas.getValueAt(seleccionadaFila, 3).toString()),
							tbReservas.getValueAt(seleccionadaFila, 4).toString()
							);

					System.out.println("La reserva es: " + nuevaReserva);

					JOptionPane.showMessageDialog(null, "Se modificó la reserva con el indice: " + idTablaReserva, reservaController.modificarReserva(idTablaReserva, nuevaReserva) + " celda afectada.",
							JOptionPane.INFORMATION_MESSAGE);

					mostrarReservas(modelo);
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

		/*---------------------------------------------------------BOTON DE ELIMINAR-------------------------------------------*/
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tablaHuespedSeleccionada()) {//Tabla huesped

					HuespedController huespedController = new HuespedController();

					//idTablaHuesped = (Integer) tbHuespedes.getValueAt(seleccionadaFila, 0);//Seleccionar siempre la primer columna

					JOptionPane.showMessageDialog(null, "Se Eliminó el huesped con el indice: " + idTablaHuesped, huespedController.eliminarHuesped(idTablaHuesped) + " celda afectada.",
							JOptionPane.INFORMATION_MESSAGE);

					mostrarHuespedes(modeloHuesped);
				}
				else if(tablaReservaSeleccionada()) {
					System.out.println("El indice a modificar de reserva es: " + idTablaReserva);

					ReservaController reservaController = new ReservaController();

					//idTablaHuesped = (Integer) tbHuespedes.getValueAt(seleccionadaFila, 0);//Seleccionar siempre la primer columna

					JOptionPane.showMessageDialog(null, "Se Eliminó la reserva con el indice: " + idTablaReserva, reservaController.eliminarReserva(idTablaReserva) + " celda afectada.",
							JOptionPane.INFORMATION_MESSAGE);

					mostrarReservas(modelo);
				}
				else {
					System.out.println("Ninguna tabla seleccionada.");
				}
			}
		});
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
	public Boolean tablaHuespedSeleccionada() {
		return tablaActiva.equals("Huéspedes");
	}

	/**
	 * Este método indica que la tabla de reservas ha sido seleccionada.
	 */
	public Boolean tablaReservaSeleccionada() {
		return tablaActiva.equals("Reservas");
	}

	/**
	 * Se encarga de imprimir todas las reservas buscadas en la tabla
	 * @param modelReserva es la tabla donde se imprimirán todas las reservas.
	 */
	public void mostrarReservas(DefaultTableModel modelReserva) {

		limpiarTabla(modelReserva);

		//Conexion a bd
		ReservaController reservaController = new ReservaController();

		//Traer la lista de todas las reservas registradas
		List<Reserva> reservaList = new ArrayList<Reserva>(reservaController.obtenerReservas());

		reservaList.forEach(reserva -> {
			imprimirReserva(reserva, modelReserva);
		});
	}

	/**
	 * Se encarga de imprimir a todos los huespedes buscados en la tabla.
	 * @param modelHuesped es la tabla donde se imprimirán todos los huéspedes.
	 */
	public void mostrarHuespedes(DefaultTableModel modelHuesped) {

		limpiarTabla(modelHuesped);

		//Conexion a bd
		HuespedController reservaController = new HuespedController();

		//Traer la lista de todas las reservas registradas
		List<Huesped> reservaList = new ArrayList<Huesped>(reservaController.obtenerHuespedes());

		reservaList.forEach(huesped -> {
			imprimirHuesped(huesped, modelHuesped);
		});
	}

	/**
	 * Este método se encarga de imprimir únicamente a la reserva buscada, si lo encuentra.
	 * @param modelReserva es la tabla de las reservas.
	 * @param id es el codigo de la reserva a buscar
	 * @return true si se imprimió la reserva, false si no se encontró a la reserva.
	 */
	public Boolean buscarReservaPorId(DefaultTableModel modelReserva, Integer id) {
		//Conexion a bd
		ReservaController reservaController = new ReservaController();
		Reserva reserva = reservaController.buscarPorId(id);

		if(reserva != null) {
			imprimirReserva(reserva, modelReserva);
			return true;
		}
		return false;
	}

	/**
	 * Este método se encarga de imprimir únicamente el huesped buscado, si lo encuentra.
	 * @param modelHuesped es la tabla de los Huespedes
	 * @param id es el codigo del huesped a buscar
	 * @return true si se imprimió el huesped, false si no se encontró al huesped.
	 */
	public Boolean buscarHuespedPorId(DefaultTableModel modelHuesped, Integer id) {
		//Conexion a bd
		HuespedController huespedController = new HuespedController();

		Huesped huesped = huespedController.buscarHuespedPorId(id);

		if(huesped != null) {//Si se encontro al huesped, retorna true
			imprimirHuesped(huesped, modelHuesped);
			return true;
		}
		return false;
	}

	public void limpiarTabla(DefaultTableModel modeloTabla) {
		modeloTabla.setRowCount(0);
	}

	/**
	 * Se encarga de imprimir las reservas en las celdas de la tabla
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
	 * Se encarga de imprimir los huespedes en las celdas de la tabla
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
