package ui.work_concept;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javax.swing.JTextField;

import character_values.AptitudeConcept;
import character_values.PsychotecnicTestResult;
import entities.MedicalAnomaly;
import entities.PostExamAction;
import persistence.entityPersisters.MedicalAnomalyPersistence;
import persistence.entityPersisters.PostExamActionPersistence;
import ui.exam.PhysicalExamInternalFrame;
import ui.generic_bicolumn_selection.GenericSelectionInternalFrame;
import ui.generic_bicolumn_selection.GenericSelectionListener;

public class WorkConceptInternalFrame extends JInternalFrame implements GenericSelectionListener<PostExamAction> {
	
	private static final long serialVersionUID = 1L;

	private JTextArea recommendations;
	
	private JComboBox<PsychotecnicTestResult> psychotechnicTest;
	
	private JComboBox<AptitudeConcept> workConcept;
	
	private List<PostExamAction> selectedMedicalAnomalies;
	
	private JTextField observations;
	
	JScrollPane scrollRecommendationsText, scrollPostExamList;
	
	private ActionListener addPostExamActionActionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent event) {

			String type = event.getActionCommand();
			
			try {

				List<PostExamAction> availableAnomalies = PostExamActionPersistence.loadPostExamActionsByType(type);
			
				GenericSelectionInternalFrame<PostExamAction> selectionScreen =  new GenericSelectionInternalFrame<>(type, availableAnomalies, selectedMedicalAnomalies, WorkConceptInternalFrame.this);
				
				selectionScreen.setVisible(true);
				getDesktopPane().add(selectionScreen);
				selectionScreen.toFront();
				
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(getDesktopPane(), "Ha ocurrido un error al cargar las anomalías médicas", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	};
	

	public WorkConceptInternalFrame() {
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
		recommendations.setRows(9);
		//salto de linea automatico
		recommendations.setLineWrap(true);
		scrollRecommendationsText = new JScrollPane(recommendations);
		

		//
		psychotechnicTest = new JComboBox<> (PsychotecnicTestResult.values()); 
		JList postExamList = new JList();
		scrollPostExamList = new JScrollPane(postExamList);

		
		//psychotechnicPanel
		JPanel psychotechnicPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		psychotechnicPanel.add(new JLabel("Concepto Prueba Psicotécnica", JLabel.CENTER));
		psychotechnicPanel.add(psychotechnicTest);
		
		//Conduct Panel (Post Exam Actions)
		JPanel postExamPanel = new JPanel(new FlowLayout());
		postExamPanel.setBorder(BorderFactory.createTitledBorder("Conducta"));
		
		//BUttons panel
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		List<PostExamAction> conductTypes = new ArrayList();
			
		try {
			conductTypes = PostExamActionPersistence.loadPostExamActions();
			buttonsPanel.setLayout(new GridLayout(conductTypes.size(),1));
			for(int x=0; x<=conductTypes.size()-1; x++) {
				JButton button = new JButton(conductTypes.get(x).getType());
				buttonsPanel.add(button);
			}
			postExamPanel.add(buttonsPanel);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		postExamPanel.add(scrollPostExamList);
		
		JPanel recommendationsPanel = new JPanel(new BorderLayout());
		recommendationsPanel.setBorder(BorderFactory.createTitledBorder("Recomendaciónes específicas"));
		recommendationsPanel.add(scrollRecommendationsText);
		
		
		JPanel partialPanels = new JPanel(new FlowLayout());
		partialPanels.add(psychotechnicPanel);
		partialPanels.add(postExamPanel);
		
		contentPane.add(recommendationsPanel, BorderLayout.SOUTH);
		contentPane.add(partialPanels, BorderLayout.CENTER);
		
		workConcept = new JComboBox<>(AptitudeConcept.values());
		observations = new JTextField();


		JPanel workConceptPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		workConceptPanel.setBorder(BorderFactory.createTitledBorder("Examen de Ingreso"));
		workConceptPanel.add(new JLabel("Concepto Examen de Ingreso"));
		workConceptPanel.add(workConcept);
		
		
		contentPane.add(workConceptPanel, BorderLayout.NORTH);
		
		pack();
	}

	@Override
	public void onItemsSelected(String type, List<PostExamAction> selectedItems) {
		// TODO Auto-generated method stub
		
	}

}
