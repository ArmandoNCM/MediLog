package character_values;

public enum Handedness implements ValueHoldingEnum {

	RIGHT_HANDED('R', "Diestro"), LEFT_HANDED('L', "Zurdo"), AMBIDEXTROUS('A', "Ambidiestro");
	
	private final char value;
	
	private final String displayName;
	
	private Handedness(char value, String displayName) {
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
