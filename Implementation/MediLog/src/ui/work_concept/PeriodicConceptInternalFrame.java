package ui.work_concept;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PeriodicConceptInternalFrame extends AbstractWorkConcept{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JComboBox<Character> periodicConcept, relocationConcept;

	public PeriodicConceptInternalFrame() {
		
		super();

		periodicConcept = new JComboBox<Character>(new Character[] {'A', 'N'});
		relocationConcept = new JComboBox<Character>(new Character[] {'T', 'P'});
		
		JPanel periodConcept1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		periodConcept1.add(new JLabel("Puede continuar laborando"));
		periodConcept1.add(periodicConcept);
		
		JPanel relocationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		relocationPanel.add(new JLabel("Se sugiere reubicacion laboral"));
		relocationPanel.add(relocationConcept);
		
		JPanel periodicConceptPanel = new JPanel(new GridLayout(2,1));
		periodicConceptPanel.setBorder(BorderFactory.createTitledBorder("Examen periodico"));
		periodicConceptPanel.add(periodConcept1);
		periodicConceptPanel.add(relocationPanel);
		
		contentPane.add(periodicConceptPanel, BorderLayout.NORTH);
		
		pack();
		

	}

}
