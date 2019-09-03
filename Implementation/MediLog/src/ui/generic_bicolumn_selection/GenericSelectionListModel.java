package ui.generic_bicolumn_selection;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

public class GenericSelectionListModel <T> extends AbstractListModel<T>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1970151690320791369L;
	
	private final List<T> items = new ArrayList<T>();
	
	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> newItems) {
		items.clear();
		items.addAll(newItems);
		fireContentsChanged(this, 0, items.size() - 1);
	}
	
	public void addItem(T newItem) {
		items.add(newItem);
		int lastIndex = items.size() - 1;
		fireIntervalAdded(this, lastIndex, lastIndex);
	}
	
	public void removeItem(T removedItem) {
		int indexToRemove = items.indexOf(removedItem);
		items.remove(indexToRemove);
		fireIntervalRemoved(this, indexToRemove, indexToRemove);
	}
	
	@Override
	public int getSize() {
		return items.size();
	}

	@Override
	public T getElementAt(int index) {
		return items.get(index);
	}

}
