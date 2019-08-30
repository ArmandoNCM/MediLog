package ui.exam;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import entities.MedicalAnomaly;

public class MedicalAnomalyListModel extends AbstractListModel<MedicalAnomaly>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4356068294673046959L;
	
	private final List<MedicalAnomaly> medicalAnomalies = new ArrayList<MedicalAnomaly>();
	
	public List<MedicalAnomaly> getAnomalies() {
		return medicalAnomalies;
	}

	public void setAnomalies(List<MedicalAnomaly> newAnomalies) {
		medicalAnomalies.clear();
		medicalAnomalies.addAll(newAnomalies);
		fireContentsChanged(this, 0, medicalAnomalies.size() - 1);
	}
	
	public void addAnomaly(MedicalAnomaly newAnomaly) {
		medicalAnomalies.add(newAnomaly);
		int lastIndex = medicalAnomalies.size() - 1;
		fireIntervalAdded(this, lastIndex, lastIndex);
	}
	
	public void removeAnomaly(MedicalAnomaly removedAnomaly) {
		int indexToRemove = medicalAnomalies.indexOf(removedAnomaly);
		medicalAnomalies.remove(indexToRemove);
		fireIntervalRemoved(this, indexToRemove, indexToRemove);
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
