import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TradeGUI extends JFrame {
	public JPanel tradePanel;
	public JPanel chatPanel;
	public JPanel notificationsAndReadyPanel;
	private JButton accept, decline, ready;
	private JLabel timer;
	private JLabel team1, team2, send, receive;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TradeGUI() {
		initializeComponents();
		createGUI();
		addActionListeners();
	}
	
	private void initializeComponents() {
		this.setLayout(new BorderLayout());
		tradePanel = new JPanel();
		chatPanel = new JPanel();
		notificationsAndReadyPanel = new JPanel();
		accept = new JButton("Accept");
		decline = new JButton("Decline");
		ready = new JButton("Ready");
		
		//TODO replace the team name labels with the names of the team names
		team1 = new JLabel("team 1");
		team2 = new JLabel("team 2");
		send = new JLabel("send");
		receive = new JLabel("receive");
		
	}
	
	private void createGUI() {
		// Create trade panel.
		tradePanel.setLayout(new BorderLayout());
		JPanel buttonsPanel = new JPanel();
		JPanel tradingGrid = new JPanel();
		
		// Trading Grid
		tradingGrid.setLayout(new GridLayout(4, 1));
		
		tradePanel.add(tradingGrid, BorderLayout.CENTER);
		
		buttonsPanel.add(accept);
		buttonsPanel.add(decline);
		tradePanel.add(buttonsPanel, BorderLayout.SOUTH);
		
		
		// Create chat Panel //TODO Temporary
		JLabel chat = new JLabel("CHAT PANEL");
		chatPanel.add(chat);
		
		//Create notificationsAndReadyPanel
		
		add(chatPanel, BorderLayout.SOUTH);
		add(tradePanel, BorderLayout.CENTER);
		add(notificationsAndReadyPanel, BorderLayout.EAST);
		
	}
	
	private void addActionListeners() {
		accept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
			}
		});
	}
}
