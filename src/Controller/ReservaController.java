package Controller;

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
}
