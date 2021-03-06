package ui.informed_consent;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import entities.Client;
import entities.InformedConsent;
import persistence.entityPersisters.ClientPersistence;
import persistence.entityPersisters.InformedConsentPersistence;

public class InformedConsentSelectionInternalFrame extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4774525022423665973L;

	private static final String ACTION_SEARCH = "ACTION_SEARCH";
	
	private static final String ACTION_ACCEPT = "ACTION_ACCEPT";
	
	private static final String ACTION_CANCEL = "ACTION_CANCEL";

	private JTable informedConsentsTable;
	private InformedConsentTableModel informedConsentTableModel = new InformedConsentTableModel();
	private JTextField idField;
	
	private SelectionListener selectionListener;

	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			switch (event.getActionCommand()) {
			case ACTION_SEARCH:
				
				String patientIdInput = idField.getText();
				if (patientIdInput != null && patientIdInput.trim().length() > 0) {
					patientIdInput = patientIdInput.trim();
					
					try {					
						Client client = ClientPersistence.loadClient(patientIdInput);
						if (client != null) {
							List<InformedConsent> informedConsents = InformedConsentPersistence.loadClientInformedConsents(client);
							informedConsentTableModel.setItems(informedConsents);
							informedConsentTableModel.fireTableDataChanged();
						} else {
							JOptionPane.showMessageDialog(getDesktopPane(), "El ID no se encuentra registrado en el sistema");
							informedConsentTableModel.setItems(new ArrayList<>());
							informedConsentTableModel.fireTableDataChanged();
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				} else {
					JOptionPane.showMessageDialog(getDesktopPane(), "Por favor ingrese el ID del paciente");
				}
				
				break;

			case ACTION_ACCEPT:
				
				int row = informedConsentsTable.getSelectedRow();
				if (row > -1) {
					InformedConsent informedConsent = informedConsentTableModel.getItem(row);
					dispose();
					selectionListener.onInformedConsentSelected(informedConsent);
				} else
					JOptionPane.showMessageDialog(InformedConsentSelectionInternalFrame.this, "Por favor seleccione un consentimiento informado");
					break;
				
			case ACTION_CANCEL:
				dispose();
			}
			
		}
	};
	
	public InformedConsentSelectionInternalFrame(SelectionListener selectionListener) {
		
		this.selectionListener = selectionListener;
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Buscar consentimiento informado");
		setResizable(false);
		setClosable(true);
		setIconifiable(true);
		
		JPanel contentPane = new JPanel(new BorderLayout(50, 5));
		contentPane.setBorder(BorderFactory.createTitledBorder("Búsqueda de Consentimiento Informado"));
		setContentPane(contentPane);
		
		idField = new JTextField();
		idField.setActionCommand(ACTION_SEARCH);
		idField.addActionListener(actionListener);
		JButton searchByPatientButton = new JButton("Buscar");
		searchByPatientButton.setActionCommand(ACTION_SEARCH);
		searchByPatientButton.addActionListener(actionListener);
		
		JPanel searchBarPanel = new JPanel(new GridLayout(1, 3, 20, 20));
		searchBarPanel.add(new JLabel("Identificación del paciente"));
		searchBarPanel.add(idField);
		searchBarPanel.add(searchByPatientButton);
		
		contentPane.add(searchBarPanel, BorderLayout.NORTH);
		
		informedConsentsTable = new JTable(informedConsentTableModel);
		informedConsentsTable.setFillsViewportHeight(true);
		informedConsentsTable.setRowSelectionAllowed(true);
		
		TableColumnModel columnModel = informedConsentsTable.getColumnModel();
		TableColumn column;
		for (int i = 0; i < InformedConsentTableModel.NUMBER_OF_COLUMNS; i++) {
			column = columnModel.getColumn(i);
			
			if (i == 1)
				column.setPreferredWidth(300);
			else
				column.setPreferredWidth(200);
		}
		
		JScrollPane scrollPane = new JScrollPane(informedConsentsTable);
		
		scrollPane.setBorder(BorderFactory.createTitledBorder("Consentimientos informados"));
		
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
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
	
	public interface SelectionListener {
		
		void onInformedConsentSelected(InformedConsent informedConsent);
		
	}
	
}
