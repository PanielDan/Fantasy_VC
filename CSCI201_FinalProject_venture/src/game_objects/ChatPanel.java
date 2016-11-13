/**
 * 
 */
package game_objects;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.Client;

/**
 * @author alancoon
 *
 */
public class ChatPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;
	private Client client;
	private static JTextArea chat;
	private JTextField input;
	private JButton submit;
	
	public ChatPanel(Client client) { 
		this.client = client;
		this.user = client.getUser();
		initializeComponents();
		createGUI();
		addEvents();
	}


	private void initializeComponents() {
		chat = new JTextArea();
		input = new JTextField();
		submit = new JButton();
	}
	
	private void createGUI() {
		
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
