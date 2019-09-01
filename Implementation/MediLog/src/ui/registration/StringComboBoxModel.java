package ui.registration;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class StringComboBoxModel extends AbstractListModel<String> implements ComboBoxModel<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9458466973405262L;

	private String selectedItem;
	
	private final List<String> list = new ArrayList<String>();
	
	public void setList(List<String> newList) {
		list.clear();
		list.addAll(newList);
		fireContentsChanged(this, 0, list.size() - 1);
	}

	@Override
	public int getSize() {
		return list.size();
	}

	@Override
	public String getElementAt(int index) {
		return list.get(index);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selectedItem = (String) anItem;
	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

}
