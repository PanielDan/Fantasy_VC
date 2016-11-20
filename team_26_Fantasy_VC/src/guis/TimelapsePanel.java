package guis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Random;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
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
	private DefaultListModel<String> model;
	private JScrollBar scrollBar;
	
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
			notificationList = gameFrame.game.updateCompanies();
			//increment quarter every time lapse
			gameFrame.game.incrementQuarter();
			System.out.println(gameFrame.game.currentQuarter);
		}
		//TODO in networked game:
		/**
		 *  call game.updateCompanies() to return vector of strings of notifications
		 *  Send that vector and the game with updated companies and user data to all clients
		 *  When you receive that message on the client:
		 *  set notificationList = the vector of strings of notificatons
		 *  set the game = the new game from the message
		 *  call game.incrementQuarter()
		 */
		else {
			//create message here
		}
		
		//test code
		/*
		for ( int i = 0; i < 30; i++){
			notificationList.add("Notification " + Integer.toString(i));
		}
		*/
		
		//notifications = new JList<String>(notificationList);
		model = new DefaultListModel<>();
		notifications = new JList<>( model );
		
		int quarter = gameFrame.game.getQuarter();
		int year = gameFrame.game.getYear();
		
		notificationLabel = new JLabel("Notifications for Q" + quarter + ", " + year);
//		Image i = ImageLibrary.getImage(Constants.images + "graph" + Constants.gif);
//	    animation = new ImageIcon(i.getScaledInstance(400, 300, Image.SCALE_SMOOTH));
		animation = new ImageIcon(Constants.images + "graph2" + Constants.gif);
	    animationLabel = new JLabel(animation);
	}
	
	private void createGUI() {

		setSize(1280, 504);
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setBackground(AppearanceConstants.lightBlue);
				
		JScrollPane listPane = new JScrollPane(notifications);
		listPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listPane.getViewport().setBackground(AppearanceConstants.darkBlue);
		JPanel leftPanel = new JPanel();
		JPanel notificationPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		notificationPanel.setLayout(new BorderLayout());
		
		//Set borders
		listPane.setBorder(null);
		notifications.setBorder(new EmptyBorder(5,5,5,5));
		notificationLabel.setBorder(new EmptyBorder(10,0,0,0));
				
		AppearanceSettings.setSize(700, 400, notificationPanel);
		notificationPanel.setMaximumSize(new Dimension(500,400));
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, notificationPanel, notifications,
				leftPanel, listPane);
		AppearanceSettings.setForeground(AppearanceConstants.offWhite, listPane, notifications,
				notificationLabel);
		AppearanceSettings.setFont(AppearanceConstants.fontLarge, notificationLabel);
		AppearanceSettings.setCenterAlignment(notificationLabel);
		notifications.setFont(AppearanceConstants.fontSmallest);

		//LeftPanel Adding
		leftPanel.add(notificationLabel);
		leftPanel.add(new JSeparator(JSeparator.HORIZONTAL));

		notificationPanel.add(leftPanel, BorderLayout.NORTH);
		notificationPanel.add(listPane, BorderLayout.CENTER);
		scrollBar = listPane.getVerticalScrollBar();

		//Adding to normal panel
		add(Box.createHorizontalStrut(50));
		add(notificationPanel);
		add(Box.createGlue());
		add(animationLabel);
		add(Box.createHorizontalStrut(50));
		
		if(!gameFrame.networked) {
			new TimelapseHelper().start();
		}
		//TODO in non networked game:
		/**
		 * One client should call new TimelapseHelper().start();
		 * and whenever a notification is displayed to timelapse 
		 * a message should be sent to all clients to display that notification
		 * (see timelapsehelper below)
		 */

	}
	
	public void addActionListeners(){
		notifications.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		notifications.setSelectionModel(new DisabledItemSelectionModel());
	}
	
	public void appendNotification(String message) {
		model.addElement(message);
		scrollBar.setValue(scrollBar.getMaximum());
	}
	
	public class TimelapseHelper extends Thread {
		
		public void run() {
			Random rand = new Random();
			
			for(int i = 0; i < notificationList.size(); i++) {
				
				if(!gameFrame.networked) {
					model.addElement(notificationList.get(i));

					scrollBar.setValue(scrollBar.getMaximum());
					try {
						Thread.sleep(rand.nextInt(1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else {
					//TODO
					/**
					 * send a message to all clients telling them
					 * to display this message by doing model.addElement(element from message)
					 * Then sleep for a random amount between 0 and 1000 ms which should
					 * be the same for every client
					 */
				}

			}
			
			//TODO sleep for 4000 ms after all the notifications are sent
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//TODO send a message to all clients about what panel to go to next
			if(gameFrame.game.currentQuarter == 8) {
				gameFrame.changePanel(new FinalGUI(gameFrame, client));
			}
			else {
				gameFrame.changePanel(new QuarterlyGUI(gameFrame, client));
			}
		}
	}
}
