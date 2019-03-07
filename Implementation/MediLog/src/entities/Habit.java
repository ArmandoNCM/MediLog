package entities;

import java.time.LocalDate;

public class Habit {
	
	private int id;
	
	private String description;
	
	private int weeklyHoursIntensity;
	
	private LocalDate registeredOn;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getWeeklyHoursIntensity() {
		return weeklyHoursIntensity;
	}

	public void setWeeklyHoursIntensity(int weeklyHoursIntensity) {
		this.weeklyHoursIntensity = weeklyHoursIntensity;
	}

	public LocalDate getRegisteredOn() {
		return registeredOn;
	}

	public void setRegisteredOn(LocalDate registeredOn) {
		this.registeredOn = registeredOn;
	}

}
