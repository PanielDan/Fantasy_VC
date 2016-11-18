package guis;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.Client;
import gameplay.Company;
import gameplay.GameFrame;
import listeners.DisabledItemSelectionModel;
import listeners.TableModel;
import messages.BeginAuctionBidMessage;
import utility.AppearanceConstants;
import utility.AppearanceSettings;
import utility.Constants;

/*
 * Author: Danny Pan
 */

public class AuctionTeamList extends JPanel {
	//variables used here
	private JLabel timer, middleFirmPicture, firmCurrentMoney, middleFirmName,
	purchasedFirmsLabel, detailsFirmPicture, detailsFirmCurrentMoney, detailsFirmName,
	detailsCompanyPicture,detailsPurchasedLabel ,detailsCompanyName;
	private JTextArea detailsCompanyBio;
	private Vector<String> firms, purchasedFirms;
	private JList<String> firmList, purchasedCompanysList, detailsFirmPurchasedList;
	private JTable firmData, detailsCompanyInfo;
	private JButton bidButton;
	private JPanel companyDetailsPanel;
	
	private Client client;
	private GameFrame gameFrame;
	private Vector<Company> companyVect;
	
	
	public AuctionTeamList(Client client, GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		this.client = client;
		intializeVariables();
		createGUI();
		addActionListeners();
	}
	
	
	private void intializeVariables(){
		
		//Variables for left panel
		timer = new JLabel("GUEST", SwingConstants.CENTER);
		firms = new Vector<String>();
		if(gameFrame.networked){
			testDraftOrder();
			timer.setText("0:45");
		}
		firmList = new JList<String>(firms);
		
		//Variables for middle panel
		middleFirmPicture = new JLabel();
		middleFirmPicture.setPreferredSize(new Dimension(100,100));
		firmCurrentMoney = new JLabel(Constants.currentCapital + Double.toString(gameFrame.user.getCurrentCapital()) +
				 Constants.million);
		middleFirmName = new JLabel("JMoney Capital");
		purchasedFirmsLabel = new JLabel("Purchased Firms", SwingConstants.CENTER);
		purchasedFirms = new Vector<String>();
		
		String[] columnNames = {"Name", "Tier Level", "Price"};
		TableModel dtm = new TableModel();
		dtm.setColumnIdentifiers(columnNames);
		companyVect = gameFrame.getGame().getCompanies();
		for(int i = 0; i < companyVect.size(); i++){
			dtm.addRow(new Object[]{companyVect.get(i).getName(), Integer.toString(companyVect.get(i).getTierLevel()),
					Double.toString(companyVect.get(i).getStartingPrice())});
		}
		firmData = new JTable(dtm);
		firmData.setBackground(AppearanceConstants.darkBlue);
		firmData.setForeground(AppearanceConstants.darkBlue);
		firmData.setFont(AppearanceConstants.fontSmallest);
		
		//Placed down here for testing purposes
		purchasedCompanysList = new JList<String>();

		
		//Variables for firm details
		detailsFirmPicture = new JLabel();
		detailsFirmPicture.setPreferredSize(new Dimension(100,100));
		detailsFirmCurrentMoney = new JLabel("Current Capital: $50,000,000", SwingConstants.CENTER);
		detailsFirmName = new JLabel("JMoney Capital", SwingConstants.CENTER);
		detailsPurchasedLabel = new JLabel("Purchased Firms", SwingConstants.CENTER);
		detailsCompanyPicture = new JLabel();
		detailsCompanyPicture.setPreferredSize(new Dimension(100,100));
		detailsCompanyName = new JLabel();
		detailsCompanyBio = new JTextArea();
		detailsCompanyBio.setLineWrap(true);
		detailsCompanyBio.setEditable(false);
		detailsCompanyBio.setWrapStyleWord(true);
		detailsCompanyInfo = new JTable();
		detailsCompanyInfo.setForeground(AppearanceConstants.darkBlue);
		detailsCompanyInfo.setFont(AppearanceConstants.fontSmallest);
		
		bidButton = new JButton("BID");
		
		//Initialized here to purchased firms for testing purposes.
		detailsFirmPurchasedList = new JList<String>(purchasedFirms);
		
		intializePictures();

	}
	
