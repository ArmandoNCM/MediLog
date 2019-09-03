package ui.generic_bicolumn_selection;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import contracts.IntegerIdentifiable;
import contracts.TypeClassified;

public class GenericSelectionInternalFrame<T extends IntegerIdentifiable & TypeClassified> extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5375386184941604307L;
	
	private static final String ACTION_ADD = "ACTION_ADD";
	private static final String ACTION_REMOVE = "ACTION_REMOVE";
	private static final String ACTION_ACCEPT = "ACTION_ACCEPT";
	private static final String ACTION_CANCEL = "ACTION_CANCEL";
	
	private String type;
	
	private GenericSelectionListModel<T> availableItemsListModel, selectedItemsListModel;
	
	private List<T> availableItems, selectedItems;

	private JList<T> availableItemsList, selectedItemsList;
	
	private GenericSelectionListener<T> selectionListener;
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			T selection;
			
			switch (event.getActionCommand()) {
			
			case ACTION_ADD:
				selection = availableItemsList.getSelectedValue();
				if (selection != null) {
					selectedItemsListModel.addItem(selection);
					availableItemsListModel.removeItem(selection);
				}
				break;
				
			case ACTION_REMOVE:
				selection = selectedItemsList.getSelectedValue();
				if (selection != null) {
					availableItemsListModel.addItem(selection);
					selectedItemsListModel.removeItem(selection);
				}
				break;
				
			case ACTION_ACCEPT:
				dispose();
				selectionListener.onItemsSelected(type, selectedItemsListModel.getItems());
				break;
				
			case ACTION_CANCEL:
				dispose();
			}
		}
	};
	

	public GenericSelectionInternalFrame(String medicalAnomalyType, List<T> availableItems, List<T> selectedAnomalies, GenericSelectionListener<T> medicalAnomaliesSelectionListener) {
		
		this.type = medicalAnomalyType;
		this.availableItems = availableItems;
		this.selectedItems = new ArrayList<T>();
		this.selectionListener = medicalAnomaliesSelectionListener;
		
		configureListModels(selectedAnomalies);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Selección de elementos");
		setResizable(false);
		setClosable(true);
		setIconifiable(true);
		
		JPanel contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);
		
		JPanel centerPanel = new JPanel(new FlowLayout());
		contentPane.add(centerPanel);
		
		availableItemsList = new JList<T>(availableItemsListModel);
		availableItemsList.setVisibleRowCount(10);
		availableItemsList.setFixedCellWidth(300);
		JScrollPane availableScrollPane = new JScrollPane(availableItemsList);
		
		selectedItemsList = new JList<T>(selectedItemsListModel);
		selectedItemsList.setVisibleRowCount(10);
		selectedItemsList.setFixedCellWidth(300);
		JScrollPane selectedScrollPane = new JScrollPane(selectedItemsList);
		
		JPanel centerButtonsPanel = new JPanel(new GridLayout(2,1));
		centerPanel.add(availableScrollPane);
		centerPanel.add(centerButtonsPanel);
		centerPanel.add(selectedScrollPane);
		
		JButton addButton = new JButton(">>");
		addButton.setActionCommand(ACTION_ADD);
		addButton.addActionListener(actionListener);
		centerButtonsPanel.add(addButton);
		
		JButton removeButton = new JButton("<<");
		removeButton.setActionCommand(ACTION_REMOVE);
		removeButton.addActionListener(actionListener);
		centerButtonsPanel.add(removeButton);
		
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		
		JButton acceptButton = new JButton("Aceptar");
		acceptButton.setActionCommand(ACTION_ACCEPT);
		acceptButton.addActionListener(actionListener);
		
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.setActionCommand(ACTION_CANCEL);
		cancelButton.addActionListener(actionListener);
		
		bottomPanel.add(acceptButton);
		bottomPanel.add(cancelButton);
		
		pack();
		
	}
	
	private void configureListModels(List<T> generalSelectedAnomalies) {
		
		for (T anomaly : generalSelectedAnomalies) {
			if (anomaly.getType().equals(type))
				selectedItems.add(anomaly);
		}
		
		List<T> duplicateAnomalies = new ArrayList<T>();
		for (T selectedAnomaly : selectedItems) 
			for (T availableAnomaly : availableItems) 
				if (selectedAnomaly.getId() == availableAnomaly.getId())
					duplicateAnomalies.add(availableAnomaly);
		
		availableItems.removeAll(duplicateAnomalies);
		
		availableItemsListModel = new GenericSelectionListModel<T>();
		availableItemsListModel.setItems(availableItems);
		
		selectedItemsListModel = new GenericSelectionListModel<T>();
		selectedItemsListModel.setItems(selectedItems);
	}

}
