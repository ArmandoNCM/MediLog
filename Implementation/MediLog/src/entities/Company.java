package entities;

import java.util.List;

import contracts.StringIdentifiable;

public class Company implements StringIdentifiable {

	private String id;
	
	private String name;
	
	private List<ContactInformation> contactInformationEntries;

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ContactInformation> getContactInformationEntries() {
		return contactInformationEntries;
	}

	public void setContactInformationEntries(List<ContactInformation> contactInformationEntries) {
		this.contactInformationEntries = contactInformationEntries;
	}
	
}
