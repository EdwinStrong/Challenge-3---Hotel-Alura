package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Model.Huesped;

public class HuespedDao {
	
	final private Connection con;
	
	public HuespedDao(Connection con) {
		this.con = con;
	}
	
	public Integer registrarHuesped(Huesped huesped) {
		try(con){
			PreparedStatement statement = con.prepareStatement("INSERT INTO Huespedes (nombre, apellido, nacimiento, nacionalidad, telefono, id_reserva) "
					+ "VALUES (?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, huesped.getNombre());
			statement.setString(2, huesped.getApellido());
			statement.setTimestamp(3, huesped.getNacimiento());
			statement.setString(4, huesped.getNacionalidad());
			statement.setString(5, huesped.getTelefono());
			statement.setInt(6, huesped.getIdReserva());
			
			statement.execute();
			
			final ResultSet resulSet = statement.getGeneratedKeys();
			int indiceFinal = 0;
			
			try(resulSet){
				while(resulSet.next()) {
					indiceFinal = resulSet.getInt(1);
					System.out.println("Registrado huesped con id: "+resulSet.getInt(1));
				}
				return indiceFinal;
			}
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public List<Huesped> obtenerHuespedes(){
		Huesped huesped;
		try(con){
			List<Huesped> huespedes = new ArrayList<>();
			PreparedStatement statement = con.prepareStatement("SELECT * FROM HUESPEDES;");
			
			statement.execute();
			
			final ResultSet resultSet = statement.getResultSet();
			try(resultSet){
				while(resultSet.next()) {
					huesped = new Huesped(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getTimestamp(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7));
					huespedes.add(huesped);
				}
				return huespedes;
			}
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public Huesped buscarPorId(Integer id) {
		
		try(con){
			PreparedStatement statement = con.prepareStatement("SELECT * FROM HUESPEDES WHERE ID = ?;");
			
			statement.setInt(1, id);
			
			statement.execute();
			
			final ResultSet resultSet = statement.getResultSet();
			
			try(resultSet){
				if(resultSet.next()) {
					return new Huesped(resultSet.getInt(1),
							resultSet.getString(2),
							resultSet.getString(3),
							resultSet.getTimestamp(4),
							resultSet.getString(5),
							resultSet.getString(6),
							resultSet.getInt(7)
							);
				}
				//Si no halla al huesped, retorna null
				return null;
			}
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	
	public Integer modificarHuesped(Integer id, Huesped nuevoHuesped) {
		try(con){
			PreparedStatement statement = con.prepareStatement("UPDATE HUESPEDES SET NOMBRE = ?, APELLIDO = ?, NACIMIENTO = ?, "
					+ "NACIONALIDAD = ?, TELEFONO = ?, ID_RESERVA = ? WHERE ID = ?;");
			
			statement.setString(1, nuevoHuesped.getNombre());
			statement.setString(2, nuevoHuesped.getApellido());
			statement.setTimestamp(3, nuevoHuesped.getNacimiento());
			statement.setString(4, nuevoHuesped.getNacionalidad());
			statement.setString(5, nuevoHuesped.getTelefono());
			statement.setInt(6, nuevoHuesped.getIdReserva());
			statement.setInt(7, id);

			statement.execute();
			
			return statement.getUpdateCount();	
			
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public Integer eliminarHuesped(Integer id) {
		try(con){
			PreparedStatement statement = con.prepareStatement("DELETE FROM HUESPEDES WHERE ID = ?;");
			
			statement.setInt(1, id);
			
			statement.execute();
			
			return statement.getUpdateCount();
			
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
