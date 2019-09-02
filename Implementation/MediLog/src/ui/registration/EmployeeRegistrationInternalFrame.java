package ui.registration;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import character_values.EmployeeRole;
import character_values.ValueHoldingEnum;
import entities.Employee;
import entities.Person;
import persistence.entityPersisters.ClientPersistence;
import persistence.entityPersisters.EmployeePersistence;

public class EmployeeRegistrationInternalFrame extends AbstractPersonRegistration {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6376594457800727114L;

	private Employee employee;
	
	private String password;
	
	private JComboBox<EmployeeRole> roleComboBox;
	
	private JPasswordField passwordField, passwordConfirmationField;
	
	public EmployeeRegistrationInternalFrame() {
		
		person = employee = new Employee();
		
		roleComboBox = new JComboBox<>(EmployeeRole.values());
		passwordField = new JPasswordField();
		passwordConfirmationField = new JPasswordField();
		
		JPanel employeePanel = new JPanel(new GridLayout(3, 2, 10, 10));
		employeePanel.setBorder(BorderFactory.createTitledBorder("Datos del Empleado"));
		inputFormPanel.add(employeePanel, BorderLayout.SOUTH);
		
		employeePanel.add(new JLabel("Rol"));
		employeePanel.add(roleComboBox);
		
		employeePanel.add(new JLabel("Contraseña"));
		employeePanel.add(passwordField);
		
		employeePanel.add(new JLabel("Confirmación contraseña"));
		employeePanel.add(passwordConfirmationField);
		
		pack();
	}
	
	@Override
	void readInputValues() throws Exception {
		super.readInputValues();
		
		String passwordInput = new String(passwordField.getPassword());
		String passwordConfirmationInput = new String(passwordConfirmationField.getPassword());
		
		if (passwordInput.isEmpty())
			throw new Exception("Por favor ingrese la contraseña");
		
		if (!passwordInput.equals(passwordConfirmationInput))
			throw new Exception("Las contraseñas no coinciden");
		
		password = passwordInput;
		
		byte role = (byte) ((EmployeeRole) roleComboBox.getSelectedItem()).getValue();
		
		employee.setRole(role);
	}

	@Override
	void checkExistingData(String personId) {
		
		try {
			Employee preExistingEmployee = EmployeePersistence.loadEmployee(personId);
			if (preExistingEmployee != null) {
				person = employee = preExistingEmployee;
				fillFieldsWithData();
			} else {
				clearFields();
				Person client = ClientPersistence.loadClient(personId);
				if (client != null) {
					person.setId(client.getId());
					person.setIdentificationType(client.getIdentificationType());
					person.setIdExpeditionCity(client.getIdExpeditionCity());
					person.setFirstName(client.getFirstName());
					person.setLastName(client.getLastName());
					person.setBirthDate(client.getBirthDate());
					person.setGender(client.getGender());
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
		roleComboBox.setSelectedItem(ValueHoldingEnum.getByValue(EmployeeRole.values(), (char) employee.getRole()));
	}
	
	@Override
	void clearFields() {
		super.clearFields();
		
		person = employee = new Employee();
		passwordField.setText("");
		passwordConfirmationField.setText("");
		roleComboBox.setSelectedIndex(0);
	}

	@Override
	void saveData() throws SQLException {
		EmployeePersistence.saveEmployee(employee, password);
	}

}
