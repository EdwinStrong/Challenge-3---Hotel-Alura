package Controller;

import java.util.List;

import Dao.HuespedDao;
import Factory.ConnectionFactory;
import Model.Huesped;

public class HuespedController {
	ConnectionFactory connectionFactory;
	ReservaController reservaController;
	
	public HuespedController(){
		this.connectionFactory = new ConnectionFactory();
	}
	
	HuespedDao huespedDao;
	
	/**
	 * Registrar un huesped
	 * @param huesped
	 * @return La cantidad de huespedes registrados.
	 */
	public Integer registrarHuesped(Huesped huesped) {
		reservaController = new ReservaController();
		
		if(reservaController.existeId(huesped.getIdReserva())) {
			//System.out.println("La reservacion existe.");
			huespedDao = new HuespedDao(connectionFactory.recuperaConexion());
			return huespedDao.registrarHuesped(huesped);
		}

		return 0;
	}
	
	/**
	 * Obtener los huespedes
	 * @return todos los huespedes registrados en la base.
	 */
	public List<Huesped> obtenerHuespedes(){
		huespedDao = new HuespedDao(connectionFactory.recuperaConexion());
		
		return huespedDao.obtenerHuespedes();
	}
	
	/**
	 * Busca un huesped por su llave primaria
	 * @param id es la llave primaria, o código del huesped
	 * @return el huesped buscado o null si no se encuentra.
	 */
	public Huesped buscarHuespedPorId(Integer id) {
		huespedDao = new HuespedDao(connectionFactory.recuperaConexion());
		
		return huespedDao.buscarPorId(id);
	}
	
	/**
	 * Se encarga de modificar a un huesped
	 * @param id es el código del huesped a modificar
	 * @param nuevoHuesped es la nueva información del huesped
	 * @return la cantidad de celdas afectadas, o null si no existe la nueva reserva modificada.
	 */
	public Integer modificarHuesped(Integer id, Huesped nuevoHuesped) {
		
		reservaController = new ReservaController();
		
		if(reservaController.existeId(nuevoHuesped.getIdReserva())) {
			
			huespedDao = new HuespedDao(connectionFactory.recuperaConexion());
			
			return huespedDao.modificarHuesped(id, nuevoHuesped);
		}
		
		return null;
	}
	
	/**
	 * Se encarga de eliminar a un huesped de la base
	 * @param id es el código del huesped a eliminar
	 * @return la cantidad de registros que fueron afectados.
	 */
	public Integer eliminarHuesped(Integer id) {
		huespedDao = new HuespedDao(connectionFactory.recuperaConexion());
		
		return huespedDao.eliminarHuesped(id);
	}
}
