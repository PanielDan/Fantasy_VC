package guis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import gameplay.Company;
import gameplay.GameFrame;
import gameplay.User;
import listeners.TableModel;
import utility.AppearanceConstants;
import utility.AppearanceSettings;

/*
 * Author: Danny Pan
 */



public class AuctionBidScreen extends JPanel {
	private JLabel timer, companyPicture, companyName, minimumBid, maximumBidLabel,
	maximumBidIcon, maximumBidAmount;
	private JTable companyStatistics;
	private JTextArea companyBio;
	private JLabel[] firmPicture, firmName, firmBid;
	private JButton bidButton;
	private JTextField bidAmount;
	private JPanel[] firmPanels;
	private Company company;
	private GameFrame gameFrame;
	
	
	public AuctionBidScreen(GameFrame gameFrame, Company company){
		this.company = company;
		this.gameFrame = gameFrame;
		initializeVariables();
		createGUI();
		addActionListeners();
	}
	
	private void initializeVariables(){
		//Timer Panel
		timer = new JLabel("0:45");
		
		//Middle Panel Variables
		companyPicture = new JLabel();
		companyName = new JLabel(company.getName());
		minimumBid = new JLabel("Minimum Bid: " + company.getAskingPrice() + "Million");
		companyBio = new JTextArea(company.getDescription());
		companyBio.setLineWrap(true);
		companyBio.setWrapStyleWord(true);
		companyBio.setEditable(false);
		
		//Table code		
    	Object[][] companyData = {
    			{"Name", company.getName()},
    			{"Tier", company.getTierLevel()},
    			{"Asking Price", company.getAskingPrice()},
    			{"Current Worth", company.getCurrentWorth()},
    	};
    	String[] columnNames = {"",""};
    	TableModel dtm = new TableModel();
    	dtm.setDataVector(companyData, columnNames);
    	companyStatistics = new JTable(dtm);
		companyStatistics.setForeground(AppearanceConstants.darkBlue);
		companyStatistics.setFont(AppearanceConstants.fontSmallest);
		
		//Firm bidding panel Variables
		intializeFirms();
		bidButton = new JButton("BID");
		bidAmount = new JTextField();
		maximumBidAmount = new JLabel();
		maximumBidLabel = new JLabel("CURRENT MAX BID");
		maximumBidIcon = new JLabel();
		
		//Testing code
		companyPicture.setIcon(new ImageIcon(company.getCompanyLogo().getScaledInstance((int)(150*company.getAspectRatio()), 150, Image.SCALE_SMOOTH)));
	}
	
