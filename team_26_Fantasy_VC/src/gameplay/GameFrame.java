package gameplay;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import client.Client;
import guis.AuctionBidScreen;
import guis.ChatPanel;
import guis.IntroPanel;
import guis.TopPanel;
import listeners.ExitWindowListener;

public class GameFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	public static Boolean networked;
	public Game game;
	public JPanel currentPanel;
	public TopPanel header;
	public User user;
	public ChatPanel chatbox;

	/**
	 * Single player.
	 * @param guest A guest {@code User} instantiation.
	 */
	public GameFrame(User guest) {
		super("Venture Capital – Guest Mode");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new ExitWindowListener(this));
		setSize(1280, 720);
		
		networked = false;
		game = new Game();
		user = guest;
		game.addUser(guest);
		game.addUser(new User(0,"Guest 1","Hello"));
		
		header = new TopPanel(this, guest);
		chatbox = new ChatPanel(guest);
		
		header.setPreferredSize(new Dimension(1280,72));
		chatbox.setPreferredSize(new Dimension(1280,144));
		
		AuctionBidScreen main = new AuctionBidScreen(this,game.getCompanies().get(5));
		//AuctionTeamList main = new AuctionTeamList(null, this);
		//FinalGUI main = new FinalGUI(this);
		
		add(header, BorderLayout.NORTH);
		add(chatbox, BorderLayout.SOUTH);
		add(main, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public GameFrame(Client client) {
		super("Venture Capital Online");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new ExitWindowListener(this, client));
		setSize(1280,720);
		
		User user = client.getUser();
		networked = true;
		
		JPanel header = new TopPanel(this, client);
		JPanel chatbox = new ChatPanel(client);

		header.setPreferredSize(new Dimension(1280,72));
		chatbox.setPreferredSize(new Dimension(1280,144));
		
		IntroPanel main = new IntroPanel();

		currentPanel = main;
		
		chatbox.setBackground(Color.GRAY);
		
		add(header, BorderLayout.NORTH);
		add(chatbox, BorderLayout.SOUTH);
		add(currentPanel, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	
	public void changePanel(JPanel panel) {
		remove(currentPanel);
		currentPanel = panel;
		add(currentPanel, BorderLayout.CENTER);
		// must repaint or the change won't show
		repaint();
		revalidate();
	}
	
	public Game getGame() {
		return game;
	}

}
