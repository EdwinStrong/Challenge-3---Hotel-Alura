package Factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;


/***
 * Esta clase se encarga de realizar la conexión directa a la base de datos
 */
public class ConnectionFactory {
	
	private DataSource dataSource;
	
	/***
	 * Constructor por defecto para la conexión.
	 */
	public ConnectionFactory() {
		//Definimos el pool de conexiones
		ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
		pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/challenge3DB?useTimeZone=true&serverTimeZone=UTC");
		pooledDataSource.setUser("root");
		pooledDataSource.setPassword("0123456789");
		pooledDataSource.setMaxPoolSize(10);//Máximo de conexiones.
		this.dataSource = pooledDataSource;
	}
	
	/**
	 * Se encarga de realizar la conexión a la base de datos.
	 * @return Connection o Una excepción.
	 */
	public Connection recuperaConexion() {
		try {
			return this.dataSource.getConnection();
		}catch(SQLException error) {
			throw new RuntimeException(error.getMessage());
		}
	}
}
