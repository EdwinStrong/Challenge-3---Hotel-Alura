package Controller;

import java.sql.Connection;
import java.util.List;

import Dao.UsuarioDao;
import Factory.ConnectionFactory;
import Model.Usuario;

public class UsuarioController {
	
	ConnectionFactory connectionFactory;
	
	//Cada vez que se inicialice el controller, se abrirá una conexión.
	public UsuarioController() {
		this.connectionFactory = new ConnectionFactory();
	}
	
	UsuarioDao usuarioDao;
	
	/**
	 * Se encarga de registrar un usuario.
	 * @param usuario
	 * @return 1 si todo salió bien.
	 */
	public Integer registrarUsuario(Usuario usuario) {
		usuarioDao = new UsuarioDao(connectionFactory.recuperaConexion());
		return usuarioDao.registrarUsuario(usuario);
	}
	
	public List<Usuario> obtenerUsuario(){
		usuarioDao = new UsuarioDao(connectionFactory.recuperaConexion());
		return usuarioDao.obtenerUsuarios();
	}
	public String modificarUsuario(String usuario, String nuevaPass) {
		usuarioDao = new UsuarioDao(connectionFactory.recuperaConexion());
		return "Usuario modificado con éxito. Columnas afectadas: "+usuarioDao.modificarContrasenia(usuario, nuevaPass);
	}
	public String eliminarUsuario(String usuario) {
		usuarioDao = new UsuarioDao(connectionFactory.recuperaConexion());
		return "Usuario eliminado con éxito. Columnas afectadas: "+usuarioDao.eliminarUsuario(usuario);
	}
	public Boolean realizarLogin(Usuario usuario) {
		usuarioDao = new UsuarioDao(connectionFactory.recuperaConexion());
		return usuarioDao.realizarLogin(usuario);
	}
}
