package persistence.entityPersisters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Employee;
import persistence.Database;
import util.PasswordUtils;

public abstract class EmployeePersistence {

	private static final String SELECT_QUERY =
			"SELECT role " + 
			"FROM employee " + 
			"WHERE id_employee = ?";
	
	private static final String INSERT_QUERY = 
			"INSERT INTO employee " + 
			"    ( " + 
			"        id_employee, " + 
			"        role, " + 
			"        password_salt, " + 
			"        password_hash " + 
			"    ) " + 
			"VALUES (?,?,?,?) " + 
			"ON DUPLICATE KEY UPDATE " + 
			"    role = VALUES(role)";
	
	private static final String SELECT_PASSWORD_HASH_QUERY = 
			"SELECT " + 
			"    password_salt, " + 
			"    password_hash " + 
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
		if (resultSet.first()) {
			// Retrieve information
			int role = resultSet.getInt(1);
			// Instantiate Client object
			Employee employee = new Employee();
			employee.setId(employeeId);
			employee.setRole(role);

			// Load the Person (superclass) fields from database
			if (PersonPersistence.loadPerson(employee)) {
				return employee;
			}
		}
		return null;
	}
	
	public static void saveEmployee(Employee employee, String password) throws SQLException {
		// Save person
		PersonPersistence.savePerson(employee);
		// Prevent null pointer exception with password
		if (password == null)
			password = "null";
		byte[] salt = PasswordUtils.getNextSalt();
		byte[] passwordHash = PasswordUtils.hash(password.toCharArray(), salt);
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_QUERY);
		// Set query parameters
		preparedStatement.setString(1, employee.getId());
		preparedStatement.setInt(2, employee.getRole());
		preparedStatement.setBytes(3, salt);
		preparedStatement.setBytes(4, passwordHash);
		// Execute query
		preparedStatement.executeUpdate();
	}
	
	public static boolean confirmIdentity(String employeeId, String password) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_PASSWORD_HASH_QUERY);
		// Set query parameters
		preparedStatement.setString(1, employeeId);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();	
		// Retrieve information
		if (resultSet.first()) {
			byte[] salt = resultSet.getBytes(1);
			byte[] passwordHash = resultSet.getBytes(2);
			return PasswordUtils.isExpectedPassword(password.toCharArray(), salt, passwordHash);
		} else {
			return false;
		}
	}
}
