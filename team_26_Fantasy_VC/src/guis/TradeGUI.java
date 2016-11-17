package guis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import messages.AcceptTradeMessage;
import messages.DeclineTradeMessage;
import messages.QuarterlyReadyMessage;

public class TradeGUI extends JFrame {
	public JPanel tradePanel;
	public JPanel team1Portfolio, team2Portfolio, sending, receiving;
	public JPanel notificationsAndReadyPanel;
	private JButton accept, decline, ready;
	private JLabel timer;
	private JLabel team1, team2, send, receive;
	public JTextArea updatesTextArea;
	public boolean networked;
	public QuarterlyGUI qg;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TradeGUI(QuarterlyGUI qg) {
		this.qg = qg;
		initializeComponents();
		createGUI();
		addActionListeners();
	}
	
	private void initializeComponents() {
		setSize(1280, 504);
		this.setLayout(new BorderLayout());
		tradePanel = new JPanel();
		updatesTextArea = new JTextArea();
		notificationsAndReadyPanel = new JPanel();
		team1Portfolio = new JPanel();
		team2Portfolio = new JPanel();
		sending = new JPanel();
		receiving = new JPanel();
		accept = new JButton("Accept");
		decline = new JButton("Decline");
		ready = new JButton("Ready");
		
		//TODO replace the team name labels with the names of the team names
		team1 = new JLabel("team 1");
		team2 = new JLabel("team 2");
		send = new JLabel("send");
		receive = new JLabel("receive");
		timer = new JLabel("00:10", SwingConstants.CENTER);
	}
	
	private void createGUI() {
		JScrollPane updatesScrollPane = new JScrollPane(updatesTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// Create trade panel.
		tradePanel.setLayout(new BorderLayout());
		JPanel buttonsPanel = new JPanel();
		JPanel tradingGrid = new JPanel();
		
		// Trading Grid
		tradingGrid.setLayout(new GridLayout(1, 4));
		tradingGrid.add(team1Portfolio);
		tradingGrid.add(sending);
		tradingGrid.add(receiving);
		tradingGrid.add(team2Portfolio);
		
		team1Portfolio.setBorder(BorderFactory.createLineBorder(Color.black));
		team2Portfolio.setBorder(BorderFactory.createLineBorder(Color.black));
		sending.setBorder(BorderFactory.createLineBorder(Color.red));
		receiving.setBorder(BorderFactory.createLineBorder(Color.green));
		team1Portfolio.add(team1);
		team2Portfolio.add(team2);
		sending.add(send);
		receiving.add(receive);
		
		tradePanel.add(tradingGrid, BorderLayout.CENTER);
		
		buttonsPanel.add(accept);
		buttonsPanel.add(decline);
		tradePanel.add(buttonsPanel, BorderLayout.SOUTH);

		//Create notificationsAndReadyPanel
		notificationsAndReadyPanel.setLayout(new BorderLayout());
		notificationsAndReadyPanel.add(timer, BorderLayout.NORTH);
		notificationsAndReadyPanel.add(ready, BorderLayout.CENTER);
		notificationsAndReadyPanel.add(updatesTextArea, BorderLayout.SOUTH);
		sendUpdate("Notifications Tray");
		
		notificationsAndReadyPanel.setPreferredSize(new Dimension(300, 1));
		updatesTextArea.setPreferredSize(new Dimension(1, 400));
		add(tradePanel, BorderLayout.CENTER);
		add(notificationsAndReadyPanel, BorderLayout.EAST);
		ready.setBackground(Color.GREEN);
		accept.setBackground(Color.GREEN);
		decline.setBackground(Color.RED);
		accept.setPreferredSize(new Dimension(200, 30));
		decline.setPreferredSize(new Dimension(200, 30));
	}
	
	private void addActionListeners() {
		accept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// Swap players
				AcceptTradeMessage atm = new AcceptTradeMessage();
				//Switch players here
			}
		});
		
		decline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				DeclineTradeMessage dtm = new DeclineTradeMessage();
				//Do nothing 
				setVisible(false);
				qg.setVisible(true);
			}
		});
		
		ready.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				QuarterlyReadyMessage qrm = new QuarterlyReadyMessage();
			}
		});
	}
	
	public void sendUpdate(String message) {
		updatesTextArea.append("\n" + message);
	}
}
