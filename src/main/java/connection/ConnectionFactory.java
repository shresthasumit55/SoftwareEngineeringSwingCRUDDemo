package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public static final String JDBC_URL = "jdbc:derby:cowdb;create=true";
	
	private static ConnectionFactory INSTANCE;
	
	public static ConnectionFactory getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new ConnectionFactory();
		}
		return INSTANCE;
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(JDBC_URL);
	}
}
