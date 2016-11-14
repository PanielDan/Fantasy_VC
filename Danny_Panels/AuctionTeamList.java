package panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import resources.AppearanceConstants;
import resources.AppearanceSettings;

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
	
	
	public AuctionTeamList() {
		
		intializeVariables();
		createGUI();
		addActionListeners();
	}
	
	
	private void intializeVariables(){
		
		//Variables for left panel
		timer = new JLabel("0:45", SwingConstants.CENTER);
		firms = new Vector<String>();
		testDraftOrder();
		firmList = new JList<String>(firms);
		
		//Variables for middle panel
		middleFirmPicture = new JLabel();
		middleFirmPicture.setPreferredSize(new Dimension(100,100));
		firmCurrentMoney = new JLabel("Current Capital: $50,000,000");
		middleFirmName = new JLabel("JMoney Capital");
		purchasedFirmsLabel = new JLabel("Purchased Firms", SwingConstants.CENTER);
		purchasedFirms = new Vector<String>();
		firmData = new JTable();
		//firmData.setEdit
		
		
		//Test Values for Purchased Firms
		for (int i = 0; i < 5; i++){
			purchasedFirms.add("Firm " + Integer.toString(i));
		}
		//All for trying to scale down a picture
		ImageIcon jeffrey = new ImageIcon("images/Jeffrey.png");
		Image firmIcon = jeffrey.getImage();
		middleFirmPicture.setIcon(new ImageIcon(firmIcon.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH)));
		
		//Placed down here for testing purposes
		purchasedCompanysList = new JList<String>(purchasedFirms);

		
		//Variables for firm details
		detailsFirmPicture = new JLabel();
		detailsFirmPicture.setPreferredSize(new Dimension(100,100));
		detailsFirmCurrentMoney = new JLabel("Current Capital: $50,000,000", SwingConstants.CENTER);
		detailsFirmName = new JLabel("JMoney Capital", SwingConstants.CENTER);
		detailsPurchasedLabel = new JLabel("Purchased Firms", SwingConstants.CENTER);
		detailsCompanyPicture = new JLabel();
		detailsCompanyPicture.setPreferredSize(new Dimension(100,100));
		detailsCompanyName = new JLabel("Example Company Name",SwingConstants.CENTER);
		detailsCompanyBio = new JTextArea("Alliance Pharmaceuticals an American large-cap business driven biomedical research corporation focused on the discovery of innovative medicine. Alliance is looking for a large investment to fund an expansion into european laboritories to further drug creation.");
		detailsCompanyBio.setLineWrap(true);
		detailsCompanyBio.setEditable(false);
		detailsCompanyBio.setWrapStyleWord(true);
		detailsCompanyInfo = new JTable();
		bidButton = new JButton("BID");
		
		//Initialized here to purchased firms for testing purposes.
		detailsFirmPurchasedList = new JList<String>(purchasedFirms);
		
		//Picture Test Code
		detailsFirmPicture.setIcon(new ImageIcon(firmIcon.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH)));
		detailsCompanyPicture.setIcon(new ImageIcon(firmIcon.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH)));
	
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
		timer.setFont(new Font("Avenir", Font.BOLD, 60));
		
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
		companyListPane.getViewport().setBackground(AppearanceConstants.offWhite);
		
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
		firmDetailsPanel.add(Box.createHorizontalStrut(5));
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
		companyTablePane.getViewport().setBackground(AppearanceConstants.offWhite);
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
		detailsCompanyBio.setFont(new Font("Avenir", Font.BOLD,12));
		detailsCompanyBio.setBorder(new EmptyBorder(5,5,5,5));
		//Bid Button Appearance Settings
		bidButton.setOpaque(true);
		bidButton.setFont(new Font("Avenir Bold", Font.BOLD,24));
		bidButton.setBackground(new Color(51,102,0));
		bidButton.setBorderPainted(false);
		//Function to Align all Labels to the center of the BoxLayout.
		//Needs to be added to Appearance Settings.
		AppearanceSettings.setCenterAlignment(detailsFirmPicture, detailsFirmName, detailsFirmCurrentMoney,
				detailsPurchasedLabel, detailsCompanyPicture, detailsCompanyName);
		
		//Adding Firm Info panel objects
		firmInfoPanel.add(Box.createVerticalStrut(5));
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
		companyInfoPanel.add(Box.createVerticalStrut(5));
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
		firmList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				CardLayout cardLayout = (CardLayout) companyDetailsPanel.getLayout();
				cardLayout.show(companyDetailsPanel, "Firm");
				
			}
			
		});
		
		//Listener for the purschased firms list at the top
		purchasedCompanysList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				CardLayout cardLayout = (CardLayout) companyDetailsPanel.getLayout();
				cardLayout.show(companyDetailsPanel, "Company");				
			}
			
		});
		
		//Maybe we use a mouse listener? We'll see in the future
		firmData.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	            // do some actions here, for example
	            // print first column value from selected row
	            int selectedRow = firmData.getSelectedRow();
				CardLayout cardLayout = (CardLayout) companyDetailsPanel.getLayout();
				cardLayout.show(companyDetailsPanel, "Company");	
	        }
		});
				
		//Action Listener to make purchased firms list read only
		detailsFirmPurchasedList.setSelectionModel(new DisabledItemSelectionModel());
		detailsFirmPurchasedList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		
		bidButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		//We might have to add empty action listeners to the tables to prevent
		//editing of data
	}
	
	//List selection model so purchased firms in details can't be clicked
	private class DisabledItemSelectionModel extends DefaultListSelectionModel {

	    @Override
	    public void setSelectionInterval(int index0, int index1) {
	        super.setSelectionInterval(-1, -1);
	    }
	}
	
	//Add more functions for networked message updating.
}