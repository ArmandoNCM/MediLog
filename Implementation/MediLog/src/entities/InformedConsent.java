package entities;

import java.time.LocalDate;
import java.util.List;

public class InformedConsent {

	private int id;
	
	private String clientId;
	
	private Client client;
	
	private String employeeId;
	
	private Employee employee;
	
	private char checkType;
	
	private String contractingCompanyId;
	
	private Company contractingCompany;
	
	private boolean workInHeights;
	
	private LocalDate date;
	
	private List<ProfessionalRisk> professionalRisks;
	
	private List<LaboratoryExam> laboratoryExams;
	
	private PhysicalCheck physicalCheck;
	
	private WorkAptitudeConcept workAptitudeConcept;
	
	public InformedConsent(Client client) {
		this.client = client;
		clientId = client.getId();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public Client getClient() {
		return client;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
		if (employee != null)
			employeeId = employee.getId();
	}

	public char getCheckType() {
		return checkType;
	}

	public void setCheckType(char checkType) {
		this.checkType = checkType;
	}

	public String getContractingCompanyId() {
		return contractingCompanyId;
	}

	public Company getContractingCompany() {
		return contractingCompany;
	}

	public void setContractingCompany(Company contractingCompany) {
		this.contractingCompany = contractingCompany;
		if (contractingCompany != null)
			contractingCompanyId = contractingCompany.getId();
	}

	public boolean isWorkInHeights() {
		return workInHeights;
	}

	public void setWorkInHeights(boolean workInHeights) {
		this.workInHeights = workInHeights;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<ProfessionalRisk> getProfessionalRisks() {
		return professionalRisks;
	}

	public void setProfessionalRisks(List<ProfessionalRisk> professionalRisks) {
		this.professionalRisks = professionalRisks;
	}

	public List<LaboratoryExam> getLaboratoryExams() {
		return laboratoryExams;
	}

	public void setLaboratoryExams(List<LaboratoryExam> laboratoryExams) {
		this.laboratoryExams = laboratoryExams;
	}

	public PhysicalCheck getPhysicalCheck() {
		return physicalCheck;
	}
	
	void setPhysicalCheck(PhysicalCheck physicalCheck) {
		this.physicalCheck = physicalCheck;
	}

	public WorkAptitudeConcept getWorkAptitudeConcept() {
		return workAptitudeConcept;
	}
	
	void setWorkAptitudeConcept(WorkAptitudeConcept workAptitudeConcept) {
		this.workAptitudeConcept = workAptitudeConcept;
	}
	
}
