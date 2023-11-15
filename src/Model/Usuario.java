package Model;

public class Usuario {
	private String usuario;
	private String pass;
	
	public Usuario(String usuario, String pass) {
		super();
		this.usuario = usuario;
		this.pass = pass;
	}
	public String getUsuario() {
		return usuario;
	}
	public String getPass() {
		return pass;
	}
	
	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + ", pass=" + pass + "]";
	}
	
	
}
