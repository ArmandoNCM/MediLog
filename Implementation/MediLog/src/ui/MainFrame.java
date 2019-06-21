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

import ui.registration.ClientRegistration;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String ACTION_REGISTER_CLIENT = "ACTION_REGISTER_CLIENT";  
	
	private JDesktopPane desktopPane;
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			if (event.getActionCommand().equals(ACTION_REGISTER_CLIENT)) {
				
				JInternalFrame clientRegistrationFrame = new ClientRegistration();
				
				desktopPane.add(clientRegistrationFrame);
				
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
		
		JMenuItem physicalExamMenuItem = new JMenuItem("Exámen físico");
		examsMenu.add(physicalExamMenuItem);
		
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
