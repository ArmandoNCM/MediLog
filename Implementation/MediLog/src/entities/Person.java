package entities;

import java.time.LocalDate;

public abstract class Person {

	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private LocalDate birthDate;
	
	private int expeditionCityId;
	
	private Location expeditionCity;
	
	private char identificationType;
	
	private char gender;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public int getExpeditionCityId() {
		return expeditionCityId;
	}

	public void setExpeditionCityId(int expeditionCityId) {
		this.expeditionCityId = expeditionCityId;
	}

	public Location getExpeditionCity() {
		return expeditionCity;
	}

	public void setExpeditionCity(Location expeditionCity) {
		this.expeditionCity = expeditionCity;
	}

	public char getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(char identificationType) {
		this.identificationType = identificationType;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}
}
