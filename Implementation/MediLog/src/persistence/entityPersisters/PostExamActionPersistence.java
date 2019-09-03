package persistence.entityPersisters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.PostExamAction;
import persistence.Database;

public abstract class PostExamActionPersistence {
	
	private static final String SELECT_POST_EXAM_ACTIONS_QUERY = 
			"SELECT " + 
			"    id_post_exam_action, " + 
			"    name, " + 
			"    type " + 
			"FROM post_exam_action";
	
	private static final String SELECT_POST_EXAM_ACTIONS_BY_TYPE_QUERY = 
			"SELECT " + 
			"    id_post_exam_action, " + 
			"    name " + 
			"FROM post_exam_action " +
			"WHERE type = ?";
	
	private static final String SELECT_APTITUDE_CONCEPT_POST_EXAM_ACTIONS_QUERY = 
			"SELECT " + 
			"    post_exam_action.id_post_exam_action, " + 
			"    post_exam_action.name, " + 
			"    post_exam_action.type, " + 
			"    work_aptitude_concept_post_exam_action.observations " + 
			"FROM post_exam_action " + 
			"JOIN work_aptitude_concept_post_exam_action " + 
			"    ON work_aptitude_concept_post_exam_action.post_exam_action = post_exam_action.id_post_exam_action " + 
			"WHERE work_aptitude_concept_post_exam_action.work_aptitude_concept = ?";

	private static final String INSERT_APTITUDE_CONCEPT_POST_EXAM_ACTION =
			"INSERT INTO work_aptitude_concept_post_exam_action " + 
			"    ( " + 
			"        work_aptitude_concept, " + 
			"        post_exam_action, " + 
			"        observations " + 
			"    ) " + 
			"VALUES (?,?,?) " + 
			"ON DUPLICATE KEY UPDATE " + 
			"    observations = VALUES(observations)";
	
	public static List<PostExamAction> loadPostExamActions() throws SQLException {
		// Create prepared statement
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_POST_EXAM_ACTIONS_QUERY);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		// Iterate over results
		List<PostExamAction> actions = new ArrayList<PostExamAction>();
		while (resultSet.next()) {
			// Retrieve results
			int actionId = resultSet.getInt(1);
			String name = resultSet.getString(2);
			String type = resultSet.getString(3);
			// Instantiate PostExamAction object
			PostExamAction postExamAction = new PostExamAction();
			postExamAction.setId(actionId);
			postExamAction.setName(name);
			postExamAction.setType(type);
			// Add to list
			actions.add(postExamAction);
		}
		return actions;
	}
	
	public static List<PostExamAction> loadPostExamActionsByType(String type) throws SQLException {
		// Create prepared statement
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_POST_EXAM_ACTIONS_BY_TYPE_QUERY);
		// Set parameters
		preparedStatement.setString(1, type);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		// Iterate over results
		List<PostExamAction> actions = new ArrayList<PostExamAction>();
		while (resultSet.next()) {
			// Retrieve results
			int actionId = resultSet.getInt(1);
			String name = resultSet.getString(2);
			// Instantiate PostExamAction object
			PostExamAction postExamAction = new PostExamAction();
			postExamAction.setId(actionId);
			postExamAction.setName(name);
			postExamAction.setType(type);
			// Add to list
			actions.add(postExamAction);
		}
		return actions;
	}
	
	public static List<PostExamAction> loadAptitudeConceptPostExamActions(int aptitudeConceptId) throws SQLException {
		// Create prepared statement
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_APTITUDE_CONCEPT_POST_EXAM_ACTIONS_QUERY);
		// Set parameter
		preparedStatement.setInt(1, aptitudeConceptId);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		// Iterate over results
		List<PostExamAction> actions = new ArrayList<PostExamAction>();
		while (resultSet.next()) {
			// Retrieve results
			int actionId = resultSet.getInt(1);
			String name = resultSet.getString(2);
			String type = resultSet.getString(3);
			String observations = resultSet.getString(4);
			// Instantiate PostExamAction object
			PostExamAction postExamAction = new PostExamAction();
			postExamAction.setId(actionId);
			postExamAction.setName(name);
			postExamAction.setType(type);
			postExamAction.setObservations(observations);
			// Add to list
			actions.add(postExamAction);
		}
		return actions;
	}
	
	public static void savePostExamActions(int aptitudeConceptId, PostExamAction ...actions) throws SQLException {
		// Create prepared statement
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_APTITUDE_CONCEPT_POST_EXAM_ACTION);
		// Iterate over the anomalies
		for (PostExamAction action : actions) {
			// Set parameters
			preparedStatement.setInt(1, aptitudeConceptId);
			preparedStatement.setInt(2, action.getId());
			preparedStatement.setString(3, action.getObservations());
			// Add batch
			preparedStatement.addBatch();
		}
		// Execute query
		preparedStatement.executeBatch();
	}
	
}
