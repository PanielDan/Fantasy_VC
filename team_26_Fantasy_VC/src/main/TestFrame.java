package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import client.Client;
import gameplay.User;
import guis.AuctionBidScreen;
import guis.ChatPanel;
import guis.TopPanel;

public class TestFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1000;
	public static Boolean networked;
	
	public TestFrame() {
		super("Test Frame");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1280,720);
		User user = new User(1, "Tim Lewd", "test");
		networked = true;
		Client client = null;
		
		JPanel header = new TopPanel(null,user);
		JPanel chatbox = new ChatPanel(user);

		//AuctionTeamList main = new AuctionTeamList(null);
		AuctionBidScreen main = new AuctionBidScreen();
		//TimelapsePanel main = new TimelapsePanel(null);
		
		header.setPreferredSize(new Dimension(1280,72));
		chatbox.setPreferredSize(new Dimension(1280,144));
		
		chatbox.setBackground(Color.GRAY);
		
		add(header, BorderLayout.NORTH);
		add(chatbox, BorderLayout.SOUTH);
		add(main, BorderLayout.CENTER);
		
		setVisible(true);
	}

	public static void main(String args[]){
		new TestFrame();
	}
}
