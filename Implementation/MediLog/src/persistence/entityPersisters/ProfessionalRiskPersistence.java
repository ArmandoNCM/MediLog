package persistence.entityPersisters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.ProfessionalRisk;
import persistence.Database;

public abstract class ProfessionalRiskPersistence {
	
	private static final String SELECT_PROFESSIONAL_RISK_TYPES_QUERY =
			"SELECT " + 
			"    id_professional_risk, " + 
			"    name, " + 
			"    type " + 
			"FROM professional_risk";
	
	private static final String SELECT_INFORMED_CONSENT_PROFESSIONAL_RISKS_QUERY =
			"SELECT " + 
			"    professional_risk.id_professional_risk, " + 
			"    professional_risk.name, " + 
			"    professional_risk.type " + 
			"FROM professional_risk " + 
			"INNER JOIN informed_consent_professional_risk " + 
			"    ON professional_risk.id_professional_risk = informed_consent_professional_risk.professional_risk " + 
			"WHERE informed_consent_professional_risk.informed_consent = ?";
	
	private static final String INSERT_PROFESSIONAL_RISK_TYPE_QUERY =
			"INSERT INTO professional_risk " + 
			"    ( " + 
			"        name, " + 
			"        type " + 
			"    ) " + 
			"VALUES (?, ?)";
	
	private static final String INSERT_INFORMED_CONSENT_PROFESSIONAL_RISK_QUERY = 
			"REPLACE INTO informed_consent_professional_risk " + 
			"    ( " + 
			"        informed_consent, " + 
			"        professional_risk " + 
			"    ) " + 
			"VALUES (?, ?)";

	public static List<ProfessionalRisk> loadProfessionalRiskTypes() throws SQLException {
		return loadProfessionalRisks(0);
	}
	
	public static List<ProfessionalRisk> loadProfessionalRiskTypes(int informedConsentId) throws SQLException {
		return loadProfessionalRisks(informedConsentId);
	}
	
	private static List<ProfessionalRisk> loadProfessionalRisks(int informedConsentId) throws SQLException {
		// Create prepared statement
		PreparedStatement preparedStatement;
		if (informedConsentId > 0) {
			// Use informed consent in query
			preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_INFORMED_CONSENT_PROFESSIONAL_RISKS_QUERY);
			preparedStatement.setInt(1, informedConsentId);
		} else {
			// Query all professional risks
			preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_PROFESSIONAL_RISK_TYPES_QUERY);
		}
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		// Create list of Professional Risks
		List<ProfessionalRisk> professionalRisks = new ArrayList<ProfessionalRisk>();
		// Iterate over the results
		while (resultSet.next()) {
			// Retrieve column data
			int professionalRiskId = resultSet.getInt(1);
			String name = resultSet.getString(2);
			String type = resultSet.getString(3);
			// Instantiate and initialize ProfessionalRisk object
			ProfessionalRisk professionalRisk = new ProfessionalRisk();
			professionalRisk.setId(professionalRiskId);
			professionalRisk.setName(name);
			professionalRisk.setType(type);
			// Add professional risk to list
			professionalRisks.add(professionalRisk);
		}
		return professionalRisks;
	}
	
	public static boolean saveProfessionalRiskType(ProfessionalRisk professionalRisk) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_PROFESSIONAL_RISK_TYPE_QUERY);
		// Set query parameters
		preparedStatement.setString(1, professionalRisk.getName());
		preparedStatement.setString(2, professionalRisk.getType());
		// Execute query
		return preparedStatement.executeUpdate() == 1;
	}
	
	public static boolean saveProfessionalRisk(int informedConsentId, int professionalRiskId) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_INFORMED_CONSENT_PROFESSIONAL_RISK_QUERY);
		// Set query parameters
		preparedStatement.setInt(1, informedConsentId);
		preparedStatement.setInt(2, professionalRiskId);
		// Execute query
		return preparedStatement.executeUpdate() == 1;
	}
}
