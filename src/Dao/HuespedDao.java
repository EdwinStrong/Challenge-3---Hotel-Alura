package Dao;

import java.awt.Taskbar.State;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

import Model.Huesped;
import Model.Usuario;

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
}