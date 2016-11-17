package gameplay;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import client.Client;
import guis.AuctionTeamList;
import guis.ChatPanel;
import guis.TopPanel;
import listeners.ExitWindowListener;

public class GameFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	public static Boolean networked;
	

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
		
		JPanel header = new TopPanel(guest);
		JPanel chatbox = new ChatPanel(guest);
		
		header.setPreferredSize(new Dimension(1280,72));
		chatbox.setPreferredSize(new Dimension(1280,144));
		
		AuctionTeamList main = new AuctionTeamList(null);

		chatbox.setBackground(Color.GRAY);
		
		add(header, BorderLayout.NORTH);
		add(chatbox, BorderLayout.SOUTH);
		add(main, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public GameFrame(Client client) {
		super("Venture Capital");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new ExitWindowListener(this, client));
		setSize(1280,720);
		
		User user = client.getUser();
		networked = true;
		
		JPanel header = new TopPanel(client);
		JPanel chatbox = new ChatPanel(client);

		header.setPreferredSize(new Dimension(1280,72));
		chatbox.setPreferredSize(new Dimension(1280,144));
		
		AuctionTeamList main = new AuctionTeamList(null);
		
		chatbox.setBackground(Color.GRAY);
		
		add(header, BorderLayout.NORTH);
		add(chatbox, BorderLayout.SOUTH);
		add(main, BorderLayout.CENTER);
		
		setVisible(true);
	}

}
