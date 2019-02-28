package entities;

import java.util.List;

public class LaboratoryExam {

	private int id;
	
	private String name;
	
	private List<LaboratoryExamAttachment> attachments;

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

	public List<LaboratoryExamAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<LaboratoryExamAttachment> attachments) {
		this.attachments = attachments;
	}
	
}
