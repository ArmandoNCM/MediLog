package ui.registration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import character_values.ValueHoldingEnum;
import character_values.IdentificationType;
import character_values.SexualGenderType;
import entities.Location;
import entities.Person;
import persistence.entityPersisters.PersonPersistence;

public abstract class AbstractPersonRegistration extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1297574237881233684L;

	private static final String ACTION_ACCEPT = "ACTION_ACCEPT";
	
	private static final String ACTION_CANCEL = "ACTION_CANCEL";
	
	private static final String BIRTH_DATE_FORMAT_REGEX = "\\d{2}\\/\\d{2}\\/\\d{4}";
	
	private static final String ACTION_SELECT_LOCATION = "ACTION_SELECT_EXPEDITION_LOCATION";
	
	private final DateTimeFormatter birthDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	Person person;
	
	JPanel contentPane, inputFormPanel;

	private Location idExpeditionCity;
	
	private JComboBox<IdentificationType> identificationTypeComboBox;
	
	private JComboBox<SexualGenderType> genderComboBox;
	
	private JButton selectExpeditionCityButton;

	private JTextField identificationNumberTextField, identificationExpeditionCityTextField, firstNameTextField, lastNameTextField, birthdateTextField;
	
	private LocationSelectionInternalFrame.LocationSelectionListener listener = new LocationSelectionInternalFrame.LocationSelectionListener() {
		
		@Override
		public void onLocationSelected(Location location) {
			idExpeditionCity = location;
			identificationExpeditionCityTextField.setText(idExpeditionCity.getCity());
		}
	};
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			switch (event.getActionCommand()) {
				case ACTION_ACCEPT:
					
					try {
						readInputValues();
						saveData();
						dispose();
						JOptionPane.showMessageDialog(getDesktopPane(), "Datos guardados con éxito", "Éxito al guardar", JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(getDesktopPane(), "No se pudo guardar la información", "Error al guardar", JOptionPane.ERROR_MESSAGE);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(getDesktopPane(), e.getMessage());
					}
					break;
				case ACTION_CANCEL:
					dispose();
					break;
					
				case ACTION_SELECT_LOCATION:
					JInternalFrame internalFrame = new LocationSelectionInternalFrame(listener, false);
					internalFrame.setVisible(true);
					getDesktopPane().add(internalFrame);
					internalFrame.toFront();
			}
		}
	};
	
	private FocusListener focusListener = new FocusListener() {
		
		@Override
		public void focusLost(FocusEvent event) {
			
			if (event.getComponent() instanceof JTextField) {
				JTextField component = (JTextField) event.getComponent();
				component.setText(component.getText().trim());
				
				if (component == identificationNumberTextField) {
					
					if (component.getText().isEmpty())
						clearFields();
					else {
						String id = component.getText();
						try {
							if (PersonPersistence.personExists(id))
								checkExistingData(id);
							else
								clearFields();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		@Override
		public void focusGained(FocusEvent event) {
		}
	};
	
	private FocusListener switchOrderFocusListener = new FocusListener() {
		
		@Override
		public void focusLost(FocusEvent e) {
		}
		
		@Override
		public void focusGained(FocusEvent event) {
			if (event.getOppositeComponent() == selectExpeditionCityButton) {
				// Backward switch of focus
				identificationNumberTextField.grabFocus();
			} else {
				// Forward or arbitrary switch of focus
				selectExpeditionCityButton.grabFocus();
			}
		}
	};

	public AbstractPersonRegistration() {

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setClosable(true);
		setIconifiable(true);
		
		contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);
		
		// Initialization of components
		identificationTypeComboBox = new JComboBox<>(IdentificationType.values());
		identificationNumberTextField = new JTextField();
		identificationNumberTextField.addFocusListener(focusListener);
		identificationExpeditionCityTextField = new JTextField();
		identificationExpeditionCityTextField.setEditable(false);
		identificationExpeditionCityTextField.setBackground(Color.WHITE);
		selectExpeditionCityButton = new JButton("Seleccionar");
		selectExpeditionCityButton.setActionCommand(ACTION_SELECT_LOCATION);
		selectExpeditionCityButton.addActionListener(actionListener);
		
		firstNameTextField = new JTextField();
		firstNameTextField.addFocusListener(focusListener);
		lastNameTextField = new JTextField();
		lastNameTextField.addFocusListener(focusListener);
		genderComboBox = new JComboBox<>(SexualGenderType.values());
		birthdateTextField = new JTextField();
		birthdateTextField.addFocusListener(focusListener);
		
		JPanel idTypePanel = new JPanel(new GridLayout(2, 1));
		idTypePanel.add(new JLabel("Tipo de Identificación", JLabel.CENTER));
		idTypePanel.add(identificationTypeComboBox);
		
		JPanel idNumberPanel = new JPanel(new GridLayout(2,1));
		idNumberPanel.add(new JLabel("Número de Identificación", JLabel.CENTER));
		idNumberPanel.add(identificationNumberTextField);
		
		JPanel idExpeditionPanel = new JPanel(new GridLayout(2,1));
		idExpeditionPanel.add(new JLabel("Ciudad de Expedición", JLabel.CENTER));
		identificationExpeditionCityTextField.addFocusListener(switchOrderFocusListener);
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
		
		JPanel birthdateFormatPanel = new JPanel(new GridLayout(2, 1));
		birthdateFormatPanel.add(new JLabel("Formato", JLabel.CENTER));
		birthdateFormatPanel.add(new JLabel("DD/MM/AAAA", JLabel.CENTER));
		
		JPanel additionalInfoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		additionalInfoPanel.add(genderPanel);
		additionalInfoPanel.add(birthdatePanel);
		additionalInfoPanel.add(birthdateFormatPanel);
		
		JPanel personInputFormPanel = new JPanel(new GridLayout(3, 1));
		personInputFormPanel.add(idInputPanel);
		personInputFormPanel.add(namePanel);
		personInputFormPanel.add(additionalInfoPanel);
		personInputFormPanel.setBorder(BorderFactory.createTitledBorder("Datos Básicos"));
		
		inputFormPanel = new JPanel(new BorderLayout());
		inputFormPanel.add(personInputFormPanel, BorderLayout.NORTH);

		contentPane.add(inputFormPanel, BorderLayout.CENTER);
		
		// Buttons panel
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
	}
	
	void readInputValues() throws Exception {
		
		String id = identificationNumberTextField.getText().trim();
		String name = firstNameTextField.getText().trim();
		String lastName = lastNameTextField.getText().trim();
		String birthDateString = birthdateTextField.getText().trim();
		
		char identificationType = ((IdentificationType) identificationTypeComboBox.getSelectedItem()).getValue();
		char sexualGender = ((SexualGenderType) genderComboBox.getSelectedItem()).getValue();
		
		if (idExpeditionCity == null)
			throw new Exception("Por favor seleccione una ciudad de expedición");
		
		if (id.isEmpty() || name.isEmpty() || lastName.isEmpty() || birthDateString.isEmpty())
			throw new Exception("Por favor ingrese todos los datos básicos requeridos");
		
		if (!birthDateString.matches(BIRTH_DATE_FORMAT_REGEX))
			throw new Exception("Por favor revise el formato de la fecha de nacimiento");
		
		LocalDate birthDate = LocalDate.parse(birthDateString, birthDateFormatter);
		
		person.setId(id);
		person.setFirstName(name);
		person.setLastName(lastName);
		person.setBirthDate(birthDate);
		person.setIdentificationType(identificationType);
		person.setGender(sexualGender);
		person.setIdExpeditionCity(idExpeditionCity);
	}
	
	void fillFieldsWithData() {
		idExpeditionCity = person.getIdExpeditionCity();
		identificationExpeditionCityTextField.setText(idExpeditionCity.getCity());
		firstNameTextField.setText(person.getFirstName());
		lastNameTextField.setText(person.getLastName());
		birthdateTextField.setText(birthDateFormatter.format(person.getBirthDate()));
		identificationTypeComboBox.setSelectedItem(ValueHoldingEnum.getByValue(IdentificationType.values(), person.getIdentificationType()));
		genderComboBox.setSelectedItem(ValueHoldingEnum.getByValue(SexualGenderType.values(), person.getGender()));
	}
	
	void clearFields() {
		identificationTypeComboBox.setSelectedIndex(0);
		idExpeditionCity = null;
		identificationExpeditionCityTextField.setText("");
		firstNameTextField.setText("");
		lastNameTextField.setText("");
		genderComboBox.setSelectedIndex(0);
		birthdateTextField.setText("");
	}
	
	abstract void checkExistingData(String personId);
	
	abstract void saveData() throws SQLException;
	
}