	//Function to allocate information for each user.
	//This is probably going to be rewritten once the back end is connected.
	private void intializeFirms(){
		//I'll auto allocate 4 for spacing purposes and then just hide unused ones
		firmPicture = new JLabel[4];
		firmName = new JLabel[4];
		firmBid = new JLabel[4];
		
		for(int i = 0; i < 4; i++){
			firmPicture[i] = new JLabel();
			firmName[i] = new JLabel("");
			firmBid[i] = new JLabel("$");
			
			AppearanceSettings.setBackground(AppearanceConstants.darkBlue, firmPicture[i],firmName[i],firmBid[i]);
			AppearanceSettings.setForeground(AppearanceConstants.offWhite,firmName[i],firmBid[i]);
			firmName[i].setFont(AppearanceConstants.fontFirmName);
			firmBid[i].setFont(AppearanceConstants.fontBidAmount);
			AppearanceSettings.setCenterAlignment(firmPicture[i],firmName[i],firmBid[i]);
			AppearanceSettings.setSize(100,100,firmPicture[i]);
			firmPicture[i].setMaximumSize(new Dimension(100,100));
			firmName[i].setBorder(new EmptyBorder(5,5,5,5));
			firmBid[i].setBorder(new EmptyBorder(5,5,5,5));
		}
		
		Vector<User> userVect = gameFrame.game.getUsers();
		int i = 0;
		int j = 0;
		while(i < 4){
			if (j < userVect.size()){
				if (i == 0){
					firmPicture[i].setIcon(new ImageIcon(gameFrame.user.getUserIcon().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
					firmName[i].setText(gameFrame.user.getCompanyName());
					firmBid[i].setText("0 Million");
					i++;
				} else if (!userVect.get(j).getUsername().equals(gameFrame.user.getUsername())){
					firmPicture[i].setIcon(new ImageIcon(userVect.get(j).getUserIcon().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
					firmName[i].setText(userVect.get(j).getCompanyName());
					firmBid[i].setText("0 Million");
					i++;
					j++;
				}else{
					j++;
				}
			} else{
				firmPicture[i].setText("");
				firmName[i].setText("");
				firmBid[i].setText("");
				i++;
			}
		}
	}
	
	private void createGUI(){
		//Size accounts for chat window
		setPreferredSize(new Dimension(1280,504));
		setBackground(AppearanceConstants.lightBlue);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel timePanel = createTimePanel();
		JPanel companyInfoPanel = createCompanyInfoPanel();
		JPanel firmBiddingPanel = createFirmBiddingPanel();
		
		add(Box.createGlue());
		add(timePanel);
		add(Box.createGlue());
		add(companyInfoPanel);
		add(Box.createGlue());
		add(firmBiddingPanel);
		add(Box.createGlue());
	}
	
	private JPanel createTimePanel(){
		JPanel timePanel = new JPanel();
		timePanel.setLayout(new BoxLayout(timePanel,BoxLayout.PAGE_AXIS));
		
		timePanel.setBackground(AppearanceConstants.darkBlue);
		timePanel.setPreferredSize(new Dimension(200,60));
		timePanel.setMaximumSize(new Dimension(200,60));
		
		timer.setForeground(AppearanceConstants.offWhite);
		timer.setFont(AppearanceConstants.fontTimerMedium);
		AppearanceSettings.setCenterAlignment(timer);
		
		timePanel.add(timer);

		
		return timePanel;
	}
	
	private JPanel createCompanyInfoPanel(){
		JPanel companyInfoPanel = new JPanel();
		JPanel companyLabelsPanel = new JPanel();
		JPanel companyStatisticsPanel = new JPanel();
		JScrollPane companyTablePane = new JScrollPane(companyStatistics);
		JScrollPane companyBioPane = new JScrollPane(companyBio);
		
		JLabel statisticsLabel = new JLabel("Statistics");
		
		//remove borders
		companyTablePane.setFocusable(false);
		companyTablePane.setBorder(BorderFactory.createEmptyBorder());
		companyTablePane.getViewport().setBackground(AppearanceConstants.darkBlue);
		companyBioPane.setFocusable(false);
		companyBioPane.setBorder(new EmptyBorder(5,5,5,5));
		
		//Set padding
		companyName.setBorder(new EmptyBorder(5,5,5,5));
		minimumBid.setBorder(new EmptyBorder(5,5,5,5));
		companyStatistics.setBorder(new EmptyBorder(5,5,5,5));

		//Set layouts
		AppearanceSettings.setBoxLayout(BoxLayout.PAGE_AXIS, companyStatisticsPanel);
		AppearanceSettings.setBoxLayout(BoxLayout.LINE_AXIS, companyInfoPanel);
		companyLabelsPanel.setLayout(new BorderLayout());
		
		//Set Sizes
		AppearanceSettings.setSize(1200, 200, companyInfoPanel);
		companyInfoPanel.setMaximumSize(new Dimension(1200, 200));
		AppearanceSettings.setSize(600, 150, companyLabelsPanel);
		companyLabelsPanel.setMaximumSize(new Dimension(600, 150));
		AppearanceSettings.setSize(200, 150, companyTablePane);
		companyBioPane.setMaximumSize(new Dimension(200, 150));
		AppearanceSettings.setSize(200, 200, companyStatisticsPanel);
		companyStatisticsPanel.setMaximumSize(new Dimension(200, 200));

		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, companyInfoPanel,companyLabelsPanel,
				companyTablePane, companyBioPane, companyBio, companyStatisticsPanel,statisticsLabel);
		AppearanceSettings.setForeground(AppearanceConstants.offWhite, companyName, companyBio, minimumBid,
				statisticsLabel);
		AppearanceSettings.setFont(AppearanceConstants.fontSmall,companyName, minimumBid, statisticsLabel);
		AppearanceSettings.setCenterAlignment(statisticsLabel);
		companyBio.setFont(AppearanceConstants.fontSmallest);
		
		companyLabelsPanel.add(companyName, BorderLayout.NORTH);
		companyLabelsPanel.add(companyBioPane, BorderLayout.CENTER);
		companyLabelsPanel.add(minimumBid, BorderLayout.SOUTH);
		
		AppearanceSettings.addGlue(companyStatisticsPanel, BoxLayout.PAGE_AXIS, true, statisticsLabel, companyTablePane);
		
		AppearanceSettings.addGlue(companyInfoPanel, BoxLayout.LINE_AXIS, true, companyPicture, companyLabelsPanel);
		companyInfoPanel.add(companyStatisticsPanel);
		companyInfoPanel.add(Box.createHorizontalStrut(30));
		
		return companyInfoPanel;
	}
	
	private JPanel createFirmBiddingPanel(){
		JPanel firmBiddingPanel = new JPanel();
		firmPanels = new JPanel[4];
		JPanel maxBidPanel = new JPanel();
		JPanel maxBidFirmPanel = new JPanel();
		
		//Set Sizes
		AppearanceSettings.setSize(1200, 200, firmBiddingPanel);
		firmBiddingPanel.setMaximumSize(new Dimension(1200,200));

		AppearanceSettings.setSize(300, 200, maxBidPanel);
		maxBidPanel.setMaximumSize(new Dimension(300,150));
		
		//Set Layouts
		AppearanceSettings.setBoxLayout(BoxLayout.LINE_AXIS, firmBiddingPanel, maxBidFirmPanel);
		AppearanceSettings.setBoxLayout(BoxLayout.PAGE_AXIS, maxBidPanel);
		
		//Set Appearance Settings
		AppearanceSettings.setForeground(AppearanceConstants.offWhite, maximumBidAmount, maximumBidLabel);
		AppearanceSettings.setBackground(AppearanceConstants.lightBlue, firmBiddingPanel);
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, maxBidPanel, maxBidFirmPanel);
		AppearanceSettings.setFont(AppearanceConstants.fontSmall, maximumBidAmount, maximumBidLabel);
		AppearanceSettings.setCenterAlignment(maximumBidLabel);
		//Create all the user and their max bids
		createFirmsPanels(firmBiddingPanel);
		
		maxBidFirmPanel.add(Box.createHorizontalStrut(20));
		AppearanceSettings.addGlue(maxBidFirmPanel, BoxLayout.LINE_AXIS, false, maximumBidIcon, maximumBidAmount);
		maxBidPanel.add(Box.createVerticalStrut(20));
		AppearanceSettings.addGlue(maxBidPanel, BoxLayout.PAGE_AXIS, false, maximumBidLabel, maxBidFirmPanel);
		
		//Add
		AppearanceSettings.addGlue(firmBiddingPanel, BoxLayout.LINE_AXIS, true, firmPanels[0],
				firmPanels[1], maxBidPanel, firmPanels[2], firmPanels[3]);

		
		return firmBiddingPanel;
	}
	
	private void createFirmsPanels(JPanel firmBiddingPanel){
		//Constructing each firm's auction Icon
		//I can write a if statement that makes the selected user
		for(int i = 0; i < 4; i++){
			firmPanels[i] = new JPanel();
			
			AppearanceSettings.setBoxLayout(BoxLayout.PAGE_AXIS, firmPanels[i]);
			AppearanceSettings.setBackground(AppearanceConstants.lightBlue, firmPanels[i]);
			AppearanceSettings.setSize(150, 200, firmPanels[i]);

			firmPanels[i].add(firmName[i]);
			firmPanels[i].add(Box.createVerticalStrut(5));
			firmPanels[i].add(firmPicture[i]);
			firmPanels[i].add(Box.createVerticalStrut(5));


			if (i == 0){
				JPanel buttonTextPanel = new JPanel();
				buttonTextPanel.setLayout(new BoxLayout(buttonTextPanel,BoxLayout.LINE_AXIS));
				AppearanceSettings.setSize(150, 30, buttonTextPanel);

				JPanel buttonPanel = new JPanel();
				//buttonPanel.setLayout(new BorderLayout());
				bidButton.setOpaque(true);
				bidButton.setForeground(AppearanceConstants.offWhite);
				bidButton.setFont(AppearanceConstants.fontSmallBidButton);
				bidButton.setBackground(new Color(51,102,0));
				bidButton.setBorderPainted(false);
				bidButton.setVerticalAlignment(SwingConstants.TOP);
				buttonPanel.setMinimumSize(new Dimension(40,30));
				buttonPanel.setMaximumSize(new Dimension(40,30));
				buttonPanel.add(bidButton);
				
				JPanel textPanel = new JPanel();
				textPanel.setLayout(new BorderLayout());
				AppearanceSettings.setBackground(AppearanceConstants.lightBlue, textPanel, buttonPanel);
				textPanel.setMinimumSize(new Dimension(100,30));
				textPanel.setMaximumSize(new Dimension(100,30));
				textPanel.add(bidAmount);
				
				buttonTextPanel.add(textPanel);
				buttonTextPanel.add(buttonPanel, BorderLayout.CENTER);
				firmPanels[i].add(buttonTextPanel);
			}
			else{
				firmPanels[i].add(firmBid[i]);
			}
		}
	}
	
	private void addActionListeners(){
		bidButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Need to send out the message to update the bid amount
				/*
				if(networked){
					client.sendMessage(new AuctionBidUpdateMessage())
				} else {
					
				}
				*/
			}
			
		});
	}
	
	public void setCompany(Company company){
		this.company = company;
	}
}
