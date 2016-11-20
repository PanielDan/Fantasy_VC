package guis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import client.Client;
import gameplay.Company;
import gameplay.User;
import messages.AcceptTradeMessage;
import messages.DeclineTradeMessage;
import messages.QuarterlyReadyMessage;
import trade.CompanyTradeItem;
import trade.Trade;
import trade.TradeItem;
import utility.AppearanceConstants;
import utility.AppearanceSettings;

public class TradeGUI extends JPanel {
	private JButton accept, decline, ready;
	private JLabel timer;
	private JLabel team1, team2, teamOffers;
	private JList<TradeItem> team1Companies, team2Companies, notifications, team1OfferList, 
	team2OfferList;
	public QuarterlyGUI qg;
	private Client client;
	
	private Trade trade;
	private boolean received;
	private User user1;
	private User user2;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TradeGUI(Client client, QuarterlyGUI qg, User user1, User user2) {
		this.client = client;
		this.qg = qg;
		this.received = false;
		this.user1 = user1;
		this.user2 = user2;
		initializeComponents();
		createGUI();
		addActionListeners();
		populate();
	}
	
	public TradeGUI(Client client, QuarterlyGUI qg, Trade tradeState, User user1, User user2) { 
		this.trade = tradeState;
		this.client = client;
		this.qg = qg;
		this.received = true;
		this.user1 = user1;
		this.user2 = user2;
		initializeComponents();
		createGUI();
		addActionListeners();
		populate(tradeState);
	}
	

	private void initializeComponents() {
		accept = new JButton("Accept");
		accept.setOpaque(true);
		accept.setBorderPainted(false);
		decline = new JButton("Decline");
		decline.setOpaque(true);
		decline.setBorderPainted(false);
		ready = new JButton("Ready");
		ready.setOpaque(true);
		ready.setBorderPainted(false);
		
		//TODO replace the team name labels with the names of the team names
		team1 = new JLabel(user1.getCompanyName());
		team2 = new JLabel(user2.getCompanyName());
		teamOffers = new JLabel("Offers");
		timer = new JLabel("00:10", SwingConstants.CENTER);
		notifications = new JList();
		team1Companies = new JList<TradeItem>();
		team2Companies = new JList<TradeItem>();
		team1OfferList = new JList<TradeItem>();
		team2OfferList = new JList<TradeItem>();
		
		//Set appearance Constants
		AppearanceSettings.setFont(AppearanceConstants.fontTimerMedium,timer);
		AppearanceSettings.setFont(AppearanceConstants.fontSmall, team1, team2, teamOffers,
				team1Companies, team2Companies, team1OfferList, team2OfferList);
		AppearanceSettings.setFont(AppearanceConstants.fontButtonBig, accept, decline, ready);
		AppearanceSettings.setForeground(AppearanceConstants.offWhite, team1, team2, teamOffers,
				team1OfferList, team2OfferList, team1Companies, team2Companies);
		AppearanceSettings.setForeground(AppearanceConstants.darkBlue, notifications, timer, accept,
				decline);
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, team1Companies,team2Companies,
				team1OfferList, team2OfferList);
		AppearanceSettings.setBackground(AppearanceConstants.offWhite, notifications, accept, decline);
		AppearanceSettings.setCenterAlignment(team1,team2,teamOffers, timer, ready);
		ready.setBackground(AppearanceConstants.green);
		ready.setForeground(AppearanceConstants.offWhite);
		
