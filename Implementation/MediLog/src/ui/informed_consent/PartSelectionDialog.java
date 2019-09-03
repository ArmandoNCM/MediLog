package ui.informed_consent;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import contracts.StringIdentifiable;
import persistence.entityPersisters.ClientPersistence;
import persistence.entityPersisters.CompanyPersistence;

public class PartSelectionDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3869694959848340841L;

	private static final String ACTION_ACCEPT = "ACTION_ACCEPT";
	
	private static final String ACTION_CLEAR = "ACTION_CLEAR";
	
	private IdentifiableSelectionListener selectionListener;
	
	private PartSelectionMode selectionMode;
	
	private JTextField idField;
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent event) {

			switch (event.getActionCommand()) {
				case ACTION_ACCEPT:
					try {
						searchAndLoadData();
						dispose();
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(PartSelectionDialog.this, e.getMessage());
					}
					break;
					
				case ACTION_CLEAR:
					selectionListener.onSelected(null, selectionMode);
					dispose();
			}
		}
	};
	
	public PartSelectionDialog(PartSelectionMode selectionMode, IdentifiableSelectionListener selectionListener) {
		
		this.selectionListener = selectionListener;
		this.selectionMode = selectionMode;
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		if (selectionMode == PartSelectionMode.CLIENT_SELECTION)
			setTitle("Selección de Cliente");
		else
			setTitle("Selección de Empresa");
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		idField = new JTextField();
		idField.setColumns(20);
		idField.setActionCommand(ACTION_ACCEPT);
		idField.addActionListener(actionListener);
		
		JPanel contentPane = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 10));
		contentPane.setBorder(BorderFactory.createTitledBorder("Seleccion de parte"));
		setContentPane(contentPane);

		contentPane.add(new JLabel("ID"));
		contentPane.add(idField);
		
		JButton acceptButton = new JButton("Aceptar");
		acceptButton.setActionCommand(ACTION_ACCEPT);
		acceptButton.addActionListener(actionListener);
		contentPane.add(acceptButton);
		
		if (selectionMode == PartSelectionMode.COMPANY_SELECTION) {
			JButton clearButton = new JButton("Limpiar");
			clearButton.setActionCommand(ACTION_CLEAR);
			clearButton.addActionListener(actionListener);
			contentPane.add(clearButton);
		}
		
		pack();
	}

	private void searchAndLoadData() throws Exception {

		String id = idField.getText();
		
		if (id.trim().isEmpty())
			throw new Exception("Por favor escriba el ID");
		
		try {
			
			StringIdentifiable identifiable;
			
			if (selectionMode == PartSelectionMode.CLIENT_SELECTION)
				identifiable = ClientPersistence.loadClient(id);
			else
				identifiable = CompanyPersistence.loadCompany(id);
			
			if (identifiable == null)
				throw new Exception("El ID ingresado no esta registrado en el sistema");
			
			selectionListener.onSelected(identifiable, selectionMode);
			
		} catch (SQLException e) {
			throw new Exception("Sucedió un error al intentar cargar la información");
		}
	}

}
