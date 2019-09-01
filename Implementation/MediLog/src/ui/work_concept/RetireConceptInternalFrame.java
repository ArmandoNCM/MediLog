package ui.work_concept;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RetireConceptInternalFrame extends AbstractWorkConcept{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Character> retireConcept;
	
	public RetireConceptInternalFrame() {
		
		super();
		
		retireConcept = new JComboBox<Character>(new Character [] {'A', 'N'});
		
		JPanel retireConceptPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		retireConceptPanel.setBorder(BorderFactory.createTitledBorder("Examen de Egreso"));	
		retireConceptPanel.add(new JLabel("Concepto examen de egreso"));
		retireConceptPanel.add(retireConcept);
		
		contentPane.add(retireConceptPanel, BorderLayout.NORTH);
		pack();
		
	}

}
