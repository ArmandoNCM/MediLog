package character_values;

public enum PsychotecnicTestResult implements ValueHoldingEnum {

	POSITIVE('P', "Positiva"), NEGATIVE('N', "Negativa");
	
	private final char value;
	
	private final String displayName;
	
	private PsychotecnicTestResult(char value, String displayName) {
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
