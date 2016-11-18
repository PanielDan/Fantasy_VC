package gameplay;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import client.Client;
import guis.AuctionTeamList;
import guis.ChatPanel;
import guis.IntroPanel;
import guis.TopPanel;
import listeners.ExitWindowListener;

public class GameFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	private Client client;
	public static Boolean networked;
	public Game game;
	public JPanel currentPanel;
	public TopPanel header;
	public User user;
	public ChatPanel chatbox;
	public static Boolean gameInProgress;

	/**
	 * Single player.
	 * @param guest A guest {@code User} instantiation.
	 */
	public GameFrame(User guest) {
		super("Venture Capital Guest Mode");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new ExitWindowListener(this));
		setSize(1280, 720);
		
		networked = false;
		gameInProgress = false;
		
		game = new Game();
		user = guest;
		guest.setCompanyName("Guestbros");
		game.addUser(guest);
		
		header = new TopPanel(this, guest);
		chatbox = new ChatPanel(guest);
		
		header.setPreferredSize(new Dimension(1280,72));
		chatbox.setPreferredSize(new Dimension(1280,144));
		
//		LobbyPanel main = new LobbyPanel(this);
		AuctionTeamList main = new AuctionTeamList(null, this);
		//AuctionBidScreen main = new AuctionBidScreen(this,game.getCompanies().get(5));
		//AuctionTeamList main = new AuctionTeamList(null, this);
		//FinalGUI main = new FinalGUI(this);
		
		
		add(header, BorderLayout.NORTH);
		add(chatbox, BorderLayout.SOUTH);
		add(main, BorderLayout.CENTER);
		
		currentPanel = main;
		setVisible(true);
	}
	
	public GameFrame(Client client) {
		super("Venture Capital Online");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new ExitWindowListener(this, client));
		setSize(1280,720);
		
		this.client = client;
		user = client.getUser();
		networked = true;
		JPanel header = new TopPanel(this, client);
		JPanel chatbox = new ChatPanel(client);

		header.setPreferredSize(new Dimension(1280,72));
		chatbox.setPreferredSize(new Dimension(1280,144));
		
		IntroPanel main = new IntroPanel(this);

		currentPanel = main;
		
		chatbox.setBackground(Color.GRAY);
		
		add(header, BorderLayout.NORTH);
		//add(chatbox, BorderLayout.SOUTH);
		add(currentPanel, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	
	public void changePanel(JPanel panel) {
		remove(currentPanel);
		currentPanel = panel;
		add(currentPanel, BorderLayout.CENTER);
		// must repaint or the change won't show
		revalidate();
		repaint();
	}
	
	public JPanel getCurrentPanel() {
		return currentPanel;
	}
	
	public Game getGame() {
		return game;
	}
	
	public Client getClient() {
		return client;
	}

}
