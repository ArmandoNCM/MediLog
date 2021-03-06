package character_values;

public enum SexualGenderType implements ValueHoldingEnum {
	
	MALE('M', "Masculino"), FEMALE('F', "Femenino");

	private final char value;
	
	private final String displayName;
	
	private SexualGenderType(char value, String displayName) {
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
