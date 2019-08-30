package ui.exam;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import entities.MedicalAnomaly;
import persistence.entityPersisters.MedicalAnomalyPersistence;

public class AddMedicalAnomaliesInternalFrame extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4379239213736561500L;
	
	private static final String ACTION_ADD = "ACTION_ADD";
	private static final String ACTION_REMOVE = "ACTION_REMOVE";
	private static final String ACTION_ACCEPT = "ACTION_ACCEPT";
	private static final String ACTION_CANCEL = "ACTION_CANCEL";
	
	private String medicalAnomalyType;
	
	private MedicalAnomalyListModel availableAnomaliesListModel, selectedAnomaliesListModel;
	
	private List<MedicalAnomaly> availableAnomalies, selectedAnomalies;

	private JList<MedicalAnomaly> availableAnomaliesList, selectedAnomaliesList;
	
	private MedicalAnomaliesSelectionListener medicalAnomaliesSelectionListener;
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			MedicalAnomaly selection;
			
			switch (event.getActionCommand()) {
			
			case ACTION_ADD:
				selection = availableAnomaliesList.getSelectedValue();
				if (selection != null) {
					selectedAnomaliesListModel.addAnomaly(selection);
					availableAnomaliesListModel.removeAnomaly(selection);
				}
				break;
				
			case ACTION_REMOVE:
				selection = selectedAnomaliesList.getSelectedValue();
				if (selection != null) {
					availableAnomaliesListModel.addAnomaly(selection);
					selectedAnomaliesListModel.removeAnomaly(selection);
				}
				break;
				
			case ACTION_ACCEPT:
				dispose();
				medicalAnomaliesSelectionListener.onMedicalAnomaliesSelected(medicalAnomalyType, selectedAnomaliesListModel.getAnomalies());
				break;
				
			case ACTION_CANCEL:
				dispose();
			}
		}
	};
	

	public AddMedicalAnomaliesInternalFrame(String medicalAnomalyType, List<MedicalAnomaly> selectedAnomalies, MedicalAnomaliesSelectionListener medicalAnomaliesSelectionListener) {
		
		this.medicalAnomaliesSelectionListener = medicalAnomaliesSelectionListener;
		this.medicalAnomalyType = medicalAnomalyType;
		this.selectedAnomalies = new ArrayList<MedicalAnomaly>();
		
		try {
			configureListModels(selectedAnomalies);
		} catch (SQLException e) {
			e.printStackTrace();
			dispose();
			JOptionPane.showMessageDialog(getDesktopPane(), "Ha ocurrido un error al cargar las anomalías médicas", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Examen físico");
		setResizable(false);
		setClosable(true);
		setIconifiable(true);
		
		JPanel contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);
		
		JPanel centerPanel = new JPanel(new FlowLayout());
		contentPane.add(centerPanel);
		
		availableAnomaliesList = new JList<MedicalAnomaly>(availableAnomaliesListModel);
		availableAnomaliesList.setVisibleRowCount(10);
		availableAnomaliesList.setFixedCellWidth(300);
		JScrollPane availableScrollPane = new JScrollPane(availableAnomaliesList);
		
		selectedAnomaliesList = new JList<MedicalAnomaly>(selectedAnomaliesListModel);
		selectedAnomaliesList.setVisibleRowCount(10);
		selectedAnomaliesList.setFixedCellWidth(300);
		JScrollPane selectedScrollPane = new JScrollPane(selectedAnomaliesList);
		
		JPanel centerButtonsPanel = new JPanel(new GridLayout(2,1));
		centerPanel.add(availableScrollPane);
		centerPanel.add(centerButtonsPanel);
		centerPanel.add(selectedScrollPane);
		
		JButton addButton = new JButton(">>");
		addButton.setActionCommand(ACTION_ADD);
		addButton.addActionListener(actionListener);
		centerButtonsPanel.add(addButton);
		
		JButton removeButton = new JButton("<<");
		removeButton.setActionCommand(ACTION_REMOVE);
		removeButton.addActionListener(actionListener);
		centerButtonsPanel.add(removeButton);
		
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		
		JButton acceptButton = new JButton("Aceptar");
		acceptButton.setActionCommand(ACTION_ACCEPT);
		acceptButton.addActionListener(actionListener);
		
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.setActionCommand(ACTION_CANCEL);
		cancelButton.addActionListener(actionListener);
		
		bottomPanel.add(acceptButton);
		bottomPanel.add(cancelButton);
		
		pack();
		
	}
	
	private void configureListModels(List<MedicalAnomaly> generalSelectedAnomalies) throws SQLException {
		
		for (MedicalAnomaly anomaly : generalSelectedAnomalies) {
			if (anomaly.getType().equals(medicalAnomalyType))
				selectedAnomalies.add(anomaly);
		}
		
		availableAnomalies = MedicalAnomalyPersistence.loadMedicalAnomaliesByType(medicalAnomalyType);
		
		List<MedicalAnomaly> duplicateAnomalies = new ArrayList<MedicalAnomaly>();
		for (MedicalAnomaly selectedAnomaly : selectedAnomalies) 
			for (MedicalAnomaly availableAnomaly : availableAnomalies) 
				if (selectedAnomaly.getId() == availableAnomaly.getId())
					duplicateAnomalies.add(availableAnomaly);
		
		availableAnomalies.removeAll(duplicateAnomalies);
		
		availableAnomaliesListModel = new MedicalAnomalyListModel();
		availableAnomaliesListModel.setAnomalies(availableAnomalies);
		
		selectedAnomaliesListModel = new MedicalAnomalyListModel();
		selectedAnomaliesListModel.setAnomalies(selectedAnomalies);
		
		
	}
	
	public interface MedicalAnomaliesSelectionListener {
		
		void onMedicalAnomaliesSelected(String medicalAnomalyType, List<MedicalAnomaly> selectedAnomalies);
	}

}
