package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;import org.hibernate.internal.ExceptionConverterImpl;

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
					System.out.println("Registrado huesped con id: " + resulSet.getInt(1));
				}
			}
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return 1;
	}
	
	public List<Reserva> obtenerReservas(){
		Reserva reserva;
		try(con){
			List<Reserva> reservasList = new ArrayList<>();
			PreparedStatement statement = con.prepareStatement("SELECT * FROM RESERVAS;");
			
			statement.execute();
			
			final ResultSet resultSet = statement.getResultSet();
			
			try(resultSet){
				while(resultSet.next()) {
					reserva = new Reserva(resultSet.getInt(1), resultSet.getTimestamp(2), resultSet.getTimestamp(3),
							resultSet.getDouble(4), resultSet.getString(5));
					reservasList.add(reserva);
				}
				return reservasList;
			}
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public Boolean buscarPorId(Integer id) {
		try(con){
			PreparedStatement statement = con.prepareStatement("SELECT * FROM RESERVAS WHERE id = ?;");
			statement.setInt(1, id);
			
			statement.execute();
			
			final ResultSet resultSet = statement.getResultSet();
			Boolean seEncuentra = false;
			
			try(resultSet){
				while(resultSet.next()) {
					//Si fue afectada una o más filas, se encuentra.
					seEncuentra = true;;
				}
				return seEncuentra;
			}
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
