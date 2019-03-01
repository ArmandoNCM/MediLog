package persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import util.PropertiesUtil;

public class Database {
	
	private static final String DATABASE_CONNECTION_URL = "jdbc:mysql://localhost/test";

	private Connection connection;
	
	private static Database instance = new Database();
	
	private Database() {
		
		try {
		    Properties connectionProperties = PropertiesUtil.loadProperties(PropertiesUtil.DATABASE_PROPERTIES_FILEPATH);
		    connection = DriverManager.getConnection(DATABASE_CONNECTION_URL, connectionProperties);
		} catch (IOException exception) {
			System.out.println("The database connection properties could not be loaded");
			System.out.println("IOException: " + exception.getMessage());
		} catch (SQLException exception) {
		    System.out.println("SQLException: " + exception.getMessage());
		    System.out.println("SQLState: " + exception.getSQLState());
		    System.out.println("VendorError: " + exception.getErrorCode());
		}
		
	}
	
	public static Database getInstance() {
		return instance;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
}
