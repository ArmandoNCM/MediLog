package character_values;

public enum SocialLevel implements ValueHoldingEnum {

	NONE_SELECTED((char) 0), ONE((char) 1), TWO((char) 2), THREE((char) 3), FOUR((char) 4), FIVE((char) 5), SIX((char) 6);
	
	private final char value;
	
	private SocialLevel(char value) {
		this.value = value;
	}

	@Override
	public String toString() {
		if (value > 0)
			return String.valueOf((byte) value);
		return "-";
	}
	
	@Override
	public char getValue() {
		return value;
	}
}
