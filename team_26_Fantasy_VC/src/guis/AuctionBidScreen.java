package guis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

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
	
	
	public AuctionBidScreen(){
		initializeVariables();
		createGUI();
		addActionListeners();
	}
	
	private void initializeVariables(){
		//Timer Panel
		timer = new JLabel("0:45");
		
		//Middle Panel Variables
		companyPicture = new JLabel();
		companyName = new JLabel("Alliance Pharmaceuticals");
		minimumBid = new JLabel("Minimum Bid: $15,000,000");
		companyStatistics = new JTable();
		companyBio = new JTextArea("Alliance Pharmaceuticals an American large-cap business driven biomedical research corporation focused on the discovery of innovative medicine. Alliance is looking for a large investment to fund an expansion into european laboritories to further drug creation.");
		companyBio.setLineWrap(true);
		companyBio.setWrapStyleWord(true);
		companyBio.setEditable(false);
		
		//Firm bidding panel Variables
		intializeFirms();
		bidButton = new JButton("BID");
		bidAmount = new JTextField();
		maximumBidAmount = new JLabel("$20,400,000");
		maximumBidLabel = new JLabel("CURRENT MAX BID");
		maximumBidIcon = new JLabel();
		
		//Testing code
		ImageIcon alliance = new ImageIcon("resources/img/lobbies.png");
		Image companyIcon = alliance.getImage();
		companyPicture.setIcon(new ImageIcon(companyIcon.getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH)));
		
		ImageIcon tim = new ImageIcon("resources/img/profile.png");
		Image firmIcon = tim.getImage();
		maximumBidIcon.setIcon(new ImageIcon(firmIcon.getScaledInstance(75, 75,  java.awt.Image.SCALE_SMOOTH)));

	}
	
	//Function to allocate information for each user.
	//This is probably going to be rewritten once the back end is connected.
	private void intializeFirms(){
		//I'll auto allocate 4 for spacing purposes and then just hide unused ones
		firmPicture = new JLabel[4];
		firmName = new JLabel[4];
		firmBid = new JLabel[4];
		
		//INTIALIZED FOR TESTING PURPOSES
		ImageIcon tim = new ImageIcon("resources/img/profile.png");
		Image firmIcon = tim.getImage();

		for(int i = 0; i < 4; i++){
			firmPicture[i] = new JLabel();
			firmPicture[i].setIcon(new ImageIcon(firmIcon.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH)));
			firmName[i] = new JLabel("Timillionaries");
			firmBid[i] = new JLabel("$20,400,000");
			
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
		JScrollPane companyTablePane = new JScrollPane(companyStatistics);
		JScrollPane companyBioPane = new JScrollPane(companyBio);
		
		//remove borders
		companyTablePane.setFocusable(false);
		companyTablePane.setBorder(BorderFactory.createEmptyBorder());
		companyTablePane.getViewport().setBackground(AppearanceConstants.darkBlue);
		companyBioPane.setFocusable(false);
		companyBioPane.setBorder(new EmptyBorder(5,5,5,5));
		
		//Set padding
		companyName.setBorder(new EmptyBorder(5,5,5,5));
		minimumBid.setBorder(new EmptyBorder(5,5,5,5));

		//Set layouts
		AppearanceSettings.setBoxLayout(BoxLayout.LINE_AXIS, companyInfoPanel);
		companyLabelsPanel.setLayout(new BorderLayout());
		
		//Set Sizes
		AppearanceSettings.setSize(1200, 200, companyInfoPanel);
		companyInfoPanel.setMaximumSize(new Dimension(1200, 200));
		AppearanceSettings.setSize(600, 150, companyLabelsPanel);
		companyLabelsPanel.setMaximumSize(new Dimension(600, 150));
		AppearanceSettings.setSize(300, 180, companyTablePane);
		companyBioPane.setMaximumSize(new Dimension(300, 180));

		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, companyInfoPanel,companyLabelsPanel,
				companyTablePane, companyBioPane, companyBio);
		AppearanceSettings.setForeground(AppearanceConstants.offWhite, companyName, companyBio, minimumBid);
		AppearanceSettings.setFont(AppearanceConstants.fontSmall,companyName, minimumBid);
		companyBio.setFont(AppearanceConstants.fontSmallest);
		
		companyLabelsPanel.add(companyName, BorderLayout.NORTH);
		companyLabelsPanel.add(companyBioPane, BorderLayout.CENTER);
		companyLabelsPanel.add(minimumBid, BorderLayout.SOUTH);
		
		
		AppearanceSettings.addGlue(companyInfoPanel, BoxLayout.LINE_AXIS, true, companyPicture, companyLabelsPanel,
				new JSeparator(JSeparator.VERTICAL), companyTablePane);
		
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
				
		AppearanceSettings.addGlue(maxBidFirmPanel, BoxLayout.LINE_AXIS, true, maximumBidIcon, maximumBidAmount);
		AppearanceSettings.addGlue(maxBidPanel, BoxLayout.PAGE_AXIS, true, maximumBidLabel, maxBidFirmPanel);
		
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
}
