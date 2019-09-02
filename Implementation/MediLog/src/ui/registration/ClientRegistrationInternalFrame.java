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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import character_values.AcademicLevel;
import character_values.ValueHoldingEnum;
import character_values.CivilStatus;
import character_values.SocialLevel;
import entities.Client;
import entities.Location;
import entities.Person;
import persistence.entityPersisters.ClientPersistence;
import persistence.entityPersisters.EmployeePersistence;

public class ClientRegistrationInternalFrame extends AbstractPersonRegistration {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8665676767178915188L;

	private static final String ACTION_SELECT_LOCATION = "ACTION_SELECT_RESIDENCY_LOCATION";
	
	private Client client;
	
	private Location residencyCity;
	
	private JTextField cityTextField, addressTextField, phoneTextField;
	
	private JComboBox<AcademicLevel> academicLevelComboBox;
	
	private JComboBox<CivilStatus> civilStatusComboBox;
	
	private JComboBox<SocialLevel> socialLevelComboBox;
	
	private JButton citySelectionButton;
	
	private LocationSelectionInternalFrame.LocationSelectionListener listener = new LocationSelectionInternalFrame.LocationSelectionListener() {
		
		@Override
		public void onLocationSelected(Location location) {
			residencyCity = location;
			if (residencyCity != null)
				cityTextField.setText(residencyCity.getCity());
			else
				cityTextField.setText("");
		}
	};
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent event) {

			switch (event.getActionCommand()) {
			case ACTION_SELECT_LOCATION:
				JInternalFrame internalFrame = new LocationSelectionInternalFrame(listener, true);
				internalFrame.setVisible(true);
				getDesktopPane().add(internalFrame);
				internalFrame.toFront();
			}
		}
	};
	
	private FocusListener focusListener = new FocusListener() {
		
		@Override
		public void focusLost(FocusEvent event) {
			
			if (event.getComponent() == phoneTextField) {
				phoneTextField.setText(phoneTextField.getText().replaceAll("[^\\d]", "" ));
			}
		}
		
		@Override
		public void focusGained(FocusEvent e) {
		}
	};
	
	private FocusListener switchOrderFocusListener = new FocusListener() {
		
		@Override
		public void focusLost(FocusEvent e) {
		}
		
		@Override
		public void focusGained(FocusEvent event) {
			if (event.getOppositeComponent() == citySelectionButton) {
				// Backward switch of focus
				addressTextField.grabFocus();
			} else {
				// Forward or arbitrary switch of focus
				citySelectionButton.grabFocus();
			}
		}
	};
	
	public ClientRegistrationInternalFrame() {
		super();
		
		person = client = new Client();
		
		// Text Fields initialization
		cityTextField = new JTextField();
		cityTextField.setEditable(false);
		cityTextField.setBackground(Color.WHITE);
		cityTextField.addFocusListener(switchOrderFocusListener);
		addressTextField = new JTextField();
		phoneTextField = new JTextField();
		phoneTextField.addFocusListener(focusListener);
		// Character Combo Boxes initialization 
		academicLevelComboBox = new JComboBox<>(AcademicLevel.values());
		civilStatusComboBox = new JComboBox<>(CivilStatus.values());
		// Integer Combo Box initialization
		socialLevelComboBox = new JComboBox<>(SocialLevel.values());
		
		// Residential information
		JPanel addressInputPanel = new JPanel(new GridLayout(2,1));
		addressInputPanel.add(new JLabel("Dirección de Residencia"));
		addressInputPanel.add(addressTextField);
		
		JPanel cityInputPanel = new JPanel(new GridLayout(2, 1));
		cityInputPanel.add(new JLabel("Ciudad de Residencia"));
		cityInputPanel.add(cityTextField);
		
		citySelectionButton = new JButton("Seleccionar");
		citySelectionButton.setActionCommand(ACTION_SELECT_LOCATION);
		citySelectionButton.addActionListener(actionListener);
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
		
		pack();
		
	}
	
	@Override 
	void readInputValues() throws Exception {
		
		super.readInputValues();
		
		String address = addressTextField.getText().trim();
		if (address.isEmpty()) address = null;
		String phoneNumber = phoneTextField.getText().trim().replaceAll("[^\\d]", "" );
		if (phoneNumber.isEmpty()) phoneNumber = null;
		
		char academicLevel = ((AcademicLevel) academicLevelComboBox.getSelectedItem()).getValue();
		char civilStatus = ((CivilStatus) civilStatusComboBox.getSelectedItem()).getValue();
		byte socialLevel = (byte) ((SocialLevel) socialLevelComboBox.getSelectedItem()).getValue();

		client.setAddress(address);
		client.setPhone(phoneNumber);
		client.setAcademicLevel(academicLevel);
		client.setCivilStatus(civilStatus);
		client.setSocialLevel(socialLevel);
		client.setCity(residencyCity);
	}

	@Override
	void checkExistingData(String personId) {
		try {
			Client preExistingClient = ClientPersistence.loadClient(personId);
			if (preExistingClient != null) {
				person = client = preExistingClient;
				fillFieldsWithData();
			} else {
				clearFields();
				Person employee = EmployeePersistence.loadEmployee(personId);
				if (employee != null) {
					person.setId(employee.getId());
					person.setIdentificationType(employee.getIdentificationType());
					person.setIdExpeditionCity(employee.getIdExpeditionCity());
					person.setFirstName(employee.getFirstName());
					person.setLastName(employee.getLastName());
					person.setBirthDate(employee.getBirthDate());
					person.setGender(employee.getGender());
					fillFieldsWithData();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	void fillFieldsWithData() {
		super.fillFieldsWithData();
		
		addressTextField.setText(client.getAddress() != null ? client.getAddress() : "");
		phoneTextField.setText(client.getPhone());
		
		residencyCity = client.getCity();
		if (residencyCity != null)
			cityTextField.setText(residencyCity.getCity());
		else
			cityTextField.setText("");
		
		academicLevelComboBox.setSelectedItem(ValueHoldingEnum.getByValue(AcademicLevel.values(), client.getAcademicLevel()));
		civilStatusComboBox.setSelectedItem(ValueHoldingEnum.getByValue(CivilStatus.values(), client.getCivilStatus()));
		socialLevelComboBox.setSelectedItem(ValueHoldingEnum.getByValue(SocialLevel.values(), (char) client.getSocialLevel()));
	}

	@Override
	void saveData() throws SQLException {
		ClientPersistence.saveClient(client);
	}
	
	@Override
	void clearFields() {
		super.clearFields();
		
		person = client = new Client();
		addressTextField.setText("");
		residencyCity = null;
		cityTextField.setText("");
		phoneTextField.setText("");
		academicLevelComboBox.setSelectedIndex(0);
		civilStatusComboBox.setSelectedIndex(0);
		socialLevelComboBox.setSelectedIndex(0);
	}

}