	//All of this just has to be updated with user images from company and user objects.
	private void intializePictures(){
		ImageIcon jeffrey = new ImageIcon("resources/img/profile.png");
		Image firmIcon = jeffrey.getImage();
		
		middleFirmPicture.setIcon(new ImageIcon(firmIcon.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH)));
		detailsFirmPicture.setIcon(new ImageIcon(firmIcon.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH)));
    	detailsCompanyPicture.setIcon(new ImageIcon(companyVect.get(0).getCompanyLogo().getScaledInstance((int)(100*companyVect.get(0).getAspectRatio()), 100,  java.awt.Image.SCALE_SMOOTH)));
	
	}
	
	//Function that fills in the random test team names
	private void testDraftOrder(){
		for (int i = 0; i < 20; i++){
			firms.add("Team " + Integer.toString((i%4) + 1));
		}
	}
	
	private void createGUI(){
		//Size accounts of chatbox and header
		setPreferredSize(new Dimension(1280,504));
		setBackground(AppearanceConstants.lightBlue);
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		//Code is designed so each panel has it's own create function to be modular
		JPanel draftOrderPanel = createDraftOrderPanel();
		JPanel companyListPanel = createCompanyListPanel();
		companyDetailsPanel = createCompanyDetailsPanel();
		
		//Adding Subpanels to manually b/c appearance settings function doesn't like
		//inherited panels
		add(Box.createHorizontalGlue());
		add(draftOrderPanel);
		add(Box.createHorizontalGlue());
		add(companyListPanel);
		add(Box.createHorizontalGlue());
		add(companyDetailsPanel);
		add(Box.createHorizontalGlue());
	}
	
	//Creates and returns the Draft Order Panel;
	private JPanel createDraftOrderPanel(){
		JPanel draftOrderPanel = new JPanel();
		JPanel timePanel = new JPanel();
		JScrollPane listPanel = new JScrollPane(firmList);
		
		//Appearance Adjustments
		timePanel.setLayout(new BorderLayout());
		draftOrderPanel.setLayout(new BorderLayout());
		
		AppearanceSettings.setSize(250, 450, draftOrderPanel);
		draftOrderPanel.setMaximumSize(new Dimension(250,450));
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, draftOrderPanel, listPanel, timePanel, firmList);
		AppearanceSettings.setForeground(AppearanceConstants.offWhite, timer, firmList);
		AppearanceSettings.setFont(AppearanceConstants.fontMedium, firmList);
		timer.setFont(AppearanceConstants.fontTimerLarge);
		
		//Set List to center text alignment
		DefaultListCellRenderer renderer = (DefaultListCellRenderer)firmList.getCellRenderer();  
		renderer.setHorizontalAlignment(JLabel.CENTER);  
		listPanel.setFocusable(false);
		listPanel.setBorder(null);
		
		//Adding individual components to respective panels
		timePanel.add(timer, BorderLayout.CENTER);
		timePanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.SOUTH);
				
		//Adding everything to main panel
		draftOrderPanel.add(timePanel, BorderLayout.NORTH);
		draftOrderPanel.add(listPanel, BorderLayout.CENTER);
		
		return draftOrderPanel;
	}
	
	//Creates and return the comapny list Panel
	private JPanel createCompanyListPanel(){
		JPanel companyListPanel = new JPanel();
		//Top spacing is used to hold spacing and a JSeparator
		JPanel topSpacingPanel = new JPanel();
		JPanel firmDetailsPanel = new JPanel();
		JPanel firmStatsPanel = new JPanel();
		JPanel purchasedListPanel = new JPanel();
		JScrollPane purchasedScrollPane = new JScrollPane(purchasedCompanysList);
		JScrollPane companyListPane = new JScrollPane(firmData);
		
		//Tring to remove borders from the scroll paes
		purchasedScrollPane.setFocusable(false);
		purchasedScrollPane.setBorder(null);
		DefaultListCellRenderer renderer = (DefaultListCellRenderer)purchasedCompanysList.getCellRenderer();  
		renderer.setHorizontalAlignment(JLabel.CENTER);  
		
		//Remove borders
		companyListPane.setFocusable(false);
		companyListPane.setBorder(BorderFactory.createEmptyBorder());
		companyListPane.getViewport().setBackground(AppearanceConstants.darkBlue);
		
		//Add padding
		purchasedCompanysList.setBorder(new EmptyBorder(5,5,5,5));
		purchasedFirmsLabel.setBorder(new EmptyBorder(5,5,5,5));

		
		//Layouts are set
		AppearanceSettings.setBoxLayout(BoxLayout.PAGE_AXIS, topSpacingPanel);
		AppearanceSettings.setBoxLayout(BoxLayout.LINE_AXIS, firmDetailsPanel);
		companyListPanel.setLayout(new BorderLayout());
		purchasedListPanel.setLayout(new BorderLayout());
		firmStatsPanel.setLayout(new GridLayout(2,1,5,5));
		
		//Appearance Settings are changed
		AppearanceSettings.setSize(650, 450, companyListPanel);
		companyListPanel.setMaximumSize(new Dimension(650,450));
		AppearanceSettings.setSize(650, 100, firmDetailsPanel);
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, companyListPanel, firmDetailsPanel,
				firmStatsPanel, purchasedScrollPane, purchasedCompanysList, topSpacingPanel,companyListPane,
				purchasedListPanel);
		AppearanceSettings.setBackground(AppearanceConstants.offWhite, firmData);

		AppearanceSettings.setForeground(AppearanceConstants.offWhite, firmCurrentMoney, middleFirmName,
				purchasedCompanysList, purchasedFirmsLabel, companyListPane);
		AppearanceSettings.setFont(AppearanceConstants.fontSmallest, purchasedCompanysList, firmCurrentMoney,
				firmCurrentMoney, middleFirmName, purchasedFirmsLabel);

		//firmstats adding in sequence
		firmStatsPanel.add(middleFirmName);
		//firmStatsPanel.add(purchasedFirmsLabel);
		firmStatsPanel.add(firmCurrentMoney);
		//firmStatsPanel.add(purchasedScrollPane);
		
		purchasedListPanel.add(purchasedFirmsLabel, BorderLayout.NORTH);
		purchasedListPanel.add(purchasedScrollPane, BorderLayout.CENTER);

		
		//Add firm details panel in sequence
		firmDetailsPanel.add(Box.createHorizontalStrut(10));
		firmDetailsPanel.add(middleFirmPicture);
		firmDetailsPanel.add(Box.createHorizontalStrut(20));
		firmDetailsPanel.add(firmStatsPanel);
		firmDetailsPanel.add(Box.createHorizontalStrut(10));
		firmDetailsPanel.add(purchasedListPanel);
		
		//Add everything to top spacing panel so we can have a divider
		topSpacingPanel.add(Box.createVerticalStrut(5));
		topSpacingPanel.add(firmDetailsPanel);
		topSpacingPanel.add(new JSeparator(JSeparator.HORIZONTAL));

		
		companyListPanel.add(topSpacingPanel, BorderLayout.NORTH);
		companyListPanel.add(companyListPane, BorderLayout.CENTER);
		
		return companyListPanel;
	}
	
	//Creates and return the Company Details Panel
	private JPanel createCompanyDetailsPanel(){
		JPanel cardSwapPanel = new JPanel();
		JPanel companyDetailsPanel = new JPanel();
		JPanel companyInfoPanel = new JPanel();
		JScrollPane companyTablePane = new JScrollPane(detailsCompanyInfo);
		JScrollPane companyBioPane = new JScrollPane(detailsCompanyBio);
		JPanel firmDetailsPanel = new JPanel();
		JPanel firmInfoPanel = new JPanel();
		JScrollPane firmListPane = new JScrollPane(detailsFirmPurchasedList);
		JPanel bidButtonPanel = new JPanel();
		
		//Tring to remove borders from the panes
		companyTablePane.setFocusable(false);
		companyTablePane.setBorder(BorderFactory.createEmptyBorder());
		companyTablePane.getViewport().setBackground(AppearanceConstants.darkBlue);
		firmListPane.setFocusable(false);
		firmListPane.setBorder(null);
		companyBioPane.setFocusable(false);
		companyBioPane.setBorder(null);
		
		//Set purchased list text to be centered
		DefaultListCellRenderer renderer = (DefaultListCellRenderer)detailsFirmPurchasedList.getCellRenderer();  
		renderer.setHorizontalAlignment(JLabel.CENTER);  
		
		//Set Layouts
		cardSwapPanel.setLayout(new CardLayout());
		companyDetailsPanel.setLayout(new BorderLayout());
		firmDetailsPanel.setLayout(new BorderLayout());
		AppearanceSettings.setBoxLayout(BoxLayout.PAGE_AXIS, firmInfoPanel,companyInfoPanel);
	
		//Appearance Settings
		AppearanceSettings.setSize(250, 450, cardSwapPanel, firmDetailsPanel, companyDetailsPanel);
		cardSwapPanel.setMaximumSize(new Dimension(250,450));
		firmDetailsPanel.setMaximumSize(new Dimension(250,450));
		companyDetailsPanel.setMaximumSize(new Dimension(250,450));
		AppearanceSettings.setSize(250,80, companyBioPane);
		AppearanceSettings.setSize(100,40, bidButton);
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, cardSwapPanel, firmDetailsPanel,
				companyDetailsPanel,companyInfoPanel,firmInfoPanel,detailsFirmCurrentMoney, detailsFirmName,
				detailsCompanyName, detailsCompanyBio, detailsFirmPurchasedList,
				detailsFirmPicture, detailsCompanyPicture, purchasedCompanysList, companyTablePane,
				firmListPane,detailsPurchasedLabel, companyBioPane, bidButtonPanel);
		AppearanceSettings.setForeground(AppearanceConstants.offWhite, detailsFirmCurrentMoney, detailsFirmName,
				detailsCompanyName, detailsCompanyBio, detailsFirmPurchasedList,
				purchasedCompanysList, detailsPurchasedLabel, bidButton);
		AppearanceSettings.setFont(AppearanceConstants.fontSmallest, detailsFirmCurrentMoney, detailsFirmName,
				detailsCompanyName, detailsFirmPurchasedList, purchasedCompanysList, detailsPurchasedLabel);
		detailsCompanyBio.setFont(AppearanceConstants.fontSmallBidButton);
		detailsCompanyBio.setBorder(new EmptyBorder(5,5,5,5));
		//Bid Button Appearance Settings
		bidButton.setOpaque(true);
		bidButton.setFont(AppearanceConstants.fontLargeBidButton);
		bidButton.setBackground(new Color(51,102,0));
		bidButton.setBorderPainted(false);
		//Function to Align all Labels to the center of the BoxLayout.
		//Needs to be added to Appearance Settings.
		AppearanceSettings.setCenterAlignment(detailsFirmPicture, detailsFirmName, detailsFirmCurrentMoney,
				detailsPurchasedLabel, detailsCompanyPicture, detailsCompanyName);
		
		//Adding Firm Info panel objects
		firmInfoPanel.add(Box.createVerticalStrut(20));
		firmInfoPanel.add(detailsFirmPicture);
		firmInfoPanel.add(Box.createVerticalStrut(10));
		firmInfoPanel.add(detailsFirmName);
		firmInfoPanel.add(Box.createVerticalStrut(10));
		firmInfoPanel.add(detailsFirmCurrentMoney);
		firmInfoPanel.add(Box.createVerticalStrut(10));
		firmInfoPanel.add(detailsPurchasedLabel);
		firmInfoPanel.add(new JSeparator(JSeparator.HORIZONTAL));
		
		//Finishing firm details Panel
		firmDetailsPanel.add(firmInfoPanel, BorderLayout.NORTH);
		firmDetailsPanel.add(firmListPane, BorderLayout.CENTER);
		
		//Adding Company Info panel objects
		companyInfoPanel.add(Box.createVerticalStrut(20));
		companyInfoPanel.add(detailsCompanyPicture);
		companyInfoPanel.add(Box.createVerticalStrut(10));
		companyInfoPanel.add(detailsCompanyName);
		companyInfoPanel.add(Box.createVerticalStrut(10));
		companyInfoPanel.add(companyBioPane);
		companyInfoPanel.add(new JSeparator(JSeparator.HORIZONTAL));
		
		bidButtonPanel.add(bidButton);
		
		//Adding everything to company details Panel
		companyDetailsPanel.add(companyInfoPanel, BorderLayout.NORTH);
		companyDetailsPanel.add(companyTablePane, BorderLayout.CENTER);
		companyDetailsPanel.add(bidButtonPanel, BorderLayout.SOUTH);
		
		//Add both layouts into the card layout.
		cardSwapPanel.add(companyDetailsPanel, "Company");
		cardSwapPanel.add(firmDetailsPanel, "Firm");
		
		return cardSwapPanel;
	}
	
	private void addActionListeners(){
		//List selection listener for draft order list
		firmList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
	            //TODO Add some sort of function to update detail panel
				
				/*needs list of users.
				detailsFirmPicture.setIcon(gameFrame.getGame());
				detailsFirmName.setText(text);
				detailsFirmCurrentMoney.setText(text);
				*/
				
				CardLayout cardLayout = (CardLayout) companyDetailsPanel.getLayout();
				cardLayout.show(companyDetailsPanel, "Firm");
				
			}
			
		});
		
		//Listener for the purschased firms list at the top
		purchasedCompanysList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
	        	Company selectedCompany = gameFrame.game.returnCompany(purchasedCompanysList.getSelectedValue());
	        	detailsCompanyName.setText(selectedCompany.getName());

	        	detailsCompanyPicture.setIcon(new ImageIcon(selectedCompany.getCompanyLogo().getScaledInstance((int)(100*selectedCompany.getAspectRatio()), 100,  java.awt.Image.SCALE_SMOOTH)));
	        	detailsCompanyBio.setText(selectedCompany.getDescription());
	        	
	        	//detailsCompanyInfo
	        	Object[][] companyData = {
	        			{"Name", selectedCompany.getName()},
	        			{"Tier", selectedCompany.getTierLevel()},
	        			{"Asking Price", selectedCompany.getAskingPrice()},
	        			{"Current Worth", selectedCompany.getCurrentWorth()},
	        	};
	        	String[] columnNames = {"",""};
	        	TableModel dtm = new TableModel();
	        	dtm.setDataVector(companyData, columnNames);
	   	       	detailsCompanyInfo.setModel(dtm);
				
				CardLayout cardLayout = (CardLayout) companyDetailsPanel.getLayout();
				cardLayout.show(companyDetailsPanel, "Company");				
			}
			
		});
		
		//Maybe we use a mouse listener? We'll see in the future
		firmData.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		firmData.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	
	        	int selectedRow = firmData.getSelectedRow();
	        	TableModel dtm = (TableModel)firmData.getModel();
	        	
				Company selectedCompany = gameFrame.game.returnCompany((String)dtm.getValueAt(selectedRow, 0));
	        	detailsCompanyName.setText(selectedCompany.getName());

	        	detailsCompanyPicture.setIcon(new ImageIcon(selectedCompany.getCompanyLogo().getScaledInstance((int)(100*companyVect.get(selectedRow).getAspectRatio()), 100,  java.awt.Image.SCALE_SMOOTH)));
	        	detailsCompanyBio.setText(selectedCompany.getDescription());
	        	
	        	//detailsCompanyInfo
	        	Object[][] companyData = {
	        			{"Name", selectedCompany.getName()},
	        			{"Tier", selectedCompany.getTierLevel()},
	        			{"Asking Price", selectedCompany.getAskingPrice()},
	        			{"Current Worth", selectedCompany.getCurrentWorth()},
	        	};
	        	String[] columnNames = {"",""};
	        	dtm = new TableModel();
	        	dtm.setDataVector(companyData, columnNames);
	   	       	detailsCompanyInfo.setModel(dtm);
	   	       		        	
				CardLayout cardLayout = (CardLayout)companyDetailsPanel.getLayout();
				cardLayout.show(companyDetailsPanel, "Company");
	        }
		});
				
		//Action Listener to make purchased firms list read only
		detailsFirmPurchasedList.setSelectionModel(new DisabledItemSelectionModel());
		detailsFirmPurchasedList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		
		bidButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: Set up bidding screen and transition to bidding screen.
				if(gameFrame.networked) {
					client.sendMessage(new BeginAuctionBidMessage(companyVect.get(firmData.getSelectedRow()).getName()));
				} else {	
					int selectedRow = firmData.getSelectedRow();
					firmData.getSelectionModel().addSelectionInterval((selectedRow+1)%firmData.getRowCount(),
							(selectedRow+1)%firmData.getRowCount());
					TableModel dtm = (TableModel) firmData.getModel();
					Company selectedCompany = gameFrame.game.returnCompany((String)dtm.getValueAt(selectedRow, 0));
					
					purchasedFirms.add(selectedCompany.getName());
					purchasedCompanysList.setListData(purchasedFirms);
					gameFrame.user.addCompany(selectedCompany);
					double newMoney = gameFrame.user.getCurrentCapital() - selectedCompany.getAskingPrice();
					gameFrame.user.setCurrentCapital(newMoney);
					firmCurrentMoney.setText(Constants.currentCapital + Double.toString(newMoney) + Constants.million);
					gameFrame.header.updateCurrentCapital();
					dtm.removeRow(selectedRow);
					firmData.revalidate();
					firmData.repaint();
					
					if(gameFrame.user.getCompanies().size() == 8) {
						gameFrame.changePanel(new TimelapsePanel(client, gameFrame));
					}
				}
			}
			
		});
		
		//We might have to add empty action listeners to the tables to prevent
		//editing of data
	}
}