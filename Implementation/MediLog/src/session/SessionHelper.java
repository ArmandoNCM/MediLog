package session;

import entities.Employee;

public class SessionHelper {
	
	private static final SessionHelper instance = new SessionHelper();

	public static SessionHelper getInstance() {
		return instance;
	}
	
	private Employee employee;
	
	private SessionHelper() {}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
}
