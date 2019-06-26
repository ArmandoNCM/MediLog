package ui.exam;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InformedConsentRegistrationInternalFrame extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField clientTextField, companyTextField;
	
	private JComboBox<Character> checkTypeComboBox;
	
	private JCheckBox workInHeightCheckBox;

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

	private JButton selectClientButton;

	private JButton selectCompanyButton;
	
	public InformedConsentRegistrationInternalFrame() {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setClosable(true);
		setIconifiable(true);
		
		// Initialization of components
		clientTextField = new JTextField();
		clientTextField.setEditable(false);
		clientTextField.addFocusListener(focusListener );
		
		companyTextField = new JTextField();
		companyTextField.setEditable(false);
		companyTextField.addFocusListener(focusListener );
		
		checkTypeComboBox = new JComboBox<>(new Character[] {'I', 'P', 'R'});
		
		workInHeightCheckBox = new JCheckBox();
		
		selectClientButton = new JButton("Seleccionar");
		selectCompanyButton = new JButton("Seleccionar");
		
		// Client and company
		JPanel clientInputPanel = new JPanel(new GridLayout(1, 3));
		clientInputPanel.add(new JLabel("Cliente"));
		clientInputPanel.add(clientTextField);
		clientInputPanel.add(selectClientButton);
		
		JPanel companyInputPanel = new JPanel(new GridLayout(1, 3));
		companyInputPanel.add(new JLabel("Compañía"));
		companyInputPanel.add(companyTextField);
		companyInputPanel.add(selectCompanyButton);
		
		JPanel partiesInputPanel = new JPanel();
		partiesInputPanel.setLayout(new BoxLayout(partiesInputPanel, BoxLayout.Y_AXIS));
		partiesInputPanel.add(clientInputPanel);
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
		JButton addButton = new JButton("Agregar");
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(addButton);
		
		// Content pane
		JPanel contentPane = new JPanel(new GridLayout(3, 1));
		contentPane.add(partiesInputPanel);
		contentPane.add(informedConsentInformationPanel);
		contentPane.add(buttonsPanel);
		contentPane.setBorder(BorderFactory.createTitledBorder("Consentimiento Informado"));
		setContentPane(contentPane);
		pack();
		
	}
}
