package ui.work_concept;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class PostExamInternalFrame extends JInternalFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton addButton;
	private JButton deleteButton;

	public PostExamInternalFrame() {
		// TODO Auto-generated constructor stub
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Conducta");
		setResizable(false);
		setClosable(true);
		setIconifiable(true);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
		addButton = new JButton(">>");
		deleteButton = new JButton("<<");
		buttonsPanel.add(addButton);
		buttonsPanel.add(deleteButton);
		
		JPanel contentPane = new JPanel(new GridLayout(1,2));
//		List<PostExamAction> actions = new ArrayList();
//		List<String> addedActions = new ArrayList();
		
		
		
		
		
	}

}
