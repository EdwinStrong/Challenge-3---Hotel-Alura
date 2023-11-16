package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Usuario;

public class UsuarioDao {
	
	final private Connection con;
	
	public UsuarioDao(Connection con) {
		this.con = con;
	}
	
	public Integer registrarUsuario(Usuario usuario) {
		
		try(con){
			PreparedStatement statement = con.prepareStatement("INSERT INTO USUARIOS(usuario, pass) VALUES(?,?);", Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, usuario.getUsuario());
			statement.setString(2, usuario.getPass());
			statement.execute(); //Correr la query
			
			final ResultSet resulSet = statement.getGeneratedKeys();
			
			try(resulSet){
				while(resulSet.next()) {
					System.out.println("REGISTRADO USUARIO: "+resulSet.getString(1));
				}
			}
			
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}	
		return 1;
	}
	
	public List<Usuario> obtenerUsuarios(){
		
		//Variable de apoyo
		Usuario usuario;
		
		try(con){
			List<Usuario> usuarios = new ArrayList<>();
			
			PreparedStatement statement = con.prepareStatement("SELECT * FROM Usuarios;");
			
			statement.execute();
			
			final ResultSet resulSet = statement.getResultSet();
			
			try(resulSet){
				while(resulSet.next()) {
					//Creamos nuevo instancia de usuario
					usuario = new Usuario(resulSet.getString(1), resulSet.getString(2));
					//Agregaramos al usuario a la lista.
					usuarios.add(usuario);
				}
				return usuarios;
			}
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Integer modificarContrasenia(String usuario, String nuevoPass) {
		try(con){
			PreparedStatement statement = con.prepareStatement("UPDATE USUARIOS SET pass = ?"
					+ "WHERE usuario=?;");
			statement.setString(1, nuevoPass);
			statement.setString(2, usuario);
			statement.execute();
			//Obtener las columnas modificadas
			Integer updateCount = statement.getUpdateCount();
			
			return updateCount;
			
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	public Integer eliminarUsuario(String usuario) {
		try(con){
			PreparedStatement statement = con.prepareStatement("DELETE FROM USUARIOS WHERE usuario=?;");
			statement.setString(1, usuario);
			statement.execute();
			//Obtener las columnas modificadas
			Integer updateCount = statement.getUpdateCount();
			
			return updateCount;
			
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
