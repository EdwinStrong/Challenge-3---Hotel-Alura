package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

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
			int idReservaRegistrado = 0;
			
			try(resulSet){
				while(resulSet.next()) {
					idReservaRegistrado = resulSet.getInt(1);
				}
				return idReservaRegistrado;
			}
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
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
	
	public Reserva buscarPorId(Integer id) {
		try(con){
			PreparedStatement statement = con.prepareStatement("SELECT * FROM RESERVAS WHERE id = ?;");
			
			statement.setInt(1, id);
			
			statement.execute();
			
			final ResultSet resultSet = statement.getResultSet();
			
			try(resultSet){
				if(resultSet.next()) {
					System.out.println(new Reserva(resultSet.getInt(1), resultSet.getTimestamp(2), resultSet.getTimestamp(3),
							resultSet.getDouble(4), resultSet.getString(5)));
					return new Reserva(resultSet.getInt(1), resultSet.getTimestamp(2), resultSet.getTimestamp(3),
							resultSet.getDouble(4), resultSet.getString(5));
				}
				return null;
			}
					
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public Integer modificarReserva(Integer id, Reserva reserva) {
		try(con){
			PreparedStatement statement = con.prepareStatement("UPDATE RESERVAS SET fecha_entrada = ?, fecha_salida = ?, "
					+ "valor = ?, forma_pago = ? WHERE ID = ?");
			
			statement.setTimestamp(1, reserva.getFechaEntrada());
			statement.setTimestamp(2, reserva.getFechaSalida());
			statement.setDouble(3, reserva.getValor());
			statement.setString(4, reserva.getFormaPago());
			statement.setInt(5, id);
			
			statement.execute();
			
			//Las filas modificadas.
			return statement.getUpdateCount();
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public Integer eliminarReserva(Integer id) {
		try(con){
			
			PreparedStatement statement = con.prepareStatement("DELETE FROM RESERVAS WHERE ID = ?");
			
			statement.setInt(1, id);
			
			statement.execute();
			
			return statement.getUpdateCount();
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
