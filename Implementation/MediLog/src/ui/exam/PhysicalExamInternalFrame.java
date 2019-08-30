package ui.exam;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.Console;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import entities.MedicalAnomaly;
import persistence.entityPersisters.MedicalAnomalyPersistence;

public class PhysicalExamInternalFrame extends JInternalFrame {
	
	private JScrollPane scrollDiagnosticsText, scrollRecomendationsText, scrollConclusionsText, scrollAnomalyList ;
	
	private static final long serialVersionUID = 1L;
	private JTextField weightTextField, heightTextField, pulseTextField, tempeTextField, respiratoryFTextField, bloodPressureU, bloodPressureD, imcTextField;
	
	private JTextArea diagnosticsText, recomendationsText, conclusionsText;
	private JComboBox<Character> handednessComboBox;
	
	
//	private FocusListener focusListener = new FocusListener() {
//		
//		@Override
//		public void focusLost(FocusEvent e) {
//			// No operation
//		}
//		
//		@Override
//		public void focusGained(FocusEvent event) {
//
//			if (event.getComponent().equals(weightTextField)) {
//				// weight text field
//				if (event.getOppositeComponent().equals(selectCompanyButton))
//					selectClientButton.grabFocus();
//				else
//					selectCompanyButton.grabFocus();
//			} else {
//				// Client text field
//				selectClientButton.grabFocus();
//			}
//		}
//	};

