package entities;

import java.time.LocalDate;

public abstract class Person {

	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private LocalDate birthDate;
	
	private int idExpeditionCityId;
	
	private Location idExpeditionCity;
	
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

	public int getIdExpeditionCityId() {
		return idExpeditionCityId;
	}

	public void setIdExpeditionCityId(int expeditionCityId) {
		this.idExpeditionCityId = expeditionCityId;
	}

	public Location getIdExpeditionCity() {
		return idExpeditionCity;
	}

	public void setIdExpeditionCity(Location expeditionCity) {
		this.idExpeditionCity = expeditionCity;
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
