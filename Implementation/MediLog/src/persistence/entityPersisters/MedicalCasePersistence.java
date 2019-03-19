package persistence.entityPersisters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entities.MedicalCase;
import persistence.Database;

public class MedicalCasePersistence {
	
	private static final String SELECT_MEDICAL_CASES_QUERY = 
			"SELECT " + 
			"    medical_case_type.id_medical_case_type, " + 
			"    medical_case_type.name, " + 
			"    case_history.background_type, " + 
			"    case_history.registered_on " + 
			"FROM case_history " + 
			"JOIN medical_case_type " + 
			"    ON case_history.case_type = medical_case_type.id_medical_case_type " + 
			"WHERE case_history.client = ?";
	
	private static final String SELECT_MEDICAL_CASE_TYPES_QUERY = 
			"SELECT " + 
			"    id_medical_case_type, " + 
			"    name " + 
			"FROM medical_case_type";
	
	public static List<MedicalCase> getClientMedicalCases(String clientId) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_MEDICAL_CASES_QUERY);
		// Set query parameters
		preparedStatement.setString(1, clientId);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<MedicalCase> medicalCases = new ArrayList<MedicalCase>();
		// Iterate over the result set
		while (resultSet.next()) {
			// Retrieve column data
			int medicalCaseTypeId = resultSet.getInt(1);
			String medicalCaseName = resultSet.getString(2);
			char backgroundType = resultSet.getString(3).charAt(0);
			LocalDate registeredOn = resultSet.getDate(4).toLocalDate();
			// Instantiate MedicalCase object
			MedicalCase medicalCase = new MedicalCase();
			medicalCase.setId(medicalCaseTypeId);
			medicalCase.setName(medicalCaseName);
			medicalCase.setBackgroundType(backgroundType);
			medicalCase.setRegisteredOn(registeredOn);
			// Add to object to list
			medicalCases.add(medicalCase);
		}
		return medicalCases;
	}
	
	public static List<MedicalCase> getMedicalCaseTypes() throws SQLException {
		// Create a simple statement and run query
		Statement statement = Database.getInstance().getConnection().createStatement();
		ResultSet resultSet = statement.executeQuery(SELECT_MEDICAL_CASE_TYPES_QUERY);
		List<MedicalCase> medicalCaseTypes = new ArrayList<MedicalCase>();
		// Iterate over the result set
		while(resultSet.next()) {
			// Retrieve column data
			int medicalCaseTypeId = resultSet.getInt(1);
			String medicalCaseName = resultSet.getString(2);
			// Instantiate MedicalCase object
			MedicalCase medicalCaseType = new MedicalCase();
			medicalCaseType.setId(medicalCaseTypeId);
			medicalCaseType.setName(medicalCaseName);
			// Add object to list
			medicalCaseTypes.add(medicalCaseType);
		}
		return medicalCaseTypes;
	}

}
