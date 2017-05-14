package application.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/*
 * ConnectionManager takes care of connection with db
 */
public class ConnectionManager {

	private static final String USERNAME = "student";
	private static final String PASSWORD = "abcdef";
	private static final String URL = "jdbc:db2://localhost:50001/VSTUD";
	private Connection connection;

	static {
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ConnectionManager() {
		connection = null;
	}
	/*
	 * returns established connection to database
	 */
	public Connection connect() {
		

		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Creating connection");
		return connection;

	}

	/*
	 * Breaks open connection
	 */
	public void disconnect() {	
		
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Disconnected");
	}

}
