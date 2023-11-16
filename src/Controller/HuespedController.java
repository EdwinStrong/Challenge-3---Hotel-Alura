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
		
		if(reservaConexion.buscarPorId(huesped.getIdReserva())) {
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
	
}
