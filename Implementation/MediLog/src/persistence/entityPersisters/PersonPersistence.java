package persistence.entityPersisters;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import entities.Person;
import persistence.Database;

public class PersonPersistence {
	
	private static final String INSERT_QUERY = "INSERT INTO person"
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
	

	
	public static boolean persistPerson(Person person) throws SQLException {
		
		// Create prepared statement with parameterized insert query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_QUERY);
		// Set query parameters
		preparedStatement.setString(1, person.getId());
		preparedStatement.setString(2, person.getFirstName());
		preparedStatement.setString(3,  person.getLastName());
		preparedStatement.setDate(4, Date.valueOf(person.getBirthDate()));
		preparedStatement.setInt(5, person.getExpeditionCityId());
		preparedStatement.setString(6, String.valueOf(person.getIdentificationType()));
		preparedStatement.setString(7, String.valueOf(person.getGender()));
		// Execute query
		return preparedStatement.execute();
	}

}
