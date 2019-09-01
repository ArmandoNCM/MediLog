package character_values;

public enum IdentificationType implements CharacterValueHoldingEnum {
	
	ID('C', "Cédula"), FOREIGN_ID('F', "Cédula de extranjería"), PASSPORT('P', "Pasaporte"), ID_CARD('T', "Tarjeta de identidad");
	
	private final char value;
	
	private final String displayName;
	
	private IdentificationType(char value, String displayName) {
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
