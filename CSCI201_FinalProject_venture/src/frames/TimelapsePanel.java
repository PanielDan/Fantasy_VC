package frames;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import client.Client;
import game_objects.ChatPanel;
import game_objects.TopPanel;
import util.Constants;

public class TimelapsePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Client client;
	private JTextPane notifications;
	
	private ImageIcon animation;
	private JLabel animationLabel;
	
	private TopPanel topPanel;
	private ChatPanel chatPanel;
	
	/**
	 * Create the panel.
	 */
	public TimelapsePanel(Client client) {
		this.client = client;
		
		initializeComponents();
		createGUI();
		colorizeComponents();
	}

	private void initializeComponents() {
//		notifications = new JTextArea(40, 40);
		notifications = new JTextPane();
	    animation = new ImageIcon(this.getClass().getResource(Constants.images + "animation" + Constants.gif));
	    animationLabel = new JLabel(animation);
	    topPanel = new TopPanel(client.getUser());
	    chatPanel = new ChatPanel(client);
	}

	private void colorizeComponents() {
		// TODO change once we get the AppearanceSettings/Constants up and running
		Color lighterBlue = new Color(49, 71, 112);
		Color darkBlue = new Color(49, 59, 71);
		Color offWhite = new Color(221, 221, 221);
		
		notifications.setBackground(lighterBlue);
	}
	private void createGUI() {

		setSize(1280, 720);
		setLayout(new BorderLayout());
		add(topPanel);
		add(chatPanel);
		
		JPanel centerPane = new JPanel();
		centerPane.setBackground(new Color(128, 128, 128));
		add(centerPane, BorderLayout.CENTER);
		
		notifications.setText("notifications n' shit");
		
		GroupLayout gl_centerPane = new GroupLayout(centerPane);
		gl_centerPane.setHorizontalGroup(
			gl_centerPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_centerPane.createSequentialGroup()
					.addGap(32)
					.addComponent(notifications, GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
					.addGap(27)
					.addComponent(animationLabel, GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
					.addGap(29))
		);
		gl_centerPane.setVerticalGroup(
			gl_centerPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_centerPane.createSequentialGroup()
					.addGap(67)
					.addGroup(gl_centerPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(animationLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(notifications, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
					.addGap(149))
		);
		centerPane.setLayout(gl_centerPane);
		
		// Modify the news box:
		notifications.setEnabled(false);
		
	}
	
	
	public void appendNotification(String message) {
		
	}
}
