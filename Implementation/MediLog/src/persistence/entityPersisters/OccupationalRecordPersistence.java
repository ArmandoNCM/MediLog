package persistence.entityPersisters;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entities.OccupationalRecord;
import persistence.Database;

public abstract class OccupationalRecordPersistence {
	
	private static final String SELECT_QUERY = 
			"SELECT " + 
			"    id_occupational_record, " + 
			"    company, " + 
			"    start_date, " + 
			"    end_date, " + 
			"    role, " + 
			"    registered_on " + 
			"FROM occupational_record " + 
			"WHERE client = ? " + 
			"ORDER BY registered_on ASC";
	
	
	private static final String INSERT_QUERY =
			"INSERT INTO occupational_record " + 
			"    ( " + 
			"        client, " + 
			"        company, " + 
			"        start_date, " + 
			"        end_date, " + 
			"        role, " + 
			"        registered_on " + 
			"    ) " + 
			"VALUES (?, ?, ?, ?, ?, ?)";
	
	
	public static List<OccupationalRecord> loadOccupationalRecords(String clientId) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_QUERY);
		// Set query parameters
		preparedStatement.setString(1, clientId);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<OccupationalRecord> occupationalRecords = new ArrayList<OccupationalRecord>();
		// Iterate over the result set
		while (resultSet.next()) {
			// Retrieve column data
			int occupationalRecordId = resultSet.getInt(1);
			String companyId = resultSet.getString(2);
			LocalDate startDate = resultSet.getDate(3).toLocalDate();
			LocalDate endDate = resultSet.getDate(4) != null ? resultSet.getDate(4).toLocalDate() : null;
			String role = resultSet.getString(5);
			LocalDate registeredOn = resultSet.getDate(6).toLocalDate();
			// Instantiate Habit object
			OccupationalRecord occupationalRecord = new OccupationalRecord();
			occupationalRecord.setId(occupationalRecordId);
			occupationalRecord.setCompany(CompanyPersistence.loadCompany(companyId));
			occupationalRecord.setStartDate(startDate);
			occupationalRecord.setEndDate(endDate);
			occupationalRecord.setRole(role);
			occupationalRecord.setRegisteredOn(registeredOn);
			// Add to object to list
			occupationalRecords.add(occupationalRecord);
		}
		return occupationalRecords;
	}
	
	public static boolean saveOccupationalRecord(String clientId, OccupationalRecord occupationalRecord) throws SQLException { 
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_QUERY);
		// Set query parameters
		preparedStatement.setString(1, clientId);
		preparedStatement.setString(2, occupationalRecord.getCompanyId());
		preparedStatement.setDate(3, Date.valueOf(occupationalRecord.getStartDate()));
		preparedStatement.setDate(4, occupationalRecord.getEndDate() != null ? Date.valueOf(occupationalRecord.getEndDate()) : null);
		preparedStatement.setString(5, occupationalRecord.getRole());
		preparedStatement.setDate(6, Date.valueOf(occupationalRecord.getRegisteredOn()));
		// Execute query
		return preparedStatement.executeUpdate() == 1;
	}

}
