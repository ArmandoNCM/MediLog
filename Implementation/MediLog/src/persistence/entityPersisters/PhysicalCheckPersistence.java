package persistence.entityPersisters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.InformedConsent;
import entities.MedicalAnomaly;
import entities.PhysicalCheck;
import persistence.Database;

public abstract class PhysicalCheckPersistence {
	
	private static final String SELECT_QUERY = 
			"SELECT " + 
			"    employee, " + 
			"    weight_kilograms, " + 
			"    height_centimeters, " + 
			"    pulse_beats_per_minute, " + 
			"    respiratory_frequency_per_minute, " + 
			"    body_temperature, " + 
			"    blood_pressure_standing, " + 
			"    blood_pressure_laying_down, " + 
			"    handedness, " + 
			"    diagnostics, " + 
			"    conclusions, " + 
			"    recommendations " + 
			"FROM physical_check " + 
			"WHERE id_physical_check = ?";

	private static final String INSERT_QUERY =
			"INSERT INTO physical_check  " + 
			"    ( " + 
			"        id_physical_check, " + 
			"        employee, " + 
			"        weight_kilograms, " + 
			"        height_centimeters, " + 
			"        pulse_beats_per_minute, " + 
			"        respiratory_frequency_per_minute, " + 
			"        body_temperature, " + 
			"        blood_pressure_standing, " + 
			"        blood_pressure_laying_down, " + 
			"        handedness, " + 
			"        diagnostics, " + 
			"        conclusions, " + 
			"        recommendations  " + 
			"    )  " + 
			"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) " + 
			"ON DUPLICATE KEY UPDATE " + 
			"    employee = VALUES(employee), " + 
			"    weight_kilograms = VALUES(weight_kilograms), " + 
			"    height_centimeters = VALUES(height_centimeters), " + 
			"    pulse_beats_per_minute = VALUES(pulse_beats_per_minute), " + 
			"    respiratory_frequency_per_minute = VALUES(respiratory_frequency_per_minute), " + 
			"    body_temperature = VALUES(body_temperature), " + 
			"    blood_pressure_standing = VALUES(blood_pressure_standing), " + 
			"    blood_pressure_laying_down = VALUES(blood_pressure_laying_down), " + 
			"    handedness = VALUES(handedness), " + 
			"    diagnostics = VALUES(diagnostics), " + 
			"    conclusions = VALUES(conclusions), " + 
			"    recommendations = VALUES(recommendations)";
	
	static PhysicalCheck loadPhysicalCheck(InformedConsent informedConsent) throws SQLException{
		// Create prepared statement
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_QUERY);
		// Set parameters
		preparedStatement.setInt(1, informedConsent.getId());
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		// Check results
		if (resultSet.first()) {
			// Retrieve data from columns
			String employeeId = resultSet.getString(1);
			float weight = resultSet.getFloat(2);
			short height = resultSet.getShort(3);
			short pulse = resultSet.getShort(4);
			short respiratoryFrequency = resultSet.getShort(5);
			float bodyTemperature = resultSet.getFloat(6);
			float bloodPressureStanding = resultSet.getFloat(7);
			float bloodPressureLayingDown = resultSet.getFloat(8);
			char handedness = resultSet.getString(9).charAt(0);
			String diagnostics = resultSet.getString(10);
			String conclusions = resultSet.getString(11);
			String recommendations = resultSet.getString(12);
			// Instantiate PhysicalCheck object
			PhysicalCheck physicalCheck = new PhysicalCheck(informedConsent);
			physicalCheck.setEmployee(EmployeePersistence.loadEmployee(employeeId));
			physicalCheck.setWeightKilograms(weight);
			physicalCheck.setHeightCentimeters(height);
			physicalCheck.setPulseBeatsPerMinute(pulse);
			physicalCheck.setRespiratoryFrequencyPerMinute(respiratoryFrequency);
			physicalCheck.setBodyTemperature(bodyTemperature);
			physicalCheck.setBloodPressureStanding(bloodPressureStanding);
			physicalCheck.setBloodPressureLayingDown(bloodPressureLayingDown);
			physicalCheck.setHandedness(handedness);
			physicalCheck.setDiagnostics(diagnostics);
			physicalCheck.setConclusions(conclusions);
			physicalCheck.setRecommendations(recommendations);
			physicalCheck.setMedicalAnomalies(MedicalAnomalyPersistence.loadPhysicalCheckMedicalAnomalies(physicalCheck.getId()));
			
			return physicalCheck;
		}
		return null;
	}
	
	public static void savePhysicalCheck(PhysicalCheck physicalCheck) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_QUERY);
		// Set query parameters
		preparedStatement.setInt(1, physicalCheck.getId());
		preparedStatement.setString(2, physicalCheck.getEmployeeId());
		preparedStatement.setFloat(3, physicalCheck.getWeightKilograms());
		preparedStatement.setShort(4, physicalCheck.getHeightCentimeters());
		preparedStatement.setShort(5, physicalCheck.getPulseBeatsPerMinute());
		preparedStatement.setShort(6, physicalCheck.getRespiratoryFrequencyPerMinute());
		preparedStatement.setFloat(7, physicalCheck.getBodyTemperature());
		preparedStatement.setFloat(8, physicalCheck.getBloodPressureStanding());
		preparedStatement.setFloat(9, physicalCheck.getBloodPressureLayingDown());
		preparedStatement.setString(10, String.valueOf(physicalCheck.getHandedness()));
		preparedStatement.setString(11, physicalCheck.getDiagnostics());
		preparedStatement.setString(12, physicalCheck.getConclusions());
		preparedStatement.setString(13, physicalCheck.getRecommendations());
		// Execute query
		preparedStatement.executeUpdate();
		if (physicalCheck.getMedicalAnomalies() != null)
			MedicalAnomalyPersistence.savePhysicalCheckMedicalAnomalies(physicalCheck.getId(), physicalCheck.getMedicalAnomalies().toArray(new MedicalAnomaly[0]));
	}

}
