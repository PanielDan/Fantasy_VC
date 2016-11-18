package guis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import gameplay.GameFrame;
import messages.InitiateTradeMessage;

public class PlayerTab extends JPanel{
	public JTextArea portfolio;
	public JButton trade;
	public String playerName;
	public ImageIcon playerIcon;
	public JPanel playerInfo;
	public Vector<String> assets;
	public QuarterlyGUI qg;
	public GameFrame gameFrame;
	
	public PlayerTab(String playerName, ImageIcon playerIcon, Vector<String> assets, QuarterlyGUI qg) {
		this.playerName = playerName;
		this.playerIcon = playerIcon;
		this.assets = assets;
		this.qg = qg;
		this.gameFrame = qg.gameFrame;
		initializeComponents();
		createGUI();
		addActionListeners();
	}
	
	private void initializeComponents(){
		portfolio = new JTextArea();
		trade = new JButton("Trade with this player.");
		playerInfo = new JPanel();
	}
	
	private void createGUI(){
		JScrollPane updatesScrollPane = new JScrollPane(portfolio, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		for(int i = 0; i < assets.size(); i++) {
			portfolio.append(assets.get(i));
		}
		
		setLayout(new BorderLayout());
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new BorderLayout());
		 
		JLabel playerPicture = new JLabel();
		playerPicture.setIcon(playerIcon);
		JLabel playerLabel = new JLabel(playerName);
		westPanel.add(playerPicture, BorderLayout.CENTER);
		westPanel.add(playerLabel, BorderLayout.SOUTH);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(portfolio, BorderLayout.CENTER);
		centerPanel.add(trade, BorderLayout.SOUTH);
		
		westPanel.setPreferredSize(new Dimension(350, 0));
		add(westPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
	}
	
	private void addActionListeners() {
		trade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				qg.setVisible(false);
				gameFrame.changePanel(new TradeGUI(qg));
				//System.out.println("TRADE");
				
				InitiateTradeMessage itm = new InitiateTradeMessage();
			}
		});
	}
} 
