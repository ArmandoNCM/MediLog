package persistence.entityPersisters;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entities.Client;
import entities.InformedConsent;
import persistence.Database;

public abstract class InformedConsentPersistence {
	
	private static final String SELECT_CLIENT_INFORMED_CONSENTS_QUERY = 
			"SELECT " + 
			"    id_informed_consent, " + 
			"    employee, " + 
			"    contracting_company, " + 
			"    check_type, " + 
			"    work_in_heights, " + 
			"    date " + 
			"FROM informed_consent " + 
			"WHERE client = ? " +
			"ORDER BY date DESC";
	
	private static final String SELECT_INFORMED_CONSENT_QUERY = 
			"SELECT " + 
			"    client, " + 
			"    employee, " + 
			"    contracting_company, " + 
			"    check_type, " + 
			"    work_in_heights, " + 
			"    date " + 
			"FROM informed_consent " + 
			"WHERE id_informed_consent = ?";
	
	private static final String INSERT_INFORMED_CONSENT_QUERY = 
			"INSERT INTO informed_consent " + 
			"    ( " + 
			"        client, " + 
			"        employee, " + 
			"        contracting_company, " + 
			"        check_type, " + 
			"        work_in_heights, " + 
			"        date " + 
			"    ) " + 
			"VALUES (?,?,?,?,?,?) " + 
			"ON DUPLICATE KEY UPDATE " + 
			"    employee = VALUES(employee), " + 
			"    contracting_company = VALUES(contracting_company), " + 
			"    check_type = VALUES(check_type), " + 
			"    work_in_heights = VALUES(work_in_heights)";
	
	
	public static List<InformedConsent> loadClientInformedConsents(Client client) throws SQLException {
		
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_CLIENT_INFORMED_CONSENTS_QUERY);
		// Set query parameters
		preparedStatement.setString(1, client.getId());
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<InformedConsent> informedConsents = new ArrayList<>();
		// Iterate over the result set
		while (resultSet.next()) {
			// Retrieve column data
			int id = resultSet.getInt(1);
			String employeeId = resultSet.getString(2);
			String contractingCompanyId = resultSet.getString(3);
			char checkType = resultSet.getString(4).charAt(0);
			boolean workInHeights = resultSet.getBoolean(5);
			LocalDate date = resultSet.getDate(6).toLocalDate();
			// Instantiate InformedConsent object
			InformedConsent informedConsent = new InformedConsent(client);
			informedConsent.setId(id);
			informedConsent.setEmployee(EmployeePersistence.loadEmployee(employeeId));
			if (contractingCompanyId != null)
				informedConsent.setContractingCompany(CompanyPersistence.loadCompany(contractingCompanyId));
			informedConsent.setCheckType(checkType);
			informedConsent.setWorkInHeights(workInHeights);
			informedConsent.setDate(date);
			informedConsent.setLaboratoryExams(LaboratoryExamPersistence.loadInformedConsentLaboratoryExams(id));
			informedConsent.setProfessionalRisks(ProfessionalRiskPersistence.loadProfessionalRiskTypes(id));
			PhysicalCheckPersistence.loadPhysicalCheck(informedConsent);
			WorkAptitudeConceptPersistence.loadWorkAptitudeConcept(informedConsent);
			// Add to object to list
			informedConsents.add(informedConsent);
		}
		return informedConsents;
	}
	
	public static InformedConsent loadInformedConsent(int informedConsentId) throws SQLException {
		
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_INFORMED_CONSENT_QUERY);
		// Set query parameters
		preparedStatement.setInt(1, informedConsentId);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (resultSet.first()) {
			// Retrieve column data
			String clientId = resultSet.getString(1);
			String employeeId = resultSet.getString(2);
			String contractingCompanyId = resultSet.getString(3);
			char checkType = resultSet.getString(4).charAt(0);
			boolean workInHeights = resultSet.getBoolean(5);
			LocalDate date = resultSet.getDate(6).toLocalDate();
			// Instantiate InformedConsent object
			Client client = ClientPersistence.loadClient(clientId);
			InformedConsent informedConsent = new InformedConsent(client);
			informedConsent.setId(informedConsentId);
			informedConsent.setEmployee(EmployeePersistence.loadEmployee(employeeId));
			if (contractingCompanyId != null)
				informedConsent.setContractingCompany(CompanyPersistence.loadCompany(contractingCompanyId));
			informedConsent.setCheckType(checkType);
			informedConsent.setWorkInHeights(workInHeights);
			informedConsent.setDate(date);
			informedConsent.setLaboratoryExams(LaboratoryExamPersistence.loadInformedConsentLaboratoryExams(informedConsentId));
			informedConsent.setProfessionalRisks(ProfessionalRiskPersistence.loadProfessionalRiskTypes(informedConsentId));
			PhysicalCheckPersistence.loadPhysicalCheck(informedConsent);
			WorkAptitudeConceptPersistence.loadWorkAptitudeConcept(informedConsent);
			return informedConsent;
		} else {
			return null;
		}
		
	}
	
	public static boolean saveInformedConsent(InformedConsent informedConsent) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_INFORMED_CONSENT_QUERY);
		// Set query parameters
		preparedStatement.setString(1, informedConsent.getClientId());
		preparedStatement.setString(2, informedConsent.getEmployeeId());
		preparedStatement.setString(3, informedConsent.getContractingCompanyId());
		preparedStatement.setString(4, String.valueOf(informedConsent.getCheckType()));
		preparedStatement.setBoolean(5, informedConsent.isWorkInHeights());
		preparedStatement.setDate(6, Date.valueOf(informedConsent.getDate()));
		// Execute query
		return preparedStatement.executeUpdate() == 1;
	}

}
