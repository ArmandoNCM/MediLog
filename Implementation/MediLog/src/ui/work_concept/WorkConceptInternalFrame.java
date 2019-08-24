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
import javax.swing.JTextField;

import entities.PostExamAction;
import persistence.entityPersisters.PostExamActionPersistence;

public class WorkConceptInternalFrame extends AbstractWorkConcept{
	
	private static final long serialVersionUID = 1L;
	
	private JComboBox<Character> workConcept;
	private JTextField observations;
	

	public WorkConceptInternalFrame() {
		
		super();
		
		workConcept = new JComboBox<Character>(new Character[] {'A', 'N'});
		observations = new JTextField();


		JPanel workConceptPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		workConceptPanel.setBorder(BorderFactory.createTitledBorder("Examen de Ingreso"));
		workConceptPanel.add(new JLabel("Concepto Examen de Ingreso"));
		workConceptPanel.add(workConcept);
		
		
		contentPane.add(workConceptPanel, BorderLayout.NORTH);
		
		pack();
	}

}
