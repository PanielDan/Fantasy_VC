package guis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import client.Client;
import gameplay.GameFrame;
import listeners.DisabledItemSelectionModel;
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
	private JLabel animationLabel, notificationLabel;
	public GameFrame gameFrame;
	
	/**
	 * Create the panel.
	 */
	public TimelapsePanel(Client client, GameFrame gameFrame) {
		this.client = client;
		this.gameFrame = gameFrame;
		initializeComponents();
		createGUI();
		addActionListeners();
		
		
	}
	

	private void initializeComponents() {
		notificationList = new Vector<String>();
		
		//set notificationList in NOT NETWORKED game
		if(!gameFrame.networked) {
			notificationList = gameFrame.game.updateNonNetworkedCompanies();
			gameFrame.game.incrementQuarter();
		}
		
		//test code
		/*
		for ( int i = 0; i < 30; i++){
			notificationList.add("Notification " + Integer.toString(i));
		}
		*/
		
		notifications = new JList<String>(notificationList);
		notificationLabel = new JLabel("Notifications");
		
	    animation = new ImageIcon(Constants.images + "animation" + Constants.gif);
	    animationLabel = new JLabel(animation);
	}
	
	private void createGUI() {

		setSize(1280, 504);
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setBackground(AppearanceConstants.lightBlue);
				
		JScrollPane listPane = new JScrollPane(notifications);
		JPanel leftPanel = new JPanel();
		JPanel notificationPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		notificationPanel.setLayout(new BorderLayout());
		
		//Set borders
		listPane.setBorder(null);
		notifications.setBorder(new EmptyBorder(5,5,5,5));
		notificationLabel.setBorder(new EmptyBorder(10,0,0,0));
				
		AppearanceSettings.setSize(500, 400, notificationPanel);
		notificationPanel.setMaximumSize(new Dimension(500,400));
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, notificationPanel, notifications,
				leftPanel, listPane);
		AppearanceSettings.setForeground(AppearanceConstants.offWhite, listPane, notifications,
				notificationLabel);
		AppearanceSettings.setFont(AppearanceConstants.fontLarge, notificationLabel);
		AppearanceSettings.setCenterAlignment(notificationLabel);
		notifications.setFont(AppearanceConstants.fontSmall);

		//LeftPanel Adding
		leftPanel.add(notificationLabel);
		leftPanel.add(new JSeparator(JSeparator.HORIZONTAL));

		notificationPanel.add(leftPanel, BorderLayout.NORTH);
		notificationPanel.add(listPane, BorderLayout.CENTER);

		//Adding to normal panel
		add(Box.createHorizontalStrut(50));
		add(notificationPanel);
		add(Box.createGlue());
		add(animationLabel);
		add(Box.createHorizontalStrut(50));
	}
	
	public void addActionListeners(){
		notifications.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		notifications.setSelectionModel(new DisabledItemSelectionModel());
	}
	
	public void appendNotification(String message) {
		
	}
}
