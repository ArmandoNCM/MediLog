package persistence.entityPersisters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.InformedConsent;
import entities.WorkAptitudeConcept;
import persistence.Database;

public abstract class WorkAptitudeConceptPersistence {

	private static final String SELECT_QUERY =
			"SELECT " + 
			"    employee, " + 
			"    work_aptitude, " + 
			"    work_in_heights_aptitude, " + 
			"    concept, " + 
			"    concept_observations, " + 
			"    psychotechnic_test, " + 
			"    recommendations " + 
			"FROM work_aptitude_concept " + 
			"WHERE id_work_aptitude_concept = ?";
	
	private static final String INSERT_QUERY =
			"INSERT INTO work_aptitude_concept " + 
			"    ( " + 
			"        id_work_aptitude_concept, " + 
			"        employee, " + 
			"        work_aptitude, " + 
			"        work_in_heights_aptitude, " + 
			"        concept, " + 
			"        concept_observations, " + 
			"        psychotechnic_test, " + 
			"        recommendations " + 
			"    ) " + 
			"VALUES (?,?,?,?,?,?,?,?) " + 
			"ON DUPLICATE KEY UPDATE " + 
			"    employee = VALUES(employee), " + 
			"    work_aptitude = VALUES(work_aptitude), " + 
			"    work_in_heights_aptitude = VALUES(work_in_heights_aptitude), " + 
			"    concept = VALUES(concept), " + 
			"    concept_observations = VALUES(concept_observations), " + 
			"    psychotechnic_test = VALUES(psychotechnic_test), " + 
			"    recommendations = VALUES(recommendations)";
	
	public static void saveWorkAptitudeConcept(WorkAptitudeConcept workAptitudeConcept) throws SQLException {
		// Create prepared statement
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_QUERY);
		// Set parameters
		preparedStatement.setInt(1, workAptitudeConcept.getId());
		preparedStatement.setString(2, workAptitudeConcept.getEmployeeId());
		preparedStatement.setString(3, String.valueOf(workAptitudeConcept.getWorkAptitude()));
		preparedStatement.setString(4, workAptitudeConcept.getWorkInHeightsAptitude() != 0 ? String.valueOf(workAptitudeConcept.getWorkInHeightsAptitude()) : null);
		preparedStatement.setString(5, String.valueOf(workAptitudeConcept.getConcept()));
		preparedStatement.setString(6, workAptitudeConcept.getConceptObservations());
		preparedStatement.setString(7, String.valueOf(workAptitudeConcept.getPsychotechnicTest()));
		preparedStatement.setString(8, workAptitudeConcept.getRecommendations());
		// Execute insert
		preparedStatement.executeUpdate();
		if (workAptitudeConcept.getPostExamActions() != null && workAptitudeConcept.getPostExamActions().size() > 0)
			 ;// TODO Insert post exam actions
	}
	
	static WorkAptitudeConcept loadWorkAptitudeConcept(InformedConsent informedConsent) throws SQLException {
		// Create prepared statement
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_QUERY);
		// Set parameters
		preparedStatement.setInt(1, informedConsent.getId());
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			// Retrieve results
			String employee = resultSet.getString(1);
			char aptitude = resultSet.getString(2).charAt(0);
			char workInHeightsAptitude = resultSet.getString(3) != null ? resultSet.getString(3).charAt(0) : 0;
			char conceptStatus = resultSet.getString(4).charAt(0);
			String conceptObservations = resultSet.getString(5);
			char psychotechnicTest = resultSet.getString(6).charAt(0);
			String recommendations = resultSet.getString(7);
			// Instantiate WorkAptitudeConcept object 
			WorkAptitudeConcept concept = new WorkAptitudeConcept(informedConsent);
			concept.setEmployee(EmployeePersistence.loadEmployee(employee));
			concept.setWorkAptitude(aptitude);
			concept.setWorkInHeightsAptitude(workInHeightsAptitude);
			concept.setConcept(conceptStatus);
			concept.setConceptObservations(conceptObservations);
			concept.setPsychotechnicTest(psychotechnicTest);
			concept.setRecommendations(recommendations);
			
			return concept;
		}
		return null;
	}
	
}
