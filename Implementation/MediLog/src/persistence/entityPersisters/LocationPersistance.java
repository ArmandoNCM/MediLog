package persistence.entityPersisters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Location;
import persistence.Database;

public abstract class LocationPersistance {
	
	private static final String SELECT_COUNTRIES_QUERY =
			"SELECT " + 
			"    id_country, " + 
			"    name " + 
			"FROM country";
	
	private static final String SELECT_STATES_PROVINCES_QUERY = 
			"SELECT " + 
			"    id_state_province, " + 
			"    name " + 
			"FROM state_province " + 
			"WHERE country = ?";
	
	private static final String SELECT_CITIES_QUERY =
			"SELECT " + 
			"    id_city, " + 
			"    name " + 
			"FROM city " + 
			"WHERE state_province = ?";
	
	private static final String SELECT_SPECIFIC_LOCATION_QUERY = 
			"SELECT " + 
			"    country.id_country AS country_id, " + 
			"    country.name AS country, " + 
			"    state_province.id_state_province AS state_province_id, " + 
			"    state_province.name AS state_province, " + 
			"    city.name AS city " + 
			"FROM city " + 
			"INNER JOIN state_province " + 
			"    ON city.state_province = state_province.id_state_province " + 
			"INNER JOIN country " + 
			"    ON state_province.country = country.id_country " + 
			"WHERE city.id_city = ?";
	
	public static Location loadLocation(int cityId) throws SQLException {
		
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_SPECIFIC_LOCATION_QUERY);
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
	
	public static List<Location> loadCountries() throws SQLException {
		// Create prepared statement
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_COUNTRIES_QUERY);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		// Iterate over results
		List<Location> locations = new ArrayList<Location>();
		while (resultSet.next()) {
			// Read values from columns
			int id = resultSet.getInt(1);
			String name = resultSet.getString(2);
			// Instantiate object
			Location location = new Location();
			location.setCountryId(id);
			location.setCountry(name);
			// Add to list
			locations.add(location);
		}
		return locations;
	}
	
	public static List<Location> loadStateProvinces(int countryId) throws SQLException {
		// Create prepared statement
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_STATES_PROVINCES_QUERY);
		// Set parameters
		preparedStatement.setInt(1, countryId);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		// Iterate over results
		List<Location> locations = new ArrayList<Location>();
		while (resultSet.next()) {
			// Read values from columns
			int id = resultSet.getInt(1);
			String name = resultSet.getString(2);
			// Instantiate object
			Location location = new Location();
			location.setStateProvinceId(id);
			location.setStateProvince(name);
			// Add to list
			locations.add(location);
		}
		return locations;
	}
	
	public static List<Location> loadCities(int stateProvinceId) throws SQLException {
		// Create prepared statement
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_CITIES_QUERY);
		// Set parameters
		preparedStatement.setInt(1, stateProvinceId);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		// Iterate over results
		List<Location> locations = new ArrayList<Location>();
		while (resultSet.next()) {
			// Read values from columns
			int id = resultSet.getInt(1);
			String name = resultSet.getString(2);
			// Instantiate object
			Location location = new Location();
			location.setCityId(id);
			location.setCity(name);
			// Add to list
			locations.add(location);
		}
		return locations;
	}
}
