package ui.exam;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import entities.MedicalAnomaly;

public class MedicalAnomalyListModel extends AbstractListModel<MedicalAnomaly>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7163868184591964533L;
	
	private final List<MedicalAnomaly> medicalAnomalies = new ArrayList<MedicalAnomaly>();

	public void setAnomalies(List<MedicalAnomaly> newAnomalies) {
		medicalAnomalies.clear();
		medicalAnomalies.addAll(newAnomalies);
		fireContentsChanged(this, 0, medicalAnomalies.size() - 1);
	}
	
	@Override
	public int getSize() {
		return medicalAnomalies.size();
	}
	

	@Override
	public MedicalAnomaly getElementAt(int index) {
		return medicalAnomalies.get(index);
	}

}
