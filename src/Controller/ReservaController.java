package Controller;

import java.util.List;

import Dao.ReservaDao;
import Factory.ConnectionFactory;
import Model.Reserva;

public class ReservaController {
	
	ConnectionFactory connectionFactory;
	
	public ReservaController() {
		this.connectionFactory = new ConnectionFactory();
	}
	
	ReservaDao reservaDao;

	public Integer registrarReserva(Reserva reserva) {
		reservaDao = new ReservaDao(connectionFactory.recuperaConexion());
		return reservaDao.registrarReserva(reserva);
	}
	
	public List<Reserva> obtenerReservas(){
		reservaDao = new ReservaDao(connectionFactory.recuperaConexion());
		return reservaDao.obtenerReservas();
	}
	
	public boolean buscarPorId(Integer id) {
		reservaDao = new ReservaDao(connectionFactory.recuperaConexion());
		return reservaDao.buscarPorId(id);
	}
}
