package character_values;

public enum AcademicLevel implements ValueHoldingEnum {
	
	NONE_SELECTED((char) 0, "-"), PRIMARY('B', "Básica"), SECONDARY('S', "Secundaria"), TECHNICAL('T', "Técnica"), PROFESSIONAL('P', "Profesional");
	
	private final String displayName;
	
	private final char value;
	
	private AcademicLevel(char value, String displayName) {
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