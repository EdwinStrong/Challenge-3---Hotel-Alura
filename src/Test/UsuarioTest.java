package Test;

import java.sql.Connection;

import Controller.UsuarioController;
import Factory.ConnectionFactory;
import Model.Usuario;

public class UsuarioTest {
	/**
	 * Main para los tests de usuarios
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*//CONEEXION
		ConnectionFactory conexion = new ConnectionFactory();

		final Connection con = conexion.recuperaConexion();
		
		//Cerramos la conexión
		try(con){
			conexion.recuperaConexion();
			
			System.out.println("Todo piola.");
			
		}catch(Exception e) {
			System.out.println(e);
		}
		*/
		UsuarioController usuarioConexion = null;
		
		//registrarUsuario(usuarioConexion);
		
		obtenerUsuarios(usuarioConexion);
	}
	
	public static void registrarUsuario(UsuarioController usuarioConexion) {
		Usuario usuario = new Usuario("Alexander5", "XDSASAS");
		//Abrir la conexión.
		usuarioConexion = new UsuarioController();
		//Registrar un usuario.
		usuarioConexion.registrarUsuario(usuario);
		System.out.println("A");
	}
	
	public static void obtenerUsuarios(UsuarioController usuarioConexion) {
		usuarioConexion = new UsuarioController();
				
		usuarioConexion.obtenerUsuario().forEach(usuario -> {
			System.out.println(usuario);
		});
	}
}
