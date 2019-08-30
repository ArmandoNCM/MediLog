package ui.work_concept;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import entities.PostExamAction;
import persistence.entityPersisters.PostExamActionPersistence;

public abstract class AbstractWorkConcept extends JInternalFrame{
	
	private static final long serialVersionUID = 1L;

	JPanel contentPane;
	
	JTextArea recommendations;
	
	JComboBox<Character> psychotechnicTest;
	
	JScrollPane scrollRecommendationsText, scrollPostExamList;

	public AbstractWorkConcept() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Concepto de Aptitud Laboral");
		setResizable(false);
		setClosable(true);
		setIconifiable(true);
		
		contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);
		
		// Initialization of components
		recommendations = new JTextArea();
		recommendations.setColumns(40);
		recommendations.setRows(9);
		//salto de linea automatico
		recommendations.setLineWrap(true);
		scrollRecommendationsText = new JScrollPane(recommendations);
		

		//
		psychotechnicTest = new JComboBox<> (new Character[] {'A', 'N'}); 
		JList postExamList = new JList();
		scrollPostExamList = new JScrollPane(postExamList);

		
		//psychotechnicPanel
		JPanel psychotechnicPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		psychotechnicPanel.add(new JLabel("Concepto prueba psicotecnica", JLabel.CENTER));
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
		recommendationsPanel.setBorder(BorderFactory.createTitledBorder("Recomendaciones especificas"));
		recommendationsPanel.add(scrollRecommendationsText);
		
		
		JPanel partialPanels = new JPanel(new FlowLayout());
		partialPanels.add(psychotechnicPanel);
		partialPanels.add(postExamPanel);
		
		contentPane.add(recommendationsPanel, BorderLayout.SOUTH);
		contentPane.add(partialPanels, BorderLayout.CENTER);
	}

}
