package entities;

import java.util.List;

public class Company {

	private String id;
	
	private String name;
	
	private List<ContactInformation> contactInformationEntries;

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
