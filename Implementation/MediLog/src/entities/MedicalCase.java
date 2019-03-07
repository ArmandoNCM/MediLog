package entities;

import java.time.LocalDate;

public class MedicalCase {

	private int id;
	
	private String name;
	
	private char backgroundType;
	
	private LocalDate registeredOn;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getBackgroundType() {
		return backgroundType;
	}

	public void setBackgroundType(char backgroundType) {
		this.backgroundType = backgroundType;
	}

	public LocalDate getRegisteredOn() {
		return registeredOn;
	}

	public void setRegisteredOn(LocalDate registeredOn) {
		this.registeredOn = registeredOn;
	}
	
}
