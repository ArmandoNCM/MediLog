package ui.registration;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class AbstractPersonRegistration extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel contentPane, inputFormPanel;

	JComboBox<Character> identificationTypeComboBox, genderComboBox;

	JTextField identificationNumberTextField, identificationExpeditionCityTextField, firstNameTextField, lastNameTextField, birthdateTextField;

	public AbstractPersonRegistration() {

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setClosable(true);
		setIconifiable(true);
		
		contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);
		
		// Initialization of components
		identificationTypeComboBox = new JComboBox<Character>(new Character[]{'C', 'E'});
		identificationNumberTextField = new JTextField();
		identificationExpeditionCityTextField = new JTextField();
		identificationExpeditionCityTextField.setEditable(false);
		JButton selectExpeditionCityButton = new JButton("Seleccionar");
		firstNameTextField = new JTextField();
		lastNameTextField = new JTextField();
		genderComboBox = new JComboBox<Character>(new Character[] {'M', 'F'});
		birthdateTextField = new JTextField();
		
		JPanel idTypePanel = new JPanel(new GridLayout(2, 1));
		idTypePanel.add(new JLabel("Tipo de Identificación", JLabel.CENTER));
		idTypePanel.add(identificationTypeComboBox);
		
		JPanel idNumberPanel = new JPanel(new GridLayout(2,1));
		idNumberPanel.add(new JLabel("Número de Identificación", JLabel.CENTER));
		idNumberPanel.add(identificationNumberTextField);
		
		JPanel idExpeditionPanel = new JPanel(new GridLayout(2,1));
		idExpeditionPanel.add(new JLabel("Ciudad de Expedición", JLabel.CENTER));
		identificationExpeditionCityTextField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// No operation
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if (e.getOppositeComponent().equals(selectExpeditionCityButton)) {
					// Backward switch of focus
					identificationNumberTextField.grabFocus();
				} else {
					// Forward or arbitrary switch of focus
					selectExpeditionCityButton.grabFocus();
				}
			}
		});
		idExpeditionPanel.add(identificationExpeditionCityTextField);
		
		JPanel idExpeditionButtonPanel = new JPanel(new GridLayout(2,1));
		idExpeditionButtonPanel.add(Box.createVerticalStrut(10));
		idExpeditionButtonPanel.add(selectExpeditionCityButton);
		
		JPanel idInputPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 10));
		idInputPanel.add(idTypePanel);
		idInputPanel.add(idNumberPanel);
		idInputPanel.add(idExpeditionPanel);
		idInputPanel.add(idExpeditionButtonPanel);
		
		JPanel namePanel = new JPanel(new GridLayout(2, 2));
		namePanel.add(new JLabel("Nombres", JLabel.CENTER));
		namePanel.add(new JLabel("Apellidos", JLabel.CENTER));
		namePanel.add(firstNameTextField);
		namePanel.add(lastNameTextField);
		
		JPanel genderPanel = new JPanel(new GridLayout(2, 1));
		genderPanel.add(new JLabel("Género", JLabel.CENTER));
		genderPanel.add(genderComboBox);
		
		JPanel birthdatePanel = new JPanel(new GridLayout(2, 1));
		birthdatePanel.add(new JLabel("Fecha de Nacimiento", JLabel.CENTER));
		birthdatePanel.add(birthdateTextField);
		
		JPanel additionalInfoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		additionalInfoPanel.add(genderPanel);
		additionalInfoPanel.add(birthdatePanel);
		
		JPanel personInputFormPanel = new JPanel(new GridLayout(3, 1));
		personInputFormPanel.add(idInputPanel);
		personInputFormPanel.add(namePanel);
		personInputFormPanel.add(additionalInfoPanel);
		personInputFormPanel.setBorder(BorderFactory.createTitledBorder("Datos Básicos"));
		
		inputFormPanel = new JPanel(new BorderLayout());
		inputFormPanel.add(personInputFormPanel, BorderLayout.NORTH);

		contentPane.add(inputFormPanel, BorderLayout.CENTER);		
		
	}
	
}
