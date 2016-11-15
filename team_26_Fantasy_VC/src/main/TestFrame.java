package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import guis.AuctionTeamList;

public class TestFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1000;

	public TestFrame(){
		super("Test Frame");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1280,720);
		
		JPanel header = new JPanel();
		JPanel chatbox = new JPanel();
		AuctionTeamList main = new AuctionTeamList();
		//AuctionBidScreen main = new AuctionBidScreen();
		
		header.setPreferredSize(new Dimension(1280,72));
		chatbox.setPreferredSize(new Dimension(1280,144));
		
		header.setBackground(Color.CYAN);
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
