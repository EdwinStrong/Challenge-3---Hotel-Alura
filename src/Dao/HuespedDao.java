package Dao;

import java.awt.Taskbar.State;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
