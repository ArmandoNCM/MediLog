package persistence.entityPersisters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.ContactInformation;
import persistence.Database;

public class ContactInformationPersistence {
	
	private static final String SELECT_QUERY = 
			"SELECT   " + 
			"   id_contact_information, " + 
			"   type, " + 
			"   name, " +
			"   contact " +
			"FROM " +
			"   contact_information " +
			"WHERE " +
			"   company = ?";
	
	private static final String INSERT_QUERY = 
			"INSERT INTO contact_information " +
			"   ( " +
			"       type, " + 
			"       name, " +
			"       contact, " +
			"       company "  + 
			"   ) " + 
			"VALUES(?,?,?,?)";
	
	public static List<ContactInformation> loadContactInformation (String companyId) throws SQLException {
	
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_QUERY);
		// Set query parameters
		preparedStatement.setString(1, companyId);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		// Create list of contact information entries
		List<ContactInformation> contactInformationList = new ArrayList<>();
		// Iterate over the results
		while (resultSet.next()) {
			// Retrieve column data
			int contactInformationId = resultSet.getInt(1);
			String type = resultSet.getString(2);
			String name = resultSet.getString(3);
			String contact = resultSet.getString(4);
			
			// Instantiate ContactInformation object
			ContactInformation contactInformation = new ContactInformation();
			contactInformation.setId(contactInformationId);
			contactInformation.setType(type);
			contactInformation.setName(name);
			contactInformation.setContact(contact);
			
			contactInformationList.add(contactInformation);
		}
		
		return contactInformationList;
	}
	
	public static boolean saveContacInformation(String companyId, ContactInformation contactInformation) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_QUERY);
		// Set query parameters
		preparedStatement.setString(1, contactInformation.getType());
		preparedStatement.setString(2, contactInformation.getName());
		preparedStatement.setString(3, contactInformation.getContact());
		preparedStatement.setString(4, companyId);
		// Execute query		
		return preparedStatement.executeUpdate() == 1;
	}

}
