package Test;

import java.sql.Connection;

import Factory.ConnectionFactory;

public class UsuarioTest {
	/**
	 * Main para los tests de usuarios
	 * @param args
	 */
	public static void main(String[] args) {
		
		ConnectionFactory conexion = new ConnectionFactory();

		final Connection con = conexion.recuperaConexion();
		
		//Cerramos la conexión
		try(con){
			conexion.recuperaConexion();
			
			System.out.println("Todo piola.");
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
