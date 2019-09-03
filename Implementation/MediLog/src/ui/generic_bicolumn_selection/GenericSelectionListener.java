package ui.generic_bicolumn_selection;

import java.util.List;

public interface GenericSelectionListener <T> {
	
	void onItemsSelected(String type, List<T> selectedItems);
}