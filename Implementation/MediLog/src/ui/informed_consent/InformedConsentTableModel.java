package ui.informed_consent;


import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import character_values.ExamType;
import character_values.ValueHoldingEnum;
import entities.InformedConsent;

public class InformedConsentTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6792925301483258103L;

	public static final int NUMBER_OF_COLUMNS = 6;
	
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
				return consent.getEmployee().getFullName();
			case 2:
				return ValueHoldingEnum.getByValue(ExamType.values(), consent.getCheckType());
			case 3:
				return consent.isWorkInHeights() ? "Aplica" : "No Aplica";
			case 4:
				return consent.getContractingCompany() != null ? consent.getContractingCompany().getName() : "No Aplica";
			case 5:
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
				return "Empleado";
			case 2:
				return "Tipo de Examen";
			case 3:
				return "Trabajo en Alturas";
			case 4:
				return "Empresa";
			case 5:
				return "Fecha";
		}
		
		return "Error";
	}
	
}
