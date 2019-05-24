package persistence.entityPersisters;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entities.Habit;
import persistence.Database;

public class HabitPersistence {
	
	private static final String SELECT_CLIENT_HABITS_QUERY = 
			"SELECT " + 
			"    habit.id_habit, " + 
			"    habit.description, " + 
			"    client_habit.weekly_hours_intensity, " + 
			"    client_habit.registered_on " + 
			"FROM habit " + 
			"JOIN client_habit " + 
			"    ON habit.id_habit = client_habit.habit " + 
			"WHERE client_habit.client = ? " +
			"ORDER BY client_habit.registered_on ASC";
	
	private static final String SELECT_HABITS_QUERY = 
			"SELECT " + 
			"    id_habit, " + 
			"    description " + 
			"FROM habit " +
			"ORDER BY description ASC";
	
	private static final String INSERT_HABIT_QUERY =
			"INSERT INTO habit " + 
			"    ( " + 
			"        description " + 
			"    ) " + 
			"VALUES (?)";
	
	private static final String INSERT_CLIENT_HABIT_QUERY = 
			"INSERT INTO client_habit " + 
			"    ( " + 
			"        client, " + 
			"        habit, " + 
			"        weekly_hours_intensity, " + 
			"        registered_on " + 
			"    ) " + 
			"VALUES (?, ?, ?, ?)";
	
	public static List<Habit> loadClientHabits(String clientId) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(SELECT_CLIENT_HABITS_QUERY);
		// Set query parameters
		preparedStatement.setString(1, clientId);
		// Execute query
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<Habit> habits = new ArrayList<Habit>();
		// Iterate over the result set
		while (resultSet.next()) {
			// Retrieve column data
			int habitId = resultSet.getInt(1);
			String description = resultSet.getString(2);
			int weeklyHoursIntensity = resultSet.getInt(3);
			LocalDate registeredOn = resultSet.getDate(4).toLocalDate();
			// Instantiate Habit object
			Habit habit = new Habit();
			habit.setId(habitId);
			habit.setDescription(description);
			habit.setWeeklyHoursIntensity(weeklyHoursIntensity);
			habit.setRegisteredOn(registeredOn);
			// Add to object to list
			habits.add(habit);
		}
		return habits;
	}
	
	public static List<Habit> loadHabitTypes() throws SQLException {
		// Create a simple statement and run query
		Statement statement = Database.getInstance().getConnection().createStatement();
		ResultSet resultSet = statement.executeQuery(SELECT_HABITS_QUERY);
		List<Habit> habits = new ArrayList<Habit>();
		// Iterate over the result set
		while(resultSet.next()) {
			// Retrieve column data
			int habitId = resultSet.getInt(1);
			String description = resultSet.getString(2);
			// Instantiate Habit object
			Habit habit = new Habit();
			habit.setId(habitId);
			habit.setDescription(description);
			// Add object to list
			habits.add(habit);
		}
		return habits;
	}
	
	public static boolean saveHabitType(String habitDescription) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_HABIT_QUERY);
		// Set query parameters
		preparedStatement.setString(1, habitDescription);
		// Execute query
		return preparedStatement.executeUpdate() == 1;
	}
	
	public static boolean saveClientHabit(String clientId, Habit habit) throws SQLException {
		// Create prepared statement with parameterized query
		PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(INSERT_CLIENT_HABIT_QUERY);
		// Set query parameters
		preparedStatement.setString(1, clientId);
		preparedStatement.setInt(2, habit.getId());
		preparedStatement.setInt(3, habit.getWeeklyHoursIntensity());
		preparedStatement.setDate(4, Date.valueOf(habit.getRegisteredOn()));
		// Execute query
		return preparedStatement.executeUpdate() == 1;
	}

}