	public PhysicalExamInternalFrame() {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Examen físico");
		setResizable(false);
		setClosable(true);
		setIconifiable(true);


		weightTextField = new JTextField();
		//weightTextField.setEditable(false);
		//weightTextField.addFocusListener(focusListener );
		
		heightTextField = new JTextField();
		//heightTextField.setEditable(false);
		//heightTextField.addFocusListener(focusListener );
		
		pulseTextField = new JTextField();
		//pulseTextField.setEditable(false);
		//pulseTextField.addFocusListener(focusListener );
		
		tempeTextField = new JTextField();
		//tempeTextField.setEditable(false);
		//tempeTextField.addFocusListener(focusListener );
		
		respiratoryFTextField = new JTextField();
		//respiratoryFTextField.setEditable(false);
		//frTextField.addFocusListener(focusListener );
		
		bloodPressureU = new JTextField();
		//frTextField.addFocusListener(focusListener );
		
		bloodPressureD = new JTextField();
		//frTextField.addFocusListener(focusListener );
		
		imcTextField = new JTextField();
		//frTextField.addFocusListener(focusListener );
		
		handednessComboBox = new JComboBox<> (new Character[] {'D', 'Z', 'A'});
		
		diagnosticsText = new JTextArea();
        diagnosticsText.setColumns(40);
        diagnosticsText.setRows(9);
	    // Se define el salto de linea automático
        // (cuando llega al final del JTextArea hace un salto de línea)
        diagnosticsText.setLineWrap(true);

        //Recomendations 
		recomendationsText = new JTextArea();
		recomendationsText.setColumns(40);
		recomendationsText.setRows(9);
		recomendationsText.setLineWrap(true);
		
		//Conclusion
		conclusionsText = new JTextArea();
		conclusionsText.setColumns(40);
		conclusionsText.setRows(9);
		conclusionsText.setLineWrap(true);
		
		scrollDiagnosticsText = new JScrollPane(diagnosticsText);
		scrollConclusionsText = new JScrollPane(conclusionsText);
		scrollRecomendationsText = new JScrollPane(recomendationsText);
		
		JPanel dataPhysicalPanel1 = new JPanel(new GridLayout(3,6));
		dataPhysicalPanel1.add(new JLabel("Peso(Kg)"));
		dataPhysicalPanel1.add(weightTextField);
		dataPhysicalPanel1.add(new JLabel("Talla(Cm)"));
		dataPhysicalPanel1.add(heightTextField);
		
		
		//JPanel dataPhysicalPanel2  = new JPanel(new GridLayout(2,2));
		dataPhysicalPanel1.add(new JLabel("Pulso / Min"));
		dataPhysicalPanel1.add(pulseTextField);
		dataPhysicalPanel1.add(new JLabel("Temperatura "));
		dataPhysicalPanel1.add(tempeTextField);

		
//		JPanel dataPhysicalPanel3 = new JPanel(new GridLayout(5, 2));
		dataPhysicalPanel1.add(new JLabel("F.R / Min"));
		dataPhysicalPanel1.add(respiratoryFTextField);
		dataPhysicalPanel1.add(new JLabel("T.A. Sentado (MMHG)"));
		dataPhysicalPanel1.add(bloodPressureU);
		dataPhysicalPanel1.add(new JLabel("T.A. Acostado (MMHG)"));
		dataPhysicalPanel1.add(bloodPressureD);
		dataPhysicalPanel1.add(new JLabel("Mano dominante"));
		dataPhysicalPanel1.add(handednessComboBox);
		dataPhysicalPanel1.add(new JLabel("IMC"));
		dataPhysicalPanel1.add(imcTextField);

		
		JPanel partiesInputPanel = new JPanel();
		partiesInputPanel.setLayout(new BoxLayout(partiesInputPanel, BoxLayout.Y_AXIS));
		partiesInputPanel.add(dataPhysicalPanel1);
		//partiesInputPanel.add(dataPhysicalPanel2);
		//partiesInputPanel.add(dataPhysicalPanel3);
		
		//Anomally Panel
		List<String> anomalies = new ArrayList<>();
		JList anomaliasAgregadas = new JList();
		scrollAnomalyList = new JScrollPane(anomaliasAgregadas);
		JPanel anomalyPanel = new JPanel();
		JPanel panelBotones = new JPanel();
//		anomalyPanel.setBorder(BorderFactory.createTitledBorder("Anomalias"));
//		JList anomaliasAgregadas = new JList<String>();

		try {
			anomalies = MedicalAnomalyPersistence.loadMedicalAnomalyTypes();
			anomalyPanel.setLayout(new BorderLayout());
//			add(panelCentral, BorderLayout.CENTER);
			panelBotones.setLayout(new GridLayout(0,3));
//			JButton boton = new JButton(anomalies.get(1).getName()) ;
//			anomalyPanel.add(boton);
			for(int x=0; x<=anomalies.size()-1; x++) {
				JButton boton = new JButton(anomalies.get(x));
				panelBotones.add(boton);
			}
			anomalyPanel.add(panelBotones, BorderLayout.WEST);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		JPanel panel = new JPanel(new BorderLayout());
//		JPanel panel = new JPanel();
//		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		anomalyPanel.setBorder(BorderFactory.createTitledBorder("Anomalias"));
//		panel.add(anomalyPanel, BorderLayout.WEST);
		anomalyPanel.add(anomaliasAgregadas, BorderLayout.CENTER);
		

		//Diagnostics Panel
		JPanel diagnosticsPanel = new JPanel(new GridLayout(1,1));
		diagnosticsPanel.add(scrollDiagnosticsText);
		diagnosticsPanel.setBorder(BorderFactory.createTitledBorder("Diagnostico"));

		//Conclusions Panel
		JPanel conclusionPanel = new JPanel(new GridLayout(1,1));
		conclusionPanel.add(scrollConclusionsText);
		conclusionPanel.setBorder(BorderFactory.createTitledBorder("Conclusiones"));
		
		JPanel recomendationsPanel = new JPanel(new GridLayout(1, 1));
		recomendationsPanel.add(scrollRecomendationsText);
		recomendationsPanel.setBorder(BorderFactory.createTitledBorder("Recomendaciones"));

		
		// Content pane
//		JPanel contentPane = new JPanel(new GridLayout(6, 1));
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
//		contentPane.add(dataPhysicalPanel1);
//		contentPane.add(dataPhysicalPanel2);
//		contentPane.add(dataPhysicalPanel3);
		contentPane.add(partiesInputPanel);
		contentPane.add(anomalyPanel);
		contentPane.add(diagnosticsPanel);
		contentPane.add(conclusionPanel);
		contentPane.add(recomendationsPanel);
		contentPane.setBorder(BorderFactory.createTitledBorder("Examen Fisico"));
		setContentPane(contentPane);
		
		pack();
	}

}
