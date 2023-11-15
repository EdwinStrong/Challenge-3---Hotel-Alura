package Controller;

import java.util.List;

import javax.security.auth.login.AppConfigurationEntry;

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
}
