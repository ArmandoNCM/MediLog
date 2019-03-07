package entities;

import java.time.LocalDate;

public class Trauma {
	
	private int id;
	
	private String traumaNature;
	
	private String sequels;
	
	private LocalDate occurrenceDate;
	
	private LocalDate registeredOn;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTraumaNature() {
		return traumaNature;
	}

	public void setTraumaNature(String traumaNature) {
		this.traumaNature = traumaNature;
	}

	public String getSequels() {
		return sequels;
	}

	public void setSequels(String sequels) {
		this.sequels = sequels;
	}

	public LocalDate getOccurrenceDate() {
		return occurrenceDate;
	}

	public void setOccurrenceDate(LocalDate occurrenceDate) {
		this.occurrenceDate = occurrenceDate;
	}

	public LocalDate getRegisteredOn() {
		return registeredOn;
	}

	public void setRegisteredOn(LocalDate registeredOn) {
		this.registeredOn = registeredOn;
	}

}
