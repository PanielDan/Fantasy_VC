package guis;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import gameplay.GameFrame;
import gameplay.Lobby;
import messages.HostGameMessage;
import messages.JoinGameMessage;
import utility.AppearanceConstants;

public class IntroPanel extends JPanel {
	JLabel lobbyLabel, hostLabel, sizeLabel, playerLabel, playerList;
	JButton hostButton, joinButton;
	JPanel eastPanel, centerPanel;
	public GameFrame gameFrame;
	Vector<JButton> lobbyButton;
	IntroPanel ip;
	
	public IntroPanel(GameFrame gameFrame) {
		this.ip = this;
		this.gameFrame = gameFrame;
		initializeComponents();
		createGUI();
		addEvents();
		clearCenterPanel();
		lobbyButton = new Vector<JButton>();
		joinButton.setEnabled(false);
	}
	
	private void initializeComponents() {
		lobbyLabel = new JLabel("Lobby Name");
		hostLabel = new JLabel("Host: Host Name");
		sizeLabel = new JLabel("Game Size: 3");
		playerLabel = new JLabel("Players:");
		playerList = new JLabel();
		hostButton = new JButton("Host");
		joinButton = new JButton("Join");
	}
	
	private void createGUI() {
		this.setBackground(AppearanceConstants.lightBlue);
		this.setLayout(new BorderLayout());
		
		eastPanel = new JPanel();
		eastPanel.setBackground(AppearanceConstants.offWhite);
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setPreferredSize(new Dimension(400,0));
		eastPanel.setBorder(new EmptyBorder(30, 20, 30, 20));
		addToInfo(lobbyLabel);
		addToInfo(hostLabel);
		addToInfo(sizeLabel);
		addToInfo(playerLabel);
		addToInfo(playerList);
		
		JScrollPane infoPane = new JScrollPane(eastPanel);
		infoPane.getViewport().setOpaque(false);
		infoPane.setOpaque(false);
		infoPane.setBorder(null);
		infoPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		this.add(infoPane, BorderLayout.EAST);
		
		JPanel southPanel = new JPanel();
		southPanel.setBackground(AppearanceConstants.mediumGray);
		southPanel.setLayout(new GridLayout(1, 2, 90, 90));
		southPanel.setBorder(new EmptyBorder(20, 90, 20, 90));
		southPanel.setPreferredSize(new Dimension(0, 90));
		
		makeButton(hostButton, joinButton);
		southPanel.add(hostButton);
		southPanel.add(joinButton);
		
		this.add(southPanel, BorderLayout.SOUTH);
		
		centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
		
		JScrollPane lobbyPane = new JScrollPane(centerPanel);
		lobbyPane.getViewport().setOpaque(false);
		lobbyPane.setOpaque(false);
		lobbyPane.setBorder(null);
		lobbyPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		this.add(lobbyPane);
	}
	
	public void switchToLobby() {
		gameFrame.changePanel(new LobbyPanel(gameFrame));
	}
	
	private void addEvents() {
		hostButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				HostGameMessage hgm = new HostGameMessage();
				new CreateGameGUI(ip).setVisible(true);
				
			}
		});
		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JoinGameMessage jgm = new JoinGameMessage(gameFrame.user.getUsername());
				//gameFrame.changePanel(new LobbyPanel(gameFrame));
			}
		});
	}
	
	public void makeButton(JButton... button) {
		for (JButton b : button) {
			b.setBackground(AppearanceConstants.offWhite);
			b.setForeground(AppearanceConstants.darkBlue);
			b.setFont(new Font("Arial", Font.BOLD, 32));
			b.setBorder(null);
		}
	}
	
	public void addToInfo(JComponent jc) {
		jc.setFont(new Font("Arial", Font.BOLD, 32));
		jc.setForeground(AppearanceConstants.darkGray);
		jc.setBorder(new EmptyBorder(10, 0, 10, 0));
		eastPanel.add(jc);
	}
	
	public void addLobby(Lobby lobby) {
		JPanel lobbyPanel = new JPanel();
		lobbyPanel.setLayout(new BoxLayout(lobbyPanel, BoxLayout.X_AXIS));
		lobbyPanel.setBorder(new EmptyBorder(15,40,15,40));
		lobbyPanel.setOpaque(false);
		
		JLabel lobbyName = new JLabel(lobby.getLobbyName());
		lobbyName.setFont(new Font("Arial", Font.BOLD, 32));
		lobbyName.setForeground(AppearanceConstants.offWhite);
		
		lobbyPanel.add(lobbyName);
		lobbyPanel.add(Box.createHorizontalGlue());
		
		JButton selectButton = new JButton("Select");
		selectButton.setForeground(AppearanceConstants.offWhite);
		selectButton.setBackground(AppearanceConstants.darkGray);
		selectButton.setBorder(new EmptyBorder(10,40,10,40));
		selectButton.setFont(new Font("Arial", Font.BOLD, 28));
		selectButton.putClientProperty("lobbyName", lobby);
		selectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				JButton source = (JButton) ae.getSource();
				Lobby lobby = (Lobby)source.getClientProperty("lobbyName");
				lobbyLabel.setText(lobby.getLobbyName());
				hostLabel.setText("Host: " + lobby.getHostName());
				sizeLabel.setText("Game Size: " + lobby.getGameSize());
				String players = "";
				for (String p : lobby.getUsername()){
					players += p+"\n";
				}
				playerList.setText(players);
			}
			
		});
		lobbyButton.add(selectButton);
		
		
		lobbyPanel.add(selectButton);
		
		centerPanel.add(lobbyPanel);
		
//		JSeparator separator = new JSeparator();
//		separator.setBackground(AppearanceConstants.offWhite);
//		separator.setBorder(null);
		//centerPanel.add(separator);
		
	}
	
	public void setLobbies(Vector<Lobby> lobbies) {
		lobbyButton.clear();
		clearCenterPanel();
		
		for(Lobby lobby : lobbies) {
			addLobby(lobby);
		}
		gameFrame.revalidate();
		gameFrame.repaint();
	}

	public void clearCenterPanel() {
		for(Component c : centerPanel.getComponents()) {
			centerPanel.remove(c);
		}
	}
}
