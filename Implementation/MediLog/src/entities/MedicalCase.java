package entities;

import java.util.Date;

public class MedicalCase {

	private int id;
	
	private String name;
	
	private char backgroundType;
	
	private Date registeredOn;

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

	public Date getRegisteredOn() {
		return registeredOn;
	}

	public void setRegisteredOn(Date registeredOn) {
		this.registeredOn = registeredOn;
	}
	
}
