package entities;

import java.util.List;

public class Client extends Person {
	
	private char academicLevel;
	
	private byte socialLevel;
	
	private char civilStatus;
	
	private int cityId;
	
	private Location city;
	
	private String address;
	
	private String phone;
	
	private List<Trauma> traumaRecords;
	
	private List<Habit> habits;
	
	private List<MedicalCase> medicalCaseRecords;
	
	private List<OcupationalRecord> ocupationalRecords;

	public char getAcademicLevel() {
		return academicLevel;
	}

	public void setAcademicLevel(char academicLevel) {
		this.academicLevel = academicLevel;
	}

	public byte getSocialLevel() {
		return socialLevel;
	}

	public void setSocialLevel(byte socialLevel) {
		this.socialLevel = socialLevel;
	}

	public char getCivilStatus() {
		return civilStatus;
	}

	public void setCivilStatus(char civilStatus) {
		this.civilStatus = civilStatus;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public Location getCity() {
		return city;
	}

	public void setCity(Location city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Trauma> getTraumaRecords() {
		return traumaRecords;
	}

	public void setTraumaRecords(List<Trauma> traumaRecords) {
		this.traumaRecords = traumaRecords;
	}

	public List<Habit> getHabits() {
		return habits;
	}

	public void setHabits(List<Habit> habits) {
		this.habits = habits;
	}

	public List<MedicalCase> getMedicalCaseRecords() {
		return medicalCaseRecords;
	}

	public void setMedicalCaseRecords(List<MedicalCase> medicalCaseRecords) {
		this.medicalCaseRecords = medicalCaseRecords;
	}

	public List<OcupationalRecord> getOcupationalRecords() {
		return ocupationalRecords;
	}

	public void setOcupationalRecords(List<OcupationalRecord> ocupationalRecords) {
		this.ocupationalRecords = ocupationalRecords;
	}

}
