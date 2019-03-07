package persistence.entityPersisters;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import entities.Person;
import persistence.Database;

public class PersonPersistence {
	
	private static final String INSERT_QUERY = 
			"INSERT INTO person"
			+ "("
				+ "id_person,"
				+ "first_name,"
				+ "last_name,"
				+ "birth_date,"
				+ "id_expedition_city,"
				+ "identification_type,"
				+ "gender"
			+ ")"
			+ "VALUES(?,?,?,?,?,?,?)";
	
	private static final String LOAD_QUERY = 
			"SELECT" + 
			"    first_name," + 
			"    last_name," + 
			"    birth_date," + 
			"    id_expedition_city," + 
			"    identification_type," + 
			"    gender" + 
			"FROM person" + 
			"WHERE id_person = ?";

	
	public static boolean savePerson(Person person) throws SQLException {
		
		// Create prepared statement with parameterized insert query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_QUERY);
		// Set query parameters
		preparedStatement.setString(1, person.getId());
		preparedStatement.setString(2, person.getFirstName());
		preparedStatement.setString(3,  person.getLastName());
		preparedStatement.setDate(4, Date.valueOf(person.getBirthDate()));
		preparedStatement.setInt(5, person.getIdExpeditionCityId());
		preparedStatement.setString(6, String.valueOf(person.getIdentificationType()));
		preparedStatement.setString(7, String.valueOf(person.getGender()));
		// Execute query
		return preparedStatement.executeUpdate() == 1;
	}
	
	public static boolean loadPerson(Person person) throws SQLException {
		
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(LOAD_QUERY);
		// Set query parameters
		preparedStatement.setString(1, person.getId());
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		// Go to first and only result record
		if (resultSet.first()) {
			// Retrieve column data
			String firstName = resultSet.getString(1);
			String lastName = resultSet.getString(2);
			LocalDate birthDate = resultSet.getDate(3).toLocalDate();
			int idExpeditionCityId = resultSet.getInt(4);
			char identificationType = resultSet.getString(5).charAt(0);
			char gender = resultSet.getString(6).charAt(0);

			// Initializing fields of person instance 
			person.setFirstName(firstName);
			person.setLastName(lastName);
			person.setBirthDate(birthDate);
			person.setIdExpeditionCityId(idExpeditionCityId);
			person.setIdExpeditionCity(LocationPersistance.loadLocation(idExpeditionCityId));
			person.setIdentificationType(identificationType);
			person.setGender(gender);
			
			return true;
		}
		return false;
	}

}
