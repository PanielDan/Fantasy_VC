package guis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import gameplay.GameFrame;
import gameplay.User;
import messages.LeaveLobbyMessage;
import messages.LobbyPlayerReadyMessage;
import utility.AppearanceConstants;

public class LobbyPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Vector<User> players;
	JLabel statusLabel, firmLabel;
	JTextField firmField;
	JButton readyButton, inviteButton, leaveButton;
	public GameFrame gameFrame;
	
	public LobbyPanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		initializeComponents();
		createGUI();
		addEvents();
	}
	
	private void initializeComponents() {
		statusLabel = new JLabel("Waiting for 1 more player to join...");
		firmLabel = new JLabel("Firm Name");
		firmField = new JTextField();
		readyButton = new JButton("Ready");
		inviteButton = new JButton("Invite");
		leaveButton = new JButton("Leave");
		
	}
	private void createGUI() {
		this.setBackground(AppearanceConstants.lightBlue);
		this.setLayout(new BorderLayout());
		
		JPanel eastPanel = new JPanel();
		eastPanel.setBackground(AppearanceConstants.offWhite);
		eastPanel.setLayout(new BorderLayout());
		eastPanel.setPreferredSize(new Dimension(400, 0));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setBorder(new EmptyBorder(30,60,30,60));
		buttonPanel.setLayout(new GridLayout(3, 1, 30, 30));
		makeButton(readyButton, inviteButton, leaveButton);
		buttonPanel.add(readyButton);
		buttonPanel.add(inviteButton);
		buttonPanel.add(leaveButton);
		
		eastPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		JPanel firmPanel = new JPanel();
		firmPanel.setBorder(new EmptyBorder(50, 50, 0, 50));
		firmPanel.setOpaque(false);
		firmPanel.setLayout(new GridLayout(2,1,10,10));
		
		firmLabel.setHorizontalAlignment(JLabel.CENTER);
		firmLabel.setFont(new Font("Arial", Font.BOLD, 32));
		firmLabel.setForeground(AppearanceConstants.darkGray);
		firmPanel.add(firmLabel);
		
		firmField.setFont(new Font("Arial", Font.BOLD, 32));
		firmField.setForeground(Color.black);
		firmField.setBorder(new EmptyBorder(10, 5, 10, 5));
		firmPanel.add(firmField);
		
		eastPanel.add(firmPanel, BorderLayout.NORTH);
		
		this.add(eastPanel, BorderLayout.EAST);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setOpaque(false);
				
		JPanel memberPanel = new JPanel();
		memberPanel.setLayout(new BoxLayout(memberPanel, BoxLayout.Y_AXIS));
		memberPanel.setOpaque(false);
		memberPanel.setBorder(new EmptyBorder(20,40,0,40));
		
		for(int i = 0; i < 2; i++) {
			addPlayer(memberPanel, Integer.toString(i), Integer.toString(i));
		}
		
		JScrollPane scrollPane = new JScrollPane(memberPanel);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(860, 400));
		centerPanel.add(scrollPane, BorderLayout.NORTH);
		
		statusLabel.setBorder(new EmptyBorder(20, 20, 20, 20));
		statusLabel.setForeground(AppearanceConstants.offWhite);
		statusLabel.setFont(new Font("Arial", Font.ITALIC, 28));
		centerPanel.add(statusLabel, BorderLayout.SOUTH);
		
		this.add(centerPanel);
		
	}
	private void addEvents() {
		readyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				LobbyPlayerReadyMessage lprm = new LobbyPlayerReadyMessage();
			}
		});
				
		leaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				LeaveLobbyMessage llm = new LeaveLobbyMessage();
				//TODO must send this out.
				gameFrame.changePanel(new IntroPanel(gameFrame));
			}
		});
				
	}
	
	public void makeButton(JButton... button) {
		for (JButton b : button) {
			b.setBackground(AppearanceConstants.darkGray);
			b.setForeground(AppearanceConstants.offWhite);
			b.setFont(new Font("Arial", Font.BOLD, 32));
			b.setBorder(null);
		}
	}
	
	public void addPlayer(JPanel member, String name, String firm) {
		JPanel playerPanel = new JPanel();
		playerPanel.setOpaque(false);
		playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.X_AXIS));
		JLabel username = new JLabel("Jeffrey");
		username.setFont(new Font("Arial", Font.PLAIN, 28));
		username.setForeground(AppearanceConstants.offWhite);
		JLabel firmName = new JLabel(firm);
		firmName.setFont(new Font("Arial", Font.PLAIN, 28));
		firmName.setForeground(AppearanceConstants.offWhite);
		JLabel ready = new JLabel("ready");		
		ready.setFont(new Font("Arial", Font.PLAIN, 28));
		ready.setForeground(Color.green);
		playerPanel.add(username);
		playerPanel.add(Box.createHorizontalGlue());
		playerPanel.add(firmName);
		playerPanel.add(Box.createHorizontalGlue());
		playerPanel.add(ready);
		playerPanel.setBorder(new EmptyBorder(15,40,15,40));
		member.add(playerPanel);
		//member.add(Box.createRigidArea(new Dimension(0,15)));
		JSeparator separator = new JSeparator();
		separator.setBackground(AppearanceConstants.offWhite);
		separator.setBorder(null);
//		member.add(separator);
		//member.add(Box.createRigidArea(new Dimension(0,15)));
		
	}

}
