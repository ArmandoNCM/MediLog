package ui.exam;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import character_values.Handedness;
import character_values.ValueHoldingEnum;
import entities.InformedConsent;
import entities.MedicalAnomaly;
import entities.PhysicalCheck;
import persistence.entityPersisters.MedicalAnomalyPersistence;
import persistence.entityPersisters.PhysicalCheckPersistence;
import ui.generic_bicolumn_selection.GenericSelectionInternalFrame;
import ui.generic_bicolumn_selection.GenericSelectionListModel;
import ui.generic_bicolumn_selection.GenericSelectionListener;

public class PhysicalExamInternalFrame extends JInternalFrame implements GenericSelectionListener<MedicalAnomaly> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6590336491802990613L;

	private static final String ACTION_ACCEPT = "ACTION_ACCEPT";
	
	private static final String ACTION_CANCEL = "ACTION_CANCEL";
	
	private InformedConsent informedConsent;
	
	private PhysicalCheck physicalCheck;
	
	private List<MedicalAnomaly> selectedMedicalAnomalies = new ArrayList<MedicalAnomaly>();
	
	private GenericSelectionListModel<MedicalAnomaly> medicalAnomaliesListModel = new GenericSelectionListModel<>();
	
	private JTextField weightTextField, heightTextField, pulseTextField, tempeTextField, respiratoryFTextField, bloodPressureStandingField, bloodPressureLayingDownField;
	
	private JTextArea diagnosticsText, recomendationsText, conclusionsText;
	private JComboBox<Handedness> handednessComboBox;
	
	private Comparator<MedicalAnomaly> comparator = new Comparator<MedicalAnomaly>() {
		
		@Override
		public int compare(MedicalAnomaly o1, MedicalAnomaly o2) {
			return o1.toString().compareTo(o2.toString());
		}
	};
	
	private ActionListener addAnomalyActionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent event) {

			String type = event.getActionCommand();
			
			try {

				List<MedicalAnomaly> availableAnomalies = MedicalAnomalyPersistence.loadMedicalAnomaliesByType(type);
			
				GenericSelectionInternalFrame<MedicalAnomaly> selectionScreen =  new GenericSelectionInternalFrame<>(type, availableAnomalies, selectedMedicalAnomalies, PhysicalExamInternalFrame.this);
				
				selectionScreen.setVisible(true);
				getDesktopPane().add(selectionScreen);
				selectionScreen.toFront();
				
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(getDesktopPane(), "Ha ocurrido un error al cargar las anomalías médicas", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	};
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent event) {

			switch (event.getActionCommand()) {
			
			case ACTION_ACCEPT:
				readInputValues();
				try {
					PhysicalCheckPersistence.savePhysicalCheck(physicalCheck);
					dispose();
					JOptionPane.showMessageDialog(getDesktopPane(), "Los datos han sido guardados con éxito", "Éxito al guardar", JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(getDesktopPane(), "Algo salió mal en el proceso de guardado", "Error al guardar", JOptionPane.ERROR_MESSAGE);
				}
				break;
			case ACTION_CANCEL:
				dispose();
			}
			
		}
	};
	
	public PhysicalExamInternalFrame(InformedConsent informedConsent) {
		
		this.informedConsent = informedConsent;
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Examen físico");
		setResizable(false);
		setClosable(true);
		setIconifiable(true);
		
		// Content pane
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		contentPane.setBorder(BorderFactory.createTitledBorder("Examen Fisico"));

		weightTextField = new JTextField();
		heightTextField = new JTextField();
		pulseTextField = new JTextField();
		tempeTextField = new JTextField();
		respiratoryFTextField = new JTextField();
		bloodPressureStandingField = new JTextField();
		bloodPressureLayingDownField = new JTextField();
		handednessComboBox = new JComboBox<> (Handedness.values());
		
		diagnosticsText = new JTextArea();
        diagnosticsText.setColumns(40);
        diagnosticsText.setRows(3);
	    // Se define el salto de linea automático
        // (cuando llega al final del JTextArea hace un salto de línea)
        diagnosticsText.setLineWrap(true);

        //Recomendations 
		recomendationsText = new JTextArea();
		recomendationsText.setColumns(40);
		recomendationsText.setRows(3);
		recomendationsText.setLineWrap(true);
		
		//Conclusion
		conclusionsText = new JTextArea();
		conclusionsText.setColumns(40);
		conclusionsText.setRows(3);
		conclusionsText.setLineWrap(true);
		
		JScrollPane scrollDiagnosticsText = new JScrollPane(diagnosticsText);
		JScrollPane scrollConclusionsText = new JScrollPane(conclusionsText);
		JScrollPane scrollRecomendationsText = new JScrollPane(recomendationsText);
		
		JPanel dataPhysicalPanel1 = new JPanel(new GridLayout(3,6));
		dataPhysicalPanel1.add(new JLabel("Peso(Kg)"));
		dataPhysicalPanel1.add(weightTextField);
		dataPhysicalPanel1.add(new JLabel("Talla(Cm)"));
		dataPhysicalPanel1.add(heightTextField);
		
		
		dataPhysicalPanel1.add(new JLabel("Pulso / Min"));
		dataPhysicalPanel1.add(pulseTextField);
		dataPhysicalPanel1.add(new JLabel("Temperatura "));
		dataPhysicalPanel1.add(tempeTextField);

		dataPhysicalPanel1.add(new JLabel("F.R / Min"));
		dataPhysicalPanel1.add(respiratoryFTextField);
		dataPhysicalPanel1.add(new JLabel("T.A. Parado (MMHG)"));
		dataPhysicalPanel1.add(bloodPressureStandingField);
		dataPhysicalPanel1.add(new JLabel("T.A. Acostado (MMHG)"));
		dataPhysicalPanel1.add(bloodPressureLayingDownField);
		dataPhysicalPanel1.add(new JLabel("Mano dominante"));
		dataPhysicalPanel1.add(handednessComboBox);

		
		JPanel partiesInputPanel = new JPanel();
		partiesInputPanel.setLayout(new BoxLayout(partiesInputPanel, BoxLayout.Y_AXIS));
		contentPane.add(partiesInputPanel);
		partiesInputPanel.add(dataPhysicalPanel1);
		
		//Anomalies Panel
		try {
			List<String> anomalyTypes = MedicalAnomalyPersistence.loadMedicalAnomalyTypes();
			if (anomalyTypes.size() > 0) {

				JPanel anomalyPanel = new JPanel(new BorderLayout());
				contentPane.add(anomalyPanel);
				anomalyPanel.setBorder(BorderFactory.createTitledBorder("Anomalías médicas"));
				// List
				JList<MedicalAnomaly> anomaliasAgregadas = new JList<>(medicalAnomaliesListModel);
				JScrollPane scrollAnomalyList = new JScrollPane(anomaliasAgregadas);
//				anomaliasAgregadas.set
				anomalyPanel.add(scrollAnomalyList, BorderLayout.CENTER);
				// Buttons
				JPanel panelBotones = new JPanel();
				int columns = 3;
				int rows = anomalyTypes.size() / columns;
				if (anomalyTypes.size() % columns > 0) rows++;
			
				panelBotones.setLayout(new GridLayout(rows,columns));
				for(String anomalyType : anomalyTypes) {
					JButton boton = new JButton(anomalyType);
					boton.addActionListener(addAnomalyActionListener);
					panelBotones.add(boton);
				}
				anomalyPanel.add(panelBotones, BorderLayout.WEST);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//Diagnostics Panel
		JPanel diagnosticsPanel = new JPanel(new GridLayout(1,1));
		contentPane.add(diagnosticsPanel);
		diagnosticsPanel.add(scrollDiagnosticsText);
		diagnosticsPanel.setBorder(BorderFactory.createTitledBorder("Diagnóstico"));

		//Conclusions Panel
		JPanel conclusionPanel = new JPanel(new GridLayout(1,1));
		contentPane.add(conclusionPanel);
		conclusionPanel.add(scrollConclusionsText);
		conclusionPanel.setBorder(BorderFactory.createTitledBorder("Conclusiones"));
		
		JPanel recomendationsPanel = new JPanel(new GridLayout(1, 1));
		contentPane.add(recomendationsPanel);
		recomendationsPanel.add(scrollRecomendationsText);
		recomendationsPanel.setBorder(BorderFactory.createTitledBorder("Recomendaciones"));
		
		JButton acceptButton = new JButton("Aceptar");
		acceptButton.setActionCommand(ACTION_ACCEPT);
		acceptButton.addActionListener(actionListener);
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.setActionCommand(ACTION_CANCEL);
		cancelButton.addActionListener(actionListener);
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
		buttonsPanel.add(acceptButton);
		buttonsPanel.add(cancelButton);
		contentPane.add(buttonsPanel);

		pack();
		
		checkForExistingPhysicalExamData();
	}
	
	private void checkForExistingPhysicalExamData() {

		if (informedConsent.getPhysicalCheck() != null) {
			
			physicalCheck = informedConsent.getPhysicalCheck();
			
			weightTextField.setText(String.valueOf(physicalCheck.getWeightKilograms()));
			heightTextField.setText(String.valueOf(physicalCheck.getHeightCentimeters()));
			pulseTextField.setText(String.valueOf(physicalCheck.getPulseBeatsPerMinute()));
			tempeTextField.setText(String.valueOf(physicalCheck.getBodyTemperature()));
			respiratoryFTextField.setText(String.valueOf(physicalCheck.getRespiratoryFrequencyPerMinute()));
			bloodPressureStandingField.setText(String.valueOf(physicalCheck.getBloodPressureStanding()));
			bloodPressureLayingDownField.setText(String.valueOf(physicalCheck.getBloodPressureLayingDown()));
			diagnosticsText.setText(physicalCheck.getDiagnostics() != null ? String.valueOf(physicalCheck.getDiagnostics()) : "");
			conclusionsText.setText(physicalCheck.getConclusions() != null ? String.valueOf(physicalCheck.getConclusions()) : "");
			recomendationsText.setText(physicalCheck.getRecommendations() != null ? String.valueOf(physicalCheck.getRecommendations()) : "");

			handednessComboBox.setSelectedItem(ValueHoldingEnum.getByValue(Handedness.values(), physicalCheck.getHandedness()));
			
			selectedMedicalAnomalies = physicalCheck.getMedicalAnomalies();
			medicalAnomaliesListModel.setItems(selectedMedicalAnomalies);
			
		} else
			physicalCheck = new PhysicalCheck(informedConsent);
	}
	
	private void readInputValues() {
		
		try {
			float weight = Float.parseFloat(weightTextField.getText());
			short height = Short.parseShort(heightTextField.getText());
			short pulse = Short.parseShort(pulseTextField.getText());
			float temperature = Float.parseFloat(tempeTextField.getText());
			short respiratoryFrequency = Short.parseShort(respiratoryFTextField.getText());
			float bloodPressureStanding = Float.parseFloat(bloodPressureStandingField.getText());
			float bloodPressureLayingDown = Float.parseFloat(bloodPressureLayingDownField.getText());
			String diagnostics = diagnosticsText.getText().trim().length() > 0 ? diagnosticsText.getText() : null;
			String conclusions = conclusionsText.getText().trim().length() > 0 ? conclusionsText.getText() : null;
			String recommendations = recomendationsText.getText().trim().length() > 0 ? recomendationsText.getText() : null;
			char handedness = ((Handedness) handednessComboBox.getSelectedItem()).getValue();
			
			physicalCheck.setWeightKilograms(weight);
			physicalCheck.setHeightCentimeters(height);
			physicalCheck.setPulseBeatsPerMinute(pulse);
			physicalCheck.setBodyTemperature(temperature);
			physicalCheck.setRespiratoryFrequencyPerMinute(respiratoryFrequency);
			physicalCheck.setBloodPressureStanding(bloodPressureStanding);
			physicalCheck.setBloodPressureLayingDown(bloodPressureLayingDown);
			physicalCheck.setDiagnostics(diagnostics);
			physicalCheck.setConclusions(conclusions);
			physicalCheck.setRecommendations(recommendations);
			physicalCheck.setHandedness(handedness);
			
			physicalCheck.setMedicalAnomalies(selectedMedicalAnomalies);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(getDesktopPane(), "Revise los valores ingresados", "Error de formato", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void onItemsSelected(String type, List<MedicalAnomaly> selectedItems) {

		List<MedicalAnomaly> anomaliesToRemove = new ArrayList<MedicalAnomaly>();
		for (MedicalAnomaly anomaly : selectedMedicalAnomalies) {
			if (anomaly.getType().equals(type))
				anomaliesToRemove.add(anomaly);
		}
		selectedMedicalAnomalies.removeAll(anomaliesToRemove);
		
		selectedMedicalAnomalies.addAll(selectedItems);
		
		selectedMedicalAnomalies.sort(comparator);
		
		medicalAnomaliesListModel.setItems(selectedMedicalAnomalies);
	}

}
