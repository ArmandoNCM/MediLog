package character_values;

public enum AptitudeConcept implements ValueHoldingEnum {

	ABLE('A', "Apto"), UNABLE('U', "No Apto");
	
	private final char value;
	
	private final String displayName;
	
	private AptitudeConcept(char value, String displayName) {
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
