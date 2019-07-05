package entities;

import java.util.List;

public class PhysicalCheck {
	
	private InformedConsent informedConsent;
	
	private int id;
	
	private String employeeId;
	
	private Employee employee;
	
	private float weightKilograms;
	
	private short heightCentimeters;
	
	private short pulseBeatsPerMinute;
	
	private short respiratoryFrequencyPerMinute;
	
	private float bodyTemperature;
	
	private float bloodPressureStanding;
	
	private float bloodPressureLayingDown;
	
	private char handedness;
	
	private String diagnostics;
	
	private String conclusions;
	
	private String recommendations;
	
	private List<MedicalAnomaly> medicalAnomalies;
	
	public PhysicalCheck(InformedConsent informedConsent) {
		id = informedConsent.getId();
		this.informedConsent = informedConsent;
		informedConsent.setPhysicalCheck(this);
	}

	public InformedConsent getInformedConsent() {
		return informedConsent;
	}

	public int getId() {
		return id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public float getWeightKilograms() {
		return weightKilograms;
	}

	public void setWeightKilograms(float weightKilograms) {
		this.weightKilograms = weightKilograms;
	}

	public short getHeightCentimeters() {
		return heightCentimeters;
	}

	public void setHeightCentimeters(short heightCentimeters) {
		this.heightCentimeters = heightCentimeters;
	}

	public short getPulseBeatsPerMinute() {
		return pulseBeatsPerMinute;
	}

	public void setPulseBeatsPerMinute(short pulseBeatsPerMinute) {
		this.pulseBeatsPerMinute = pulseBeatsPerMinute;
	}

	public short getRespiratoryFrequencyPerMinute() {
		return respiratoryFrequencyPerMinute;
	}

	public void setRespiratoryFrequencyPerMinute(short respiratoryFrequencyPerMinute) {
		this.respiratoryFrequencyPerMinute = respiratoryFrequencyPerMinute;
	}

	public float getBodyTemperature() {
		return bodyTemperature;
	}

	public void setBodyTemperature(float bodyTemperature) {
		this.bodyTemperature = bodyTemperature;
	}

	public float getBloodPressureStanding() {
		return bloodPressureStanding;
	}

	public void setBloodPressureStanding(float bloodPressureStanding) {
		this.bloodPressureStanding = bloodPressureStanding;
	}

	public float getBloodPressureLayingDown() {
		return bloodPressureLayingDown;
	}

	public void setBloodPressureLayingDown(float bloodPressureLayingDown) {
		this.bloodPressureLayingDown = bloodPressureLayingDown;
	}

	public char getHandedness() {
		return handedness;
	}

	public void setHandedness(char handedness) {
		this.handedness = handedness;
	}

	public String getDiagnostics() {
		return diagnostics;
	}

	public void setDiagnostics(String diagnostics) {
		this.diagnostics = diagnostics;
	}

	public String getConclusions() {
		return conclusions;
	}

	public void setConclusions(String conclusions) {
		this.conclusions = conclusions;
	}
	
	public String getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(String recommendations) {
		this.recommendations = recommendations;
	}
	
	public List<MedicalAnomaly> getMedicalAnomalies() {
		return medicalAnomalies;
	}

	public void setMedicalAnomalies(List<MedicalAnomaly> medicalAnomalies) {
		this.medicalAnomalies = medicalAnomalies;
	}

}
