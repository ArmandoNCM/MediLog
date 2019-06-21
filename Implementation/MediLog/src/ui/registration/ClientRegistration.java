package ui.registration;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientRegistration extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientRegistration() {

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// ID
		JComboBox<Character> identificationTypeComboBox = new JComboBox<Character>(new Character[]{'C', 'E'});
		JTextField identificationNumberTextField = new JTextField();
		JTextField identificationExpeditionCityTextField = new JTextField();
		identificationExpeditionCityTextField.setEditable(false);
		JButton selectExpeditionCityButton = new JButton("Seleccionar");
		// Name
		JTextField firstNameTextField = new JTextField();
		JTextField lastNameTextField = new JTextField();
		// Gender
		JComboBox<Character> genderComboBox = new JComboBox<Character>(new Character[] {'M', 'F'});
		JTextField birthdateTextField = new JTextField();
		
		JPanel idTypePanel = new JPanel(new GridLayout(2, 1));
		idTypePanel.add(new JLabel("Tipo de Identificación", JLabel.CENTER));
		idTypePanel.add(identificationTypeComboBox);
		
		JPanel idNumberPanel = new JPanel(new GridLayout(2,1));
		idNumberPanel.add(new JLabel("Número de Identificación", JLabel.CENTER));
		idNumberPanel.add(identificationNumberTextField);
		
		JPanel idExpeditionPanel = new JPanel(new GridLayout(2,1));
		idExpeditionPanel.add(new JLabel("Ciudad de Expedición", JLabel.CENTER));
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
		
		// Content pane
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new GridLayout(3, 1));
		contentPane.add(idInputPanel);
		contentPane.add(namePanel);
		contentPane.add(additionalInfoPanel);
		contentPane.setBorder(BorderFactory.createTitledBorder("Datos Básicos"));
		setContentPane(contentPane);
		
		pack();
		setResizable(false);
		setVisible(true);
		setClosable(true);
		setIconifiable(true);
		
		
	}

}
