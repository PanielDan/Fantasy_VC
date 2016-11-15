package guis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import utility.AppearanceConstants;

public class CreateGameGUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel createLabel, lobbyLabel, sizeLabel, warningLabel;
	JButton cancelButton, createButton;
	JTextField lobbyName;
	JComboBox<Integer> size;
	
	CreateGameGUI() {
		initializeComponents();
		createGUI();
		addEvents();
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new CreateGameGUI().setVisible(true);
	}
	
	private void initializeComponents() {
		createLabel = new JLabel("Create Game");
		lobbyLabel = new JLabel("Lobby Name");
		sizeLabel = new JLabel("Lobby Size");
		warningLabel = new JLabel("Warning Text");
		
		cancelButton = new JButton("Cancel");
		createButton = new JButton("Create");
		
		lobbyName = new JTextField();
		
		Integer [] ints = {2, 3, 4};
		size = new JComboBox<Integer>(ints);
	}
	
	private void createGUI() {
		this.setSize(360, 480);
		this.getContentPane().setBackground(AppearanceConstants.lightBlue);
		
		createLabel.setFont(new Font("Arial", Font.BOLD, 42));
		createLabel.setForeground(AppearanceConstants.offWhite);
		createLabel.setHorizontalAlignment(JLabel.CENTER);
		createLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
		
		this.add(createLabel, BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2, 16, 16));
		buttonPanel.setOpaque(false);
		buttonPanel.setBorder(new EmptyBorder(40, 16, 40, 16));
		makeButton(cancelButton, createButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(createButton);
		
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(0, 40, 0, 40));
//		centerPanel.add(size);
		
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		northPanel.setOpaque(false);
		lobbyLabel.setFont(new Font("Arial", Font.PLAIN, 28));
		lobbyLabel.setForeground(AppearanceConstants.offWhite);
		lobbyLabel.setHorizontalAlignment(JLabel.CENTER);
		northPanel.add(lobbyLabel, BorderLayout.NORTH);
		
		lobbyName.setFont(new Font("Arial", Font.PLAIN, 28));
		lobbyName.setBorder(new EmptyBorder(10,4,10,4));
		northPanel.add(lobbyName);
		
		warningLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		warningLabel.setForeground(Color.red);
		warningLabel.setHorizontalAlignment(JLabel.CENTER);
		northPanel.add(warningLabel, BorderLayout.SOUTH);
		
		centerPanel.add(northPanel, BorderLayout.NORTH);
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.setOpaque(false);
		sizeLabel.setFont(new Font("Arial", Font.PLAIN, 28));
		sizeLabel.setForeground(AppearanceConstants.offWhite);
		sizeLabel.setHorizontalAlignment(JLabel.CENTER);
		southPanel.add(sizeLabel, BorderLayout.NORTH);
		
		size.setFont(new Font("Arial", Font.PLAIN, 28));
		size.setBackground(Color.white);
		((JLabel)size.getRenderer()).setBorder(new EmptyBorder(0, 120, 0, 0));
		size.setFocusable(false);
		size.setPreferredSize(new Dimension(0, 50));
		southPanel.add(size);
		
		centerPanel.add(southPanel, BorderLayout.SOUTH);
		
		this.add(centerPanel);
		
		
	}
	
	private void addEvents() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void makeButton(JButton... button) {
		for (JButton b : button) {
			b.setBackground(AppearanceConstants.darkGray);
			b.setForeground(AppearanceConstants.offWhite);
			b.setFont(new Font("Arial", Font.BOLD, 32));
			b.setBorder(new EmptyBorder(8, 0, 8, 0));
		}
	}
}
