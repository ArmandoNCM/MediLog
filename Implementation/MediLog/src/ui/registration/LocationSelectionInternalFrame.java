package ui.registration;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entities.Location;
import persistence.entityPersisters.LocationPersistance;

public class LocationSelectionInternalFrame extends JInternalFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3162979631222149200L;

	private static final String ACTION_ACCEPT = "ACTION_ACCEPT";
	
	private static final String ACTION_CANCEL = "ACTION_CANCEL";

	private static final String ACTION_CLEAR = "ACTION_CLEAR";
	
	private LocationSelectionListener locationSelectionListener;
	
	private StringComboBoxModel statesProvincesModel, citiesModel;
	
	private JComboBox<String> countryCB, stateProvinceCB, cityCB;
	
	private List<Location> countries, statesProvinces, cities;
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			switch (event.getActionCommand()) {
			case ACTION_ACCEPT:
				
				int countryIndex = countryCB.getSelectedIndex();
				if (countryIndex == -1) return;
				
				int stateProvinceIndex = stateProvinceCB.getSelectedIndex();
				if (stateProvinceIndex == -1) return;
				
				int cityIndex = cityCB.getSelectedIndex();
				if (cityIndex == -1) return;
				
				Location country = countries.get(countryIndex);
				Location stateProvince = statesProvinces.get(stateProvinceIndex);
				Location city = cities.get(cityIndex);
				
				Location location = new Location();
				location.setCountryId(country.getCountryId());
				location.setCountry(country.getCountry());
				location.setStateProvinceId(stateProvince.getStateProvinceId());
				location.setStateProvince(stateProvince.getStateProvince());
				location.setCityId(city.getCityId());
				location.setCity(city.getCity());
				
				dispose();
				locationSelectionListener.onLocationSelected(location);
				break;
				
			case ACTION_CLEAR:
				locationSelectionListener.onLocationSelected(null);
				
			case ACTION_CANCEL:
				dispose();
			}
			
		}
	};
	
	private ItemListener itemListener = new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent event) {
			
			@SuppressWarnings("unchecked")
			JComboBox<String> comboBox = (JComboBox<String>) event.getSource();
			int index = comboBox.getSelectedIndex();
			
			if (index > -1) {
				if (comboBox.equals(countryCB)) {
					int countryId = countries.get(index).getCountryId();
					loadStateProvinceList(countryId);
					statesProvincesModel.setList(Arrays.asList(locationListToStringArray(statesProvinces, LocationMode.STATE_PROVINCE)));
					stateProvinceCB.setSelectedIndex(0);
				} else if (comboBox.equals(stateProvinceCB)) {
					int stateProvinceId = statesProvinces.get(index).getStateProvinceId();
					loadCityList(stateProvinceId);
					citiesModel.setList(Arrays.asList(locationListToStringArray(cities, LocationMode.CITY)));
					cityCB.setSelectedIndex(0);
				}
			}
		}
	};
	
	public LocationSelectionInternalFrame(LocationSelectionListener locationSelectionListener, boolean isClearable) {
		
		this.locationSelectionListener = locationSelectionListener;
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setClosable(true);
		setIconifiable(true);
		setTitle("Selección de Ciudad");
		
		JPanel contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);
		
		JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		contentPane.add(inputPanel, BorderLayout.NORTH);
		
		statesProvincesModel = new StringComboBoxModel();
		citiesModel = new StringComboBoxModel();
		
		loadInitialState();
		
		countryCB = new JComboBox<String>(locationListToStringArray(countries, LocationMode.COUNTRY));
		countryCB.addItemListener(itemListener);
		countryCB.setPreferredSize(new Dimension(100, 25));
		((JLabel)countryCB.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
		
		stateProvinceCB = new JComboBox<String>(statesProvincesModel);
		stateProvinceCB.addItemListener(itemListener);
		stateProvinceCB.setPreferredSize(new Dimension(100, 25));
		((JLabel)stateProvinceCB.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
		
		cityCB = new JComboBox<String>(citiesModel);
		cityCB.setPreferredSize(new Dimension(100, 25));
		((JLabel)cityCB.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
		
		stateProvinceCB.setSelectedIndex(0);
		cityCB.setSelectedIndex(0);
		
		inputPanel.add(countryCB);
		inputPanel.add(stateProvinceCB);
		inputPanel.add(cityCB);

		// Buttons panel
		JButton acceptButton = new JButton("Aceptar");
		acceptButton.setActionCommand(ACTION_ACCEPT);
		acceptButton.addActionListener(actionListener);
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.setActionCommand(ACTION_CANCEL);
		cancelButton.addActionListener(actionListener);
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
		buttonsPanel.add(acceptButton);
		if (isClearable) {
			JButton clearButton = new JButton("Limpiar");
			clearButton.setActionCommand(ACTION_CLEAR);
			clearButton.addActionListener(actionListener);
			buttonsPanel.add(clearButton);
		}
		buttonsPanel.add(cancelButton);
		
		contentPane.add(buttonsPanel, BorderLayout.SOUTH);
		
		pack();
	}
	
	private void loadInitialState() {
		loadCountryList();
		int countryId = countries.get(0).getCountryId();
		loadStateProvinceList(countryId);
		int stateProvinceId = statesProvinces.get(0).getStateProvinceId();
		loadCityList(stateProvinceId);
		
		statesProvincesModel.setList(Arrays.asList(locationListToStringArray(statesProvinces, LocationMode.STATE_PROVINCE)));
		citiesModel.setList(Arrays.asList(locationListToStringArray(cities, LocationMode.CITY)));
	}
	
	private void loadCountryList() {
		try {
			countries = LocationPersistance.loadCountries();
		} catch (SQLException e) {
			e.printStackTrace();
			dispose();
			JOptionPane.showMessageDialog(getDesktopPane(), "No se pudieron cargar los países", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void loadStateProvinceList(int countryId) {
		try {
			statesProvinces = LocationPersistance.loadStateProvinces(countryId);
		} catch (SQLException e) {
			e.printStackTrace();
			dispose();
			JOptionPane.showMessageDialog(getDesktopPane(), "No se pudieron cargar las provincias o estados", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void loadCityList(int stateProvinceId) {
		try {
			cities = LocationPersistance.loadCities(stateProvinceId);
		} catch (SQLException e) {
			e.printStackTrace();
			dispose();
			JOptionPane.showMessageDialog(getDesktopPane(), "No se pudieron cargar las ciudades", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private String[] locationListToStringArray(List<Location> locations, LocationMode mode) {
		
		String[] strings = new String[locations.size()];
		
		for (int i = 0; i < strings.length; i++)			
			strings[i] = locationToString(locations.get(i), mode);
		
		return strings;
	}
	
	private String locationToString(Location location, LocationMode mode) {
		switch (mode) {
			case COUNTRY:
				return location.getCountry();
			case STATE_PROVINCE:
				return location.getStateProvince();
			case CITY:
				return location.getCity();
		}
		return null;
	}
	
	private enum LocationMode {
		COUNTRY, STATE_PROVINCE, CITY
	}
	
	public interface LocationSelectionListener {
		void onLocationSelected(Location location);
	}
	

}
