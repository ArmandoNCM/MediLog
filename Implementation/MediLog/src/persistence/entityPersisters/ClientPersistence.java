package persistence.entityPersisters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Client;
import persistence.Database;

public abstract class ClientPersistence {
	
	private static final String SELECT_QUERY = 
			"SELECT " + 
			"    city, " + 
			"    academic_level, " + 
			"    social_level, " + 
			"    civil_status, " + 
			"    address, " + 
			"    phone " + 
			"FROM client " + 
			"WHERE id_client = ?";
	
	private static final String INSERT_QUERY =
			"INSERT INTO client " + 
			"    ( " + 
			"        id_client, " + 
			"        city, " + 
			"        academic_level, " + 
			"        social_level, " + 
			"        civil_status, " + 
			"        address, " + 
			"        phone " + 
			"    ) " + 
			"VALUES(?,?,?,?,?,?,?)";
	
	
	public static Client loadClient(String clientId) throws SQLException {
		
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_QUERY);
		// Set query parameters
		preparedStatement.setString(1, clientId);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		// Go to first and only result record
		if (resultSet.first()) {
			// Retrieve column data
			int cityId = resultSet.getInt(1);
			char academicLevel = resultSet.getString(2) != null ? resultSet.getString(2).charAt(0) : 0;
			byte socialLevel = resultSet.getByte(3);
			char civilStatus = resultSet.getString(4) != null ? resultSet.getString(4).charAt(0) : 0;
			String address = resultSet.getString(5);
			String phone = resultSet.getString(6);
			
			// Instantiate Client object
			Client client = new Client();
			client.setId(clientId);
			client.setCityId(cityId);
			client.setCity(LocationPersistance.loadLocation(cityId));
			client.setAcademicLevel(academicLevel);
			client.setSocialLevel(socialLevel);
			client.setCivilStatus(civilStatus);
			client.setAddress(address);
			client.setPhone(phone);
			
			// Load the Person (superclass) fields from database
			if (PersonPersistence.loadPerson(client)) {
				return client;
			}
		}
		
		return null;
	}
	
	public static boolean saveClient(Client client) throws SQLException {
		
		if (!PersonPersistence.personExists(client.getId())) {
			// Store the person (superclass) data
			PersonPersistence.savePerson(client);
		}
		
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_QUERY);
		// Set query parameters
		preparedStatement.setString(1, client.getId());
		preparedStatement.setInt(2, client.getCityId());
		preparedStatement.setString(3, client.getAcademicLevel() != 0 ? String.valueOf(client.getAcademicLevel()) : null);
		preparedStatement.setInt(4, client.getSocialLevel());
		preparedStatement.setString(5, client.getCivilStatus() != 0 ? String.valueOf(client.getCivilStatus()) : null);
		preparedStatement.setString(6, client.getAddress());
		preparedStatement.setString(7, client.getPhone());
		// Execute query
		return preparedStatement.executeUpdate() == 1;
	}

}
