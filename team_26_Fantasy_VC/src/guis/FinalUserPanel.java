package guis;

import java.awt.BorderLayout;
import java.awt.Image;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gameplay.Company;
import gameplay.User;
import utility.AppearanceConstants;
import utility.AppearanceSettings;
import utility.ImageLibrary;

/**
 * A listing for each {@code User} to be shown on the
 * {@code FinalGUI} panel.
 * @author alancoon
 *
 */
public class FinalUserPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User user;
	private double delta;
	
	private JLabel avatarLabel;
	private JLabel usernameLabel;
	private JLabel firmnameLabel;
	private JLabel profitLabel;
	private JLabel deltaLabel;
	private JLabel bioLabel;

	public FinalUserPanel(User user) {
		this.user = user;
		initializeComponents();
		createGUI();
	}


	private void createGUI() {
		setSize(400, 100);
		setBackground(AppearanceConstants.mediumGray);
		setLayout(new BorderLayout());
		JPanel northPanel = new JPanel(new BorderLayout());
		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel southPanel = new JPanel(new BorderLayout());
		
		northPanel.add(avatarLabel, BorderLayout.WEST);
		northPanel.add(usernameLabel, BorderLayout.EAST);
		JPanel centerEastPanel = new JPanel();
		
		centerEastPanel.add(profitLabel);
		centerEastPanel.add(deltaLabel);
		
		centerPanel.add(firmnameLabel, BorderLayout.WEST);
		centerPanel.add(centerEastPanel, BorderLayout.EAST);
		
		southPanel.add(bioLabel, BorderLayout.WEST);
		
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		
		AppearanceSettings.setFont(AppearanceConstants.fontMedium, usernameLabel);
		AppearanceSettings.setFont(AppearanceConstants.fontSmall, firmnameLabel, profitLabel, deltaLabel);
		AppearanceSettings.setFont(AppearanceConstants.fontSmallest, bioLabel);
		AppearanceSettings.setBackground(AppearanceConstants.mediumGray, northPanel, centerPanel, southPanel);
		AppearanceSettings.setForeground(AppearanceConstants.offWhite, usernameLabel, firmnameLabel, bioLabel);
		
		/* If the person ends up with a positive profit, make the numbers green, else make it red. */
		if (delta >= 0.00) {
			AppearanceSettings.setForeground(AppearanceConstants.lightGreen, profitLabel, deltaLabel);
		} else {
			AppearanceSettings.setForeground(AppearanceConstants.red, profitLabel, deltaLabel);
		}
	}


	private void initializeComponents() {
		Image image = ImageLibrary.getImage(user.getUserIconString());
		avatarLabel = new JLabel(new ImageIcon(image.getScaledInstance(125, 125, Image.SCALE_SMOOTH)));
		usernameLabel = new JLabel(user.getUsername());
		firmnameLabel = new JLabel(user.getCompanyName());
		
		DecimalFormat df = new DecimalFormat("#.##");
		double currentCapital = user.getCurrentCapital();
		for (Company c : user.getCompanies()) {
			currentCapital += c.getCurrentWorth();
		}
		
		delta = 100 * ((user.getTotalProfit() - user.getStartingCapital()) / user.getStartingCapital());
		profitLabel = new JLabel("$" + df.format(currentCapital) + "M");
		deltaLabel = new JLabel(df.format(delta) + "%");
		bioLabel = new JLabel(user.getUserBio());
	}
}
