package character_values;

public enum EmployeeRole implements ValueHoldingEnum {
	
	ADMINISTRATOR((char) 1, "Administrador"), EMPLOYEE((char) 2, "Empleado");

	private final char value;
	
	private final String displayName;
	
	private EmployeeRole(char value, String displayName) {
		this.value = value;
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}
	
	@Override
	public char getValue() {
		return value;
	}
	
}
