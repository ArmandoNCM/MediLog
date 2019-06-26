package ui.registration;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientRegistration extends AbstractPersonRegistration {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField cityTextField, addressTextField, phoneTextField;
	
	private JComboBox<Character> academicLevelComboBox, civilStatusComboBox;
	
	private JComboBox<Integer> socialLevelComboBox;
	
	
	public ClientRegistration() {
		super();
		
		// Text Fields initialization
		cityTextField = new JTextField();
		addressTextField = new JTextField();
		phoneTextField = new JTextField();
		// Character Combo Boxes initialization 
		academicLevelComboBox = new JComboBox<>(new Character[] {'B', 'S', 'T', 'P'});
		civilStatusComboBox = new JComboBox<>(new Character[] {'S', 'U', 'C', 'V'});
		// Integer Combo Box initialization
		socialLevelComboBox = new JComboBox<>(new Integer[] {1, 2, 3, 4, 5, 6});
		
		// Residential information
		JPanel addressInputPanel = new JPanel(new GridLayout(2,1));
		addressInputPanel.add(new JLabel("Dirección de Residencia"));
		addressInputPanel.add(addressTextField);
		
		JPanel cityInputPanel = new JPanel(new GridLayout(2, 1));
		cityInputPanel.add(new JLabel("Ciudad de Residencia"));
		cityInputPanel.add(cityTextField);
		
		JButton citySelectionButton = new JButton("Seleccionar");
		JPanel citySelectionButtonPanel = new JPanel(new GridLayout(2,1));
		citySelectionButtonPanel.add(Box.createVerticalStrut(10));
		citySelectionButtonPanel.add(citySelectionButton);
	
		JPanel residenceInformationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		residenceInformationPanel.add(addressInputPanel);
		residenceInformationPanel.add(cityInputPanel);
		residenceInformationPanel.add(citySelectionButtonPanel);
		
		// Additional information
		JPanel phoneInputPanel = new JPanel(new GridLayout(2,1));
		phoneInputPanel.add(new JLabel("Número Telefónico"));
		phoneInputPanel.add(phoneTextField);
		
		JPanel academicLevelInputPanel = new JPanel(new GridLayout(2,1));
		academicLevelInputPanel.add(new JLabel("Nivel Académico"));
		academicLevelInputPanel.add(academicLevelComboBox);
		
		JPanel civilStatusInputPanel = new JPanel(new GridLayout(2,1));
		civilStatusInputPanel.add(new JLabel("Estado Civil"));
		civilStatusInputPanel.add(civilStatusComboBox);
		
		JPanel socialLevelInputPanel = new JPanel(new GridLayout(2,1));
		socialLevelInputPanel.add(new JLabel("Estrato"));
		socialLevelInputPanel.add(socialLevelComboBox);
		
		JPanel additionalInformationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		additionalInformationPanel.add(phoneInputPanel);
		additionalInformationPanel.add(academicLevelInputPanel);
		additionalInformationPanel.add(civilStatusInputPanel);
		additionalInformationPanel.add(socialLevelInputPanel);
		
		// Client input panel
		JPanel clientInputFormPanel = new JPanel(new GridLayout(2, 1));
		clientInputFormPanel.add(residenceInformationPanel);
		clientInputFormPanel.add(additionalInformationPanel);
		clientInputFormPanel.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
		
		inputFormPanel.add(clientInputFormPanel, BorderLayout.SOUTH);
		
		
		// Buttons panel
		JButton cancelButton = new JButton("Cancelar");
		
		JButton addButton = new JButton("Agregar");
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(addButton);
		
		contentPane.add(buttonsPanel, BorderLayout.SOUTH);
		
		pack();
		
	}

}
