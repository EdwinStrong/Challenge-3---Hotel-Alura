package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import Model.Reserva;

public class ReservaDao {
	
	final private Connection con;
	
	public ReservaDao(Connection con) {
		this.con = con;
	}
	
	public Integer registrarReserva(Reserva reserva) {
		try(con){
			PreparedStatement statement = con.prepareStatement("INSERT INTO Reservas (fecha_entrada, fecha_salida, valor, forma_pago) "
					+ "VALUES(?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
			statement.setTimestamp(1, (Timestamp) reserva.getFechaEntrada());
			statement.setTimestamp(2,(Timestamp) reserva.getFechaSalida());
			statement.setDouble(3, reserva.getValor());
			statement.setString(4, reserva.getFormaPago());
			
			statement.execute();
			
			final ResultSet resulSet = statement.getGeneratedKeys();
			
			try(resulSet){
				while(resulSet.next()) {
					System.out.println("Registrado huesped con id: "+resulSet.getInt(1));
				}
			}
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return 1;
	}
}
