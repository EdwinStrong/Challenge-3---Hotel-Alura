package Controller;

import java.util.List;

import Dao.HuespedDao;
import Factory.ConnectionFactory;
import Model.Huesped;

public class HuespedController {
	ConnectionFactory connectionFactory;
	ReservaController reservaConexion;
	
	public HuespedController(){
		this.connectionFactory = new ConnectionFactory();
	}
	
	HuespedDao huespedDao;
	
	public Integer registrarHuesped(Huesped huesped) {
		reservaConexion = new ReservaController();
		
		if(reservaConexion.existeId(huesped.getIdReserva())) {
			//System.out.println("La reservacion existe.");
			huespedDao = new HuespedDao(connectionFactory.recuperaConexion());
			return huespedDao.registrarHuesped(huesped);
		}

		return 0;
	}
	
	public List<Huesped> obtenerHuepedes(){
		huespedDao = new HuespedDao(connectionFactory.recuperaConexion());
		
		return huespedDao.obtenerHuespedes();
	}
	
	public Huesped buscarHuespedPorId(Integer id) {
		huespedDao = new HuespedDao(connectionFactory.recuperaConexion());
		
		return huespedDao.buscarPorId(id);
	}
	
	public Integer modificarHuesped(Integer id, Huesped nuevoHuesped) {
		huespedDao = new HuespedDao(connectionFactory.recuperaConexion());
		
		return huespedDao.modificarHuesped(id, nuevoHuesped);
	}
	
	public Integer eliminarHuesped(Integer id) {
		huespedDao = new HuespedDao(connectionFactory.recuperaConexion());
		
		return huespedDao.eliminarHuesped(id);
	}
}
