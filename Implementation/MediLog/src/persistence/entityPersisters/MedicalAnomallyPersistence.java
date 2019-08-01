package persistence.entityPersisters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.MedicalAnomaly;
import persistence.Database;

public abstract class MedicalAnomallyPersistence {

	private static final String SELECT_MEDICAL_ANOMALIES_QUERY = 
			"SELECT " + 
			"    id_medical_anomaly, " + 
			"    name " + 
			"FROM medical_anomaly";
	
	private static final String SELECT_PHYSICAL_CHECK_MEDICAL_ANOMALIES =
			"SELECT " + 
			"    medical_anomaly.id_medical_anomaly, " + 
			"    medical_anomaly.name, " + 
			"    physical_check_medical_anomaly.observations " + 
			"FROM medical_anomaly " + 
			"JOIN physical_check_medical_anomaly " + 
			"    ON physical_check_medical_anomaly.medical_anomaly = medical_anomaly.id_medical_anomaly " + 
			"WHERE physical_check_medical_anomaly.physical_check = ?";
	
	private static final String SELECT_MEDICAL_ANOMALY_TYPES_QUERY =
			"SELECT type " + 
			"FROM medical_anomaly";
	
	private static final String INSERT_PHYSICAL_CHECK_MEDICAL_ANOMALY =
			"INSERT INTO physical_check_medical_anomaly " + 
			"    ( " + 
			"        physical_check, " + 
			"        medical_anomaly, " + 
			"        observations " + 
			"    ) " + 
			"VALUES (?,?,?) " + 
			"ON DUPLICATE KEY UPDATE " + 
			"    observations = VALUES(observations)";
	
	public static List<MedicalAnomaly> loadMedicalAnomalies() throws SQLException {
		// Create prepared statement
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_MEDICAL_ANOMALIES_QUERY);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<MedicalAnomaly> anomalies = new ArrayList<>();
		// Check results
		while (resultSet.next()) {
			// Retrieve data
			int id = resultSet.getInt(1);
			String name = resultSet.getString(2);
			// Instantiate MedicalAnomaly object
			MedicalAnomaly anomaly = new MedicalAnomaly();
			anomaly.setId(id);
			anomaly.setName(name);
			// Add anomaly to list
			anomalies.add(anomaly);
		}
		return anomalies;
	}
	
	public static List<MedicalAnomaly> loadPhysicalCheckMedicalAnomalies(int physicalCheckId) throws SQLException {
		// Create prepared statement
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_PHYSICAL_CHECK_MEDICAL_ANOMALIES);
		preparedStatement.setInt(1, physicalCheckId);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<MedicalAnomaly> anomalies = new ArrayList<>();
		// Check results
		while (resultSet.next()) {
			// Retrieve data
			int id = resultSet.getInt(1);
			String name = resultSet.getString(2);
			String observations = resultSet.getString(3);
			// Instantiate MedicalAnomaly object
			MedicalAnomaly anomaly = new MedicalAnomaly();
			anomaly.setId(id);
			anomaly.setName(name);
			anomaly.setObservations(observations);
			// Add anomaly to list
			anomalies.add(anomaly);
		}
		return anomalies;
	}
	
	public static void savePhysicalCheckMedicalAnomalies(int physicalCheckId, MedicalAnomaly ...anomalies) throws SQLException {
		// Create prepared statement
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_PHYSICAL_CHECK_MEDICAL_ANOMALY);
		// Iterate over the anomalies
		for (MedicalAnomaly anomaly : anomalies) {
			// Set parameters
			preparedStatement.setInt(1, physicalCheckId);
			preparedStatement.setInt(2, anomaly.getId());
			preparedStatement.setString(3, anomaly.getObservations());
			// Add batch
			preparedStatement.addBatch();
		}
		// Execute query
		preparedStatement.executeBatch();
	}
	
	public static List<String> loadMedicalAnomalyTypes() throws SQLException {
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_MEDICAL_ANOMALY_TYPES_QUERY);
		
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<String> types = new ArrayList<>();
		// Check results
		while (resultSet.next()) {
			// Retrieve data
			String medicalAnomalyType = resultSet.getString(1);
			types.add(medicalAnomalyType);
		}
		return types;
	}
}
