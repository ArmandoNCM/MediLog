package persistence.entityPersisters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Employee;
import persistence.Database;

public class EmployeePersistence {

	private static final String SELECT_QUERY =
			"SELECT COUNT(id_employee) AS employee_exists " + 
			"FROM employee " + 
			"WHERE id_employee = ?";
	
	
	public static Employee loadEmployee(String employeeId) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_QUERY);
		// Set query parameters
		preparedStatement.setString(1, employeeId);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		// Go to first and only result record
		if (resultSet.first() && resultSet.getInt(1) == 1) {
			// Instantiate Client object
			Employee employee = new Employee();
			employee.setId(employeeId);
			
			// Load the Person (superclass) fields from database
			if (PersonPersistence.loadPerson(employee)) {
				return employee;
			}
		}
		return null;
	}
}
