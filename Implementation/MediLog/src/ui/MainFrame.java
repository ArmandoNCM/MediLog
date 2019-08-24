package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import ui.exam.InformedConsentRegistrationInternalFrame;
import ui.exam.PhysicalExamInternalFrame;
import ui.registration.ClientRegistrationInternalFrame;
import ui.work_concept.PeriodicConceptInternalFrame;
import ui.work_concept.RetireConceptInternalFrame;
import ui.work_concept.WorkConceptInternalFrame;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String ACTION_REGISTER_CLIENT = "ACTION_REGISTER_CLIENT";
	
	private static final String ACTION_REGISTER_INFORMED_CONSENT = "ACTION_REGISTER_INFORMED_CONSENT";
	
	
	private static final String ACTION_REGISTER_PHYSICAL_CHECK = "ACTION_REGISTER_PHYSICAL_CHECK";
	
	private static final String ACTION_REGISTER_WORK_CONCEPT = "ACTION_REGISTER_WORK_CONCEPT";
	
	private static final String ACTION_REGISTER_RETIRE_CONCEPT = "ACTION_REGISTER_RETIRE_CONCEPT";
	
	private JDesktopPane desktopPane;
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			
			JInternalFrame internalFrame;
			switch (event.getActionCommand()) {
				
				case ACTION_REGISTER_CLIENT:
					internalFrame = new ClientRegistrationInternalFrame();
					internalFrame.setVisible(true);
					desktopPane.add(internalFrame);
					break;
	
				case ACTION_REGISTER_INFORMED_CONSENT:
					internalFrame = new InformedConsentRegistrationInternalFrame();
					internalFrame.setVisible(true);
					desktopPane.add(internalFrame);
					break;
					
				case ACTION_REGISTER_PHYSICAL_CHECK:
					internalFrame = new PhysicalExamInternalFrame();
					internalFrame.setVisible(true);
					desktopPane.add(internalFrame);
					break;
					
				case ACTION_REGISTER_WORK_CONCEPT:
					internalFrame = new WorkConceptInternalFrame();
					internalFrame.setVisible(true);
					desktopPane.add(internalFrame);
					break;
					
				case ACTION_REGISTER_RETIRE_CONCEPT:
					internalFrame = new RetireConceptInternalFrame();
					internalFrame.setVisible(true);
					desktopPane.add(internalFrame);
					break;
			}
			
		}
	};

	

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		
		// Session
		JMenu sessionMenu = new JMenu("Sesión");
		menuBar.add(sessionMenu);
		
		JMenuItem logInMenuItem = new JMenuItem("Iniciar sesión");
		sessionMenu.add(logInMenuItem);
		
		// Registry
		JMenu registryMenu = new JMenu("Registro");
		menuBar.add(registryMenu);
		
		JMenuItem registerClientMenuItem = new JMenuItem("Registrar cliente");
		registerClientMenuItem.setActionCommand(ACTION_REGISTER_CLIENT);
		registerClientMenuItem.addActionListener(actionListener);
		registryMenu.add(registerClientMenuItem);
		
		JMenuItem registerEmployeeMenuItem = new JMenuItem("Registrar empleado");
		registryMenu.add(registerEmployeeMenuItem);
		
		// Exams
		JMenu examsMenu = new JMenu("Exámenes");
		menuBar.add(examsMenu);
		
		JMenuItem informedConsentMenuItem = new JMenuItem("Consentimiento informado");
		examsMenu.add(informedConsentMenuItem);
		informedConsentMenuItem.setActionCommand(ACTION_REGISTER_INFORMED_CONSENT);
		informedConsentMenuItem.addActionListener(actionListener);
		
		JMenuItem physicalExamMenuItem = new JMenuItem("Exámen físico");
		examsMenu.add(physicalExamMenuItem);
		physicalExamMenuItem.setActionCommand(ACTION_REGISTER_PHYSICAL_CHECK);
		physicalExamMenuItem.addActionListener(actionListener);
		
		JMenuItem workConceptMenuItem = new JMenuItem("Examen de ingreso");
		examsMenu.add(workConceptMenuItem);
		workConceptMenuItem.setActionCommand(ACTION_REGISTER_WORK_CONCEPT);
		workConceptMenuItem.addActionListener(actionListener);
		
		JMenuItem periodicConceptMenuItem = new JMenuItem("Examen de egreso");
		examsMenu.add(periodicConceptMenuItem);
		periodicConceptMenuItem.setActionCommand(ACTION_REGISTER_RETIRE_CONCEPT);
		periodicConceptMenuItem.addActionListener(actionListener);
		
		setJMenuBar(menuBar);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBorder(BorderFactory.createTitledBorder("SAO S.A.S."));
		desktopPane.setBackground(getContentPane().getBackground());
		
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.add(desktopPane, BorderLayout.CENTER);
		
		setContentPane(contentPane);
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle bounds = env.getMaximumWindowBounds();
		setBounds(bounds);
		setResizable(false);

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
