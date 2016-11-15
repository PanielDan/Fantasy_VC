package guis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.Client;
import utility.AppearanceConstants;
import utility.AppearanceSettings;
import utility.Constants;

public class TimelapsePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Client client;
	private JList<String> notifications;
	private Vector<String> notificationList;
	private ImageIcon animation;
	private JLabel animationLabel;
	
	/**
	 * Create the panel.
	 */
	public TimelapsePanel(Client client) {
		this.client = client;
		
		initializeComponents();
		createGUI();
	}

	private void initializeComponents() {
//		notifications = new JTextArea(40, 40);
		notifications = new JList<String>();
		notificationList = new Vector<String>();
	    animation = new ImageIcon(Constants.images + "animation" + Constants.gif);
	    animationLabel = new JLabel(animation);
	}
	
	private void createGUI() {

		setSize(1280, 504);
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setBackground(AppearanceConstants.lightBlue);
		notifications.setBackground(AppearanceConstants.darkBlue);
		notifications.setForeground(AppearanceConstants.offWhite);
		
		JScrollPane notificationPanel = new JScrollPane(notifications);
		
		JPanel centerPane = new JPanel();
		//centerPane.setBackground(new Color(128, 128, 128));
		add(centerPane, BorderLayout.CENTER);
				
		AppearanceSettings.setSize(600, 450, notificationPanel);
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, notificationPanel, notifications);
		AppearanceSettings.setForeground(AppearanceConstants.offWhite, notificationPanel, notifications);

		
		add(notificationPanel);
		
	}
	
	
	public void appendNotification(String message) {
		
	}
}
