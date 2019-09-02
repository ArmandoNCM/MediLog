package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import character_values.EmployeeRole;
import character_values.ValueHoldingEnum;
import entities.Employee;
import entities.InformedConsent;
import permissions.PermissionHelper;
import session.SessionHelper;
import session.SessionLogInListener;
import ui.exam.PhysicalExamInternalFrame;
import ui.informed_consent.InformedConsentRegistrationInternalFrame;
import ui.informed_consent.InformedConsentSelectionInternalFrame;
import ui.login.SessionLogInDialog;
import ui.registration.ClientRegistrationInternalFrame;
import ui.registration.EmployeeRegistrationInternalFrame;
import ui.work_concept.RetireConceptInternalFrame;
import ui.work_concept.WorkConceptInternalFrame;
import util.Pair;

public class MainFrame extends JFrame implements SessionLogInListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5620602976525288429L;

	private static final String ACTION_REGISTER_CLIENT = "ACTION_REGISTER_CLIENT";
	
	private static final String ACTION_REGISTER_EMPLOYEE = "ACTION_REGISTER_EMPLOYEE";
	
	private static final String ACTION_REGISTER_INFORMED_CONSENT = "ACTION_REGISTER_INFORMED_CONSENT";
	
	private static final String ACTION_REGISTER_PHYSICAL_CHECK = "ACTION_REGISTER_PHYSICAL_CHECK";
	
	private static final String ACTION_REGISTER_WORK_CONCEPT = "ACTION_REGISTER_WORK_CONCEPT";
	
	private static final String ACTION_REGISTER_RETIRE_CONCEPT = "ACTION_REGISTER_RETIRE_CONCEPT";
	
	private final List<Pair<JComponent, Set<EmployeeRole>>> restrictableComponentsPermissions = new ArrayList<>();
	
	private JMenuBar menuBar;
	
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
					
				case ACTION_REGISTER_EMPLOYEE:
					internalFrame = new EmployeeRegistrationInternalFrame();
					internalFrame.setVisible(true);
					desktopPane.add(internalFrame);
					break;
	
				case ACTION_REGISTER_INFORMED_CONSENT:
					internalFrame = new InformedConsentRegistrationInternalFrame();
					internalFrame.setVisible(true);
					desktopPane.add(internalFrame);
					break;
					
				case ACTION_REGISTER_PHYSICAL_CHECK:
					internalFrame = new InformedConsentSelectionInternalFrame(new InformedConsentSelectionInternalFrame.SelectionListener() {
						
						@Override
						public void onInformedConsentSelected(InformedConsent informedConsent) {

							JInternalFrame physicalExamInternalFrame = new PhysicalExamInternalFrame(informedConsent);
							physicalExamInternalFrame.setVisible(true);
							desktopPane.add(physicalExamInternalFrame);
						}
					});
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
		
		menuBar = new JMenuBar();
		menuBar.setVisible(false);
		setJMenuBar(menuBar);
		
		// Registry
		JMenu registryMenu = new JMenu("Registro");
		restrictableComponentsPermissions.add(buildPair(registryMenu, PermissionHelper.EMPLOYEES));
		menuBar.add(registryMenu);
		
		JMenuItem registerClientMenuItem = new JMenuItem("Registrar cliente");
		registerClientMenuItem.setActionCommand(ACTION_REGISTER_CLIENT);
		registerClientMenuItem.addActionListener(actionListener);
		restrictableComponentsPermissions.add(buildPair(registerClientMenuItem, PermissionHelper.EMPLOYEES));
		registryMenu.add(registerClientMenuItem);
		
		JMenuItem registerEmployeeMenuItem = new JMenuItem("Registrar empleado");
		restrictableComponentsPermissions.add(buildPair(registerEmployeeMenuItem, PermissionHelper.ADMINISTRATORS_ONLY));
		registerEmployeeMenuItem.setActionCommand(ACTION_REGISTER_EMPLOYEE);
		registerEmployeeMenuItem.addActionListener(actionListener);
		registryMenu.add(registerEmployeeMenuItem);
		
		// Exams
		JMenu examsMenu = new JMenu("Examenes");
		restrictableComponentsPermissions.add(buildPair(examsMenu, PermissionHelper.EMPLOYEES));
		menuBar.add(examsMenu);
		
		JMenuItem informedConsentMenuItem = new JMenuItem("Consentimiento informado");
		examsMenu.add(informedConsentMenuItem);
		informedConsentMenuItem.setActionCommand(ACTION_REGISTER_INFORMED_CONSENT);
		informedConsentMenuItem.addActionListener(actionListener);
		
		JMenuItem physicalExamMenuItem = new JMenuItem("Examen físico");
		examsMenu.add(physicalExamMenuItem);
		physicalExamMenuItem.setActionCommand(ACTION_REGISTER_PHYSICAL_CHECK);
		physicalExamMenuItem.addActionListener(actionListener);
		
//		JMenuItem workConceptMenuItem = new JMenuItem("Examen de ingreso");
//		examsMenu.add(workConceptMenuItem);
//		workConceptMenuItem.setActionCommand(ACTION_REGISTER_WORK_CONCEPT);
//		workConceptMenuItem.addActionListener(actionListener);
//		
//		JMenuItem periodicConceptMenuItem = new JMenuItem("Examen de egreso");
//		examsMenu.add(periodicConceptMenuItem);
//		periodicConceptMenuItem.setActionCommand(ACTION_REGISTER_RETIRE_CONCEPT);
//		periodicConceptMenuItem.addActionListener(actionListener);
		
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
	
	@Override
	public void onLoggedIn() {
		Employee employee = SessionHelper.getInstance().getEmployee();
		if (employee != null) {
			EmployeeRole role = (EmployeeRole) ValueHoldingEnum.getByValue(EmployeeRole.values(), (char) employee.getRole());
			
			for (Pair<JComponent, Set<EmployeeRole>> item : restrictableComponentsPermissions) {
				item.getKey().setVisible(PermissionHelper.permissionGranted(role, item.getValue()));
			}
			
			menuBar.setVisible(true);
		}
	}
	
	private Pair<JComponent, Set<EmployeeRole>> buildPair(JComponent component, Set<EmployeeRole> permissions) {
		return new Pair<JComponent, Set<EmployeeRole>>(component, permissions);
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
					JDialog logInDialog = new SessionLogInDialog(frame);
					logInDialog.setLocationRelativeTo(frame);
					logInDialog.setLocation((frame.getWidth() / 2) - (logInDialog.getWidth() / 2), (frame.getHeight() / 2) - (logInDialog.getHeight() / 2));
					logInDialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
