package ui.work_concept;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import character_values.AptitudeConcept;
import character_values.PsychotecnicTestResult;
import character_values.ValueHoldingEnum;
import entities.InformedConsent;
import entities.PostExamAction;
import entities.WorkAptitudeConcept;
import persistence.entityPersisters.PostExamActionPersistence;
import persistence.entityPersisters.WorkAptitudeConceptPersistence;
import ui.generic_bicolumn_selection.GenericSelectionInternalFrame;
import ui.generic_bicolumn_selection.GenericSelectionListModel;
import ui.generic_bicolumn_selection.GenericSelectionListener;

public class WorkConceptInternalFrame extends JInternalFrame implements GenericSelectionListener<PostExamAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1433658523769062513L;

	private static final String ACTION_ACCEPT = "ACTION_ACCEPT";
	
	private static final String ACTION_CANCEL = "ACTION_CANCEL";
	
	private InformedConsent informedConsent;
	
	private WorkAptitudeConcept workAptitudeConcept;
	
	private JTextArea recommendations, observations;
	
	private JComboBox<PsychotecnicTestResult> psychotechnicTest;
	
	private JComboBox<AptitudeConcept> workConcept;
	
	private List<PostExamAction> selectedActions = new ArrayList<>();
	
	private GenericSelectionListModel<PostExamAction> selectionListModel = new GenericSelectionListModel<PostExamAction>();
	
	private ActionListener addPostExamActionActionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent event) {

			String type = event.getActionCommand();
			
			try {

				List<PostExamAction> availableAnomalies = PostExamActionPersistence.loadPostExamActionsByType(type);
			
				GenericSelectionInternalFrame<PostExamAction> selectionScreen =  new GenericSelectionInternalFrame<>(type, availableAnomalies, selectedActions, WorkConceptInternalFrame.this);
				
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
				try {
					saveData();
					dispose();
					JOptionPane.showMessageDialog(getDesktopPane(), "Concepto de aptitud guardado exitosamente");
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(getDesktopPane(), e.getMessage());
				}
				break;

			case ACTION_CANCEL:
				dispose();
			}
		}
	};
	
	private Comparator<PostExamAction> comparator = new Comparator<PostExamAction>() {
		
		@Override
		public int compare(PostExamAction o1, PostExamAction o2) {
			return o1.toString().compareTo(o2.toString());
		}
	};

	public WorkConceptInternalFrame(InformedConsent informedConsent) {
		
		this.informedConsent = informedConsent;
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Concepto de Aptitud Laboral");
		setResizable(false);
		setClosable(true);
		setIconifiable(true);
		
		JPanel contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);
		
		// Initialization of components
		recommendations = new JTextArea();
		recommendations.setColumns(40);
		recommendations.setRows(5);
		//salto de linea automatico
		recommendations.setLineWrap(true);
		
		observations = new JTextArea();
		observations.setColumns(40);
		observations.setRows(5);
		//salto de linea automatico
		observations.setLineWrap(true);
		
		JPanel textAreas = new JPanel(new BorderLayout(10, 10));
		contentPane.add(textAreas, BorderLayout.SOUTH);
		
		JPanel recommendationsPanel = new JPanel(new BorderLayout());
		recommendationsPanel.setBorder(BorderFactory.createTitledBorder("Recomendaciones"));
		textAreas.add(recommendationsPanel, BorderLayout.SOUTH);
		
		JScrollPane scrollRecommendationsText = new JScrollPane(recommendations);
		recommendationsPanel.add(scrollRecommendationsText);
		
		JPanel observationsPanel = new JPanel(new BorderLayout());
		observationsPanel.setBorder(BorderFactory.createTitledBorder("Observaciones"));
		textAreas.add(observationsPanel, BorderLayout.NORTH);
		
		JScrollPane scrollObservations = new JScrollPane(observations);
		observationsPanel.add(scrollObservations);
		
		psychotechnicTest = new JComboBox<> (PsychotecnicTestResult.values()); 
		
		JPanel partialPanels = new JPanel(new FlowLayout());
		
		JPanel psychotechnicPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		partialPanels.add(psychotechnicPanel);
		
		psychotechnicPanel.add(new JLabel("Concepto Prueba Psicotécnica", JLabel.CENTER));
		psychotechnicPanel.add(psychotechnicTest);
		
		try {
			//Conduct Panel (Post Exam Actions)
			JPanel postExamPanel = new JPanel(new FlowLayout());
			postExamPanel.setBorder(BorderFactory.createTitledBorder("Conducta"));
			partialPanels.add(postExamPanel);
			//BUttons panel
			JPanel buttonsPanel = new JPanel(new FlowLayout());
			postExamPanel.add(buttonsPanel);
			
			List<String> actionTypes = PostExamActionPersistence.loadMedicalAnomalyTypes(); 
			
			for (String type : actionTypes) {
				JButton button = new JButton(type);
				button.addActionListener(addPostExamActionActionListener);
				buttonsPanel.add(button);
			}
		
			JList<PostExamAction> actionsList = new JList<>(selectionListModel);
			JScrollPane scrollPostExamList = new JScrollPane(actionsList);
			postExamPanel.add(scrollPostExamList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		contentPane.add(partialPanels, BorderLayout.CENTER);
		
		workConcept = new JComboBox<>(AptitudeConcept.values());

		JPanel workConceptPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		workConceptPanel.setBorder(BorderFactory.createTitledBorder("Examen de Ingreso"));
		workConceptPanel.add(new JLabel("Concepto Examen de Ingreso"));
		workConceptPanel.add(workConcept);
		
		contentPane.add(workConceptPanel, BorderLayout.NORTH);
		
		JButton acceptButton = new JButton("Aceptar");
		acceptButton.setActionCommand(ACTION_ACCEPT);
		acceptButton.addActionListener(actionListener);
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.setActionCommand(ACTION_CANCEL);
		cancelButton.addActionListener(actionListener);
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
		buttonsPanel.add(acceptButton);
		buttonsPanel.add(cancelButton);
		contentPane.add(buttonsPanel, BorderLayout.SOUTH);
		
		pack();
		
		checkForExistingPhysicalExamData();
	}
	
	private void checkForExistingPhysicalExamData() {
		
		if (informedConsent.getWorkAptitudeConcept() != null) {
			workAptitudeConcept = informedConsent.getWorkAptitudeConcept();
			fillFields();
		} else {
			workAptitudeConcept = new WorkAptitudeConcept(informedConsent);
		}
	}
	
	private void fillFields() {
		
		workConcept.setSelectedItem(ValueHoldingEnum.getByValue(AptitudeConcept.values(), workAptitudeConcept.getConcept()));
		psychotechnicTest.setSelectedItem(ValueHoldingEnum.getByValue(PsychotecnicTestResult.values(), workAptitudeConcept.getPsychotechnicTest()));
		selectedActions = workAptitudeConcept.getPostExamActions();
		selectionListModel.setItems(selectedActions);
		if (workAptitudeConcept.getRecommendations() != null) recommendations.setText(workAptitudeConcept.getRecommendations());
		
	}
	
	private void saveData() throws Exception {
		
		readInputValues();
		try {
			WorkAptitudeConceptPersistence.saveWorkAptitudeConcept(workAptitudeConcept);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Ocurrió un error al guardar la información");
		}
		
	}
	
	private void readInputValues() {

		String observations = this.observations.getText().trim();
		if (observations.isEmpty()) observations = null;
		
		String recommendations = this.recommendations.getText().trim();
		if (recommendations.isEmpty()) recommendations = null;
		
		char concept = ((AptitudeConcept) workConcept.getSelectedItem()).getValue();
		char psychotecnicTest = ((PsychotecnicTestResult) psychotechnicTest.getSelectedItem()).getValue();

		workAptitudeConcept.setConceptObservations(observations);
		workAptitudeConcept.setRecommendations(recommendations);
		workAptitudeConcept.setConcept(concept);
		workAptitudeConcept.setPsychotechnicTest(psychotecnicTest);
		workAptitudeConcept.setPostExamActions(selectedActions);
	}

	@Override
	public void onItemsSelected(String type, List<PostExamAction> selectedItems) {

		List<PostExamAction> actionsToRemove = new ArrayList<PostExamAction>();
		for (PostExamAction action : selectedActions) {
			if (action.getType().equals(type))
				actionsToRemove.add(action);
		}
		selectedActions.removeAll(actionsToRemove);
		
		selectedActions.addAll(selectedItems);
		
		selectedActions.sort(comparator);
		
		selectionListModel.setItems(selectedActions);	
	}
	
	

}
