package persistence.entityPersisters;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entities.Trauma;
import persistence.Database;

public class TraumaPersistence {
	
	private static final String  SELECT_QUERY =
			"SELECT  " +  
			"    id_trauma_history, " +
			"    trauma_nature, " + 
			"    sequels, " + 
			"    occurrence_date, " + 
			"    registered_on " + 
			"FROM " + 
			"    trauma_history " + 
			"WHERE " + 
			"    client = ?";
			
	
	private static final String INSERT_QUERY = 
			"INSERT INTO trauma_history " + 
			"	( " + 
			"		client, " + 
			"		trauma_nature, " + 
			"		sequels, " + 
			"		occurrence_date, " + 
			"		registered_on " +
			"	) " + 
			"VALUES(?,?,?,?,?)";
	
	public static List<Trauma> loadTraumas(String clientId) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_QUERY);
		// Set query parameters
		preparedStatement.setString(1, clientId);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
	
		List<Trauma> traumas=new ArrayList<Trauma>(); 
		// Iterate over the results
		while (resultSet.next()) {
			// Retrieve column data
			int traumaId = resultSet.getInt(1);
			String traumaNature = resultSet.getString(2);
			String sequels = resultSet.getString(3);
			LocalDate occurrenceDate = resultSet.getDate(4).toLocalDate();
			LocalDate registeredOn = resultSet.getDate(5).toLocalDate();
			
			// Instantiate the Trauma object
			Trauma trauma = new Trauma();
			trauma.setId(traumaId);
			trauma.setTraumaNature(traumaNature);
			trauma.setSequels(sequels);
			trauma.setOccurrenceDate(occurrenceDate);
			trauma.setRegisteredOn(registeredOn);
			
			// Add the trauma to the list of traumas
			traumas.add(trauma);
		}

		return traumas;
	}
			
	public static boolean saveTrauma(String clientId, Trauma trauma) throws SQLException {
		
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_QUERY);
		// Set query parameters
		preparedStatement.setString(1, clientId);
		preparedStatement.setString(2, trauma.getTraumaNature());
		preparedStatement.setString(3, trauma.getSequels());
		preparedStatement.setDate(4, Date.valueOf(trauma.getOccurrenceDate()));
		preparedStatement.setDate(5, Date.valueOf(trauma.getRegisteredOn()));
		
		return preparedStatement.executeUpdate() == 1;		
		
	}

}
