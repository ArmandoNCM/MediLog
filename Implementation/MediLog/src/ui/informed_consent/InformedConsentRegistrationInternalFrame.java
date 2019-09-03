package ui.informed_consent;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import character_values.ExamType;
import contracts.StringIdentifiable;
import entities.Client;
import entities.Company;
import entities.InformedConsent;
import persistence.entityPersisters.InformedConsentPersistence;
import session.SessionHelper;

public class InformedConsentRegistrationInternalFrame extends JInternalFrame implements IdentifiableSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3594161351645852727L;

	private static final String ACTION_ACCEPT = "ACTION_ACCEPT";
	
	private static final String ACTION_CANCEL = "ACTION_CANCEL";
	
	private static final String ACTION_SELECT_COMPANY = "ACTION_SELECT_COMPANY";
	
	private static final String ACTION_SELECT_CLIENT = "ACTION_SELECT_CLIENT";

	private JTextField clientTextField, companyTextField;
	
	private JComboBox<ExamType> checkTypeComboBox;
	
	private JCheckBox workInHeightCheckBox;
	
	private Company company;
	
	private Client client;

	private FocusListener focusListener = new FocusListener() {
		
		@Override
		public void focusLost(FocusEvent e) {
			// No operation
		}
		
		@Override
		public void focusGained(FocusEvent event) {

			if (event.getComponent().equals(companyTextField)) {
				// Company text field
				if (event.getOppositeComponent().equals(selectCompanyButton))
					selectClientButton.grabFocus();
				else
					selectCompanyButton.grabFocus();
			} else {
				// Client text field
				selectClientButton.grabFocus();
			}
		}
	};
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent event) {

			switch (event.getActionCommand()) {
				case ACTION_ACCEPT:
					try {
						saveInformedConsent();
						dispose();
						JOptionPane.showMessageDialog(getDesktopPane(), "Consentimiento informado guardado con éxito", "Éxito al guardar", JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(getDesktopPane(), e.getMessage());
					}
					break;
					
				case ACTION_CANCEL:
					dispose();
					break;
					
				case ACTION_SELECT_COMPANY:
					displaySelectionDialog(PartSelectionMode.COMPANY_SELECTION);
					break;
					
				case ACTION_SELECT_CLIENT:
					displaySelectionDialog(PartSelectionMode.CLIENT_SELECTION);
					break;
			}
		}
	};

	private JButton selectClientButton;

	private JButton selectCompanyButton;
	
	public InformedConsentRegistrationInternalFrame() {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setClosable(true);
		setIconifiable(true);
		setTitle("Consentimiento informado");
		
		// Initialization of components
		clientTextField = new JTextField();
		clientTextField.setEditable(false);
		clientTextField.addFocusListener(focusListener );
		
		companyTextField = new JTextField();
		companyTextField.setEditable(false);
		companyTextField.addFocusListener(focusListener );
		
		checkTypeComboBox = new JComboBox<>(ExamType.values());
		
		workInHeightCheckBox = new JCheckBox();
		
		selectClientButton = new JButton("Seleccionar");
		selectClientButton.setActionCommand(ACTION_SELECT_CLIENT);
		selectClientButton.addActionListener(actionListener);
		selectCompanyButton = new JButton("Seleccionar");
		selectCompanyButton.setActionCommand(ACTION_SELECT_COMPANY);
		selectCompanyButton.addActionListener(actionListener);
		
		// Client and company
		JPanel clientInputPanel = new JPanel(new GridLayout(1, 3, 10, 10));
		clientInputPanel.add(new JLabel("Cliente"));
		clientInputPanel.add(clientTextField);
		clientInputPanel.add(selectClientButton);
		
		JPanel companyInputPanel = new JPanel(new GridLayout(1, 3, 10, 10));
		companyInputPanel.add(new JLabel("Compañía"));
		companyInputPanel.add(companyTextField);
		companyInputPanel.add(selectCompanyButton);
		
		JPanel partiesInputPanel = new JPanel();
		partiesInputPanel.setLayout(new BoxLayout(partiesInputPanel, BoxLayout.Y_AXIS));
		partiesInputPanel.add(clientInputPanel);
		partiesInputPanel.add(Box.createVerticalStrut(10));
		partiesInputPanel.add(companyInputPanel);
		
		// Informed consent information
		JPanel workInHeightsInput = new JPanel(new GridLayout(2, 1));
		workInHeightsInput.add(new JLabel("Trabajo en Alturas"));
		workInHeightsInput.add(workInHeightCheckBox);
		
		JPanel checkTypeInput = new JPanel(new GridLayout(2, 1));
		checkTypeInput.add(new JLabel("Tipo de Examen"));
		checkTypeInput.add(checkTypeComboBox);
		
		JPanel informedConsentInformationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		informedConsentInformationPanel.add(workInHeightsInput);
		informedConsentInformationPanel.add(checkTypeInput);
		
		// Buttons panel
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.setActionCommand(ACTION_CANCEL);
		cancelButton.addActionListener(actionListener);
		JButton acceptButton = new JButton("Agregar");
		acceptButton.setActionCommand(ACTION_ACCEPT);
		acceptButton.addActionListener(actionListener);
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(acceptButton);
		
		// Content pane
		JPanel contentPane = new JPanel(new GridLayout(3, 1));
		contentPane.add(partiesInputPanel);
		contentPane.add(informedConsentInformationPanel);
		contentPane.add(buttonsPanel);
		contentPane.setBorder(BorderFactory.createTitledBorder("Consentimiento Informado"));
		setContentPane(contentPane);
		pack();
	}
	
	private void displaySelectionDialog(PartSelectionMode selectionMode) {
		
		JDialog selectionDialog = new PartSelectionDialog(selectionMode, this);
		Component frame = getDesktopPane();
		selectionDialog.setLocationRelativeTo(frame);
		selectionDialog.setLocation((frame.getWidth() / 2) - (selectionDialog.getWidth() / 2), (frame.getHeight() / 2) - (selectionDialog.getHeight() / 2));
		selectionDialog.setVisible(true);
	}

	@Override
	public void onSelected(StringIdentifiable identifiable, PartSelectionMode selectionMode) {

		if (selectionMode == PartSelectionMode.CLIENT_SELECTION)
			client = (Client) identifiable;
		else
			company = (Company) identifiable;
		
		fillFields();
	}
	
	private void fillFields() {
		
		if (client != null)
			clientTextField.setText(client.getFullName());
		
		if (company != null)
			companyTextField.setText(company.getName());
		else
			companyTextField.setText("");
	}
	
	private void saveInformedConsent() throws Exception {

		if (client == null)
			throw new Exception("Debe seleccionar un cliente");
		
		boolean workInHeights = workInHeightCheckBox.isSelected();
		
		char checkType = ((ExamType) checkTypeComboBox.getSelectedItem()).getValue();

		InformedConsent informedConsent = new InformedConsent(client);
		informedConsent.setEmployee(SessionHelper.getInstance().getEmployee());
		informedConsent.setContractingCompany(company);
		informedConsent.setCheckType(checkType);
		informedConsent.setWorkInHeights(workInHeights);
		informedConsent.setDate(LocalDate.now());
		
		try {
			boolean success = InformedConsentPersistence.saveInformedConsent(informedConsent);
			if (!success)
				throw new SQLException();
		} catch (SQLException e) {
			throw new Exception("Ocurrió un error al guardar la información");
		}
	}
}