		//Set Centering
		DefaultListCellRenderer renderer1 = (DefaultListCellRenderer)team1Companies.getCellRenderer();  
		renderer1.setHorizontalAlignment(JLabel.CENTER);  
		DefaultListCellRenderer renderer2 = (DefaultListCellRenderer)team2Companies.getCellRenderer();  
		renderer2.setHorizontalAlignment(JLabel.CENTER);  
		DefaultListCellRenderer renderer3 = (DefaultListCellRenderer)team1OfferList.getCellRenderer();  
		renderer3.setHorizontalAlignment(JLabel.CENTER);  
		DefaultListCellRenderer renderer4 = (DefaultListCellRenderer)team2OfferList.getCellRenderer();  
		renderer4.setHorizontalAlignment(JLabel.CENTER);  
		
	}
	
	private void createGUI() {
		setSize(1280, 504);
		setBackground(AppearanceConstants.lightBlue);
		setLayout(new BorderLayout());
		
		JPanel companiesPanelHolder = new JPanel();
		JPanel team1CompaniesPanel = new JPanel();
		JPanel team2CompaniesPanel = new JPanel();
		JPanel offersPanel = new JPanel();
		JPanel offersListPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel timerAndNotificationsPanel = new JPanel();
		JPanel leftPanel = new JPanel ();
		
		JScrollPane team1CompaniesPane = new JScrollPane(team1Companies);
		JScrollPane team2CompaniesPane = new JScrollPane(team2Companies);
		JScrollPane team1OffersPane = new JScrollPane(team1OfferList);
		JScrollPane team2OffersPane = new JScrollPane(team2OfferList);
		JScrollPane notificationsPane = new JScrollPane(notifications);
		
		//setBorders
//		team1CompaniesPane.setFocusable(false);
//		team1CompaniesPane.setBorder(null);
//		team2CompaniesPane.setFocusable(false);
//		team2CompaniesPane.setBorder(null);
//		team1OffersPane.setFocusable(false);
//		team1OffersPane.setBorder(null);
//		team2OffersPane.setFocusable(false);
//		team2OffersPane.setBorder(null);
//		notificationsPane.setFocusable(false);
//		notificationsPane.setBorder(null);
		
		//Set Layouts
		AppearanceSettings.setBoxLayout(BoxLayout.PAGE_AXIS, timerAndNotificationsPanel, offersPanel,
				team1CompaniesPanel, team2CompaniesPanel, leftPanel);
		AppearanceSettings.setBoxLayout(BoxLayout.LINE_AXIS,companiesPanelHolder, buttonPanel, offersListPanel);
		
		//Set Sizes for scrollpanes
		AppearanceSettings.setSize(200,300, team1CompaniesPane);
		team1CompaniesPane.setMaximumSize(new Dimension(200,300));
		AppearanceSettings.setSize(200,300, team2CompaniesPane);
		team2CompaniesPane.setMaximumSize(new Dimension(200,300));
		AppearanceSettings.setSize(200,300, team1OffersPane);
		team1OffersPane.setMaximumSize(new Dimension(200,300));
		AppearanceSettings.setSize(200,300, team2OffersPane);
		team2OffersPane.setMaximumSize(new Dimension(200,300));
		AppearanceSettings.setSize(300,250, notificationsPane);
		notificationsPane.setMaximumSize(new Dimension(300,250));
		
		//Set Sizes for Panels
		AppearanceSettings.setSize(200,350, team1CompaniesPanel);
		team1CompaniesPanel.setMaximumSize(new Dimension(200,350));
		AppearanceSettings.setSize(200,350, team2CompaniesPanel);
		team2CompaniesPanel.setMaximumSize(new Dimension(200, 350));
		AppearanceSettings.setSize(400,350, offersPanel);
		offersPanel.setMaximumSize(new Dimension(400,350));
		AppearanceSettings.setSize(400,300, offersListPanel);
		offersListPanel.setMaximumSize(new Dimension(400,300));
		AppearanceSettings.setSize(300,400, timerAndNotificationsPanel);
		timerAndNotificationsPanel.setMaximumSize(new Dimension(300,400));
		
		//Set Color
		AppearanceSettings.setBackground(AppearanceConstants.lightBlue, buttonPanel, leftPanel, companiesPanelHolder);
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue,team1CompaniesPanel,
				team2CompaniesPanel, offersPanel, offersListPanel, timerAndNotificationsPanel,
				team1CompaniesPane, team2CompaniesPane, team1OffersPane, team2OffersPane, notificationsPane);
		AppearanceSettings.setBackground(AppearanceConstants.offWhite, timerAndNotificationsPanel);
				
		//Add TEAM 1 PANEL;
		team1CompaniesPanel.add(Box.createGlue());
		team1CompaniesPanel.add(team1);
		team1CompaniesPanel.add(new JSeparator(JSeparator.HORIZONTAL));
		team1CompaniesPanel.add(team1CompaniesPane);
		
		//ADD TEAM 2 PANEL
		team2CompaniesPanel.add(Box.createGlue());
		team2CompaniesPanel.add(team2);
		team2CompaniesPanel.add(new JSeparator(JSeparator.HORIZONTAL));
		team2CompaniesPanel.add(team2CompaniesPane);
		
		//AddOfferlistPanel
		AppearanceSettings.addGlue(offersListPanel, BoxLayout.LINE_AXIS, true, team1OffersPane,
				new JSeparator(JSeparator.VERTICAL), team2OffersPane);
		
		//Finish off Offers Panel
		offersPanel.add(Box.createGlue());
		offersPanel.add(teamOffers);
		offersPanel.add(new JSeparator(JSeparator.HORIZONTAL));
		offersPanel.add(offersListPanel);
		offersPanel.add(Box.createGlue());
		
		//Button Group layout
		buttonPanel.add(Box.createGlue());
		buttonPanel.add(accept);
		buttonPanel.add(Box.createHorizontalStrut(20));
		buttonPanel.add(decline);
		buttonPanel.add(Box.createGlue());

		AppearanceSettings.addGlue(timerAndNotificationsPanel, BoxLayout.PAGE_AXIS, true, timer,
				notificationsPane, ready);
				
		
		//Add together Length of companies Panels
		AppearanceSettings.addGlue(companiesPanelHolder, BoxLayout.LINE_AXIS, true, team1CompaniesPanel,
				offersPanel, team2CompaniesPanel);
		
		//Add together Left panel;
		AppearanceSettings.addGlue(leftPanel, BoxLayout.PAGE_AXIS, true, companiesPanelHolder, buttonPanel);
		
		add(leftPanel, BorderLayout.CENTER);
		add(timerAndNotificationsPanel, BorderLayout.EAST);

		
	}
	
	private void populate() {
		System.out.println("POPULATING");
				
		Vector<TradeItem> vec1 = new Vector<TradeItem>();
		Vector<TradeItem> vec2 = new Vector<TradeItem>();
		
		for (Company company : user1.getCompanies()) {
			vec1.add(new CompanyTradeItem(company));
			System.out.println(company.getName() + " is being populated");
		} for (Company company : user2.getCompanies()) {
			vec2.add(new CompanyTradeItem(company));
			System.out.println(company.getName() + " is being populated");
		}
		
		
		team1Companies = new JList<TradeItem>(vec1);
		team2Companies = new JList<TradeItem>(vec2);
		
		revalidate();
		repaint();
	}
	
	private void populate(Trade tradeState) {
		
		for (Object obj : tradeState.getPlayerOneInventory()) {
			if (obj instanceof Company) { 
				team1Companies.add(new CompanyTradeItem((Company) obj));
			}
		}
		
		for (Object obj : tradeState.getPlayerOneTrade()) { 
			if (obj instanceof Company) { 
				team1OfferList.add(new CompanyTradeItem((Company) obj));
			}
		}
		
		for (Object obj : tradeState.getPlayerTwoInventory()) {
			if (obj instanceof Company) {
				team2Companies.add(new CompanyTradeItem((Company) obj));
			}
		}
		
		for (Object obj : tradeState.getPlayerTwoTrade()) {
			if (obj instanceof Company) { 
				team2OfferList.add(new CompanyTradeItem((Company) obj));
			}
		}
		
		revalidate();
		repaint();
		
	}

	
	private void addActionListeners() {
		accept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// Swap players: NEED TO PASS IN BOTH TEAM NAMES and BOTH TEAM COMPANY VECTORS
//				AcceptTradeMessage atm = new AcceptTradeMessage();
				//Switch players here
			}
		});
		
		decline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				DeclineTradeMessage dtm = new DeclineTradeMessage();
				//Do nothing 
				qg.gameFrame.changePanel(qg);
			}
		});
		
		ready.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				QuarterlyReadyMessage qrm = new QuarterlyReadyMessage();
			}
		});
	}
}
