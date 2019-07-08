package persistence.entityPersisters;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.LaboratoryExam;
import entities.LaboratoryExamAttachment;
import persistence.Database;

public abstract class LaboratoryExamPersistence {

	private static final String SELECT_INFORMED_CONSENT_LABORATORY_EXAMS_QUERY = 
			"SELECT " + 
			"    id_laboratory_exam, " + 
			"    name " + 
			"FROM laboratory_exam " + 
			"WHERE informed_consent = ?";
	
	private static final String SELECT_LABORATORY_EXAM_ATTACHMENTS_QUERY = 
			"SELECT " + 
			"    id_laboratory_exam_attachment, " + 
			"    name, " + 
			"    uri " + 
			"FROM laboratory_exam_attachment " + 
			"WHERE laboratory_exam = ?";
	
	private static final String INSERT_LABORATORY_EXAM = 
			"INSERT INTO laboratory_exam " + 
			"    ( " + 
			"        informed_consent, " + 
			"        name " + 
			"    ) " + 
			"VALUES (?,?)";
	
	private static final String INSERT_LABORATORY_EXAM_ATTACHMENT = 
			"INSERT INTO laboratory_exam_attachment " + 
			"    ( " + 
			"        laboratory_exam, " + 
			"        name, " + 
			"        uri " + 
			"    ) " + 
			"VALUES (?,?,?)";
	
	private static List<LaboratoryExamAttachment> loadLaboratoryExamAttachments(int laboratoryExamId) throws SQLException{
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_LABORATORY_EXAM_ATTACHMENTS_QUERY);
		// Set query parameters
		preparedStatement.setInt(1, laboratoryExamId);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		List<LaboratoryExamAttachment> attachments = new ArrayList<>();
		// Iterate over the result set
		while (resultSet.next()) {
			// Retrieve column data
			int id = resultSet.getInt(1);
			String name = resultSet.getString(2);
			String uriString = resultSet.getString(3);
			URI uri;
			try {
				uri = new URI(uriString);
			} catch (URISyntaxException e) {
				uri = null;
			}
			// Instantiate attachment object
			LaboratoryExamAttachment attachment = new LaboratoryExamAttachment();
			attachment.setId(id);
			attachment.setName(name);
			attachment.setUri(uri);
			// Add to object to list
			attachments.add(attachment);
		}
		
		return attachments;
	}
	
	public static List<LaboratoryExam> loadInformedConsentLaboratoryExams(int informedConsentId) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_INFORMED_CONSENT_LABORATORY_EXAMS_QUERY);
		// Set query parameters
		preparedStatement.setInt(1, informedConsentId);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		List<LaboratoryExam> exams = new ArrayList<>();
		// Iterate over the result set
		while (resultSet.next()) {
			// Retrieve column data
			int id = resultSet.getInt(1);
			String name = resultSet.getString(2);
			// Instantiate laboratory exam object
			LaboratoryExam laboratoryExam = new LaboratoryExam();
			laboratoryExam.setId(id);
			laboratoryExam.setName(name);
			laboratoryExam.setAttachments(loadLaboratoryExamAttachments(id));
			// Add to object to list
			exams.add(laboratoryExam);
		}
		
		return exams;
	}
	
	public static void saveLaboratoryExamAttachments(int examId, LaboratoryExamAttachment laboratoryExamAttachment) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_LABORATORY_EXAM_ATTACHMENT, Statement.RETURN_GENERATED_KEYS);
		// Set query parameters
		preparedStatement.setInt(1,examId);
		preparedStatement.setString(2, laboratoryExamAttachment.getName());
		preparedStatement.setString(3, laboratoryExamAttachment.getUri().toString());
		// Execute query
		if (preparedStatement.executeUpdate() == 1) {
			// Get generated keys
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.first())
				laboratoryExamAttachment.setId(generatedKeys.getInt(1));
			else
				throw new SQLException("Save procedure failed, no IDs were generated");
		} else
			throw new SQLException("Save procedure failed, no rows were updated");
	}
	
	public static void saveLaboratoryExam(int informedConsentId, LaboratoryExam laboratoryExam) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_LABORATORY_EXAM, Statement.RETURN_GENERATED_KEYS);
		// Set query parameters
		preparedStatement.setInt(1, informedConsentId);
		preparedStatement.setString(2, laboratoryExam.getName());
		// Execute query
		if (preparedStatement.executeUpdate() == 1) {
			// Get generated keys
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.first())
				laboratoryExam.setId(generatedKeys.getInt(1));
			else
				throw new SQLException("Save procedure failed, no IDs were generated");
		} else
			throw new SQLException("Save procedure failed, no rows were updated");
	}
	
}
