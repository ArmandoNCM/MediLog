package persistence.entityPersisters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Company;
import persistence.Database;

public class CompanyPersistence {
	
	private static final String SELECT_QUERY =
			"SELECT " + 
			"    name " + 
			"FROM company " + 
			"WHERE id_company = ?";
	
	private static final String INSERT_QUERY =
			"INSERT INTO company " + 
			"    ( " + 
			"        id_company, " + 
			"        name " + 
			"    ) " + 
			"VALUES (?, ?)";
	
	
	public static Company loadCompany(String companyId) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_QUERY);
		// Set query parameters
		preparedStatement.setString(1, companyId);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		// Go to first and only result record
		if (resultSet.first()) {
			// Retrieve column data
			String name = resultSet.getString(1);
			// Instantiate Company 
			Company company = new Company();
			company.setId(companyId);
			company.setName(name);
			company.setContactInformationEntries(ContactInformationPersistence.loadContactInformation(companyId));
			// Return result
			return company;
		}
		return null;
	}
	
	public static boolean saveCompany(Company company) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_QUERY);
		// Set query parameters
		preparedStatement.setString(1, company.getId());
		preparedStatement.setString(2, company.getName());
		// Execute query
		return preparedStatement.executeUpdate() == 1;
	}

}
