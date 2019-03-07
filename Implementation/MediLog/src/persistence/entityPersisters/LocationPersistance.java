package persistence.entityPersisters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Location;
import persistence.Database;

public class LocationPersistance {
	
	private static final String LOAD_QUERY = 
			"SELECT " + 
			"    country.id_country AS country_id," + 
			"    country.name AS country," + 
			"    state_province.id_state_province AS state_province_id," + 
			"    state_province.name AS state_province," + 
			"    city.name AS city" + 
			"FROM city" + 
			"INNER JOIN state_province" + 
			"    ON city.state_province = state_province.id_state_province" + 
			"INNER JOIN country" + 
			"    ON state_province.country = country.id_country" + 
			"WHERE city.id_city = ?";
	
	public static Location loadLocation(int cityId) throws SQLException {
		
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(LOAD_QUERY);
		// Set query parameters
		preparedStatement.setInt(1, cityId);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		
		// Go to first and only result record
		if (resultSet.first()) {
			// Retrieve column data
			int countryId = resultSet.getInt(1);
			String country = resultSet.getString(2);
			int stateProvinceId = resultSet.getInt(3);
			String stateProvince = resultSet.getString(4);
			String city = resultSet.getString(5);

			// Instantiate and initialize Location object
			Location location = new Location();
			location.setCountryId(countryId);
			location.setCountry(country);
			location.setStateProvinceId(stateProvinceId);
			location.setStateProvince(stateProvince);
			location.setCityId(cityId);
			location.setCity(city);
			
			return location;
		}
		
		return null;
	}
}
