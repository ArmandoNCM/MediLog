package persistence.entityPersisters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.ContactInformation;
import persistence.Database;

public class ContactInformationPersistence {
	
	private static final String SELECT_QUERY = 
			"SELECT   " + 
			"   id_contact_information, " + 
			"   type, " + 
			"   name, " +
			"   contact, " +
			"FROM " +
			"   contact_information " +
			"WHERE " +
			"   company = ?";
	
	private static final String INSERT_QUERY = 
			"INSERT INTO contact_information " +
			"   ( " +
			"       type, " + 
			"       name, " +
			"       contect, " +
			"       company "  + 
			"   ) " + 
			"VALUES(?,?,?,?)";
	
	public static ContactInformation loadContactInformation (int contactId) throws SQLException {
	
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_QUERY);
		// Set query parameters
		preparedStatement.setInt(1, contactId);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		// Go to first and only result record
		if(resultSet.first()) {
			// Retrieve column data
			String type = resultSet.getString(1);
			String name = resultSet.getString(2);
			String contact = resultSet.getString(3);
			String company = resultSet.getString(4);
			
			// Instantiate Client object
			ContactInformation contactInformation = new ContactInformation();
			contactInformation.setId(contactId);
			contactInformation.setType(type);
			contactInformation.setName(name);
			contactInformation.setContact(contact);
			
			return contactInformation;
		}
		
		return null;
	}
	
	public static boolean saveContacInformation(String company, ContactInformation contactInformation) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_QUERY);
		// Set query parameters
		preparedStatement.setString(1, contactInformation.getType());
		preparedStatement.setString(2, contactInformation.getName());
		preparedStatement.setString(3, contactInformation.getContact());
		preparedStatement.setString(4, company);
		// Execute query		
		return preparedStatement.executeUpdate() == 1;
	}

}
