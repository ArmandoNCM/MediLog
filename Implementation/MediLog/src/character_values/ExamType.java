package character_values;

public enum ExamType implements ValueHoldingEnum {
	
	INGRESS('I', "Ingreso"), EGRESS('E', "Egreso"), PERIODICAL('P', "Periódico");
	
	private final char value;
	
	private final String displayName;
	
	private ExamType(char value, String displayName) {
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
