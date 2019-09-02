package character_values;

public interface ValueHoldingEnum {
	
	char getValue();
	
	public static ValueHoldingEnum getByValue(ValueHoldingEnum[] items, char character) {
		if (character == 0)
			return items[0];
		for (ValueHoldingEnum item : items) {
			if (item.getValue() == character)
				return item;
		}
		return null;
	}
	
}
