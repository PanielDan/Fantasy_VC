package guis;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.border.EmptyBorder;

import gameplay.GameFrame;
import gameplay.User;
import utility.AppearanceConstants;
import utility.AppearanceSettings;
import utility.Constants;
import utility.FinalUserButton;

public class FinalGUI extends JPanel {

	private GameFrame gameFrame;
	private Vector<FinalUserButton> userButtons;
	private JLabel winner, userIcon, userFirmName, totalEquity,
	percentGain, numCompanies, bestInvestment, portfolioLabel;
	private JButton done;
	private JTable portfolio;
	
	public FinalGUI(GameFrame gameFrame){
		this.gameFrame = gameFrame;
		initializeVariables();
		CreateGUI();
		addActionListeners();
	}
	
	private void initializeVariables(){
		userButtons = new Vector<FinalUserButton>();
		for (User user : gameFrame.game.getWinners()){
			userButtons.add(new FinalUserButton(user));
		}
		
		done = new JButton("Done");
		done.setOpaque(true);
		AppearanceSettings.unSetBorderOnButtons(done);
		
		//TODO All these labels need logic
		//userIcon = new JLabel(new ImageIcon(gameFrame.getIconImage().getScaledInstance(200, 200,java.awt.Image.SCALE_SMOOTH)));
		userIcon = new JLabel();
		ImageIcon profile = new ImageIcon("resources/img/profile.png");
		Image profileImage = profile.getImage();
		userIcon.setIcon(new ImageIcon(profileImage.getScaledInstance(250, 250, java.awt.Image.SCALE_SMOOTH)));
		userIcon.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		winner = new JLabel("Winner: JMoney Capital");
		userFirmName = new JLabel("JMoney Capital");
		totalEquity = new JLabel("Total value: 308 Million");
		numCompanies = new JLabel("Numbers of vompanies: 5");
		percentGain = new JLabel("Percentage gain: 1250%");
		bestInvestment = new JLabel("Best investment: Umbrella Corp");
		portfolioLabel = new JLabel("Portfolio");
		
		portfolio = new JTable();
		
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, winner, done);
		AppearanceSettings.setForeground(AppearanceConstants.offWhite, done, winner, userFirmName,
				totalEquity, percentGain, bestInvestment, portfolioLabel, numCompanies);
		AppearanceSettings.setFont(AppearanceConstants.fontSmall, totalEquity, percentGain,
				bestInvestment, numCompanies);
		AppearanceSettings.setFont(AppearanceConstants.fontLarge, portfolioLabel);
		AppearanceSettings.setFont(AppearanceConstants.fontHeader, userFirmName);
		AppearanceSettings.setFont(AppearanceConstants.fontButtonBig, winner,done);
		AppearanceSettings.setCenterAlignment(done, portfolioLabel);
		
		//Set Border
		userIcon.setBorder(new EmptyBorder(20,20,20,20));
		winner.setBorder(new EmptyBorder(5,20,5,20));
		portfolioLabel.setBorder(new EmptyBorder(5,5,5,5));
		
	}
	
	private void CreateGUI(){
		setSize(1280, 504);
		setBackground(AppearanceConstants.lightBlue);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel winnerPanel = new JPanel();
		JPanel winnerAndButtons = new JPanel();
		JPanel buttonsPanel = new JPanel();
		JPanel mainPanel = new JPanel();
		JPanel userDetailsPanel = new JPanel();
		JPanel portfolioPanel = new JPanel();
		JScrollPane tablePane = new JScrollPane(portfolio);
		
		//Remove borders on table pane;
		tablePane.setFocusable(false);
		tablePane.setBorder(BorderFactory.createEmptyBorder());
		tablePane.getViewport().setBackground(AppearanceConstants.darkBlue);
		
		//Set Sizes
		AppearanceSettings.setSize((int) winner.getPreferredSize().getWidth(), 60, winnerPanel);
		winnerPanel.setMaximumSize(new Dimension((int) winner.getPreferredSize().getWidth(),60));
		AppearanceSettings.setSize(1150, 60, winnerAndButtons);
		winnerAndButtons.setMaximumSize(new Dimension(1150,60));
		AppearanceSettings.setSize(1150, 300, mainPanel);
		mainPanel.setMaximumSize(new Dimension(1150,300));
		AppearanceSettings.setSize(300, 200, tablePane);
		tablePane.setMaximumSize(new Dimension(300,200));
		
		//Set layouts
		winnerPanel.setLayout(new BorderLayout());
		winnerAndButtons.setLayout(new BorderLayout());
		AppearanceSettings.setBoxLayout(BoxLayout.LINE_AXIS, winnerAndButtons, buttonsPanel, mainPanel);
		AppearanceSettings.setBoxLayout(BoxLayout.PAGE_AXIS, userDetailsPanel, portfolioPanel);
		
		//Set Appearance settings
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, mainPanel, userDetailsPanel,
				portfolioPanel, tablePane, winnerPanel);
		AppearanceSettings.setBackground(AppearanceConstants.lightBlue, winnerAndButtons, buttonsPanel);
		
		//Top button bar panel
		winnerPanel.add(winner);
		AppearanceSettings.addGlue(winnerAndButtons, BoxLayout.LINE_AXIS, false, winnerPanel);
		for (FinalUserButton fub : userButtons){
			winnerAndButtons.add(Box.createHorizontalStrut(10));
			winnerAndButtons.add(fub);
		}
		
		//User Details in main panel
		AppearanceSettings.addGlue(userDetailsPanel, BoxLayout.PAGE_AXIS, true, userFirmName);
		userDetailsPanel.add(totalEquity);
		userDetailsPanel.add(percentGain);
		userDetailsPanel.add(numCompanies);
		AppearanceSettings.addGlue(userDetailsPanel, BoxLayout.PAGE_AXIS, false, bestInvestment);

		//ScrollPane Panel;
		portfolioPanel.add(Box.createGlue());
		portfolioPanel.add(portfolioLabel);
		portfolioPanel.add(Box.createVerticalStrut(10));
		portfolioPanel.add(tablePane);
		portfolioPanel.add(Box.createGlue());
		
		
		mainPanel.add(userIcon);
		mainPanel.add(Box.createHorizontalStrut(20));
		AppearanceSettings.addGlue(mainPanel, BoxLayout.LINE_AXIS, false, userDetailsPanel);
		mainPanel.add(portfolioPanel);
		mainPanel.add(Box.createHorizontalStrut(20));
		
		
		add(Box.createGlue());
		add(winnerAndButtons);
		add(Box.createGlue());
		add(mainPanel);
		add(Box.createGlue());
		add(done);
		add(Box.createGlue());
	
	}
	
	private void addActionListeners(){
		for(FinalUserButton fub : userButtons){
			fub.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					User selectedUser = fub.getUser();
					userIcon.setIcon(new ImageIcon(selectedUser.getUserIcon().getScaledInstance(250, 250, java.awt.Image.SCALE_SMOOTH)));
					userFirmName.setText(selectedUser.getCompanyName());
					//TODO Needs to replaced with total equity or value number
					totalEquity.setText("Total value: " + Double.toString(selectedUser.getCurrentCapital()) + Constants.million);
					percentGain.setText("Percent Gain: ");
					numCompanies.setText("Number of companies:");
					bestInvestment.setText("Best investment: ");
					
					//TODO needs logic to set the table.
				}	
			});
		}
		
		done.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO go back to intro screen. If Guest do we want to ask to replay?
				
			}
			
		});
	}
}
