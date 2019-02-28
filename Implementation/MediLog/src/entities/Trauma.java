package entities;

import java.util.Date;

public class Trauma {
	
	private int id;
	
	private String traumaNature;
	
	private String sequels;
	
	private Date occurrenceDate;
	
	private Date registeredOn;

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

	public Date getOccurrenceDate() {
		return occurrenceDate;
	}

	public void setOccurrenceDate(Date occurrenceDate) {
		this.occurrenceDate = occurrenceDate;
	}

	public Date getRegisteredOn() {
		return registeredOn;
	}

	public void setRegisteredOn(Date registeredOn) {
		this.registeredOn = registeredOn;
	}

}
