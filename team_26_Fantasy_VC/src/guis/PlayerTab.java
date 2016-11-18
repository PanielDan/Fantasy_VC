package guis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import gameplay.Company;
import gameplay.GameFrame;
import gameplay.User;
import listeners.TableModel;
import messages.InitiateTradeMessage;
import utility.AppearanceConstants;
import utility.AppearanceSettings;
import utility.ImageLibrary;

public class PlayerTab extends JPanel{
	public JButton trade;
	public String playerName;
	public ImageIcon playerIcon;
	public JPanel playerInfo;
	public Vector<Company> assets;
	public QuarterlyGUI qg;
	public GameFrame gameFrame;
	
	
	private JTable portfolio;
	private User user;
	
	public PlayerTab(String playerName, ImageIcon playerIcon, Vector<Company> assets, QuarterlyGUI qg) {
		this.playerName = playerName;
		this.playerIcon = playerIcon;
		this.assets = assets;
		this.qg = qg;
		this.gameFrame = qg.gameFrame;
		initializeComponents();
		createGUI();
		addActionListeners();
	}
	
	public PlayerTab(User user, QuarterlyGUI qg) {
		this.user = user;
//		this.playerName = user.getUsername();
//		this.playerIcon = new ImageIcon(user.getUserIcon());
//		this.assets = user.getCompanies();
		this.qg = qg;
		this.gameFrame = this.qg.gameFrame;
		initializeComponents();
		createGUI();
		addActionListeners();
	}
	

	private void initializeComponents(){
		trade = new JButton("Trade with this player.");
		playerInfo = new JPanel();
	}
	
	private void createGUI(){
		JScrollPane updatesScrollPane = new JScrollPane(portfolio, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		String[] columnNames = {"Name", "Tier Level", "Price", "Trend"};
		TableModel dtm = new TableModel();
		dtm.setColumnIdentifiers(columnNames);
		Vector<Company> companies = user.getCompanies();
		for(int i = 0; i < companies.size(); i++){
			dtm.addRow(new Object[]{companies.get(i).getName(), Integer.toString(companies.get(i).getTierLevel()),
					Double.toString(companies.get(i).getCurrentWorth()), 
					Double.toString((companies.get(i).getCurrentWorth() - companies.get(i).getStartingPrice())/
									 companies.get(i).getStartingPrice()) + "%"});
		}
		
		portfolio = new JTable(dtm);
		portfolio.setBackground(AppearanceConstants.darkBlue);
		portfolio.setForeground(AppearanceConstants.darkBlue);
		portfolio.setFont(AppearanceConstants.fontSmallest);
		
//		for (int a = 0; a < user.getCompanies().size(); a++) { 
//			
//			portfolio.append(user.getCompanies().get(a).getName() + '\n');
//		}
//		
		setLayout(new BorderLayout());
		JPanel westPanel = new JPanel();
//		westPanel.setLayout(new BorderLayout());
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.PAGE_AXIS));
//		JLabel playerPicture = new JLabel();
//		playerPicture.setIcon(playerIcon);
//		JLabel playerLabel = new JLabel(playerName);
//		JLabel playerBio = new JLabel()
		
		
		JButton playerPicture = new JButton();
		Image i = ImageLibrary.getImage("resources/img/profile.png");
		ImageIcon ii = new ImageIcon(i.getScaledInstance(300, 300, Image.SCALE_SMOOTH));
		playerPicture.setIcon(ii);
		JLabel playerName = new JLabel(user.getUsername());
		JLabel companyName = new JLabel(user.getCompanyName());
		user.setUserBio("This is the User's bio. It's less than 144 characters.");
		JTextArea playerBio = new JTextArea(user.getUserBio());
		playerBio.setEditable(false);
		playerBio.setLineWrap(true);
		playerBio.setWrapStyleWord(true);
		
		AppearanceSettings.setFont(AppearanceConstants.fontHeaderUser, playerName);
		AppearanceSettings.setFont(AppearanceConstants.fontFirmName, companyName);
		AppearanceSettings.setFont(AppearanceConstants.fontSmall, playerBio);
		
		
		JPanel wordsPanel = new JPanel();
		wordsPanel.setLayout(new BoxLayout(wordsPanel, BoxLayout.PAGE_AXIS));
		wordsPanel.add(playerName);
		wordsPanel.add(companyName);
		wordsPanel.add(playerBio);
		
//		westPanel.add(playerPicture, BorderLayout.CENTER);
//		westPanel.add(wordsPanel, BorderLayout.SOUTH);
		westPanel.add(playerPicture);
		westPanel.add(wordsPanel);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
		centerPanel.add(portfolio);
		centerPanel.add(trade);
		
		westPanel.setPreferredSize(new Dimension(350, 0));
		add(westPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		
		/* Colorize! */
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, wordsPanel, playerInfo, playerBio, this);
		AppearanceSettings.setBackground(AppearanceConstants.lightBlue, centerPanel, portfolio);
		AppearanceSettings.setForeground(AppearanceConstants.offWhite, playerName, companyName, playerBio, trade, portfolio);
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
