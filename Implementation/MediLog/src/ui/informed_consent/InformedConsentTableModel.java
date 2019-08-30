package ui.informed_consent;


import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entities.InformedConsent;

public class InformedConsentTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6792925301483258103L;

	private static final int NUMBER_OF_COLUMNS = 5;
	
	private final List<InformedConsent> items = new ArrayList<InformedConsent>();
	
	public void setItems(List<InformedConsent> newItems) {
		items.clear();
		items.addAll(newItems);
	}
	
	public InformedConsent getItem(int row) {
		return items.get(row);
	}

	@Override
	public int getRowCount() {
		return items.size();
	}

	@Override
	public int getColumnCount() {
		return NUMBER_OF_COLUMNS;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		InformedConsent consent = items.get(rowIndex);
		
		switch (columnIndex) {
		
			case 0:
				return consent.getId();
			case 1:
				return consent.getClientId();
			case 2:
				return consent.getClient().getFirstName();
			case 3:
				return consent.getClient().getLastName();
			case 4:
				return consent.getDate();
			
		}
		
		return "Error";
	}

	@Override
	public String getColumnName(int column) {
		
		switch (column) {
			case 0:
				return "ID";
			case 1:
				return "Cliente";
			case 2:
				return "Nombre";
			case 3:
				return "Apellido";
			case 4:
				return "Fecha";
		}
		
		return "Error";
	}
	
}
