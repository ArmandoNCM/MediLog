package character_values;

public interface CharacterValueHoldingEnum {
	
	char getValue();
	
	public static CharacterValueHoldingEnum getByValue(CharacterValueHoldingEnum[] items, char character) {
		for (CharacterValueHoldingEnum item : items) {
			if (item.getValue() == character)
				return item;
		}
		return null;
	}
	
}
