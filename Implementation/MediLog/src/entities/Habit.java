package entities;

import java.util.Date;

public class Habit {
	
	private int id;
	
	private String description;
	
	private int weeklyHoursIntensity;
	
	private Date registeredOn;

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

	public Date getRegisteredOn() {
		return registeredOn;
	}

	public void setRegisteredOn(Date registeredOn) {
		this.registeredOn = registeredOn;
	}

}
