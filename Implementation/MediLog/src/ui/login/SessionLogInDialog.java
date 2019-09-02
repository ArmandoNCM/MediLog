package ui.login;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import entities.Employee;
import persistence.entityPersisters.EmployeePersistence;
import session.SessionHelper;
import session.SessionLogInListener;

public class SessionLogInDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5526980869451774940L;

	private static final String ACTION_ACCEPT = "ACTION_ACCEPT";
	
	private static final String ACTION_CANCEL = "ACTION_CANCEL";
	
	private SessionLogInListener sessionLogInListener;
	
	private boolean loggedIn = false;
	
	private JTextField idField;
	
	private JPasswordField passwordField;
	
	private WindowListener windowListener = new WindowAdapter() {
		
		public void windowClosed(WindowEvent e) {
			if (!loggedIn)
				System.exit(0);
		};
		
	};
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			switch (event.getActionCommand()) {
				case ACTION_ACCEPT:
					try {
						checkCredentials();
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(SessionLogInDialog.this, e.getMessage());
					}
					break;
					
				case ACTION_CANCEL:
					dispose();
			}
		}
	};
	
	public SessionLogInDialog(SessionLogInListener sessionLogInListener) {
		
		this.sessionLogInListener = sessionLogInListener;
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Inicio de sesión");
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(windowListener);
		
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(BorderFactory.createTitledBorder("Inicio de sesión"));
		setContentPane(contentPane);
		
		JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		contentPane.add(inputPanel, BorderLayout.CENTER);
		
		idField = new JTextField();
		passwordField = new JPasswordField();
		
		inputPanel.add(new JLabel("ID"));
		inputPanel.add(idField);
		inputPanel.add(new JLabel("Contraseña"));
		inputPanel.add(passwordField);
		
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
		
		pack();
	}
	
	private void checkCredentials() throws Exception {
		
		String id = idField.getText();
		
		String password = new String(passwordField.getPassword());
		
		if (id.isEmpty() || password.isEmpty())
			throw new Exception("Por favor ingrese los datos requeridos");
		
		try {
			Employee employee = EmployeePersistence.loadEmployee(id);
		
			if (employee == null)
				throw new Exception("The ID is not registered");
			
			if (EmployeePersistence.confirmIdentity(id, password)) {
				SessionHelper.getInstance().setEmployee(employee);
				loggedIn = true;
				dispose();
				sessionLogInListener.onLoggedIn();
			} else
				throw new Exception("Contraseña incorrecta");
			
		} catch (SQLException e) {
			throw new Exception("Sucedió un error al intentar verificar las credenciales");
		}
	}

}
