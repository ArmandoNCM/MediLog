package character_values;

public enum CivilStatus implements CharacterValueHoldingEnum {

	NONE_SELECTED((char) 0, "-"), SNGLE('S', "Soltero"), MARRIED('M', "Casado"), FREE_UNION('F', "Unión Libre"), WIDOWED('W', "Viudo"), DIVORCED('D', "Divorciado");
	
	private final char value;
	
	private final String displayName;
	
	private CivilStatus(char value, String displayName) {
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
