package entities;

import java.util.List;

public class WorkAptitudeConcept {
	
	private InformedConsent informedConsent;
	
	private int id;
	
	private String employeeId;
	
	private Employee employee;
	
	private char workAptitude;
	
	private char workInHeightsAptitude;
	
	private char concept;
	
	private String conceptObservations;
	
	private char psychotechnicTest;
	
	private String recommendations;
	
	private List<PostExamAction> postExamActions;
	
	public WorkAptitudeConcept(InformedConsent informedConsent) {
		id = informedConsent.getId();
		this.informedConsent = informedConsent;
		informedConsent.setWorkAptitudeConcept(this);
	}

	public InformedConsent getInformedConsent() {
		return informedConsent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public char getWorkAptitude() {
		return workAptitude;
	}

	public void setWorkAptitude(char workAptitude) {
		this.workAptitude = workAptitude;
	}

	public char getWorkInHeightsAptitude() {
		return workInHeightsAptitude;
	}

	public void setWorkInHeightsAptitude(char workInHeightsAptitude) {
		this.workInHeightsAptitude = workInHeightsAptitude;
	}

	public char getConcept() {
		return concept;
	}

	public void setConcept(char concept) {
		this.concept = concept;
	}

	public String getConceptObservations() {
		return conceptObservations;
	}

	public void setConceptObservations(String conceptObservations) {
		this.conceptObservations = conceptObservations;
	}

	public char getPsychotechnicTest() {
		return psychotechnicTest;
	}

	public void setPsychotechnicTest(char psychotechnicTest) {
		this.psychotechnicTest = psychotechnicTest;
	}

	public String getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(String recommendations) {
		this.recommendations = recommendations;
	}

	public List<PostExamAction> getPostExamActions() {
		return postExamActions;
	}

	public void setPostExamActions(List<PostExamAction> postExamActions) {
		this.postExamActions = postExamActions;
	}

}
