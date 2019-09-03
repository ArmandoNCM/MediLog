package entities;

import contracts.IntegerIdentifiable;
import contracts.TypeClassified;

public class MedicalAnomaly implements IntegerIdentifiable, TypeClassified{
	
	private int id;
	
	private String name;
	
	private String type;
	
	private String observations;

	@Override
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
	
	@Override
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", getType(), getName());
	}

}
